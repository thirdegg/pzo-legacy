package InventItem.radiodetali;

import InventItem.core.Item;

/**
 * Created by 777 on 04.05.2017.
 */
public class Kondensator extends Item {
    public Kondensator() {
        id = generatorId.incrementAndGet();
        tipitem = 38;
        cena=50;
    }
}
