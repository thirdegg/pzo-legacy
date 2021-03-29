package Unit;

import GameWorld.Game;
import GameWorld.MapObject;
import GameWorld.SpatialHashGrid;
import InventItem.Weapon;
import MapObjectss.Bolt;
import Modules.Life;
import Modules.Modifikator;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import util.Overlap;
import util.Rectang;
import util.Sounds;
import util.Util;

import java.util.List;

public class Player extends MapObject {
    public float oldx, oldy;
    public float holod, golod, zhazda, rad, krovotech;
    public int timedie, timevistrel;
    public boolean ataka,ukostra,ansit;
    public MapObject atakunit;
    public int colvopatronov;
    public float atakatime, radtime;
    public Weapon gun;
private long atakoid;
    public float speed, ld, radiation, uron;
    public int napravlenie = 1;
    public int urdlnapravlenie = 4;
    public int nsnapravlenie=4;
    int napravat=4;
    public Color colorlife;
    public Label name,klanname;
    public boolean vdome;
    public Image radimg,karandash;
    public long ldt;
    boolean fireanim;
    float fireanimtime,sitanimtime,deadanimtime;
    boolean andead;
    // используется для установкипозиции пули
    int firenaprav;
    public TextureRegion imgfirenaprav[],imgstandnaprav[];
    public Animation wlakanim[],perezanim[],atakpistanim[],deadanim[],boltanim[],sitinganim[],gitaranim[],atakavtanim[],perezaanim[];
    public TextureRegion hero;
   public TextureRegion die,stand,ajumpup,ajumpright,ajumpdown,ajumpleft;
   public float stateTime;
    Game game;
    Modifikator mod;
    final int COLUMNS = 24, ROWS = 19;
    Texture heroTexture;
    TextureRegion[][] tmp;
    TextureRegion[] tmp2;
    public Bolt bolt;
    boolean gitara;
    LabelStyle lsname;
    public int timekarandash;
    public Player(float xx, float yy, long id, String sname, LabelStyle lsname, LabelStyle lsuron, Game game, int tipodeto) {
        super("cane1.png", xx, yy, 4,lsuron,true);
        this.id = id;
        this.tip = Util.PLAYER;
        plasrecx = 18;
        plasrecy = 5;
        this.lsname=lsname;
        rectang = new Rectang(x + plasrecx, y + plasrecy, 11, 10);
        centx = rectang.x + rectang.width / 2;
        centy = rectang.y + rectang.height / 2;
        name = new Label(sname, lsname);
        colorlife = new Color();
        this.game=game;
        radimg = new Image(new Texture("effrad.png"));
        karandash = new Image(new Texture("karandash.png"));
        karandash.setVisible(false);
        karandash.setSize(12,12);
        setImgOdeto(tipodeto);
        ld = life.maxlife / 40f;
        canbeattacked =true;
        unit=true;
        bolt=new Bolt(this);
    }

