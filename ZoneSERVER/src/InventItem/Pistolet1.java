package InventItem;

import InventItem.core.Weapon;
import Maps.WMap;
import Server.NettyServerHandler;
import MapObjects.Units.Player;

public class Pistolet1 extends Weapon {
    public Pistolet1() {
        super(4, 6, 150, 10, 25);
        id = generatorId.incrementAndGet();
        tipitem = 13;
        cena=600;
    }
    @Override
    public void odet(Player player,boolean send) {
        player.uron += uron;
        player.gun = this;
        if(send)NettyServerHandler.sendMsgClient("2/9/" + player.id + "/2/" + (int) player.uron, player.idchanel);
    }
    @Override
    public void snyat(Player player,boolean send) {
        player.uron -= uron;
        player.gun = null;
        if(send)NettyServerHandler.sendMsgClient("2/9/" + player.id + "/2/" + (int) player.uron, player.idchanel);
    }

}
