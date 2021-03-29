package InventItem.device;

import InventItem.core.Item;
import MapObjects.Units.Player;

/**
 * Created by 777 on 06.05.2017.
 */
public class DetektorAnomalys extends Item {
    public DetektorAnomalys() {
        id = generatorId.incrementAndGet();
        tipitem = 44;
        cena=7000;
    }
    public void odet(Player player, boolean send){
        player.detektoranomalys=true;
    }
    public void snyat(Player player,boolean send){
        player.detektoranomalys=false;
    }
}
