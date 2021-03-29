package GameWorld;

import Anomaly.*;
import GameInterface.Ginterface;
import InventItem.Item;
import Map.Imagee;
import Map.Map;
import MapObjectss.*;
import Modules.AnomalyJump;
import Modules.Teleport;
import Unit.*;
import Unit.Hero.Runtip;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.utils.Array;
import com.blackbirds.projectzone.GdxGame;
import util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class Game implements Screen {
   public GdxGame game;
   public static boolean tochm=true;
    public SpatialHashGrid grid;
    WorldState worldstate;
    public static Hero hero;
    public Ginterface ginterface;
    Vector3 touch = new Vector3();
    float tx, ty;
    // вся графика карты
    Array<Imagee> tr = new Array<Imagee>();
    // графика которую надо рисовать (рисуется только та чать графики которая находится около героя)
    private Array<Imagee> images = new Array<Imagee>();

    public HashMap<Long, MapObject> mapobjects = new HashMap<Long, MapObject>();
    HashMap<Long, Player> players = new HashMap<Long, Player>();
    Array<Puli> puli = new Array<Puli>();
    Array<ClientMapObject> clientMapObjects = new Array<ClientMapObject>();
    List<Rectang> rec = new ArrayList<Rectang>();
    String oldservermsg, oldatak = "a";
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private ShapeRenderer shape;
    private Map level;
    private Batch spriteBatch;
    private Rectangle scissors, clipBounds;
    private Vibros vibros;
    private boolean load = true;
    public static boolean  rain=false;
    boolean endrain;
    int strongrain,timeplusrain=0;
    Texture imgrain[]=new Texture[66];
    public static boolean rbolt=false;
    String messagg;
    //наступаем на нпс открываем диалог
    Rectang npcrec;
    boolean opendialog;

    public Game(GdxGame game, String str) {
        this.game = game;
        // create hero
        String tmp[] = str.split("/");
        long id = Long.parseLong(tmp[1]);
        float x = Float.parseFloat(tmp[2]);
        float y = Float.parseFloat(tmp[3]);

        int odeto= Integer.parseInt(tmp[16]);
        hero = new Hero(x, y, id, tmp[4], game.text.textName, game.text.minusuron,this,odeto);

        hero.life.life = Float.parseFloat(tmp[5]);
        hero.speed = Float.parseFloat(tmp[6]);
        hero.uron = Float.parseFloat(tmp[7]);
        hero.holod = Float.parseFloat(tmp[8]);
        hero.golod = Float.parseFloat(tmp[9]);
        hero.zhazda = Float.parseFloat(tmp[10]);
        hero.rad = Float.parseFloat(tmp[11]);
        hero.krovotech = Float.parseFloat(tmp[12]);
        hero.timedie = Integer.parseInt(tmp[13]);
        hero.addHashMapQvest(tmp[14]);
        hero.idmap = Integer.parseInt(tmp[15]);
        if (hero.timedie > 0) {
            hero.state = Util.DIED;
            hero.ldt = System.currentTimeMillis() + hero.timedie;
        }
        game.tout.sendMsg("1/9/1");
        worldstate = new WorldState(mapobjects);
        ginterface = new Ginterface(game,this);
        hero.setHarakteristic(8,Integer.parseInt(tmp[17]));
        if(!tmp[18].equals("null")){hero.klanname=new Label(tmp[18],game.text.textName);
        hero.klanname.setColor(Color.SKY);}
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 480, 320);
        camera.update();
        switchMap(hero.idmap,(int)x,(int)y);
        Gdx.input.setCatchBackKey(true);
        spriteBatch = renderer.getBatch();
        scissors = new Rectangle();
        clipBounds = new Rectangle(0, 0, 480, 320);
        shape = new ShapeRenderer();
        //возможно лучше отключить и сделать круг на флаге картинкой
        shape.setAutoShapeType(true);
        vibros = new Vibros();
        for(int i=0;i<imgrain.length;i++){
            imgrain[i]=new Texture("rain.png");
        }
    }

    @Override
    public void render(float delta) {
        try {
            worldstate.run(delta);
            //удаление обьектов
            for (MapObject mo : mapobjects.values()) {
                if (!Overlap.pointPoint(hero.centx, hero.centy, mo.centx, mo.centy,mo.raddclient+50)) {
                    mapobjects.remove(mo.id);
                    if (mo.tip == 1)tr.removeValue(players.remove(mo.id).bolt,false);
                    tr.removeValue(mo, false);
                    images.removeValue(mo,false);
                    break;
                }
            }


            for(Imagee img : tr){
                if(Overlap.pointPoint(img.x + img.width / 2, img.y + img.height / 2, camera.position.x, camera.position.y, 400)){
                    images.add(img);
                    tr.removeValue(img, false);
                }
            }
            // удаляем ту графику которую уже не надо отрисовывать (игрок отошел от нее)
            for(Imagee img : images){
                if( ! Overlap.pointPoint(img.x + img.width / 2, img.y + img.height / 2, camera.position.x, camera.position.y, 400)){
                    tr.add(img);
                    images.removeValue(img, false);
                }
            }


            dellPuli();
            getserverMsg();
            // логика клиентских объектов карты (в том числе и анимация)
            for (ClientMapObject cmo : clientMapObjects) {cmo.run(delta);}
            if(!ginterface.pda.isVisible()&&!ginterface.dialog.isVisible()){if (!game.android)controlKey();}
          //  if (tochm) tochMap();
            for (MapObject mo : mapobjects.values()) {
                mo.run(delta);
            }
            //плюс есть координаты в классе выброса
            if (vibros.collitter != 1) {
                if (hero.x > 216 && hero.x < 1336) {
                    camera.position.x = hero.x + 23.5f;
                }
                if (hero.y > 140 && hero.y < 1420) {
                    camera.position.y = hero.y + 19.5f;
                }
            }
            sortZIndexF();
            if (vibros.vibros) vibros.run(mapobjects.values(), delta);
            if (!hero.vdome) setServerMsgDvig();
            dell();
            shape.setProjectionMatrix(camera.combined);
            renderer.setView(camera);
            camera.update();
            clipBounds.set(camera.position.x - 240, camera.position.y - 160, 480, 320);
            ScissorStack.calculateScissors(camera, spriteBatch.getTransformMatrix(), clipBounds, scissors);
            ScissorStack.pushScissors(scissors);
            renderer.render();
            spriteBatch.begin();
            for (Puli pul : puli) {
                pul.update(delta);
                spriteBatch.draw(pul.pul, pul.x, pul.y);
            }
            for (Imagee img : images) {
                if(img.visibl)spriteBatch.draw(img, img.x, img.y, img.originx, img.originy, img.width, img.height, img.scalex, img.scaley, img.rotate);
            }
            if(rain)rain();
            for(MapObject mo:mapobjects.values()){
                mo.dopDraw(spriteBatch,shape);
            }
            for(ClientMapObject cmo:clientMapObjects){
                cmo.dopDraw(spriteBatch,shape);
            }
            if (vibros.vibros) vibros.drawVibros(spriteBatch, hero, camera);
            if (vibros.red) vibros.stbColor(spriteBatch);
            spriteBatch.end();
            spriteBatch.flush();
            ScissorStack.popScissors();
            shape.begin(ShapeType.Filled);
            lifeRend();
            shape.end();
            ginterface.run();
            poiskPredmetov();
            //перенести в hero
            if(hero.nastupKorobka!=null){if(!Overlap.overlapRectang(hero.rectang,hero.nastupKorobka.rectang))hero.nastupKorobka=null;}
            //переход на другую локацию
            // думаю можно убрать и сделатиь все на сервере
            if(!hero.sendgoloc){
                if(hero.rectang.x>1587&&(hero.idmap+1)%5!=0)hero.sendgoloc=true;
                else
                if(hero.rectang.x<2&&hero.idmap!=0&&hero.idmap%5!=0)hero.sendgoloc=true;
                else
               if(hero.rectang.y>1560&&hero.idmap>4)hero.sendgoloc=true;
               else
               if(hero.rectang.y<2&&hero.idmap<15)hero.sendgoloc=true;
                if(hero.sendgoloc)game.tout.sendMsg("1/13/");
            }
        } catch (Exception e) {
            //FileHandle file = Gdx.files.local("ProjectZoneError.txt");
            String err = "\n";
            for (StackTraceElement ste : e.getStackTrace()) {
                err += "Class name -" + ste.getClassName() + " Line -" + ste.getLineNumber() + "\n";
            }
            game.tout.sendMsg("err:" + hero.name.getText() + " - " + e.toString() + " Message -" + e.getMessage() + err+messagg);
            e.printStackTrace();
            //file.writeString("EXCEPTION -"+e.toString()+" Message -"+e.getMessage()+err ,false);//изменить на true перед тестом
            Gdx.app.exit();
        }
    }

    void getserverMsg() {
        while (!game.tin.sQueue.isEmpty()) {
            messagg=game.tin.sQueue.poll();
            String str[] = messagg.split("/");
            int tip = Integer.parseInt(str[0]);
            switch (tip) {
                //addNewMapObject(1 tip id x y)
                case 1:
                    if(str.length<5)return;
                    int tip2 = Integer.parseInt(str[1]);
                    long zid = Long.parseLong(str[2]);
                    int zx = Integer.parseInt(str[3]);
                    int zy = Integer.parseInt(str[4]);
                    switch (tip2) {
                        //player
                        case 1:
                            if(str.length<11)return;
                            if (mapobjects.get(zid) == null) {
                                Player player = new Player(zx, zy, zid, str[5], game.text.textName, game.text.minusuron, this,Integer.parseInt(str[9]));
                                player.speed = Integer.parseInt(str[6]);
                                player.life.life=Integer.parseInt(str[7]);
                                player.radiation=Float.parseFloat(str[8]);
                                player.setHarakteristic(8,(int)Float.parseFloat(str[10]));
                                if(!str[11].equals("null")){player.klanname=new Label(str[11],game.text.textName);
                                    player.klanname.setColor(Color.SKY);}
                                addNewTr(player);
                                addNewTr(player.bolt);
                                players.put(player.id, player);
                                mapobjects.put(player.id, player);
                            }
                            break;
                        //кабан
                        case 2:
                            if(str.length<6)return;
                            if (mapobjects.get(zid) == null) {
                                Kaban kaban = new Kaban(zx, zy, zid, game.text.minusuron, this);
                                kaban.life.life=Integer.parseInt(str[5]);
                                addNewTr(kaban);
                                mapobjects.put(kaban.id, kaban);
                            }
                            break;
                        //artmgla
                        case 3:
                            if (mapobjects.get(zid) == null) {
                                ArtObjectMgla art = new ArtObjectMgla(zx, zy, zid);
                                addNewTr(art);
                                mapobjects.put(art.id, art);
                            }
                            break;
                        //electra1
                        case 4:
                            if (mapobjects.get(zid) == null) {
                                Electra1 electra1 = new Electra1(zx, zy, zid,mapobjects);
                                mapobjects.put(electra1.id, electra1);
                                addNewTr(electra1);
                            }
                            break;
                        //dvigelectra
                        case 5:
                            if(str.length<6)return;
                            if (mapobjects.get(zid) == null) {
                                DvigElectra1 electra = new DvigElectra1(zx, zy, zid,mapobjects,Integer.parseInt(str[5]));
                                addNewTr(electra);
                                mapobjects.put(electra.id, electra);
                            }
                            break;
                        //electra2
                        case 6:
                            if (mapobjects.get(zid) == null) {
                                Electra2 electra2 = new Electra2(zx, zy, zid,mapobjects);
                                mapobjects.put(electra2.id, electra2);
                                addNewTr(electra2);
                            }
                            break;
                        //zombi
                        case 7:
                            if(str.length<6)return;
                            if (mapobjects.get(zid) == null) {
                                Zombi zombi = new Zombi(zx, zy, zid, game.text.minusuron, this);
                                zombi.life.life=Integer.parseInt(str[5]);
                                addNewTr(zombi);
                                mapobjects.put(zombi.id, zombi);
                            }
                            break;
                        //npc
                        case 8:
                            if(str.length<7)return;
                            Npc npc = new Npc(zx, zy, zid,this,game.text.textName,Integer.parseInt(str[5]),str[6],ginterface);
                            addNewTr(npc);
                            mapobjects.put(npc.id, npc);
                            break;
                        //tipqvest
                        case 9:
                            TipQvets tipqvest = new TipQvets(zx, zy, zid, game.text.minusuron, game.text.textName);
                            addNewTr(tipqvest);
                            mapobjects.put(tipqvest.id, tipqvest);
                            break;
                        //object akkom
                        case 10:
                            if(str.length<6)return;
                            ObjectAkkom akk = new ObjectAkkom(zx, zy, zid,str[5]);
                            addNewTr(akk);
                            mapobjects.put(akk.id, akk);
                            break;
                        //object binokl
                        case 11:
                            ObdjBinokl bin = new ObdjBinokl(zx, zy, zid);
                            addNewTr(bin);
                            mapobjects.put(bin.id, bin);
                            break;
                        //korobka
                        case 12:
                            if (mapobjects.get(zid) == null) {
                            Korobka kor = new Korobka(zx, zy, zid,Integer.parseInt(str[5]));
                            addNewTr(kor);
                            mapobjects.put(kor.id, kor);
                            if(Overlap.overlapRectang(hero.rectang,kor.rectang))hero.nastupKorobka=kor;
                            }
                            break;
                        //koster
                        case 13:
                            if (mapobjects.get(zid) == null) {
                                Koster kostr = new Koster(zx, zy, zid,Long.parseLong(str[5]),players.values());
                                addNewTr(kostr);
                                mapobjects.put(kostr.id, kostr);
                            }
                            break;
                        //krusa
                        case 14:
                            if(str.length<6)return;
                            if (mapobjects.get(zid) == null) {
                                Friger krusa = new Friger(zx, zy, zid, game.text.minusuron, this);
                                krusa.life.life=Integer.parseInt(str[5]);
                                addNewTr(krusa);
                                mapobjects.put(krusa.id, krusa);
                            }
                            break;
                        //kisoblako
                        case 15:
                            if(str.length<7)return;
                            if (mapobjects.get(zid) == null) {
                                KisOblako oblako = new KisOblako(zx, zy, zid,mapobjects,Integer.parseInt(str[5]));
                                oblako.timeuron=Float.parseFloat(str[6]);
                                addNewTr(oblako);
                                mapobjects.put(oblako.id, oblako);
                            }
                            break;
                        //impuls
                        case 16:
                            if (mapobjects.get(zid) == null) {
                                Impuls batut = new Impuls(zx, zy, zid,mapobjects);
                                mapobjects.put(batut.id, batut);
                                addNewTr(batut);
                            }
                            break;
                        //lipuchka
                        case 17:
                            if (mapobjects.get(zid) == null) {
                                Lipuchka lip = new Lipuchka(zx, zy, zid,mapobjects);
                                mapobjects.put(lip.id, lip);
                                addNewTr(lip);
                            }
                            break;
                        //vkoster
                        case 18:
                            if (mapobjects.get(zid) == null) {
                                Vkoster vkostr = new Vkoster(zx, zy, zid,players.values());
                                mapobjects.put(vkostr.id, vkostr);
                                addNewTr(vkostr);
                            }
                            break;
                        //artveter
                        case 19:case 31:case 32:
                            if (mapobjects.get(zid) == null) {
                                ArtObjectVeter art = new ArtObjectVeter(zx, zy, zid);
                                if(str.length>5)art.timevis=Float.parseFloat(str[5]);
                                if(tip2==32)art.vis=true;
                                addNewTr(art);
                                mapobjects.put(art.id, art);
                            }
                            break;
                        //artiskra
                        case 20:
                            if (mapobjects.get(zid) == null) {
                                ArtObjectIskra art = new ArtObjectIskra(zx, zy, zid);
                                if(str.length>5)art.timevis=Float.parseFloat(str[5]);
                                addNewTr(art);
                                mapobjects.put(art.id, art);
                            }
                            break;
                        //arthart
                        case 21:case 29:case 30:
                            if(str.length<6)return;
                            if (mapobjects.get(zid) == null) {
                                ArtObjectHeart art = new ArtObjectHeart(zx, zy, zid,Integer.parseInt(str[5]));
                                addNewTr(art);
                                mapobjects.put(art.id, art);
                            }
                            break;
                        //artblood
                        case 22:case 27:case 28:
                            if (mapobjects.get(zid) == null) {
                                ArtObjectBlood art = new ArtObjectBlood(zx, zy, zid);
                                if(str.length>5)art.timevis=Float.parseFloat(str[5]);
                                if(tip2==28)art.vis=true;
                                addNewTr(art);
                                mapobjects.put(art.id, art);
                            }
                            break;
                        //randitem
                        case 23:
                            if (mapobjects.get(zid) == null) {
                                int tremov=0;
                                if(str.length>5)tremov=Integer.parseInt(str[5]);
                            RandomMapItem rmi = new RandomMapItem(zx, zy, zid,tremov);
                            addNewTr(rmi);
                            mapobjects.put(rmi.id, rmi);}
                            break;
                        //radiation
                        case 24:
                            if(str.length<7)return;
                            if (mapobjects.get(zid) == null) {
                                Radiation rad = new Radiation(zx, zy, zid,Integer.parseInt(str[5]),Float.parseFloat(str[6]));
                                mapobjects.put(rad.id, rad);
                            }
                            break;
                            //control flag
                        case 25:
                            if (mapobjects.get(zid) == null) {
                                ControlMap cm = new ControlMap(zx, zy, zid,players.values(),game.text.minusuron);
                                addNewTr(cm);
                                mapobjects.put(cm.id, cm);
                                cm.zahvat=Boolean.parseBoolean(str[5]);
                                cm.setState(1,str);
                            }
                            break;
                            // kaban boss
                        case 26:
                            if (mapobjects.get(zid) == null) {
                                BosKaban boskaban = new BosKaban(zx, zy, zid, game.text.minusuron, this);
                                boskaban.life.life=Integer.parseInt(str[5]);
                                boskaban.speed=Float.parseFloat(str[6]);
                                addNewTr(boskaban);
                                mapobjects.put(boskaban.id, boskaban);
                            }
                            break;
                        //impuls
                        case 33:
                            if (mapobjects.get(zid) == null) {
                                Impuls2 batut2 = new Impuls2(zx, zy, zid,mapobjects);
                                mapobjects.put(batut2.id, batut2);
                                addNewTr(batut2);
                            }
                            break;
                        case 34:case 35:case 36:
                            if (mapobjects.get(zid) == null) {
                                ArtObjectObereg art = new ArtObjectObereg(zx, zy, zid);
                                if(str.length>5)art.timevis=Float.parseFloat(str[5]);
                                if(tip2==36)art.vis=true;
                                addNewTr(art);
                                mapobjects.put(art.id, art);
                            }
                            break;
                    }
                    break;
                //refreshMapobject
                case 2:
                    if(str.length<3)return;
                    MapObject mo = mapobjects.get(Long.parseLong(str[2]));
                    if(mo!=null)mo.setState(Integer.parseInt(str[1]), str);
                    else System.out.println("ERROR ERROR mapobject==null");
                    break;
                    // mo life
                case 3:
                    if(str.length<3)return;
                    MapObject mo2 = mapobjects.get(Long.parseLong(str[2]));
                    if(mo2!=null)mo2.msgLife(Integer.parseInt(str[1]), str);
                    else System.out.println("ERROR ERROR mapobject==null");
                    break;
                //hero item
                case 4:
                    ginterface.invent.setAllItems(str);
                    if (ginterface.invent.binvent) ginterface.invent.openInvent();
                    if (ginterface.invent.btorg) ginterface.invent.openTorg();
                    if (load) game.tout.sendMsg("1/9/2");
                    break;
                //chat
                case 5:
                    if(str.length<3)return;
                    ginterface.chat.addMessage(str[1], str[2]);
                    for(Player pl:players.values()){
                        if(pl.name.getText().toString().equals(str[1])&&pl.karandash.isVisible()){
                        pl.karandash.setVisible(false);pl.timekarandash=0;break;
                        }
                    }
                    break;
                //server time
                case 6:
                    if (str.length > 2) {
                        if(str.length<5)return;
                        WorldState.seconds = Float.parseFloat(str[1]);
                        WorldState.minutes = Integer.parseInt(str[2]);
                        WorldState.hours = Integer.parseInt(str[3]);
                        if (load) {load = false;
                        worldstate.setTimeNight();
                        if(Boolean.parseBoolean(str[4]))startRain();}
                    } else {
                        WorldState.seconds = 0;
                        WorldState.minutes = 0;
                        WorldState.hours = Integer.parseInt(str[1]);
                    }
                    break;
                //выброс
                case 7:
                    if (str.length > 1){
                        if(str.length<5){vibros.startVibros();return;}
                        vibros.startVibros2(Integer.parseInt(str[1]), Integer.parseInt(str[2]), Float.parseFloat(str[3]), Integer.parseInt(str[4]));}
                    else vibros.startVibros();
                    break;
                //вход1 в и выход2 из дома
                case 8:
                    Player unittt = (Player) mapobjects.get(Long.parseLong(str[2]));
                    if(unittt!=null){
                    unittt.napravlenie = 1;
                    if (Integer.parseInt(str[1]) == 1) {
                        tr.removeValue(unittt, false);
                        images.removeValue(unittt, false);
                        unittt.vdome = true;
                        if (unittt.ataka) unittt.ataka = false;
                        if (unittt.gun != null && unittt.gun.perezarad) unittt.gun.perezarad = false;
                        unittt.state = Util.VDOME;
                        unittt.canbeattacked = false;
                        if(unittt==hero)ginterface.vih.setVisible(true);
                    }
                    if (Integer.parseInt(str[1]) == 2) {
                        addNewTr(unittt);
                        unittt.vdome = false;
                        unittt.state = Util.WALKING;
                        unittt.canbeattacked = true;
                        if(unittt==hero)ginterface.vih.setVisible(false);
                    }}
                    break;
                //список игроков
                case 10:

                    break;
                //swatchMap
                case 15:
                    if(str.length<4)return;
                    switchMap(Integer.parseInt(str[1]),Integer.parseInt(str[2]),Integer.parseInt(str[3]));
                    hero.sendgoloc=false;
                    break;
                //serverError
                case 16:
                    ginterface.textprivetstvie.setText("Что то пошло не так.\nСервер остановлен. Если в данный момент идет тестирование,\n" +
                            "значит сервер будет перезапущен в течении 30 сек.\n Попробуйте переподключиться.\n" +
                            "Все данные сохранены(будем на это надеяться).");
                    ginterface.privetstvie.setVisible(true);
                    break;
                //дождь
                case 17:
                    int startend=Integer.parseInt(str[1]);
                    if(startend==1){startRain();
                        Sounds.playRain();
                    }
                    else endrain=true;
                    break;
                //otvet qvest
                case 18:
                    ginterface.dialog.serverOtvet(Integer.parseInt(str[1]),Integer.parseInt(str[2]));
                    break;
                //addItem(and many)
                case 19:
                    if(!str[1].equals("m")){
                    String sit[]=str[1].split("-");
                    Item it=ginterface.invent.createItem(Integer.parseInt(sit[0]),Integer.parseInt(sit[1]),0);
                    if(str.length>2)it.dopintcolvo=Integer.parseInt(str[2]);
                    hero.addItem(it);}
                    else{hero.many=Integer.parseInt(str[2]);}
                    break;
                //удаление предметов при смерти
                case 20:
                    hero.throwItem(str);
                    break;
                    //modofikator state
                case 21:
                    int mtip=Integer.parseInt(str[1]);
                    MapObject mmo = mapobjects.get(Long.parseLong(str[2]));
                    if(mmo!=null){
                switch(mtip){
                    case 1:
                        mmo.signal(4,new AnomalyJump(mmo,Integer.parseInt(str[3]),80,Float.parseFloat(str[4])));
                        break;
                    case 2:
                        boolean t=Boolean.parseBoolean(str[4]);
                        float cx=0;float cy=0;
                        if(!t){
                        cx=Float.parseFloat(str[5]);
                        cy=Float.parseFloat(str[6]);
                        }
                        mmo.signal(4,new Teleport(mmo,Float.parseFloat(str[3]),t,cx,cy));
                        break;
                }}
                break;
                    //globalchat
                case 22:
                    ginterface.pda.gchat.addMessage(str[1], str[2]);
                    break;
                    //npc вход выход из дома
                case 23:
                    Npc nnpc = (Npc) mapobjects.get(Long.parseLong(str[2]));
                    if(nnpc!=null){
                        nnpc.napravlenie = 1;
                        if (Integer.parseInt(str[1]) == 1) {nnpc.visibl=false;
                            nnpc.state=Util.VDOME;}
                        else{nnpc.visibl=true;}
                    }
                    break;
                    //klans
                case 24:
                   ginterface.pda.klans.serverMsg(str);
                    break;
                    //obmen
                case 25:
                    ginterface.obmenSystem.serverMsg(str);
                    break;
                    //opovesh vibros
                case 26:
                    if(str.length>1){ginterface.privibros.setDrawable(ginterface.privskill.getDrawable());}else{
                        ginterface.privibros.setDrawable(ginterface.priv.getDrawable());Sounds.playRecVibros();}
                    ginterface.privibros.setVisible(true);
                    break;
                    //kolvo exp
                case 27:
                    ginterface.invent.exp.setText(Integer.parseInt(str[1])+" опыта");
                    break;
                //skill message
                case 28:
                    ginterface.invent.skillmessage(str);
                    break;
                //detector radius anomaly
                case 29:
                    int zxc=Integer.parseInt(str[1]);
                    if(zxc==0){Sounds.playEsayAnomaly();ginterface.imganom.setVisible(true);}
                    else {Sounds.stopEsayAnomaly();ginterface.imganom.setVisible(false);}
                    break;
                    //lichka msg
                case 30:
                    ginterface.pda.lichka.serverMsg(str);
                    if(Integer.parseInt(str[1])==2)ginterface.message.setVisible(true);
                    break;
                    //remov invent items
                case 31:
                String sitid[]=str[1].split(":");
                for (String ss:sitid){
                 hero.remItem(Integer.parseInt(ss));}
                    break;
            }
        }
    }
         /*     //последни й выстрел
                    if (dunit != null) {
                        dunit.life.stopAll();
                        if (hero.state == hero.ATAKA && dunit == hero.atakunit) {
                            if (hero.timevistrel - hero.atakatime < 5) {
                                hero.atakatime = hero.timevistrel + 1;
                                hero.Ataka(Gdx.graphics.getDeltaTime(), this);
                            }
                        }
                        for (Player pla : players.values()) {
                            if (pla.state == pla.ATAKA && dunit == pla.atakunit) {
                                if (pla.timevistrel - pla.atakatime < 5) {
                                    pla.atakatime = pla.timevistrel + 1;
                                    pla.Ataka(Gdx.graphics.getDeltaTime(), this);
                                    break;
                                }
                            }
                        }}*/



    public void addPuli(float x, float y, int celx, int cely,int tip,int sspeed) {
        Puli pul = new Puli(tip,sspeed);
        pul.x = x;
        pul.y = y;
        puli.add(pul);
        pul.atansetup(celx, cely);
    }
    private void startRain(){
rain=true;strongrain=10;timeplusrain=0;endrain=false;
    }
    private void rain(){
        for(int i=0;i<strongrain;i++){
            spriteBatch.draw(imgrain[i], rand.getIntRnd((int)camera.position.x - 240,480), rand.getIntRnd((int)camera.position.y - 160,320));
        }
        if(timeplusrain==30){if(!endrain){if(strongrain<65)strongrain++;}else{
            if(strongrain>10){strongrain--;}else{rain=false;Sounds.stopRain();}
        }
        timeplusrain=0;}
        timeplusrain++;
    }
    void lifeRend(){
       //     shape.begin(ShapeType.Line);
         //   shape.setColor(new Color(1, 0, 0, 1));
        //    shape.circle(hero.centx, hero.centy, 300);
        //    shape.circle(hero.centx, hero.centy, 350);

		/*if(rad!=null){
		shape.setColor(new Color(1, 0, 0, 1));
		for(int i=0;i<rad.rc.length;i++){
		shape.circle(rad.rc[i].x,rad.rc[i].y,rad.rc[i].r);}}*/

       /* for(Rectang r:rec){
            float radius= r.width> r.height? r.width/2+r.height/3: r.height/2+r.width/3;
            shape.circle(r.x+r.width/2,r.y+r.height/2,radius);}*/

     //       shape.end();

            for(MapObject mo:mapobjects.values()){
                if(mo.unit)mo.dopDraw(spriteBatch,shape);
            }
            shape.set(ShapeType.Line);
            shape.setColor(Color.GREEN);
        for(MapObject mo:mapobjects.values()){
            if(mo.tip==Util.CONTROLMAP)mo.dopDraw(spriteBatch,shape);
        }
    }
    //когда персонаж наступает на предмет
   private void poiskPredmetov(){
        for(MapObject mo:mapobjects.values()) {
            if(mo.mput) {
                //если игрок стоит на выброшенной коробке
                if(mo.tip==Util.KOROB&&mo==hero.nastupKorobka)return;
                if (hero.idactivobject!=mo.id && Overlap.overlapRectang(hero.rectang,mo.rectang)) {
                    //не подбираем не свой аккум(переделать)
                    if(mo.tip==Util.AKKOMULATOR){
                        MapObjectss.ObjectAkkom oa=(MapObjectss.ObjectAkkom)mo;
                    if(!oa.namehero.equals(hero.name.getText().toString()))return;}
                    if (hero.getMesto()) {
                        game.tout.sendMsg("1/11/" +mo.id);
                        hero.idactivobject=mo.id;
                    } else {
                        Ginterface.setText("нет места", 3000);
                    }
                }
            }
            if(mo.tip==Util.NPC){
                if(Overlap.overlapRectang(hero.rectang,mo.rectang)&&!opendialog){
                Npc npc=(Npc)mo;
                ginterface.invent.setTorg(npc.items,npc.id,npc.name.getText().toString());
                ginterface.dialog.openDialog(npc.tipnpc);
                npcrec=mo.rectang;
                hero.runtip=Runtip.STOP;
                opendialog=true;}
            }
            for(ClientMapObject cmo:clientMapObjects){
                // если client нпс
                if(cmo.tip==2){
                    if(Overlap.overlapRectang(hero.rectang,cmo.rectang)&&!opendialog){
                        ClientNpc npc2=(ClientNpc)cmo;
                        ginterface.invent.setTorg(npc2.items,0,npc2.name.getText().toString());
                        ginterface.dialog.openDialog(npc2.tipnpc);
                        npcrec=cmo.rectang;
                        hero.runtip=Runtip.STOP;
                        opendialog=true;
                    }
                }
            }
            if(npcrec!=null){if(!Overlap.overlapRectang(hero.rectang,npcrec))opendialog=false;}
        }
    }
    void dellPuli() {
        Iterator<Puli> it = puli.iterator();
        while (it.hasNext()) {
            Puli pul = it.next();
            if (!pul.run) it.remove();
        }
    }
    void sortZIndexF() {
        for (int i = 1; i < images.size; i++) {
            Imagee img = images.get(i);
            for (int i1 = i - 1; i1 < i + 1; i1++) {
                Imagee img1 = images.get(i1);
                if (img.y + img.zheight > img1.y + img1.zheight) {
                    images.swap(i, i1);
                    break;
                }
            }
        }
    }

    void addNewTr(Imagee img) {
        float zimg = img.y + img.zheight;
        int numimg = 0;
        for (int i = 0; i < tr.size; i++) {
            Imagee img2 = tr.get(i);
            if (img2.y + img2.zheight > zimg) {
                numimg = i;
                break;
            }
        }
        tr.insert(numimg, img);
    }

    void dell() {
        for (MapObject unit : mapobjects.values()) {
            if (unit.remov) {
                unit.centx = -1000;
                unit.centy = -1000;
                break;
            }
        }
    }

    void setServerMsgDvig() {
        String str = null;
        switch (hero.runtip) {
            case STOP:
                str = "1/1/1";
                break;
            case UP:
                str = "1/1/2";
                break;
            case RIGHT:
                str = "1/1/3";
                break;
            case DOWN:
                str = "1/1/4";
                break;
            case LEFT:
                str = "1/1/5";
                break;
            case UR:
                str = "1/1/6";
                break;
            case RD:
                str = "1/1/7";
                break;
            case DL:
                str = "1/1/8";
                break;
            case LU:
                str = "1/1/9";
                break;
        }
        if (str != oldservermsg && hero.state != Util.DIED && !ginterface.chat.booleanchat &&
                !ginterface.invent.binvent && !ginterface.invent.btorg&&!hero.sendgoloc&&!hero.zapretdvig) {
            game.tout.sendMsg(str);
            Util.starttimeset=System.currentTimeMillis();
            oldservermsg = str;
            oldatak = "a";
        }
    }

  public void tochMap(boolean downup) {
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);
            tx = touch.x;
            ty = touch.y;
            if(!ginterface.dialog.isVisible()&&!ginterface.obmenSystem.isVisible()&&!ginterface.pda.isVisible()&&!ginterface.invent.binvent&&!ginterface.invent.btorg){
            if (!rbolt){
                for (MapObject mo : mapobjects.values()) {
                    if (Overlap.pointPoint(tx, ty, mo.centx, mo.centy, 18)) {
                        switch (mo.tip) {
                            case Util.PLAYER:
                                if(hero.itstate==0){game.tout.sendMsg("1/19/1/"+mo.id);Ginterface.setText("предлагаем обмен...",3000);}
                                if(hero.itstate==2){game.tout.sendMsg("1/3/1/" + hero.activitemid+"/"+mo.id);
                                    hero.useItem(hero.activitemid);hero.activitemid=0;
                                    hero.itstate=0;
                                    ginterface.activit.setVisible(false);return;}
                            case Util.KABAN:
                            case Util.KRUSA:
                            case Util.ZOMBI:
                            case Util.BOSKABAN:
                                if(hero.itstate==1){
                                if (mo.tip == 1 && (hero.idmap == 2||hero.idmap==11) && mo != hero) {
                                    Ginterface.setText("На этой локации запрещено pvp", 2000);
                                    hero.activitemid=0;
                                    hero.itstate=0;
                                    ginterface.activit.setVisible(false);
                                    return;
                                }
                                if (hero.gun == null) {
                                    Ginterface.setText("У вас нет оружия.", 2000);
                                    hero.activitemid=0;
                                    hero.itstate=0;
                                    ginterface.activit.setVisible(false);
                                    return;
                                }
                                if (!Overlap.pointPoint(hero.centx, hero.centy, mo.centx, mo.centy, hero.gun.dalnostrel)) {
                                    Ginterface.setText("Цель слишком далеко.", 2000);
                                    hero.activitemid=0;
                                    hero.itstate=0;
                                    ginterface.activit.setVisible(false);
                                    return;
                                }
                                if (hero.gun.dopintcolvo == 0 && hero.getItem(14,true) == null) {
                                    Ginterface.setText("У вас нет патронов.", 2000);
                                    hero.activitemid=0;
                                    hero.itstate=0;
                                    ginterface.activit.setVisible(false);
                                    return;
                                }
                                if (mo.canbeattacked && mo != hero) {
                                    if (!oldatak.equals("1/4/" + mo.id)) {
                                        game.tout.sendMsg("1/4/" + mo.id);
                                        oldatak = "1/4/" + mo.id;
                                        hero.activitemid=0;
                                        hero.itstate=0;
                                        ginterface.activit.setVisible(false);
                                        return;
                                    }
                                }}
                                break;
                            case Util.NPC:
                                if (!Overlap.pointPoint(hero.centx, hero.centy, mo.centx, mo.centy, 30)) {
                                    Ginterface.setText("Цель слишком далеко.", 2000);
                                    return;
                                }
                                Npc npc=(Npc)mo;
                                //переделать
                                ginterface.invent.setTorg(npc.items,npc.id,npc.name.getText().toString());
                                ginterface.dialog.openDialog(npc.tipnpc);
                              // if(npc.tipnpc==1){ginterface.invent.openTorg(npc);}
                               // if(npc.tipnpc==2){ginterface.systemqvest.Qvest(npc.id);}
                                hero.runtip = Runtip.STOP;
                                return;
                        }
                    }
                }
// проверка объектов клиента
                for (ClientMapObject cmo : clientMapObjects) {
                    switch(cmo.tip){
                        case 2:
                        if (Overlap.pointPoint(tx, ty, cmo.centx, cmo.centy, 18)) {
                            if (!Overlap.pointPoint(hero.centx, hero.centy, cmo.centx, cmo.centy, 30)) {
                                Ginterface.setText("Цель слишком далеко.", 2000);
                                return;
                            }
                            ClientNpc npc=(ClientNpc)cmo;
                            ginterface.invent.setTorg(npc.items,0,npc.name.getText().toString());
                            ginterface.dialog.openDialog(npc.tipnpc);
                            // if(npc.tipnpc==1){ginterface.invent.openTorg(npc);}
                            // if(npc.tipnpc==2){ginterface.systemqvest.Qvest(npc.id);}
                            hero.runtip = Runtip.STOP;
                    }
                    break;
                    }}

        }else{if(hero.state==Util.WALKING){
                int naprav=detectNapravlenie(tx,ty);
               if(!hero.bolt.run&&naprav!=0)game.tout.sendMsg("1/16/"+naprav);}
            }
        }
    }

    public int detectNapravlenie(double cx, double cy) {
        double A = Math.atan2((double) hero.centx - cx, (double) hero.centy - cy) / Math.PI * 180;
        if (A < 0) A += 360;
        if (A > 67.5 && A < 112.5)if(hero.urdlnapravlenie==5)return  5;else return 0;
        if (A < 22.5 || A > 337.5)if(hero.urdlnapravlenie==4)return  4;else return 0;
        if (A > 247.5 && A < 292.5)if(hero.urdlnapravlenie==3)return  3;else return 0;
        if (A > 157.5 && A < 202.5)if(hero.urdlnapravlenie==2)return 2;else return 0;
        if (A >= 202.5 && A <= 247.5)if(hero.urdlnapravlenie==2||hero.urdlnapravlenie==3)return  6;else return 0;
        if (A >= 112.5 && A <= 157.5)if(hero.urdlnapravlenie==2||hero.urdlnapravlenie==5)return  9;else return 0;
        if (A >= 22.5 && A <= 67.5)if(hero.urdlnapravlenie==5||hero.urdlnapravlenie==4)return 8;else return 0;
        if (A >= 292.5 && A <= 337.5)if(hero.urdlnapravlenie==4||hero.urdlnapravlenie==3)return  7;else return 0;
        return 0;
    }
    void controlKey() {
        if (Gdx.input.isKeyPressed(Keys.A)) {
            if (Gdx.input.isKeyPressed(Keys.W)) {
                hero.runtip = Runtip.LU;
                return;
            }
            if (Gdx.input.isKeyPressed(Keys.S)) {
                hero.runtip = Runtip.DL;
                return;
            }
            hero.runtip = Runtip.LEFT;
            return;
        }
        if (Gdx.input.isKeyPressed(Keys.D)) {
            if (Gdx.input.isKeyPressed(Keys.S)) {
                hero.runtip = Runtip.RD;
                return;
            }
            if (Gdx.input.isKeyPressed(Keys.W)) {
                hero.runtip = Runtip.UR;
                return;
            }
            hero.runtip = Runtip.RIGHT;
            return;
        }
        if (Gdx.input.isKeyPressed(Keys.W)) {
            hero.runtip = Runtip.UP;
            return;
        }
        if (Gdx.input.isKeyPressed(Keys.S)) {
            hero.runtip = Runtip.DOWN;
            return;
        }
        if (Gdx.input.isKeyJustPressed(Keys.R)&&hero.state!=Util.PEREZARAD) {
            if(hero.gun!=null&&hero.gun.dopintcolvo!=hero.gun.oboima){
            game.tout.sendMsg("1/8/");
            return;}
        }
        if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
            if (ginterface.invent.binvent || ginterface.invent.btorg) {
                ginterface.invent.closeInvent();
            } else {
                if (ginterface.out.isVisible()) ginterface.out.setVisible(false);
                else ginterface.out.setVisible(true);
            }
        }
        hero.runtip = Runtip.STOP;
    }

   /* void controlToch() {
       if(!ginterface.dialog.isVisible()){
        if (Gdx.input.isTouched()) {
            float tx, ty;
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            ginterface.stage.getCamera().unproject(touchPos);
            tx = touchPos.x;
            ty = touchPos.y;
            // крестовина управления
            int sizx = 250 / 3;
            int sizy = 250 / 3;
            if (tx > sizx && tx < sizx + sizx && ty < sizy * 3 && ty > sizy * 2) {
                hero.runtip = Runtip.UP;
                return;
            }
            if (tx > 0 && tx < sizx && ty < sizy + sizy && ty > sizy) {
                hero.runtip = Runtip.LEFT;
                return;
            }
            if (tx > sizx && tx < sizx * 2 && ty < sizy && ty > 0) {
                hero.runtip = Runtip.DOWN;
                return;
            }
            if (tx > sizx * 2 && tx < sizx * 3 && ty < sizy * 2 && ty > sizy) {
                hero.runtip = Runtip.RIGHT;
                return;
            }
            if (tx > sizx * 2 && tx < sizx * 3 && ty > sizy * 2 && ty < sizy * 3) {
                hero.runtip = Runtip.UR;
                return;
            }
            if (tx > sizx * 2 && tx < sizx * 3 && ty > 0 && ty < sizy) {
                hero.runtip = Runtip.RD;
                return;
            }
            if (tx > 0 && tx < sizx && ty > 0 && ty < sizy) {
                hero.runtip = Runtip.DL;
                return;
            }
            if (tx > 0 && tx < sizx && ty > sizy * 2 && ty < sizy * 3) {
                hero.runtip = Runtip.LU;
                return;
            }
        }
        hero.runtip = Runtip.STOP;
        if (Gdx.input.isKeyJustPressed(Keys.BACK)) {
            if (ginterface.invent.binvent || ginterface.invent.btorg || ginterface.systemqvest.dialog.isVisible()) {
                ginterface.closeInvent();
            } else {
                if (ginterface.out.isVisible()) ginterface.out.setVisible(false);
                else ginterface.out.setVisible(true);
            }
        }}
    }*/
    void switchMap(int lvl,int hx,int hy){
        tr.clear();rec.clear();
        images.clear();
        mapobjects.clear();
        clientMapObjects.clear();
        players.clear();
        hero.life.stopAllMobMinusLife();
        level=new Map("tmxmap/map"+lvl+".tmx",tr,rec, clientMapObjects,game);
        hero.idmap=lvl;
       /* //sort z index
        for (int i = 0; i < tr.size; i++) {
            Imagee img = tr.get(i);
            for (int i1 = 0; i1 < tr.size; i1++) {
                Imagee img1 = tr.get(i1);
                if (img.y + img.zheight > img1.y + img1.zheight) {
                    tr.swap(i, i1);
                }
            }
        }*/
        grid=null;
        grid = new SpatialHashGrid(1750,1750,250);
        for (Rectang r : rec) {
            grid.insertStaticObject(r);
        }
        hero.setPosition(hx,hy);
        hero.oldx=hero.x;
        hero.oldy=hero.y;
        hero.napravlenie=1;
        camera.position.x = hero.x + 22;
        camera.position.y = hero.y + 19.5f;
        if (camera.position.x < 216 + 23.5f) camera.position.x = 216 + 23.5f;
        if (camera.position.y < 140 + 19.5f) camera.position.y = 140 + 19.5f;
        if (camera.position.x > 1336 + 23.5f) camera.position.x = 1336 + 23.5f;
        if (camera.position.y > 1420 + 19.5f) camera.position.y = 1420 + 19.5f;
        addNewTr(hero);
        addNewTr(hero.bolt);
        players.put(hero.id, hero);
        mapobjects.put(hero.id,hero);

        for(Imagee img : tr){
            if(Overlap.pointPoint(img.x + img.width / 2, img.y + img.height / 2, camera.position.x, camera.position.y, 400)){
                images.add(img);
                tr.removeValue(img, false);
            }
        }
        // удаляем ту графику которую уже не надо отрисовывать (игрок отошел от нее)
        for(Imagee img : images){
            if( ! Overlap.pointPoint(img.x + img.width / 2, img.y + img.height / 2, camera.position.x, camera.position.y, 400)){
                tr.add(img);
                images.removeValue(img, false);
            }
        }

        if(renderer==null)renderer = new OrthogonalTiledMapRenderer(level.getMap(),1);
        else
        renderer.setMap(level.getMap());
    }
    @Override
    public void show() {
        // TODO Auto-generated method stub

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
    }

    @Override
    public void dispose() {
    }
}
