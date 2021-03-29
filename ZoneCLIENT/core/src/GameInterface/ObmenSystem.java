package GameInterface;

import GameWorld.Game;
import InventItem.Item;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.decals.GroupPlug;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import util.Util;

public class ObmenSystem extends Group{
Image itfon1,itfon2,itfon3;
Button clear,gotov,otmena,predlozh,manyok;
    Ginterface ginterface;
    Group groupmyitems;
    Label vit,pvi,pvam, mypredmany,predmany,mymany;
    int px=280,py=330,nit;
    StringBuilder predit;
    int sumapredmany;
    TextField manytf;
    Group groupzapros;
    Image zaprosfon;
    Label zaproslabel;
    Button da,net;
    int idobmen;
public ObmenSystem(final Ginterface ginterface){
    setVisible(false);
    this.ginterface=ginterface;
    groupmyitems=new Group();
    itfon1=new Image(new Texture("fonitem.png"));
    itfon2=new Image(new Texture("fonitem.png"));
    itfon3=new Image(new Texture("fonitem.png"));
    itfon1.setSize(250, 300);
    itfon2.setSize(250, 300);
    itfon3.setSize(250, 300);
    itfon1.setPosition(0, 95);
    itfon2.setPosition(275, 95);
    itfon3.setPosition(550, 95);
    addActor(itfon1);
    addActor(itfon2);
    addActor(itfon3);
    vit =new Label("Ваши предметы",ginterface.game.text.linvent);
    pvi =new Label("Предлагаете вы",ginterface.game.text.linvent);
    pvam =new Label("Предлагают вам",ginterface.game.text.linvent);
    mypredmany =new Label("Деньги: 0р",ginterface.game.text.linvent);
    predmany =new Label("Деньги: 0р",ginterface.game.text.linvent);
    mymany =new Label("Деньги: 0р",ginterface.game.text.linvent);
    vit.setPosition(600,395);
    pvi.setPosition(325,395);
    pvam.setPosition(50,395);
    mypredmany.setPosition(325,130);
    predmany.setPosition(50,130);
    mymany.setPosition(555,130);
    addActor(mypredmany);
    addActor(predmany);
    addActor(mymany);
    addActor(vit);
    addActor(pvi);
    addActor(pvam);
    addActor(groupmyitems);
    predit=new StringBuilder("1/19/3");
    clear =new TextButton("Очистить",ginterface.game.text.ButtonStyle);
    predlozh =new TextButton("Предложить",ginterface.game.text.ButtonStyle);
    otmena =new TextButton("Отменить сделку",ginterface.game.text.ButtonStyle);
    gotov =new TextButton("Я согласен",ginterface.game.text.ButtonStyle);
    manyok =new TextButton("ok",ginterface.game.text.ButtonStyle);
    gotov.setWidth(gotov.getWidth()+15);
    clear.setWidth(clear.getWidth()+15);
    otmena.setWidth(otmena.getWidth()+15);
    predlozh.setWidth(predlozh.getWidth()+15);
    manyok.setWidth(50);
    gotov.setPosition(150,65);
    clear.setPosition(160+gotov.getWidth(),65);
    predlozh.setPosition(clear.getX()+clear.getWidth()+10,65);
    otmena.setPosition(predlozh.getX()+predlozh.getWidth()+10,65);
    manyok.setPosition(640,100);
    addActor(manyok);
    addActor(gotov);
    addActor(predlozh);
    addActor(otmena);
    addActor(clear);
    predlozh.addListener(new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            predit.insert(6,"/"+sumapredmany);
            ginterface.game.tout.sendMsg(predit.toString());
            itfon2.setColor(Color.YELLOW);
            pvi.setColor(Color.YELLOW);
            super.clicked(event, x, y);
        }
    });
    otmena.addListener(new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            ginterface.game.tout.sendMsg("1/19/5");
            setVisible(false);
            px=280;py=330;nit=0;
            sumapredmany=0;
            mypredmany.setText("Деньги: "+sumapredmany+"р");
            predmany.setText("Деньги: "+sumapredmany+"р");
            itfon1.setColor(1,1,1,1);
            pvam.setColor(1,1,1,1);
            itfon2.setColor(1,1,1,1);
            pvi.setColor(1,1,1,1);
            super.clicked(event, x, y);
        }
    });
    gotov.addListener(new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            ginterface.game.tout.sendMsg("1/19/4");
            itfon2.setColor(Color.GREEN);
            pvi.setColor(Color.GREEN);
            super.clicked(event, x, y);
        }
    });
    clear.addListener(new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            startObmen();
            px=280;py=330;nit=0;
            sumapredmany=0;
            mypredmany.setText("Деньги: "+sumapredmany+"р");
            predit.setLength(0);
            predit.append("1/19/3");
            super.clicked(event, x, y);
        }
    });
    manyok.addListener(new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            if(manytf.getText().length()>1){
            sumapredmany=Integer.parseInt(manytf.getText());
            if(sumapredmany>Game.hero.many)sumapredmany=Game.hero.many;
            mypredmany.setText("Деньги: "+sumapredmany+"р");
            manytf.getOnscreenKeyboard().show(false);
            manytf.setText("");}
            super.clicked(event, x, y);
        }
    });
    manytf=new TextField("",ginterface.game.text.tfsnum);
    manytf.setPosition(555,100);
    manytf.setMaxLength(5);
    manytf.setWidth(80);
    manytf.setOnlyFontChars(true);
    addActor(manytf);
    //запрос на обмен (не входит в эту группу)
    zaprosfon = new Image(new Texture("helpwindow.png"));
    zaprosfon.setSize(250, 150);
    zaprosfon.setPosition(275, 250);
    groupzapros=new Group();
    groupzapros.setVisible(false);
    groupzapros.addActor(zaprosfon);
    zaproslabel = new Label("Игрок хххххххх предлагает\nвам обмен.", ginterface.game.text.linvent);
    zaproslabel.setAlignment(1);
    zaproslabel.setPosition(290, 350);
    groupzapros.addActor(zaproslabel);
    da = new TextButton("Да", ginterface.game.text.ButtonStyle);
    da.setSize(50, 30);
    da.setPosition(310, 290);
    da.addListener(new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
          ginterface.game.tout.sendMsg("1/19/2/"+idobmen);
            groupzapros.setVisible(false);
            super.clicked(event, x, y);
        }
    });
    groupzapros.addActor(da);
    net = new TextButton("Нет", ginterface.game.text.ButtonStyle);
    net.setSize(50, 30);
    net.setPosition(440, 290);
    net.addListener(new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {
            groupzapros.setVisible(false);
            ginterface.game.tout.sendMsg("1/19/6/"+idobmen);
            super.clicked(event, x, y);
        }
    });
    groupzapros.addActor(net);
}
    public void serverMsg(String str[]){
        int tip=Integer.parseInt(str[1]);
        switch(tip){
            case 1:
                idobmen=Integer.parseInt(str[2]);
                zaproslabel.setText("Игрок "+str[3]+"\nпредлагает вам обмен.");
                groupzapros.setVisible(true);
                break;
            case 2:
                startObmen();
                break;
            case 3:
                setPredAddItems(str);
                break;
            case 4:
                Ginterface.setText("сделка прошла успешно",3000);
                setVisible(false);
                Game.hero.many=Integer.parseInt(str[2]);
                // добавляем предметы
                for(int i=3;i<str.length;i+=3){
                Item it=ginterface.invent.createItem(Integer.parseInt(str[i]),Integer.parseInt(str[i+1]),Integer.parseInt(str[i+2]));
                Game.hero.items.add(it);
                }
                //удаляем предметы
                String delstr[]=predit.toString().split("/");
                for(int i=3;i<delstr.length;i++){
                Game.hero.remItem(Integer.parseInt(delstr[i]));
                }
                px=280;py=330;nit=0;
                sumapredmany=0;
                mypredmany.setText("Деньги: "+sumapredmany+"р");
                predmany.setText("Деньги: "+sumapredmany+"р");
                itfon1.setColor(1,1,1,1);
                pvam.setColor(1,1,1,1);
                itfon2.setColor(1,1,1,1);
                pvi.setColor(1,1,1,1);
                break;
            case 5:
                Ginterface.setText("игрок отказался от сделки",3000);
                setVisible(false);
                px=280;py=330;nit=0;
                sumapredmany=0;
                mypredmany.setText("Деньги: "+sumapredmany+"р");
                predmany.setText("Деньги: "+sumapredmany+"р");
                itfon1.setColor(1,1,1,1);
                pvam.setColor(1,1,1,1);
                itfon2.setColor(1,1,1,1);
                pvi.setColor(1,1,1,1);
                break;
            case 6:
                Ginterface.setText("игрок отказался от обмена",3000);
                break;
            case 7:
                itfon1.setColor(Color.GREEN);
                pvam.setColor(Color.GREEN);
                break;
        }
    }
    public void startObmen(){
    setVisible(true);
    mymany.setText("Деньги: "+Game.hero.many+"р");
    groupmyitems.clear();
        //item
        int sx = 555, sy = 330, itt = 0;
        if (Game.hero.items.size > 0) {
            for (final Item it:Game.hero.items) {
                final Drawable drawable = ginterface.invent.imgitems.tiptoitem.get(it.tipitem).getDrawable();
                final Image img = new Image(drawable);
                img.setSize(40, 40);
                groupmyitems.addActor(img);
                img.setPosition(sx, sy);
//установка колличества
                int col = it.dopintcolvo;
                final Label coll;
                if (col != 0) {
                    coll = new Label("" + col, ginterface.game.text.linvent);
                    coll.setSize(coll.getWidth()/1.5f,coll.getHeight()/1.5f);
                    coll.setPosition(sx + 40 - (coll.getWidth()), sy);
                    groupmyitems.addActor(coll);
                }else coll=null;

                sx += 50;
                itt++;
                if (kratnost5(itt)) {
                    sx = 555;
                    sy -= 50;
                }
                img.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        addPredlIt(img,coll,it);
                        super.clicked(event, x, y);
                    }
                });
            }
        }
    }
    void setPredAddItems(String str[]){
        itfon1.setColor(Color.YELLOW);
        pvam.setColor(Color.YELLOW);
        int many=Integer.parseInt(str[2]);
        predmany.setText("Деньги: "+many+"р");
        int sx=10, sy = 330, itt = 0;
        for(int i=3;i<str.length;i++){
        int tipitt=Integer.parseInt(str[i]);
            final Drawable drawable = ginterface.invent.imgitems.tiptoitem.get(tipitt).getDrawable();
            final Image img = new Image(drawable);
            img.setSize(40, 40);
            groupmyitems.addActor(img);
            img.setPosition(sx, sy);
            sx += 50;
            itt++;
            if (kratnost5(itt)) {
                sx = 10;
                sy -= 50;
            }
        }
    }
    void addPredlIt(Image img,Label col,Item it){
        img.setPosition(px, py);
        if(col!=null)col.setPosition(px + 40 - (col.getWidth()), py);
        px += 50;
        nit++;
        if (kratnost5(nit)) {
            px = 280;
            py -= 50;
        }
        predit.append("/"+it.id);
    }
    boolean kratnost5(int num) {
        return num % 5 == 0;
    }
}
