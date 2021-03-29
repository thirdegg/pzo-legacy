package InventItem.artefacts;

import InventItem.core.Item;
import MapObjects.Units.Player;

/**
 * Created by 777 on 03.01.2017.
 */
public class ArtVeter  extends Item {
    public ArtVeter() {
        id = generatorId.incrementAndGet();
        tipitem = 24;
        cena=1000;
    }
    @Override
    public void odet(Player player,boolean send) {
        player.speed+=9;
        if(send)player.sendMsg("2/9/" + player.id + "/4/" + (int) player.speed,true);
    }
    @Override
    public void snyat(Player player,boolean send) {
        player.speed-=9;
        if(send)player.sendMsg("2/9/" + player.id + "/4/" + (int) player.speed,true);
    }
}
