package MapObjectss;

import GameWorld.MapObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import util.Rectang;
import util.Util;

/**
 * Created by 777 on 01.09.2017.
 */
public class ArtObjectObereg extends MapObject {
    Animation anim;
    float stateTime;
    public float timevis;
    public boolean vis;
    public ArtObjectObereg(float xx, float yy, long id) {
        super("artob.png", xx, yy, 2, null, false);
        this.id = id;
        tip = Util.ARTOBERG;
        TextureRegion[][] tmp = split(getRegionWidth() / 4, getRegionHeight());
        int index = 0;
        TextureRegion[] tanim = new TextureRegion[4];
        for (int j = 0; j < 4; j++) {
            tanim[index++] = tmp[0][j];
        }
        width = tanim[0].getRegionWidth();
        height = tanim[0].getRegionHeight();
        anim = new Animation(0.2f, tanim);
        plasrecx = 5;
        plasrecy = 3;
        centx = x + plasrecx;
        centy = y + plasrecy;
        rectang = new Rectang(x + plasrecx, y + plasrecy, 10, 10);
        width = tanim[0].getRegionWidth();
        height = tanim[0].getRegionHeight();
        mput = true;
    }

    public void run(float delta) {
        if(visibl){
            stateTime += Gdx.graphics.getDeltaTime();
            super.setRegion((TextureRegion) anim.getKeyFrame(stateTime, true));
            if(!vis){
                timevis += delta * 10;
                if (timevis > 45) {
                    timevis = 0;
                    visibl=false;
                    remov=true;
                }}}
    }
    //инногда не срабатывает (хотя скорее всего просто не приходит с сервера)
    public void setState(int newstate, String[] str) {
        x = Integer.parseInt(str[3]);
        y = Integer.parseInt(str[4]);
        rectang.x = x + plasrecx;
        rectang.y = y + plasrecy;
        centx = rectang.x + rectang.width / 2;
        centy = rectang.y + rectang.height / 2;
    }
}