package InventItem;

public class Pistolet2 extends Weapon {
    public Pistolet2(int idd) {
        super(5, 5, 200, 6, 30);
        id = idd;
        tipitem = 18;
        useslot = 2;
        name="Пистолет";
        opisanie="Калибр патронов 19х8\nУрон - 5\nСкорострельность - 6\nРадиус стрельбы - 200\nОбойма - 5 патронов\nСкорость перезарядки - 30";
        cena=3200;
    }
}
