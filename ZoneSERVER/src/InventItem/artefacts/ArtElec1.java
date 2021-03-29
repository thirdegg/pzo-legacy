package InventItem.artefacts;

import InventItem.core.Item;
import MapObjects.Anomaly.Electra1;
import Maps.WMap;
import MapObjects.Units.Player;

public class ArtElec1 extends Item {
    public ArtElec1() {
        id = generatorId.incrementAndGet();
        tipitem = 15;
        use=true;
        cena=100;
    }
    @Override
    public void use(WMap wm, Player player) {
        int x = 0, y = 0;
        switch (player.urdlnapravlenie) {
            case 2:
                x = (int) (player.centx);
                y = (int) (player.centy + 35);
                break;
            case 3:
                x = (int) (player.centx + 35);
                y = (int) (player.centy);
                break;
            case 4:
                x = (int) (player.centx);
                y = (int) (player.centy - 35);
                break;
            case 5:
                x = (int) (player.centx - 35);
                y = (int) (player.centy);
                break;
        }
        Electra1 electra1 = new Electra1(x, y,wm.mapobjects,wm.addmapobjects,true);
        wm.addMapHitObject(electra1,player.urdlnapravlenie,true);
    }
}