    public void run(float delta) {
        stateTime += Gdx.graphics.getDeltaTime();
        switch (state) {
            case Util.WALKING:
                move(delta);
                //bolt
                if(bolt.run)bolt.run(delta);
                break;
            case Util.ATAKA:
                Ataka(delta);
                break;
            case  Util.DIED:
                dead();
                break;
            case  Util.PEREZARAD:
                perezaradka(delta);
                break;
            case  Util.MODIFIKATOR:
                mod.run(delta,game.grid);
                break;
            case Util.SITTING:
                if(ansit){
                sitanimtime+=Gdx.graphics.getDeltaTime();
                hero=(TextureRegion)sitinganim[nsnapravlenie-2].getKeyFrame(sitanimtime,false);
                if(sitinganim[nsnapravlenie-2].isAnimationFinished(sitanimtime)){ansit=false;sitanimtime=0;}}
                if(gitara){
                    sitanimtime+=Gdx.graphics.getDeltaTime();
                    hero=(TextureRegion)gitaranim[nsnapravlenie-2].getKeyFrame(sitanimtime,true);}
                break;
        }
        if(state!=Util.DIED)radiation(delta);
        //life
        if (life.life < 0) life.life = 0;
        if (life.life / ld < 15) {
            colorlife = Color.RED;
        } else {
            if (life.life / ld < 30) {
                colorlife = Color.YELLOW;
            } else {
                if (life.life / ld <= 40) {
                    colorlife = Color.GREEN;
                }
            }
        }
        life.run(delta,x,y);
        super.setRegion(hero);
    }
    @Override
    public void setState(int newstate, String[] str) {
     //не состояние
        switch(newstate){
            case 8:setImgOdeto(Integer.parseInt(str[3]));return;
            case 9:setHarakteristic(Integer.parseInt(str[3]), Integer.parseInt(str[4]));return;
            case 10:if (state != Util.PEREZARAD)state = Util.WALKING;
            ataka = false;atakunit = null;return;
            case 12:bolt.startBolt(centx,centy+5,Integer.parseInt(str[3]),str.length>4?true:false);return;
            case 13:
                int tip=Integer.parseInt(str[3]);
                if(tip==1){klanname = new Label(str[4],lsname);klanname.setColor(Color.SKY);}
                if(tip==2)klanname=null;
                return;
            case 14:karandash.setVisible(true);return;
        }

        switch(state){
            case  Util.ATAKA:
                if (newstate !=  Util.PEREZARAD) {
                    ataka = false;
                    atakunit = null;
                }
                break;
            case  Util.PEREZARAD:
                gun.perezarad = false;
                gun.timep = 0;
                break;
            case  Util.MODIFIKATOR:
                break;
            case  Util.SITTING:
                gitara=false;
                break;
        }
        switch(newstate){
            case  Util.WALKING:
                if(str.length<5)return;
                canbeattacked =true;
                x = Integer.parseInt(str[3]);
                y = Integer.parseInt(str[4]);
                napravlenie = Integer.parseInt(str[5]);
                if(str.length>6)life.life = Integer.parseInt(str[6]);
                rectang.x = x + plasrecx;
                rectang.y = y + plasrecy;
                centx = rectang.x + rectang.width / 2;
                centy = rectang.y + rectang.height / 2;
                //состояние сети
                if(id==game.hero.id){
                    int state=(int)(System.currentTimeMillis()-Util.starttimeset);
                    if(state>=400)game.ginterface.stateinternet.setColor(Color.RED);
                    if(state<400)game.ginterface.stateinternet.setColor(Color.YELLOW);
                    if(state<200)game.ginterface.stateinternet.setColor(Color.GREEN);
                    game.ginterface.stateinternet.setText("Сеть :"+state);
                }
                break;
            case Util.ATAKA:
                if(str.length<7)return;
                atakoid=Long.parseLong(str[3]);
                atakunit=game.mapobjects.get(atakoid);
                setPlayerGun(Integer.parseInt(str[4]),Integer.parseInt(str[5]));
                colvopatronov=Integer.parseInt(str[6]);
                ataka=true;
                timevistrel = gun.skorostrel;
                atakatime = 0;
                if (str.length > 7) {
                    timevistrel = Integer.parseInt(str[7]);
                    atakatime = Float.parseFloat(str[8]);
                }
                break;
            case Util.DIED:
                ldt = System.currentTimeMillis() + 10000;
                life.life = 0;
                napravlenie=1;
                if (str.length > 3)ldt = System.currentTimeMillis() + Integer.parseInt(str[3]);
                canbeattacked =false;
                life.stopAll();
                break;
            case Util.PEREZARAD:
                //обычная перезарядка уже существующего игрока
                if(str.length<6)return;
                stateTime=0;
                colvopatronov=Integer.parseInt(str[5]);
                setPlayerGun(Integer.parseInt(str[3]),Integer.parseInt(str[4]));
                gun.perezarad =true;
                gun.timep = 0;
                // новый игрок в состоянии перезарядки
                if (str.length > 6) {
                    gun.timep = Float.parseFloat(str[6]);
                    // плюс он атакует
                    if (str.length > 7) {
                        atakoid=Long.parseLong(str[7]);
                        MapObject unit2 =game.mapobjects.get(atakoid);
                        atakunit = unit2;
                        ataka = true;
                    }
                }
                break;
            case Util.SITTING:
                canbeattacked =true;
                if(str.length<6)return;
                x = Integer.parseInt(str[3]);
                y = Integer.parseInt(str[4]);
                nsnapravlenie = Integer.parseInt(str[5]);
                rectang.x = x + plasrecx;
                rectang.y = y + plasrecy;
                centx = rectang.x + rectang.width / 2;
                centy = rectang.y + rectang.height / 2;
                ansit=true;
                int g=0;
                if(str.length>6)g= Integer.parseInt(str[6]);
                if(g==1){gitara=true;ansit=false;sitanimtime=0;}
                break;
            case Util.MODIFIKATOR:

                break;
        }
        state=newstate;
    }
    //1 stop ataked 2 rem playuer 3 you kill me 4 start modofikator 5 stop modofokator
    @Override
    public void signal(int tip,Object object){
        switch(tip){
            //start modifikator
            case 4:
                if(life.life>0){
                    mod=(Modifikator)object;
                    setState(Util.MODIFIKATOR,null);}
                break;
            //stop modofokator
            case 5:
                state=Util.WALKING;napravlenie=1;
                mod=null;
                if(!visibl)visibl=true;
                break;
        }
    }
    @Override
    public void dopDraw(Batch sb, ShapeRenderer sr) {
        if(state!=Util.VDOME) {
            //имя эффекты
            if(sb.isDrawing()){
            if(visibl){
                if (radiation > 0||life.containsPlusMinusLife(111)){ radimg.draw(sb, 1);
                radimg.setPosition(x , y + height -20);}
                if(karandash.isVisible()){karandash.draw(sb, 1);karandash.setPosition(x+15 , y + height -20);
                if(timekarandash>550){karandash.setVisible(false);timekarandash=0;}
                timekarandash++;}
            for (Life.MinusLife ml : life.minuslife) {
                ml.luron.draw(sb, 1);
            }
            name.setPosition(x + 22 - name.getWidth() / 2, y + 44);
            name.draw(sb,1);
                if(klanname!=null){klanname.setPosition(x + 22 - klanname.getWidth() / 2, y + 55);
                klanname.draw(sb,1);}
            }
            //модификатор
            if(state==Util.MODIFIKATOR) mod.draw(sb);}
            //жизни
            if(sr.isDrawing()&&visibl)sr.rect(x+3, y + 43, life.life / ld, 1, colorlife, colorlife, colorlife, colorlife);
            //квадрат
           // if(sr.isDrawing()&&visibl)sr.rect(rectang.x,rectang.y,rectang.width,rectang.height);
        }
    }
    void move(float delta) {
        float speed=this.speed;
        if(speed>90)speed=90;
        switch (napravlenie) {
            case 1:
                hero = stand;
                break;
            case 2:
                y += speed * delta;
                urdlnapravlenie=napravlenie;
                break;
            case 3:
                x += speed * delta;
                urdlnapravlenie=napravlenie;
                break;
            case 4:
                y -= speed * delta;
                urdlnapravlenie=napravlenie;
                break;
            case 5:
                x -= speed * delta;
                urdlnapravlenie=napravlenie;
                break;
            case 6:
                x += speed * delta;
                y += speed * delta;
                urdlnapravlenie=2;
                break;
            case 7:
                x += speed * delta;
                y -= speed * delta;
                urdlnapravlenie=3;
                break;
            case 8:
                x -= speed * delta;
                y -= speed * delta;
                urdlnapravlenie=4;
                break;
            case 9:
                x -= speed * delta;
                y += speed * delta;
                urdlnapravlenie=5;
                break;
        }
        if(napravlenie>=2){nsnapravlenie=napravlenie;
        stand=imgstandnaprav[nsnapravlenie-2];
        hero = (TextureRegion) wlakanim[nsnapravlenie-2].getKeyFrame(stateTime, true);}
        rectang.y = y + plasrecy;
        rectang.x = x + plasrecx;
        centx = rectang.x + rectang.width / 2;
        centy = rectang.y + rectang.height / 2;
        if (napravlenie != 1) {
            if (inRec(game.grid)) {
                x = oldx;
                y = oldy;
            } else {
                oldx = x;
                oldy = y;
            }
        }
    }

