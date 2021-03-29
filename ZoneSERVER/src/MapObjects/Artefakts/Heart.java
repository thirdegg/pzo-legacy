package MapObjects.Artefakts;

import Gm.SpatialHashGrid;
import InventItem.core.Item;
import MapObjects.MObject;
import MapObjects.MapObject;
import MapObjects.Units.Player;
import Server.NettyServerHandler;
import Utils.Overlap;
import Utils.Rectang;
import Utils.Util;
import Utils.rand;

import java.util.ArrayList;
import java.util.List;

public class Heart extends MObject {
    List<Player> players=new ArrayList<>();
    private final int LEFT = 5, RIGHT = 3, UP = 2, DOWN = 4, STOP = 1, UR = 6, RD = 7, DL = 8, LU = 9;
    public int napravlenie;
    private float oldx,oldy;
    private SpatialHashGrid grid;
    float speed=18;
    MapObject anomaly;
    public Heart(float x, float y, SpatialHashGrid grid, MapObject anomaly) {
        super(x,y);
        this.grid=grid;
        id = MapObject.generatorId.incrementAndGet();
        tip = Util.ARTHEART;
        podtip=Util.PPARTEFAKT;
        this.anomaly=anomaly;
        clientvisible=false;
        napravlenie=rand.getIntRnd(2,10);
    }
    @Override
    public void run(float delta){
        if(clientvisible){
        move(delta);
        if (inRec()) {
            x = oldx;
            y = oldy;
            setZrandNapravlenie();
            sendMsgPlayers();
        } else {
            oldx = x;
            oldy = y;
        }}
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
    boolean inRec() {
        if (x > 1550 || x < 10 || y > 1550 || y < 10) return true;
        List<Rectang> colliders = grid.getPotentialColliders(rectang);
        for (Rectang re : colliders) {
            if (Overlap.overlapRectang(re, rectang))return true;
        }
        return false;
    }
    public String addedAndDop(Player pl,String str){
        pl.mapobjects.put(id,this);
        players.add(pl);
        str+="/"+napravlenie;
        return str;
    }
    @Override
    public void addItem(Player player){
        if(clientvisible){
        Item it=Util.createItem(27);
        if(it!=null)player.inventar.addItem(it);
        if(anomaly!=null)anomaly.signal(8,null);}
    }
    @Override
    public void signal(int tip,Object object){
        switch(tip){
            case 2:
                long idv=(Long)object;
                for(Player pl:players){
                    if(pl.id==idv){players.remove(pl);break;}
                }
                break;
        }
    }
    public void sendMsgPlayers() {
        for (Player unit : players) {
            NettyServerHandler.sendMsgClient("2/1/" + id + "/" + (int) x + "/" + (int) y + "/" + napravlenie, unit.idchanel);
        }
    }
}
