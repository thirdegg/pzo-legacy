package InventItem;

import InventItem.core.Weapon;
import Maps.WMap;
import Server.NettyServerHandler;
import MapObjects.Units.Player;

public class Pistolet2 extends Weapon {
    public Pistolet2() {
        super(5, 5, 200, 6, 30);
        id = generatorId.incrementAndGet();
        tipitem = 18;
        cena=3200;
    }
    @Override
    public void odet(Player player,boolean send) {
        player.uron += uron;
        player.gun = this;
        if(send) NettyServerHandler.sendMsgClient("2/9/" + player.id + "/2/" + (int) player.uron, player.idchanel);
    }
    @Override
    public void snyat(Player player,boolean send) {
        player.uron -= uron;
        player.gun = null;
        if(send) NettyServerHandler.sendMsgClient("2/9/" + player.id + "/2/" + (int) player.uron, player.idchanel);
    }

}
