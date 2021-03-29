package InventItem.artefacts;

import InventItem.core.Item;
import MapObjects.Units.Player;

/**
 * Created by 777 on 01.09.2017.
 */
public class ArtArmor2 extends Item {
    public ArtArmor2() {
        id = generatorId.incrementAndGet();
        tipitem = 121;
        cena=300;
    }

    @Override
    public void odet(Player player, boolean send) {
        player.pullarmor += 2;
        player.ellarmor +=2;
        player.fizarmor+=2;
    }

    @Override
    public void snyat(Player player, boolean send) {
        player.pullarmor -= 2;
        player.ellarmor -=2;
        player.fizarmor-=2;
    }
}