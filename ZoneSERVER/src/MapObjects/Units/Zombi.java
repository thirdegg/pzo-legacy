package MapObjects.Units;

import MapObjects.MapObject;
import Gm.SpatialHashGrid;
import MapObjects.RandomItemMap;
import Modules.Life;
import Server.NettyServerHandler;
import Utils.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 777 on 12.12.2016.
 */
public class Zombi extends MapObject {
    private final int LEFT = 5, RIGHT = 3, UP = 2, DOWN = 4, STOP = 1, UR = 6, RD = 7, DL = 8, LU = 9;
    public final int DVIG=0,DEAD=1,ATAKA=2,PRESLEDOVANIE=5,DVIGCELL=6;
    int napravlenie=1,sensetivdetect,oldnapravlenie,skorostatak = 15;

    public SpatialHashGrid grid;
    public	float speed,uron,oldx,oldy;
    public Life pmLife;
    public int dvigcellx, dvigcelly;
    private MapObject atakobject;
    Rectang activhitbox;
    int state,tipemov;
    long nextstep;
    boolean ataka;
    long ldt;
    int tdead = 20000;
    List<Player> players=new ArrayList<>();
    HashMap<Long,MapObject> mapobjects= new HashMap<>();
    List<AddMOparameters>addmapobjects;
    public Zombi(float x, float y, SpatialHashGrid grid,HashMap<Long,MapObject> mapobjects,List<AddMOparameters>addmapobjects){
        tip = Util.ZOMBI;
        this.x = x;
        this.y = y;
        plasrecx = 17;
        plasrecy = 8;
        rectang = new Rectang(x + plasrecx, y + plasrecy, 11, 10);
        centx = rectang.x + rectang.width / 2;
        centy = rectang.y + rectang.height / 2;
        id = generatorId.incrementAndGet();
        this.grid=grid;
        speed = 30;
        uron = 8;
        pmLife=new Life();
        pmLife.life=30;
        canbeattacked=true;
        tipemov=rand.getIntRnd(0,3);
      //  tipemov=2;
        this.mapobjects=mapobjects;
        this.addmapobjects=addmapobjects;
        unit=true;
    }
    public void run(float delta) {
        switch(state){
            case DVIG:
                if(tipemov!=0){  setMove();
                move(delta);}
                if (sensetivdetect > 30) {
                    poiscAtakUnit();
                    sensetivdetect = 0;
                }
                sensetivdetect++;
                if(tipemov!=0){
                if (inRec(grid)) {
                    x = oldx;
                    y = oldy;
                 if(napravlenie==LEFT)napravlenie=RIGHT;
                    if(napravlenie==RIGHT)napravlenie=LEFT;
                    if(napravlenie==DOWN)napravlenie=UP;
                    if(napravlenie==UP)napravlenie=DOWN;
                } else {
                    oldx = x;
                    oldy = y;
                }
                }
                break;

            case PRESLEDOVANIE:
                if (atakobject!=null&&Overlap.pointPoint(x, y, atakobject.x, atakobject.y, 200) && atakobject.canbeattacked) {
                if (sensetivdetect > 25) {
                    detectNapravlenie(atakobject.x, atakobject.y);
                    sensetivdetect = 0;
                }
                sensetivdetect++;
                move(delta);
                if (Overlap.pointPoint(x, y, atakobject.x, atakobject.y, 10)) {
                    state = ATAKA;
                    napravlenie = oldnapravlenie;
                    ataka=true;
                    atakobject.ataked(id,uron,skorostatak,0,Util.FIZURON,true,true,true);
                    sendPlayersMsg("2/2/" + id + "/" + atakobject.id + "/" + (int) x + "/" + (int) y);
                }
                if (inRec(grid)) {
                    x = oldx;
                    y = oldy;
                    if(activhitbox!=null)poiscPuti(grid);
                } else {
                    oldx = x;
                    oldy = y;
                }}else{
                    atakobject = null;
                    state = DVIG;
                    if(tipemov==0)napravlenie=STOP;
                    if(tipemov==1)napravlenie=RIGHT;
                    if(tipemov==2)napravlenie=DOWN;
                }
                break;

            case ATAKA:
                Ataka();
                break;

            case DVIGCELL:
                move(delta);
                if (sensetivdetect > 25) {
                    detectNapravlenie(dvigcellx, dvigcelly);
                    sensetivdetect = 0;
                }
                sensetivdetect++;
                if (inRec(grid)) {
                    x = oldx;
                    y = oldy;
                    poiscPuti(grid);
                } else {
                    oldx = x;
                    oldy = y;
                }
                if (x - dvigcellx < 10 && x - dvigcellx > -10 && y - dvigcelly < 10 && y - dvigcelly > -10) {state = DVIG;setMove();}
                break;

            case DEAD:
                if (System.currentTimeMillis() > ldt) remov = true;
                break;
        }
        pmLife.run(delta);
        if (state != DEAD && pmLife.life <= 0) {
            state = DEAD;
            pmLife.stopAll();
            Player pla=null;
            for(Player pl:players){
                if(pl.id==pmLife.killid){pla=pl;break;}
            }
            if(pla!=null)pla.signal(3,id);
            canbeattacked=false;
            if(ataka){ataka=false;atakobject.signal(1,id);atakobject=null;ataka=false;}
            sendPlayersMsg("2/3/" + id);
            ldt = System.currentTimeMillis() + tdead;

            int randitem=rand.getIntRnd(1,11);
            if(randitem>5){
                RandomItemMap tim=new RandomItemMap(centx,centy,Util.createItem(42),120000);
                addmapobjects.add(new AddMOparameters(tim,false,false,0));
            }
        }
        if (oldnapravlenie != napravlenie&&state!=DEAD) {
            oldnapravlenie = napravlenie;
            sendPlayersMsg("2/1/" + id + "/" + (int) x + "/" + (int) y + "/" + napravlenie + "/" + (int) pmLife.life);
        }
    }
    void Ataka() {
        if (atakobject != null && atakobject.canbeattacked) {
            if (!Overlap.pointPoint(centx, centy, atakobject.centx, atakobject.centy, 20)) {
                oldnapravlenie = 0;
                state = PRESLEDOVANIE;
                ataka=false;
                atakobject.signal(1,id);
            }
        } else {
            oldnapravlenie = 0;
            state = DVIG;
            ataka=false;
            if(tipemov==0)napravlenie=STOP;
            if(tipemov==1)napravlenie=RIGHT;
            if(tipemov==2)napravlenie=DOWN;
            if(atakobject!=null){atakobject.signal(1,id);atakobject = null;}
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
    void setMove() {
        if (System.currentTimeMillis() > nextstep) {
            nextstep = System.currentTimeMillis() + 3000;
            switch (tipemov) {
                case 1:
                    if(napravlenie==LEFT){napravlenie=RIGHT;
                    return;}
                    if(napravlenie==RIGHT){napravlenie=LEFT;
                    return;}
                    if(napravlenie==STOP){napravlenie=RIGHT;
                    return;}
                    if(rand.getIntRnd(0,10)<5)napravlenie=RIGHT;
                    else napravlenie=LEFT;
                    break;
                case 2:
                    if(napravlenie==UP){napravlenie=DOWN;
                    return;}
                    if(napravlenie==DOWN){napravlenie=UP;
                    return;}
                    if(napravlenie==STOP){napravlenie=DOWN;
                    return;}
                    if(rand.getIntRnd(0,10)<5)napravlenie=DOWN;
                    else napravlenie=UP;
                    break;
                case 0:
                    napravlenie=STOP;
                    break;
            }
        }
    }
    void poiscAtakUnit() {
        for (Player player : players) {
            if (player.canbeattacked) {
                if (Overlap.pointPoint(x, y, player.x, player.y, 150)) {
                    atakobject = player;
                    state = PRESLEDOVANIE;
                    break;
                }
            }
        }
            for (MapObject kaban : mapobjects.values()) {
                if (kaban.tip==Util.KABAN&&kaban.canbeattacked) {
                    if (Overlap.pointPoint(x, y, kaban.x, kaban.y, 150)) {
                        atakobject = kaban;
                        state = PRESLEDOVANIE;
                        break;
                    }
                }
            }
    }
    void poiscPuti(SpatialHashGrid grid) {
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
                if (inRec(grid)) {
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
                if (inRec(grid)) {
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
                if (inRec(grid)) {
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
                if (inRec(grid)) {
                    napravlenie = UP;
                } else {
                    napravlenie = LEFT;
                }
                x += 2;
                break;
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
    }
    //этот объект добавляют к игроку в его списки и добавляют в строку состояния доп параметры
    public String addedAndDop(Player pl,String str){
        pl.mapobjects.put(id,this);
        players.add(pl);
        str+="/"+(int)pmLife.life;
        String mlife=null;
        //allMinusPlusLife
        mlife=pmLife.getAllTmpl(id);
        if(mlife!=null)str+="\0"+mlife;
        return str;
    }
    public String getStateForClient() {
        switch (state) {
            case PRESLEDOVANIE:
            case DVIGCELL:
            case DVIG:
                return "2/1/" + id + "/" + (int) x + "/" + (int) y + "/" + napravlenie + "/" + (int) pmLife.life;
case ATAKA:
    if(atakobject!=null)
    return"2/2/" + id + "/" + atakobject.id + "/" + (int) x + "/" + (int) y;
    else{state=DVIG;return "2/1/" + id + "/" + (int) x + "/" + (int) y + "/" + napravlenie + "/" + (int) pmLife.life;}
            case DEAD:
              int  timedie = (int) (ldt - System.currentTimeMillis());
                return "2/3/" + id + "/" + timedie;
        }
        return null;
    }
    public void setState(int newstate) {
        switch (newstate) {
            case DVIGCELL:
                state = DVIGCELL;
                canbeattacked=true;
                break;
        }
    }
    //1 stop ataked 2 rem playuer 3 you kill me
    @Override
    public void signal(int tip,Object object){
        switch(tip){
            case 1:
                long idv=(Long)object;
                sendPlayersMsg("3/2/"+id+"/"+idv);
                pmLife.stopPlusMinusLife(idv);
                break;
            case 2:
                long idv2=(Long)object;
                for(Player pl:players){
                    if(pl.id==idv2){players.remove(pl);
                    break;}
                }
                if(atakobject!=null&&atakobject.id==idv2)atakobject=null;
                break;
        }
    }
    @Override
    public void ataked(long idcel, float kolvolife, float timeperiod, float fulltime, int tipuron, boolean notendtime,boolean send,boolean fasturon) {
        if(send) sendPlayersMsg("3/1/"+id+"/"+idcel+"/"+kolvolife+"/"+timeperiod+"/"+fulltime+"/"+tipuron+"/"+notendtime+"/"+fasturon);
        pmLife.timeMinusLife(idcel,kolvolife,timeperiod,fulltime,tipuron,notendtime,fasturon);
    }
    @Override
    public void ataked(long idatak, float kolvolife,int tipuron,boolean send) {
        if(send) sendPlayersMsg("3/1/"+id+"/"+idatak+"/"+kolvolife);
        pmLife.minusLife(kolvolife);
    }
    void sendPlayersMsg(String msg) {
        for (Player pl : players) {
            NettyServerHandler.sendMsgClient(msg, pl.idchanel);
        }
    }

    boolean inRec(SpatialHashGrid grid) {
        if (x > 1575 || x < 0 || y > 1565 || y < 0) return true;
        List<Rectang> colliders = grid.getPotentialColliders(rectang);
        for (Rectang re : colliders) {
            if (Overlap.overlapRectang(re, rectang)) {
                activhitbox = re;
                return true;
            }
        }
        return false;
    }
}
