package InventItem.artefacts;

import InventItem.core.Item;
import MapObjects.Units.Player;

public class ArtArmor extends Item {
    public ArtArmor() {
        id = generatorId.incrementAndGet();
        tipitem = 46;
        cena=800;
    }

    @Override
    public void odet(Player player, boolean send) {
        player.pullarmor += 6;
        player.ellarmor +=6;
        player.fizarmor+=6;
    }

    @Override
    public void snyat(Player player, boolean send) {
        player.pullarmor -= 6;
        player.ellarmor -=6;
        player.fizarmor-=6;
    }
}