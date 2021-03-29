package MapObjects.Units;

import Gm.GameMechanics;
import Gm.MsgSwitchMap;
import Gm.skils.Skil;
import InventItem.core.Inventar;
import InventItem.core.Weapon;
import AccountService.UserDataSet;
import MapObjects.Bolt;
import MapObjects.MapObject;
import Gm.SpatialHashGrid;
import Modules.Life;
import Modules.Modifikator;
import Server.NettyServerHandler;
import Utils.AddMOparameters;
import Utils.Overlap;
import Utils.Rectang;
import Utils.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
наверное самый сложный класс
класс игрока
 */
public class Player extends MapObject {
    private final int LEFT = 5, RIGHT = 3, UP = 2, DOWN = 4, STOP = 1, UR = 6, RD = 7, DL = 8, LU = 9;
//состояние игрока
    public final int DVIG=0,DEAD=1,ATAKA=2,PEREZARAD=3,VDOME=4,SITTING=5, MODIFIKATOR =6;
    // имя игрока
    public String name;
    public String nameclan;
    // параметры игрока
    public	float speed,uron,holod,golod,zhazda,radiation,krovotech;
    // кол-во защиты от разных типов повреждений
    public int fizarmor,ellarmor,pullarmor;
    // скорость хотьбы
    public float normalspeed=47;
    // модуль отвечающий за кол-во жизней игрока
    public Life pmLife;
    // направление хотыбы, направление хотьбы без значения STOP=1(требуется для некоторых вещей)
    // и направление хотьбы без движения по диагоналям (требуется для некоторых вещей)
    public int napravlenie=STOP,nsnapravlenie=DOWN,urdlnapravlenie = DOWN;
    // номер карты на которой находится игрок и время смерти
    public int idmap,timedie;
    // авторизован ли игрок мертв ли он или находится ли он в доме
    public boolean autorized,dead,vdome;
    // объект в которой стреляет игрок(другой игрок кабан зомби фригер)
    private MapObject atakobject;
    // прямоуголшьники локации
    public SpatialHashGrid grid;
    // требуется для базы данных
    public UserDataSet uds;
    // сидит ли игролк у костра
    public boolean ukostra;
    // инвентарь
    public Inventar inventar;
    // переменная для времени смерти и айдишник для базы данных
    public long ldt, iddatabase;
    // время смертти
    public int tdead = 10000;
    // старые координаты позиции игрока на которые перемещяется игрок при столкновении с каким нибудь хитбоктом
    public float oldx, oldy;
    // список объектов которые находятся в зоне видимости игрока
    public Map<Long, MapObject> mapobjects = new HashMap<>();
    // активные квесты
    public Map<Integer, Integer> qvests = new HashMap<>();
    // для добавления объектоа на карту
    public List<AddMOparameters>addmapobjects;
    // снизу список переменных нужных для боя
    public boolean ataka;
    public Weapon gun;
    public float atakatime;
    public int timevistrel;
    public float radtime;
    int levelraduron;
    long radiationid;
    // некоторые прямоугольники выполняют роль домов в которых можно спрятаться от выброса
    // в переменную записывается ближайший прямоугольник
    private Rectang ardom;
    // состояние игрока в которое может быть записал любой модификатор (сейчас их 2: когда амомалия импульс отбрасывает героя и когда аномалия телепорт телепортирует)
    Modifikator mod;
    // болт взаимодействует с некоторыми аномалиями (игрок кидает болт вперед перед собой)
    public Bolt bolt;
    // указывает играет ли игрок на гитаре
    boolean gitara;
    public long idobmen;
    public int exp;
    public Map<Integer, Skil> numstoskils = new HashMap<>();
    public String sskill;
    public boolean detektoranomalys,inraddetect;
    public Player(int idchanel) {
        //устанавливаем id канала связси
        this.idchanel = idchanel;
        tip = Util.PLAYER;
        podtip=Util.PPPLAYER;
        id = generatorId.incrementAndGet();
        plasrecx = 18;
        plasrecy = 5;
        rectang = new Rectang(x + plasrecx, y + plasrecy, 11, 10);
        centx = rectang.x + rectang.width / 2;
        centy = rectang.y + rectang.height / 2;
        inventar = new Inventar(this);
        pmLife= new Life();
        unit=true;
        bolt=new Bolt(mapobjects);
        radiationid=MapObject.generatorId.incrementAndGet();
    }
// установка направления движения
    public void setMove(int naprav) {
        switch (naprav) {
            case UP:
                napravlenie = UP;
                urdlnapravlenie = napravlenie;
                break;
            case RIGHT:
                napravlenie = RIGHT;
                urdlnapravlenie = napravlenie;
                break;
            case DOWN:
                napravlenie = DOWN;
                urdlnapravlenie = napravlenie;
                break;
            case LEFT:
                napravlenie = LEFT;
                urdlnapravlenie = napravlenie;
                break;
            case STOP:
                napravlenie = STOP;
                break;
            case UR:
                napravlenie = UR;
                urdlnapravlenie = UP;
                break;
            case RD:
                napravlenie = RD;
                urdlnapravlenie = RIGHT;
                break;
            case DL:
                napravlenie = DL;
                urdlnapravlenie = DOWN;
                break;
            case LU:
                napravlenie = LU;
                urdlnapravlenie = LEFT;
                break;
        }
        if(napravlenie>1)nsnapravlenie=napravlenie;
    }
@Override
    public void run(float delta) {
        // обновляем только того состояния к которм сейчас находится игрок
        switch (state) {
            // ходит
            case DVIG:
                move(delta);
                //обновляем логику болта
                if(bolt.run)bolt.run(delta);
                //detector anomalys
                //перенести на клиент
                if(detektoranomalys&&!inraddetect){
                    for(MapObject mo:mapobjects.values()){
                        if(mo.podtip==Util.PPANOMALY&&Overlap.pointPoint(centx,centy,mo.centx,mo.centy,mo.radius+65)){
                            NettyServerHandler.sendMsgClient("29/0",idchanel);
                            inraddetect=true;
                            break;
                        }
                    }
                }
                    if(inraddetect){
                        boolean out=true;
                        for(MapObject mo:mapobjects.values()) {
                            if(mo.podtip==Util.PPANOMALY&&Overlap.pointPoint(centx,centy,mo.centx,mo.centy,mo.radius+65)){
                                out=false;
                                break;
                            }
                        }
                        if(out){inraddetect=false;NettyServerHandler.sendMsgClient("29/1",idchanel);}
                    }
                break;
                //помер
            case DEAD:
                dead();
                break;
                //атака
            case ATAKA:
                Ataka(delta);
                break;
                //перезарядка оружия
            case PEREZARAD:
                if(gun!=null){
                gun.perezaradka(delta, this);}
                else{state=DVIG;}
                break;
                //модифицированное состояние
            case MODIFIKATOR:
                mod.run(delta,grid);
                break;
        }
        //смерть
        if (state != DEAD && pmLife.life <= 0) {if(name.equals("korvin"))pmLife.life=0;else setState(DEAD, null); }
        if (state!=DEAD)radiation(delta);
        //удаление обьектов которые вышли за предел видимости (напомню добавляются они в класее WMap методом addObjectsToPlayer)
        for (MapObject mo : mapobjects.values()) {
            if (!Overlap.pointPoint(centx, centy, mo.centx, mo.centy, mo.raddclient+50)) {
                // посылаем объекту сигнал что бы тот тоже удалил игрока из своего списка
                mo.signal(2,id);
                mapobjects.remove(mo.id);
                break;
            }
        }
        // обновление логики модуля жизней
        pmLife.run(delta);
        //доступность использования предметов инвентаря
    if(!inventar.canuse){
     inventar.timecanuse+=delta*10;
     if(inventar.timecanuse>200)inventar.canuse=true;
    }
    }
// движение игрока
    private void move(float delta) {
        float speed=this.speed;
        if(speed>90)speed=90;
        switch (napravlenie) {
            case UP:
                setPosition(x,y+speed*delta);
                break;
            case RIGHT:
                setPosition(x + speed * delta,y);
                break;
            case DOWN:
                setPosition(x,y-speed*delta);
                break;
            case LEFT:
                setPosition(x - speed * delta,y);
                break;
            case UR:
                setPosition(x + speed * delta,y + speed * delta);
                break;
            case RD:
                setPosition(x + speed * delta,y - speed * delta);
                break;
            case DL:
                setPosition(x - speed * delta,y - speed * delta);
                break;
            case LU:
                setPosition(x - speed * delta,y + speed * delta);
                break;
        }
        // если игрок столкнулся с квадратом возращаем его назад (на ~1 пиксель)
        if (inRec(grid)) {
            x = oldx;
            y = oldy;
        } else {
            oldx = x;
            oldy = y;
        }
    }
// проверка не столкнулся ли игрок с каким нибудь хитбоксом и проверка не вышел ли игрок за пределы локации
  public boolean inRec(SpatialHashGrid grid) {
        if (rectang.x > 1588 || rectang.x < 1 || rectang.y > 1589 || rectang.y < 1) return true;
        List<Rectang> colliders = grid.getPotentialColliders(rectang);
        for (Rectang re : colliders) {
            if (Overlap.overlapRectang(re, rectang)) {
                // некоторые прямоугольники выполняют роль домов в которые можно зайти
               if(re.dom)ardom =re;
                return true;
            }
        }
        return false;
    }
//переделать убрать timevistrel;
    void Ataka(float delta) {
        // если идет атака и у игрока есть оружие и объект может быть атакован
        if (ataka && gun != null && atakobject != null && atakobject.canbeattacked) {
            // если патроны в оружии закончиились
            if (gun.dopintcolvo == 0) {
                gun.perezarad = true;
                state = PEREZARAD;
            }
            atakatime += 10 * delta;
            // если объект не вышел за пределы радиуса стрельбы оружия
            if (Overlap.pointPoint(centx, centy, atakobject.centx, atakobject.centy, gun.dalnostrel)) {
                // если настало время выстрела
                if (atakatime > timevistrel) {
                    // стреляем
                    gun.fire(this);
                    // устанавливаем новое время выстрела в зависимости от скорострельности оружия
                    timevistrel = (int) (atakatime + gun.skorostrel);
                }
            } else {
                // если объект вышел из радиуса стрельбы
                ataka = false;
                state = DVIG;
                // посылаем объекту сигнал который говорит ему - ну я в тебя уже не стреляю
                atakobject.signal(1,id);
                // посылаем всем игрокам (клиентам) которые находятся в поле зреня сообщени о том что атака завершена
                sendMsg("2/10/" + id,true);
            }
        } else {
            // все тоже самое что и выше
            ataka = false;
            state = DVIG;
            sendMsg("2/10/" + id,true);
            if (atakobject != null) {
                atakobject.signal(1,id);
                atakobject = null;
            }
        }
    }
    // игрок погиб идет отсчет времени до воскрешения
    void dead() {
        if (System.currentTimeMillis() > ldt) {
            state = DVIG;
            pmLife.life = pmLife.maxlife;
            radiation = 0;
            setTrueCanbeAtaked();
            if(idmap!=2){
                GameMechanics.ms.sendMessage(new MsgSwitchMap(null,GameMechanics.ms.getAddressService().getAddressGameMechanics(),this,2,757,1048));}
        }
    }
    // описание метода в супер классе MapObject
    @Override
    public String addedAndDop(Player pl,String str){
        pl.mapobjects.put(id,this);
        // доп параметры игрока
        str+="/"+name+"/"+(int)speed+"/"+(int)pmLife.life+"/"+radiation+"/"+inventar.getTipOdeto()+"/"+pmLife.maxlife+"/"+nameclan;
        String mlife=null;
        //allMinusPlusLife
        mlife=pmLife.getAllTmpl(id);
        if(mlife!=null)str+="\0"+mlife;
        return str;
    }
    //состаяние обьекта для клиента во время добавления (когда игрок появляется в зоне видимости другого игрока)
    @Override
    public String getStateForClient() {
        switch (state) {
            case DVIG:
                return "2/1/" + id + "/" + (int) x + "/" + (int) y + "/" + napravlenie;

             case ATAKA:
                 if(atakobject!=null)return"2/2/"+id+"/"+atakobject.id+"/"+gun.tipitem+"/"+gun.dopintcolvo+"/"+(inventar.isItem(14,true)!=null?inventar.isItem(14,true).dopintcolvo:0)+
                 "/"+timevistrel+"/"+atakatime;
                 else {state=DVIG;return "2/1/" + id + "/" + (int) x + "/" + (int) y + "/" + napravlenie;}
            case PEREZARAD:
                if(gun!=null){
                if (!ataka) return "2/4/" + id + "/" + gun.tipitem + "/"+gun.dopintcolvo+"/"+(inventar.isItem(14,true) != null ? inventar.isItem(14,true).dopintcolvo : 0)+"/"+ gun.timep;
                else if(atakobject!=null){
                    return "2/4/" + id + "/" + gun.tipitem + "/" + gun.dopintcolvo+"/"+(inventar.isItem(14,true) != null ? inventar.isItem(14,true).dopintcolvo : 0)+
                            "/" + gun.timep + "/" + atakobject.id;}
                }
            case DEAD:
                return "2/3/" + id + "/" + (int) (ldt - System.currentTimeMillis());
            case VDOME:
                return "8/1/" + id;
            case SITTING:
                return "2/6/" + id + "/" + (int) x + "/" + (int) y + "/" + nsnapravlenie+"/"+(gitara?1:0);
            case MODIFIKATOR:
                return mod.getState();
        }
        return null;
    }

