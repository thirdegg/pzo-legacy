package Gm;

import MapObjects.MapObject;
import MapObjects.Units.Player;
import Maps.WMap;
import Server.NettyServerHandler;
import Utils.Util;
import java.util.HashMap;

/**
 * Created by 777 on 21.08.2017.
 */
public class Chat {
    HashMap<Long,Player>plglobalchat=new HashMap<>();
    String lostglobalstring[]=new String[]{"22/server/тест","22/server/тест","22/server/тест","22/server/тест","22/server/тест"};
    void msgPlayers(Player pl, String str[],WMap map){
        int tip=Integer.parseInt(str[2]);
        switch(tip){
            case 0:
               pl.sendMsg("5/" + pl.name + "/" + str[3],true);
                break;
            case 1:
                map.plmapchat.put(pl.id,pl);
                StringBuilder sb=new StringBuilder();
                for(String ss:map.lostmapstring){
                    sb.append(ss+"\0");
                }
                NettyServerHandler.sendMsgClient(sb.toString(),pl.idchanel);
                break;
            case 2:
                remPlayer(pl,map);
                break;
            case 3:
                //отправляем сообщение или в глобал чат или в мап чат
                boolean globalormap=true;
                if(map.plmapchat.containsKey(pl.id))globalormap=false;
                for (Player pp:(globalormap?plglobalchat.values():map.plmapchat.values())){
                    NettyServerHandler.sendMsgClient("22/"+pl.name + "/"+str[3],pp.idchanel);
                }
               for(int i=0;i<map.lostmapstring.length;i++){
               if(globalormap){
               if(i<4)lostglobalstring[i]=lostglobalstring[i+1];
               else lostglobalstring[i]="22/"+pl.name + "/"+str[3];
               }
               else{
                   if(i<4)map.lostmapstring[i]=map.lostmapstring[i+1];
                   else map.lostmapstring[i]="22/"+pl.name + "/"+str[3];
               }
               }
                break;
            case 4:
                plglobalchat.put(pl.id,pl);
                StringBuilder sb2=new StringBuilder();
                for(String ss:lostglobalstring){
                    sb2.append(ss+"\0");
                }
                NettyServerHandler.sendMsgClient(sb2.toString(),pl.idchanel);
                break;
        }
    }
    void remPlayer(Player pl,WMap map){
        plglobalchat.remove(pl.id);
        map.plmapchat.remove(pl.id);
    }
}
