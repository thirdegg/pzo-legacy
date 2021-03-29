package InventItem.artefacts;

import InventItem.Item;

public class ArtHeart extends Item {
    public ArtHeart(int idd) {
        id = idd;
        tipitem = 27;
        name="Артефакт сердце";
        opisanie="Повышает максимальное кол-во хитпоинтов на 30.";
        cena=1000;
    }
}
