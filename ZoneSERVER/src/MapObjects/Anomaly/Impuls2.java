package MapObjects.Anomaly;

import Gm.SpatialHashGrid;
import Gm.StateWorld;
import MapObjects.Artefakts.Obereg;
import MapObjects.Artefakts.Obereg1;
import MapObjects.MapObject;
import MapObjects.Units.Player;
import Modules.AnomalyJump;
import Server.NettyServerHandler;
import Utils.AddMOparameters;
import Utils.Overlap;
import Utils.Util;
import Utils.rand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 777 on 01.09.2017.
 */
public class Impuls2 extends MapObject {
    // список игроков в радиусе видимости аномалии
    List<Player> players = new ArrayList<>();
    // список всех объектов на карте на которой расположена аномалия
    // пока не используется так как пока аномалия взаимодействует тольеко с игроками
    // но в будующем будет взаимодействовать со всеми юнитами
    HashMap<Long, MapObject> mapobjects;
    // урон аномалии и направление отбрасывания персонажа
    public int uron,znaprav;
    // счетчик времени времени использующийся при зарядке аномалии (после срабатывания она некоторое вря не активна)
    float timezarad;
    // состояние
    int state;
    // объект который атакует аномалия
    MapObject atakobject;
    // можно задать направление отбрасывания а можно что бы направление выбералось случайным образом
    boolean randnaprav;
    // ипользуется для зопоминания позиции игрока
    float cellx,celly;
    Obereg obereg;
    List<AddMOparameters>listaddobject;
    SpatialHashGrid grid;
    public Impuls2(int x, int y, HashMap<Long, MapObject> mapobjects, int napravlenie, List<AddMOparameters>listaddobject, SpatialHashGrid grid) {
        // создаем новый id
        id = generatorId.incrementAndGet();
        this.mapobjects=mapobjects;
        this.x = x;
        this.y = y;
        this.listaddobject=listaddobject;
        this.grid=grid;
        centx = x;
        centy = y;
        tip = Util.IMPULS2;
        podtip=Util.PPANOMALY;
        uron = 30;
        radius = 33;
        raddclient+=radius+1;
        // если направление не задано(0) тогда будем выставлять случайное
        if(napravlenie==0)randnaprav=true;
        else znaprav=napravlenie;
        // аномаля невидима для игрока (пеока не сработает)
        clientvisible=false;

        int rnd= rand.getIntRnd(0,11);
        if(rnd>=4){
            rnd=rand.getIntRnd(0,3);
            if(rnd==0)obereg=new Obereg(x,y,this);
            if(rnd==1||rnd==2)obereg=new Obereg1(x,y,this);
            listaddobject.add(new AddMOparameters(obereg,false,false,0));}
    }
    @Override
    public void run(float delta) {
        switch(state){
            // идет проверка не столкнуля ли кто из игроков с аномалие
            case 0:
                for (MapObject  mo: mapobjects.values()) {
                    // если объект игрок и его можно атаковать и если он дотронулся
                    // до аномалии (расстояние между центральной позицией игрока и аномалии стало меньше радиуса аномалмм)
                    if ((mo.tip==Util.PLAYER||mo.tip==Util.KABAN)&&mo.canbeattacked && Overlap.pointPoint(mo.centx, mo.centy, centx, centy, radius)) {
                        atakobject=mo;
                        // если направление не задано выберем случайное
                        if(randnaprav)znaprav= rand.getIntRnd(2,8);
                        state=1;
                        cellx=atakobject.x;
                        celly=atakobject.y;
                        if(mo.tip==Util.KABAN&&obereg!=null){
                            obereg.clientvisible=true;
                            sendMsgPlayers("1/" +obereg.tip + "/" + obereg.id + "/" + (int) x + "/" + (int) y);}
                        // отправляем сообщение(всем игрокам(клиентам) в зоне видимости) о том что эта аномалия атакует такого(id) игрока
                        sendMsgPlayers("1/" + tip + "/" + id + "/" + (int) x + "/" + (int) y+"\0"+"2/1/" + id+"/"+atakobject.id+"/"+znaprav);
                        break;
                    }
                }
                break;
            // небольшой промежуток времени(пока проигрывается анимация на клиенте)аномалия удердивает игрока на одном месте
            // после этого навешивает на него модификатор состояния отвечеющий за отбрасывание(AnomalyJump)
            case 1:
                timezarad += delta * 10;
                //не даем двигаться игроку
                if(atakobject!=null)atakobject.setPosition(cellx,celly);
                // анимация на клиенте проиграна настало время что бы откинуть игрока
                if (timezarad > 7) {
                    // если объект не равен нулл и если он не находится уже под воздействием какого нибудь модификатора
                    if(atakobject!=null&&atakobject.state!=6){
                        // наносим урон
                        atakobject.ataked(id,uron,Util.AEROURON,true);
                        // навешиваем на игрока модификатор состояния
                        // в модификатор передаем объект к которому он присвоен и направление
                        atakobject.signal(4,new AnomalyJump(atakobject,znaprav,80,15,true));}
                    state=2;
                    atakobject=null;
                    timezarad = 0;
                }
                break;
            // аномалия заряжается (в это вря она не активна)
            case 2:
                timezarad += delta * 10;
                if (timezarad > 20) {
                    state=0;
                    timezarad = 0;
                }
                break;
        }
    }
    // если аномалия попала в поле зрения игрока как раз в то время когда она атакует другого игрока
    @Override
    public String getStateForClient() {
        if(state==1)return"1/" + tip + "/" + id + "/" + (int) x + "/" + (int) y+"\0"+"2/1/" + id+"/"+(atakobject!=null?atakobject.id:-1)+"/"+znaprav+"/"+timezarad;
        return null;
    }
    // вызывается в классе WMap в методе addObjectsToPlayer
    // добавляем игрока в свои списки доп параметров нет
    @Override
    public String addedAndDop(Player pl, String str) {
        pl.mapobjects.put(id, this);
        players.add(pl);
        //
        return str;
    }

