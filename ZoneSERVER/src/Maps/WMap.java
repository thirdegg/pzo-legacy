package Maps;

import MapObjects.MapObject;
import Base.MessageSystem;
import Gm.SpatialHashGrid;
import Gm.WorldRec;
import MapObjects.RandomItemMap;
import MapObjects.Units.BosKaban;
import Server.NettyServerHandler;
import MapObjects.Units.Player;
import Utils.*;

import java.util.*;

/* супер класс для каждой игровой локации*/
public class WMap {
    // система проверки потенциальных столкновений для квадратов(как он устроен можеш не разбераться)
    public SpatialHashGrid grid;
    // статичные квадраты сквозь которые нельзя пройти (на клиенте это дома деревья заборы и тд)
    WorldRec worldrec;
    // список игроков находящихся на карте
    public List<Player> players;
    // список всех объектов находящихся на карте(игроки аномалии артефакты монстры и тд)
    // Long это уникальный id объекта
    public HashMap<Long, MapObject> mapobjects;
    //список тех объектов которые должны появляться на карте через определенный промежуток времени
    public List<ObjectParametr> createobjects;
    // список для добавляния объектов в основной мапобджектс (обходим канкарент еррор)
    public List<AddMOparameters>addmapobjects;
    public long timespawnmob,tsm,tsi;
    // номер карты
    int idmap;

