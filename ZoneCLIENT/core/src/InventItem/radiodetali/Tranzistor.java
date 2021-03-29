package InventItem.radiodetali;

import InventItem.Item;

/**
 * Created by 777 on 04.05.2017.
 */
public class Tranzistor extends Item {
    public Tranzistor(int idd) {
        id = idd;
        tipitem = 37;
        name="Транзистор";
        opisanie="Работает как электрокнопка.";
        cena=50;
    }
}
