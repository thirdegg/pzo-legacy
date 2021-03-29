package InventItem;

import InventItem.core.Item;

/**
 * Created by 777 on 30.12.2016.
 */
public class ItBinokl extends Item {
    public ItBinokl() {
        id = generatorId.incrementAndGet();
        tipitem = 23;
        vibrosit=false;
    }
}