    public void Ataka(float delta) {
        if (ataka && gun != null) {
            if (gun.dopintcolvo == 0) {
                gun.perezarad = true;
                state = Util.PEREZARAD;
            }

            // граф отображение стрельбы относительно атакобджекс
            float rx, ry;
            if(atakunit!=null) {
                int napravat=detectNapravlenie(atakunit.centx,atakunit.centy)-2;
                nsnapravlenie=napravat+2;
                hero=imgfirenaprav[napravat];

                if (x < atakunit.x) {
                    rx = atakunit.x - x;
                    //riht
                    firenaprav = 1;
                } else {
                    rx = x - atakunit.x;
                    //left
                    firenaprav = 2;
                }
                if (y < atakunit.y) {
                    ry = atakunit.y - y;
                    if (ry > rx) {
                        //up
                        firenaprav = 3;
                    }
                } else {
                    ry = y - atakunit.y;
                    if (ry > rx) {
                        //down
                        firenaprav = 4;
                    }
                }
            }else{
                //добавить вторую переменную направления куда перс смотрел
                // граф отображение стрельбы относительно hero
                if (x < game.hero.x) {
                    rx = game.hero.x - x;
                    //left
                    firenaprav = 1;
                    napravat=3;
                } else {
                    rx = x - game.hero.x;
                    //right
                    firenaprav = 2;
                    napravat=1;
                }
                if (y < game.hero.y) {
                    ry = game.hero.y - y;
                    if (ry > rx) {
                        //down
                        firenaprav = 3;
                        napravat=2;
                    }
                } else {
                    ry = y - game.hero.y;
                    if (ry > rx) {
                        //up
                        firenaprav = 4;
                        napravat=0;
                    }
                }
            }


            atakatime += 10 * delta;
            if (atakatime > timevistrel) {
                //поиск атак обджекс
                if(atakunit==null){
                if(game.mapobjects.containsKey(atakoid))atakunit=game.mapobjects.get(atakoid);}
                float xx = centx, yy = centy+22;
                float cx = 0, cy = 0;
                Sounds.playVistrel();
                switch (firenaprav) {
                    case 1:
                        cx = xx-200;
                        cy = yy;
                        break;
                    case 2:
                        cx = xx + 200;
                        cy = yy;
                        break;
                    case 3:
                        cx = xx;
                        cy = yy - 200;
                        break;
                    case 4:
                        cx = xx;
                        cy = yy+200;
                        break;
                }
                if (atakunit != null){game.addPuli(xx, yy, (int)atakunit.centx,(int)atakunit.centy + 30,1,1000);
                    napravat=detectNapravlenie(atakunit.centx,atakunit.centy)-2;
                    nsnapravlenie=napravat+2;}
                else game.addPuli(xx, yy, (int) cx, (int) cy,1,1000);
                gun.fire(this);
                timevistrel = (int) (atakatime + gun.skorostrel);
                fireanim=true;
            }
            // если пистолеты
            if(gun.tipitem<21){
            if(fireanim){
            fireanimtime+=Gdx.graphics.getDeltaTime();
            hero=(TextureRegion)atakpistanim[napravat].getKeyFrame(stateTime,false);
            if(atakpistanim[napravat].isAnimationFinished(fireanimtime)){fireanim=false;fireanimtime=0;}}}
            //автоматы
            else{
                fireanimtime+=Gdx.graphics.getDeltaTime();
                hero=(TextureRegion)atakavtanim[napravat].getKeyFrame(stateTime,true);
            }


        } else {
            ataka = false;
            state = Util.WALKING;
        }
    }

    public int detectNapravlenie(double cx, double cy) {
        double A = Math.atan2((double) centx - cx, (double) centy - cy) / Math.PI * 180;
        if (A < 0) A += 360;
        if (A > 67.5 && A < 112.5)return  5;
        if (A < 22.5 || A > 337.5)return  4;
        if (A > 247.5 && A < 292.5)return 3;
        if (A > 157.5 && A < 202.5)return 2;
        if (A >= 202.5 && A <= 247.5)return  6;
        if (A >= 112.5 && A <= 157.5)return  9;
        if (A >= 22.5 && A <= 67.5)return 8;
        if (A >= 292.5 && A <= 337.5)return  7;
        return 2;
    }
    void perezaradka(float delta) {
        gun.perezaradka(delta, this);
        if(gun.tipitem<21)hero=(TextureRegion)perezanim[nsnapravlenie-2].getKeyFrame(stateTime,true);
        else hero=(TextureRegion)perezaanim[nsnapravlenie-2].getKeyFrame(stateTime,true);
    }

    void dead() {
        if(!andead){
            deadanimtime+=Gdx.graphics.getDeltaTime();
            hero= (TextureRegion)deadanim[nsnapravlenie-2].getKeyFrame(deadanimtime,false);
            if(deadanim[nsnapravlenie-2].isAnimationFinished(deadanimtime)){andead=true;deadanimtime=0;}
        }
        if (System.currentTimeMillis() > ldt) {
            state = Util.WALKING;
            napravlenie=1;
            life.life = life.maxlife;
            radiation = 0;
            canbeattacked =true;
            andead=false;
        }
    }

