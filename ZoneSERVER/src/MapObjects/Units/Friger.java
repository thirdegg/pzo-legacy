package MapObjects.Units;

import MapObjects.MapObject;
import Gm.SpatialHashGrid;
import Modules.Life;
import Server.NettyServerHandler;
import Utils.Overlap;
import Utils.Rectang;
import Utils.Util;
import Utils.rand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Friger extends MapObject {
    private final int LEFT = 5, RIGHT = 3, UP = 2, DOWN = 4, STOP = 1, UR = 6, RD = 7, DL = 8, LU = 9;
    int napravlenie = 1, oldnapravlenie, sensetivdetect;
    long nextrandstep;
    float oldx, oldy;
    Rectang activhitbox;
    long ldt;
    int tdead = 20000;
    int speedjump=120,normalspeed=30;
    float timejump;
    public SpatialHashGrid grid;
    public final int DVIG=0,DEAD=1,JUMP=6,PRESLEDOVANIE=2;
    public	float speed,uron,golod;
    public Life pmLife;
    protected int state;
    private MapObject atakobject;
    List<Player> players=new ArrayList<>();
    HashMap<Long, MapObject> mapobjects;
    public Friger(float x, float y, SpatialHashGrid grid, HashMap<Long, MapObject> mapobjects) {
        tip = Util.KRUSA;
        podtip=Util.PPMOB;
        this.x = x;
        this.y = y;
        plasrecx = 17;
        plasrecy = 12;
        rectang = new Rectang(x + plasrecx, y + plasrecy, 11, 10);
        centx = rectang.x + rectang.width / 2;
        centy = rectang.y + rectang.height / 2;
        id = generatorId.incrementAndGet();
        this.grid=grid;
        speed = normalspeed;
        uron = 7;
        pmLife=new Life();
        pmLife.life=50;
        canbeattacked=true;
        this.mapobjects=mapobjects;
        unit=true;
    }

    public void run(float delta) {
        switch (state) {
            case DVIG:
                randomSetMove();
                move(delta);
                if (sensetivdetect > 25) {
                    poiscAtakUnit();
                    sensetivdetect = 0;
                }
                sensetivdetect++;
                if (inRec(grid)) {
                    x = oldx;
                    y = oldy;
                    setZrandNapravlenie();
                } else {
                    oldx = x;
                    oldy = y;
                }
                break;
            case PRESLEDOVANIE:
                if (atakobject!=null&&Overlap.pointPoint(x, y, atakobject.x, atakobject.y, 300) && atakobject.canbeattacked) {
                    if (sensetivdetect > 25) {
                        detectNapravlenie(atakobject.x, atakobject.y);
                        sensetivdetect = 0;
                    }
                    sensetivdetect++;
                    move(delta);
                    if (Overlap.pointPoint(x, y, atakobject.x, atakobject.y, 100)) {
                        atakobject.ataked(id,uron,Util.FIZURON,true);
                        detectNapravlenie(atakobject.centx,atakobject.centy);
                        setJump(atakobject.id);
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
                }
                break;
            case JUMP:
                if(timejump<15){
                    timejump+=10*delta;
                    move(delta);
                    if (inRec(grid)) {
                        x = oldx;
                        y = oldy;
                    } else {
                        oldx = x;
                        oldy = y;
                    }
                }else{
                  state=PRESLEDOVANIE;
                    speed=normalspeed;
                }
                break;
            case DEAD:
                if (System.currentTimeMillis() > ldt) remov = true;
                break;
        }
        if (state != DEAD && pmLife.life <= 0) {
            state = DEAD;
            pmLife.stopAll();
            MapObject mo=mapobjects.get(pmLife.killid);
            if(mo!=null)mo.signal(3,id);
            canbeattacked=false;
            sendPlayersMsg("2/3/" + id);
            ldt = System.currentTimeMillis() + tdead;
        }
        pmLife.run(delta);
        if (oldnapravlenie != napravlenie&&state!=DEAD) {
            oldnapravlenie = napravlenie;
            sendPlayersMsg("2/1/" + id + "/" + (int) x + "/" + (int) y + "/" + napravlenie + "/" + (int) pmLife.life);
        }
    }
    private void setZrandNapravlenie(){
        int znapr=napravlenie-2;
        if(napravlenie==7)znapr=9;
        if(napravlenie==6)znapr=8;
        if(napravlenie==3)znapr=5;
        if(napravlenie==2)znapr=4;
        int rand3=rand.getIntRnd(0,3);
        if (rand3==0){napravlenie=znapr;return;}
        switch(znapr){
            case 2:if(rand3==1)napravlenie=9;if(rand3==2)napravlenie=6;break;
            case 3:if(rand3==1)napravlenie=6;if(rand3==2)napravlenie=7;break;
            case 4:if(rand3==1)napravlenie=7;if(rand3==2)napravlenie=8;break;
            case 5:if(rand3==1)napravlenie=8;if(rand3==2)napravlenie=9;break;
            case 6:if(rand3==1)napravlenie=2;if(rand3==2)napravlenie=3;break;
            case 7:if(rand3==1)napravlenie=3;if(rand3==2)napravlenie=4;break;
            case 8:if(rand3==1)napravlenie=4;if(rand3==2)napravlenie=5;break;
            case 9:if(rand3==1)napravlenie=5;if(rand3==2)napravlenie=2;break;
        }
    }
    void randomSetMove() {
        if (System.currentTimeMillis() > nextrandstep) {
            nextrandstep = System.currentTimeMillis() + rand.getIntRnd(3000, 10000);
            napravlenie = rand.getIntRnd(1, 9);
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
    void move(float delta) {
        switch (napravlenie) {
            case UP:
                y += speed * delta;
                break;
            case RIGHT:
                x += speed * delta;
                break;
            case DOWN:
                y -= speed * delta;
                break;
            case LEFT:
                x -= speed * delta;
                break;
            case UR:
                y += speed/2 * delta;
                x += speed/2 * delta;
                break;
            case RD:
                x += speed/2 * delta;
                y -= speed/2 * delta;
                break;
            case DL:
                y -= speed/2 * delta;
                x -= speed/2 * delta;
                break;
            case LU:
                x -= speed/2 * delta;
                y += speed/2 * delta;
                break;
        }
        rectang.y = y + plasrecy;
        rectang.x = x + plasrecx;
        centx = rectang.x + rectang.width / 2;
        centy = rectang.y + rectang.height / 2;
    }

    void poiscAtakUnit() {
        for (Player player : players) {
            if (player.canbeattacked) {
                if (Overlap.pointPoint(x, y, player.x, player.y, 150)) {
                    atakobject=player;
                    state=PRESLEDOVANIE;
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

    public String getStateForClient() {
        switch (state) {
            case PRESLEDOVANIE:
            case DVIG:
                return "2/1/" + id + "/" + (int) x + "/" + (int) y + "/" + napravlenie + "/" + (int) pmLife.life;
            case JUMP:
                return "2/7/" + id + "/" + (int) x + "/" + (int) y + "/" + napravlenie + "/" + (int) pmLife.life+"/"+timejump;
            case DEAD:
                int timedie = (int) (ldt - System.currentTimeMillis());
                return "2/3/" + id + "/" + timedie;
        }
        return null;
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
    public void setJump(long atakid){
        switch (napravlenie) {
            case UP:
                napravlenie=DOWN;
                break;
            case RIGHT:
                napravlenie=LEFT;
                break;
            case DOWN:
                napravlenie=UP;
                break;
            case LEFT:
                napravlenie=RIGHT;
                break;
            case UR:
                napravlenie=DL;
                break;
            case RD:
                napravlenie=LU;
                break;
            case DL:
                napravlenie=UR;
                break;
            case LU:
                napravlenie=RD;
                break;
        }
        state=JUMP;
        timejump=0;
        speed=speedjump;
        oldnapravlenie = napravlenie;
        sendPlayersMsg("2/7/" + id + "/" + (int) x + "/" + (int) y +"/"+napravlenie+ "/" +atakid);
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
