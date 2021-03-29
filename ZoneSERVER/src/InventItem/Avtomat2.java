package InventItem;

import InventItem.core.Weapon;
import MapObjects.Units.Player;
import Maps.WMap;
import Server.NettyServerHandler;

/**
 * Created by 777 on 02.04.2017.
 */
public class Avtomat2 extends Weapon {
    public Avtomat2() {
        super(3, 25, 250, 2, 20);
        id = generatorId.incrementAndGet();
        tipitem = 30;
        cena=21000;

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
