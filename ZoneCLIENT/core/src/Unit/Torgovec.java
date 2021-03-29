package Unit;

import GameWorld.MapObject;
import InventItem.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Array;
import util.Rectang;
import util.Util;

public class Torgovec extends MapObject {
    public Array<Item>items=new Array<Item>();
    Label name;
    Color colorlife;
    public Torgovec(float x, float y, long id, LabelStyle ls, LabelStyle lsname) {
        super("torgtmx.png", x, y, 4, ls,true);
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
        rectang = new Rectang(x, y, 35, 35);
        tip = Util.NPC;
        this.id = id;
        centx = x + 5;
        centy = y + 9;
        name = new Label("Торговец", lsname);
        name.setPosition(x - 28, y + 33);
        name.setColor(Color.BLUE);
        colorlife = new Color(Color.GREEN);
        scalex=0.50f;
        scaley=0.50f;
    }
    public void dopDraw(Batch sb, ShapeRenderer sr) {
if(sb.isDrawing())name.draw(sb, 1);
    }
}