    void radiation(float delta) {
        if (radiation > 0) {
            radtime += 10 * delta;
            if (radtime > 35) {
                radiation-=0.3f;
                radtime=0;
                if(radiation>10)radiation=10;
            }
        }
    }

    boolean inRec(SpatialHashGrid grid) {
        if (rectang.x > 1588 || rectang.x < 1 || rectang.y > 1589 || rectang.y < 1) return true;
        List<Rectang> colliders = grid.getPotentialColliders(rectang);
        for (Rectang re : colliders) {
            if (Overlap.overlapRectang(re, rectang)) {
                return true;
            }
        }
        return false;
    }
    //в hero свой метод
    public  void setPlayerGun(int tipgun,int colvo) {
        gun=(Weapon)game.ginterface.invent.createItem(tipgun,0,colvo);
        float pr=gun.perezaradka;
        for(Animation anim:perezanim){
            anim.setFrameDuration(pr/65f);
        }
        for(Animation anim:perezaanim){
            anim.setFrameDuration(pr/75f);
        }
    }
    public void setHarakteristic(int harakteristic, int znach) {
        switch (harakteristic) {
            case 1:
                life.life = znach;
                break;
            case 2:
                uron = znach;
                break;
            case 3:
                //zashita = znach;
                break;
            case 4:
                speed = znach;
                break;
            case 5:
                golod = znach;
                break;
            case 6:
                zhazda = znach;
                break;
            case 7:
                radiation = znach;
                break;
            case 8:
                life.maxlife=znach;
                ld = life.maxlife / 40f;
                if(life.life>life.maxlife)life.life=life.maxlife;
                break;
        }
    }
    void setImgOdeto(int tip){
        switch(tip){
            case 0:
                heroTexture=Util.bandit;
                break;
            case 1:
                heroTexture=Util.dolg;
                break;
            case 16:
                heroTexture=Util.nebo;
                break;
            case 17:
                heroTexture=Util.ekza;
                break;
            case 25:
                heroTexture=Util.stalker;
                break;
            case 26:
                heroTexture=Util.seva;
                break;
            case 32:
                heroTexture=Util.maks;
                break;
            case 33:
                heroTexture=Util.paha;
                break;
            case 34:
                heroTexture=Util.roma;
                break;
            default:
                heroTexture=Util.defult;
                break;
        }
        tmp = TextureRegion.split(heroTexture, heroTexture.getWidth() / COLUMNS, heroTexture.getHeight() / ROWS);
        tmp2 = new TextureRegion[COLUMNS * ROWS];
        int index = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                tmp2[index++] = tmp[i][j];
            }
        }
//ходьба
        TextureRegion[]walkUp = new TextureRegion[7];
        walkUp[0] = tmp2[57];
        walkUp[1] = tmp2[58];
        walkUp[2] = tmp2[59];
        walkUp[3] = tmp2[60];
        walkUp[4] = tmp2[61];
        walkUp[5] = tmp2[62];
        walkUp[6] = tmp2[63];
       Animation wup = new Animation(0.15f, walkUp);
        TextureRegion[]walkRight = new TextureRegion[7];
        walkRight[0] = tmp2[171];
        walkRight[1] = tmp2[172];
        walkRight[2] = tmp2[173];
        walkRight[3] = tmp2[174];
        walkRight[4] = tmp2[175];
        walkRight[5] = tmp2[176];
        walkRight[6] = tmp2[177];
        Animation wright = new Animation(0.15f, walkRight);
        TextureRegion[]walkDown = new TextureRegion[7];
        walkDown[0] = tmp2[0];
        walkDown[1] = tmp2[1];
        walkDown[2] = tmp2[2];
        walkDown[3] = tmp2[3];
        walkDown[4] = tmp2[4];
        walkDown[5] = tmp2[5];
        walkDown[6] = tmp2[6];
        Animation wdown = new Animation(0.15f, walkDown);
        TextureRegion[]walkLeft = new TextureRegion[7];
        walkLeft[0] = tmp2[114];
        walkLeft[1] = tmp2[115];
        walkLeft[2] = tmp2[116];
        walkLeft[3] = tmp2[117];
        walkLeft[4] = tmp2[118];
        walkLeft[5] = tmp2[119];
        walkLeft[6] = tmp2[120];
        Animation wleft = new Animation(0.15f, walkLeft);
        TextureRegion[]walkUpRight = new TextureRegion[7];
        walkUpRight[0] = tmp2[285];
        walkUpRight[1] = tmp2[286];
        walkUpRight[2] = tmp2[287];
        walkUpRight[3] = tmp2[288];
        walkUpRight[4] = tmp2[289];
        walkUpRight[5] = tmp2[290];
        walkUpRight[6] = tmp2[291];
        Animation wupright = new Animation(0.15f, walkUpRight);
        TextureRegion[]walkRightDown = new TextureRegion[7];
        walkRightDown[0] = tmp2[228];
        walkRightDown[1] = tmp2[229];
        walkRightDown[2] = tmp2[230];
        walkRightDown[3] = tmp2[231];
        walkRightDown[4] = tmp2[232];
        walkRightDown[5] = tmp2[233];
        walkRightDown[6] = tmp2[234];
        Animation wrightdown = new Animation(0.15f, walkRightDown);
        TextureRegion[]walkDownLeft = new TextureRegion[7];
        walkDownLeft[0] = tmp2[399];
        walkDownLeft[1] = tmp2[400];
        walkDownLeft[2] = tmp2[401];
        walkDownLeft[3] = tmp2[402];
        walkDownLeft[4] = tmp2[403];
        walkDownLeft[5] = tmp2[404];
        walkDownLeft[6] = tmp2[405];
        Animation wdownleft = new Animation(0.15f, walkDownLeft);
        TextureRegion[]walkLeftUp = new TextureRegion[7];
        walkLeftUp[0] = tmp2[342];
        walkLeftUp[1] = tmp2[343];
        walkLeftUp[2] = tmp2[344];
        walkLeftUp[3] = tmp2[345];
        walkLeftUp[4] = tmp2[346];
        walkLeftUp[5] = tmp2[347];
        walkLeftUp[6] = tmp2[348];
        Animation wleftup = new Animation(0.15f, walkLeftUp);
