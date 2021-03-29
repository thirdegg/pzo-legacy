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
import util.rand;

/**
 * Created by 777 on 12.12.2016.
 */
public class Zombi extends MapObject{
    private final int COLS = 12, ROWS = 18;
    public MapObject atakunit;
    public float atakatime;
    public int timeudar, skorostatak = 15;
    public Animation walkanim[],walkanim2[],atakanim[],deadanim[];
    public TextureRegion imgstandnaprav[];
    private TextureRegion hero;
    private TextureRegion die,stand;
    private float stateTime;
    private Color colorlife;
    public float speed,ld,uron;
    public int napravlenie = 1,oldstate,nsnapravlenie=4;
    public long ldt;
    Game game;
    boolean animdead;
    public Zombi(int xx, int yy, long id, Label.LabelStyle lsuron, Game game) {
        super("cane1.png", xx, yy, 2,lsuron,true);
        this.id = id;
        this.tip = Util.ZOMBI;
        colorlife = new Color();
        this.game=game;
        int r= rand.getIntRnd(0,3);
        Texture heroTexture=null;
        switch(r){
            case 0: heroTexture = Util.zombi;break;
            case 1: heroTexture = Util.zombi2;break;
            case 2: heroTexture = Util.zombi3;break;
        }
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
        walkUp[0] = tmp2[27];
        walkUp[1] = tmp2[28];
        walkUp[2] = tmp2[29];
        walkUp[3] = tmp2[30];
        walkUp[4] = tmp2[31];
        walkUp[5] = tmp2[32];
        walkUp[6] = tmp2[33];
        Animation wup = new Animation(0.15f, walkUp);
        TextureRegion[]walkRight = new TextureRegion[7];
        walkRight[0] = tmp2[81];
        walkRight[1] = tmp2[82];
        walkRight[2] = tmp2[83];
        walkRight[3] = tmp2[84];
        walkRight[4] = tmp2[85];
        walkRight[5] = tmp2[86];
        walkRight[6] = tmp2[87];
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
        walkLeft[0] = tmp2[54];
        walkLeft[1] = tmp2[55];
        walkLeft[2] = tmp2[56];
        walkLeft[3] = tmp2[57];
        walkLeft[4] = tmp2[58];
        walkLeft[5] = tmp2[59];
        walkLeft[6] = tmp2[60];
        Animation wleft = new Animation(0.15f, walkLeft);
        TextureRegion[]walkUpRight = new TextureRegion[7];
        walkUpRight[0] = tmp2[135];
        walkUpRight[1] = tmp2[136];
        walkUpRight[2] = tmp2[137];
        walkUpRight[3] = tmp2[138];
        walkUpRight[4] = tmp2[139];
        walkUpRight[5] = tmp2[140];
        walkUpRight[6] = tmp2[141];
        Animation wupright = new Animation(0.15f, walkUpRight);
        TextureRegion[]walkRightDown = new TextureRegion[7];
        walkRightDown[0] = tmp2[108];
        walkRightDown[1] = tmp2[109];
        walkRightDown[2] = tmp2[110];
        walkRightDown[3] = tmp2[111];
        walkRightDown[4] = tmp2[112];
        walkRightDown[5] = tmp2[113];
        walkRightDown[6] = tmp2[114];
        Animation wrightdown = new Animation(0.15f, walkRightDown);
        TextureRegion[]walkDownLeft = new TextureRegion[7];
        walkDownLeft[0] = tmp2[189];
        walkDownLeft[1] = tmp2[190];
        walkDownLeft[2] = tmp2[191];
        walkDownLeft[3] = tmp2[192];
        walkDownLeft[4] = tmp2[193];
        walkDownLeft[5] = tmp2[194];
        walkDownLeft[6] = tmp2[195];
        Animation wdownleft = new Animation(0.15f, walkDownLeft);
        TextureRegion[]walkLeftUp = new TextureRegion[7];
        walkLeftUp[0] = tmp2[162];
        walkLeftUp[1] = tmp2[163];
        walkLeftUp[2] = tmp2[164];
        walkLeftUp[3] = tmp2[165];
        walkLeftUp[4] = tmp2[166];
        walkLeftUp[5] = tmp2[167];
        walkLeftUp[6] = tmp2[168];
        Animation wleftup = new Animation(0.15f, walkLeftUp);
        //хотьба 2
        TextureRegion[]walkUp2 = new TextureRegion[8];
        walkUp2[0] = tmp2[34];
        walkUp2[1] = tmp2[35];
        walkUp2[2] = tmp2[36];
        walkUp2[3] = tmp2[37];
        walkUp2[4] = tmp2[38];
        walkUp2[5] = tmp2[39];
        walkUp2[6] = tmp2[40];
        walkUp2[7] = tmp2[41];
        Animation wup2 = new Animation(0.15f, walkUp2);
        TextureRegion[]walkRight2 = new TextureRegion[8];
        walkRight2[0] = tmp2[88];
        walkRight2[1] = tmp2[89];
        walkRight2[2] = tmp2[90];
        walkRight2[3] = tmp2[91];
        walkRight2[4] = tmp2[92];
        walkRight2[5] = tmp2[93];
        walkRight2[6] = tmp2[94];
        walkRight2[7] = tmp2[95];
        Animation wright2 = new Animation(0.15f, walkRight2);
        TextureRegion[]walkDown2 = new TextureRegion[8];
        walkDown2[0] = tmp2[7];
        walkDown2[1] = tmp2[8];
        walkDown2[2] = tmp2[9];
        walkDown2[3] = tmp2[10];
        walkDown2[4] = tmp2[11];
        walkDown2[5] = tmp2[12];
        walkDown2[6] = tmp2[13];
        walkDown2[7] = tmp2[14];
        Animation wdown2 = new Animation(0.15f, walkDown2);
        TextureRegion[]walkLeft2 = new TextureRegion[8];
        walkLeft2[0] = tmp2[61];
        walkLeft2[1] = tmp2[62];
        walkLeft2[2] = tmp2[63];
        walkLeft2[3] = tmp2[64];
        walkLeft2[4] = tmp2[65];
        walkLeft2[5] = tmp2[66];
        walkLeft2[6] = tmp2[67];
        walkLeft2[7] = tmp2[68];
        Animation wleft2 = new Animation(0.15f, walkLeft2);
        TextureRegion[]walkUpRight2 = new TextureRegion[8];
        walkUpRight2[0] = tmp2[142];
        walkUpRight2[1] = tmp2[143];
        walkUpRight2[2] = tmp2[144];
        walkUpRight2[3] = tmp2[145];
        walkUpRight2[4] = tmp2[146];
        walkUpRight2[5] = tmp2[147];
        walkUpRight2[6] = tmp2[148];
        walkUpRight2[7] = tmp2[149];
        Animation wupright2 = new Animation(0.15f, walkUpRight2);
        TextureRegion[]walkRightDown2 = new TextureRegion[8];
        walkRightDown2[0] = tmp2[115];
        walkRightDown2[1] = tmp2[116];
        walkRightDown2[2] = tmp2[117];
        walkRightDown2[3] = tmp2[118];
        walkRightDown2[4] = tmp2[119];
        walkRightDown2[5] = tmp2[120];
        walkRightDown2[6] = tmp2[121];
        walkRightDown2[7] = tmp2[122];
        Animation wrightdown2 = new Animation(0.15f, walkRightDown2);
        TextureRegion[]walkDownLeft2 = new TextureRegion[8];
        walkDownLeft2[0] = tmp2[169];
        walkDownLeft2[1] = tmp2[170];
        walkDownLeft2[2] = tmp2[171];
        walkDownLeft2[3] = tmp2[172];
        walkDownLeft2[4] = tmp2[173];
        walkDownLeft2[5] = tmp2[174];
        walkDownLeft2[6] = tmp2[175];
        walkDownLeft2[7] = tmp2[176];
        Animation wdownleft2 = new Animation(0.15f, walkDownLeft2);
        TextureRegion[]walkLeftUp2 = new TextureRegion[8];
        walkLeftUp2[0] = tmp2[169];
        walkLeftUp2[1] = tmp2[170];
        walkLeftUp2[2] = tmp2[171];
        walkLeftUp2[3] = tmp2[172];
        walkLeftUp2[4] = tmp2[173];
        walkLeftUp2[5] = tmp2[174];
        walkLeftUp2[6] = tmp2[175];
        walkLeftUp2[7] = tmp2[176];
        Animation wleftup2 = new Animation(0.15f, walkLeftUp2);
// атака
        TextureRegion[]tapup = new TextureRegion[7];
        tapup[0] = tmp2[42];
        tapup[1] = tmp2[43];
        tapup[2] = tmp2[44];
        tapup[3] = tmp2[45];
        tapup[4] = tmp2[46];
        tapup[5] = tmp2[47];
        tapup[6] = tmp2[48];
        Animation apup = new Animation(0.2f, tapup);
        TextureRegion[]tapright = new TextureRegion[7];
        tapright[0] = tmp2[96];
        tapright[1] = tmp2[97];
        tapright[2] = tmp2[98];
        tapright[3] = tmp2[99];
        tapright[4] = tmp2[100];
        tapright[5] = tmp2[101];
        tapright[6] = tmp2[102];
        Animation apright = new Animation(0.2f, tapright);
        TextureRegion[]tapdown = new TextureRegion[7];
        tapdown[0] = tmp2[15];
        tapdown[1] = tmp2[16];
        tapdown[2] = tmp2[17];
        tapdown[3] = tmp2[18];
        tapdown[4] = tmp2[19];
        tapdown[5] = tmp2[20];
        tapdown[6] = tmp2[21];
        Animation apdown = new Animation(0.2f, tapdown);
        TextureRegion[]tapleft = new TextureRegion[7];
        tapleft[0] = tmp2[69];
        tapleft[1] = tmp2[70];
        tapleft[2] = tmp2[71];
        tapleft[3] = tmp2[72];
        tapleft[4] = tmp2[73];
        tapleft[5] = tmp2[74];
        tapleft[6] = tmp2[75];
        Animation apleft = new Animation(0.2f, tapleft);
        TextureRegion[]tapupright = new TextureRegion[7];
        tapupright[0] = tmp2[150];
        tapupright[1] = tmp2[151];
        tapupright[2] = tmp2[152];
        tapupright[3] = tmp2[153];
        tapupright[4] = tmp2[154];
        tapupright[5] = tmp2[155];
        tapupright[6] = tmp2[156];
        Animation apupright = new Animation(0.2f, tapupright);
        TextureRegion[]taprightdown = new TextureRegion[7];
        taprightdown[0] = tmp2[123];
        taprightdown[1] = tmp2[124];
        taprightdown[2] = tmp2[125];
        taprightdown[3] = tmp2[126];
        taprightdown[4] = tmp2[127];
        taprightdown[5] = tmp2[128];
        taprightdown[6] = tmp2[129];
        Animation aprightdown = new Animation(0.2f, taprightdown);
        TextureRegion[]tapdownleft = new TextureRegion[7];
        tapdownleft[0] = tmp2[204];
        tapdownleft[1] = tmp2[205];
        tapdownleft[2] = tmp2[206];
        tapdownleft[3] = tmp2[207];
        tapdownleft[4] = tmp2[208];
        tapdownleft[5] = tmp2[209];
        tapdownleft[6] = tmp2[210];
        Animation apdownleft = new Animation(0.2f, tapdownleft);
        TextureRegion[]tapleftup = new TextureRegion[7];
        tapleftup[0] = tmp2[177];
        tapleftup[1] = tmp2[178];
        tapleftup[2] = tmp2[179];
        tapleftup[3] = tmp2[180];
        tapleftup[4] = tmp2[181];
        tapleftup[5] = tmp2[182];
        tapleftup[6] = tmp2[183];
        Animation apleftup = new Animation(0.2f, tapleftup);
        //смерть
        TextureRegion[]tdeadup = new TextureRegion[5];
        tdeadup[0] = tmp2[49];
        tdeadup[1] = tmp2[50];
        tdeadup[2] = tmp2[51];
        tdeadup[3] = tmp2[52];
        tdeadup[4] = tmp2[53];
        Animation dup = new Animation(0.2f, tdeadup);
        TextureRegion[]tdeadright = new TextureRegion[5];
        tdeadright[0] = tmp2[103];
        tdeadright[1] = tmp2[104];
        tdeadright[2] = tmp2[105];
        tdeadright[3] = tmp2[106];
        tdeadright[4] = tmp2[107];
        Animation dright = new Animation(0.2f, tdeadright);
        TextureRegion[]tdeaddown = new TextureRegion[5];
        tdeaddown[0] = tmp2[22];
        tdeaddown[1] = tmp2[23];
        tdeaddown[2] = tmp2[24];
        tdeaddown[3] = tmp2[25];
        tdeaddown[4] = tmp2[26];
        Animation ddown = new Animation(0.2f, tdeaddown);
        TextureRegion[]tdeadleft = new TextureRegion[5];
        tdeadleft[0] = tmp2[76];
        tdeadleft[1] = tmp2[77];
        tdeadleft[2] = tmp2[78];
        tdeadleft[3] = tmp2[79];
        tdeadleft[4] = tmp2[80];
        Animation dleft = new Animation(0.2f, tdeadleft);
        TextureRegion[]tdeadupright = new TextureRegion[5];
        tdeadupright[0] = tmp2[157];
        tdeadupright[1] = tmp2[158];
        tdeadupright[2] = tmp2[159];
        tdeadupright[3] = tmp2[160];
        tdeadupright[4] = tmp2[161];
        Animation dupright = new Animation(0.2f, tdeadupright);
        TextureRegion[]tdeadrightdown = new TextureRegion[5];
        tdeadrightdown[0] = tmp2[130];
        tdeadrightdown[1] = tmp2[131];
        tdeadrightdown[2] = tmp2[132];
        tdeadrightdown[3] = tmp2[133];
        tdeadrightdown[4] = tmp2[134];
        Animation drightdown = new Animation(0.2f, tdeadrightdown);
        TextureRegion[]tdeaddownleft = new TextureRegion[5];
        tdeaddownleft[0] = tmp2[211];
        tdeaddownleft[1] = tmp2[212];
        tdeaddownleft[2] = tmp2[213];
        tdeaddownleft[3] = tmp2[214];
        tdeaddownleft[4] = tmp2[215];
        Animation ddownleft = new Animation(0.2f, tdeaddownleft);
        TextureRegion[]tdeadleftup = new TextureRegion[5];
        tdeadleftup[0] = tmp2[184];
        tdeadleftup[1] = tmp2[185];
        tdeadleftup[2] = tmp2[186];
        tdeadleftup[3] = tmp2[187];
        tdeadleftup[4] = tmp2[188];
        Animation dleftup = new Animation(0.2f, tdeadleftup);
        stand = tmp2[0];
        die=tmp2[26];
        ld = (float) 30 / 40f;
        speed = 30;
        uron = 8;
        plasrecx = 17;
        plasrecy = 8;
        rectang = new Rectang(x + plasrecx, y + plasrecy, 11, 10);
        centx = rectang.x + rectang.width / 2;
        centy = rectang.y + rectang.height / 2;
        hero = tmp2[4];
        scalex=0.50f;
        scaley=0.50f;
        height=walkRight[0].getRegionHeight();
        width=walkRight[0].getRegionWidth();
        canbeattacked =true;
        walkanim=new Animation []{wup, wright, wdown, wleft, wupright, wrightdown, wdownleft, wleftup};
        walkanim2=new Animation []{wup2, wright2, wdown2, wleft2, wupright2, wrightdown2, wdownleft2, wleftup2};
        atakanim=new Animation []{apup,apright,apdown,apleft,apupright,aprightdown,apdownleft,apleftup};
        deadanim=new Animation []{dup,dright,ddown,dleft,dupright,drightdown,ddownleft,dleftup};
        imgstandnaprav=new TextureRegion[]{tmp2[20],tmp2[60],tmp2[0],tmp2[40],tmp2[100],tmp2[80],tmp2[140],tmp2[120]};
        //второй вид хотьбы
        int r2=rand.getIntRnd(0,1);
        if(r2==1)walkanim=walkanim2;
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
    public void dopDraw(Batch sb, ShapeRenderer sr) {
        if(sb.isDrawing())for (Life.MinusLife ml :life.minuslife) {
            ml.luron.draw(sb, 1);
        }
        //жизни
        if(sr.isDrawing()&&visibl)sr.rect(x+3, y + 43, life.life / ld, 1, colorlife, colorlife, colorlife, colorlife);
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
        if(napravlenie!=1){nsnapravlenie=napravlenie;
            hero = (TextureRegion)walkanim[nsnapravlenie-2].getKeyFrame(stateTime, true);
        stand=imgstandnaprav[nsnapravlenie-2];}
        rectang.y = y + plasrecy;
        rectang.x = x + plasrecx;
        centx = rectang.x + rectang.width / 2;
        centy = rectang.y + rectang.height / 2;
    }
    public void setState(int newstate, String[] str) {
       /* if(newstate==-1){
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
            case Util.WALKING:
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
            case Util.ATAKA:
                atakunit=game.mapobjects.get(Long.parseLong(str[3]));
                x = Integer.parseInt(str[4]);
                y = Integer.parseInt(str[5]);
                /*        if (str.length > 6) {
                            kaban.timeudar = Integer.parseInt(str[6]);
                            kaban.atakatime = Float.parseFloat(str[7]);
                        }*/
                break;
            case Util.DIED:
                canbeattacked =false;
                ldt = System.currentTimeMillis() + 20000;
                life.stopAll();
                life.life = 0;
                if (str!=null&&str.length > 3){ldt = System.currentTimeMillis() + Integer.parseInt(str[3]);
                hero=die;}
                stateTime=0;
                break;
        }
        state=newstate;oldstate=state;
    }
}
