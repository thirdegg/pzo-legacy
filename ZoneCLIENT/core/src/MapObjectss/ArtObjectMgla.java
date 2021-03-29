package MapObjectss;

import GameWorld.MapObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import util.Rectang;
import util.Util;

public class ArtObjectMgla extends MapObject {
        Animation anim;
        float stateTime;
        boolean send;

public ArtObjectMgla(float xx, float yy, long id) {
        super("artmgla.png", xx, yy, 2,null,false);
        this.id = id;
        tip = Util.ARTMGLA;
        TextureRegion[][] tmp = split(getRegionWidth()/4,getRegionHeight());
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
        mput=true;
        }

public void run(float delta) {
        stateTime += Gdx.graphics.getDeltaTime();
        super.setRegion((TextureRegion) anim.getKeyFrame(stateTime, true));
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
