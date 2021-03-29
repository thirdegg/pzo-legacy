package InventItem.artefacts;

import InventItem.Item;


public class ArtArmor extends Item {
    public ArtArmor(int idd) {
        id = idd;
        tipitem = 46;
        name="Артефакт оберег";
        opisanie="Физическая защита - +6%\nЭлектрозащита - +6%\nПулестойкость - +6%";
        cena=800;
    }
}