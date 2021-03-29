package MapObjects.Units;

import Gm.SpatialHashGrid;
import Klans.SystemKlans;
import MapObjects.MapObject;
import MapObjects.RandomItemMap;
import Modules.Life;
import Server.NettyServerHandler;
import Utils.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 777 on 07.06.2017.
 */
public class BosKaban  extends MapObject {
    private final int LEFT = 5, RIGHT = 3, UP = 2, DOWN = 4, STOP = 1, UR = 6, RD = 7, DL = 8, LU = 9;
    public int dvigcellx, dvigcelly;
    int napravlenie = 1, oldnapravlenie, sensetivdetect, skorostatak = 15;
    boolean ataka;
    float oldx, oldy;
    Rectang activhitbox;
    long ldt;
    int tdead = 20000;

    public SpatialHashGrid grid;
    public final int DVIG=0,DEAD=1,ATAKA=2,PRESLEDOVANIE=5,DVIGCELL=6;
    public	float speed,uron,golod;
    public Life pmLife;
    protected int state;
    private MapObject atakobject;
    List<Player> players=new ArrayList<>();
    List<AddMOparameters>addmapobjects;
    long lasttimeatak;
    ObjectParametr op;
    public BosKaban(float x, float y,SpatialHashGrid grid,List<AddMOparameters>addmapobjects, ObjectParametr op) {
        tip = Util.BOSKABAN;
        podtip=Util.PPBOS;
        this.x = x;
        this.y = y;
        this.op=op;
        plasrecx = 16;
        plasrecy = 11;
        rectang = new Rectang(x + plasrecx, y + plasrecy, 11, 10);
        centx = rectang.x + rectang.width / 2;
        centy = rectang.y + rectang.height / 2;
        id = generatorId.incrementAndGet();
        this.grid=grid;
        speed = 90;
        uron = 30;
        pmLife=new Life();
        pmLife.life=600;
        canbeattacked=true;
        this.addmapobjects=addmapobjects;
        unit=true;
        dvigcellx=(int)x;
        dvigcelly=(int)y;
    }

    public void run(float delta) {
        switch (state) {
            case DVIG:
                if (sensetivdetect > 25) {
                    poiscAtakUnit();
                    sensetivdetect = 0;
                }
                sensetivdetect++;
                //поменять при движении наискость вниз при подходе к левому краю карты
                // возникает проблема
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
                if (atakobject!=null&& Overlap.pointPoint(x, y, atakobject.x, atakobject.y, 400) && atakobject.canbeattacked) {
                    if (sensetivdetect > 4) {
                        detectNapravlenie(atakobject.x, atakobject.y);
                        sensetivdetect = 0;
                    }
                    sensetivdetect++;
                    move(delta);
                    if (Overlap.pointPoint(x, y, atakobject.x, atakobject.y, 50)) {
                        state = ATAKA;
                        napravlenie = oldnapravlenie;
                        ataka=true;
                        boolean fu=false;
                        if(System.currentTimeMillis()-lasttimeatak>1500){fu=true;
                        lasttimeatak=System.currentTimeMillis();}
                        atakobject.ataked(id,uron,skorostatak,0,Util.FIZURON,true,true,fu);
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
                    state = DVIGCELL;
                }
                break;

            case ATAKA:
                Ataka();
                break;

            case DVIGCELL:
                move(delta);
                if (x - dvigcellx < 15 && x - dvigcellx > -15 && y - dvigcelly < 15 && y - dvigcelly > -15) {state = DVIG;
                    napravlenie=0;}
                if (sensetivdetect > 7) {
                    poiscAtakUnit();
                    detectNapravlenie(dvigcellx, dvigcelly);
                    sensetivdetect = 0;
                }
                sensetivdetect++;
                if (inRec(grid)) {
                    x = oldx;
                    y = oldy;
                    poiscPuti(grid);
                    setZrandNapravlenie();
                } else {
                    oldx = x;
                    oldy = y;
                }
                break;

            case DEAD:
                if (System.currentTimeMillis() > ldt) remov = true;
                break;
        }
        if (state != DEAD && pmLife.life <= 0) {
            state = DEAD;
            op.timespaawn=System.currentTimeMillis()+30000;
            op.create=true;
            pmLife.stopAll();
            Player pl=null;
            for(Player pla:players){
                if(pla.id==pmLife.killid){pl=pla;break;}
            }
            if(pl!=null)pl.signal(3,id);
            canbeattacked=false;
            if(ataka){ataka=false;atakobject.signal(1,id);atakobject=null;}
            sendPlayersMsg("2/3/" + id);
            ldt = System.currentTimeMillis() + tdead;
            RandomItemMap tim=new RandomItemMap(centx,centy,Util.createItem(45),120000);
            addmapobjects.add(new AddMOparameters(tim,false,false,0));
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

    void poiscPuti(SpatialHashGrid grid) {
        switch (napravlenie) {
            case DOWN:
            case UP:
                if (activhitbox!=null&&x < activhitbox.x + activhitbox.centw) {
                    napravlenie = LEFT;
                } else {
                    napravlenie = RIGHT;
                }
                break;
            case LEFT:
            case RIGHT:
                if (activhitbox!=null&&y < activhitbox.y + activhitbox.centh) {
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

    void Ataka() {
        if (atakobject != null && atakobject.canbeattacked) {
            if (!Overlap.pointPoint(centx, centy, atakobject.centx, atakobject.centy, 55)) {
                oldnapravlenie = 0;
                state = PRESLEDOVANIE;
                ataka=false;
                atakobject.signal(1,id);
            }
        } else {
            oldnapravlenie = 0;
            state = DVIGCELL;
            ataka=false;
            if(atakobject!=null){atakobject.signal(1,id);atakobject = null;}
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
            case DVIGCELL:
            case DVIG:
                return "2/1/" + id + "/" + (int) x + "/" + (int) y + "/" + napravlenie + "/" + (int) pmLife.life;
            case ATAKA:if(atakobject!=null){return"2/2/" + id + "/" + atakobject.id + "/" + (int) x + "/" + (int) y;}
            else {state=DVIG;
                return "2/1/" + id + "/" + (int) x + "/" + (int) y + "/" + napravlenie + "/" + (int) pmLife.life;}
            case DEAD:
                int timedie = (int) (ldt - System.currentTimeMillis());
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
    //этот объект добавляют к игроку в его списки и добавляют в строку состояния доп параметры
    public String addedAndDop(Player pl,String str){
        pl.mapobjects.put(id,this);
        players.add(pl);
        str+="/"+(int)pmLife.life+"/"+speed;
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
    @Override
    public void ataked(long idcel, float kolvolife, float timeperiod, float fulltime, int tipuron, boolean notendtime,boolean send,boolean fasturon) {
        if(send) sendPlayersMsg("3/1/"+id+"/"+idcel+"/"+kolvolife+"/"+timeperiod+"/"+fulltime+"/"+tipuron+"/"+notendtime+"/"+fasturon);
        pmLife.timeMinusLife(idcel,kolvolife,timeperiod,fulltime,tipuron,notendtime,fasturon);
        Player pl=null;
        for(Player pla:players){
                if(pla.id==idcel){pl=pla;break;}}
        if(pl!=null){
            atakobject = pl;
            state = PRESLEDOVANIE;
        }
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
}