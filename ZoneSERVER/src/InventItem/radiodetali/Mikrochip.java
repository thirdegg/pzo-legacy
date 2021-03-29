package InventItem.radiodetali;

import InventItem.core.Item;

/**
 * Created by 777 on 04.05.2017.
 */
public class Mikrochip extends Item {
    public Mikrochip() {
        id = generatorId.incrementAndGet();
        tipitem = 39;
        cena=50;
    }
}
