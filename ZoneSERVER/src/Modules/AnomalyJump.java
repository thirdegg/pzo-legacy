package Modules;

import Gm.SpatialHashGrid;
import MapObjects.MapObject;
import MapObjects.Units.Player;
import Utils.Overlap;
import Utils.Rectang;
import Utils.Util;

import java.util.List;

/**
 * Created by 777 on 20.02.2017.
 */
public class AnomalyJump extends Modifikator {
    private final int LEFT = 5, RIGHT = 3, UP = 2, DOWN = 4, STOP = 1, UR = 6, RD = 7, DL = 8, LU = 9;
    int napravlenie;
    float speed,oldx,oldy;
    int timefly;
    //переделать радиацию
    public AnomalyJump(MapObject mo,int napravlenie,float speed,int timefly,boolean radiation){
        this.mo=mo;
        this.napravlenie=napravlenie;
        this.speed=speed;
        this.timefly=timefly;
        tip=1;
        if(radiation&&mo.tip==Util.PLAYER){
            Player pl=(Player)mo;
            pl.radiation=10;
        }
    }
    @Override
    public void run(float delta, SpatialHashGrid grid){
        mtime+=delta*10;
        move(delta,grid);
        if(mtime>timefly){mtime=0;mo.signal(5,1);}
    }
    private void move(float delta,SpatialHashGrid grid) {
        switch (napravlenie) {
            case UP:
                mo.setPosition( mo.x, mo.y+speed*delta);
                break;
            case RIGHT:
                mo.setPosition( mo.x + speed * delta, mo.y);
                break;
            case DOWN:
                mo.setPosition( mo.x, mo.y-speed*delta);
                break;
            case LEFT:
                mo.setPosition( mo.x - speed * delta, mo.y);
                break;
            case UR:
                mo.setPosition( mo.x + speed * delta, mo.y + speed * delta);
                break;
            case RD:
                mo.setPosition( mo.x + speed * delta, mo.y - speed * delta);
                break;
            case DL:
                mo.setPosition( mo.x - speed * delta, mo.y - speed * delta);
                break;
            case LU:
                mo.setPosition( mo.x - speed * delta, mo.y + speed * delta);
                break;
        }
        if (inRec(grid)) {
            mo.x =  oldx;
            mo.y =  oldy;
        } else {
            oldx =  mo.x;
            oldy =  mo.y;
        }
    }
    public boolean inRec(SpatialHashGrid grid) {
        if (mo.x > 1575 || mo.x < 0 || mo.y > 1565 || mo.y < 0) return true;
        List<Rectang> colliders = grid.getPotentialColliders(mo.rectang);
        for (Rectang re : colliders) {
            if (Overlap.overlapRectang(re, mo.rectang))return true;
        }
        return false;
    }
    @Override
    public String getState(){
        return "21/"+tip+"/"+ mo.id + "/" + napravlenie +"/" + mtime;
    }
}
