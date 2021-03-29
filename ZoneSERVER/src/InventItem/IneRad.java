package InventItem;

import InventItem.core.Item;
import MapObjects.Units.Player;
import Maps.WMap;

public class IneRad extends Item {
    public IneRad() {
        id = generatorId.incrementAndGet();
        tipitem = 10;
        use=true;
        cena=30;
    }
    @Override
    public void use(WMap map,Player pl) {
        pl.radiation -= 2.5;
        if (pl.radiation < 0) pl.radiation = 0;
        pl.sendMsg("2/9/" + pl.id + "/7/" + (int) pl.radiation,true);
    }
}
