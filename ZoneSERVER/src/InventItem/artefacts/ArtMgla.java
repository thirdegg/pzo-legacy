package InventItem.artefacts;

import InventItem.core.Item;

public class ArtMgla extends Item {
    public ArtMgla() {
        id = generatorId.incrementAndGet();
        tipitem = 12;
        cena=50;
    }
}
