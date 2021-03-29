package GameWorld;

import com.badlogic.gdx.graphics.Texture;

public class Puli {
    int lostx, losty, tipmov;
    float speed = 1000, x, y, angle;
    boolean run = true;
    Texture pul;

    public Puli(int tip,int sspeed) {
        switch(tip){
            case 1:
            pul = new Texture("pulya.png");
                break;
            case 2:
                pul = new Texture("pulkrys.png");
                break;
        }
        speed=sspeed;
    }

    void update(float delta) {
        if (run) {
            switch (tipmov) {
                case 1:
                    x += speed * delta * Math.cos(angle);
                    y += speed * delta * Math.sin(angle);
                    break;
                case 2:
                    x -= speed * delta * Math.cos(angle);
                    y -= speed * delta * Math.sin(angle);
                    break;
                case 3:
                    x -= speed * delta * Math.cos(angle);
                    y += speed * delta * Math.sin(angle);
                    break;
                case 4:
                    x += speed * delta * Math.cos(angle);
                    y -= speed * delta * Math.sin(angle);
                    break;
                case 5:
                    y += speed * delta;
                    break;
                case 6:
                    y -= speed * delta;
                    break;
                case 7:
                    x -= speed * delta;
                    break;
                case 8:
                    x += speed * delta;
                    break;
            }
            if (x > lostx - 18 && x < lostx + 18 && y > losty - 18 && y < losty + 18) {
                x = lostx;
                y = losty;
                run = false;
            }
        }
    }

    void atansetup(int lx, int ly) {
        lostx = lx;
        losty = ly;
        if (x < lostx && y < losty) {
            angle = (float) Math.atan((y - losty) / (x - lostx));
            tipmov = 1;
        }
        if (x > lostx && y > losty) {
            angle = (float) Math.atan((losty - y) / (lostx - x));
            tipmov = 2;
        }
        if (x > lostx && y < losty) {
            angle = (float) Math.atan((y - losty) / (lostx - x));
            tipmov = 3;
        }
        if (x < lostx && y > losty) {
            angle = (float) Math.atan((losty - y) / (x - lostx));
            tipmov = 4;
        }
        if (x == lostx && y < losty) {
            tipmov = 5;
        }
        if (x == lostx && y > losty) {
            tipmov = 6;
        }
        if (x > lostx && y == losty) {
            tipmov = 7;
        }
        if (x < lostx && y == losty) {
            tipmov = 8;
        }
    }
}
