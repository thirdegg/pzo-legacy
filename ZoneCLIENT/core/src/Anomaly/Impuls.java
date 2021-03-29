package Anomaly;

import GameWorld.Game;
import GameWorld.MapObject;
import Modules.AnomalyJump;
import Unit.Kaban;
import Unit.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import util.Util;

import java.util.HashMap;

public class Impuls extends MapObject {
    TextureRegion electra;
    Animation anim;
    float stateTime;
    HashMap<Long, MapObject> mapobjects;
    int uron;
  //  TextureRegion electrablack;
    MapObject atakobject;
    int naprav;
    float timezarad;
    boolean vzriv;
    boolean run = true;
    float cellx,celly;
    boolean run2=true;
    public Impuls(int xx, int yy, long id, HashMap<Long, MapObject> mapobjects){
        super("impuls.png", xx - 49, yy - 30, 50,null,false);
        this.id = id;
        centx = xx;
        centy = yy;
        this.mapobjects=mapobjects;
        tip = Util.IMPULS;
      //  electrablack=new TextureRegion();
     //   electrablack.setRegion(new Texture("electrblack.png"));
      //  electra=electrablack;
        TextureRegion[][] tmp = split(getRegionWidth() / 15, getRegionHeight());
        int index = 0;
        TextureRegion[] tanim = new TextureRegion[15];
        for (int j = 0; j < 15; j++) {
            tanim[index++] = tmp[0][j];
        }
        width = tanim[0].getRegionWidth();
        height = tanim[0].getRegionHeight();
        electra=tanim[0];
        anim = new Animation(0.1f, tanim);
        uron=10;
        this.setRegion(electra);
    }
    public void run(float delta) {
        if(run){
                if(vzriv) {
                    stateTime += Gdx.graphics.getDeltaTime();
                    electra = (TextureRegion) anim.getKeyFrame(stateTime, false);
                    if (anim.isAnimationFinished(stateTime)) {
                        stateTime = 0;
                        vzriv = false;
                        run=false;
                        run2=true;
                        visibl=false;
                        return;
                    }
                    super.setRegion(electra);
                }
                timezarad += delta * 10;
                if(atakobject!=null&&run2)atakobject.setPosition(cellx,celly);
                if (timezarad > 7&&run2) {
                    if(atakobject!=null){
                 //   atakobject.life.life-=uron;
                    atakobject.signal(4,new AnomalyJump(atakobject,naprav,80,10,false));
                    //если херо тогда убераем из аномалии(для откл\вкыл управление)
                    if(atakobject== Game.hero) Game.hero.vanombatut=false;}
                    timezarad = 0;
                    run2=false;
                }
        }
    }
    @Override
    public void setState(int newstate, String[] str) {
        if(str.length<5)return;
        visibl=true;
        atakobject=mapobjects.get(Long.parseLong(str[3]));
        if(atakobject!=null){
            cellx=atakobject.x;
            celly=atakobject.y;
        if(atakobject.tip==Util.PLAYER){
            Player pl=(Player)atakobject;
        // если херо отключаем управление и включаем в аномалии
        if(pl== Game.hero){
            Game.hero.zapretdvig=true;
            Game.hero.vanombatut=true;}
        pl.napravlenie=1;}
        if(atakobject.tip==Util.KABAN){
            Kaban ka=(Kaban)atakobject;
            ka.napravlenie=1;
        }
        naprav=Integer.parseInt(str[4]);}
        if(str.length>5)timezarad=Float.parseFloat(str[5]);
        else timezarad=0;
        vzriv=true;
        run=true;
        }
}
