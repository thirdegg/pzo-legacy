package InventItem.artefacts;

import InventItem.core.Item;
import MapObjects.Units.Player;

/**
 * Created by 777 on 25.03.2017.
 */
public class ArtHeart extends Item {
    public ArtHeart() {
        id = generatorId.incrementAndGet();
        tipitem = 27;
        cena=1000;
    }

    @Override
    public void odet(Player player, boolean send) {
        player.pmLife.maxlife += 30;
        if(send)player.sendMsg("2/9/" + player.id + "/8/" + (int) player.pmLife.maxlife, true);
    }

    @Override
    public void snyat(Player player, boolean send) {
        player.pmLife.maxlife -= 30;
        if(player.pmLife.life>player.pmLife.maxlife)player.pmLife.life=player.pmLife.maxlife;
        if(send)player.sendMsg("2/9/" + player.id + "/8/" + (int) player.pmLife.maxlife, true);
    }
}