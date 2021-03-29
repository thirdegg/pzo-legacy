package InventItem.device;

import InventItem.Item;

/**
 * Created by 777 on 15.07.2017.
 */
public class Guitar extends Item {
    public Guitar(int idd) {
        id = idd;
        tipitem = 103;
        use=true;
        remuse=false;
        name="Гитара";
        opisanie="Неизменный атрибут сталкерской романтики.";
        cena=5000;
    }
}
