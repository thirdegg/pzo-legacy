package InventItem;

/**
 * Created by 777 on 29.01.2017.
 */
public class Kurtka5 extends Item {
    private final int fizarmor = 3;
    private final int ellarmor = 5;
    private final int pullarmor = 1;
    public Kurtka5(int idd) {
        id=idd;
        tipitem = 25;
        useslot = 1;
        name="Куртка 5";
        opisanie="Физическая защита - 15%\nЭлектрозащита - 10%\nПулестойкость - 10%";
        cena=4800;
    }

}
