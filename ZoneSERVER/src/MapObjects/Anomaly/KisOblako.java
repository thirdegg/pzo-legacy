package MapObjects.Anomaly;

import MapObjects.MapObject;
import MapObjects.Units.Player;
import Utils.Overlap;
import Utils.Util;
import Utils.rand;

import java.util.HashMap;



public class KisOblako extends MapObject {
    private final int LEFT = 5, RIGHT = 3, UP = 2, DOWN = 4, UR = 6, RD = 7, DL = 8, LU = 9;
    public int napravlenie;
    HashMap<Long, MapObject> mapobjects;
    public int uron;
    float speed,timeuron;
    public KisOblako(HashMap<Long, MapObject> mapobjects){
        id = generatorId.incrementAndGet();
        this.mapobjects=mapobjects;
        tip = Util.KISOBLAKO;
        podtip=Util.PPANOMALY;
        uron=3;
        radius=200;
        raddclient+=radius/2;
        speed=10;
        napravlenie=rand.getIntRnd(2,4);
        switch(napravlenie){
            case 2:
                y=-100;x=rand.getIntRnd(50,1500);centx=x;centy=y;
                break;
            case 3:
                x=-100;y=rand.getIntRnd(50,1500);centx=x;centy=y;
                break;
            case 4:
                y=1700;x=rand.getIntRnd(50,1500);centx=x;centy=y;
                break;
            case 5:
                x=1700;y=rand.getIntRnd(50,1500);centx=x;centy=y;
                break;
        }
        x=2300;y=880;
    }
    public void run(float delta) {
        move(delta);
        timeuron += delta * 10;
        if (timeuron > 15) {
            timeuron = 0;
            for(MapObject mo:mapobjects.values()){
                if (mo.unit&&mo.canbeattacked && Overlap.pointPoint(mo.centx, mo.centy, centx, centy, radius)) {
                    mo.ataked(id,uron,Util.YADURON,false);
                }
            }
            //если ушло за край карты
            if(y>1700||x>1700||x<-100||y<-100){
                napravlenie=rand.getIntRnd(2,4);
            switch(napravlenie){
                case 2:
                    y=-100;x=rand.getIntRnd(50,1500);centx=x;centy=y;
                    break;
                case 3:
                    x=-100;y=rand.getIntRnd(50,1500);centx=x;centy=y;
                    break;
                case 4:
                    y=1700;x=rand.getIntRnd(50,1500);centx=x;centy=y;
                    break;
                case 5:
                    x=1700;y=rand.getIntRnd(50,1500);centx=x;centy=y;
                    break;
            }
           }
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
        centx = x;
        centy = y;
    }
    public String addedAndDop(Player pl,String str){
        pl.mapobjects.put(id,this);
        str+="/"+napravlenie+"/"+timeuron;
        return str;
    }
}
