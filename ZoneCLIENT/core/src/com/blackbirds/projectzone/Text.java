package com.blackbirds.projectzone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;

public class Text {
    private final String FONT_CHARACTERS =
            "абвгдежзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.-,?!+*";
    private final String FONT_SYSTEM =
            "абвгдежзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789:.-+,?!()\n";
    private final String FONT_NUM="0123456789";
    public TextButtonStyle ButtonStyle, buttonwind;
    public TextFieldStyle tfs, tfs2, tgamechat,tfsnum;
    public LabelStyle ls, ls2, textName, lgamechat, linvent, vzaimod, minusuron,lspda;
    public BitmapFont font1, font2, font3, font4, font5, font6, gamechat, invent, name, ct, ct2,fontnum;

    //"абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯabcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789][_!$%#@|\\/?-+=()*&.;,{}\"´`'<>";
    public Text() {
        FreeTypeFontParameter param = new FreeTypeFontParameter();
        param.size = 33;
        param.characters = FONT_CHARACTERS;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/font1.ttf"));
        font1 = generator.generateFont(param);
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/font2.ttf"));
        font2 = generator.generateFont(param);
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/font3.ttf"));
        font3 = generator.generateFont(param);
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/font4.ttf"));
        param.size = 13;
        font4 = generator.generateFont(param);
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/font4.ttf"));
        param.size = 19;
        gamechat = generator.generateFont(param);
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/font5.ttf"));
        font5 = generator.generateFont(param);
        param.size = 30;
        font6 = generator.generateFont(param);
        param.characters = FONT_SYSTEM;
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/invent2.ttf"));
        param.size = 19;
        invent = generator.generateFont(param);
        param.size = 15;
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/font1.ttf"));
        name = generator.generateFont(param);

        param.size = 15;
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/ct2.ttf"));
        ct = generator.generateFont(param);
//pda
        param.size=11;
        param.spaceX=1;
        ct2=generator.generateFont(param);
        // числа для tfs
        param.characters=FONT_NUM;
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/font4.ttf"));
        param.size = 19;
        fontnum= generator.generateFont(param);



        generator.dispose();
        Skin skin = new Skin();
        TextureAtlas tr = new TextureAtlas(Gdx.files.internal("ui.atlas"));
        skin.addRegions(tr);
        tfs = new TextFieldStyle();
        tfs.font = font1;
        tfs.fontColor = Color.BLACK;
        //tfs.background = skin.getDrawable("tf2");
        tfs.cursor = skin.getDrawable("ncursor");

        tfs2 = new TextFieldStyle();
        tfs2.font = font4;
        tfs2.fontColor = Color.WHITE;
        tfs2.background = skin.getDrawable("tf5");
        tfs2.cursor = skin.getDrawable("ncursor");

        tfsnum = new TextFieldStyle();
        tfsnum.font = fontnum;
        tfsnum.fontColor = Color.WHITE;
        tfsnum.background = skin.getDrawable("ntf2");
        tfsnum.cursor = skin.getDrawable("ncursor");

        tgamechat = new TextFieldStyle();
        tgamechat.font = ct;
        tgamechat.fontColor = Color.WHITE;
        tgamechat.background = skin.getDrawable("ntf2");
        tgamechat.cursor = skin.getDrawable("ncursor");

        ls = new LabelStyle(font1, Color.BLUE);
        ls2 = new LabelStyle(font3, Color.WHITE);
        linvent = new LabelStyle(invent, Color.WHITE);
        vzaimod = new LabelStyle(font6, Color.GREEN);
        textName = new LabelStyle(name, Color.WHITE);
        minusuron = new LabelStyle(font4, Color.WHITE);
        textName.font.setUseIntegerPositions(false);
        lgamechat = new LabelStyle(ct, Color.WHITE);
        lspda=new LabelStyle(ct2, Color.WHITE);

        ButtonStyle = new TextButtonStyle();
        ButtonStyle.font = font5;
        ButtonStyle.up = skin.getDrawable("knopka");

        buttonwind = new TextButtonStyle();
        buttonwind.font = font5;
        buttonwind.up = skin.getDrawable("knopkab1");
    }
}
