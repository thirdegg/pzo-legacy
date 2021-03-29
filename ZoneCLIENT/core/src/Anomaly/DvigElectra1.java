package Anomaly;

import GameWorld.MapObject;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import util.Overlap;
import util.Sounds;
import util.Util;

import java.util.HashMap;

public class DvigElectra1 extends Electra {
    private final int LEFT = 5, RIGHT = 3, UP = 2, DOWN = 4, STOP = 1, UR = 6, RD = 7, DL = 8, LU = 9;
    public boolean vzriv;
    public int napravlenie = 1;
    TextureRegion[]tdanim;
    Animation danim;
    float stateTime2;
    int speed = 100;
    float rot;
    public DvigElectra1(int xx, int yy, long id,HashMap<Long, MapObject> mapobjects,int napravlenie) {
        super(xx, yy,id,mapobjects);
        tip = Util.DVIGELECTRA;
        Texture electr2 = new Texture(Gdx.files.internal("dvigelec.png"));
        TextureRegion[][] tmp2 = TextureRegion.split(electr2, electr2.getWidth() / 2, electr2.getHeight());
        int index2 = 0;
        tdanim = new TextureRegion[2];
            for (int j = 0; j < 2; j++) {
                tdanim[index2++] = tmp2[0][j];
            }
        danim = new Animation(0.03f, tdanim);
        scalex=0.4f;
        scaley=0.4f;
        visibl=false;
        this.napravlenie=napravlenie;
    }

    public void run(float delta) {
        rot+=4f;
        if(rot>351)rot=0;
        stateTime2 += Gdx.graphics.getDeltaTime();
        if (vzriv) {
            stateTime += Gdx.graphics.getDeltaTime();
            electra = (TextureRegion) anim.getKeyFrame(stateTime, true);
            if (anim.getKeyFrameIndex(stateTime) == 9) {
                vzriv = false;
                stateTime = 0;
                visibl=false;
            }
            super.setRegion(electra);}
        move(delta);
    }
    @Override
    public void dopDraw(Batch sb, ShapeRenderer sr) {
        if(sb.isDrawing())sb.draw((TextureRegion) danim.getKeyFrame(stateTime2, true),centx-17,centy-17,17,17,34,34,1,1,rot);
    }
    void move(float delta) {
        switch (napravlenie) {
            case UP:
                y += speed * delta;
                break;
            case RIGHT:
                x += speed * delta;
                break;
            case DOWN:
                y -= speed * delta;
                break;
            case LEFT:
                x -= speed * delta;
                break;
            case UR:
                y += speed * delta;
                x += speed * delta;
                break;
            case RD:
                x += speed * delta;
                y -= speed * delta;
                break;
            case DL:
                y -= speed * delta;
                x -= speed * delta;
                break;
            case LU:
                x -= speed * delta;
                y += speed * delta;
                break;
        }
        centx = x + 42;
        centy = y + 30;
    }
    public void setState(int newstate, String[] str) {
        switch(newstate){
            //WALKING
            case 1:
                if(str.length<6)return;
        centx = Integer.parseInt(str[3]);
        centy = Integer.parseInt(str[4]);
        x = centx - 42;
        y = centy - 30;
        napravlenie = Integer.parseInt(str[5]);
        break;
        //VZRIV
            case 2:
                visibl=true;
                if(GameWorld.Game.rain)uron=40;else uron=30;
                vzriv = true;
                Sounds.playElectra();
                break;
        }
    }
}
