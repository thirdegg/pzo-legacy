package InventItem;

import InventItem.core.Item;
import MapObjects.Units.Player;

public class Kurtka3 extends Item {
    private final int fizarmor = 15;
    private final int ellarmor = 25;
    private final int pullarmor = 20;

    public Kurtka3() {
        id = generatorId.incrementAndGet();
        tipitem = 16;
        useslot = 1;
        cena=7800;
    }
    @Override
    public void odet(Player player,boolean send) {
        player.fizarmor+=fizarmor;
        player.ellarmor+=ellarmor;
        player.pullarmor+=pullarmor;
        if(send)player.sendMsg("2/8/" + player.id + "/"+tipitem,true);
    }
    @Override
    public void snyat(Player player,boolean send) {
        player.fizarmor-=fizarmor;
        player.ellarmor-=ellarmor;
        player.pullarmor-=pullarmor;
        if(send)player.sendMsg("2/8/" + player.id + "/"+""+-1,true);
    }
}
