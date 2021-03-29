package InventItem;

import InventItem.core.Item;

public class Patrony extends Item {
    public Patrony() {
        id = generatorId.incrementAndGet();
        tipitem = 14;
        dopintcolvo = 70;
        cena=140;
    }
}
