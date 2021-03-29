package Modules;


import GameWorld.MapObject;
import GameWorld.SpatialHashGrid;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by 777 on 23.02.2017.
 */
public class Teleport extends Modifikator {
    float kx,ky;
    boolean t,indo,prod;
    Animation anim;
    float animtime;
    int frame;
    public Teleport(MapObject mo, float x, float y){
        this.mo=mo;
        kx=x;ky=y;
        tip=2;
        Texture electr = new Texture(Gdx.files.internal("teleport.png"));
        TextureRegion[][] tmp = TextureRegion.split(electr, electr.getWidth() / 3, electr.getHeight() / 3);
        int index = 0;
        TextureRegion tanim[] = new TextureRegion[3 * 3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tanim[index++] = tmp[i][j];
            }
        }
        anim = new Animation(0.08f, tanim);
        anim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
    }
    public Teleport(MapObject mo, float mtime,boolean t,float x,float y){
        this.mo=mo;
        tip=2;
        Texture electr = new Texture(Gdx.files.internal("teleport.png"));
        TextureRegion[][] tmp = TextureRegion.split(electr, electr.getWidth() / 3, electr.getHeight() / 3);
        int index = 0;
        TextureRegion tanim[] = new TextureRegion[3 * 3];
        if(!t){
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tanim[index++] = tmp[i][j];
            }
        }
        anim = new Animation(0.08f, tanim);
        anim.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);}
        else{
            for (int i = 2; i>=0; i--) {
                for (int j = 2; j >= 0; j--) {
                    tanim[index++] = tmp[i][j];
                }
            }
            anim = new Animation(0.08f, tanim);
        }
        this.mtime=mtime;
        this.t=t;
        if(!t){kx=x;ky=y;}else{
            prod=true;
        }
    }
    @Override
    public void run(float delta, SpatialHashGrid grid){
        mtime+=delta*10;
        if(mtime>7){
            if(!t){
            mtime=0;t=true;
                mo.setPosition(kx,ky);}
            else{
                mo.signal(5,tip);
            }
    }
    }
    @Override
    public void draw(Batch spbstch){
        if(!indo&&!prod){
        animtime+=Gdx.graphics.getDeltaTime();
        spbstch.draw((TextureRegion)anim.getKeyFrame(animtime),mo.x,mo.y);
        frame=anim.getKeyFrameIndex(animtime);
        if(frame>4)mo.visibl=false;else mo.visibl=true;
        if(t&&frame==0)indo=true;}
        if(prod){
            animtime+=Gdx.graphics.getDeltaTime();
            spbstch.draw((TextureRegion)anim.getKeyFrame(animtime,false),mo.x-6,mo.y);
        }
    }
}
