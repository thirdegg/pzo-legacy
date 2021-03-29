package Qvests;

import InventItem.*;
import MapObjects.ObdjBinokl;
import Maps.WMap;
import Server.NettyServerHandler;
import MapObjects.Units.Player;

/**
 * Created by 777 on 30.12.2016.
 */
public class Binokl extends Qvest{
    Binokl(){tip=2;}
    @Override
    void startQvest(Player player, WMap[]maps) {
        if (!player.qvests.containsKey(tip)) {
            player.qvests.put(tip, 1);
            ObdjBinokl bin=new ObdjBinokl(693,1077,player.name);
            maps[5].mapobjects.put(bin.id,bin);
        }
    }
    @Override
    void endQvest(Player player) {
        if (player.qvests.containsKey(tip)&&player.qvests.get(tip)!=99) {
            if (player.inventar.isItem(23,false) != null) {
              //  player.qvests.remove(tip);
                player.qvests.put(tip,99);
                int id1=player.inventar.isItem(23,false).id;
                player.inventar.item.remove(id1);
                if(player.inventar.getMesto()){
                    Kurtka5 kurtka5 = new Kurtka5();
                player.inventar.addItem(kurtka5);}
                NettyServerHandler.sendMsgClient("18/"+tip+"/1\0"+"31/"+id1, player.idchanel);}
            else{NettyServerHandler.sendMsgClient("18/"+tip+"/2", player.idchanel);}
        }
    }
}
