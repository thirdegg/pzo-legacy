package GameInterface.Pda;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blackbirds.projectzone.GdxGame;

/**
 * Created by 777 on 27.02.2017.
 */
public class Settings extends Group {
    Label setcolortext,fon,fon1,fon2,fon3,fon4,fon5,black,green,red,blue,white;
    Group vfon,colort;
    Image nazad,font1,font2;
    Settings(GdxGame game, final CorePda pda){
        setVisible(false);
        nazad= new Image(new Texture("pda/nazad.png"));
        font1= new Image(new Texture("pda/fontext1.png"));
        font2= new Image(new Texture("pda/fontext2.png"));
        fon=new Label("Изменить фоновое\nизображение",game.text.linvent);
        fon1=new Label("Изображение 1",game.text.linvent);
        fon2=new Label("Изображение 2",game.text.linvent);
        fon3=new Label("Изображение 3",game.text.linvent);
        fon4=new Label("Изображение 4",game.text.linvent);
        fon5=new Label("Изображение 5",game.text.linvent);
        white=new Label("Белый",game.text.linvent);
        black=new Label("Черный",game.text.linvent);
        red=new Label("Красный",game.text.linvent);
        blue=new Label("Синий",game.text.linvent);
        green=new Label("Зеленый",game.text.linvent);
        setcolortext=new Label("Цвет текста",game.text.linvent);
        vfon=new Group();
        colort=new Group();
        vfon.addActor(fon1);
        vfon.addActor(fon2);
        vfon.addActor(fon3);
        vfon.addActor(fon4);
        vfon.addActor(fon5);
        vfon.setVisible(false);
        colort.addActor(white);
        colort.addActor(black);
        colort.addActor(green);
        colort.addActor(red);
        colort.addActor(blue);
        colort.setVisible(false);
        fon.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                vfon.setVisible(true);
                colort.setVisible(false);
                super.clicked(event, x, y);
            }
        });
        fon1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pda.switchFon(1);
                super.clicked(event, x, y);
            }
        });
        fon2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pda.switchFon(2);
                super.clicked(event, x, y);
            }
        });
        fon3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pda.switchFon(3);
                super.clicked(event, x, y);
            }
        });
        fon4.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pda.switchFon(4);
                super.clicked(event, x, y);
            }
        });
        fon5.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pda.switchFon(5);
                super.clicked(event, x, y);
            }
        });
        white.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pda.switchColor(Color.WHITE);
                super.clicked(event, x, y);
            }
        });
        black.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pda.switchColor(Color.BLACK);
                super.clicked(event, x, y);
            }
        });
        green.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pda.switchColor(Color.GREEN);
                super.clicked(event, x, y);
            }
        });
        red.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pda.switchColor(Color.RED);
                super.clicked(event, x, y);
            }
        });
        blue.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pda.switchColor(Color.BLUE);
                super.clicked(event, x, y);
            }
        });
        nazad.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVisible(false);
                pda.rabstol.setVisible(true);
                super.clicked(event, x, y);
            }
        });
        setcolortext.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                vfon.setVisible(false);
                colort.setVisible(true);
                super.clicked(event, x, y);
            }
        });
        font1.setPosition(87,90);
        font2.setPosition(317,90);
        nazad.setPosition(690,62);
        fon.setPosition(100,340);
        setcolortext.setPosition(100, fon.getY()-fon.getHeight());
        float y=358;float h=fon1.getHeight()+10;
        fon1.setPosition(330,y);
        fon2.setPosition(330,y-=h);
        fon3.setPosition(330,y-=h);
        fon4.setPosition(330,y-=h);
        fon5.setPosition(330,y-=h);
        y=358;h=white.getHeight()+10;
        white.setPosition(330,y);
        black.setPosition(330,y-=h);
        red.setPosition(330,y-=h);
        green.setPosition(330,y-=h);
        blue.setPosition(330,y-=h);
        addActor(font1);
        addActor(font2);
        addActor(nazad);
        addActor(fon);
        addActor(setcolortext);
        addActor(vfon);
        addActor(colort);
    }
}
