package InventItem;

import InventItem.core.Item;
import MapObjects.Units.Player;

public class Kurtka1 extends Item {
    private final int fizarmor = 20;
    private final int ellarmor = 10;
    private final int pullarmor = 10;

    public Kurtka1() {
        id = generatorId.incrementAndGet();
        tipitem = 1;
        useslot = 1;
        cena=3600;
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