//perezaradka пистолет
        TextureRegion[]tpleft = new TextureRegion[6];
        tpleft[0] = tmp2[144];
        tpleft[1] = tmp2[145];
        tpleft[2] = tmp2[146];
        tpleft[3] = tmp2[147];
        tpleft[4] = tmp2[148];
        tpleft[5] = tmp2[149];
        Animation pleft = new Animation(0.2f, tpleft);
        TextureRegion[]tpup = new TextureRegion[6];
        tpup[0] = tmp2[87];
        tpup[1] = tmp2[88];
        tpup[2] = tmp2[89];
        tpup[3] = tmp2[90];
        tpup[4] = tmp2[91];
        tpup[5] = tmp2[92];
        Animation pup = new Animation(0.2f, tpup);
        TextureRegion[]tpdown = new TextureRegion[6];
        tpdown[0] = tmp2[30];
        tpdown[1] = tmp2[31];
        tpdown[2] = tmp2[32];
        tpdown[3] = tmp2[33];
        tpdown[4] = tmp2[34];
        tpdown[5] = tmp2[35];
        Animation pdown = new Animation(0.2f, tpdown);
        TextureRegion[]tpright = new TextureRegion[6];
        tpright[0] = tmp2[201];
        tpright[1] = tmp2[202];
        tpright[2] = tmp2[203];
        tpright[3] = tmp2[204];
        tpright[4] = tmp2[205];
        tpright[5] = tmp2[206];
        Animation pright = new Animation(0.2f, tpright);
        TextureRegion[]tpupright = new TextureRegion[6];
        tpupright[0] = tmp2[315];
        tpupright[1] = tmp2[316];
        tpupright[2] = tmp2[317];
        tpupright[3] = tmp2[318];
        tpupright[4] = tmp2[319];
        tpupright[5] = tmp2[320];
        Animation pupright = new Animation(0.2f, tpupright);
        TextureRegion[]tprightdown = new TextureRegion[6];
        tprightdown[0] = tmp2[258];
        tprightdown[1] = tmp2[259];
        tprightdown[2] = tmp2[260];
        tprightdown[3] = tmp2[261];
        tprightdown[4] = tmp2[262];
        tprightdown[5] = tmp2[263];
        Animation prightdown = new Animation(0.2f, tprightdown);
        TextureRegion[]tpdownleft = new TextureRegion[6];
        tpdownleft[0] = tmp2[429];
        tpdownleft[1] = tmp2[430];
        tpdownleft[2] = tmp2[431];
        tpdownleft[3] = tmp2[432];
        tpdownleft[4] = tmp2[433];
        tpdownleft[5] = tmp2[434];
        Animation pdownleft = new Animation(0.2f, tpdownleft);
        TextureRegion[]tpleftup = new TextureRegion[6];
        tpleftup[0] = tmp2[372];
        tpleftup[1] = tmp2[373];
        tpleftup[2] = tmp2[374];
        tpleftup[3] = tmp2[375];
        tpleftup[4] = tmp2[376];
        tpleftup[5] = tmp2[377];
        Animation pleftup = new Animation(0.2f, tpleftup);
