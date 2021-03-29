package InventItem;

import InventItem.core.Item;
import MapObjects.Units.Player;

public class Kurtka2 extends Item {
    private final int fizarmor = 20;
    private final int ellarmor = 25;
    private final int pullarmor = 20;

    public Kurtka2() {
        id = generatorId.incrementAndGet();
        tipitem = 0;
        useslot = 1;
        cena=9600;
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
