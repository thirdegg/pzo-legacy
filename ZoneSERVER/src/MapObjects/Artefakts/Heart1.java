package MapObjects.Artefakts;

import Gm.SpatialHashGrid;
import InventItem.core.Item;
import MapObjects.MapObject;
import MapObjects.Units.Player;
import Utils.Util;

/**
 * Created by 777 on 10.06.2017.
 */
public class Heart1 extends Heart {
    public Heart1(float x, float y, SpatialHashGrid grid, MapObject anomaly) {
        super(x,y,grid,anomaly);
        tip = Util.ARTHEART1;}
    @Override
    public void addItem(Player player){
        if(clientvisible){
            Item it= Util.createItem(49);
            if(it!=null)player.inventar.addItem(it);
            if(anomaly!=null)anomaly.signal(8,id);
        }
    }
}
