package MapObjectss;

import Map.Imagee;
import Unit.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Bolt extends Imagee {
    private final int LEFT = 5, RIGHT = 3, UP = 2, DOWN = 4, STOP = 1, UR = 6, RD = 7, DL = 8, LU = 9;
    private float speed=110,time,urspeed;
    public  float timefly =10;
    private int napravlenie;
   public boolean run,anim;
   float animtime;
   Player pl;
    public Bolt(Player pl){
        super("bolt.png",-10,-10,2);
        originx=width/2;
        originy=height/2;
        this.pl=pl;
        urspeed=speed/1.7f;
    }
    public void run(float delta){
        time+=10*delta;
        if(time> timefly){
            run=false;time=0;rotate=0;anim=false;animtime=0;visibl=false;}
        move(delta);
        rotate+=6;
        if(anim){
        if(pl.napravlenie==1){
         animtime+=Gdx.graphics.getDeltaTime();
            pl.hero=(TextureRegion)pl.boltanim[pl.nsnapravlenie-2].getKeyFrame(animtime,false);
            if (pl.boltanim[pl.nsnapravlenie-2].isAnimationFinished(animtime)) {
                anim=false;animtime=0;}
        }else{anim=false;animtime=0;}
        }
    }
    public void startBolt(float x,float y,int naprav,boolean skill){
        if(skill)timefly=20;
        else timefly=10;
        run=true;
        this.x=x;this.y=y;
        napravlenie=naprav;anim=true;
        visibl=true;
    }
    private void move(float delta) {
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
                x += urspeed * delta;
                y += urspeed * delta;
                break;
            case RD:
                x += urspeed * delta;
                y -= urspeed * delta;
                break;
            case DL:
                x -= urspeed * delta;
                y -= urspeed * delta;
                break;
            case LU:
                x -= urspeed * delta;
                y += urspeed * delta;
                break;
        }
    }
}
