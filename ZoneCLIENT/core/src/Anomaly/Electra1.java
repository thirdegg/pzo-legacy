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

public class Electra1 extends Electra {
    TextureRegion electrablack;
    private float rot;
    public Electra1(int xx, int yy, long id,HashMap<Long, MapObject> mapobjects) {
        super(xx, yy,id,mapobjects);
        tip = Util.ELECTRA;
        electrablack=new TextureRegion();
        electrablack.setRegion(new Texture("electrblack.png"));
        scalex=0.4f;
        scaley=0.4f;
        visibl=false;
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
                visibl=false;
            }
            super.setRegion(electra);
        }
    }
    @Override
    public void dopDraw(Batch sb, ShapeRenderer sr) {
        if(sb.isDrawing())sb.draw(electrablack,centx-17,centy-17,17,17,34,34,1,1,rot);
    }
    @Override
    public void setState(int newstate, String[] str) {
        if(GameWorld.Game.rain)uron=40;else uron=30;
        vzriv = true;
        visibl=true;
        Sounds.playElectra();
    }
}
