package InventItem;

public class Flaga extends Item {
    public Flaga(int idd) {
        id = idd;
        tipitem = 4;
        name="Фляга с водой";
        opisanie="Питьевая вода\nЭффект: жажда -10";
        cena=40;
        use=true;
    }
}
