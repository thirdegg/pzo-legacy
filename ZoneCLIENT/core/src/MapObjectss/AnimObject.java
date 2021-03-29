package MapObjectss;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimObject extends ClientMapObject {
    Animation anim;
    float stateTime;

    public AnimObject(TextureRegion trr,float xx, float yy, float z,int tip,int kolvokadrov,float framed) {
        super(trr,xx, yy, z,tip);
        TextureRegion[][] tmp = split(getRegionWidth() / kolvokadrov, getRegionHeight());
        int index = 0;
        TextureRegion[] tanim = new TextureRegion[kolvokadrov];
            for (int j = 0; j < kolvokadrov; j++) {
                tanim[index++] = tmp[0][j];
            }
        width = tanim[0].getRegionWidth();
        height = tanim[0].getRegionHeight();
        anim = new Animation(framed, tanim);
    }
@Override
    public void run(float delta) {
        stateTime += Gdx.graphics.getDeltaTime();
        super.setRegion((TextureRegion)anim.getKeyFrame(stateTime, true));
    }
}
