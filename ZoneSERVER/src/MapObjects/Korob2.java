package MapObjects;

import InventItem.core.Item;
import MapObjects.Units.Player;
import Server.NettyServerHandler;
import Utils.Util;

import java.util.HashMap;

/**
 * Created by 777 on 05.05.2017.
 */
public class Korob2 extends MObject{
    HashMap<Integer,Item> tiptoit=new HashMap<>();
    public Korob2(float x, float y) {
        super(x,y);
        id = MapObject.generatorId.incrementAndGet();
        tip = Util.KOROB2;
        podtip=Util.PPKOROB2;
    }
    @Override
    public void addItem(Player player){
        String item=null;
        for(Integer integ:tiptoit.keySet()){
        item+=integ+":";
        }
        NettyServerHandler.sendMsgClient("2/3"+item,player.idchanel);
    }
    public void addItem(Item it){
        tiptoit.put(it.tipitem,it);
    }
    public void remItem(Player player,int tipitem){
        player.inventar.addItem(tiptoit.remove(tipitem));
    }
}
