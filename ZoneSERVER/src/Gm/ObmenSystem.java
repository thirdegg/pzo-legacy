package Gm;

import InventItem.core.Item;
import MapObjects.Units.Player;
import Server.NettyServer;
import Server.NettyServerHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ObmenSystem {
    private long idobmen;
    private HashMap<Long,Obmen> playersobmen=new HashMap<>();
    public void msgForKlient(String str[],Player pl){
        int tip=Integer.parseInt(str[2]);
        switch (tip){
            // запрос на обмен
            case 1:
                Player pl2=(Player) pl.mapobjects.get(Long.parseLong(str[3]));
                if(pl2!=null){
                Obmen obmen= new Obmen(pl,pl2);
                playersobmen.put(obmen.ido,obmen);
                NettyServerHandler.sendMsgClient("25/1/"+obmen.ido+"/"+pl.name,pl2.idchanel);
                }
                break;
                // подтверждение обмена
            case 2:
                long tmpid=Long.parseLong(str[3]);
                Obmen obmen=playersobmen.get(tmpid);
                if(obmen!=null){
                    if(obmen.player1==null){if(obmen.player2!=null)NettyServerHandler.sendMsgClient("25/5",obmen.player2.idchanel);
                    playersobmen.remove(obmen.ido);return;}
                    if(obmen.player2==null){if(obmen.player1!=null)NettyServerHandler.sendMsgClient("25/5",obmen.player1.idchanel);
                        playersobmen.remove(obmen.ido);return;}
                obmen.podtverzhdenie=true;
                obmen.player1.idobmen=obmen.ido;
                obmen.player2.idobmen=obmen.ido;
                NettyServerHandler.sendMsgClient("25/2/",obmen.player1.idchanel);
                NettyServerHandler.sendMsgClient("25/2/",obmen.player2.idchanel);
                }
                break;
                // добавление предметоа и денег
            case 3:
                Obmen obmen3=playersobmen.get(pl.idobmen);
                if(obmen3!=null&&!obmen3.yespl1&&!obmen3.yespl2){
                    if(obmen3.player1==null){if(obmen3.player2!=null)NettyServerHandler.sendMsgClient("25/5",obmen3.player2.idchanel);
                        playersobmen.remove(obmen3.ido);return;}
                    if(obmen3.player2==null){if(obmen3.player1!=null)NettyServerHandler.sendMsgClient("25/5",obmen3.player1.idchanel);
                        playersobmen.remove(obmen3.ido);return;}
                boolean oneortwopl=false;
                StringBuilder tmpstr=new StringBuilder("25/3");
                if(pl.id==obmen3.player1.id)oneortwopl=true;
                    //деньги
                    int many=Integer.parseInt(str[3]);
                    tmpstr.append("/"+many);
                    if(oneortwopl)obmen3.manypl1=many;
                    else obmen3.manypl2=many;
                //итем
for(int i=4;i<str.length;i++){
    int idit= Integer.parseInt(str[i]);
    Item it=pl.inventar.item.get(idit);
    if(it!=null){
        tmpstr.append("/"+it.tipitem);
        if(oneortwopl)obmen3.itemspl1.add(it);
        else obmen3.itemspl2.add(it);
    }
}
if(oneortwopl)NettyServerHandler.sendMsgClient(tmpstr.toString(),obmen3.player2.idchanel);
else NettyServerHandler.sendMsgClient(tmpstr.toString(),obmen3.player1.idchanel);
                }
                    break;
                // я согласен
            case 4:
                Obmen obmen1=playersobmen.get(pl.idobmen);
                if(obmen1!=null){
                    if(obmen1.player1==null){if(obmen1.player2!=null)NettyServerHandler.sendMsgClient("25/6/",obmen1.player2.idchanel);
                        playersobmen.remove(obmen1.ido);return;}
                    if(obmen1.player2==null){if(obmen1.player1!=null)NettyServerHandler.sendMsgClient("25/6/",obmen1.player1.idchanel);
                        playersobmen.remove(obmen1.ido);return;}
                if(pl.id==obmen1.player1.id){if(!obmen1.yespl1&&!obmen1.yespl2)NettyServerHandler.sendMsgClient("25/7/",obmen1.player2.idchanel);obmen1.yespl1=true;}
                if(pl.id==obmen1.player2.id){if(!obmen1.yespl1&&!obmen1.yespl2)NettyServerHandler.sendMsgClient("25/7/",obmen1.player1.idchanel);obmen1.yespl2=true;}
                if(obmen1.yespl1&&obmen1.yespl2)endObmen(obmen1);
                }
                break;
                // отказ от обмена
            case 5:
                Obmen obmen4=playersobmen.get(pl.idobmen);
                if(obmen4!=null){
                    if(obmen4.player1==null){if(obmen4.player2!=null)NettyServerHandler.sendMsgClient("25/5",obmen4.player2.idchanel);
                        playersobmen.remove(obmen4.ido);return;}
                    if(obmen4.player2==null){if(obmen4.player1!=null)NettyServerHandler.sendMsgClient("25/5",obmen4.player1.idchanel);
                        playersobmen.remove(obmen4.ido);return;}
                    if(pl.id==obmen4.player1.id)NettyServerHandler.sendMsgClient("25/5",obmen4.player2.idchanel);
                    else NettyServerHandler.sendMsgClient("25/5",obmen4.player1.idchanel);
                    playersobmen.remove(obmen4.ido);
                }
                break;
                //отказ от предложения обмена
            case 6:
                long tmpid2=Long.parseLong(str[3]);
                Obmen obmen5=playersobmen.get(tmpid2);
                if(obmen5!=null){
                if(obmen5.player1!=null)NettyServerHandler.sendMsgClient("25/6/",obmen5.player1.idchanel);
                playersobmen.remove(obmen5.ido);}
                break;
        }

    }
    private void endObmen(Obmen obmen){
        StringBuilder tmpstr1=new StringBuilder("25/4");
        StringBuilder tmpstr2=new StringBuilder("25/4");
        //деньги первого игрока
        if(obmen.manypl1>0){
            if(obmen.player1.inventar.many>=obmen.manypl1){
                obmen.player1.inventar.many-=obmen.manypl1;
                obmen.player2.inventar.many+=obmen.manypl1;
            }else{
                obmen.player2.inventar.many+=obmen.player1.inventar.many;
                obmen.player1.inventar.many=0;
            }

        }
        //деньги второго игрока
        if(obmen.manypl2>0){
            if(obmen.player2.inventar.many>=obmen.manypl2){
            obmen.player2.inventar.many-=obmen.manypl2;
            obmen.player1.inventar.many+=obmen.manypl2;}
            else{
                obmen.player1.inventar.many+=obmen.player2.inventar.many;
                obmen.player2.inventar.many=0;
            }
        }
        tmpstr1.append("/"+obmen.player1.inventar.many);
        tmpstr2.append("/"+obmen.player2.inventar.many);
        //предметы первого игрока
            if(!obmen.itemspl1.isEmpty()){
            for (Item it:obmen.itemspl1){
                if(!obmen.player2.inventar.getMesto())break;
            obmen.player2.inventar.item.put(it.id,it);
            obmen.player1.inventar.item.remove(it.id);
            tmpstr2.append("/"+it.tipitem+"/"+it.id+"/"+it.dopintcolvo);
            }
        }
        //предметы второго игрока
        if(!obmen.itemspl2.isEmpty()){
            for (Item it:obmen.itemspl2){
                if(!obmen.player1.inventar.getMesto())break;
                obmen.player1.inventar.item.put(it.id,it);
                obmen.player2.inventar.item.remove(it.id);
                tmpstr1.append("/"+it.tipitem+"/"+it.id+"/"+it.dopintcolvo);
            }
        }
        playersobmen.remove(obmen.ido);
        NettyServerHandler.sendMsgClient(tmpstr1.toString(),obmen.player1.idchanel);
        NettyServerHandler.sendMsgClient(tmpstr2.toString(),obmen.player2.idchanel);
    }
   private class Obmen {
     Player player1,player2;
     int manypl1,manypl2;
     List<Item>itemspl1,itemspl2;
     boolean yespl1,yespl2;
     boolean podtverzhdenie;
     long ido;
     Obmen(Player player1,Player player2){
     this.player1=player1;
     this.player2=player2;
     idobmen++;
     ido=idobmen;
     itemspl2=new ArrayList<>();
         itemspl1=new ArrayList<>();
     }
   }
}