// стрельбa пистолет
        TextureRegion[]tapup = new TextureRegion[2];
        tapup[0] = tmp2[85];
        tapup[1] = tmp2[86];
        Animation apup = new Animation(0.2f, tapup);
        TextureRegion[]tapright = new TextureRegion[2];
        tapright[0] = tmp2[199];
        tapright[1] = tmp2[200];
        Animation apright = new Animation(0.2f, tapright);
        TextureRegion[]tapdown = new TextureRegion[2];
        tapdown[0] = tmp2[28];
        tapdown[1] = tmp2[29];
        Animation apdown = new Animation(0.2f, tapdown);
        TextureRegion[]tapleft = new TextureRegion[2];
        tapleft[0] = tmp2[142];
        tapleft[1] = tmp2[143];
        Animation apleft = new Animation(0.2f, tapleft);
        TextureRegion[]tapupright = new TextureRegion[2];
        tapupright[0] = tmp2[313];
        tapupright[1] = tmp2[314];
        Animation apupright = new Animation(0.2f, tapupright);
        TextureRegion[]taprightdown = new TextureRegion[2];
        taprightdown[0] = tmp2[256];
        taprightdown[1] = tmp2[257];
        Animation aprightdown = new Animation(0.2f, taprightdown);
        TextureRegion[]tapdownleft = new TextureRegion[2];
        tapdownleft[0] = tmp2[427];
        tapdownleft[1] = tmp2[428];
        Animation apdownleft = new Animation(0.2f, tapdownleft);
        TextureRegion[]tapleftup = new TextureRegion[2];
        tapleftup[0] = tmp2[370];
        tapleftup[1] = tmp2[371];
        Animation apleftup = new Animation(0.2f, tapleftup);

        //perezaradka автомат
        TextureRegion[]tplefta = new TextureRegion[7];
        tplefta[0] = tmp2[153];
        tplefta[1] = tmp2[154];
        tplefta[2] = tmp2[155];
        tplefta[3] = tmp2[156];
        tplefta[4] = tmp2[157];
        tplefta[5] = tmp2[158];
        tplefta[6] = tmp2[159];
        Animation plefta = new Animation(0.2f, tplefta);
        TextureRegion[]tpupa = new TextureRegion[7];
        tpupa[0] = tmp2[96];
        tpupa[1] = tmp2[97];
        tpupa[2] = tmp2[98];
        tpupa[3] = tmp2[99];
        tpupa[4] = tmp2[100];
        tpupa[5] = tmp2[101];
        tpupa[6] = tmp2[102];
        Animation pupa = new Animation(0.2f, tpupa);
        TextureRegion[]tpdowna = new TextureRegion[7];
        tpdowna[0] = tmp2[39];
        tpdowna[1] = tmp2[40];
        tpdowna[2] = tmp2[41];
        tpdowna[3] = tmp2[42];
        tpdowna[4] = tmp2[43];
        tpdowna[5] = tmp2[44];
        tpdowna[6] = tmp2[45];
        Animation pdowna = new Animation(0.2f, tpdowna);
        TextureRegion[]tprighta = new TextureRegion[7];
        tprighta[0] = tmp2[210];
        tprighta[1] = tmp2[211];
        tprighta[2] = tmp2[212];
        tprighta[3] = tmp2[213];
        tprighta[4] = tmp2[214];
        tprighta[5] = tmp2[215];
        tprighta[6] = tmp2[216];
        Animation prighta = new Animation(0.2f, tprighta);
        TextureRegion[]tpuprighta = new TextureRegion[7];
        tpuprighta[0] = tmp2[324];
        tpuprighta[1] = tmp2[325];
        tpuprighta[2] = tmp2[326];
        tpuprighta[3] = tmp2[327];
        tpuprighta[4] = tmp2[328];
        tpuprighta[5] = tmp2[329];
        tpuprighta[6] = tmp2[330];
        Animation puprighta = new Animation(0.2f, tpuprighta);
        TextureRegion[]tprightdowna = new TextureRegion[7];
        tprightdowna[0] = tmp2[267];
        tprightdowna[1] = tmp2[268];
        tprightdowna[2] = tmp2[269];
        tprightdowna[3] = tmp2[270];
        tprightdowna[4] = tmp2[271];
        tprightdowna[5] = tmp2[272];
        tprightdowna[6] = tmp2[273];
        Animation prightdowna = new Animation(0.2f, tprightdowna);
        TextureRegion[]tpdownlefta = new TextureRegion[7];
        tpdownlefta[0] = tmp2[438];
        tpdownlefta[1] = tmp2[439];
        tpdownlefta[2] = tmp2[440];
        tpdownlefta[3] = tmp2[441];
        tpdownlefta[4] = tmp2[442];
        tpdownlefta[5] = tmp2[443];
        tpdownlefta[6] = tmp2[444];
        Animation pdownlefta = new Animation(0.2f, tpdownlefta);
        TextureRegion[]tpleftupa = new TextureRegion[7];
        tpleftupa[0] = tmp2[381];
        tpleftupa[1] = tmp2[382];
        tpleftupa[2] = tmp2[383];
        tpleftupa[3] = tmp2[384];
        tpleftupa[4] = tmp2[385];
        tpleftupa[5] = tmp2[386];
        tpleftupa[6] = tmp2[387];
        Animation pleftupa = new Animation(0.2f, tpleftupa);
