package InventItem;

public class Aptechka extends Item {

    public Aptechka(int idd) {
        id = idd;
        tipitem = 11;
        name="Аптечка";
        opisanie="Немного заживляет раны\nЭффект: здоровье +40";
        cena=150;
        use=true;
        fastuse=false;
    }
}
