package InventItem.artefacts;

import InventItem.Item;

public class ArtVeter  extends Item {
    public ArtVeter(int idd) {
        id = idd;
        tipitem = 24;
        name="Артефакт ветер";
        opisanie="При использовании увеличивает скорость\n передвижения владельца на 9 единиц.";
        cena=1000;
    }
}