    void radiation(float delta) {
        if (radiation > 0) {
            radtime += 10 * delta;
            if (radtime > 35) {
                radiation-=0.3;
                radtime=0;
                if(radiation>3&&levelraduron==0){ataked(radiationid,3,30,0,111,true,true,false);
                levelraduron++;}
                if(radiation<3&&levelraduron==1){pmLife.stopPlusMinusLife(radiationid);levelraduron--;sendMsg("3/2/"+id+"/"+radiationid,true);}
                if(radiation>10)radiation=10;
            }
        }
    }
// установка состояния игрока
    public void setState(int newstate, String[] str) {
        // сначало смотрим какое было предл идущее состояние и выполняем необходимые действия
        switch (state) {
            case ATAKA:
                if (newstate != PEREZARAD) {
                    ataka = false;
                    if(atakobject!=null)atakobject.signal(1,id);
                    atakobject = null;
                }
                if(newstate==PEREZARAD)atakobject.signal(1,id);
                break;
            case PEREZARAD:
                if(newstate==DVIG||newstate==DEAD){ataka=false;
                atakobject=null;}
               if(gun!=null){ gun.perezarad = false;
                gun.timep = 0;}
                break;
            case DVIG:
                break;
            case VDOME:
                if (newstate != DVIG) return;
                //убераем ситуацию когда перс на клиенте уходит зайдя в дом
                if(newstate==DVIG&&str!=null)return;
                if (str == null) {
                    state = DVIG;
                    vdome = false;
                    sendMsg("8/2/" + id,true);
                    return;
                }
                break;
            case DEAD:
                return;
            case SITTING:
                if(newstate==DVIG||newstate==DEAD||newstate==SITTING){}else{
                    return;
                }
                break;
            case MODIFIKATOR:
                if(newstate!=DEAD)return;
                break;
        }
// потом устанавливаем новое состояние
        switch (newstate) {
            case DVIG:
                if (str != null){ setMove(Integer.parseInt(str[2]));
                sendMsg("2/1/" + id + "/" + (int) x + "/" + (int) y + "/" + napravlenie + "/" + (int) pmLife.life,true);}
                setTrueCanbeAtaked();
                state = DVIG;
                break;
            //переделать хрень после перезарядки
            case ATAKA:
               if(str!=null){MapObject unit = mapobjects.get(Long.parseLong(str[2]));
                    if (unit == null||((idmap == 2||idmap==11)&&unit.tip==Util.PLAYER)) return;
                   atakobject = unit;}
                if(atakobject!=null&&atakobject.canbeattacked&&gun!=null&&
                        Overlap.pointPoint(centx, centy, atakobject.centx, atakobject.centy, gun.dalnostrel)){
                        ataka = true;
                        setTrueCanbeAtaked();
                        timevistrel = gun.skorostrel;
                        atakatime = 0;
                        if(str!=null)sendMsg("2/2/" + id + "/" + atakobject.id + "/" + gun.tipitem + "/" + gun.dopintcolvo+"/"+(inventar.isItem(14,true)!=null?inventar.isItem(14,true).dopintcolvo:0),true);
                        state = ATAKA;
                    inventar.canuse=false;
                    inventar.timecanuse=0;
                        atakobject.ataked(id,gun.uron,gun.skorostrel,gun.dopintcolvo*gun.skorostrel,Util.PULLURON,false,true,false);}
                else{
                    ataka=false;state=DVIG;
                    sendMsg("2/10/" + id,true);
                }
                break;
            case VDOME:
                if(inventar.canuse){
                if(proverkaOkoloDoma()){
                state = VDOME;
                vdome = true;
                canbeattacked=false;
                sendMsg("8/1/" + id,true);
                setMove(1);}}
                break;
            case PEREZARAD:
                if (gun!=null&&gun.oboima == gun.dopintcolvo) return;
                state = PEREZARAD;
                setTrueCanbeAtaked();
                gun.perezarad = true;
                if (str != null) sendMsg("2/4/" + id + "/" + gun.tipitem+"/"+gun.dopintcolvo+"/"+(inventar.isItem(14,true)!=null?inventar.isItem(14,true).dopintcolvo:0),true);
                break;
            case DEAD:
                sendMsg("2/3/" + id,true);
                canbeattacked=false;
                pmLife.stopAll();
                napravlenie=STOP;
                ldt = System.currentTimeMillis() + tdead;
                state = DEAD;
                inventar.throwItemAll(addmapobjects);
                break;
            case SITTING:
                state=SITTING;
                if (str != null&&str.length>2)gitara=true;
                else gitara=false;
                sendMsg("2/6/" + id + "/" + (int) x + "/" + (int) y + "/" + nsnapravlenie+"/"+(gitara?1:0),true);
                break;
            case MODIFIKATOR:
                state=MODIFIKATOR;
                break;
        }
    }
    public int getState(){return state;}

