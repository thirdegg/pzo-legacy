package InventItem;

import InventItem.core.Item;
import MapObjects.ObjectAkkom;
import Maps.WMap;
import MapObjects.Units.Player;

public class Akkom extends Item {
    public Akkom() {
        id = generatorId.incrementAndGet();
        tipitem = 21;
        use=true;
        vibrosit=false;
    }
@Override
    public void use(WMap wm, Player player) {
        int x = 0, y = 0;
        switch (player.urdlnapravlenie) {
            case 2:
                x = (int) (player.centx);
                y = (int) (player.centy + 30);
                break;
            case 3:
                x = (int) (player.centx + 30);
                y = (int) (player.centy);
                break;
            case 4:
                x = (int) (player.centx);
                y = (int) (player.centy - 30);
                break;
            case 5:
                x = (int) (player.centx - 30);
                y = (int) (player.centy);
                break;
        }
        ObjectAkkom akkom = new ObjectAkkom(x, y,wm.mapobjects,player.name);
        wm.addMapHitObject(akkom,player.urdlnapravlenie,true);
    }
}