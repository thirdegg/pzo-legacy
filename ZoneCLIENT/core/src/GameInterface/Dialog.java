package GameInterface;

import GameWorld.Game;
import Unit.Npc;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.blackbirds.projectzone.GdxGame;
import org.w3c.dom.*;
import util.Util;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * Created by 777 on 29.04.2017.
 */
public class Dialog extends Group {
    Image fon;
    Label text;
    GdxGame game;
    Label otvet[] = new Label[6];
    Ginterface ginterface;
    String tag = "a";
    int tipqvest;
    XmlReader.Element root;
int tipnpc;
boolean android;
    Dialog(GdxGame game, Ginterface ginterface) {
        setVisible(false);
        this.game = game;
        this.ginterface = ginterface;
        fon = new Image(new Texture("dialogfon.png"));
        fon.setPosition(146, 25);
        addActor(fon);
        text = new Label("Прив", game.text.linvent);
        addActor(text);
        int y = 177;
        for (int i = 0; i < otvet.length; i++) {
            otvet[i] = new Label("Ответ", game.text.linvent);
            otvet[i].setPosition(160, y -= 30);
            addActor(otvet[i]);
        }
        android=game.android;
    }
    public void openDialog(int tipnpc) {
        this.tipnpc=tipnpc;
        setVisible(true);
        tag="a";
        try {
        XmlReader xmlReader = new XmlReader();
        FileHandle file = Gdx.files.internal("dialog/dialog"+tipnpc+".xml");
        root = xmlReader.parse(file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Dialog();



/*
        if(!android)filepath= "dialog/dialog"+tipnpc+".xml";
        else filepath= "res/xml/dialogg"+tipnpc+".xml";
        File xmlFile = new File(filepath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document document = builder.parse(xmlFile);
            document.getDocumentElement().normalize();
            coreElement=document.getDocumentElement();
            Dialog();
        } catch (Exception exc) {
            exc.printStackTrace();
        }*/
    }
    void Dialog(){
        //отключаем ненужные ответы и стираем логику нажатий
        for(int i=0;i<otvet.length;i++){
            otvet[i].setVisible(false);
            otvet[i].clearListeners();
        }

        final Array<XmlReader.Element> nodeList=root.getChildrenByName(tag);
        for (int i = 0; i < nodeList.size; i++) {
            //первый узел текст дилога нпс
            if(i==0){
                String text=nodeList.get(0).getText();
                setDText(text,text.length()>40?true:false);
            }else{
                //ответы
                otvet[i-1].setVisible(true);
                otvet[i-1].setText(nodeList.get(i).getText());
                otvet[i-1].pack();
                    final int nomer=i;
                    otvet[i-1].addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            tochOtvet(nodeList.get(nomer));
                            super.clicked(event, x, y);
                        }
                    });
                }
        }
    }
    void tochOtvet(XmlReader.Element element){
        String attTag=element.getAttribute("tag",null);
        if(attTag!=null)tag=attTag;
        // считываем тип ответа
        String nameAttrib;
        nameAttrib= element.getAttribute("type",null);
        if(nameAttrib!=null){
        int type = Integer.parseInt(nameAttrib);
        // если нет доп атрибутов продолжаем диалог согласно полученному тегу
        // 1=sendMsg 2=qvest 3=torg
        switch(type){
            case 1:
                nameAttrib = element.getAttribute("send",null);
                String send =nameAttrib;
                game.tout.sendMsg(send+"/"+tipqvest);
                //добавуить ли квест в список квестов
                nameAttrib = element.getAttribute("addqvest",null);
                if(nameAttrib!=null){
                    if(Boolean.parseBoolean(nameAttrib)) Game.hero.qvests.put(tipqvest,1);}
                setVisible(false);
                return;
            case 2:
                nameAttrib = element.getAttribute("qvest",null);
                tipqvest =Integer.parseInt(nameAttrib);
                //если квест взят устанавливаем второй вариант диалога
                if(Game.hero.qvests.containsKey(tipqvest))tag+=1;
                break;
            case 3:
                setVisible(false);
                ginterface.invent.openTorg();
                return;
            case 4:
                setVisible(false);
                return;
        }}
        Dialog();
    }

    public void serverOtvet(int tipqvest,int vipolnen){
        setVisible(true);
        //квест не взят
        if(vipolnen==3){
            Ginterface.setText("Квест не взят, наверное в рюкзаке нет места",5000);
            setVisible(false);Game.hero.qvests.remove(tipqvest);return;}
        //квест выполнен
        if(vipolnen==1){ginterface.systemqvest.createQvestandEnd(tipqvest);}
        //квест не выполнен
        if(vipolnen==2){tag+=1;}
        System.out.println("пришел ответ от сервера");
        Dialog();
    }

    public void setDText(String dtext, boolean formattext) {
        if (formattext) {
            StringTokenizer str = new StringTokenizer(dtext, " ", true);
            text.setText(Util.perenosSlov(str,40));
        } else text.setText(dtext);
        text.pack();
        text.setPosition(180, 430 - text.getHeight());
    }


}