   public HashMap<Long,Player>plmapchat=new HashMap<>();
   public String lostmapstring[]=new String[]{"22/server/тест","22/server/тест","22/server/тест","22/server/тест","22/server/тест"};
    public WMap(){
        players = new ArrayList<>();
        mapobjects = new HashMap<>();
        createobjects = new ArrayList<>();
        addmapobjects=new ArrayList<>();
        grid = new SpatialHashGrid(1750, 1750, 250);
        timespawnmob=180000;
        tsm = System.currentTimeMillis() + timespawnmob;
        tsi = System.currentTimeMillis() + rand.getIntRnd(5000,5000);
        //artefakts all map
        createobjects.add(new ObjectParametr(Util.ARTMGLA,Util.PPARTEFAKT,2,0,0,0,null));
    }
// метод удаления игрока с карты
    public void removePlayer(Player player){
        // отправляем всем клиентам (которые находятся в радиусе видимости игрока) новую позицию -1000 -1000
        // при таких координатах на клиенте игрок удалится
        player.sendMsg("2/1/" + player.id + "/-1000/-1000/" + player.napravlenie + "/" + (int) player.pmLife.life,false);
        // посылаем всем объектам сигнал об удалении себя из их списков
        for(MapObject mo:player.mapobjects.values()){
            mo.signal(2,player.id);
        }
        player.pmLife.stopAllMobMinusLife();
        player.mapobjects.clear();
        players.remove(player);
        mapobjects.remove(player.id);
    }
// метод добавления игрока на карту
   public void addPlayer(Player player){
        player.setPosition(player.x,player.y);
        player.oldx=player.x;player.oldy=player.y;
        player.napravlenie=1;
        player.grid=grid;
        players.add(player);
        player.addmapobjects=addmapobjects;
        player.idmap=idmap;
        addMapHitObject(player,player.urdlnapravlenie,true);
        if (player.timedie > 0) {
            player.setState(player.DEAD, null);
            player.ldt = System.currentTimeMillis() + player.timedie;
            player.timedie = 0;
        }
    }
    /* у каждого игрока есть список объектов которые находятся в зоне его видимости
    данный метод заполняет этот список */
    void addObjectsToPlayer() {
        for (Player pl : players) {
            for (MapObject object : mapobjects.values()) {
                // если у игрока в списке нет такого объекта и данный объек находитя в зоне видимости игрока
                if (!pl.mapobjects.containsKey(object.id)&&Overlap.pointPoint(pl.centx, pl.centy, object.centx, object.centy, object.raddclient)) {
                    if (pl!=object) {
                        // если объект видим для игрока игроку отправляются данные
                        // об этом объекте (позиция, некоторые характеристики, состояние в котом он находится)
                        if(object.clientvisible){
                        //базовое состояние объекта
                        String msg = "1/" + object.tip + "/" + object.id + "/" + (int) object.x + "/" + (int) object.y;
                        //добавляем объект в спиcки к игроку и дополняем базовое состояние
                        msg = object.addedAndDop(pl, msg);
                        //состояие объекта
                        String str = object.getStateForClient();
                        if (str != null) msg += "\0" + str;
                        // отправляем данные клиенту(игроку)
                        NettyServerHandler.sendMsgClient(msg, pl.idchanel);}
                        // если объект не виден для игрока то игроку ничего не отправляется
                        // но на сервере к списку игроков у объекта добавляется данный игрок
                        else object.addedAndDop(pl,"");
                    }
                }
            }
        }
    }
 // метод проверяет не сталкивается объект с каким нибудь прямоугольником если сталкивается передвигает объкект чуть дальше
    // так же добавляет объект в общий списов всех объектов карты mapobjects
    public void addMapHitObject(MapObject mapo,int napravlenie,boolean chekYourType){
        boolean out=true;
        if(mapo.rectang!=null){

            if(inRec(mapo.rectang)){
                switch(napravlenie){
                    case 2:
                        mapo.setPosition(mapo.x,mapo.y+mapo.rectang.height+5);out=false;
                        break;
                    case 3:
                        mapo.setPosition(mapo.x+mapo.rectang.width+5,mapo.y);out=false;
                        break;
                    case 4:
                        mapo.setPosition(mapo.x,mapo.y-mapo.rectang.height+5);out=false;
                        break;
                    case 5:
                        mapo.setPosition(mapo.x-mapo.rectang.width+5,mapo.y);out=false;
                        break;
                }
            }
            //проверка на столкновение со своим типом
            if(chekYourType&&out){
            for(MapObject mo:mapobjects.values()){
                if(mapo.tip==mo.tip&&Overlap.overlapRectang(mo.rectang,mapo.rectang)&&mapo!=mo){
                    switch(napravlenie){
                        case 2:
                            mapo.setPosition(mapo.x,mapo.y+mapo.rectang.height+5);out=false;
                            break;
                        case 3:
                            mapo.setPosition(mapo.x+mapo.rectang.width+5,mapo.y);out=false;
                            break;
                        case 4:
                            mapo.setPosition(mapo.x,mapo.y-mapo.rectang.height+5);out=false;
                            break;
                        case 5:
                            mapo.setPosition(mapo.x-mapo.rectang.width+5,mapo.y);out=false;
                            break;
                    }
                    break;
                }
            }}
        }
        else{
            for(MapObject mo:mapobjects.values()){
                if(mo.tip==mapo.tip&&Overlap.pointPoint(mo.centx,mo.centy,mapo.centx,mapo.centy,mapo.radius*2)&&mapo!=mo){
                    switch(napravlenie) {
                        case 2:
                        mapo.setPosition(mapo.x, mapo.y + mapo.radius * 2);out = false;
                            break;
                        case 3:
                            mapo.setPosition(mapo.x + mapo.radius * 2, mapo.y);out = false;
                            break;
                        case 4:
                            mapo.setPosition(mapo.x, mapo.y - mapo.radius * 2);out = false;
                            break;
                        case 5:
                            mapo.setPosition(mapo.x - mapo.radius * 2, mapo.y);out = false;
                            break;
                    }
                        break;
                }
            }
        }
        // рекурсия метод вызыват сам себя если объект сталкивается с каким нибудь прямоугольником
        if(!out)addMapHitObject(mapo,napravlenie,chekYourType);
        else mapobjects.put(mapo.id,mapo);
    }
// добавляем новые объекты в список объектов
    void addMapObjectOfamo(){
        if(!addmapobjects.isEmpty()){
    for(AddMOparameters amop:addmapobjects){
    if(amop.clashChek)addMapHitObject(amop.mo,amop.napravlenie,amop.clashChekWithYoutType);
    else mapobjects.put(amop.mo.id,amop.mo);
    }
    addmapobjects.clear();}
    }
    void spawnObjects() {
        // спавн мобов
        if (System.currentTimeMillis() > tsm) {
  for(ObjectParametr op:createobjects){
      // если подтип объекта моб
      if(op.podtip==Util.PPMOB){
          // если на карте находится меньше чем одна четвертая данных мобов(кабан и фригер) от изначального колличества
         if(Util.podschet(mapobjects.values(),op.tip)<op.kolvo/4){
             // создаем новых мобов
          LoadObjects.createObjectKolvo(this,op.tip,op.kolvo);
         }
      }
  }
            tsm = System.currentTimeMillis() + timespawnmob;
        }
//спавн рандом итемов (проверка каждые 5-10 сек)
        if (System.currentTimeMillis() > tsi) {
            for(ObjectParametr op:createobjects){
                // если подтип объекта randitem
                if(op.podtip==Util.PPRANDOMITEM) {
                if(System.currentTimeMillis() >op.timespaawn){
                    boolean create=true;
                    for(MapObject mo:mapobjects.values()){
                        //если на карте все еще лежит такой объект
                        if(mo.tip==Util.RANDOMITEM){
                            if(mo.x==op.cx&&mo.y==op.cy){create=false;break;}
                        }
                    }
                    if(create){
                        RandomItemMap rim=new RandomItemMap(op.cx,op.cy,(String)op.dopobject);
                        mapobjects.put(rim.id,rim);
                    }
                    op.timespaawn=System.currentTimeMillis()+op.konstimespawn;
                }
                }
                //boss peredelat
                // konsttime spaw ставим время появления после события в самом объекте переделать рандом итемы по тому же принципу
                //добавлена переменная create
                if(op.podtip==Util.PPBOS) {
                    if(op.create&&System.currentTimeMillis() >op.timespaawn){
                            BosKaban bos=new BosKaban(op.cx,op.cy,grid,addmapobjects,op);
                            mapobjects.put(bos.id,bos);
                            op.create=false;
                    }
                }

            }

            tsi = System.currentTimeMillis() + rand.getIntRnd(5000,5000);
        }
    }
    // некоторые объекты внутри себя при определенных условиях (смерь) могут выставить remov=true
    // этот метод находит такие объекты и удаляет их с карты
    void dell() {
        Iterator<MapObject> r=mapobjects.values().iterator();
        while (r.hasNext()) {
            MapObject mo=r.next();
            if (mo.remov) {
                mo.centx = -1000;
                mo.centy = -1000;
                r.remove();
            }
        }
    }

// метод проверки на столкновение квадрата с другими квадратами
    public boolean inRec(Rectang rectang) {
        List<Rectang> colliders = grid.getPotentialColliders(rectang);
        for (Rectang re : colliders) {
            if (Overlap.overlapRectang(re, rectang)) {
                return true;
            }
        }
        return false;
    }

    public void sendMsg(String s) {
        for (Player p : players) {
            NettyServerHandler.sendMsgClient(s, p.idchanel);
        }
    }
// эти меторды должны переопределить подклассы

    //метод который позволяет послать какой нибудь сигнал в карту
    public  void signal(int tip){}
    //обновление логики
    public  void run(float delta, MessageSystem ms) {}
    //проверка на столкновение с квадратами локации
    public void hitObject(MapObject object){}
}
