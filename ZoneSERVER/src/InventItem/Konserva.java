package InventItem;

import InventItem.core.Item;
import MapObjects.Units.Player;
import Maps.WMap;

public class Konserva extends Item {
    private final int golod = 7;

    public Konserva() {
        id = generatorId.incrementAndGet();
        tipitem = 6;
        use=true;
        cena=50;
    }
    @Override
    public void use(WMap map,Player player) {
        player.golod -= golod;
    }
}
