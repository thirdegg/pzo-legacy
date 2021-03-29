package InventItem.radiodetali;

import InventItem.core.Item;

/**
 * Created by 777 on 04.05.2017.
 */
public class Display extends Item {
    public Display() {
        id = generatorId.incrementAndGet();
        tipitem = 36;
        cena=50;
    }
}
