package MapObjectss;

import GameWorld.MapObject;
import Unit.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import util.Overlap;
import util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Koster extends MapObject {
    long startdl;
    int radius = 50;
    int propk;
    Collection<Player> players;
    List<Player> plukostra=new ArrayList<Player>();
    boolean gorit=true;
    Animation anim;
    float stateTime;
    public Koster(float xx, float yy,long idd,long dlitelnost,Collection<Player>players) {
        super("kostr.png",xx, yy, 13,null,false);
        tip= Util.KOSTER;
        id=idd;
        centx=x;
        centy=y;
        TextureRegion[][] tmp = split(getRegionWidth()/2,getRegionHeight()/2);
        int index = 0;
        TextureRegion[] kanim = new TextureRegion[2 * 2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                kanim[index++] = tmp[i][j];
            }
        }
        width = kanim[0].getRegionWidth();
        height = kanim[0].getRegionHeight();
        anim = new Animation(0.1f, kanim);
        this.players=players;
        startdl=System.currentTimeMillis()+dlitelnost;
    }
    @Override
    public void run(float delta) {
        if (!remov) {
            stateTime += Gdx.graphics.getDeltaTime();
            super.setRegion((TextureRegion) anim.getKeyFrame(stateTime, true));

            if (System.currentTimeMillis() < startdl) {
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
            } else {
                if (gorit) {
                    gorit = false;
                    remov = true;
                    propk=0;
                    for (Player pl : plukostra) {
                        if (pl.ukostra) {
                            pl.ukostra = false;
                            pl.holod -= 5;
                        }
                    }
                    plukostra.clear();
                }
            }
        }
    }
}
