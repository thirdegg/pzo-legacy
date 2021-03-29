package Anomaly;

import GameWorld.MapObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import util.Overlap;
import util.Sounds;
import util.Util;
import util.rand;

import java.util.HashMap;

/**
 * Created by 777 on 06.12.2016.
 */
public class Electra2 extends Electra {
    TextureRegion electra2,electrablack;
    float timezarad;
    boolean run2,vzriv2;
    private float rot;
    public Electra2(int xx, int yy, long id,HashMap<Long, MapObject> mapobjects) {
        super(xx,yy,id,mapobjects);
        electra2=new TextureRegion();
        electra2.setRegion(new Texture("electra2.png"));
        electrablack=new TextureRegion();
        electrablack.setRegion(new Texture("electrblack.png"));
        scalex=0.4f;
        scaley=0.4f;
        visibl=false;
        tip=Util.ELECTRA2;
        rot=rand.getIntRnd(0, 355);
    }
    @Override
    public void run(float delta) {
        if (vzriv) {
            stateTime += Gdx.graphics.getDeltaTime();
            electra = (TextureRegion) anim.getKeyFrame(stateTime, true);
            if (anim.isAnimationFinished(stateTime)) {
                vzriv = false;
                stateTime = 0;
            }
            super.setRegion(electra);
        }
        if(run2){
            timezarad += delta * 10;
            if (timezarad > 10&&!vzriv2) {
                vzriv2=true;
            }
            if(timezarad > 13&&vzriv2){
                vzriv = false;
                run2=false;
                vzriv2=false;
                visibl=false;
                stateTime = 0;
                timezarad=0;
            }
        }
    }
    @Override
    public void dopDraw(Batch sb, ShapeRenderer sr) {
        if(sb.isDrawing()) {
            sb.draw(electrablack, centx - 17, centy - 17, 17, 17, 34, 34, 1, 1, rot);
            if(vzriv2)sb.draw(electra2, centx - 100, centy - 100, 100, 100, 200, 200, 1, 1, rot);
        }
    }
    @Override
    public void setState(int newstate, String[] str) {
        if(str.length<4) {
            if(GameWorld.Game.rain)uron=40;else uron=30;
            vzriv = true;
            visibl=true;
            run2=true;
            Sounds.playElectra();
        }else{
            vzriv=false;
            run2=true;
            electra = tanim[5];
            timezarad=Float.parseFloat(str[3]);
        }
    }
}
