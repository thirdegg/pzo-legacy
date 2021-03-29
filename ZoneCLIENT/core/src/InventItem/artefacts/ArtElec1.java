package InventItem.artefacts;

import InventItem.Item;

public class ArtElec1 extends Item {
    public ArtElec1(int idd) {
         id = idd;
        tipitem = 15;
        use=true;
        name="Артефакт Синева";
        opisanie="При активации, создает перед вашим игроком\nаномалию искра, первого уровня.";
        cena=100;
    }
}
