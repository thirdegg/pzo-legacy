package GameInterface;

import GameWorld.Game;

import InventItem.*;
import InventItem.artefacts.*;
import InventItem.artefacts.ArtBlood;
import InventItem.artefacts.ArtElec1;
import InventItem.artefacts.ArtHeart;
import InventItem.artefacts.ArtMgla;
import InventItem.artefacts.ArtVeter;
import InventItem.device.DetektorArtefaktov;
import InventItem.device.Guitar;
import InventItem.device.Radiomiter;
import InventItem.mobitem.KlikiBossKabana;
import InventItem.mobitem.KlikiKabana;
import InventItem.mobitem.RukaZombi;
import InventItem.radiodetali.*;
import Unit.Hero;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Array;
import com.blackbirds.projectzone.GdxGame;
import util.Util;


public class Inventar {
    public boolean binvent, btorg;
    ImgItems imgitems;
    DopWindow dopwind;
    GdxGame game;
    Group gleftit, grightit, godeto, ghar,groupinventskils;
    Label leftname, rightname,life,rad;
    public Label exp;
    Image invent, leftit, rightit,out,inventskils;
    Hero hero;
    Button butskils;
    public Array<Item> torgitems=new Array<Item>();
    private Image skils[]=new Image[20];
    public Inventar(Group stage, Hero hero, final GdxGame game) {
        this.hero = hero;
        this.game = game;
        imgitems = new InventItem.ImgItems();
        dopwind = new DopWindow(game, hero);
        invent = new Image(new Texture("invent.png"));
        invent.setSize(675, 430);
        invent.setPosition(75, 25);
        invent.setVisible(false);
        inventskils = new Image(new Texture("inventskil.png"));
        inventskils.setSize(675, 430);
        inventskils.setPosition(75, 25);
        groupinventskils = new Group();
        groupinventskils.addActor(inventskils);
        leftit = new Image(new Texture("fonitem.png"));
        leftit.setSize(300, 350);
        leftit.setPosition(50, 95);
        leftit.setVisible(false);
        rightit = new Image(new Texture("fonitem.png"));
        rightit.setSize(300, 350);
        rightit.setPosition(425, 95);
        rightit.setVisible(false);
        leftname = new Label("Торговец", game.text.linvent);
        life = new Label("100", game.text.linvent);
        rad = new Label("0", game.text.linvent);
        exp = new Label("100 опыта", game.text.linvent);
        exp.setPosition(350,415);
        groupinventskils.addActor(exp);
        life.setPosition(154,380);
        rad.setPosition(160,340);
        rightname = new Label(hero.name.getText(), game.text.linvent);
        stage.addActor(invent);
        stage.addActor(leftit);
        stage.addActor(rightit);
        out = new Image(new Texture("windout.png"));
        out.setSize(50, 40);
        out.setPosition(696, 415);
        out.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                closeInvent();
                super.clicked(event, x, y);
            }
        });
        out.setVisible(false);
        gleftit = new Group();
        grightit = new Group();
        godeto = new Group();
        ghar = new Group();
        gleftit.setVisible(false);
        grightit.setVisible(false);
        godeto.setVisible(false);
        ghar.setVisible(false);
        groupinventskils.setVisible(false);
        stage.addActor(gleftit);
        stage.addActor(grightit);
        stage.addActor(godeto);
        stage.addActor(ghar);
        stage.addActor(leftname);
        stage.addActor(rightname);
        stage.addActor(groupinventskils);
        stage.addActor(dopwind.gwind);
        stage.addActor(dopwind.buttons);
        stage.addActor(out);
        rightname.setPosition(695 - rightname.getWidth(), 440);
        leftname.setPosition(50, 440);
        rightname.setVisible(false);
        leftname.setVisible(false);
        ghar.addActor(life);
        ghar.addActor(rad);
        butskils = new TextButton("Умения", game.text.ButtonStyle);
        butskils.setSize(130, 30);
        butskils.setPosition(150, 135);
        butskils.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                groupinventskils.setVisible(true);
                game.tout.sendMsg("1/20/0");
                super.clicked(event, x, y);
            }
        });
        ghar.addActor(butskils);
        int sx=97;
        int sy=340;
        for(int i=0;i<skils.length;i++){
          skils[i]=new Image(new Texture("skils/skil"+i+".jpg"));
          skils[i].setSize(45,45);
          skils[i].setPosition(sx,sy);
          skils[i].setColor(0.3f,0.3f,0.3f,1);
          sx+=65;
          if((i+1)%10==0){
          sx=97;sy-=70;
          }
          groupinventskils.addActor(skils[i]);
          final int ii=i;
            skils[i].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    openSkil(ii,skils[ii]);
                    super.clicked(event, x, y);
                }
            });
        }
    }

    void run() {
        if (binvent) {
         life.setText(""+Util.round(hero.life.life,2));
         rad.setText(""+ Util.round(hero.radiation,2));
        }
    }

    //получение кол-ва в метод сет ган устанавливаем активныйе айдишники для оружия и патронов