    private void setTrueCanbeAtaked(){
        if(state!=DEAD&&state!=VDOME)canbeattacked=true;
    }
    //1 stop ataked 2 rem playuer 3 you kill me 4 anomaly jump
    @Override
    public void signal(int tip,Object object){
        switch(tip){
            case 1:
                long idv=(Long)object;
                sendMsg("3/2/"+id+"/"+idv,true);
                pmLife.stopPlusMinusLife(idv);
                break;
            case 2:
                long idv2=(Long)object;
                mapobjects.remove(idv2);if(atakobject!=null&&atakobject.id==idv2)atakobject=null;
                break;
                //ты убил
            case 3:
                long idv3=(Long)object;
                MapObject mo=mapobjects.get(idv3);
                if(mo.tip==Util.KABAN){
                    exp+=4;
                if(qvests.containsKey(1)){int colkil=qvests.get(1);
                colkil+=1;qvests.put(1,colkil);}}
                if(mo.tip==Util.ZOMBI){
                    exp+=2;
                    if(qvests.containsKey(3)){int colkil=qvests.get(3);
                        colkil+=1;qvests.put(3,colkil);}}
                if(mo.tip==Util.KRUSA){
                    exp+=6;
                }
                break;
            //start modifikator
            case 4:
                if(pmLife.life>0){
                mod=(Modifikator)object;
                setState(MODIFIKATOR,null);}
                break;
                //stop modifikator
            case 5:
                state=DVIG;napravlenie=1;
                mod=null;oldx=x;oldy=y;
                break;
                //6 npc vibros
        }
    }
    // с клиента приходит сообщение что он хочет войти в дом
    // сервер проверяет а действительно ли игрок находится рядом с домом
    private boolean proverkaOkoloDoma(){
        if(ardom!=null){
        float radius= ardom.width> ardom.height? ardom.width/2+ardom.height/3: ardom.height/2+ardom.width/3;
        if(Overlap.pointPoint(centx,centy,ardom.x+ardom.centw,ardom.y+ardom.centh,(int)radius))return true;}
        return false;
    }
// высчитываем конечный урон для здоровья с учетом защиты
    private float uron(int tipuron,float uron){
        float ur=1;
        float procuron=uron/100;
        switch(tipuron){
            case Util.FIZURON:
                if(fizarmor<80)ur=uron-fizarmor*procuron;
                else ur=uron-80*procuron;
                break;
            case Util.ELLURON:
                if(ellarmor<80)ur=uron-ellarmor*procuron;
                else ur=uron-80*procuron;
                break;
            case Util.PULLURON:
                if(pullarmor<80)ur=uron-pullarmor*procuron;
                else ur=uron-80*procuron;
                break;
            default:
                ur=uron;
                break;
        }
        if(name.equals("korvin"))ur=0;
        return ur;
    }
    @Override
    public void ataked(long idatak, float kolvolife, float timeperiod, float fulltime, int tipuron, boolean notendtime,boolean send,boolean fasturon) {
        if(send) sendMsg("3/1/"+id+"/"+idatak+"/"+uron(tipuron,kolvolife)+"/"+timeperiod+"/"+fulltime+"/"+tipuron+"/"+notendtime+"/"+fasturon,true);
        pmLife.timeMinusLife(idatak,uron(tipuron,kolvolife),timeperiod,fulltime,tipuron,notendtime,fasturon);
        inventar.canuse=false;
        inventar.timecanuse=0;
    }
    @Override
    public void ataked(long idatak, float kolvolife,int tipuron,boolean send) {
        if(send) sendMsg("3/1/"+id+"/"+idatak+"/"+uron(tipuron,kolvolife),true);
        pmLife.minusLife(uron(tipuron,kolvolife));
        inventar.canuse=false;
        inventar.timecanuse=0;
    }
    // этот метод отпрвляет определенные даннные(я иду вверх или я атакую вот такой то объект и тд) всем игрокам(клиентам) которые находятся в зоне видимости данного игрока
    public void sendMsg(String s,boolean sebe) {
        if(sebe)NettyServerHandler.sendMsgClient(s,idchanel);
        for (MapObject mo:mapobjects.values()) {
            if (mo.tip == 1&&mo!=this)NettyServerHandler.sendMsgClient(s, mo.idchanel);
        }

    }
}
