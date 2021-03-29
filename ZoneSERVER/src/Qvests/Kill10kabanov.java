package Qvests;

import InventItem.Aptechka;
import InventItem.Kurtka3;
import InventItem.Pistolet3;
import Server.NettyServerHandler;
import MapObjects.Units.Player;

public class Kill10kabanov extends Qvest{

Kill10kabanov(){tip=1;}
    @Override
    void startQvest(Player player) {
        if (!player.qvests.containsKey(tip)) {
            player.qvests.put(tip, 1);
            System.out.print("квест добавлен");
        }
    }
@Override
    void endQvest(Player player) {
    System.out.print("запрос на выполнение квеста");
        if (player.qvests.containsKey(tip)) {
            int progress = player.qvests.get(tip);
            if (progress >= 10) {
                player.qvests.remove(tip);
                if(player.inventar.getMesto()){
                    Aptechka apt = new Aptechka();
                    player.inventar.addItem(apt);}
                if(player.inventar.getMesto()){
                    Aptechka apt2 = new Aptechka();
                    player.inventar.addItem(apt2);}
                if(player.inventar.getMesto()){
                    Aptechka apt3 = new Aptechka();
                    player.inventar.addItem(apt3);}
                NettyServerHandler.sendMsgClient("18/"+tip+"/1", player.idchanel);}
                else{NettyServerHandler.sendMsgClient("18/"+tip+"/2", player.idchanel);}
        }
    }
}
