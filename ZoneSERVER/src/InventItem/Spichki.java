package InventItem;

import InventItem.core.Item;
import MapObjects.FireKostr;
import Maps.WMap;
import MapObjects.Units.Player;

public class Spichki extends Item {
    public Spichki() {
        id = generatorId.incrementAndGet();
        tipitem = 2;
        use=true;
        cena=50;
    }
@Override
   public void use(WMap wm, Player player) {
        FireKostr fire = new FireKostr((int) player.x, (int) player.y);
        wm.mapobjects.put(fire.id, fire);
    }
}
