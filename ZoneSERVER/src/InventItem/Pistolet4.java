package InventItem;

import InventItem.core.Weapon;
import Maps.WMap;
import Server.NettyServerHandler;
import MapObjects.Units.Player;

public class Pistolet4 extends Weapon {
    public Pistolet4() {
        super(7, 8, 250, 6, 15);
        id = generatorId.incrementAndGet();
        tipitem = 20;
        cena=14000;
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