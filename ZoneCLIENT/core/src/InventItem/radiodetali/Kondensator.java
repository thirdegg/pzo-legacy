package InventItem.radiodetali;

import InventItem.Item;

/**
 * Created by 777 on 04.05.2017.
 */
public class Kondensator extends Item {
    public Kondensator(int idd) {
        id = idd;
        tipitem = 38;
        name="Конденсатор";
        opisanie="Используются для сглаживания пульсаций тока.";
        cena=50;
    }
}
