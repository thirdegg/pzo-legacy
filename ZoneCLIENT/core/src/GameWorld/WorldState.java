package GameWorld;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import util.Util;

import java.util.HashMap;

public class WorldState {
    public static int minutes, hours;
    public static float seconds;
    public static Image imgnight;
    public float alphanight;
    public HashMap<Long, MapObject> mapobjects;
    public WorldState(HashMap<Long, MapObject> mapobjects) {
        imgnight=new Image(new Texture("night.png"));
        setNight();
        this.mapobjects=mapobjects;
    }

    void run(float delta) {
        //seconds += 10 * delta;
        seconds += 80 * delta;
        if (seconds > 60) {
            seconds = 0;
            minutes++;
            if(hours>=20&&minutes%3==0){alphanight+=0.0125f;setNight();}
            if(hours>=4&&hours<8&&minutes%3==0){alphanight-=0.0125f;setNight();}
            sbrosHeroActiv();
        }
        if (minutes == 60) {
            minutes = 0;
            hours++;
            if(hours==7&& Game.hero.idmap!=0){
                for(MapObject mo:mapobjects.values()){
                    if(mo.tip==8)mo.setState(Util.DIED,null);
                }
            }
        }
        if (hours == 24) {
            hours = 0;alphanight=1;
            setNight();
        }
        if(hours==8&&alphanight!=0){alphanight=0;setNight();}
    }
    void sbrosHeroActiv(){
        Game.hero.activvhodvdom=false;
        Game.hero.idactivobject=-1;
        Game.hero.sendgoloc=false;
    }
    void setNight(){
        imgnight.setColor(1,1,1,alphanight);
    }
    void setTimeNight(){
        if(hours>=20&&hours<=23){
            alphanight=0;
        int minhours=(hours-20)*60+minutes;
            alphanight+=minhours/3*0.0125f;
        }
        if(hours>=4&&hours<8){
            alphanight=1;
            int minhours=(hours-4)*60+minutes;
            alphanight-=minhours/3*0.0125f;
        }
        if(hours>=0&&hours<4)alphanight=1;
        if(hours>=8&&hours<20)alphanight=0;
        imgnight.setColor(1,1,1,alphanight);
    }
}
