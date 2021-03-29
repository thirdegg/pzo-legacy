package InventItem;

import InventItem.core.Weapon;
import Maps.WMap;
import Server.NettyServerHandler;
import MapObjects.Units.Player;

public class Pistolet3 extends Weapon {
    public Pistolet3() {
        super(6, 8, 200, 7, 15);
        id = generatorId.incrementAndGet();
        tipitem = 19;
        cena=6000;
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