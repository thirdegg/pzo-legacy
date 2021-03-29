package InventItem;

import InventItem.core.Item;
import MapObjects.Units.Player;
import Maps.WMap;

public class Bint extends Item {
    public Bint() {
        id = generatorId.incrementAndGet();
        tipitem = 8;
        use=true;
        cena=100;
    }
    @Override
    public void use(WMap map,Player player) {
        player.krovotech -= 5;
    }
}
