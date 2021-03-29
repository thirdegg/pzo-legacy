package InventItem.radiodetali;

import InventItem.Item;

/**
 * Created by 777 on 04.05.2017.
 */
public class Rezistor extends Item {
    public Rezistor(int idd) {
        id = idd;
        tipitem = 41;
        name="Резистор";
        opisanie="Уменьшает напряжение и силу тока.";
        cena=50;
    }
}
