package com.blackbirds.projectzone;

import GameWorld.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import util.Sounds;
import util.Util;

public class ConnectKorvin implements Screen {
    Label lconnect;
    Image fon;
    GdxGame game;
    boolean tmp, waitotvet;
    TextButton nazad;
    int tmpcount;
    String namezapros="aut:korvin:123:"+ Util.gv;
    private Stage stage;

    public ConnectKorvin(GdxGame game) {
        this.game = game;
        stage = new Stage(new StretchViewport(800, 480));
        fon = new Image(new Texture("menufon.jpg"));
        fon.setSize(800, 480);
        stage.addActor(fon);
        lconnect = new Label("Идет соединение с сервером", game.text.ls);
        lconnect.setAlignment(2);
        lconnect.setPosition(150, 300);
        stage.addActor(lconnect);
        nazad = new TextButton("выход", game.text.ButtonStyle);
        nazad.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
                super.clicked(event, x, y);
            }
        });
        nazad.setPosition(350, 200);
        nazad.setSize(100, 30);
        nazad.setVisible(false);
        stage.addActor(nazad);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        stage.draw();
        if (!tmp && tmpcount == 50) {
            boolean connect = game.createConnect();
            if (connect) {
                game.tout.sendMsg(namezapros);
                waitotvet = true;
            } else {
                lconnect.setText("Сервер не отвечает или\nНет соединения с сетью internet");
                nazad.setVisible(true);
            }
            tmp = true;
        }
        if (tmpcount != 50) tmpcount++;
        if (waitotvet) {
            if (!game.tin.sQueue.isEmpty()) {
                String str = game.tin.sQueue.poll();
                if (str.charAt(0) == 'h'){new Sounds();new Util();
                    Sounds.playFonMusic(); game.setScreen(new Game(game, str));}
                if (str.charAt(0) == 'Т') {if(namezapros.equals("aut:korvin:123:"+Util.gv)){namezapros="aut:zxc:123:"+Util.gv;game.tout.sendMsg(namezapros);return;}
                    if(namezapros.equals("aut:zxc:123:"+Util.gv)){namezapros="aut:zxc2:123:"+Util.gv;game.tout.sendMsg(namezapros);}
                    }
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

}

