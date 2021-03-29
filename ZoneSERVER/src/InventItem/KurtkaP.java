package InventItem;

import InventItem.core.Item;
import MapObjects.Units.Player;

/**
 * Created by 777 on 04.04.2017.
 */
public class KurtkaP extends Item {
    private final int fizarmor = 50;
    private final int ellarmor = 50;
    private final int pullarmor = 50;

    public KurtkaP() {
        id = generatorId.incrementAndGet();
        tipitem = 33;
        useslot = 1;
        cena = 1000;
    }

    @Override
    public void odet(Player player, boolean send) {
        player.fizarmor += fizarmor;
        player.ellarmor += ellarmor;
        player.pullarmor += pullarmor;
        if (send) player.sendMsg("2/8/" + player.id + "/" + tipitem, true);
    }

    @Override
    public void snyat(Player player, boolean send) {
        player.fizarmor -= fizarmor;
        player.ellarmor -= ellarmor;
        player.pullarmor -= pullarmor;
        if (send) player.sendMsg("2/8/" + player.id + "/" + "" + -1, true);
    }
}