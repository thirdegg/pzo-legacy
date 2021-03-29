package Unit;

import GameWorld.Game;
import GameWorld.MapObject;
import Modules.Life;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import util.Rectang;
import util.Util;

/**
 * Created by 777 on 07.06.2017.
 */
public class BosKaban extends MapObject {
    private final int COLS = 12, ROWS = 22;
    public MapObject atakunit;
    private TextureRegion hero;
    private TextureRegion die, stand;
    public Animation walkanim[],walkanim2[],atakanim[],deadanim[];
    public TextureRegion imgstandnaprav[];
    private float stateTime;
    private Color colorlife;
    public float speed,ld,uron;
    public int napravlenie = 1,oldstate,nsnapravlenie=4;
    public long ldt;
    Game game;
    boolean animdead;
    public BosKaban(int xx, int yy, long id, Label.LabelStyle lsuron, Game game) {
        super("cane1.png", xx, yy, 2,lsuron,true);
        this.id = id;
        this.tip = Util.BOSKABAN;
        colorlife = new Color();
        this.game=game;
        Texture heroTexture = Util.kaban;
        TextureRegion[][] tmp = TextureRegion.split(heroTexture, heroTexture.getWidth() / COLS, heroTexture.getHeight() / ROWS);
        TextureRegion[] tmp2 = new TextureRegion[COLS * ROWS];
        int index = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                tmp2[index++] = tmp[i][j];
            }
        }
        //хотьба
        TextureRegion[]walkUp = new TextureRegion[7];
        walkUp[0] = tmp2[32];
        walkUp[1] = tmp2[33];
        walkUp[2] = tmp2[34];
        walkUp[3] = tmp2[35];
        walkUp[4] = tmp2[36];
        walkUp[5] = tmp2[37];
        walkUp[6] = tmp2[38];
        Animation wup = new Animation(0.15f, walkUp);
        TextureRegion[]walkRight = new TextureRegion[7];
        walkRight[0] = tmp2[96];
        walkRight[1] = tmp2[97];
        walkRight[2] = tmp2[98];
        walkRight[3] = tmp2[99];
        walkRight[4] = tmp2[100];
        walkRight[5] = tmp2[101];
        walkRight[6] = tmp2[102];
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
        walkLeft[0] = tmp2[64];
        walkLeft[1] = tmp2[65];
        walkLeft[2] = tmp2[66];
        walkLeft[3] = tmp2[67];
        walkLeft[4] = tmp2[68];
        walkLeft[5] = tmp2[69];
        walkLeft[6] = tmp2[70];
        Animation wleft = new Animation(0.15f, walkLeft);
        TextureRegion[]walkUpRight = new TextureRegion[7];
        walkUpRight[0] = tmp2[160];
        walkUpRight[1] = tmp2[161];
        walkUpRight[2] = tmp2[162];
        walkUpRight[3] = tmp2[163];
        walkUpRight[4] = tmp2[164];
        walkUpRight[5] = tmp2[165];
        walkUpRight[6] = tmp2[166];
        Animation wupright = new Animation(0.15f, walkUpRight);
        TextureRegion[]walkRightDown = new TextureRegion[7];
        walkRightDown[0] = tmp2[128];
        walkRightDown[1] = tmp2[129];
        walkRightDown[2] = tmp2[130];
        walkRightDown[3] = tmp2[131];
        walkRightDown[4] = tmp2[132];
        walkRightDown[5] = tmp2[133];
        walkRightDown[6] = tmp2[134];
        Animation wrightdown = new Animation(0.15f, walkRightDown);
        TextureRegion[]walkDownLeft = new TextureRegion[7];
        walkDownLeft[0] = tmp2[224];
        walkDownLeft[1] = tmp2[225];
        walkDownLeft[2] = tmp2[226];
        walkDownLeft[3] = tmp2[227];
        walkDownLeft[4] = tmp2[228];
        walkDownLeft[5] = tmp2[229];
        walkDownLeft[6] = tmp2[230];
        Animation wdownleft = new Animation(0.15f, walkDownLeft);
        TextureRegion[]walkLeftUp = new TextureRegion[7];
        walkLeftUp[0] = tmp2[192];
        walkLeftUp[1] = tmp2[193];
        walkLeftUp[2] = tmp2[194];
        walkLeftUp[3] = tmp2[195];
        walkLeftUp[4] = tmp2[196];
        walkLeftUp[5] = tmp2[197];
        walkLeftUp[6] = tmp2[198];
        Animation wleftup = new Animation(0.15f, walkLeftUp);
