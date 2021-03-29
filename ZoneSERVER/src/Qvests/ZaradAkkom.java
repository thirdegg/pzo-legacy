package Qvests;

import InventItem.Akkom;
import InventItem.Pistolet2;
import Server.NettyServerHandler;
import MapObjects.Units.Player;

public class ZaradAkkom extends Qvest{
    ZaradAkkom(){tip=0;}
    void startQvest(Player player) {
        if (!player.inventar.getMesto()){
            NettyServerHandler.sendMsgClient("18/"+tip+"/3", player.idchanel);return;}
        if (!player.qvests.containsKey(tip)) {
            player.qvests.put(tip, 1);
            Akkom akk = new Akkom();
            player.inventar.addItem(akk);
        }
    }

    void endQvest(Player player) {
        if (player.qvests.containsKey(tip)&&player.qvests.get(tip)!=99) {
            if (player.inventar.isItem(22,false) != null) {
              //  player.qvests.remove(tip);
                player.qvests.put(tip,99);
                int id1=player.inventar.isItem(22,false).id;
                player.inventar.item.remove(id1);
                if(player.inventar.getMesto()){
                Pistolet2 pist2 = new Pistolet2();
                player.inventar.addItem(pist2);}
                NettyServerHandler.sendMsgClient("18/"+tip+"/1\0"+"31/"+id1, player.idchanel);}
                else{NettyServerHandler.sendMsgClient("18/"+tip+"/2", player.idchanel);}
        }
    }
}
