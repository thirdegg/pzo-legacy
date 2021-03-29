package GameInterface.Pda;

import GameWorld.Game;
import Unit.Hero;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blackbirds.projectzone.GdxGame;

/**
 * Created by 777 on 28.02.2017.
 */
public class PMap extends Group {
    Image map,nazad,ikonka,ilocalmap;
    Label xy,kv,globalmap,localmap;
    PMap(GdxGame game,final CorePda pda){
        setVisible(false);
        ikonka=new Image(new Texture("pda/pxy.png"));
        map=new Image(new Texture("pda/map.png"));
        map.setPosition(70,59);
        nazad= new Image(new Texture("pda/nazad.png"));
        nazad.setPosition(690,62);
        nazad.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVisible(false);
                pda.rabstol.setVisible(true);
                super.clicked(event, x, y);
            }
        });
        addActor(map);
        xy = new Label("x-1000 y-1000", game.text.linvent);
        xy.setPosition(80,350);
        addActor(xy);
        kv = new Label("кв 1-", game.text.linvent);
        kv.setPosition(80, 320);
        globalmap = new Label("Глобальная карта", game.text.linvent);
        globalmap.setPosition(80,220);
        globalmap.setColor(Color.GREEN);
        globalmap.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setPos();
                super.clicked(event, x, y);
            }
        });
        localmap = new Label("Локальная карта", game.text.linvent);
        localmap.setPosition(80, 190);
        localmap.setColor(Color.BLUE);
        localmap.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ilocalmap.setVisible(true);
                localmap.setColor(Color.GREEN);
                globalmap.setColor(Color.BLUE);
                setLocalMap();
                super.clicked(event, x, y);
            }
        });
        ilocalmap=new Image(new Texture("pda/map0.jpg"));
        ilocalmap.setPosition(378,79);
        ilocalmap.setVisible(false);
        addActor(ilocalmap);
        addActor(xy);
        addActor(kv);
        addActor(ikonka);
        addActor(localmap);
        addActor(globalmap);
        addActor(nazad);
        //308 22
    }
    void  setPos(){
        xy.setText("Ваши координаты: x-" + (int) Game.hero.x + " y-" + (int) Game.hero.y);
        kv.setText("Вы находитесь в квадрате-"+Game.hero.idmap);
        //вычисляем местоположение игрока на карте
        float px=369.5f+Game.hero.x/25;
        float py=256+71.5f+Game.hero.y/25;
        int ostx=Game.hero.idmap%5;
        int osty=0;
        if(Game.hero.idmap>4){osty=Game.hero.idmap/5;
        py-=osty*64;}
        if(ostx>0)px+=ostx*64;
        ikonka.setPosition(px,py);
        ilocalmap.setVisible(false);
        globalmap.setColor(Color.GREEN);
        localmap.setColor(Color.BLUE);
    }
    void setLocalMap(){
        ilocalmap.setDrawable(new Image(new Texture("pda/map"+Game.hero.idmap+".jpg")).getDrawable());
        float px=378+Game.hero.x/5;
        float py=79+Game.hero.y/5;
        ikonka.setPosition(px,py);
    }
}
