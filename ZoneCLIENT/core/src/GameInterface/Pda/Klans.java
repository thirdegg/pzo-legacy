package GameInterface.Pda;

import GameWorld.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blackbirds.projectzone.GdxGame;

public class Klans extends Group {
    Image fon,nazad, nazad2;
    GdxGame game;
    CorePda pda;
    //core spisok
    Label mgroup,spisgroup,addgroup,kontrolt;
    Group groupspisok;
    // klan info
    Label klaninfo[]=new Label[30];
    TextButton vstupit;
    TextButton viyti;
    Group groupinfoclan;
    Label dlremov;
    //add klan
    TextField textfieldklan;
    TextButton ok;
    Label otvetservertext,trmony;
    Group groupaddk;
    // spisok klanov
    Group groupklannames;
    Label klannames[]=new Label[30];
    // vignat
    Group groupvignat;
    TextButton tvign;
    Label vign;
    String namevign;
    //kontrol terr
    Group groupcontrilterr;
    Label lcontrol[]=new Label[20];
    Image controlfon;
    Klans(final GdxGame game, final CorePda pda){
        setVisible(false);
        this.game=game;
        this.pda=pda;
        groupspisok =new Group();
        groupaddk =new Group();
        groupklannames=new Group();
        groupinfoclan =new Group();
        groupvignat=new Group();
        groupcontrilterr=new Group();
        groupvignat.setVisible(false);
        groupcontrilterr.setVisible(false);
        groupinfoclan.setVisible(false);
        groupaddk.setVisible(false);
        groupklannames.setVisible(false);
        fon=new Image(new Texture("pda/fontext3.png"));
        fon.setPosition(70,59);
        controlfon=new Image(new Texture("pda/controlterr.jpg"));
        controlfon.setPosition(70,59);
        nazad= new Image(new Texture("pda/nazad.png"));
        nazad.setPosition(690,62);
        nazad.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVisible(false);
                pda.rabstol.setVisible(true);
                super.clicked(event, x, y);
            }
        });
        nazad2 = new Image(new Texture("pda/nazad.png"));
        nazad2.setPosition(690,62);
        nazad2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                groupaddk.setVisible(false);
                groupklannames.setVisible(false);
                groupinfoclan.setVisible(false);
                groupspisok.setVisible(true);
                nazad2.setVisible(false);
                otvetservertext.setVisible(false);
                groupvignat.setVisible(false);
                groupcontrilterr.setVisible(false);
                super.clicked(event, x, y);
            }
        });
        nazad2.setVisible(false);
        addActor(fon);
        groupspisok.addActor(nazad);

        mgroup = new Label("Моя группировка", game.text.linvent);
        spisgroup = new Label("Список всех группировок", game.text.linvent);
        kontrolt = new Label("Контроль территорий", game.text.linvent);
        addgroup = new Label("Создать группировку", game.text.linvent);
        float ych = 391;
        mgroup.setPosition(100,ych);
        spisgroup.setPosition(100,ych-=mgroup.getHeight());
        kontrolt.setPosition(100,ych-=mgroup.getHeight());
        addgroup.setPosition(100,ych-=mgroup.getHeight());
        addgroup.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                groupspisok.setVisible(false);
                groupaddk.setVisible(true);
                nazad2.setVisible(true);
                super.clicked(event, x, y);
            }
        });
        groupspisok.addActor(mgroup);
        groupspisok.addActor(spisgroup);
        groupspisok.addActor(kontrolt);
        groupspisok.addActor(addgroup);
        addActor(groupspisok);
        textfieldklan = new TextField("", game.text.tfs2);
        textfieldklan.setSize(300, 30);
        textfieldklan.setPosition(85, 380);
        textfieldklan.setMaxLength(10);
        textfieldklan.setMessageText("название группировки");
        textfieldklan.setOnlyFontChars(true);
        groupaddk.addActor(textfieldklan);
        ok = new TextButton("ok", game.text.ButtonStyle);
        ok.setSize(50, 30);
        ok.setPosition(390, 380);
        spisgroup.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.tout.sendMsg("1/18/3");
                super.clicked(event, x, y);
            }
        });
        kontrolt.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.tout.sendMsg("1/18/8");
                super.clicked(event, x, y);
            }
        });
        mgroup.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(Game.hero.klanname!=null)game.tout.sendMsg("1/18/4/"+Game.hero.klanname.getText());
                super.clicked(event, x, y);
            }
        });
        ok.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.tout.sendMsg("1/18/1/"+textfieldklan.getText());
                super.clicked(event, x, y);
            }
        });
        groupaddk.addActor(ok);
        addActor(groupaddk);
        addActor(groupklannames);
        addActor(groupinfoclan);

        ych = 400;
        int xxx=75;
        for (int i = 0; i < klannames.length; i++) {
            klannames[i] = new Label("fak", game.text.lgamechat);
            klannames[i].setPosition(xxx, ych);
            ych -= klannames[i].getHeight() - 3;
            if(i%15==0&&i!=0){xxx+=170;ych=400;}
            groupklannames.addActor(klannames[i]);
            klannames[i].setVisible(false);
        }
        otvetservertext = new Label("text", game.text.linvent);
        otvetservertext.setVisible(false);
        otvetservertext.setColor(Color.RED);
        trmony = new Label("Для реггистрации группировки требуется 8000 р.", game.text.linvent);
        trmony.setPosition(80,350);
        groupaddk.addActor(trmony);

        ych = 400;
        xxx=75;
        for (int i = 0; i < klaninfo.length; i++) {
            klaninfo[i] = new Label("fak", game.text.lgamechat);
            klaninfo[i].setPosition(xxx, ych);
            ych -= klaninfo[i].getHeight() - 3;
            if(i%10==0&&i!=0){xxx+=170;ych=350;}
            groupinfoclan.addActor(klaninfo[i]);
            klaninfo[i].setVisible(false);
        }
        dlremov = new Label("Для удаления игрока нажмите на него в списке", game.text.lgamechat);
        dlremov.setPosition(195,65);
        groupinfoclan.addActor(dlremov);
        dlremov.setVisible(false);

        vstupit = new TextButton("Вступить", game.text.ButtonStyle);
        vstupit.setPosition(80, 65);
        vstupit.setWidth(110);

        viyti = new TextButton("Выйти", game.text.ButtonStyle);
        viyti.setWidth(90);
        viyti.setPosition(80, 65);
        viyti.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            if(Game.hero.klanname!=null)game.tout.sendMsg("1/18/6/"+Game.hero.klanname.getText());
                super.clicked(event, x, y);
            }
        });

        groupinfoclan.addActor(vstupit);
        groupinfoclan.addActor(viyti);

        vign = new Label("Выгнать игрока ?", game.text.linvent);
        vign.setPosition(75,400);

        groupvignat.addActor(vign);
        tvign=new TextButton("Выгнать",game.text.ButtonStyle);
        tvign.setPosition(75,365);
        groupvignat.addActor(tvign);
        tvign.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.tout.sendMsg("1/18/7/"+namevign);
                super.clicked(event, x, y);
            }
        });
        groupcontrilterr.addActor(controlfon);
        ych=365;int xch=95;
        for (int i = 0; i < lcontrol.length; i++) {
            lcontrol[i] = new Label("Одиночки", game.text.lspda);
            lcontrol[i].setPosition(xch, ych);
            lcontrol[i].setColor(Color.SKY);
            xch+=134;
            if((i+1)%5==0){ych-=88;xch=95;}
            groupcontrilterr.addActor(lcontrol[i]);
        }
        addActor(groupvignat);
        addActor(otvetservertext);
        addActor(groupcontrilterr);
        addActor(nazad2);
    }
    public void renderKlansNames(String names){
        nazad2.setVisible(true);
        final String knames[]=names.split(":");
        for(Label lbklan:klannames){
            lbklan.setVisible(false);
            lbklan.clearListeners();
        }
        for (int i=0;i<knames.length;i++){
            klannames[i].setText((i+1)+" - "+knames[i]);
            klannames[i].pack();
            klannames[i].setVisible(true);
            final int ii=i;
            klannames[i].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.tout.sendMsg("1/18/4/"+knames[ii]);
                    super.clicked(event, x, y);
                }
            });
        }
        pda.setVisible(true);
        pda.rabstol.setVisible(false);
        pda.klans.setVisible(true);
        groupklannames.setVisible(true);
        groupaddk.setVisible(false);
        groupspisok.setVisible(false);
        groupinfoclan.setVisible(false);
    }
    public void renderControlTerr(String str[]){
        nazad2.setVisible(true);
        for(Label lc:lcontrol){
            lc.setVisible(false);
        }
        if(str.length>2){
        String knames[]=str[2].split(":");
        int idm;
        for (int i=0;i<knames.length;i+=2){
            idm=Integer.parseInt(knames[i]);
            lcontrol[idm].setText(knames[i+1]);
            lcontrol[idm].setVisible(true);
        }}
        pda.setVisible(true);
        pda.rabstol.setVisible(false);
        pda.klans.setVisible(true);
        groupklannames.setVisible(false);
        groupaddk.setVisible(false);
        groupspisok.setVisible(false);
        groupinfoclan.setVisible(false);
        groupcontrilterr.setVisible(true);
    }

    public void renderKlanInfo(final String str[]){
        nazad2.setVisible(true);
        pda.setVisible(true);
        pda.rabstol.setVisible(false);
        pda.klans.setVisible(true);
        groupklannames.setVisible(false);
        groupaddk.setVisible(false);
        groupspisok.setVisible(false);
        groupinfoclan.setVisible(true);
        for (Label kl:klaninfo)kl.setVisible(false);
        klaninfo[0].setVisible(true);
        klaninfo[0].setText(str[2]);
        klaninfo[0].pack();
        klaninfo[0].setX(400-klaninfo[0].getWidth()/2);

        klaninfo[1].setVisible(true);
        klaninfo[1].setText("Лидер группировки - "+str[3]);

        klaninfo[2].setVisible(true);
        klaninfo[2].setText("Состав группировки:");
       boolean bvihod=false;
       if(str.length>4){
           final String plnames[]=str[4].split(":");
        for (int i=0;i<plnames.length;i++){
            klaninfo[i+3].setText((i+1)+" - "+plnames[i]);
            klaninfo[i+3].setVisible(true);
            //проверка если ли наш перс в списке
            if(!bvihod&&Game.hero.name.getText().toString().equals(plnames[i]))bvihod=true;
            // кнопка удаления
            if(str[3].equals(Game.hero.name.getText().toString())){
                final int ii=i;
                klaninfo[i+3].pack();
                klaninfo[i+3].addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                       namevign=plnames[ii];vignat();
                        super.clicked(event, x, y);
                    }
                });
                dlremov.setVisible(true);
            }
        }}

    if(bvihod){viyti.setVisible(true);vstupit.setVisible(false);}
    else {viyti.setVisible(false);vstupit.setVisible(true);
        vstupit.clearListeners();
        vstupit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.tout.sendMsg("1/21/0/"+str[2]);
                super.clicked(event, x, y);
            }
        });}

    }