// потом устанавливаем в арраумар значения колва по айди установленному в методе сетган
//потом записываем в лейблы значения из списка hero.idtopatronov
    public void openInvent() {
        invent.setVisible(true);
        grightit.setVisible(true);
        out.setVisible(true);
        ghar.setVisible(true);
        godeto.setVisible(true);
        rightname.setVisible(true);
        rightname.setPosition( 390- rightname.getWidth()/2, 420);
        grightit.clear();
        godeto.clear();
        binvent = true;
        Game.tochm=false;
//item
        int sx = 0, sy = 330, itt = 0;
        if (hero.items.size > 0) {
            for (final Item it:hero.items) {
                final Drawable drawable = imgitems.tiptoitem.get(it.tipitem).getDrawable();
                Image img = new Image(drawable);
                img.setSize(50, 50);
                grightit.addActor(img);
                img.setPosition(sx, sy);
//установка колличества
                int col = it.dopintcolvo;
                if (col != 0) {
                    Label coll = new Label("" + col, game.text.linvent);
                    coll.setPosition(sx + 50 - (coll.getWidth()), sy);
                    grightit.addActor(coll);
                }
                sx += 71;
                itt++;
                if (kratnost5(itt)) {
                    sx = 0;
                    sy -= 53;
                }
                img.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        dopwind.setWindow(it,drawable, 1);
                        super.clicked(event, x, y);
                    }
                });
            }
        }
        grightit.setX(390);
//odeto
        if (hero.odeto.size>0) {
            sx = 25;
            sy = 40;
            for (final Item it:hero.odeto) {
                final Drawable drawable = imgitems.tiptoitem.get(it.tipitem).getDrawable();
                Image img = new Image(drawable);
                img.setSize(50, 50);
                godeto.addActor(img);
                sx += 70;
                img.setPosition(sx, sy);
//установка колличества
                int col = it.dopintcolvo;
                if (col != 0) {
                    Label coll = new Label("" + col, game.text.linvent);
                    coll.setPosition(sx + 50 - (coll.getWidth()), sy);
                    godeto.addActor(coll);
                }
                img.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        dopwind.setWindow(it,drawable, 2);
                        super.clicked(event, x, y);
                    }
                });

            }
        }
        Label lmony = new Label("Деньги" + hero.many + " р", game.text.linvent);
        lmony.setPosition(-135, 180);
        grightit.addActor(lmony);
    }

    // временное решение для двух типов нпс и диалогов
public void setTorg(Array<Item> titems,long idtorg,String name){
    leftname.setText(name);
    torgitems=titems;
    dopwind.idtorg=idtorg;

}
    public void openTorg() {
        leftit.setVisible(true);
        rightit.setVisible(true);
        grightit.setVisible(true);
        rightname.setVisible(true);
        rightname.setPosition(695 - rightname.getWidth(), 440);
        leftname.setVisible(true);
        grightit.clear();
        btorg = true;
        out.setVisible(true);
        int sx = 0, sy = 380, itt = 0;
        if (hero.items.size>0) {
            for (final Item it:hero.items) {
                final Drawable drawable = imgitems.tiptoitem.get(it.tipitem).getDrawable();
                Image img = new Image(drawable);
                img.setSize(50, 50);
                grightit.addActor(img);
                img.setPosition(sx, sy);
                img.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        dopwind.setWindow(it,drawable, 3);
                        super.clicked(event, x, y);
                    }
                });
//установка колличества
                int col = it.dopintcolvo;
                if (col != 0) {
                    Label coll = new Label("" + col, game.text.linvent);
                    coll.setPosition(sx + 50 - (coll.getWidth()), sy);
                    grightit.addActor(coll);
                }
                sx += 53;
                itt++;
                if (kratnost5(itt)) {
                    sx = 0;
                    sy -= 53;
                }
            }
        }
        grightit.setX(446);
        Label lmony = new Label("Деньги" + hero.many + " р", game.text.linvent);
        lmony.setPosition(130, 135);
        grightit.addActor(lmony);
