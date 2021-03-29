package MapObjects.Units;

import MapObjects.MapObject;
import Utils.Rectang;
import Utils.Util;

public class TipQvest extends MapObject {
    public TipQvest(float x, float y) {
        id = MapObject.generatorId.incrementAndGet();
        tip = Util.QVEST;
        this.x = x;
        this.y = y;
        centx = x + 5;
        centy = y + 9;
        rectang = new Rectang(x, y, 35, 35);
    }

    void qest(Player player) {

    }
}
