package Qvests;

import InventItem.core.Item;
import InventItem.device.DetektorAnomalys;
import MapObjects.Units.Player;
import Server.NettyServerHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 777 on 05.05.2017.
 */
public class DetekrorArtefaktovv extends Qvest{

    DetekrorArtefaktovv(){tip=5;}
    @Override
    void startQvest(Player player) {
        if (!player.qvests.containsKey(tip)) {
            player.qvests.put(tip, 1);
        }
    }
    @Override
    void endQvest(Player player) {
        if (player.qvests.containsKey(tip)) {
            List<Integer> ids=new ArrayList<>();
            //проверка колличества деталей
            int k35=0,k36=0,k37=0,k38=0,k39=0,k40=0,k41=0;
            for(Item it:player.inventar.item.values()){
               switch(it.tipitem){
                   case 35:k35++;if(k35<5)ids.add(it.id);break;
                   case 36:k36++;if(k36<2)ids.add(it.id);break;
                   case 37:k37++;if(k37<4)ids.add(it.id);break;
                   case 38:k38++;if(k38<3)ids.add(it.id);break;
                   case 39:k39++;if(k39<2)ids.add(it.id);break;
                   case 40:k40++;if(k40<2)ids.add(it.id);break;
                   case 41:k41++;if(k41<4)ids.add(it.id);break;
               }
            }
            if (k38>=2&&k39>=1&&k35>=4&&k36>=1&&k40>=1&&k41>=3&&k37>=3&&player.inventar.many>=1500) {
                player.qvests.remove(tip);
                StringBuilder sbit=new StringBuilder("31/");
                for(Integer iids:ids){
                    player.inventar.item.remove(iids);
                    sbit.append(iids+":");
                }
                player.inventar.many-=1500;
                    DetektorAnomalys detart=new DetektorAnomalys();
                    player.inventar.addItem(detart);
                NettyServerHandler.sendMsgClient("18/"+tip+"/1\0"+sbit.toString(), player.idchanel);
                ids.clear();}
            else{NettyServerHandler.sendMsgClient("18/"+tip+"/2", player.idchanel);ids.clear();}
        }
    }
}