// стрельбa автомат
        TextureRegion[]tapupa = new TextureRegion[3];
        tapupa[0] = tmp2[93];
        tapupa[1] = tmp2[94];
        tapupa[2] = tmp2[95];
        Animation apupa = new Animation(0.2f, tapupa);
        TextureRegion[]taprighta = new TextureRegion[3];
        taprighta[0] = tmp2[207];
        taprighta[1] = tmp2[208];
        taprighta[2] = tmp2[209];
        Animation aprighta = new Animation(0.2f, taprighta);
        TextureRegion[]tapdowna = new TextureRegion[3];
        tapdowna[0] = tmp2[36];
        tapdowna[1] = tmp2[37];
        tapdowna[2] = tmp2[38];
        Animation apdowna = new Animation(0.2f, tapdowna);
        TextureRegion[]taplefta = new TextureRegion[3];
        taplefta[0] = tmp2[150];
        taplefta[1] = tmp2[151];
        taplefta[2] = tmp2[152];
        Animation aplefta = new Animation(0.2f, taplefta);
        TextureRegion[]tapuprighta = new TextureRegion[3];
        tapuprighta[0] = tmp2[321];
        tapuprighta[1] = tmp2[322];
        tapuprighta[2] = tmp2[323];
        Animation apuprighta = new Animation(0.2f, tapuprighta);
        TextureRegion[]taprightdowna = new TextureRegion[3];
        taprightdowna[0] = tmp2[264];
        taprightdowna[1] = tmp2[265];
        taprightdowna[2] = tmp2[266];
        Animation aprightdowna = new Animation(0.2f, taprightdowna);
        TextureRegion[]tapdownlefta = new TextureRegion[3];
        tapdownlefta[0] = tmp2[435];
        tapdownlefta[1] = tmp2[436];
        tapdownlefta[2] = tmp2[437];
        Animation apdownlefta = new Animation(0.2f, tapdownlefta);
        TextureRegion[]tapleftupa = new TextureRegion[3];
        tapleftupa[0] = tmp2[378];
        tapleftupa[1] = tmp2[379];
        tapleftupa[2] = tmp2[380];
        Animation apleftupa = new Animation(0.2f, tapleftupa);

        //смерь
        TextureRegion[]tdeadup = new TextureRegion[4];
        tdeadup[0] = tmp2[77];
        tdeadup[1] = tmp2[78];
        tdeadup[2] = tmp2[79];
        tdeadup[3] = tmp2[80];
        Animation dup = new Animation(0.2f, tdeadup);
        TextureRegion[]tdeadright = new TextureRegion[4];
        tdeadright[0] = tmp2[191];
        tdeadright[1] = tmp2[192];
        tdeadright[2] = tmp2[193];
        tdeadright[3] = tmp2[194];
        Animation dright = new Animation(0.2f, tdeadright);
        TextureRegion[]tdeaddown = new TextureRegion[4];
        tdeaddown[0] = tmp2[20];
        tdeaddown[1] = tmp2[21];
        tdeaddown[2] = tmp2[22];
        tdeaddown[3] = tmp2[23];
        Animation ddown = new Animation(0.2f, tdeaddown);
        TextureRegion[]tdeadleft = new TextureRegion[4];
        tdeadleft[0] = tmp2[134];
        tdeadleft[1] = tmp2[135];
        tdeadleft[2] = tmp2[136];
        tdeadleft[3] = tmp2[137];
        Animation dleft = new Animation(0.2f, tdeadleft);
        TextureRegion[]tdeadupright = new TextureRegion[4];
        tdeadupright[0] = tmp2[305];
        tdeadupright[1] = tmp2[306];
        tdeadupright[2] = tmp2[307];
        tdeadupright[3] = tmp2[308];
        Animation dupright = new Animation(0.2f, tdeadupright);
        TextureRegion[]tdeadrightdown = new TextureRegion[4];
        tdeadrightdown[0] = tmp2[248];
        tdeadrightdown[1] = tmp2[249];
        tdeadrightdown[2] = tmp2[250];
        tdeadrightdown[3] = tmp2[251];
        Animation drightdown = new Animation(0.2f, tdeadrightdown);
        TextureRegion[]tdeaddownleft = new TextureRegion[4];
        tdeaddownleft[0] = tmp2[419];
        tdeaddownleft[1] = tmp2[420];
        tdeaddownleft[2] = tmp2[421];
        tdeaddownleft[3] = tmp2[422];
        Animation ddownleft = new Animation(0.2f, tdeaddownleft);
        TextureRegion[]tdeadleftup = new TextureRegion[4];
        tdeadleftup[0] = tmp2[362];
        tdeadleftup[1] = tmp2[363];
        tdeadleftup[2] = tmp2[364];
        tdeadleftup[3] = tmp2[365];
        Animation dleftup = new Animation(0.2f, tdeadleftup);
        //болт
        TextureRegion[]tboltup=new TextureRegion[4];
        tboltup[0] = tmp2[81];
        tboltup[1] = tmp2[82];
        tboltup[2] = tmp2[83];
        tboltup[3] = tmp2[84];
        Animation bup = new Animation(0.1f, tboltup);
        TextureRegion[]tboltright=new TextureRegion[4];
        tboltright[0] = tmp2[195];
        tboltright[1] = tmp2[196];
        tboltright[2] = tmp2[197];
        tboltright[3] = tmp2[198];
        Animation bright = new Animation(0.1f, tboltright);
        TextureRegion[]tboltdown=new TextureRegion[4];
        tboltdown[0] = tmp2[24];
        tboltdown[1] = tmp2[25];
        tboltdown[2] = tmp2[26];
        tboltdown[3] = tmp2[27];
        Animation bdown = new Animation(0.1f, tboltdown);
        TextureRegion[]tboltleft=new TextureRegion[4];
        tboltleft[0] = tmp2[138];
        tboltleft[1] = tmp2[139];
        tboltleft[2] = tmp2[140];
        tboltleft[3] = tmp2[141];
        Animation bleft = new Animation(0.1f, tboltleft);

        TextureRegion[]tboltupright=new TextureRegion[4];
        tboltupright[0] = tmp2[309];
        tboltupright[1] = tmp2[310];
        tboltupright[2] = tmp2[311];
        tboltupright[3] = tmp2[312];
        Animation  bupright = new Animation(0.1f, tboltupright);
        TextureRegion[]tboltrightdown=new TextureRegion[4];
        tboltrightdown[0] = tmp2[252];
        tboltrightdown[1] = tmp2[253];
        tboltrightdown[2] = tmp2[254];
        tboltrightdown[3] = tmp2[255];
        Animation brightdown = new Animation(0.1f, tboltrightdown);
        TextureRegion[]tboltdownleft=new TextureRegion[4];
        tboltdownleft[0] = tmp2[423];
        tboltdownleft[1] = tmp2[424];
        tboltdownleft[2] = tmp2[425];
        tboltdownleft[3] = tmp2[426];
        Animation bdownleft = new Animation(0.1f, tboltdownleft);
        TextureRegion[]tboltleftup=new TextureRegion[4];
        tboltleftup[0] = tmp2[366];
        tboltleftup[1] = tmp2[367];
        tboltleftup[2] = tmp2[368];
        tboltleftup[3] = tmp2[369];
        Animation bleftup = new Animation(0.1f, tboltleftup);
