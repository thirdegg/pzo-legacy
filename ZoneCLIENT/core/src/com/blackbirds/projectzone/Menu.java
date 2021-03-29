package com.blackbirds.projectzone;

import GameWorld.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import util.Sounds;
import util.Util;

public class Menu implements Screen {
    TextField tfname, tfpassword;
    GdxGame game;
    private Stage stage;
    private Image aut, reg, avt, out,fon,kniga,namepass,knok,knnazad,otpason,otpasoff,authors;
    Label zapros;
    private boolean autreg, waitotvet;
    Group menu,gautreg,gavt;
    Label donat;
    Label donat2;
    Label donat3;
    public Menu(final GdxGame game) {
        this.game = game;
        stage = new Stage(new StretchViewport(1200, 720));
        Gdx.input.setInputProcessor(stage);
        menu=new Group();
        gautreg=new Group();
        gavt=new Group();
        fon = new Image(new Texture("menufon.jpg"));
        aut = new Image(new Texture("knvhod.png"));
        reg = new Image(new Texture("knregistr.png"));
        avt = new Image(new Texture("knavtor.png"));
        out = new Image(new Texture("knvihod.png"));
        kniga = new Image(new Texture("kniga.png"));
        namepass = new Image(new Texture("namepass.jpg"));
        knok = new Image(new Texture("knok.png"));
        knnazad = new Image(new Texture("knnazad.png"));
        otpason = new Image(new Texture("otpasson.png"));
        otpasoff = new Image(new Texture("otpassoff.png"));
        authors = new Image(new Texture("authors.png"));

        zapros = new Label("Запрос на сервер", game.text.ls2);
        zapros.setColor(Color.WHITE);
        zapros.setPosition(600-zapros.getWidth()/2, 400);
        zapros.setVisible(false);

        tfname = new TextField(Util.localSave.loadstring("tfname",""), game.text.tfs);
        tfpassword = new TextField(Util.localSave.loadstring("tfpassword",""), game.text.tfs);
        tfname.setMaxLength(10);
        tfpassword.setMaxLength(15);
        tfname.setWidth(300);
        tfname.setOnlyFontChars(true);
        tfpassword.setWidth(300);
        tfpassword.setPasswordCharacter('*');
        tfpassword.setPasswordMode(true);
        tfpassword.setOnlyFontChars(true);
        gautreg.addActor(namepass);
        gautreg.addActor(knok);
        gautreg.addActor(tfname);
        gautreg.addActor(tfpassword);
        gautreg.addActor(otpason);
        gautreg.addActor(otpasoff);
        otpason.setVisible(false);
        gautreg.setVisible(false);
        gavt.addActor(authors);
        gavt.setVisible(false);
        namepass.setPosition(81,350);
        knok.setPosition(250,250);
        tfname.setPosition(211,536);
        tfpassword.setPosition(260,420);
        otpasoff.setPosition(125,372);
        otpason.setPosition(125,372);
        authors.setPosition(600-authors.getWidth()/2,230);
        knnazad.setVisible(false);
        out.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
                super.clicked(event, x, y);
            }
        });
        otpason.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                tfpassword.setPasswordMode(true);
                otpason.setVisible(false);
                otpasoff.setVisible(true);
                super.clicked(event, x, y);
            }
        });
        otpasoff.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                tfpassword.setPasswordMode(false);
                otpason.setVisible(true);
                otpasoff.setVisible(false);
                super.clicked(event, x, y);
            }
        });
        avt.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
               menu.setVisible(false);
               gavt.setVisible(true);
               knnazad.setVisible(true);
               knnazad.setPosition(600-knnazad.getWidth()/2,130);
                super.clicked(event, x, y);
            }
        });
        aut.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                autreg = true;
                menu.setVisible(false);
                gautreg.setVisible(true);
                tfname.getOnscreenKeyboard().show(true);
                stage.setKeyboardFocus(tfname);
                knnazad.setPosition(226,150);
                knnazad.setVisible(true);
                super.clicked(event, x, y);
            }
        });
        reg.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                autreg = false;
                menu.setVisible(false);
                gautreg.setVisible(true);
                tfname.getOnscreenKeyboard().show(true);
                stage.setKeyboardFocus(tfname);
                knnazad.setPosition(226,150);
                knnazad.setVisible(true);
                super.clicked(event, x, y);
            }
        });
        knnazad.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                menu.setVisible(true);
                gautreg.setVisible(false);
                zapros.setVisible(false);
                knnazad.setVisible(false);
                gavt.setVisible(false);
                super.clicked(event, x, y);
            }
        });
        knok.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ok();
                super.clicked(event, x, y);
            }
        });

        kniga.setPosition(129,20);
        aut.setPosition(517,496);
        reg.setPosition(517,425);
        avt.setPosition(517,354);
        out.setPosition(517,283);
        stage.addActor(fon);
        menu.addActor(kniga);
        menu.addActor(aut);
        menu.addActor(reg);
        menu.addActor(avt);
        menu.addActor(out);
        stage.addActor(menu);
        stage.addActor(gautreg);
        stage.addActor(zapros);
        stage.addActor(knnazad);
        stage.addActor(gavt);

        donat=new Label("Увидите в зоне этих людей, скажите им спасибо.",game.text.linvent);
        donat.setPosition(750,670);
        stage.addActor(donat);
        donat2=new Label("Басоник Родион - 750р",game.text.linvent);
        donat2.setColor(Color.GOLD);
        donat2.setPosition(880,635);
        stage.addActor(donat2);
        donat3=new Label("Таченко - 250р\n\nJaeger - 226р\n\n" +
                "Романов - 200р\n\nЯрик Коммуняка - 174р\n\nPuZzzLeMan - 150р\n\nStatter - 146р\n\nСвязист-120 р\n\n" +
                "Артем Балакин - 111р\n\n" +
                "Manovar - 100р\n\nМорг - 97р",game.text.linvent);
        donat3.setPosition(880,290);
        stage.addActor(donat3);
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub

    }

    @Override
    public void render(float delta) {
        stage.draw();
        if (waitotvet) {
            if (!game.tin.sQueue.isEmpty()) {
                String str = game.tin.sQueue.poll();
                zapros.setText(str);
                zapros.pack();
                zapros.setPosition(600-zapros.getWidth()/2, 355);
                if (str.charAt(0) == 'h') {
                    Sounds.playFonMusic();
                    game.setScreen(new Game(game, str));
                    return;
                }

            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) ok();
    }

    void ok() {
        gautreg.setVisible(false);
        zapros.setVisible(true);
        knnazad.setPosition(600-knnazad.getWidth()/2,290);
        game.myname = tfname.getText();
        String name = tfname.getText() + ":";
        String password = tfpassword.getText()+":";
        // проверка
        if (name.length() < 3 || name.length() > 13) {
            zapros.setText("Слишком короткий или длинный логин");
            zapros.pack();
            zapros.setPosition(600-zapros.getWidth()/2, 355);
            return;
        }
        if (password.length() < 3 || password.length() > 13) {
            zapros.setText("Слишком короткий или длинный пароль");
            zapros.pack();
            zapros.setPosition(600-zapros.getWidth()/2, 355);
            return;
        }
        // отправка запроса
        if (autreg) game.tout.sendMsg("aut:" + name + password+Util.gv);
        else {game.tout.sendMsg("reg:" + name + password+ Util.gv);
         Util.localSave.save("tfname",name);
            Util.localSave.save("tfpassword",password);
        }
        waitotvet = true;
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