    //1 stop ataked 2 rem playuer 3 you kill me 5 activ anomaly
    @Override
    public void signal(int tip,Object object) {
        switch (tip) {
            // логика удаления объектов из радиуса видимости идет в классе игрока
            // когда игрок выходит из радиуса видимости он посылает сюда сигнал - типа удали меня отовсюду ты меня уже не видиш
            case 2:
                long idv=(Long)object;
                for (Player pl : players) {
                    if (pl.id == idv) {
                        players.remove(pl);
                        if(players==atakobject){atakobject=null;
                            state=0;
                            timezarad = 0;}
                        break;
                    }
                }
                break;
            //start anomaly
            // аномалию задело болтом
            case 5:
                if(state==0){state=1;
                    sendMsgPlayers("1/" + this.tip + "/" + id + "/" + (int) x + "/" + (int) y+"\0"+"2/1/" + id+"/"+(atakobject!=null?atakobject.id:-1)+"/"+znaprav);
                }
                break;
            case 7:
                if(obereg==null){
                    int rnd= rand.getIntRnd(0,11);
                    if(rnd>=4){
                        rnd=rand.getIntRnd(0,3);
                        if(rnd==0)obereg=new Obereg(x,y,this);
                        if(rnd==1||rnd==2)obereg=new Obereg1(x,y,this);
                        listaddobject.add(new AddMOparameters(obereg,false,false,0));
                    }
                }
                break;
            case  8:
                obereg=null;
                break;
        }
    }
    // отправлеяет данные всем игрокам в радиусе видимости
    public void sendMsgPlayers(String msg) {
        for (Player unit : players) {
            NettyServerHandler.sendMsgClient(msg, unit.idchanel);
        }
    }
}