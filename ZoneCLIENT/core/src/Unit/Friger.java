package Unit;

import GameWorld.Game;
import GameWorld.MapObject;
import GameWorld.SpatialHashGrid;
import Modules.Life;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import util.Overlap;
import util.Rectang;
import util.Util;

import java.util.List;

public class Friger extends MapObject{
    private final int COLS = 12, ROWS = 22;
    public MapObject atakunit;
    private TextureRegion hero;
    private TextureRegion die, stand;
    public Animation walkanim[],jumpanim[],atakanim[],deadanim[];
    public TextureRegion imgstandnaprav[];
    private float stateTime;
    private Color colorlife;
    public float speed,ld,uron;
    public int napravlenie = 1,oldstate,nsnapravlenie=4;
    public long ldt;
    public float oldx, oldy;
    Game game;
    int speedjump=120,normalspeed=30,firenaprav;
    float timejump;
    boolean animdead;
    public Friger(int xx, int yy, long id, Label.LabelStyle lsuron, Game game) {
        super("cane1.png", xx, yy, 2,lsuron,true);
        this.id = id;
        this.tip = Util.KRUSA;
        colorlife = new Color();
        this.game=game;
        Texture heroTexture = Util.friger;
        TextureRegion[][] tmp = TextureRegion.split(heroTexture, heroTexture.getWidth() / COLS, heroTexture.getHeight() / ROWS);
        TextureRegion[] tmp2 = new TextureRegion[COLS * ROWS];
        int index = 0;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                tmp2[index++] = tmp[i][j];
            }
        }

        //ходьба
        TextureRegion[]walkUp = new TextureRegion[11];
        walkUp[0] = tmp2[33];
        walkUp[1] = tmp2[34];
        walkUp[2] = tmp2[35];
        walkUp[3] = tmp2[36];
        walkUp[4] = tmp2[36];
        walkUp[5] = tmp2[38];
        walkUp[6] = tmp2[39];
        walkUp[7] = tmp2[40];
        walkUp[8] = tmp2[41];
        walkUp[9] = tmp2[42];
        walkUp[10] = tmp2[43];
        Animation wup = new Animation(0.15f, walkUp);

        TextureRegion[]walkRight = new TextureRegion[11];
        walkRight[0] = tmp2[99];
        walkRight[1] = tmp2[100];
        walkRight[2] = tmp2[101];
        walkRight[3] = tmp2[102];
        walkRight[4] = tmp2[103];
        walkRight[5] = tmp2[104];
        walkRight[6] = tmp2[105];
        walkRight[7] = tmp2[106];
        walkRight[8] = tmp2[107];
        walkRight[9] = tmp2[108];
        walkRight[10] = tmp2[109];
        Animation wright = new Animation(0.15f, walkRight);

        TextureRegion[]walkDown = new TextureRegion[11];
        walkDown[0] = tmp2[0];
        walkDown[1] = tmp2[1];
        walkDown[2] = tmp2[2];
        walkDown[3] = tmp2[3];
        walkDown[4] = tmp2[4];
        walkDown[5] = tmp2[5];
        walkDown[6] = tmp2[6];
        walkDown[7] = tmp2[7];
        walkDown[8] = tmp2[8];
        walkDown[9] = tmp2[9];
        walkDown[10] = tmp2[10];
        Animation wdown = new Animation(0.15f, walkDown);

        TextureRegion[]walkLeft = new TextureRegion[11];
        walkLeft[0] = tmp2[66];
        walkLeft[1] = tmp2[67];
        walkLeft[2] = tmp2[68];
        walkLeft[3] = tmp2[69];
        walkLeft[4] = tmp2[70];
        walkLeft[5] = tmp2[71];
        walkLeft[6] = tmp2[72];
        walkLeft[7] = tmp2[73];
        walkLeft[8] = tmp2[74];
        walkLeft[9] = tmp2[75];
        walkLeft[10] = tmp2[76];
        Animation wleft = new Animation(0.15f, walkLeft);

        TextureRegion[]walkUpRight = new TextureRegion[11];
        walkUpRight[0] = tmp2[165];
        walkUpRight[1] = tmp2[166];
        walkUpRight[2] = tmp2[167];
        walkUpRight[3] = tmp2[168];
        walkUpRight[4] = tmp2[169];
        walkUpRight[5] = tmp2[170];
        walkUpRight[6] = tmp2[171];
        walkUpRight[7] = tmp2[172];
        walkUpRight[8] = tmp2[173];
        walkUpRight[9] = tmp2[174];
        walkUpRight[10] = tmp2[175];
        Animation wupright = new Animation(0.15f, walkUpRight);

        TextureRegion[]walkRightDown = new TextureRegion[11];
        walkRightDown[0] = tmp2[132];
        walkRightDown[1] = tmp2[133];
        walkRightDown[2] = tmp2[134];
        walkRightDown[3] = tmp2[135];
        walkRightDown[4] = tmp2[136];
        walkRightDown[5] = tmp2[137];
        walkRightDown[6] = tmp2[138];
        walkRightDown[7] = tmp2[139];
        walkRightDown[8] = tmp2[140];
        walkRightDown[9] = tmp2[141];
        walkRightDown[10] = tmp2[142];
        Animation wrightdown = new Animation(0.15f, walkRightDown);

        TextureRegion[]walkDownLeft = new TextureRegion[11];
        walkDownLeft[0] = tmp2[231];
        walkDownLeft[1] = tmp2[232];
        walkDownLeft[2] = tmp2[233];
        walkDownLeft[3] = tmp2[234];
        walkDownLeft[4] = tmp2[235];
        walkDownLeft[5] = tmp2[236];
        walkDownLeft[6] = tmp2[237];
        walkDownLeft[7] = tmp2[238];
        walkDownLeft[8] = tmp2[239];
        walkDownLeft[9] = tmp2[240];
        walkDownLeft[10] = tmp2[241];
        Animation wdownleft = new Animation(0.15f, walkDownLeft);

        TextureRegion[]walkLeftUp = new TextureRegion[11];
        walkLeftUp[0] = tmp2[198];
        walkLeftUp[1] = tmp2[199];
        walkLeftUp[2] = tmp2[200];
        walkLeftUp[3] = tmp2[201];
        walkLeftUp[4] = tmp2[202];
        walkLeftUp[5] = tmp2[203];
        walkLeftUp[6] = tmp2[204];
        walkLeftUp[7] = tmp2[205];
        walkLeftUp[8] = tmp2[206];
        walkLeftUp[9] = tmp2[207];
        walkLeftUp[10] = tmp2[208];
        Animation wleftup = new Animation(0.15f, walkLeftUp);

        //прыжок
        TextureRegion[]jumpUp = new TextureRegion[10];
        jumpUp[0] = tmp2[44];
        jumpUp[1] = tmp2[45];
        jumpUp[2] = tmp2[46];
        jumpUp[3] = tmp2[47];
        jumpUp[4] = tmp2[48];
        jumpUp[5] = tmp2[49];
        jumpUp[6] = tmp2[50];
        jumpUp[7] = tmp2[51];
        jumpUp[8] = tmp2[52];
        jumpUp[9] = tmp2[53];
        Animation jup = new Animation(0.05f, jumpUp);

        TextureRegion[]jumpRight = new TextureRegion[10];
        jumpRight[0] = tmp2[110];
        jumpRight[1] = tmp2[111];
        jumpRight[2] = tmp2[112];
        jumpRight[3] = tmp2[113];
        jumpRight[4] = tmp2[114];
        jumpRight[5] = tmp2[115];
        jumpRight[6] = tmp2[116];
        jumpRight[7] = tmp2[117];
        jumpRight[8] = tmp2[118];
        jumpRight[9] = tmp2[119];
        Animation jright = new Animation(0.05f, jumpRight);

        TextureRegion[]jumpDown = new TextureRegion[10];
        jumpDown[0] = tmp2[11];
        jumpDown[1] = tmp2[12];
        jumpDown[2] = tmp2[13];
        jumpDown[3] = tmp2[14];
        jumpDown[4] = tmp2[15];
        jumpDown[5] = tmp2[16];
        jumpDown[6] = tmp2[17];
        jumpDown[7] = tmp2[18];
        jumpDown[8] = tmp2[19];
        jumpDown[9] = tmp2[20];
        Animation jdown = new Animation(0.05f, jumpDown);

        TextureRegion[]jumpLeft = new TextureRegion[10];
        jumpLeft[0] = tmp2[77];
        jumpLeft[1] = tmp2[78];
        jumpLeft[2] = tmp2[79];
        jumpLeft[3] = tmp2[80];
        jumpLeft[4] = tmp2[81];
        jumpLeft[5] = tmp2[82];
        jumpLeft[6] = tmp2[83];
        jumpLeft[7] = tmp2[84];
        jumpLeft[8] = tmp2[85];
        jumpLeft[9] = tmp2[86];
        Animation jleft = new Animation(0.05f, jumpLeft);

        TextureRegion[]jumpUpRight = new TextureRegion[10];
        jumpUpRight[0] = tmp2[176];
        jumpUpRight[1] = tmp2[177];
        jumpUpRight[2] = tmp2[178];
        jumpUpRight[3] = tmp2[179];
        jumpUpRight[4] = tmp2[180];
        jumpUpRight[5] = tmp2[181];
        jumpUpRight[6] = tmp2[182];
        jumpUpRight[7] = tmp2[183];
        jumpUpRight[8] = tmp2[184];
        jumpUpRight[9] = tmp2[185];
        Animation jupright = new Animation(0.05f, jumpUpRight);

        TextureRegion[]jumpRightDown = new TextureRegion[10];
        jumpRightDown[0] = tmp2[143];
        jumpRightDown[1] = tmp2[144];
        jumpRightDown[2] = tmp2[145];
        jumpRightDown[3] = tmp2[146];
        jumpRightDown[4] = tmp2[147];
        jumpRightDown[5] = tmp2[148];
        jumpRightDown[6] = tmp2[149];
        jumpRightDown[7] = tmp2[150];
        jumpRightDown[8] = tmp2[151];
        jumpRightDown[9] = tmp2[152];
        Animation jrightdown = new Animation(0.05f, jumpRightDown);

        TextureRegion[]jumpDownLeft = new TextureRegion[10];
        jumpDownLeft[0] = tmp2[242];
        jumpDownLeft[1] = tmp2[243];
        jumpDownLeft[2] = tmp2[244];
        jumpDownLeft[3] = tmp2[245];
        jumpDownLeft[4] = tmp2[246];
        jumpDownLeft[5] = tmp2[247];
        jumpDownLeft[6] = tmp2[248];
        jumpDownLeft[7] = tmp2[249];
        jumpDownLeft[8] = tmp2[250];
        jumpDownLeft[9] = tmp2[251];
        Animation jdownleft = new Animation(0.05f, jumpDownLeft);

        TextureRegion[]jumpLeftUp = new TextureRegion[10];
        jumpLeftUp[0] = tmp2[209];
        jumpLeftUp[1] = tmp2[210];
        jumpLeftUp[2] = tmp2[211];
        jumpLeftUp[3] = tmp2[212];
        jumpLeftUp[4] = tmp2[213];
        jumpLeftUp[5] = tmp2[214];
        jumpLeftUp[6] = tmp2[215];
        jumpLeftUp[7] = tmp2[216];
        jumpLeftUp[8] = tmp2[217];
        jumpLeftUp[9] = tmp2[218];
        Animation jleftup = new Animation(0.05f, jumpLeftUp);

