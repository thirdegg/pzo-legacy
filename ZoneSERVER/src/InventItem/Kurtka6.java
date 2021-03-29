package InventItem;

import InventItem.core.Item;
import MapObjects.Units.Player;

public class Kurtka6 extends Item {
    private final int fizarmor =30;
    private final int ellarmor =30;
    private final int pullarmor =35;

    public Kurtka6() {
        id = generatorId.incrementAndGet();
        tipitem = 26;
        useslot = 1;
        cena=21000;
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
