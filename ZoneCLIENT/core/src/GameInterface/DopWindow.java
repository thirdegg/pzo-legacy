package GameInterface;

import GameWorld.Game;
import InventItem.Item;
import Unit.Hero;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.blackbirds.projectzone.GdxGame;
import util.Util;

public class DopWindow {
    final Hero hero;
    Group gwind, buttons;
    Label namewind, textwind, cena,cenaopit;
    Image imgwind, hwindow, out;
    Button isp, vibr, snyat, sell, buy,odet,izuchit;
    Item activitem;
    GdxGame game;
    int tipskil;
long idtorg;
    public DopWindow(final GdxGame game, Hero heroo) {
        gwind = new Group();
        buttons = new Group();
        hero = heroo;
        gwind.setVisible(false);
        buttons.setVisible(false);
        this.game = game;
        hwindow = new Image(new Texture("helpwindow.png"));
     //   hwindow.setSize(525, 280);
        hwindow.setPosition(150, 100);
        gwind.addActor(hwindow);
        imgwind = new Image();
        imgwind.setSize(50, 50);
        imgwind.setPosition(165, 315);
        gwind.addActor(imgwind);
        namewind = new Label("Аптечка", game.text.linvent);
        namewind.setPosition(400 - namewind.getWidth() / 2, 335);
        gwind.addActor(namewind);
        textwind = new Label("Используется для повышения здоровья.\nЭффект: здоровье +40", game.text.linvent);
        gwind.addActor(textwind);
        cena = new Label("Цена покупки - 100р", game.text.linvent);
        cena.setPosition(265, 180);
        cena.setVisible(false);
        gwind.addActor(cena);
        cenaopit = new Label("Требуется 200 очков опыта.", game.text.linvent);
        cenaopit.setPosition(265, 180);
        cenaopit.setVisible(false);
        gwind.addActor(cenaopit);
        out = new Image(new Texture("windout.png"));
        out.setSize(30, 30);
        out.setPosition(625, 335);
        out.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gwind.setVisible(false);
                buttons.setVisible(false);
                super.clicked(event, x, y);
            }
        });
        gwind.addActor(out);

        isp = new TextButton("Использовать", game.text.ButtonStyle);
        isp.setSize(140, 30);
        isp.setPosition(165, 115);
        isp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //сделать на сервере
                if(hero.state!=Util.DIED){
                if (activitem.tipitem == 15 && (hero.idmap==2||hero.idmap==11)) {
                    Ginterface.setText("Здесь нельзя использовать этот предмет", 3000);
                    return;
                }
                game.tout.sendMsg("1/3/1/" + activitem.id);
                gwind.setVisible(false);
                buttons.setVisible(false);
                hero.useItem(activitem.id);
                Game.tochm=true;}
                super.clicked(event, x, y);
            }
        });

        odet = new TextButton("Экипировать", game.text.ButtonStyle);
        odet.setSize(130, 30);
        odet.setPosition(445, 115);
        odet.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //сделать на сервере
                if(hero.state!=Util.DIED){
                    if(hero.odeto.size<hero.colvoslotov){
                    game.tout.sendMsg("1/3/2/" + activitem.id);
                    gwind.setVisible(false);
                    buttons.setVisible(false);
                    hero.setItem(activitem);
                    Game.tochm=true;}
                    else Ginterface.setText("все слоты заняты",3000);}
                super.clicked(event, x, y);
            }
        });
        izuchit = new TextButton("Изучить", game.text.ButtonStyle);
        izuchit.setSize(140, 30);
        izuchit.setPosition(165, 115);
        izuchit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.tout.sendMsg("1/20/1/"+tipskil);
                super.clicked(event, x, y);
            }
        });
        vibr = new TextButton("Выбросить", game.text.ButtonStyle);
        vibr.setSize(140, 30);
        vibr.setPosition(305, 115);
        vibr.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(hero.state!=Util.DIED){
                game.tout.sendMsg("1/14/" + activitem.id);
                hero.remItem(activitem.id);
                gwind.setVisible(false);
                buttons.setVisible(false);
                Game.tochm=true;}
                super.clicked(event, x, y);
            }
        });
        snyat = new TextButton("В рюкзак", game.text.ButtonStyle);
        snyat.setSize(140, 30);
        snyat.setPosition(165, 115);
        snyat.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (hero.getMesto()&&hero.state!= Util.DIED) {
                    game.tout.sendMsg("1/5/" + activitem.id);
                    gwind.setVisible(false);
                    buttons.setVisible(false);
                    hero.Snyat(activitem.id);
                } else {
                    Ginterface.setText("нет места", 3000);
                }
                super.clicked(event, x, y);
            }
        });
        sell = new TextButton("Продать", game.text.ButtonStyle);
        sell.setSize(140, 30);
        sell.setPosition(165, 115);
        sell.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(hero.state!=Util.DIED){
                game.tout.sendMsg("1/10/2/" + idtorg+"/"+activitem.id);
                gwind.setVisible(false);
                buttons.setVisible(false);}
                super.clicked(event, x, y);
            }
        });
        buy = new TextButton("Купить", game.text.ButtonStyle);
        buy.setSize(140, 30);
        buy.setPosition(165, 115);
        buy.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(hero.state!=Util.DIED){
                if (hero.getMesto()&&hero.many>=activitem.cena) {
                    hero.many-=activitem.cena;
                    game.tout.sendMsg("1/10/1/"+idtorg+"/"+activitem.tipitem);
                    gwind.setVisible(false);
                    buttons.setVisible(false);
                } else {
                    Ginterface.setText("нет места или не хватает денег", 3000);
                }}
                super.clicked(event, x, y);
            }
        });
    }

    void setWindow(Item it, Drawable drawable, int tipk) {
        gwind.setVisible(true);
        buttons.setVisible(true);
        buttons.clear();
        cenaopit.setVisible(false);
        namewind.setText(it.name);
        textwind.setText(it.opisanie);

        textwind.pack();
        textwind.setPosition(165, 315 - textwind.getHeight());

        if (tipk == 4) {
            cena.setText("Цена покупки - " + it.cena + "р");
            cena.setVisible(true);
            cena.setColor(Color.VIOLET);
        }
        if (tipk == 3) {
            cena.setText("Цена продажи - " + it.cena/2 + "р");
            cena.setVisible(true);
            cena.setColor(Color.VIOLET);
        }

        if (tipk != 4 && tipk != 3) cena.setVisible(false);

        switch (tipk) {
            case 1:
                if(it.use&&!it.weapon&&Game.hero.inventcanuse)buttons.addActor(isp);
                if(it.vibrosit)buttons.addActor(vibr);
                if(Game.hero.inventcanuse)buttons.addActor(odet);
                break;
            case 2:
                buttons.addActor(snyat);
                break;
            case 3:
                if(it.vibrosit)buttons.addActor(sell);
                break;
            case 4:
                buttons.addActor(buy);
                break;
        }

        imgwind.setDrawable(drawable);
        activitem=it;
    }
    void setWindowSkil(String name,String opisanie, Drawable drawable,int colvoop,int tipskil) {
        cena.setVisible(false);
        this.tipskil=tipskil;
        cenaopit.setText("Требуется "+colvoop+" очков опыта.");
        cenaopit.setVisible(true);
        gwind.setVisible(true);
        buttons.setVisible(true);
        buttons.clear();
        if(!hero.spisskills.contains(tipskil))buttons.addActor(izuchit);
        namewind.setText(name);
        textwind.setText(opisanie);
        textwind.pack();
        textwind.setPosition(165, 315 - textwind.getHeight());
        imgwind.setDrawable(drawable);
    }


}
