package GameInterface.Pda;

import GameWorld.Game;
import Unit.Hero;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blackbirds.projectzone.GdxGame;

/**
 * Created by 777 on 21.03.2017.
 */
public class GlobalChat extends Group {
    Image fon,nazad;
    Label mapchat,globalchat;
    Label[] lchat = new Label[20];
    Label[] lname = new Label[20];
    public TextField textfieldchat;
    Group text;
    GdxGame game;
    TextButton ok;
    Group spisokpunktov;
    GlobalChat(final GdxGame game, final CorePda pda){
        setVisible(false);
        this.game=game;
        text=new Group();
        fon=new Image(new Texture("pda/fontext3.png"));
        fon.setPosition(70,59);
        nazad= new Image(new Texture("pda/nazad.png"));
        nazad.setPosition(690,62);
        nazad.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(isVisible())game.tout.sendMsg("1/2/2");
                setVisible(false);
                spisokpunktov.setVisible(false);
                pda.rabstol.setVisible(true);
                super.clicked(event, x, y);
            }
        });
        addActor(fon);
        spisokpunktov=new Group();
        spisokpunktov.setVisible(false);
        pda.addActor(spisokpunktov);
        mapchat = new Label("Чат локации", game.text.linvent);
        globalchat = new Label("Глобальный чат", game.text.linvent);
        mapchat.setPosition(100,391);
        globalchat.setPosition(100,370);
        spisokpunktov.addActor(mapchat);
        spisokpunktov.addActor(globalchat);
        spisokpunktov.addActor(nazad);
        mapchat.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVisible(true);
                spisokpunktov.setVisible(false);
                game.tout.sendMsg("1/2/1");
                spisokpunktov.removeActor(nazad);
                addActor(nazad);
                getStage().setKeyboardFocus(textfieldchat);
                for (int i = 0; i < lchat.length; i++) {
                    lchat[i].setText("");
                    lname[i].setText("");
                }
                super.clicked(event, x, y);
            }
        });
        globalchat.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVisible(true);
                spisokpunktov.setVisible(false);
                game.tout.sendMsg("1/2/4");
                spisokpunktov.removeActor(nazad);
                addActor(nazad);
                getStage().setKeyboardFocus(textfieldchat);
                for (int i = 0; i < lchat.length; i++) {
                    lchat[i].setText("");
                    lname[i].setText("");
                }
                super.clicked(event, x, y);
            }
        });
        float ych = 370;
        for (int i = 0; i < lname.length; i++) {
            lname[i] = new Label("fak", game.text.lgamechat);
            lname[i].setPosition(75, ych);
            ych -= lname[i].getHeight() - 3;
            addActor(lname[i]);
            lname[i].setText("");
            lname[i].setColor(Color.GOLD);
        }
        ych = 370;
        for (int i = 0; i < lchat.length; i++) {
            lchat[i] = new Label("fak", game.text.lgamechat);
            lchat[i].setPosition(75, ych);
            ych -= lchat[i].getHeight() - 3;
            addActor(lchat[i]);
            lchat[i].setText("");
        }
        ok = new TextButton("ok", game.text.ButtonStyle);
        ok.setSize(61, 30);
        ok.setPosition(670, 391);
        ok.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                sendmsg();
                super.clicked(event, x, y);
            }
        });
        addActor(ok);
        textfieldchat = new TextField("", game.text.tgamechat);
        textfieldchat.setSize(599, 30);
        textfieldchat.setPosition(71, 391);
        textfieldchat.setMaxLength(40);
        textfieldchat.setMessageText("");
        addActor(textfieldchat);
    }
    public void addMessage(String name, String s) {
        for (int i = lchat.length - 1; i >= 0; i--) {
            if (i != 0) {
                lname[i].setText(lname[i - 1].getText());
                lname[i].pack();
                lchat[i].setText(lchat[i - 1].getText());
                lchat[i].setX(lname[i].getWidth() + 80);
            } else {
                lchat[i].setText(s);
                lname[i].setText(name + " :");
                lname[i].pack();
                lchat[i].setX(lname[i].getWidth() + 80);
            }
        }
    }
    void run(){
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            sendmsg();
        }
    }
    public void sendmsg() {
            if (textfieldchat.getText().length() > 1)game.tout.sendMsg("1/2/3/" + textfieldchat.getText());
            textfieldchat.setText("");
            getStage().setKeyboardFocus(textfieldchat);

    }
}
