package MapObjects.Units;

import Gm.SpatialHashGrid;
import InventItem.*;
import InventItem.artefacts.ArtMgla;
import InventItem.core.Item;
import InventItem.device.Guitar;
import InventItem.device.Radiomiter;
import MapObjects.MapObject;
import Server.NettyServerHandler;
import Utils.Overlap;
import Utils.Rectang;
import Utils.Util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Npc extends MapObject {
    private final int LEFT = 5, RIGHT = 3, UP = 2, DOWN = 4, UR = 6, RD = 7, DL = 8, LU = 9;
    public int dvigcellx, dvigcelly,dvigdomx,dvigdomy;
    int napravlenie = 1, oldnapravlenie, sensetivdetect, skorostatak = 2,nsnapravlenie=4;
    boolean ataka;
    float oldx, oldy;
    Rectang activhitbox;
    public SpatialHashGrid grid;
    public final int ATAKA=2,PRESLEDOVANIE=5,DVIGCELL=6,SITTING=7,VDOME=8,GOHOME=9;
    public	float speed,uron;
    protected int state;
    private MapObject atakobject;
    List<Player> players=new ArrayList<>();
    HashMap<Long, MapObject> mapobjects;
    int tipnpc;
    String name;
    int sitnap;
    private HashMap<Integer,Item>spisokTovarov=new HashMap<>();
    public Npc(float x, float y,SpatialHashGrid grid,HashMap<Long, MapObject> mapobjects,int tipnpc,String name,int nap,List<Rectang> allmaprec) {
        tip = Util.NPC;
        this.x = x;
        this.y = y;
        this.name=name;
        this.tipnpc=tipnpc;
        napravlenie=nap;
        sitnap=nap;
        nsnapravlenie=nap;
        dvigcellx=(int)x;
        dvigcelly=(int)y;
        plasrecx = 16;
        plasrecy = 11;
        rectang = new Rectang(x + plasrecx, y + plasrecy, 11, 10);
        centx = rectang.x + rectang.width / 2;
        centy = rectang.y + rectang.height / 2;
        id = generatorId.incrementAndGet();
        this.grid=grid;
        speed = 20;
        uron = 1.5f;
        this.mapobjects=mapobjects;
        unit=true;
        state=SITTING;
        screachNearestDom(allmaprec);
        spisokTovarov.put(0,new Kurtka2());
        spisokTovarov.put(1,new Kurtka1());
        spisokTovarov.put(2,new Spichki());
        spisokTovarov.put(4,new Flaga());
        spisokTovarov.put(5,new Baton());
        spisokTovarov.put(10,new IneRad());
        spisokTovarov.put(11,new Aptechka());
        spisokTovarov.put(12,new ArtMgla());
        spisokTovarov.put(13,new Pistolet1());
        spisokTovarov.put(14,new Patrony());
        spisokTovarov.put(16,new Kurtka3());
        spisokTovarov.put(17,new Kurtka4());
        spisokTovarov.put(18,new Pistolet2());
        spisokTovarov.put(19,new Pistolet3());
        spisokTovarov.put(20,new Pistolet4());
        spisokTovarov.put(25,new Kurtka5());
        spisokTovarov.put(26,new Kurtka6());
        spisokTovarov.put(29,new Avtomat1());
        spisokTovarov.put(30,new Avtomat2());
        spisokTovarov.put(31,new Avtomat3());
        spisokTovarov.put(97,new Radiomiter());
        spisokTovarov.put(103,new Guitar());

    }

    public void run(float delta) {
        switch (state) {
            case PRESLEDOVANIE:
                if (atakobject!=null&& atakobject.canbeattacked&&atakobject.centx>400&&atakobject.centy>838&&atakobject.centx<1036&&atakobject.centy<1249){
                    if (sensetivdetect > 25) {
                        detectNapravlenie(atakobject.x, atakobject.y);
                        sensetivdetect = 0;
                    }
                    sensetivdetect++;
                    move(delta);
                    if (Overlap.pointPoint(x, y, atakobject.x, atakobject.y, 150)) {
                        state = ATAKA;
                        napravlenie = oldnapravlenie;
                        ataka=true;
                        atakobject.ataked(id,uron,skorostatak,0,Util.FIZURON,true,true,false);
                        atakobject.ataknpc=true;
                        sendPlayersMsg("2/2/" + id + "/" + atakobject.id + "/" + (int) x + "/" + (int) y);
                    }
                    if (inRec()) {
                        x = oldx;
                        y = oldy;
                        if(activhitbox!=null)poiscPuti();
                    } else {
                        oldx = x;
                        oldy = y;
                    }}else{
                    atakobject = null;
                    state = DVIGCELL;
                }
                break;

            case ATAKA:
                Ataka(delta);
                break;
            case DVIGCELL:
                move(delta);
                if (x - dvigcellx < 5 && x - dvigcellx > -5 && y - dvigcelly < 5 && y - dvigcelly > -5) {
                    state = SITTING;nsnapravlenie=sitnap;
                sendPlayersMsg("2/6/" + id + "/" + (int) x + "/" + (int) y + "/" + nsnapravlenie+"/"+0);}
                if (sensetivdetect > 25) {
                    detectNapravlenie(dvigcellx, dvigcelly);
                    poiscAtakUnit();
                    sensetivdetect = 0;
                }
                sensetivdetect++;
                if (inRec()) {
                    x = oldx;
                    y = oldy;
                    poiscPuti();
                } else {
                    oldx = x;
                    oldy = y;
                }
                break;
            case SITTING:
                if (sensetivdetect > 25) {
                    poiscAtakUnit();
                    sensetivdetect = 0;
                }
                sensetivdetect++;
                break;
            case GOHOME:
                move(delta);
                if (x - dvigdomx < 7 && x - dvigdomx > -7 && y - dvigdomy < 7 && y - dvigdomy > -7) {
                    state = VDOME;
                    napravlenie=1;
                    oldnapravlenie=napravlenie;
                    sendPlayersMsg("23/1/" + id);return;}
                if (sensetivdetect > 25) {
                    detectNapravlenie(dvigdomx, dvigdomy);
                    sensetivdetect = 0;
                }
                sensetivdetect++;
                if (inRec()) {
                    x = oldx;
                    y = oldy;
                    poiscPuti();
                } else {
                    oldx = x;
                    oldy = y;
                }
             break;

        }
        if (oldnapravlenie != napravlenie) {
            oldnapravlenie = napravlenie;
            sendPlayersMsg("2/1/" + id + "/" + (int) x + "/" + (int) y + "/" + napravlenie);
        }
    }

    private void move(float delta) {
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
        if(napravlenie>1)nsnapravlenie=napravlenie;
    }

    void poiscPuti() {
        switch (napravlenie) {
            case DOWN:
            case UP:
                if (x < activhitbox.x + activhitbox.centw) {
                    napravlenie = LEFT;
                } else {
                    napravlenie = RIGHT;
                }
                break;
            case LEFT:
            case RIGHT:
                if (y < activhitbox.y + activhitbox.centh) {
                    napravlenie = DOWN;
                } else {
                    napravlenie = UP;
                }
                break;
            case UR:
                y += 2;
                rectang.y = y + plasrecy;
                rectang.x = x + plasrecx;
                if (inRec()) {
                    napravlenie = RIGHT;
                } else {
                    napravlenie = UP;
                }
                y -= 2;
                break;
            case RD:
                x += 2;
                rectang.y = y + plasrecy;
                rectang.x = x + plasrecx;
                if (inRec()) {
                    napravlenie = DOWN;
                } else {
                    napravlenie = RIGHT;
                }
                x -= 2;
                break;
            case DL:
                y -= 2;
                rectang.y = y + plasrecy;
                rectang.x = x + plasrecx;
                if (inRec()) {
                    napravlenie = LEFT;
                } else {
                    napravlenie = DOWN;
                }
                y += 2;
                break;
            case LU:
                x -= 2;
                rectang.y = y + plasrecy;
                rectang.x = x + plasrecx;
                if (inRec()) {
                    napravlenie = UP;
                } else {
                    napravlenie = LEFT;
                }
                x += 2;
                break;
        }
    }
    @Override
    public void signal(int tip,Object object){
        switch(tip){
            case 2:
                long idv2=(Long)object;
                for(Player pl:players){
                    if(pl.id==idv2){players.remove(pl);break;}
                }
                break;
            case 6:
            setState(GOHOME);
                break;
                // end vibros
            case 7:
                sendPlayersMsg("23/0/" + id);
                state=DVIGCELL;
                oldnapravlenie=0;
                break;
        }
    }
    void Ataka(float delta) {
        if(atakobject!=null&&atakobject.canbeattacked){
        // если объект не вышел за пределы радиуса стрельбы оружия
        if (!Overlap.pointPoint(centx, centy, atakobject.centx, atakobject.centy, 200)) {
            // если объект вышел из радиуса стрельбы
            ataka = false;
            state = DVIGCELL;
            // посылаем объекту сигнал который говорит ему - ну я в тебя уже не стреляю
            atakobject.signal(1,id);
            atakobject.ataknpc=false;
            oldnapravlenie=0;
            // посылаем всем игрокам (клиентам) которые находятся в поле зреня сообщени о том что атака завершена
            sendPlayersMsg("2/10/" + id);
            poiscAtakUnit();
        }
        }else{ // если объект вышел из радиуса стрельбы
            ataka = false;
            state = DVIGCELL;
            oldnapravlenie=0;
            sendPlayersMsg("2/10/" + id);
            poiscAtakUnit();}
    }

    void poiscAtakUnit() {
        for (MapObject mo : mapobjects.values()) {
            if (mo.unit&&mo.canbeattacked&&!mo.ataknpc) {
                if(mo.tip==Util.KABAN||mo.tip==Util.ZOMBI||mo.tip==Util.KRUSA)
                if (mo.centx>400&&mo.centy>838&&mo.centx<1036&&mo.centy<1249) {
                    atakobject = mo;
                    atakobject.ataknpc=true;
                    state = PRESLEDOVANIE;
                    //если рядом
                    if (Overlap.pointPoint(x, y, atakobject.x, atakobject.y, 150)) {
                        state = ATAKA;
                        napravlenie = oldnapravlenie;
                        ataka=true;
                        atakobject.ataked(id,uron,skorostatak,0,Util.FIZURON,true,true,false);
                        atakobject.ataknpc=true;
                        sendPlayersMsg("2/2/" + id + "/" + atakobject.id + "/" + (int) x + "/" + (int) y);
                    }
                    break;
                }
            }
        }
    }

    public void detectNapravlenie(double cx, double cy) {
        double A = Math.atan2((double) y - cy, (double) x - cx) / Math.PI * 180;
        if (A < 0) A += 360;
        if (A > 67.5 && A < 112.5) napravlenie = DOWN;
        if (A < 22.5 || A > 337.5) napravlenie = LEFT;
        if (A > 247.5 && A < 292.5) napravlenie = UP;
        if (A > 157.5 && A < 202.5) napravlenie = RIGHT;
        if (A >= 202.5 && A <= 247.5) napravlenie = UR;
        if (A >= 112.5 && A <= 157.5) napravlenie = RD;
        if (A >= 22.5 && A <= 67.5) napravlenie = DL;
        if (A >= 292.5 && A <= 337.5) napravlenie = LU;
    }
    @Override
    public String getStateForClient() {
        switch (state) {
            case PRESLEDOVANIE:case DVIGCELL:
                return "2/1/" + id + "/" + (int) x + "/" + (int) y + "/" + napravlenie;
            case ATAKA:
                if(atakobject!=null)return"2/2/"+id+"/"+atakobject.id+ "/" + (int) x + "/" + (int) y;
                else {state=DVIGCELL;return "2/1/" + id + "/" + (int) x + "/" + (int) y + "/" + napravlenie;}
            case VDOME:
                return "23/1/" + id;
            case SITTING:
                return "2/6/" + id + "/" + (int) x + "/" + (int) y + "/" + nsnapravlenie;
        }
        return null;
    }
    public void setState(int newstate) {
        switch(state){
            case ATAKA:
                ataka = false;
               if(atakobject!=null){atakobject.signal(1,id);atakobject.ataknpc=false;}
                oldnapravlenie=0;
                sendPlayersMsg("2/10/" + id);
                break;
        }
        switch (newstate) {
            case DVIGCELL:
                state = DVIGCELL;
                break;
            case GOHOME:
                state=GOHOME;
                break;
        }
    }
    //этот объект добавляют к игроку в его списки и добавляют в строку состояния доп параметры
    public String addedAndDop(Player pl,String str){
        pl.mapobjects.put(id,this);
        players.add(pl);
        str+="/"+tipnpc+"/"+name;
        return str;
    }

    void sendPlayersMsg(String msg) {
        for (Player pl : players) {
            NettyServerHandler.sendMsgClient(msg, pl.idchanel);
        }
    }
    boolean inRec() {
        if (x > 1550 || x < 10 || y > 1550 || y < 10) return true;
        List<Rectang> colliders = grid.getPotentialColliders(rectang);
        for (Rectang re : colliders) {
            if (Overlap.overlapRectang(re, rectang)) {
                activhitbox = re;
                return true;
            }
        }
        return false;
    }


    public void bay(int tovar, Player player) {
        Item it=spisokTovarov.get(tovar);
        if(it!=null){
        if(player.inventar.many>=it.cena){
            player.inventar.many-=it.cena;
            player.inventar.addItem(spisokTovarov.get(tovar));
            spisokTovarov.put(tovar,Util.createItem(tovar,true,0));}
        }
    }

    public void sell(int idtovar, Player player) {
        Item it = null;
        it = player.inventar.item.remove(idtovar);
        if (it != null) {
            //исправить цену патронов
            if(it.tipitem==14){
                player.inventar.many +=it.dopintcolvo;
            }
            else{
            player.inventar.many += it.cena/2;}
            NettyServerHandler.sendMsgClient("19/m/"+player.inventar.many+"\0"+"20/"+it.id,player.idchanel);
        }
    }
    private void screachNearestDom(List<Rectang> allmaprec){
float distance=100000;
for(Rectang rec:allmaprec){
    if(rec.dom){
    float distX = x - rec.x;
    float distY = y - rec.y;
    float tmp = distX * distX + distY * distY;
    if(tmp<distance){distance=tmp;dvigdomx=(int)(rec.x+rec.width/2-25);dvigdomy=(int)rec.y-20;}}
}
    }
}

