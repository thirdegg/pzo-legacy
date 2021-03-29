package InventItem;

/**
 * Created by 777 on 02.04.2017.
 */
public class Avtomat3 extends Weapon {
    public Avtomat3(int idd) {
        super(4, 25, 250, 1, 15);
        id = idd;
        tipitem = 31;
        useslot = 2;
        name="Автомат";
        opisanie="Калибр патронов 19х8\nУрон - 4\nСкорострельность - 1\nРадиус стрельбы - 250\nОбойма - 25 патронов\nСкорость перезарядки - 15";
        cena=21000;
    }

}