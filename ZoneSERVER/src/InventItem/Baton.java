package InventItem;

import InventItem.core.Item;
import Maps.WMap;
import Server.NettyServerHandler;
import MapObjects.Units.Player;

public class Baton extends Item {
    public Baton() {
        id = generatorId.incrementAndGet();
        tipitem = 5;
        use=true;
        cena=30;
    }
    @Override
    public void use(WMap map, Player player) {
        if (player.golod > 70) {
            player.golod -= 10;
            player.sendMsg("2/9/" + player.id + "/5/" + (int) player.golod,true);
        } else {
            player.golod -= 10;
            NettyServerHandler.sendMsgClient("2/9/" + player.id + "/5/" + (int) player.golod, player.idchanel);
        }
    }
}
