package InventItem.radiodetali;

import InventItem.core.Item;

/**
 * Created by 777 on 04.05.2017.
 */
public class MedProvoda extends Item {
    public MedProvoda() {
        id = generatorId.incrementAndGet();
        tipitem = 35;
        cena=50;
    }
}
