package InventItem.radiodetali;

import InventItem.Item;

/**
 * Created by 777 on 04.05.2017.
 */
public class MedProvoda extends Item {
    public MedProvoda(int idd) {
        id = idd;
        tipitem = 35;
        name="Медные провода";
        opisanie="Очень нужная вещь в эллектронике.";
        cena=50;
    }
}
