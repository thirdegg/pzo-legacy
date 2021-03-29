package Klans;

import MapObjects.ControlMap;
import MapObjects.Units.Player;
import Maps.WMap;
import Server.NettyServer;
import Server.NettyServerHandler;

import java.util.HashMap;
import java.util.Map;

public class SystemKlans {
   public Map<String,Klan>klans=new HashMap<>();
    ControlMap cm[]=new ControlMap[14];
    StringBuilder controlMap=new StringBuilder("24/12/");
    public HashMap<String,String>zaprosnametoklan=new HashMap<>();
    public SystemKlans(WMap maps[]){
        cm[0]=new ControlMap(859,965,1);
        maps[1].mapobjects.put(cm[0].id,cm[0]);
        cm[1]=new ControlMap(458,1097,3);
        maps[3].mapobjects.put(cm[1].id,cm[1]);
        cm[2]=new ControlMap(1176,1022,4);
        maps[4].mapobjects.put(cm[2].id,cm[2]);
        cm[3]=new ControlMap(756,249,7);
        maps[7].mapobjects.put(cm[3].id,cm[3]);
        cm[4]=new ControlMap(599,341,9);
        maps[9].mapobjects.put(cm[4].id,cm[4]);
        cm[5]=new ControlMap(436,553,10);
        maps[10].mapobjects.put(cm[5].id,cm[5]);
        cm[6]=new ControlMap(545,953,13);
        maps[13].mapobjects.put(cm[6].id,cm[6]);
        cm[7]=new ControlMap(309,331,14);
        maps[14].mapobjects.put(cm[7].id,cm[7]);
        cm[8]=new ControlMap(558,803,6);
        maps[6].mapobjects.put(cm[8].id,cm[8]);
        cm[9]=new ControlMap(860,830,11);
        maps[11].mapobjects.put(cm[9].id,cm[9]);
        cm[10]=new ControlMap(431,551,15);
        maps[15].mapobjects.put(cm[10].id,cm[10]);
        cm[11]=new ControlMap(586,1036,16);
        maps[16].mapobjects.put(cm[11].id,cm[11]);
        cm[12]=new ControlMap(769,870,17);
        maps[17].mapobjects.put(cm[12].id,cm[12]);
        cm[13]=new ControlMap(607,623,18);
        maps[18].mapobjects.put(cm[13].id,cm[13]);

    }
   public void startTimeZahvat(){
        for (ControlMap com:cm){
        com.signal(3,null);
        }
    }
     public void endTimeZahvat(){
       controlMap.setLength(0);
       controlMap.append("24/12/");
        for (ControlMap com:cm){
            com.signal(4,null);
            Klan klan=klans.get(com.kontrolklanname);
            if(klan!=null){
            for (Player pl:klan.onlineplayers){
             pl.inventar.many+=100;
            }
            controlMap.append(com.idmap+":"+klan.nameklan+":");
            }
        }
         for (ControlMap com:cm){
             Klan klan=klans.get(com.kontrolklanname);
             if(klan!=null){
                 for (Player pl:klan.onlineplayers){
                     NettyServerHandler.sendMsgClient("19/m/"+pl.inventar.many,pl.idchanel);
                 }
        }}

    }
    public boolean addKlan(Player pl, String nameg){
        if(!klans.containsKey(nameg)){
            Klan k=new Klan(nameg,pl.name);
            klans.put(k.nameklan,k);
            pl.nameclan=k.nameklan;
            pl.inventar.many-=8000;
            k.onlineplayers.add(pl);
            return true;}
        return false;
    }
    public boolean remKlan(String nameg){
        Klan klan=klans.get(nameg);
        if(klan!=null){
                klan.rospusk();
            klans.remove(nameg);return true;}
        return false;
    }
    public void addOnlinePlayer(Player pl){
        //проверяем не расспущен ли его клан и не занесен ли игрок в список удаления
        if(pl.nameclan!=null&&!pl.nameclan.equals("null")){
            Klan klan=klans.get(pl.nameclan);
            if(klan!=null&&!klan.isRemPlayer(pl.name)){klan.addOnlinePlayer(pl);}
            else{pl.nameclan=null;}
        }
    }
    public void remOnlinePlayer(Player pl){
        if(pl.nameclan!=null&&!pl.nameclan.equals("null")){
            Klan klan=klans.get(pl.nameclan);
            if(klan!=null)klan.remOnlinePlayer(pl);
        }

    }
    public void getNameKlans(Player pl){
        StringBuilder sbn=new StringBuilder("24/6/");
        for(String str:klans.keySet()){
            sbn.append(str+":");
        }
        NettyServerHandler.sendMsgClient(sbn.toString(),pl.idchanel);
    }
    public void msgFromPlayer(Player pl,String str[]){
        int tip=Integer.parseInt(str[2]);
        switch(tip){
            case 1:
                if(str.length>3){
                if(pl.nameclan!=null){NettyServerHandler.sendMsgClient("24/3",pl.idchanel);
                return;}
                if(str[3].length()>10){NettyServerHandler.sendMsgClient("24/3",pl.idchanel);
                return;}
                if(pl.inventar.many<8000){NettyServerHandler.sendMsgClient("24/8",pl.idchanel);
                    return;}

                    if(str.length>4){NettyServerHandler.sendMsgClient("24/13",pl.idchanel); return;}
                for (int i = 0; i < str[3].length(); i++) {
                    if ( str[3].charAt(i) == '/'|| str[3].charAt(i) == ':') {
                        NettyServerHandler.sendMsgClient("24/13",pl.idchanel);
                        return;
                    }}
                if(addKlan(pl,str[3])){ NettyServerHandler.sendMsgClient("24/1",pl.idchanel);
                pl.sendMsg("2/13/"+pl.id+"/1/"+pl.nameclan,true);}
                else NettyServerHandler.sendMsgClient("24/2",pl.idchanel);}
                break;
            case 3:
                getNameKlans(pl);
                break;
            case 4:
                Klan klan=klans.get(str[3]);
                if(klan!=null)
                NettyServerHandler.sendMsgClient(klan.getInfoKlan(),pl.idchanel);
                break;
            case 5:

                break;
            case 6:
                Klan klan3=klans.get(str[3]);
                if(klan3!=null){
                    if(pl.name.equals(klan3.getNameOsnovatel())){remKlan(str[3]);
                    return;}
                    klan3.remPlayer(pl);}
                break;
            case 7:
    if(pl.nameclan!=null){
        Klan klan1=klans.get(pl.nameclan);
        if(klan1!=null){
            if(klan1.adminRemPlayer(pl.name,str[3]))NettyServerHandler.sendMsgClient("24/11",pl.idchanel);
        }
    }
                break;
    case 8:
        NettyServerHandler.sendMsgClient(controlMap.toString(),pl.idchanel);
         break;
         //основатель отправил ответ на запрос о вступлении
            case 9:
                if(pl.nameclan==str[2]){

                }
                break;
        }
    }
}

