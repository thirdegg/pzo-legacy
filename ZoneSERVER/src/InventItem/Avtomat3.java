package InventItem;

import InventItem.core.Weapon;
import MapObjects.Units.Player;
import Maps.WMap;
import Server.NettyServerHandler;

/**
 * Created by 777 on 02.04.2017.
 */
public class Avtomat3 extends Weapon {
    public Avtomat3() {
        super(4, 25, 250, 1, 15);
        id = generatorId.incrementAndGet();
        tipitem = 31;
        cena=42000;

    }
    @Override
    public void odet(Player player, boolean send) {
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