//торговец
            gleftit.setVisible(true);
            gleftit.clear();
            sx = 0;
            sy = 380;
            itt = 0;
            for (final Item it:torgitems) {
                final Drawable drawable = imgitems.tiptoitem.get(it.tipitem).getDrawable();
                Image img = new Image(drawable);
                img.setSize(50, 50);
                gleftit.addActor(img);
                img.setPosition(sx, sy);
                img.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        dopwind.setWindow(it, drawable, 4);
                        super.clicked(event, x, y);
                    }
                });
                sx += 53;
                itt++;
                if (kratnost5(itt)) {
                    sx = 0;
                    sy -= 53;
                }
            }
            gleftit.setX(70);
    }

    boolean kratnost5(int num) {
        return num % 5 == 0;
    }

    public void setAllItems(String tmp[]) {
        hero.many = Integer.parseInt(tmp[1]);
        String sitem[] = null, sodeto[] = null;
        if (tmp.length > 2) sitem = tmp[2].split("-");
        if (tmp.length > 3) sodeto = tmp[3].split("-");
//установка итем
        if (sitem != null && sitem.length > 1) {
            for (int i = 0; i < sitem.length; i += 3) {
                int id = Integer.parseInt(sitem[i]);
                int tip = Integer.parseInt(sitem[i+1]);
                int col=0;
                //ошибка с сервера не приходит колво
                if(sitem.length>i+2)col= Integer.parseInt(sitem[i + 2]);
                hero.items.add(createItem(tip,id,col));
            }
        }
//установка одето
        if (sodeto != null && sodeto.length > 1) {
            for (int i = 0; i < sodeto.length; i += 3) {
                int id = Integer.parseInt(sodeto[i]);
                int tip = Integer.parseInt(sodeto[i+1]);
                int col = Integer.parseInt(sodeto[i + 2]);
                Item it=createItem(tip,id,col);
                hero.odeto.add(it);
                if(it.weapon)hero.setPlayerGun(tip,col);
                //радиомитер
                if(it.tipitem==97)hero.radiomiter=true;
            }
        }
        hero.setPlayerGun();
    }
   public Item createItem(int tip, int id, int colvo){
        Item it=null;
       switch(tip){
           case 0:
               it = new Kurtka2(id);
               break;
           case 1:
               it = new Kurtka1(id);
               break;
           case 2:
               it = new Spichki(id);
               break;
//case 3:Lom lm=new Lom();addObject(lm);break;
           case 4:
               it = new Flaga(id);
               break;
           case 5:
               it = new Baton(id);
               break;
           case 6:
               it = new Konserva(id);
               break;
//case 7:Noz no=new Noz();addObject(no);break;
           case 8:
               it = new Bint(id);
               break;
//case 9:Kanistra ka=new Kanistra();addObject(ok);break;
           case 10:
               it = new IneRad(id);
               break;
           case 11:
               it = new Aptechka(id);
               break;
           case 12:
               it = new ArtMgla(id);
               break;
           case 13:
               it = new Pistolet1(id);
               break;
           case 14:
               it = new Patrony(id);
               break;
           case 15:
               it = new ArtElec1(id);
               break;
           case 16:
               it = new Kurtka3(id);
               break;
           case 17:
               it = new Kurtka4(id);
               break;
           case 18:
               it = new Pistolet2(id);
               break;
           case 19:
               it = new Pistolet3(id);
               break;
           case 20:
               it = new Pistolet4(id);
               break;
           case 21:
               it = new Akkom(id);
               break;
           case 22:
               it = new AkkomZ(id);
               break;
           case 23:
               it = new ItBinokl(id);
               break;
           case 24:
               it = new ArtVeter(id);
               break;
           case 25:
               it = new Kurtka5(id);
               break;
           case 26:
               it = new Kurtka6(id);
               break;
           case 27:
               it = new ArtHeart(id);
               break;
           case 28:
               it = new ArtBlood(id);
               break;
           case 29:
               it = new Avtomat1(id);
               break;
           case 30:
               it = new Avtomat2(id);
               break;
           case 31:
               it = new Avtomat3(id);
               break;
           case 32:
               it = new KurtkaM(id);
               break;
           case 33:
               it = new KurtkaP(id);
               break;
           case 34:
               it = new KurtkaR(id);
               break;
           case 35:
               it = new MedProvoda(id);
               break;
           case 36:
               it = new Display(id);
               break;
           case 37:
               it = new Tranzistor(id);
               break;
           case 38:
               it = new Kondensator(id);
               break;
           case 39:
               it = new Mikrochip(id);
               break;
           case 40:
               it = new Dinamik(id);
               break;
           case 41:
               it = new Rezistor(id);
               break;
           case 42:
               it = new RukaZombi(id);
               break;
           case 43:
               it = new KlikiKabana(id);
               break;
           case 44:
               it = new DetektorArtefaktov(id);
               break;
           case 45:
               it = new KlikiBossKabana(id);
               break;
           case 46:
               it = new ArtArmor(id);
               break;
           case 47:
               it = new ArtBlood1(id);
               break;
           case 48:
               it = new ArtBlood2(id);
               break;
           case 49:
               it = new ArtHeart1(id);
               break;
           case 50:
               it = new ArtHeart2(id);
               break;
           case 51:
               it = new ArtVeter1(id);
               break;
           case 52:
               it = new ArtVeter2(id);
               break;
           case 103:
               it = new Guitar(id);
               break;
           case 97:
               it = new Radiomiter(id);
               break;
           case 120:
               it=new ArtArmor1(id);
               break;
           case 121:
               it=new ArtArmor2(id);
               break;
       }
       it.dopintcolvo=colvo;
       return it;
    }
    public void closeInvent() {
        if(groupinventskils.isVisible()){groupinventskils.setVisible(false);return;}
        binvent = false;
        invent.setVisible(false);
        ghar.setVisible(false);
        btorg = false;
        grightit.setVisible(false);
        out.setVisible(false);
        dopwind.gwind.setVisible(false);
        godeto.setVisible(false);
        gleftit.setVisible(false);
        leftit.setVisible(false);
        rightit.setVisible(false);
        dopwind.buttons.setVisible(false);
        rightname.setVisible(false);
        leftname.setVisible(false);
        Game.tochm=true;
    }
    void openSkil(int skil,Image img){
        final Drawable drawable=img.getDrawable();
       switch (skil){
           case 0:dopwind.setWindowSkil("Предчувствие зоны","Позволяет  узнать о начале выброса\nзадолго до того как технарь\nобъявит об этом по рации.",drawable,100,skil);break;
           case 1:dopwind.setWindowSkil("Рука атлета","Позволяет дальше\nбросать болт.",drawable,150,skil);break;
           case 2:dopwind.setWindowSkil("Стремительность","Повышает скорость передвижения.",drawable,200,skil);break;
           case 3:dopwind.setWindowSkil("Стремительность 2","Значительно повышает скорость\nпередвижения.",drawable,400,skil);break;
           case 4:dopwind.setWindowSkil("Вместительность","Добавляет один слот\nбыстрого доступа.",drawable,150,skil);break;
           case 5:dopwind.setWindowSkil("Вместительность 2","Добавляет два слота\nбыстрого доступа.",drawable,200,skil);break;
           case 6:dopwind.setWindowSkil("Бережливость","Снижает кол-во потерянных предметов при смерти.",drawable,200,skil);break;
           case 7:dopwind.setWindowSkil("Пулестойкость","Снижает  урон от пуль.",drawable,150,skil);break;
           case 8:dopwind.setWindowSkil("Электро-защита","Снижает  урон от эллектричества.",drawable,200,skil);break;
           case 9:dopwind.setWindowSkil("Стальная плоть","Снижает  урон от физических атак.",drawable,200,skil);break;
           case 10:dopwind.setWindowSkil("Предчувствие зоны","Позволяет  узнать о начале выброса\nзадолго до того как технарь\nобъявит об этом по рации.",drawable,100,skil);break;
           case 11:dopwind.setWindowSkil("Предчувствие зоны","Позволяет  узнать о начале выброса\nзадолго до того как технарь\nобъявит об этом по рации.",drawable,100,skil);break;
           case 12:dopwind.setWindowSkil("Предчувствие зоны","Позволяет  узнать о начале выброса\nзадолго до того как технарь\nобъявит об этом по рации.",drawable,100,skil);break;
           case 13:dopwind.setWindowSkil("Предчувствие зоны","Позволяет  узнать о начале выброса\nзадолго до того как технарь\nобъявит об этом по рации.",drawable,100,skil);break;
           case 14:dopwind.setWindowSkil("Предчувствие зоны","Позволяет  узнать о начале выброса\nзадолго до того как технарь\nобъявит об этом по рации.",drawable,100,skil);break;
           case 15:dopwind.setWindowSkil("Предчувствие зоны","Позволяет  узнать о начале выброса\nзадолго до того как технарь\nобъявит об этом по рации.",drawable,100,skil);break;
       }
    }
    public void skillmessage(String message[]){
        int tip=Integer.parseInt(message[1]);
        switch(tip){
            case 0:
                dopwind.gwind.setVisible(false);
                dopwind.buttons.setVisible(false);
                Ginterface.setText("Способность изучена",3000);
                int skil=Integer.parseInt(message[2]);
                hero.spisskills.add(skil);
                skils[skil].setColor(1,1,1,1);
                loadSkil(skil);
                exp.setText(Integer.parseInt(message[3])+" опыта");
            break;
            case 1:
                Ginterface.setText("Недостаточно очков опыта",3000);
                break;
            case 2:
                Ginterface.setText("Способность уже изучена",3000);
                break;
                //load all skill
            case 3:
                String str[]=message[2].split("-");
                for(String s:str){
                    int skill=Integer.parseInt(s);
                    hero.spisskills.add(skill);
                    skils[skill].setColor(1,1,1,1);
                    loadSkil(skill);
                }
                break;
        }

    }
   public void loadSkil(int skil){
        switch (skil){
            case 4:
                hero.colvoslotov+=1;
                break;
            case 5:
                hero.colvoslotov+=2;
                break;
        }
    }
}
