package GameInterface;

import GameWorld.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blackbirds.projectzone.GdxGame;

public class Chat {
    public boolean booleanchat;
    public TextField textfieldchat;
    Label[] lchat = new Label[6];
    Label[] lname = new Label[6];
    GdxGame game;
    Stage stage;
    Group gtext, gchat;
    TextButton ok;
    Image provoloka;

    public Chat(Stage stage,Group all, final GdxGame game) {
        this.game = game;
        this.stage = stage;
        gtext = new Group();
        gchat = new Group();
        float ych = 460;
        for (int i = 0; i < lname.length; i++) {
            lname[i] = new Label("fak", game.text.lgamechat);
            lname[i].setPosition(0, ych);
            ych -= lname[i].getHeight() - 3;
            gtext.addActor(lname[i]);
            lname[i].setText("");
            lname[i].setColor(Color.GOLD);
        }
        ych = 460;
        for (int i = 0; i < lchat.length; i++) {
            lchat[i] = new Label("fak", game.text.lgamechat);
            lchat[i].setPosition(0, ych);
            ych -= lchat[i].getHeight() - 3;
            gtext.addActor(lchat[i]);
            lchat[i].setText("");
        }
        all.addActor(gtext);
        textfieldchat = new TextField("", game.text.tgamechat);
        textfieldchat.setSize(700, 30);
        textfieldchat.setPosition(0, 450);
        textfieldchat.setMaxLength(60);
        textfieldchat.setMessageText("");
        textfieldchat.setOnlyFontChars(true);
        gchat.addActor(textfieldchat);
        provoloka = new Image(new Texture("provoloka.png"));
        provoloka.setPosition(0, 433);
        provoloka.setWidth(700);
        gchat.addActor(provoloka);
        ok = new TextButton("ok", game.text.ButtonStyle);
        ok.setSize(45, 33);
        ok.setPosition(705, 447);
        ok.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (booleanchat && textfieldchat.getText().length() > 1)
                    game.tout.sendMsg("1/2/0/" + textfieldchat.getText());
                onOffChat();
                super.clicked(event, x, y);
            }
        });
        gchat.addActor(ok);
        gchat.setVisible(false);
        all.addActor(gchat);
    }

    public void addMessage(String name, String s) {
        for (int i = lchat.length - 1; i >= 0; i--) {
            if (i != 0) {
                lname[i].setText(lname[i - 1].getText());
                lname[i].pack();
                lchat[i].setText(lchat[i - 1].getText());
                lchat[i].setX(lname[i].getWidth() + 10);
            } else {
                lchat[i].setText(s);
                lname[i].setText(name + " :");
                lname[i].pack();
                lchat[i].setX(lname[i].getWidth() + 10);
            }
        }
    }

    public void onOffChat() {
        if (booleanchat) {
            textfieldchat.setText("");
            Game.tochm=true;
            booleanchat = false;
            gchat.setVisible(false);
            textfieldchat.getOnscreenKeyboard().show(false);
            textfieldchat.setDisabled(true);
            ok.setDisabled(true);
            gtext.setPosition(0, 0);
        } else {
            booleanchat = true;
            gchat.setVisible(true);
            textfieldchat.getOnscreenKeyboard().show(true);
            textfieldchat.setDisabled(false);
            ok.setDisabled(false);
            stage.setKeyboardFocus(textfieldchat);
            gtext.setPosition(0, -32);
            Game.tochm=false;
            game.tout.sendMsg("1/22");
        }
    }
}
