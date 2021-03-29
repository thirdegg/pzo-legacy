package InventItem.artefacts;

import InventItem.core.Item;
import MapObjects.Units.Player;

/**
 * Created by 777 on 01.09.2017.
 */
public class ArtArmor1 extends Item {
    public ArtArmor1() {
        id = generatorId.incrementAndGet();
        tipitem = 120;
        cena=600;
    }

    @Override
    public void odet(Player player, boolean send) {
        player.pullarmor += 4;
        player.ellarmor +=4;
        player.fizarmor+=4;
    }

    @Override
    public void snyat(Player player, boolean send) {
        player.pullarmor -= 4;
        player.ellarmor -=4;
        player.fizarmor-=4;
    }
}