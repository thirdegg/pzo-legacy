package Anomaly;

import GameWorld.Game;
import GameWorld.MapObject;
import Unit.Hero;
import com.badlogic.gdx.audio.Sound;
import util.Overlap;
import util.Sounds;
import util.Util;


/**
 * Created by 777 on 13.05.2017.
 */
public class Radiation extends MapObject {

    private float radtime,strong;
    public int radius;
    public Radiation(int xx, int yy, long id, int radius, float strong) {
        super("kisoblako.png", xx - 3, yy - 10, 0,null,false);
        this.id = id;
        centx = xx;
        centy = yy;
        tip= Util.RADIATION;
        visibl=false;
        this.strong=strong;
        this.radius=radius;
    }
    @Override
    public void run(float delta){
        radtime += 10 * delta;
        if(radtime>10){
            if(Overlap.pointPoint(Game.hero.centx,Game.hero.centy,centx,centy,radius)){
                Game.hero.radiation+=strong;
                if(Hero.radiomiter&&!Sounds.isplayrad)Sounds.playRadiomiter();
                }
        radtime=0;
        }
    }
}
