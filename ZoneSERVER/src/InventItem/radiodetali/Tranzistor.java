package InventItem.radiodetali;

import InventItem.core.Item;

/**
 * Created by 777 on 04.05.2017.
 */
public class Tranzistor extends Item {
    public Tranzistor() {
        id = generatorId.incrementAndGet();;
        tipitem = 37;
        cena=50;
    }
}