// атака
        TextureRegion[]tapup = new TextureRegion[7];
        tapup[0] = tmp2[54];
        tapup[1] = tmp2[55];
        tapup[2] = tmp2[56];
        tapup[3] = tmp2[57];
        tapup[4] = tmp2[58];
        tapup[5] = tmp2[59];
        tapup[6] = tmp2[60];
        Animation apup = new Animation(0.2f, tapup);

        TextureRegion[]tapright = new TextureRegion[7];
        tapright[0] = tmp2[120];
        tapright[1] = tmp2[121];
        tapright[2] = tmp2[122];
        tapright[3] = tmp2[123];
        tapright[4] = tmp2[124];
        tapright[5] = tmp2[125];
        tapright[6] = tmp2[126];
        Animation apright = new Animation(0.2f, tapright);

        TextureRegion[]tapdown = new TextureRegion[7];
        tapdown[0] = tmp2[21];
        tapdown[1] = tmp2[22];
        tapdown[2] = tmp2[23];
        tapdown[3] = tmp2[24];
        tapdown[4] = tmp2[25];
        tapdown[5] = tmp2[26];
        tapdown[6] = tmp2[27];
        Animation apdown = new Animation(0.2f, tapdown);

        TextureRegion[]tapleft = new TextureRegion[7];
        tapleft[0] = tmp2[87];
        tapleft[1] = tmp2[88];
        tapleft[2] = tmp2[89];
        tapleft[3] = tmp2[90];
        tapleft[4] = tmp2[91];
        tapleft[5] = tmp2[92];
        tapleft[6] = tmp2[93];
        Animation apleft = new Animation(0.2f, tapleft);

        TextureRegion[]tapupright = new TextureRegion[7];
        tapupright[0] = tmp2[186];
        tapupright[1] = tmp2[187];
        tapupright[2] = tmp2[188];
        tapupright[3] = tmp2[189];
        tapupright[4] = tmp2[190];
        tapupright[5] = tmp2[191];
        tapupright[6] = tmp2[192];
        Animation apupright = new Animation(0.2f, tapupright);

        TextureRegion[]taprightdown = new TextureRegion[7];
        taprightdown[0] = tmp2[153];
        taprightdown[1] = tmp2[154];
        taprightdown[2] = tmp2[155];
        taprightdown[3] = tmp2[156];
        taprightdown[4] = tmp2[157];
        taprightdown[5] = tmp2[158];
        taprightdown[6] = tmp2[159];
        Animation aprightdown = new Animation(0.2f, taprightdown);

        TextureRegion[]tapdownleft = new TextureRegion[7];
        tapdownleft[0] = tmp2[252];
        tapdownleft[1] = tmp2[253];
        tapdownleft[2] = tmp2[254];
        tapdownleft[3] = tmp2[255];
        tapdownleft[4] = tmp2[256];
        tapdownleft[5] = tmp2[257];
        tapdownleft[6] = tmp2[258];
        Animation apdownleft = new Animation(0.2f, tapdownleft);

        TextureRegion[]tapleftup = new TextureRegion[7];
        tapleftup[0] = tmp2[219];
        tapleftup[1] = tmp2[220];
        tapleftup[2] = tmp2[221];
        tapleftup[3] = tmp2[222];
        tapleftup[4] = tmp2[223];
        tapleftup[5] = tmp2[224];
        tapleftup[6] = tmp2[225];
        Animation apleftup = new Animation(0.2f, tapleftup);

        //смерть
        TextureRegion[]tdeadup = new TextureRegion[5];
        tdeadup[0] = tmp2[61];
        tdeadup[1] = tmp2[62];
        tdeadup[2] = tmp2[63];
        tdeadup[3] = tmp2[64];
        tdeadup[4] = tmp2[65];
        Animation dup = new Animation(0.2f, tdeadup);

        TextureRegion[]tdeadright = new TextureRegion[5];
        tdeadright[0] = tmp2[127];
        tdeadright[1] = tmp2[128];
        tdeadright[2] = tmp2[129];
        tdeadright[3] = tmp2[130];
        tdeadright[4] = tmp2[131];
        Animation dright = new Animation(0.2f, tdeadright);

        TextureRegion[]tdeaddown = new TextureRegion[5];
        tdeaddown[0] = tmp2[28];
        tdeaddown[1] = tmp2[29];
        tdeaddown[2] = tmp2[30];
        tdeaddown[3] = tmp2[31];
        tdeaddown[4] = tmp2[32];
        Animation ddown = new Animation(0.2f, tdeaddown);

        TextureRegion[]tdeadleft = new TextureRegion[5];
        tdeadleft[0] = tmp2[94];
        tdeadleft[1] = tmp2[95];
        tdeadleft[2] = tmp2[96];
        tdeadleft[3] = tmp2[97];
        tdeadleft[4] = tmp2[98];
        Animation dleft = new Animation(0.2f, tdeadleft);

        TextureRegion[]tdeadupright = new TextureRegion[5];
        tdeadupright[0] = tmp2[193];
        tdeadupright[1] = tmp2[194];
        tdeadupright[2] = tmp2[195];
        tdeadupright[3] = tmp2[196];
        tdeadupright[4] = tmp2[197];
        Animation dupright = new Animation(0.2f, tdeadupright);

        TextureRegion[]tdeadrightdown = new TextureRegion[5];
        tdeadrightdown[0] = tmp2[160];
        tdeadrightdown[1] = tmp2[161];
        tdeadrightdown[2] = tmp2[162];
        tdeadrightdown[3] = tmp2[163];
        tdeadrightdown[4] = tmp2[164];
        Animation drightdown = new Animation(0.2f, tdeadrightdown);

        TextureRegion[]tdeaddownleft = new TextureRegion[5];
        tdeaddownleft[0] = tmp2[259];
        tdeaddownleft[1] = tmp2[260];
        tdeaddownleft[2] = tmp2[261];
        tdeaddownleft[3] = tmp2[262];
        tdeaddownleft[4] = tmp2[263];
        Animation ddownleft = new Animation(0.2f, tdeaddownleft);

        TextureRegion[]tdeadleftup = new TextureRegion[5];
        tdeadleftup[0] = tmp2[226];
        tdeadleftup[1] = tmp2[227];
        tdeadleftup[2] = tmp2[228];
        tdeadleftup[3] = tmp2[229];
        tdeadleftup[4] = tmp2[230];
        Animation dleftup = new Animation(0.2f, tdeadleftup);


        walkanim=new Animation []{wup, wright, wdown, wleft, wupright, wrightdown, wdownleft, wleftup};
        jumpanim=new Animation []{jup, jright, jdown, jleft, jupright, jrightdown, jdownleft, jleftup};
        atakanim=new Animation []{apup,apright,apdown,apleft,apupright,aprightdown,apdownleft,apleftup};
        deadanim=new Animation []{dup,dright,ddown,dleft,dupright,drightdown,ddownleft,dleftup};
        imgstandnaprav=new TextureRegion[]{tmp2[33],tmp2[99],tmp2[0],tmp2[66],tmp2[165],tmp2[132],tmp2[231],tmp2[198]};

        stand = tmp2[0];
        die = tmp2[25];
        ld = (float) 50 / 40f;
        speed = normalspeed;
        uron = 7;
        plasrecx = 17;
        plasrecy = 12;
        rectang = new Rectang(x + plasrecx, y + plasrecy, 11, 10);
        centx = rectang.x + rectang.width / 2;
        centy = rectang.y + rectang.height / 2;
        hero = tmp2[0];
        scalex=0.55f;
        scaley=0.55f;
        height=walkUp[0].getRegionHeight();
        width=walkUp[0].getRegionWidth();
        canbeattacked =true;
    }

    public void run(float delta) {
        switch (state) {
            case Util.WALKING:
                move(delta);
                break;
            case Util.DIED:
                dead();
                break;
            case Util.JUMP:
                jump(delta);
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
        if(sr.isDrawing())if(state!=Util.DIED)sr.rect(x,y +45,life.life / ld, 1,colorlife,colorlife,colorlife,colorlife);
    }
    void move(float delta) {
        stateTime += Gdx.graphics.getDeltaTime();
        switch (napravlenie) {
            case 1:
                hero = stand;
                break;
            case 2:
                y += speed * delta;
                firenaprav=1;
                break;
            case 3:
                x += speed * delta;
                firenaprav=2;
                break;
            case 4:
                y -= speed * delta;
                firenaprav=3;
                break;
            case 5:
                x -= speed * delta;
                firenaprav=4;
                break;
            case 6:
                x += speed/2 * delta;
                y += speed/2 * delta;
                firenaprav=2;
                break;
            case 7:
                x += speed/2 * delta;
                y -= speed/2 * delta;
                firenaprav=2;
                break;
            case 8:
                x -= speed/2 * delta;
                y -= speed/2 * delta;
                firenaprav=4;
                break;
            case 9:
                x -= speed/2 * delta;
                y += speed/2 * delta;
                firenaprav=4;
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
void jump(float delta){
    if(timejump<15){
        timejump+=10*delta;
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
                    x += speed/2 * delta;
                    y += speed/2 * delta;
                    break;
                case 7:
                    x += speed/2 * delta;
                    y -= speed/2 * delta;
                    break;
                case 8:
                    x -= speed/2 * delta;
                    y -= speed/2 * delta;
                    break;
                case 9:
                    x -= speed/2 * delta;
                    y += speed/2 * delta;
                    break;
            }
            if(napravlenie!=1){nsnapravlenie=napravlenie;
                hero = (TextureRegion)jumpanim[nsnapravlenie-2].getKeyFrame(stateTime, true);
                stand=imgstandnaprav[nsnapravlenie-2];}
            rectang.y = y + plasrecy;
            rectang.x = x + plasrecx;
            centx = rectang.x + rectang.width / 2;
            centy = rectang.y + rectang.height / 2;
            if (inRec(game.grid)) {
                x = oldx;
                y = oldy;
            } else {
                oldx = x;
                oldy = y;
            }
        }else{napravlenie=1;state=Util.WALKING;}

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
       /* if(newstate==-1){
            int tip=Integer.parseInt(str[0]);
            switch(tip){
                case 2:
                    newstate=DVIG;
                    break;
                case 14:
                    newstate=DEAD;
                    break;
                case 27:
                    newstate=JUMP;
                    break;
            }
        }*/
        switch(newstate){
            case Util.WALKING:
                canbeattacked =true;
                speed=normalspeed;
                x = Integer.parseInt(str[3]);
                y = Integer.parseInt(str[4]);
                napravlenie = Integer.parseInt(str[5]);
                life.life = Integer.parseInt(str[6]);
                rectang.x = x + plasrecx;
                rectang.y = y + plasrecy;
                centx = rectang.x + rectang.width / 2;
                centy = rectang.y + rectang.height / 2;
                break;
            case Util.DIED:
                canbeattacked =false;
                stateTime=0;
                ldt = System.currentTimeMillis() + 20000;
                life.life = 0;
                life.stopAll();
                if (str.length > 3)ldt = System.currentTimeMillis() + Integer.parseInt(str[3]);
                break;
            case Util.JUMP:
                canbeattacked =true;
                speed=speedjump;
                x = Integer.parseInt(str[3]);
                y = Integer.parseInt(str[4]);
                if(str.length>8){life.life = Integer.parseInt(str[7]);
                timejump=Float.parseFloat(str[8]);}
                else{timejump=0;
                    atakunit=game.mapobjects.get(Long.parseLong(str[6]));
                    if (atakunit != null)game.addPuli(centx, centy+30, (int) (atakunit.centx), (int) (atakunit.centy+25),2,550);}
                napravlenie = Integer.parseInt(str[5]);
                rectang.x = x + plasrecx;
                rectang.y = y + plasrecy;
                centx = rectang.x + rectang.width / 2;
                centy = rectang.y + rectang.height / 2;
                break;
        }
        state=newstate;oldstate=state;
    }
    boolean inRec(SpatialHashGrid grid) {
        if (x > 1575 || x < 0 || y > 1565 || y < 0) return true;
        List<Rectang> colliders = grid.getPotentialColliders(rectang);
        for (Rectang re : colliders) {
            if (Overlap.overlapRectang(re, rectang)) {
                return true;
            }
        }
        return false;
    }
}