private void vignat(){
        if(!namevign.equals(Game.hero.name.getText().toString())){
    groupvignat.setVisible(true);
    groupinfoclan.setVisible(false);}
}
    public void serverMsg(String str[]){
        if(!pda.klans.isVisible()){
            nazad2.setVisible(true);
            pda.setVisible(true);
            pda.rabstol.setVisible(false);
            pda.klans.setVisible(true);
        }
        int otvet=Integer.parseInt(str[1]);
        switch(otvet){
            case 1:
                otvetservertext.setColor(Color.GREEN);
                setTextOtvet("группипровка создана");
                Game.hero.many-=8000;
                break;
            case 2:
                otvetservertext.setColor(Color.RED);
                setTextOtvet("группипровка с таким именем уже существует");
                break;
            case 3:
                otvetservertext.setColor(Color.YELLOW);
                setTextOtvet("вы уже состоите в групперовке");
                break;
            case 4:
                otvetservertext.setColor(Color.GREEN);
                setTextOtvet("группировка расспущена");
                break;
            case 5:
                otvetservertext.setColor(Color.RED);
                setTextOtvet("вы не можете расспустить группировку");
                break;
            case 6:
               if(str.length>2)renderKlansNames(str[2]);
               else renderKlansNames("Пока что, нет ни одной группировки");
                break;
            case 7:
                renderKlanInfo(str);
                break;
            case 8:
                otvetservertext.setColor(Color.RED);
                setTextOtvet("недостаточно денег");
                break;
            case 9:
                otvetservertext.setColor(Color.GREEN);
                setTextOtvet("Вы вошли в группировку");
                break;
            case 10:
                otvetservertext.setColor(Color.GREEN);
                setTextOtvet("Вы вышли из группировки");
                Game.hero.klanname=null;
                break;
            case 11:
                otvetservertext.setColor(Color.GREEN);
                setTextOtvet("игрок удален");
                break;
            case 12:
               renderControlTerr(str);
                break;
            case 13:
                otvetservertext.setColor(Color.RED);
                setTextOtvet("название содержит запрещенные символы");
                break;
        }
    }
    void setTextOtvet(String textt){
        otvetservertext.setText(textt);
        otvetservertext.setVisible(true);
        otvetservertext.pack();
        otvetservertext.setPosition(400 - otvetservertext.getWidth() / 2, 240 - otvetservertext.getHeight() / 2);
    }
}
