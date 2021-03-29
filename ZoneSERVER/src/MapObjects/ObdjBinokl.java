package MapObjects;

import InventItem.ItBinokl;
import MapObjects.Units.Player;
import Server.NettyServerHandler;
import Utils.Util;

public class ObdjBinokl extends MObject{
   public String pla;
   public ObdjBinokl(float x, float y, String pl){
       super(x,y);
    id = MapObject.generatorId.incrementAndGet();
    tip = Util.BINOKL;
       pla=pl;
       clientvisible=false;
    }
    @Override
    public String addedAndDop(Player pl, String str) {
        pl.mapobjects.put(id, this);
        if(pl.name.equals(pla))NettyServerHandler.sendMsgClient("1/11/"+id+"/"+(int)x+"/"+(int)y,pl.idchanel);
        return str;
    }
    @Override
    public void addItem(Player player){
        if(pla.equals(player.name)){
                ItBinokl itb = new ItBinokl();
            player.inventar.addItem(itb);
            player.qvests.put(2,2);
            }
        }
}
