package GameWorld;

import Unit.Hero;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.Collection;

public class Vibros {
  public static  boolean vibros;
    float vibrostime;
    int timedou = 15, timeuron;
    int duration;
    //пиковый максимальный урон
    int damage;
    //колличество итераций для достижения пикового состояния
    int collitter;
    //кол-во урона для повышения
    int colldmg;
    //шаг времени итерации
    int timeitter;
    //переменная времени
    long stepTime, startTime;
    //draw
    float colorr = 1, colorg = 1, colorb = 1;
    boolean colorv, red;
    int camerad;

    void startVibros() {
        startTime = System.currentTimeMillis();
        stepTime = startTime;
        vibros = true;
        duration = 69000;
        damage = 3;
        collitter = 3;
        timeitter = 23000;
        colldmg = 3;
        vibrostime = 0;
        timeuron = (int) (vibrostime + timedou);
        System.out.println("выброс");
    }

    void startVibros2(int coll, int time, float vt, int tu) {
        startTime = System.currentTimeMillis();
        stepTime = startTime;
        vibros = true;
        vibrostime = vt;
        timeuron = tu;
        int pt = 0;
        if (coll == 3) {
            pt = 46000;
            damage = 3;
        }
        if (coll == 2) {
            pt = 23000;
            damage = 6;
        }
        if (coll == 1) damage = 9;
        duration = time + pt;
        collitter = coll;
        colldmg = 3;
        timeitter = time;
        System.out.println("выброс2");
    }

    void run(Collection<MapObject> players, float delta) {
        vibrostime += 10 * delta;
        if (System.currentTimeMillis() - startTime < duration) {
            if (System.currentTimeMillis() - stepTime > timeitter && collitter != 0) {
                damage += colldmg;
                collitter--;
                timeitter = 23000;
                stepTime = System.currentTimeMillis();
            }
        } else {
            endVibros();
        }
        // наносим урон
        if (vibrostime > timeuron) {
            timeuron = (int) (vibrostime + timedou);
            for (MapObject p : players) {
                if (p.tip == 1 && p.canbeattacked) p.life.life -= damage;
            }
        }
    }

    void endVibros() {
        vibros = false;
        collitter = 0;
    }

    void drawVibros(Batch sb, Hero hero, OrthographicCamera camera) {
        switch (collitter) {
            case 3:
                if (!colorv) {
                    colorg -= 0.0045;
                    colorb -= 0.0045;
                    if (colorb < 0.4) colorv = true;
                } else {
                    colorg += 0.0045;
                    colorb += 0.0045;
                    if (colorg > 0.95f) colorv = false;
                }
                sb.setColor(colorr, colorg, colorb, 1);
                break;
            case 2:
                if (colorb > 0.14) {
                    colorg -= 0.001;
                    colorb -= 0.001;
                }
                sb.setColor(colorr, colorg, colorb, 1);
                break;
            case 1:
                camerad++;
                switch (camerad) {
                    case 2:
                        if (hero.x > 216 && hero.x < 1336) {
                            camera.position.x = hero.x + 23.5f;
                        }
                        if (hero.y > 140 && hero.y < 1420) {
                            camera.position.y = hero.y + 19.5f;
                        }
                        break;
                    case 4:
                        camera.position.x += 4;
                        break;
                    case 6:
                        camera.position.y -= 4;
                        break;
                    case 8:
                        camera.position.x -= 4;
                        break;
                    case 10:
                        camera.position.y += 4;
                        camerad = 0;
                        colorg = 0.14f;
                        colorb = 0.14f;
                        sb.setColor(colorr, colorg, colorb, 1);
                        red = true;
                        break;
                }
                break;
        }
    }

    void stbColor(Batch sb) {
        if (colorb < 1) {
            colorg += 0.001;
            colorb += 0.001;
        } else red = false;
        sb.setColor(colorr, colorg, colorb, 1);
    }
}
