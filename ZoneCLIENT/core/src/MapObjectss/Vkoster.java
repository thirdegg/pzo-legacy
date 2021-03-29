package MapObjectss;

import GameWorld.MapObject;
import Unit.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import util.Overlap;
import util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by 777 on 01.03.2017.
 */
public class Vkoster extends MapObject{
    int radius = 50;
    int propk;
    Collection<Player> players;
    List<Player> plukostra=new ArrayList<Player>();
    Animation anim;
    float stateTime;
    public Vkoster(float xx, float yy,long idd,Collection<Player>players) {
        super("kaban_a.png",xx, yy, 13,null,false);
        tip= Util.VKOSTER;
        id=idd;
        centx=x;
        centy=y;
        TextureRegion[][] tmp = split(getRegionWidth()/4,getRegionHeight());
        int index = 0;
        TextureRegion[] kanim = new TextureRegion[4];
        for (int i = 0; i < 4; i++) {
            kanim[index++] = tmp[0][i];
        }
        width = kanim[0].getRegionWidth();
        height = kanim[0].getRegionHeight();
        anim = new Animation(0.1f, kanim);
        this.players=players;
    }
    @Override
    public void run(float delta) {
            stateTime += Gdx.graphics.getDeltaTime();
            super.setRegion((TextureRegion) anim.getKeyFrame(stateTime, true));
                propk++;
                if (propk == 4) {
                    propk = 0;
                    for (Player pl : players) {
                        if (!pl.ukostra) {
                            if (Overlap.pointPoint(pl.x, pl.y, x, y, radius)) {
                                pl.ukostra = true;
                                pl.holod += 5;
                                plukostra.add(pl);
                            }
                        }
                    }
                    for(Player pl:plukostra){
                        if (!Overlap.pointPoint(pl.x, pl.y, x, y, radius)) {
                            pl.ukostra = false;
                            pl.holod -= 5;
                            plukostra.remove(pl);
                            break;
                        }
                    }
                }
    }
}