//садится
        TextureRegion[]tsitup=new TextureRegion[4];
        tsitup[0] = tmp2[67];
        tsitup[1] = tmp2[68];
        tsitup[2] = tmp2[69];
        tsitup[3] = tmp2[70];
        Animation sup = new Animation(0.1f, tsitup);
        TextureRegion[]tsitright=new TextureRegion[4];
        tsitright[0] = tmp2[181];
        tsitright[1] = tmp2[182];
        tsitright[2] = tmp2[183];
        tsitright[3] = tmp2[184];
        Animation sright = new Animation(0.1f, tsitright);
        TextureRegion[]tsitdown=new TextureRegion[4];
        tsitdown[0] = tmp2[10];
        tsitdown[1] = tmp2[11];
        tsitdown[2] = tmp2[12];
        tsitdown[3] = tmp2[13];
        Animation sdown = new Animation(0.1f, tsitdown);
        TextureRegion[]tsitleft=new TextureRegion[4];
        tsitleft[0] = tmp2[124];
        tsitleft[1] = tmp2[125];
        tsitleft[2] = tmp2[126];
        tsitleft[3] = tmp2[127];
        Animation sleft = new Animation(0.1f, tsitleft);

        TextureRegion[]tsitupright=new TextureRegion[4];
        tsitupright[0] = tmp2[295];
        tsitupright[1] = tmp2[296];
        tsitupright[2] = tmp2[297];
        tsitupright[3] = tmp2[298];
        Animation supright = new Animation(0.1f, tsitupright);
        TextureRegion[]tsitrigjtdown=new TextureRegion[4];
        tsitrigjtdown[0] = tmp2[238];
        tsitrigjtdown[1] = tmp2[239];
        tsitrigjtdown[2] = tmp2[240];
        tsitrigjtdown[3] = tmp2[241];
        Animation srightdown = new Animation(0.1f, tsitrigjtdown);
        TextureRegion[]tsitdownleft=new TextureRegion[4];
        tsitdownleft[0] = tmp2[409];
        tsitdownleft[1] = tmp2[410];
        tsitdownleft[2] = tmp2[411];
        tsitdownleft[3] = tmp2[412];
        Animation sdownleft = new Animation(0.1f, tsitdownleft);
        TextureRegion[]tsitleftup=new TextureRegion[4];
        tsitleftup[0] = tmp2[352];
        tsitleftup[1] = tmp2[353];
        tsitleftup[2] = tmp2[354];
        tsitleftup[3] = tmp2[355];
        Animation sleftup = new Animation(0.1f, tsitleftup);
        //гитара
        TextureRegion[]gitup=new TextureRegion[3];
        gitup[0] = tmp2[74];
        gitup[1] = tmp2[75];
        gitup[2] = tmp2[76];
        Animation gup = new Animation(0.2f, gitup);
        gup.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        TextureRegion[]gitright=new TextureRegion[3];
        gitright[0] = tmp2[188];
        gitright[1] = tmp2[189];
        gitright[2] = tmp2[190];
        Animation gright = new Animation(0.2f, gitright);
        gright.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        TextureRegion[]gitdown=new TextureRegion[3];
        gitdown[0] = tmp2[17];
        gitdown[1] = tmp2[18];
        gitdown[2] = tmp2[19];
        Animation gdown = new Animation(0.2f, gitdown);
        gdown.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        TextureRegion[]gitleft=new TextureRegion[3];
        gitleft[0] = tmp2[131];
        gitleft[1] = tmp2[132];
        gitleft[2] = tmp2[133];
        Animation gleft = new Animation(0.2f, gitleft);
        gleft.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        TextureRegion[]gitupright=new TextureRegion[3];
        gitupright[0] = tmp2[302];
        gitupright[1] = tmp2[303];
        gitupright[2] = tmp2[304];
        Animation gupright = new Animation(0.2f, gitupright);
        gupright.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        TextureRegion[]gitrigjtdown=new TextureRegion[3];
        gitrigjtdown[0] = tmp2[245];
        gitrigjtdown[1] = tmp2[246];
        gitrigjtdown[2] = tmp2[247];
        Animation grightdown = new Animation(0.2f, gitrigjtdown);
        grightdown.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        TextureRegion[]gitdownleft=new TextureRegion[3];
        gitdownleft[0] = tmp2[416];
        gitdownleft[1] = tmp2[417];
        gitdownleft[2] = tmp2[418];
        Animation gdownleft = new Animation(0.2f, gitdownleft);
        gdownleft.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        TextureRegion[]gitleftup=new TextureRegion[3];
        gitleftup[0] = tmp2[359];
        gitleftup[1] = tmp2[360];
        gitleftup[2] = tmp2[361];
        Animation gleftup = new Animation(0.2f, gitleftup);
        gleftup.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        die = tmp2[125];
        ajumpup=tmp2[58];
        ajumpright=tmp2[50];
        ajumpdown=tmp2[46];
        ajumpleft=tmp2[54];
        wlakanim=new Animation []{wup, wright, wdown, wleft, wupright, wrightdown, wdownleft, wleftup};
        perezanim=new Animation []{pup, pright,pdown,pleft,pupright,prightdown,pdownleft,pleftup};
        perezaanim=new Animation []{pupa, prighta,pdowna,plefta,puprighta,prightdowna,pdownlefta,pleftupa};
        atakpistanim=new Animation []{apup,apright,apdown,apleft,apupright,aprightdown,apdownleft,apleftup};
        atakavtanim=new Animation []{apupa,aprighta,apdowna,aplefta,apuprighta,aprightdowna,apdownlefta,apleftupa};
        deadanim=new Animation []{dup,dright,ddown,dleft,dupright,drightdown,ddownleft,dleftup};
        boltanim=new Animation []{bup,bright,bdown,bleft,bupright,brightdown,bdownleft,bleftup};
        sitinganim=new Animation []{sup,sright,sdown,sleft,supright,srightdown,sdownleft,sleftup};
        gitaranim=new Animation []{gup,gright,gdown,gleft,gupright,grightdown,gdownleft,gleftup};
        imgfirenaprav=new TextureRegion[]{tmp2[85],tmp2[199],tmp2[28],tmp2[142],tmp2[313],tmp2[256],tmp2[427],tmp2[370]};
        imgstandnaprav=new TextureRegion[]{tmp2[66],tmp2[180],tmp2[9],tmp2[123],tmp2[294],tmp2[237],tmp2[408],tmp2[351]};
        scalex=0.50f;
        scaley=0.50f;
        height=walkUp[0].getRegionHeight();
        width=walkUp[0].getRegionWidth();
        stand=imgstandnaprav[2];
        hero=stand;
    }
}
