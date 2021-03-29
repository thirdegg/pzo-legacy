package MapObjects;

import Utils.Overlap;
import Utils.Util;
import java.util.Map;

public class Bolt {
    private final int LEFT = 5, RIGHT = 3, UP = 2, DOWN = 4, STOP = 1, UR = 6, RD = 7, DL = 8, LU = 9;
    private float speed=110,time,centx,centy,urspeed;
    public float timefly =10;
    private int napravlenie;
   public boolean run;
    private int skipitter;
    Map<Long, MapObject> mapobjects;
    public Bolt(Map<Long, MapObject> mapobjects){
        this.mapobjects=mapobjects;
        urspeed=speed/1.7f;
    }
    public void run(float delta){
        time+=10*delta;
        if(skipitter==3){
            skipitter=0;
        for (MapObject mo:mapobjects.values()){
        if(mo.podtip==Util.PPANOMALY&&mo.tip!=Util.KISOBLAKO&&Overlap.pointPoint(centx,centy,mo.centx,mo.centy,mo.radius)){
            mo.signal(5,null);
            skipitter=4; break;
        }}}
        if(time> timefly){
            run=false;time=0;skipitter=0;
        }
        move(delta);
        skipitter++;

    }
    public void startBolt(float x,float y,int naprav){
        run=true;
        centx=x;centy=y;
        napravlenie=naprav;
    }
    private void move(float delta) {
        switch (napravlenie) {
            case UP:
                centy += speed * delta;
                break;
            case RIGHT:
                centx += speed * delta;
                break;
            case DOWN:
                centy -= speed * delta;
                break;
            case LEFT:
                centx -= speed * delta;
                break;
            case UR:
                centx += urspeed * delta;
                centy += urspeed * delta;
                break;
            case RD:
                centx += urspeed * delta;
                centy -= urspeed * delta;
                break;
            case DL:
                centx -= urspeed * delta;
                centy -= urspeed * delta;
                break;
            case LU:
                centx -= urspeed * delta;
                centy += urspeed * delta;
                break;
        }
    }
}
