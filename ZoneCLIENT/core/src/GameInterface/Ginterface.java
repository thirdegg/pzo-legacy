package GameInterface;

import GameInterface.Pda.CorePda;
import GameWorld.Game;
import GameWorld.WorldState;
import InventItem.Item;
import Qvests.SystemQvest;
import Unit.Hero;
import Unit.Hero.Runtip;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.blackbirds.projectzone.GdxGame;
import util.Util;

public class Ginterface {
    //public Group servmsg;Image fonsm;Label textsm;
    static long timetext;
    static boolean btext;
    static Label text;
    public Stage stage;
    public Chat chat;
    public Inventar invent;
    public SystemQvest systemqvest;
    public Dialog dialog;
    public Group out,privetstvie,gaddimg,groupname,allgroup;
    public Button vih,sitting;
    GdxGame game;
    Image kchat, kinvent, kpda,imgaddit,bolt;
    public Image activit,privibros,priv,privskill,imgeffrad,imganom,message;
    Label colvop,doptext,rad;
    Label vihod;
    TextButton da, net;
    Image outfon;
    public Label textprivetstvie,herodead,notuseitem;
    TextButton ok;
    Image fonprivet;
    int xaddit=150,timewind;boolean addrun,onoff;
    Hero hero;
    public CorePda pda;
    int oldclvop;
    Touchpad touchpad;
    Image itusepoyas[]=new Image[8];
    public ObmenSystem obmenSystem;
    int timeprvib;
    int radskipframe;
    public Label stateinternet;
    Image tochimg;
    public Ginterface(final GdxGame game,final Game ggame) {
        this.game = game;
        stage = new Stage(new StretchViewport(800, 480));
        Gdx.input.setInputProcessor(stage);
        //все добавляются к аллгроуп эт для того что бы все скрыыть открыв пда
        allgroup=new Group();
//перекрестие управления
     /*   if (Gdx.app.getType() == ApplicationType.Android) {
            Image kontrol = new Image(new Texture("kontrol.png"));
            kontrol.setPosition(0, 0);
            kontrol.setSize(250, 250);
            allgroup.addActor(kontrol);
        }*/

        this.hero=Game.hero;
        systemqvest = new SystemQvest();
        pda=new CorePda(allgroup,game);
        out = new Group();
        privetstvie = new Group();
        gaddimg=new Group();
        groupname=new Group();
        //night
        stage.addActor(WorldState.imgnight);

        tochimg = new Image();
        tochimg.setSize(800, 480);
        stage.addActor(tochimg);
        tochimg.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
                if( Game.tochm){
                    ggame.tochMap(true);
                }
                return super.touchDown(event, x, y, pointer, button);
            }
        });
     /*   tochimg.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button){
                if( Game.tochm){
                    ggame.tochMap(false);
                }
                super.touchUp(event, x, y, pointer, button);
            }
        });*/

        obmenSystem=new ObmenSystem(this);
        fonprivet = new Image(new Texture("helpwindow.png"));
        fonprivet.setSize(650, 350);
        fonprivet.setPosition(75, 65);
        privetstvie.addActor(fonprivet);
        ok = new TextButton("Ok", game.text.ButtonStyle);
        ok.setSize(50, 30);
        ok.setPosition(375, 130);
        ok.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                privetstvie.setVisible(false);

                super.clicked(event, x, y);
            }
        });
        privetstvie.addActor(ok);
        textprivetstvie = new Label("Приветствую тебя сталкер!\n Перед тобой альфа версия игры, поэтому нестоит\n рассчитывать на полноценный геймплей.\n"
                + "Так же будь готов к багам, вылетам приложения,\n и падениям сервера. О найденных багах просьба сообщать \nв специальную тему в группе проекта.\n"
                + "После теста все учетные записи будут обнулены.\n"
                + "Для получения помощи откройте пда и нажмите на пункт помощь.\n"
                + "Приятной игры.", game.text.linvent);
        textprivetstvie.setAlignment(1);
        textprivetstvie.setPosition(130, 230);
        privetstvie.addActor(textprivetstvie);
        allgroup.addActor(privetstvie);
        herodead=new Label("Вы погибли возрождение через 10 секунд.", game.text.linvent);
        herodead.setPosition(250,240);
        herodead.setColor(1,0,0,1);
        allgroup.addActor(herodead);
        herodead.setVisible(false);
        outfon = new Image(new Texture("helpwindow.png"));
        outfon.setSize(250, 150);
        outfon.setPosition(275, 250);
        out.addActor(outfon);
        vihod = new Label("Вы действительно хотите\nвыйти ?", game.text.linvent);
        vihod.setAlignment(1);
        vihod.setPosition(290, 350);
        out.addActor(vihod);
        da = new TextButton("Да", game.text.ButtonStyle);
        da.setSize(50, 30);
        da.setPosition(310, 290);
        da.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
                super.clicked(event, x, y);
            }
        });
        out.addActor(da);
        net = new TextButton("Нет", game.text.ButtonStyle);
        net.setSize(50, 30);
        net.setPosition(440, 290);
        net.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                out.setVisible(false);

                super.clicked(event, x, y);
            }
        });
        out.addActor(net);
        out.setVisible(false);
        allgroup.addActor(out);
        chat = new Chat(stage,allgroup,game);
        invent = new Inventar(allgroup, hero, game);

        int zx=750;
        for(int i=0;i<itusepoyas.length;i++){
            itusepoyas[i]=new Image();
            itusepoyas[i].setSize(50, 50);
            itusepoyas[i].setPosition(zx, 0);
            itusepoyas[i].setVisible(false);
            zx-=65;
            allgroup.addActor(itusepoyas[i]);
        }



        bolt = new Image(new Texture("ibolt.png"));
        bolt.setSize(50, 50);
        bolt.setPosition(750, 50);
        bolt.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            activit.setVisible(true);
            activit.setPosition(750,50);
                Game.rbolt=true;
                super.clicked(event, x, y);
            }
        });
        allgroup.addActor(bolt);
        activit = new Image(new Texture("activit.png"));
        activit.setSize(50, 50);
        activit.setPosition(750, 50);
        activit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(activit.getY()==50){
                Game.rbolt=false;}
                hero.itstate=0;
                hero.activitemid=0;
                activit.setVisible(false);
                super.clicked(event, x, y);
            }
        });
        activit.setVisible(false);
        allgroup.addActor(activit);
        stateinternet=new Label("Сеть : нормально", game.text.linvent);
        stateinternet.setPosition(0,0);
        allgroup.addActor(stateinternet);
        priv = new Image(new Texture("privibros.png"));
        privskill = new Image(new Texture("privibrosskil.png"));
        privibros = new Image(priv.getDrawable());
        privibros.setPosition(650, 150);
        privibros.setVisible(false);
        imgeffrad = new Image(new Texture("imgeffrad.png"));
        imgeffrad.setPosition(758,300);
        imgeffrad.setVisible(false);
        allgroup.addActor(imgeffrad);
        imganom = new Image(new Texture("anomalydanger.png"));
        imganom.setPosition(758,258);
        imganom.setVisible(false);
        allgroup.addActor(imganom);
        rad = new Label("0", game.text.linvent);
        rad.setColor(Color.GREEN);
        rad.setPosition(766,310);
        rad.setVisible(false);
        allgroup.addActor(rad);
        allgroup.addActor(privibros);
        kchat = new Image(new Texture("imgchat.png"));
        kchat.setSize(50, 50);
        kchat.setPosition(755, 435);
        allgroup.addActor(kchat);
        kchat.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hero.runtip = Runtip.STOP;
                chat.onOffChat();
                super.clicked(event, x, y);
            }
        });
        kinvent = new Image(new Texture("imginvent.png"));
        kinvent.setSize(50, 50);
        kinvent.setPosition(755, 390);
        allgroup.addActor(kinvent);
        kinvent.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hero.runtip = Runtip.STOP;
                if (invent.binvent || invent.btorg) invent.closeInvent();
                else invent.openInvent();
                super.clicked(event, x, y);
            }
        });
        kpda = new Image(new Texture("imghelp.png"));
        kpda.setSize(50, 50);
        kpda.setPosition(755, 345);
        allgroup.addActor(kpda);
        kpda.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pda.setVisible(true);
                hero.runtip = Runtip.STOP;
                allgroup.setVisible(false);
                Game.tochm=false;
                message.setVisible(false);
                super.clicked(event, x, y);
            }
        });
        message = new Image(new Texture("pda/imessage.png"));
        message.setSize(31, 30);
        message.setPosition(763, 355);
        allgroup.addActor(message);
        message.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pda.setVisible(true);
                hero.runtip = Runtip.STOP;
                allgroup.setVisible(false);
                Game.tochm=false;
                message.setVisible(false);
                super.clicked(event, x, y);
            }
        });
        message.setVisible(false);
        colvop = new Label("1", game.text.linvent);
        colvop.setPosition(800 - colvop.getWidth(), 0);
        colvop.setVisible(false);
        allgroup.addActor(colvop);
        //дополнительный текст
        doptext = new Label("кв 1-", game.text.linvent);
        doptext.setPosition(50,300);
        allgroup.addActor(doptext);
        doptext.setVisible(false);
        //
        text = new Label("text", game.text.linvent);
        text.setVisible(false);
        text.setColor(Color.RED);

        vih = new TextButton("Выйти", game.text.ButtonStyle);
        vih.setSize(140, 30);
        vih.setPosition(485,5);
        vih.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.tout.sendMsg("1/7/");
                super.clicked(event, x, y);
            }
        });
        vih.setVisible(false);
        allgroup.addActor(vih);

        sitting = new TextButton("Присесть", game.text.ButtonStyle);
        sitting.setSize(140, 30);
        sitting.setPosition(485,5);
        sitting.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.tout.sendMsg("1/15/");
                super.clicked(event, x, y);
            }
        });
        sitting.setVisible(false);
        allgroup.addActor(sitting);
        //addItemWindow
        Image gait = new Image(new Texture("additfon.png"));
        gait.setSize(150, 50);
        gait.setPosition(650,200);
        gaddimg.addActor(gait);
        Label addtext = new Label("Добавлен\nпредмет", game.text.linvent);
        addtext.setPosition(710,204);
        gaddimg.addActor(addtext);
        imgaddit=new Image();
        imgaddit.setSize(50, 50);
        imgaddit.setPosition(655, 200);
        gaddimg.addActor(imgaddit);
        imgaddit.setDrawable(invent.imgitems.tiptoitem.get(13).getDrawable());
        allgroup.addActor(gaddimg);
        gaddimg.setPosition(xaddit,0);
        if(game.android){Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
            touchpad = new Touchpad(20, skin);
            touchpad.setBounds(30, 30, 150, 150);
            stage.addActor(touchpad);
            touchpad.getStyle().background=null;}
        stage.addActor(allgroup);
        stage.addActor(pda);
        dialog=new Dialog(game,this);
        allgroup.addActor(dialog);
        allgroup.addActor(obmenSystem);
        allgroup.addActor(obmenSystem.groupzapros);
        notuseitem=new Label("предметы недоступны", game.text.linvent);
        notuseitem.setPosition(450,100);
        notuseitem.setColor(1,0,0,1);
        allgroup.addActor(notuseitem);
        notuseitem.setVisible(false);
        stage.addActor(text);
    }

    public static void setText(String textt, int time) {
        if (!btext) {
            text.setText(textt);
            text.setVisible(true);
            text.pack();
            text.setPosition(400 - text.getWidth() / 2, 240 - text.getHeight() / 2);
            timetext = System.currentTimeMillis() + time;
            btext = true;
        }
        if (System.currentTimeMillis() > timetext) {
            text.setVisible(false);
            btext = false;
        }
    }
