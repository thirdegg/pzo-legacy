package InventItem.artefacts;

import InventItem.Item;

/**
 * Created by 777 on 20.06.2017.
 */
public class ArtBlood2 extends Item {
    public ArtBlood2(int idd) {
        id = idd;
        tipitem = 48;
        name="Артефакт зарево";
        opisanie="Позволяет здоровью регенирировать\nс небольшой скоростью.\nЭффект: реген здоровья +1";
        cena=200;
    }
}
