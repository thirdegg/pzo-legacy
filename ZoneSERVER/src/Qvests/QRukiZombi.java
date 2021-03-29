package Qvests;

import InventItem.core.Item;
import MapObjects.Units.Player;
import Server.NettyServerHandler;

/**
 * Created by 777 on 06.05.2017.
 */
public class QRukiZombi extends Qvest {

    QRukiZombi() {
        tip = 6;
    }

    @Override
    void startQvest(Player player) {
        if (!player.qvests.containsKey(tip)) {
            player.qvests.put(tip, 1);
        }
    }

    @Override
    void endQvest(Player player) {
        if (player.qvests.containsKey(tip)) {
            int rz=-1;int ids[]=new int[5];
            for(Item it:player.inventar.item.values()){
                if(it.tipitem==42){rz++;if(rz<5)ids[rz]=it.id;}
            }
            if (rz>=4) {
                player.qvests.remove(tip);
                for(int i=0;i<ids.length;i++){
                    player.inventar.item.remove(ids[i]);
                }
                    player.inventar.many+=350;
                NettyServerHandler.sendMsgClient("18/" + tip + "/1\0"+"31/"+ids[0]+":"+ids[1]+":"+ids[2]+":"+ids[3]+":"+ids[4], player.idchanel);
            } else {
                NettyServerHandler.sendMsgClient("18/" + tip + "/2", player.idchanel);
            }
        }
    }
}
