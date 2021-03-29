package InventItem.radiodetali;

import InventItem.Item;

/**
 * Created by 777 on 04.05.2017.
 */
public class Mikrochip extends Item {
    public Mikrochip(int idd) {
        id = idd;
        tipitem = 39;
        name="Микрочип";
        opisanie="Используются для вычислений информационных\n операций.";
        cena=50;
    }
}
