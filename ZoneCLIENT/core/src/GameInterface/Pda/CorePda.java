package GameInterface.Pda;

import GameWorld.Game;
import GameWorld.WorldState;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blackbirds.projectzone.GdxGame;

import java.util.StringTokenizer;

/**
 * Created by 777 on 27.02.2017.
 */
public class CorePda extends Group {
    Image pda,fon,imessage,isset,iout,igame,inastr,imap,ihelp,izad,idonat,igroup;
    Group rabstol;
    Settings settings;
    PMap map;
    public GlobalChat gchat;
    Qvests qvests;
    Help hhelp;
    public Klans klans;
    public Lichka lichka;
    GdxGame game;
    Label time,karta,zad,lgame,help,donat,nastr,message,sset,group;
    public CorePda(final Group on, final GdxGame game){
        setVisible(false);
        this.game=game;
        rabstol =new Group();
        settings=new Settings(game,this);
        map=new PMap(game,this);
        qvests= new Qvests(game,this);
        hhelp= new Help(game,this);
        klans = new Klans(game,this);
        lichka= new Lichka(game,this);
        pda=new Image(new Texture("pda/pda.png"));
        fon=new Image(new Texture("pda/fon1.jpg"));
        imessage=new Image(new Texture("pda/imessage.png"));
        isset=new Image(new Texture("pda/isset.png"));
        iout=new Image(new Texture("pda/out.png"));
        igame=new Image(new Texture("pda/igame.png"));
        inastr=new Image(new Texture("pda/inastr.png"));
        ihelp=new Image(new Texture("pda/ihelp.png"));
        izad=new Image(new Texture("pda/izad.png"));
        idonat=new Image(new Texture("pda/idonat.png"));
        igroup=new Image(new Texture("pda/isset.png"));
        imap=new Image(new Texture("pda/imap.png"));
        time = new Label("10:10", game.text.linvent);
        karta = new Label("карта", game.text.lspda);
        message = new Label("почта", game.text.lspda);
        zad = new Label("задания", game.text.lspda);
        lgame = new Label("игры", game.text.lspda);
        nastr = new Label("настройки\nпда", game.text.lspda);
        nastr.setAlignment(1);
        donat = new Label("донат", game.text.lspda);
        help = new Label("помощь", game.text.lspda);
        sset = new Label("чат", game.text.lspda);
        sset.setAlignment(1);
        group = new Label("группи-\nровки", game.text.lspda);
        group.setAlignment(1);

        iout.setPosition(690,62);
        pda.setPosition(25,25);
        fon.setPosition(70,59);
        float x=77;int xx=83;
        imap.setPosition(x,355);
        izad.setPosition(x+=xx,355);
        ihelp.setPosition(x+=xx,355);
        isset.setPosition(x+=xx,355);
        imessage.setPosition(x+=xx,355);
        igame.setPosition(x+=xx,355);
        idonat.setPosition(x+=xx,355);
        inastr.setPosition(x+=xx,355);
        x=77;
        igroup.setPosition(x,265);


        time.setPosition(75,64);

        setPosText(imessage,message);
        setPosText(imap,karta);
        setPosText(ihelp,help);
        setPosText(isset,sset);
        setPosText(igame,lgame);
        setPosText(idonat,donat);
        setPosText(inastr,nastr);
        setPosText(izad,zad);
        setPosText(igroup,group);

        iout.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
             setVisible(false);
             on.setVisible(true);
                Game.tochm=true;
                super.clicked(event, x, y);
            }
        });
        inastr.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                rabstol.setVisible(false);
                settings.setVisible(true);
                super.clicked(event, x, y);
            }
        });
        ihelp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                rabstol.setVisible(false);
                hhelp.setVisible(true);
                super.clicked(event, x, y);
            }
        });
        izad.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                rabstol.setVisible(false);
                qvests.setVisible(true);
                qvests.setNameQvests();
                super.clicked(event, x, y);
            }
        });
        imap.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                rabstol.setVisible(false);
                map.setVisible(true);
                map.setPos();
                super.clicked(event, x, y);
            }
        });
        isset.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                rabstol.setVisible(false);
                gchat.spisokpunktov.setVisible(true);
                super.clicked(event, x, y);
            }
        });
        imessage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                rabstol.setVisible(false);
                lichka.setVisible(true);
                lichka.groupspisok.setVisible(true);
                super.clicked(event, x, y);
            }
        });
        igroup.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                rabstol.setVisible(false);
                klans.setVisible(true);
                super.clicked(event, x, y);
            }
        });
        addActor(pda);
        addActor(fon);
        rabstol.addActor(iout);
        rabstol.addActor(imap);
        rabstol.addActor(izad);
        rabstol.addActor(ihelp);
        rabstol.addActor(isset);
        rabstol.addActor(igame);
        rabstol.addActor(imessage);
        rabstol.addActor(idonat);
        rabstol.addActor(inastr);
        rabstol.addActor(igroup);
        rabstol.addActor(time);
        rabstol.addActor(karta);
        rabstol.addActor(message);
        rabstol.addActor(lgame);
        rabstol.addActor(nastr);
        rabstol.addActor(sset);
        rabstol.addActor(group);
        rabstol.addActor(donat);
        rabstol.addActor(help);
        rabstol.addActor(zad);
        addActor(lichka);
        addActor(rabstol);
        addActor(settings);
        addActor(map);
        addActor(qvests);
        addActor(hhelp);
        addActor(klans);
        //отдельно потому как фон перекрывает список
        gchat=new GlobalChat(game,this);
        addActor(gchat);
    }
    public void run(){
        time.setText((WorldState.hours < 10 ? "0" + WorldState.hours : WorldState.hours) + ":" + (WorldState.minutes < 10 ? "0" + WorldState.minutes : WorldState.minutes));
        if(gchat.isVisible())gchat.run();
    }
    void switchFon(int tipf){
        Image fon2= new Image(new Texture("pda/fon"+tipf+".jpg"));
        fon.setDrawable(fon2.getDrawable());
    }
    void switchColor(Color ncolor){
        game.text.linvent.fontColor=ncolor;
        game.text.lspda.fontColor=ncolor;
    }
    void setPosText(Image img,Label l){
        float simg=img.getX()+img.getWidth()/2;
        l.setPosition(simg-l.getWidth()/2,img.getY()-l.getHeight());
    }
    String perenosSlov(StringTokenizer str,int dlina){
        StringBuilder text=new StringBuilder();
        int dlinnastroki=0;
        while(str.hasMoreTokens()){
            String ts=str.nextToken();
            //если длина больше чем надо добавляем перенос и пропускаем пробел
            if(dlinnastroki>dlina&&ts.equals(" ")){dlinnastroki=0;text.append("\n");}else{
                dlinnastroki+=ts.length();
                text.append(ts);}
        }
        return text.toString();
    }
}
