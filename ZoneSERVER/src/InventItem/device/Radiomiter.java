package InventItem.device;

import InventItem.core.Item;

/**
 * Created by 777 on 08.08.2017.
 */
public class Radiomiter extends Item {
    public Radiomiter() {
        id = generatorId.incrementAndGet();
        tipitem = 97;
        cena=3000;
    }
}
