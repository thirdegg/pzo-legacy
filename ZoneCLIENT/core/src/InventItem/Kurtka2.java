package InventItem;

public class Kurtka2 extends Item {
    private final int fizarmor = 5;
    private final int ellarmor = 6;
    private final int pullarmor = 5;

    public Kurtka2(int idd) {
         id = idd;
        tipitem = 0;
        useslot = 1;
        name="Куртка 2";
        opisanie="Физическая защита - 20%\nЭлектрозащита - 25%\nПулестойкость - 20%";
        cena=9600;
    }

}
