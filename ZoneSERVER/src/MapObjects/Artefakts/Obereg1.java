package MapObjects.Artefakts;

import InventItem.core.Item;
import MapObjects.MapObject;
import MapObjects.Units.Player;
import Utils.Util;

/**
 * Created by 777 on 01.09.2017.
 */
public class Obereg1 extends Obereg {
    public Obereg1(float x, float y, MapObject anomaly) {
        super(x,y,anomaly);
        tip = Util.ARTOBEREG1;}
    @Override
    public void addItem(Player player){
        if(clientvisible){
            Item it= Util.createItem(120);
            if(it!=null)player.inventar.addItem(it);
            if(anomaly!=null)anomaly.signal(8,id);
        }
    }
}
