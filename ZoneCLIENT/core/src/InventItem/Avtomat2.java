package InventItem;


/**
 * Created by 777 on 02.04.2017.
 */
public class Avtomat2 extends Weapon {
    public Avtomat2(int idd) {
        super(3, 25, 250, 2, 20);
        id = idd;
        tipitem = 30;
        useslot = 2;
        name="Автомат";
        opisanie="Калибр патронов 19х8\nУрон - 3\nСкорострельность - 2\nРадиус стрельбы - 250\nОбойма - 25 патронов\nСкорость перезарядки - 20";
        cena=10500;
    }

}
