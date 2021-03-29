package InventItem.artefacts;

import InventItem.core.Item;
import MapObjects.Units.Player;

/**
 * Created by 777 on 10.06.2017.
 */
public class ArtVeter1 extends Item {
    public ArtVeter1() {
        id = generatorId.incrementAndGet();
        tipitem = 51;
        cena=600;
    }
    @Override
    public void odet(Player player, boolean send) {
        player.speed+=6;
        if(send)player.sendMsg("2/9/" + player.id + "/4/" + (int) player.speed,true);
    }
    @Override
    public void snyat(Player player,boolean send) {
        player.speed-=6;
        if(send)player.sendMsg("2/9/" + player.id + "/4/" + (int) player.speed,true);
    }
}