//хотьба 2(галоп)
        TextureRegion[]walkUp2 = new TextureRegion[8];
        walkUp2[0] = tmp2[56];
        walkUp2[1] = tmp2[57];
        walkUp2[2] = tmp2[58];
        walkUp2[3] = tmp2[59];
        walkUp2[4] = tmp2[60];
        walkUp2[5] = tmp2[61];
        walkUp2[6] = tmp2[62];
        walkUp2[7] = tmp2[63];
        Animation wup2 = new Animation(0.15f, walkUp2);
        TextureRegion[]walkRight2 = new TextureRegion[8];
        walkRight2[0] = tmp2[120];
        walkRight2[1] = tmp2[121];
        walkRight2[2] = tmp2[122];
        walkRight2[3] = tmp2[123];
        walkRight2[4] = tmp2[124];
        walkRight2[5] = tmp2[125];
        walkRight2[6] = tmp2[126];
        walkRight2[7] = tmp2[127];
        Animation wright2 = new Animation(0.15f, walkRight2);
        TextureRegion[]walkDown2 = new TextureRegion[8];
        walkDown2[0] = tmp2[24];
        walkDown2[1] = tmp2[25];
        walkDown2[2] = tmp2[26];
        walkDown2[3] = tmp2[27];
        walkDown2[4] = tmp2[28];
        walkDown2[5] = tmp2[29];
        walkDown2[6] = tmp2[30];
        walkDown2[7] = tmp2[31];
        Animation wdown2 = new Animation(0.15f, walkDown2);
        TextureRegion[]walkLeft2 = new TextureRegion[8];
        walkLeft2[0] = tmp2[88];
        walkLeft2[1] = tmp2[89];
        walkLeft2[2] = tmp2[90];
        walkLeft2[3] = tmp2[91];
        walkLeft2[4] = tmp2[92];
        walkLeft2[5] = tmp2[93];
        walkLeft2[6] = tmp2[94];
        walkLeft2[7] = tmp2[95];
        Animation wleft2 = new Animation(0.15f, walkLeft2);
        TextureRegion[]walkUpRight2 = new TextureRegion[8];
        walkUpRight2[0] = tmp2[184];
        walkUpRight2[1] = tmp2[185];
        walkUpRight2[2] = tmp2[186];
        walkUpRight2[3] = tmp2[187];
        walkUpRight2[4] = tmp2[188];
        walkUpRight2[5] = tmp2[189];
        walkUpRight2[6] = tmp2[190];
        walkUpRight2[7] = tmp2[191];
        Animation wupright2 = new Animation(0.15f, walkUpRight2);
        TextureRegion[]walkRightDown2 = new TextureRegion[8];
        walkRightDown2[0] = tmp2[152];
        walkRightDown2[1] = tmp2[153];
        walkRightDown2[2] = tmp2[154];
        walkRightDown2[3] = tmp2[155];
        walkRightDown2[4] = tmp2[156];
        walkRightDown2[5] = tmp2[157];
        walkRightDown2[6] = tmp2[158];
        walkRightDown2[7] = tmp2[159];
        Animation wrightdown2 = new Animation(0.15f, walkRightDown2);
        TextureRegion[]walkDownLeft2 = new TextureRegion[8];
        walkDownLeft2[0] = tmp2[248];
        walkDownLeft2[1] = tmp2[249];
        walkDownLeft2[2] = tmp2[250];
        walkDownLeft2[3] = tmp2[251];
        walkDownLeft2[4] = tmp2[252];
        walkDownLeft2[5] = tmp2[253];
        walkDownLeft2[6] = tmp2[254];
        walkDownLeft2[7] = tmp2[255];
        Animation wdownleft2 = new Animation(0.15f, walkDownLeft2);
        TextureRegion[]walkLeftUp2 = new TextureRegion[8];
        walkLeftUp2[0] = tmp2[216];
        walkLeftUp2[1] = tmp2[217];
        walkLeftUp2[2] = tmp2[218];
        walkLeftUp2[3] = tmp2[219];
        walkLeftUp2[4] = tmp2[220];
        walkLeftUp2[5] = tmp2[221];
        walkLeftUp2[6] = tmp2[222];
        walkLeftUp2[7] = tmp2[223];
        Animation wleftup2 = new Animation(0.15f, walkLeftUp2);
