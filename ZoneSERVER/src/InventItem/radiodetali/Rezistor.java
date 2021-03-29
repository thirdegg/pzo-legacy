package InventItem.radiodetali;

import InventItem.core.Item;

/**
 * Created by 777 on 04.05.2017.
 */
public class Rezistor extends Item {
    public Rezistor() {
        id = generatorId.incrementAndGet();
        tipitem = 41;
        cena=50;
    }
}
