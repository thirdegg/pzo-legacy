package Modules;

import GameWorld.MapObject;
import GameWorld.SpatialHashGrid;
import Unit.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import util.Overlap;
import util.Rectang;

import java.util.List;

/**
 * Created by 777 on 20.02.2017.
 */
public class AnomalyJump extends Modifikator {
    private final int LEFT = 5, RIGHT = 3, UP = 2, DOWN = 4, STOP = 1, UR = 6, RD = 7, DL = 8, LU = 9;
    Player pl;
    int napravlenie;
    float speed,oldx,oldy,animTimeJump;
    int animnaprav;
    int timefly;
    public AnomalyJump(MapObject mo,int napravlenie,float speed,int timefly,boolean radiation){
        this.mo=mo;
        this.napravlenie=napravlenie;
        this.speed=speed;
        this.timefly=timefly;
        tip=1;
        if(mo.tip==1){pl=(Player)mo;
            if(radiation)pl.radiation=10;}
    }
    public AnomalyJump(MapObject mo,int napravlenie,float speed,float mtime){
        this.mo=mo;
        this.napravlenie=napravlenie;
        this.speed=speed;
        this.timefly=10;
        tip=1;
        this.mtime=mtime;
    }
    @Override
    public void run(float delta, SpatialHashGrid grid){
        mtime+=delta*10;
        move(delta,grid);
        if(mtime>timefly){mtime=0;mo.signal(5,1);animTimeJump=0;}
        if(pl!=null){
        animTimeJump+= Gdx.graphics.getDeltaTime();
        //исправляем косяк
            animnaprav=napravlenie;
            if(animnaprav==4)animnaprav=0;
            if(animnaprav==5)animnaprav=1;
            if(animnaprav==8)animnaprav=4;
            if(animnaprav==9)animnaprav=5;
        pl.hero= (TextureRegion) pl.deadanim[animnaprav].getKeyFrame(animTimeJump,false);
        }
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
        return "2/7/" + mo.id + "/" + napravlenie +"/" + mtime;
    }
}
