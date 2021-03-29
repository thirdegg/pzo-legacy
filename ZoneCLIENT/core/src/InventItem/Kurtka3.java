package InventItem;

public class Kurtka3 extends Item {
    private final int fizarmor = 3;
    private final int ellarmor = 5;
    private final int pullarmor = 5;

    public Kurtka3(int idd) {
        id = idd;
        tipitem = 16;
        useslot = 1;
        name="Куртка 3";
        opisanie="Физическая защита - 15%\nЭлектрозащита - 25%\nПулестойкость - 20%";
        cena=7800;
    }

}
