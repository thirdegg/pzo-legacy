package MapObjects.Artefakts;

import InventItem.core.Item;
import MapObjects.MObject;
import MapObjects.MapObject;
import MapObjects.Units.Player;
import Utils.Overlap;
import Utils.Util;

/**
 * Created by 777 on 22.03.2017.
 */
public class Iskra extends MObject {
    MapObject anomaly;
    float timevisibl;
    public Iskra(float x, float y, MapObject anomaly) {
        super(x,y);
        id = MapObject.generatorId.incrementAndGet();
        tip = Util.ARTISKRA;
        podtip=Util.PPARTEFAKT;
        clientvisible=false;
        this.anomaly=anomaly;
        //12 mgla 15 elec1 24 veter
    }
    @Override
    public void run(float delta){
        if(clientvisible){
            timevisibl += delta * 10;
            if (timevisibl > 45) {
                timevisibl = 0;
                clientvisible=false;
            }
        }
    }
    @Override
    public void addItem(Player player){
        if(clientvisible){
        Item it=Util.createItem(15);
        if(it!=null)player.inventar.addItem(it);
        anomaly.signal(8,null);}
    }
    @Override
    public String addedAndDop(Player pl,String str){
        pl.mapobjects.put(id,this);
        if(clientvisible)str+="/"+timevisibl;
        return str;
    }
}