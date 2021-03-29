package InventItem.device;

import InventItem.Item;

/**
 * Created by 777 on 08.08.2017.
 */
public class Radiomiter extends Item {
    public Radiomiter(int idd) {
        id = idd;
        tipitem = 97;
        name="Радиомитер";
        opisanie="Оповещает звуковым сигналом о наличии\n радиации и показывает полученную дозу.";
        cena=3000;
    }
}
