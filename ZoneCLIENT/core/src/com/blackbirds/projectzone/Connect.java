package com.blackbirds.projectzone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
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

public class Connect implements Screen {
    Label lconnect;
    Image fon,korvin;
    GdxGame game;
    boolean tmp;
    TextButton nazad;
    private Stage stage;
    float sec;

    public Connect(GdxGame game) {
        this.game = game;
        stage = new Stage(new StretchViewport(1200, 720));
        fon = new Image(new Texture("menufon.jpg"));
        korvin = new Image(new Texture("created.jpg"));
        stage.addActor(fon);
        lconnect = new Label("Идет соединение с сервером", game.text.ls2);
        lconnect.setColor(Color.WHITE);
        lconnect.setAlignment(2);
        lconnect.setPosition(600-lconnect.getWidth()/2, 350);
        stage.addActor(lconnect);
        nazad = new TextButton("выход", game.text.ButtonStyle);
        nazad.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
                super.clicked(event, x, y);
            }
        });
        nazad.setPosition(535, 260);
        nazad.setSize(150, 45);
        nazad.setVisible(false);
        stage.addActor(nazad);
        stage.addActor(korvin);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        stage.draw();
        if (sec < 60)sec+=10*delta;
        if (sec>40){if(korvin.getColor().a>0.05)korvin.getColor().a-=0.01f;else korvin.setVisible(false);}
        if (!tmp && sec > 60) {
            boolean connect = game.createConnect();
            if (connect){
                new Util();
                game.setScreen(new Menu(game));
                new Sounds();}
            else {
                lconnect.setText("Сервер недоступен или\nНет соединения с сетью internet");
                nazad.setVisible(true);
            }
            tmp = true;
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
