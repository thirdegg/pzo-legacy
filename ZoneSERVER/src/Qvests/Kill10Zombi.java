package Qvests;

import Server.NettyServerHandler;
import MapObjects.Units.Player;

/**
 * Created by 777 on 03.01.2017.
 */
public class Kill10Zombi extends Qvest{

    Kill10Zombi(){tip=3;}
    @Override
    void startQvest(Player player) {
        if (!player.qvests.containsKey(tip)) {
            player.qvests.put(tip, 1);
        }
    }
    @Override
    void endQvest(Player player) {
        if (player.qvests.containsKey(tip)) {
            int progress = player.qvests.get(tip);
            if (progress >= 10) {
                player.inventar.addMany(500);
                player.qvests.remove(tip);
                NettyServerHandler.sendMsgClient("18/"+tip+"/1", player.idchanel);}
            else{NettyServerHandler.sendMsgClient("18/"+tip+"/2", player.idchanel);}
        }
    }
}
