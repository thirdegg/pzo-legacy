package Anomaly;

import GameWorld.MapObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import util.Overlap;
import util.Util;

import java.util.HashMap;

/**
 * Created by 777 on 27.01.2017.
 */
public class KisOblako extends MapObject {
    private final int LEFT = 5, RIGHT = 3, UP = 2, DOWN = 4, STOP = 1, UR = 6, RD = 7, DL = 8, LU = 9;
    public int napravlenie = 1;
    HashMap<Long, MapObject> mapobjects;
    public int uron,radius;
    public float speed,timeuron;
    public KisOblako(int xx, int yy, long id,HashMap<Long, MapObject> mapobjects,int naprav){
        super("cane1.png", xx - 200, yy - 200, -50,null,false);
        this.id = id;
        tip=Util.KISOBLAKO;
        napravlenie=naprav;
        centx = xx;
        centy = yy;
        this.mapobjects=mapobjects;
        uron=3;
        radius=200;
        raddclient+=radius/2;
        speed=10;
        height=400;
        width=400;
        originx=200;
        originy=200;
        super.setRegion(Util.kisotex);
    }
    public void run(float delta) {
        move(delta);
        timeuron += delta * 10;
        if (timeuron > 15) {
            timeuron = 0;
            for(MapObject mo:mapobjects.values()){
                if (mo.unit&&mo.canbeattacked && Overlap.pointPoint(mo.centx, mo.centy, centx, centy, radius)) {
                    mo.life.life-=uron;
                }
            }
            switch(napravlenie){
                case 2:
                    if(y>1700)remov=true;
                    break;
                case 3:
                    if(x>1700)remov=true;
                    break;
                case 4:
                    if(y<-100)remov=true;
                    break;
                case 5:
                    if(x<-100)remov=true;
                    break;
            }
        }
        rotate+=0.03;
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
                y += speed * delta;
                x += speed * delta;
                break;
            case RD:
                x += speed * delta;
                y -= speed * delta;
                break;
            case DL:
                y -= speed * delta;
                x -= speed * delta;
                break;
            case LU:
                x -= speed * delta;
                y += speed * delta;
                break;
        }
        centx = x + 200;
        centy = y + 200;
    }
}
