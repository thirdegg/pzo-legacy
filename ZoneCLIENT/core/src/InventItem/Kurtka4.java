package InventItem;

public class Kurtka4 extends Item {
    private final int fizarmor = 6;
    private final int ellarmor = 10;
    private final int pullarmor = 9;

    public Kurtka4(int idd) {
        id=idd;
        tipitem = 17;
        useslot = 1;
        name="Куртка 4";
        opisanie="Физическая защита - 30%\nЭлектрозащита - 45%\nПулестойкость - 50%";
        cena=39000;
    }

}
