package InventItem;

public class Pistolet4 extends Weapon {
    public Pistolet4(int idd) {
        super(7, 8, 250, 6, 15);
        id = idd;
        tipitem = 20;
        useslot = 2;
        name="Пистолет";
        opisanie="Калибр патронов 19х8\nУрон - 7\nСкорострельность - 6\nРадиус стрельбы - 250\nОбойма - 8 патронов\nСкорость перезарядки - 15";
        cena=14000;
    }


}