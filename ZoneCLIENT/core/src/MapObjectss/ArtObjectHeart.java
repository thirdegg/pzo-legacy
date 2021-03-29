package MapObjectss;

import GameWorld.MapObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import util.Rectang;
import util.Util;

/**
 * Created by 777 on 25.03.2017.
 */
public class ArtObjectHeart extends MapObject {
    Animation anim;
    float stateTime;
    int napravlenie = 1;
    float speed=18;
    public ArtObjectHeart(float xx, float yy, long id,int napravlenie) {
        super("artxp.png", xx, yy, 2, null, false);
        this.id = id;
        tip = Util.ARTHEART;
        this.napravlenie=napravlenie;
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
        move(delta);
        stateTime += Gdx.graphics.getDeltaTime();
        super.setRegion((TextureRegion) anim.getKeyFrame(stateTime, true));
    }
    void move(float delta) {
        switch (napravlenie) {
            case 2:
                y += speed * delta;
                break;
            case 3:
                x += speed * delta;
                break;
            case 4:
                y -= speed * delta;
                break;
            case 5:
                x -= speed * delta;
                break;
            case 6:
                x += speed * delta;
                y += speed * delta;
                break;
            case 7:
                x += speed * delta;
                y -= speed * delta;
                break;
            case 8:
                x -= speed * delta;
                y -= speed * delta;
                break;
            case 9:
                x -= speed * delta;
                y += speed * delta;
                break;
        }
        rectang.y = y + plasrecy;
        rectang.x = x + plasrecx;
        centx = rectang.x + rectang.width / 2;
        centy = rectang.y + rectang.height / 2;
    }
    //инногда не срабатывает (хотя скорее всего просто не приходит с сервера)
    public void setState(int newstate, String[] str) {
        x = Integer.parseInt(str[3]);
        y = Integer.parseInt(str[4]);
        rectang.x = x + plasrecx;
        rectang.y = y + plasrecy;
        centx = rectang.x + rectang.width / 2;
        centy = rectang.y + rectang.height / 2;
     if(str.length>5)napravlenie = Integer.parseInt(str[5]);
    }
}