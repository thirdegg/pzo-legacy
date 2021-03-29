package Unit;

import GameInterface.Ginterface;
import GameWorld.Game;
import GameWorld.MapObject;
import GameWorld.SpatialHashGrid;
import GameWorld.Vibros;
import InventItem.*;
import MapObjectss.ClientMapObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import util.Overlap;
import util.Rectang;
import util.Sounds;
import util.Util;

import java.util.List;

/**
 * Created by 777 on 01.05.2017.
 */
public class ClientNpc extends ClientMapObject {
    final int COLUMNS = 24, ROWS = 19;
    private TextureRegion hero;
    public Animation walkanim[],sitinganim[];
    public TextureRegion imgstandnaprav[];
    private float stateTime;
    public float speed;
    public int napravlenie = 1,nsnapravlenie=4;

    boolean ansit;
    float sitanimtime;
    public Label name;
    public int tipnpc;
    public Array<Item> items=new Array<Item>();
    int plasrecx,plasrecy;
    int state;
    public ClientNpc(TextureRegion trr,float xx, float yy, int tip,Label.LabelStyle lsname, int tipnpc) {
        super(trr, xx, yy, 4,tip);
        this.tipnpc=tipnpc;
        tip=Util.NPC;
        TextureRegion[][] tmp = split(getRegionWidth() / COLUMNS, getRegionHeight() / ROWS);
        TextureRegion[] tmp2 = new TextureRegion[COLUMNS * ROWS];
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

        walkanim=new Animation []{wup, wright, wdown, wleft, wupright, wrightdown, wdownleft, wleftup};
        sitinganim=new Animation []{sup,sright,sdown,sleft,supright,srightdown,sdownleft,sleftup};
        imgstandnaprav=new TextureRegion[]{tmp2[66],tmp2[180],tmp2[9],tmp2[123],tmp2[294],tmp2[237],tmp2[408],tmp2[351]};
        scalex=0.50f;
        scaley=0.50f;
        hero=imgstandnaprav[2];
        speed = 20;
        plasrecx = 16;
        plasrecy = 11;
        rectang = new Rectang(x + plasrecx, y + plasrecy, 11, 10);
        centx = rectang.x + rectang.width / 2;
        centy = rectang.y + rectang.height / 2;
        height=walkUp[0].getRegionHeight();
        width=walkUp[0].getRegionWidth();
        name = new Label(getName(), lsname);
        name.setPosition(x - 28, y + 33);
        name.setColor(Color.GREEN);

        items.add(new Aptechka(0));
        items.add(new Kurtka1(0));
        items.add(new Kurtka2(0));
        items.add(new Kurtka3(0));
        items.add(new Kurtka4(0));
        items.add(new Pistolet1(0));
        items.add(new Pistolet2(0));
        items.add(new Pistolet3(0));
        items.add(new Pistolet4(0));
        items.add(new Baton(0));
        items.add(new Flaga(0));
        items.add(new Patrony(0));
        items.add(new IneRad(0));
        items.add(new Spichki(0));
        items.add(new Kurtka5(0));
        items.add(new Kurtka6(0));
        items.add(new Avtomat1(0));
        items.add(new Avtomat2(0));
        items.add(new Avtomat3(0));
    }
    @Override
    public void run(float delta) {
        switch (state) {
            //стоит
            case 0:
if(Vibros.vibros&&visibl)visibl=false;
if(!Vibros.vibros&&!visibl)visibl=true;
                break;
            case 1:
                move(delta);
                break;
            case Util.SITTING:
                if(ansit){
                    sitanimtime+=Gdx.graphics.getDeltaTime();
                    hero=(TextureRegion)sitinganim[nsnapravlenie-2].getKeyFrame(sitanimtime,false);
                    if(sitinganim[nsnapravlenie-2].isAnimationFinished(sitanimtime)){ansit=false;sitanimtime=0;}}
                break;
        }
        super.setRegion(hero);
    }

    void move(float delta) {
        stateTime += Gdx.graphics.getDeltaTime();
        switch (napravlenie) {
            case 2:
                y += speed * delta;
                break;
            case 3:
                x += speed * delta;
                break;
            case 4:
                y -= speed * delta;
                break;
            case 5:
                x -= speed * delta;
                break;
            case 6:
                x += speed * delta;
                y += speed * delta;
                break;
            case 7:
                x += speed * delta;
                y -= speed * delta;
                break;
            case 8:
                x -= speed * delta;
                y -= speed * delta;
                break;
            case 9:
                x -= speed * delta;
                y += speed * delta;
                break;
        }
        if(napravlenie>1){nsnapravlenie=napravlenie;
            hero = (TextureRegion)walkanim[nsnapravlenie-2].getKeyFrame(stateTime, true);}
        rectang.y = y + plasrecy;
        rectang.x = x + plasrecx;
        centx = rectang.x + rectang.width / 2;
        centy = rectang.y + rectang.height / 2;
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
    @Override
    public void dopDraw(Batch sb, ShapeRenderer sr) {
        if(sb.isDrawing()&&visibl ) {name.setPosition(x + 22 - name.getWidth() / 2, y + 44);
            name.draw(sb,1);}
    }
    String getName(){
        switch(tipnpc){
            case 3:return "Гвоздь";
            case 4:return "Технарь";
            case 5:return "Охотник";
            case 6:return "Прохор";
            case 7:return "Док";
        }
        return"noname";
    }
}