public void tochkontrol(){
    if(!dialog.isVisible()&&!pda.isVisible()) {
        float kpx = touchpad.getKnobPercentX();
        float kpy = touchpad.getKnobPercentY();
        if (kpy > 0.5) {
            if (kpx < -0.5) {
                hero.runtip = Runtip.LU;
                return;
            }
            if (kpx > -0.5 && kpx < 0.5) {
                hero.runtip = Runtip.UP;
                return;
            }
            if (kpx > 0.5) {
                hero.runtip = Runtip.UR;
                return;
            }
        }
        if (kpy < 0.5 && kpy > -0.5) {
            if (kpx < -0.5) {
                hero.runtip = Runtip.LEFT;
                return;
            }
            if (kpx > 0.5) {
                hero.runtip = Runtip.RIGHT;
                return;
            }
        }
        if (kpy < -0.5) {
            if (kpx < -0.5) {
                hero.runtip = Runtip.DL;
                return;
            }
            if (kpx > -0.5 && kpx < 0.5) {
                hero.runtip = Runtip.DOWN;
                return;
            }
            if (kpx > 0.5) {
                hero.runtip = Runtip.RD;
                return;
            }
        }
        hero.runtip = Runtip.STOP;

        if (Gdx.input.isKeyJustPressed(Keys.BACK)) {
            if (invent.binvent || invent.btorg) {
                invent.closeInvent();
            } else {
                if (out.isVisible()) out.setVisible(false);
                else out.setVisible(true);
            }
        }
    }
}
    public void run() {
        stage.draw();
        if(game.android)tochkontrol();
        //doptext.setText("h"+hero.ukostra);
        if(hero.state==Util.DIED&&!herodead.isVisible())herodead.setVisible(true);
        if(hero.state!=Util.DIED&&herodead.isVisible())herodead.setVisible(false);
        if (hero.gun != null) {
            //устанавливаем кол-во патронов
            if(oldclvop!=hero.gun.dopintcolvo){
            colvop.setText("" + hero.gun.dopintcolvo);
            colvop.pack();
            colvop.setPosition(800 - colvop.getWidth(), 0);
            oldclvop=hero.gun.dopintcolvo;}
        }
        if(pda.isVisible())pda.run();
        //кнопка у костра присесть
        if(hero.ukostra&&!sitting.isVisible()&&hero.state!=Util.VDOME)sitting.setVisible(true);
        if(!hero.ukostra&&sitting.isVisible())sitting.setVisible(false);
        if(hero.state==Util.SITTING&&sitting.isVisible()||hero.state==Util.VDOME||hero.state==Util.DIED)sitting.setVisible(false);
        if (!pda.isVisible()&&Gdx.input.isKeyJustPressed(Keys.ENTER)) {
            if (chat.booleanchat && chat.textfieldchat.getText().length() > 1)
                game.tout.sendMsg("1/2/0/" + chat.textfieldchat.getText());
            hero.runtip = Runtip.STOP;
            chat.onOffChat();
        }
        invent.run();
        if (text.isVisible()) setText(null, 0);
        // if(Game.tochbutton){text.setVisible(false);btext=false;Game.tochbutton=false;}}
        if(addrun)movItWindow();
        //отсчет времени недоступности предметов
        if(invent.binvent&&!hero.inventcanuse&&!notuseitem.isVisible())notuseitem.setVisible(true);
        if(invent.binvent&&!hero.inventcanuse)notuseitem.setText("предметы недоступны "+(200-(int)hero.inventtimecanuse));
        if(notuseitem.isVisible()&&(!invent.binvent||hero.inventcanuse))notuseitem.setVisible(false);

        if(privibros.isVisible()){
            timeprvib++;
            if(timeprvib>350){timeprvib=0;privibros.setVisible(false);}
        }

        if(hero.radiomiter&&hero.radiation>0){
            if(radskipframe==20){
            rad.setText(""+ Util.round(hero.radiation,2));radskipframe=0;
            if(!imgeffrad.isVisible()){imgeffrad.setVisible(true);rad.setVisible(true);}}
        radskipframe++;
        }
        else{if(imgeffrad.isVisible()){imgeffrad.setVisible(false);rad.setVisible(false);}}
    }

    public void setItPoyas() {
    int i=0;
    for(Image img:itusepoyas){
        img.setVisible(false);
    }
        for(final Item it:hero.odeto){
        if(it.use){itusepoyas[i].setDrawable(invent.imgitems.tiptoitem.get(it.tipitem).getDrawable());
            itusepoyas[i].setVisible(true);
            itusepoyas[i].clearListeners();
            final int ii=i;
            itusepoyas[i].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    //сделать на сервере
                    if(hero.state!=Util.DIED){
                        if (it.tipitem == 15 && (hero.idmap==2||hero.idmap==11)) {
                            Ginterface.setText("Здесь нельзя использовать этот предмет", 3000);
                            return;
                        }
                        if(it.fastuse){
                        game.tout.sendMsg("1/3/1/" + it.id);
                    hero.useItem(it.id);}
                    else {
                            if(activit.getY()==50){Game.rbolt=false;}
                            activit.setVisible(true);activit.setPosition(itusepoyas[ii].getX(),itusepoyas[ii].getY());
                            if(it.weapon)hero.itstate=1;
                            else hero.itstate=2;
                            hero.activitemid=it.id;
                        }
                    }
                    super.clicked(event, x, y);
                }
            }); i++;
           }}
    }

public void setItemWindow(int tipi){
imgaddit.setDrawable(invent.imgitems.tiptoitem.get(tipi).getDrawable());
    xaddit=150;
    gaddimg.setPosition(xaddit,0);
    addrun=true;onoff=true;timewind=0;
}
private void movItWindow(){
    timewind++;
    if(onoff)if(xaddit>0)xaddit-=6;else onoff=false;
    if(!onoff&&timewind>90)if(xaddit<150)xaddit+=6;else addrun=false;
    gaddimg.setPosition(xaddit,0);
}
}
