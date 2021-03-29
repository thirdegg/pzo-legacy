package Unit;

import Anomaly.Radiation;
import GameInterface.Ginterface;
import GameWorld.Game;
import GameWorld.MapObject;
import GameWorld.SpatialHashGrid;
import InventItem.Item;
import InventItem.Weapon;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Array;
import util.Overlap;
import util.Rectang;
import util.Sounds;
import util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hero extends Player {
    public Runtip runtip = Runtip.DOWN;
    public int many;
    public static boolean radiomiter;
    public Map<Integer,Integer> qvests = new HashMap<Integer, Integer>();
    //обнуляется каждую игр минуту в статеворлд
    public long idactivobject;
    public boolean sendgoloc,activvhodvdom,zapretdvig,vanombatut;
    public int idmap;
    public MapObject nastupKorobka;
    public Array<Item>items=new Array<Item>();
    public Array<Item>odeto=new Array<Item>();
    boolean playwalk;
    //используется после активации итема из быстрой панели
    public int itstate;
    public int activitemid;
    public boolean inventcanuse=true;
    public float inventtimecanuse;
    public int colvoslotov=6;
    public List <Integer>spisskills=new ArrayList<Integer>();
    public Hero(float xx, float yy, long id, String name, LabelStyle ls, LabelStyle ls2,Game game,int tipodeto) {
        super(xx, yy, id, name, ls, ls2,game,tipodeto);
    }
    @Override
    public void run(float delta) {
        stateTime += Gdx.graphics.getDeltaTime();
        switch (state) {
            case Util.WALKING:
                move(delta);
                if(zapretdvig&&!vanombatut)zapretdvig=false;
                //звук шагов
              if(playwalk){if(napravlenie<=1){playwalk=false;Sounds.stopWalking();}}
              if(!playwalk){if(napravlenie>1){playwalk=true;Sounds.playWalking();}}
                break;
            case Util.ATAKA:
                Ataka(delta);
                inventcanuse=false;
                inventtimecanuse=0;
                break;
            case Util.DIED:
                dead();
                if(playwalk){playwalk=false;Sounds.stopWalking();}
                break;
            case Util.PEREZARAD:
                perezaradka(delta);
                break;
            case Util.VDOME:
                if(playwalk){playwalk=false;Sounds.stopWalking();}
                break;
            case  Util.MODIFIKATOR:
                mod.run(delta,game.grid);
                break;
            case Util.SITTING:
                if(ansit){
                    sitanimtime+=Gdx.graphics.getDeltaTime();
                    hero=(TextureRegion)sitinganim[nsnapravlenie-2].getKeyFrame(sitanimtime,false);
                    if(sitinganim[nsnapravlenie-2].isAnimationFinished(sitanimtime)){ansit=false;sitanimtime=0;}}
                if(gitara){
                    sitanimtime+=Gdx.graphics.getDeltaTime();
                    hero=(TextureRegion)gitaranim[nsnapravlenie-2].getKeyFrame(sitanimtime,true);}
                break;
        }
        //bolt
        if(bolt.run)bolt.run(delta);
        if(state!=Util.DIED)radiation(delta);
        if (life.life < 0) life.life = 0;
        if (life.life / ld < 15) {
            colorlife = Color.RED;
        } else {
            if (life.life / ld < 30) {
                colorlife = Color.YELLOW;
            } else {
                if (life.life / ld <= 40) {
                    colorlife = Color.GREEN;
                }
            }
        }
        life.run(delta,x,y);
        if(!inventcanuse){
            inventtimecanuse+=delta*10;
            if(inventtimecanuse>200)inventcanuse=true;
        }
        super.setRegion(hero);
        if(radiomiter&&Sounds.isplayrad){
            boolean in=false;
            for(MapObject mo:game.mapobjects.values()){
                if(mo.tip==Util.RADIATION){
                    Radiation r=(Radiation)mo;
                    if(Overlap.pointPoint(centx,centy,r.centx,r.centy,r.radius)){
                        in=true;break;}}
            }
            if(!in)Sounds.stopRadiomiter();
        }
    }

    public boolean getMesto() {
        return items.size < 20;
    }

    public Item getItem(int tipit, boolean bodeto) {
        if(!bodeto){
            for (Item it:items) {
                if (it.tipitem == tipit) return it;
            }}
            else{
            for (Item it:odeto) {
                if (it.tipitem == tipit) return it;
            }
        }
        return null;
    }
    boolean inRec(SpatialHashGrid grid) {
        if (rectang.x > 1588 || rectang.x < 1 || rectang.y > 1589 || rectang.y < 1) return true;
        List<Rectang> colliders = grid.getPotentialColliders(rectang);
        for (Rectang re : colliders) {
            if (Overlap.overlapRectang(re, rectang)) {
                if(re.dom&&!activvhodvdom&&!vdome){
                    if(inventcanuse){
                    game.game.tout.sendMsg("1/6/");
                activvhodvdom=true;}else{
                        Ginterface.setText("нельзя войти в дом в боевом режиме",3000);
                    }}
                return true;
            }
        }
        return false;
    }
  public  void addItem(Item it){
      items.add(it);
      game.ginterface.setItemWindow(it.tipitem);
      if (game.ginterface.invent.binvent) game.ginterface.invent.openInvent();
      if (game.ginterface.invent.btorg) game.ginterface.invent.openTorg();
    }
    public  void remItem(int iditem){
 for(Item it:items){
     if(it.id==iditem){items.removeValue(it,false);break;}
 }
        if (game.ginterface.invent.binvent) game.ginterface.invent.openInvent();
        if (game.ginterface.invent.btorg) game.ginterface.invent.openTorg();
    }
    public void Snyat(int id) {
        for (Item it : odeto) {
            if (it.id == id) {
                odeto.removeValue(it,false);
                items.add(it);
                if(it.weapon)gun=null;
                //радиомитер
                if(it.tipitem==97){radiomiter=false;
                if(Sounds.isplayrad)Sounds.stopRadiomiter();}
                    break;
            }
        }
        setPlayerGun();
        if (game.ginterface.invent.binvent) game.ginterface.invent.openInvent();
        if (game.ginterface.invent.btorg) game.ginterface.invent.openTorg();
    }
    public void useItem(int id) {
        if(state!= Util.DIED){
        for (Item it : items) {
            if (it.id == id) {
                if(it.remuse)items.removeValue(it,false);
                break;
            }
        }
            for (Item it : odeto) {
                if (it.id == id) {
                    if(it.remuse)odeto.removeValue(it,false);
                    break;
                }
            }
            setPlayerGun();
        if (game.ginterface.invent.binvent) game.ginterface.invent.openInvent();
        if (game.ginterface.invent.btorg) game.ginterface.invent.openTorg();
        }
    }
   public void setItem(Item it) {
        if(it.useslot==1||it.useslot==2){
            items.removeValue(it,false);
            // проверяем не одет ли уже предмет такого типа
        for (Item i : odeto) {
            if (i.useslot == it.useslot) {
                odeto.removeValue(i,false);
                items.add(i);
                break;
            }
        }
            odeto.add(it);
            if(it.weapon)setPlayerGun(it.tipitem,it.dopintcolvo);}
            else{
            items.removeValue(it,false);
            odeto.add(it);
            if(it.weapon)setPlayerGun(it.tipitem,it.dopintcolvo);
            //радиомитер
            if(it.tipitem==97)radiomiter=true;
        }
       // if(it.weapon)setPlayerGun(it.tipitem,it.dopintcolvo);
       setPlayerGun();
       if (game.ginterface.invent.binvent) game.ginterface.invent.openInvent();
       if (game.ginterface.invent.btorg) game.ginterface.invent.openTorg();
    }
    public void throwItem(String str[]){
        int iddi;
        for (int i=1;i<str.length;i++){
           iddi=Integer.parseInt(str[i]);
            remItem(iddi);
        }
    }
    @Override
    public  void setPlayerGun(int tipgun,int colvo) {
        for(Item it:odeto){
            if(it.tipitem==tipgun){gun=(Weapon) it;break;}
        }
        gun.dopintcolvo=colvo;
        //скорость анимации
        float pr=gun.perezaradka;
        for(Animation anim:perezanim){
            anim.setFrameDuration(pr/75f);
        }
        for(Animation anim:perezaanim){
            anim.setFrameDuration(pr/75f);
        }
    }

    public  void setPlayerGun() {
        game.ginterface.setItPoyas();
    }
// наверн можно убрать метод
   public void perezaradka(float delta) {
 if(gun!=null)gun.perezaradka(delta, this);
 else state = Util.WALKING;
        // граф отображение перезарядки
       if(gun.tipitem<21)hero=(TextureRegion)perezanim[nsnapravlenie-2].getKeyFrame(stateTime,true);
       else hero=(TextureRegion)perezaanim[nsnapravlenie-2].getKeyFrame(stateTime,true);
    }
    public void addHashMapQvest(String qvest) {
        String str[] = qvest.split(":");
        if (str.length > 1) {
            for (int i = 0; i < str.length; i += 2) {
                qvests.put(Integer.parseInt(str[i]), Integer.parseInt(str[i + 1]));
            }
        }
    }

    public enum Runtip {LEFT, RIGHT, UP, DOWN, STOP, UR, RD, DL, LU}
}
