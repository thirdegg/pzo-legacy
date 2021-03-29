package InventItem.radiodetali;

import InventItem.core.Item;

/**
 * Created by 777 on 04.05.2017.
 */
public class Dinamik extends Item {
    public Dinamik() {
        id = generatorId.incrementAndGet();
        tipitem = 40;
        cena=50;
    }
}
