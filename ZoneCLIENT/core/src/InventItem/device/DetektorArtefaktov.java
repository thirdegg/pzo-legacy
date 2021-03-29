package InventItem.device;

import InventItem.Item;

/**
 * Created by 777 on 06.05.2017.
 */
public class DetektorArtefaktov extends Item {
    public DetektorArtefaktov(int idd) {
        id = idd;
        tipitem = 44;
        name="Детектор аномалий";
        opisanie="При приближении к аномалии,\nиздает звуковые сигналы.";
        cena=7000;
    }
}
