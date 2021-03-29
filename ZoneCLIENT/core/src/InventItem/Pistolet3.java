package InventItem;


public class Pistolet3 extends Weapon {
    public Pistolet3(int idd) {
        super(6, 8, 200, 7, 15);
        id = idd;
        tipitem = 19;
        useslot = 2;
        name="Пистолет";
        opisanie="Калибр патронов 19х8\nУрон - 6\nСкорострельность - 7\nРадиус стрельбы - 200\nОбойма - 8 патронов\nСкорость перезарядки - 15";
        cena=6000;
    }
}