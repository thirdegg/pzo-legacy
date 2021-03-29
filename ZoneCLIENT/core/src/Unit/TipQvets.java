package Unit;

import GameWorld.MapObject;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import util.Rectang;
import util.Util;

public class TipQvets extends MapObject {
    Label name;
    Color colorlife;
    public TipQvets(float x, float y, long id, LabelStyle ls, LabelStyle lsname) {
        super("qvesttmx.png", x, y, 4, ls,true);
        rectang = new Rectang(x, y, 35, 35);
        tip = Util.QVEST;
        this.id = id;
        centx = x + 5;
        centy = y + 9;
        name = new Label("Раздаю квесты", lsname);
        name.setPosition(x - 40, y + 33);
        name.setColor(Color.BLUE);
        colorlife = new Color(Color.GREEN);
        scalex=0.50f;
        scaley=0.50f;
    }
    public void dopDraw(Batch sb, ShapeRenderer sr) {
        if(sb.isDrawing()) name.draw(sb, 1);
    }
}
