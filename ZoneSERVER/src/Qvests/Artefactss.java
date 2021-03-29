package Qvests;

import InventItem.Kurtka5;
import Server.NettyServerHandler;
import MapObjects.Units.Player;

public class Artefactss extends Qvest{

    Artefactss(){tip=4;}
    @Override
    void startQvest(Player player) {
        if (!player.qvests.containsKey(tip)) {
            player.qvests.put(tip, 1);
        }
    }
    @Override
    void endQvest(Player player) {
        if (player.qvests.containsKey(tip)) {
            if (player.inventar.isItem(24,false) != null&&player.inventar.isItem(12,false) != null&&player.inventar.isItem(15,false) != null) {
                player.qvests.remove(tip);
                int id1=player.inventar.isItem(24,false).id;
                int id2=player.inventar.isItem(12,false).id;
                int id3=player.inventar.isItem(15,false).id;
                player.inventar.item.remove(id1);
                player.inventar.item.remove(id2);
                player.inventar.item.remove(id3);
                player.inventar.many+=600;
                NettyServerHandler.sendMsgClient("18/"+tip+"/1\0"+"31/"+id1+":"+id2+":"+id3, player.idchanel);}
            else{NettyServerHandler.sendMsgClient("18/"+tip+"/2", player.idchanel);}
        }
    }
}
