package InventItem;

import InventItem.core.Item;
import Maps.WMap;
import Server.NettyServerHandler;
import MapObjects.Units.Player;


public class Flaga extends Item {
    public Flaga() {
        id = generatorId.incrementAndGet();
        tipitem = 4;
        use=true;
        cena=40;
    }
    @Override
    public void use(WMap map,Player player) {
        if (player.zhazda > 70) {
            player.zhazda -= 10;
            player.sendMsg("2/9/" + player.id + "/6/" + (int) player.zhazda,true);
        } else {
            player.zhazda -= 10;
            NettyServerHandler.sendMsgClient("2/9/" + player.id + "/6/" + (int) player.zhazda, player.idchanel);
        }
    }
}
