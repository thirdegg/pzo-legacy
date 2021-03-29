package Anomaly;

import GameWorld.MapObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import util.Util;

import java.util.HashMap;

public class Electra extends MapObject {
    TextureRegion electra;
    TextureRegion[] tanim;
    Animation anim;
    float stateTime;
    HashMap<Long, MapObject> mapobjects;
    int uron;
    boolean vzriv;
    public Electra(int xx, int yy, long id,HashMap<Long, MapObject> mapobjects){
        super("cane1.png", xx - 42, yy - 30, 10,null,false);
        this.id = id;
        centx = xx;
        centy = yy;
        this.mapobjects=mapobjects;
        Texture electr = Util.electra;
        TextureRegion[][] tmp = TextureRegion.split(electr, electr.getWidth() / 5, electr.getHeight() / 3);
        int index = 0;
        tanim = new TextureRegion[5 * 3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                tanim[index++] = tmp[i][j];
            }
        }
        width = tanim[0].getRegionWidth();
        height = tanim[0].getRegionHeight();
        anim = new Animation(0.1f, tanim);
        uron=30;
    }
}
