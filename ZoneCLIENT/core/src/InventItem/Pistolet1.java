package InventItem;

public class Pistolet1 extends Weapon {
    public Pistolet1(int idd) {
        super(4, 6, 150, 10, 25);
        id = idd;
        tipitem = 13;
        useslot = 2;
        name="Пистолет";
        opisanie="Калибр патронов 19х8\nУрон - 4\nСкорострельность - 10\nРадиус стрельбы - 150\nОбойма - 6 патронов\nСкорость перезарядки - 25";
        cena=600;
    }

}
