package Qvests;

import InventItem.artefacts.ArtArmor;
import MapObjects.Units.Player;
import Server.NettyServerHandler;

public class KillBossKaban extends Qvest{
    KillBossKaban(){tip=8;}
    void startQvest(Player player) {
        if (!player.qvests.containsKey(tip)) {
            player.qvests.put(tip, 1);
        }
    }

    void endQvest(Player player) {
        if (player.qvests.containsKey(tip)) {
            if (player.inventar.isItem(45,false) != null) {
                player.qvests.remove(tip);
                int id1=player.inventar.isItem(45,false).id;
                player.inventar.item.remove(id1);
                     ArtArmor art = new ArtArmor();
                    player.inventar.addItem(art);
                NettyServerHandler.sendMsgClient("18/"+tip+"/1\0"+"31/"+id1, player.idchanel);}
            else{NettyServerHandler.sendMsgClient("18/"+tip+"/2", player.idchanel);}
        }
    }
}