// атака
        TextureRegion[]tapup = new TextureRegion[9];
        tapup[0] = tmp2[39];
        tapup[1] = tmp2[40];
        tapup[2] = tmp2[41];
        tapup[3] = tmp2[42];
        tapup[4] = tmp2[43];
        tapup[5] = tmp2[44];
        tapup[6] = tmp2[45];
        tapup[7] = tmp2[46];
        tapup[8] = tmp2[47];
        Animation apup = new Animation(0.14f, tapup);
        TextureRegion[]tapright = new TextureRegion[9];
        tapright[0] = tmp2[103];
        tapright[1] = tmp2[104];
        tapright[2] = tmp2[105];
        tapright[3] = tmp2[106];
        tapright[4] = tmp2[107];
        tapright[5] = tmp2[108];
        tapright[6] = tmp2[109];
        tapright[7] = tmp2[110];
        tapright[8] = tmp2[111];
        Animation apright = new Animation(0.14f, tapright);
        TextureRegion[]tapdown = new TextureRegion[9];
        tapdown[0] = tmp2[7];
        tapdown[1] = tmp2[8];
        tapdown[2] = tmp2[9];
        tapdown[3] = tmp2[10];
        tapdown[4] = tmp2[11];
        tapdown[5] = tmp2[12];
        tapdown[6] = tmp2[13];
        tapdown[7] = tmp2[14];
        tapdown[8] = tmp2[15];
        Animation apdown = new Animation(0.14f, tapdown);
        TextureRegion[]tapleft = new TextureRegion[9];
        tapleft[0] = tmp2[71];
        tapleft[1] = tmp2[72];
        tapleft[2] = tmp2[73];
        tapleft[3] = tmp2[74];
        tapleft[4] = tmp2[75];
        tapleft[5] = tmp2[76];
        tapleft[6] = tmp2[77];
        tapleft[7] = tmp2[78];
        tapleft[8] = tmp2[79];
        Animation apleft = new Animation(0.14f, tapleft);
        TextureRegion[]tapupright = new TextureRegion[9];
        tapupright[0] = tmp2[167];
        tapupright[1] = tmp2[168];
        tapupright[2] = tmp2[169];
        tapupright[3] = tmp2[170];
        tapupright[4] = tmp2[171];
        tapupright[5] = tmp2[172];
        tapupright[6] = tmp2[173];
        tapupright[7] = tmp2[174];
        tapupright[8] = tmp2[175];
        Animation apupright = new Animation(0.14f, tapupright);
        TextureRegion[]taprightdown = new TextureRegion[9];
        taprightdown[0] = tmp2[135];
        taprightdown[1] = tmp2[136];
        taprightdown[2] = tmp2[137];
        taprightdown[3] = tmp2[138];
        taprightdown[4] = tmp2[139];
        taprightdown[5] = tmp2[140];
        taprightdown[6] = tmp2[141];
        taprightdown[7] = tmp2[142];
        taprightdown[8] = tmp2[143];
        Animation aprightdown = new Animation(0.14f, taprightdown);
        TextureRegion[]tapdownleft = new TextureRegion[9];
        tapdownleft[0] = tmp2[231];
        tapdownleft[1] = tmp2[232];
        tapdownleft[2] = tmp2[233];
        tapdownleft[3] = tmp2[234];
        tapdownleft[4] = tmp2[235];
        tapdownleft[5] = tmp2[236];
        tapdownleft[6] = tmp2[237];
        tapdownleft[7] = tmp2[238];
        tapdownleft[8] = tmp2[239];
        Animation apdownleft = new Animation(0.14f, tapdownleft);
        TextureRegion[]tapleftup = new TextureRegion[9];
        tapleftup[0] = tmp2[199];
        tapleftup[1] = tmp2[200];
        tapleftup[2] = tmp2[201];
        tapleftup[3] = tmp2[202];
        tapleftup[4] = tmp2[203];
        tapleftup[5] = tmp2[204];
        tapleftup[6] = tmp2[205];
        tapleftup[7] = tmp2[206];
        tapleftup[8] = tmp2[207];
        Animation apleftup = new Animation(0.14f, tapleftup);
        //смерть
        TextureRegion[]tdeadup = new TextureRegion[8];
        tdeadup[0] = tmp2[48];
        tdeadup[1] = tmp2[49];
        tdeadup[2] = tmp2[50];
        tdeadup[3] = tmp2[51];
        tdeadup[4] = tmp2[52];
        tdeadup[5] = tmp2[53];
        tdeadup[6] = tmp2[54];
        tdeadup[7] = tmp2[55];
        Animation dup = new Animation(0.2f, tdeadup);
        TextureRegion[]tdeadright = new TextureRegion[8];
        tdeadright[0] = tmp2[112];
        tdeadright[1] = tmp2[113];
        tdeadright[2] = tmp2[114];
        tdeadright[3] = tmp2[115];
        tdeadright[4] = tmp2[116];
        tdeadright[5] = tmp2[117];
        tdeadright[6] = tmp2[118];
        tdeadright[7] = tmp2[119];
        Animation dright = new Animation(0.2f, tdeadright);
        TextureRegion[]tdeaddown = new TextureRegion[8];
        tdeaddown[0] = tmp2[16];
        tdeaddown[1] = tmp2[17];
        tdeaddown[2] = tmp2[18];
        tdeaddown[3] = tmp2[19];
        tdeaddown[4] = tmp2[20];
        tdeaddown[5] = tmp2[21];
        tdeaddown[6] = tmp2[22];
        tdeaddown[7] = tmp2[23];
        Animation ddown = new Animation(0.2f, tdeaddown);
        TextureRegion[]tdeadleft = new TextureRegion[8];
        tdeadleft[0] = tmp2[80];
        tdeadleft[1] = tmp2[81];
        tdeadleft[2] = tmp2[82];
        tdeadleft[3] = tmp2[83];
        tdeadleft[4] = tmp2[84];
        tdeadleft[5] = tmp2[85];
        tdeadleft[6] = tmp2[86];
        tdeadleft[7] = tmp2[87];
        Animation dleft = new Animation(0.2f, tdeadleft);
        TextureRegion[]tdeadupright = new TextureRegion[8];
        tdeadupright[0] = tmp2[176];
        tdeadupright[1] = tmp2[177];
        tdeadupright[2] = tmp2[178];
        tdeadupright[3] = tmp2[179];
        tdeadupright[4] = tmp2[180];
        tdeadupright[5] = tmp2[181];
        tdeadupright[6] = tmp2[182];
        tdeadupright[7] = tmp2[183];
        Animation dupright = new Animation(0.2f, tdeadupright);
        TextureRegion[]tdeadrightdown = new TextureRegion[8];
        tdeadrightdown[0] = tmp2[144];
        tdeadrightdown[1] = tmp2[145];
        tdeadrightdown[2] = tmp2[146];
        tdeadrightdown[3] = tmp2[147];
        tdeadrightdown[4] = tmp2[148];
        tdeadrightdown[5] = tmp2[149];
        tdeadrightdown[6] = tmp2[150];
        tdeadrightdown[7] = tmp2[151];
        Animation drightdown = new Animation(0.2f, tdeadrightdown);
        TextureRegion[]tdeaddownleft = new TextureRegion[8];
        tdeaddownleft[0] = tmp2[240];
        tdeaddownleft[1] = tmp2[241];
        tdeaddownleft[2] = tmp2[242];
        tdeaddownleft[3] = tmp2[243];
        tdeaddownleft[4] = tmp2[244];
        tdeaddownleft[5] = tmp2[245];
        tdeaddownleft[6] = tmp2[246];
        tdeaddownleft[7] = tmp2[247];
        Animation ddownleft = new Animation(0.2f, tdeaddownleft);
        TextureRegion[]tdeadleftup = new TextureRegion[8];
        tdeadleftup[0] = tmp2[208];
        tdeadleftup[1] = tmp2[209];
        tdeadleftup[2] = tmp2[210];
        tdeadleftup[3] = tmp2[211];
        tdeadleftup[4] = tmp2[212];
        tdeadleftup[5] = tmp2[213];
        tdeadleftup[6] = tmp2[214];
        tdeadleftup[7] = tmp2[215];
        Animation dleftup = new Animation(0.2f, tdeadleftup);

        stand = tmp2[0];
        die = tmp2[119];

        walkanim=new Animation []{wup, wright, wdown, wleft, wupright, wrightdown, wdownleft, wleftup};
        walkanim2=new Animation []{wup2, wright2, wdown2, wleft2, wupright2, wrightdown2, wdownleft2, wleftup2};
        atakanim=new Animation []{apup,apright,apdown,apleft,apupright,aprightdown,apdownleft,apleftup};
        deadanim=new Animation []{dup,dright,ddown,dleft,dupright,drightdown,ddownleft,dleftup};
        imgstandnaprav=new TextureRegion[]{tmp2[32],tmp2[96],tmp2[0],tmp2[64],tmp2[160],tmp2[128],tmp2[224],tmp2[192]};
        scalex=0.75f;
        scaley=0.75f;
        hero=stand;
        ld = (float) 600 / 40f;
        speed = 90;
        uron = 30;
        plasrecx = 16;
        plasrecy = 11;
        rectang = new Rectang(x + plasrecx, y + plasrecy, 11, 10);
        centx = rectang.x + rectang.width / 2;
        centy = rectang.y + rectang.height / 2;
        height=walkUp[0].getRegionHeight();
        width=walkUp[0].getRegionWidth();
        canbeattacked =true;
    }

    public void run(float delta) {
        switch (state) {
            case Util.WALKING:
                move(delta);
                break;
            case Util.ATAKA:
                stateTime += Gdx.graphics.getDeltaTime();
                hero = (TextureRegion) atakanim[nsnapravlenie-2].getKeyFrame(stateTime, true);
                break;
            case Util.DIED:
                dead();
                break;
        }
        if (life.life < 0) life.life = 0f;
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
    public void dopDraw(Batch sb, ShapeRenderer sr) {
        if(sb.isDrawing())for (Life.MinusLife ml :life.minuslife) {
            ml.luron.draw(sb, 1);
        }
        if(sr.isDrawing())if(state!=Util.DIED)sr.rect(x+10  ,y + 47,life.life / ld, 1,colorlife,colorlife,colorlife,colorlife);
    }
    void move(float delta) {
        stateTime += Gdx.graphics.getDeltaTime();
        switch (napravlenie) {
            case 1:
                hero = stand;
                break;
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
            hero = (TextureRegion)walkanim[nsnapravlenie-2].getKeyFrame(stateTime, true);
            stand=imgstandnaprav[nsnapravlenie-2];}
        rectang.y = y + plasrecy;
        rectang.x = x + plasrecx;
        centx = rectang.x + rectang.width / 2;
        centy = rectang.y + rectang.height / 2;
    }

    void dead() {
        if(!animdead){
            stateTime += Gdx.graphics.getDeltaTime();
            hero = (TextureRegion) deadanim[nsnapravlenie-2].getKeyFrame(stateTime, false);
            if(deadanim[nsnapravlenie-2].isAnimationFinished(stateTime))animdead=true;
        }
        if (System.currentTimeMillis() > ldt) {
            remov = true;
        }
    }
    public void setState(int newstate, String[] str) {
        /*
        if(newstate==-1){
            int tip=Integer.parseInt(str[0]);
            switch(tip){
                case 2:
                    newstate=DVIG;
                    break;
                case 14:
                    newstate=DEAD;
                    break;
                case 12:
                    newstate=ATAKA;
                    break;
            }
        }*/

        switch(oldstate){
            case Util.ATAKA:
                if(atakunit!=null)atakunit.life.stopPlusMinusLife(id);
                atakunit = null;
                break;
        }

        switch(newstate){
            //1
            case Util.WALKING:
                if(str.length<7)return;
                canbeattacked =true;
                x = Integer.parseInt(str[3]);
                y = Integer.parseInt(str[4]);
                napravlenie = Integer.parseInt(str[5]);
                life.life = Integer.parseInt(str[6]);
                rectang.x = x + plasrecx;
                rectang.y = y + plasrecy;
                centx = rectang.x + rectang.width / 2;
                centy = rectang.y + rectang.height / 2;
                break;
            //2
            case Util.ATAKA:
                if(str.length<6)return;
                atakunit=game.mapobjects.get(Long.parseLong(str[3]));
                x = Integer.parseInt(str[4]);
                y = Integer.parseInt(str[5]);
                break;
            //3
            case Util.DIED:
                stateTime=0;
                canbeattacked =false;
                ldt = System.currentTimeMillis() + 20000;
                life.life = 0;
                life.stopAll();
                if (str.length > 3){ldt = System.currentTimeMillis() + Integer.parseInt(str[3]);
                    animdead=true;hero=die;}
                break;
        }
        state=newstate;oldstate=state;
    }
}