package InventItem.artefacts;

import InventItem.Item;

/**
 * Created by 777 on 28.03.2017.
 */
public class ArtBlood extends Item {
    public ArtBlood(int idd) {
        id = idd;
        tipitem = 28;
        name="Артефакт кровь";
        opisanie="Позволяет здоровью регенирировать\nс небольшой скоростью.\nЭффект: реген здоровья +5";
        cena=800;
    }
}
