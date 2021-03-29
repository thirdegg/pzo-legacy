package InventItem.device;

import InventItem.core.Item;
import MapObjects.Units.Player;
import Maps.WMap;

/**
 * Created by 777 on 15.07.2017.
 */
public class Guitar extends Item {
    public Guitar() {
        id = generatorId.incrementAndGet();
        tipitem = 103;
        cena=5000;
        use=true;
        remuse=false;
    }
    @Override
    public void use(WMap map, Player player) {
      player.setState(player.SITTING,new String[]{"z","z","z"});
    }
}
