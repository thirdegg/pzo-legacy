package MapObjects.Artefakts;

import InventItem.core.Item;
import MapObjects.MObject;
import MapObjects.MapObject;
import MapObjects.Units.Player;
import Utils.Util;

/**
 * Created by 777 on 01.09.2017.
 */
public class Obereg extends MObject {
    float timevisibl;
    MapObject anomaly;
    public Obereg(float x, float y, MapObject anomaly) {
        super(x,y);
        this.anomaly=anomaly;
        id = MapObject.generatorId.incrementAndGet();
        tip = Util.ARTOBERG;
        podtip=Util.PPARTEFAKT;
        clientvisible=false;
    }
    @Override
    public void run(float delta){
        if(clientvisible&&anomaly!=null){
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
            Item it=Util.createItem(46);
            if(it!=null)player.inventar.addItem(it);
            if(anomaly!=null)anomaly.signal(8,id);
        }
    }
    @Override
    public String addedAndDop(Player pl,String str){
        pl.mapobjects.put(id,this);
        if(clientvisible)str+="/"+timevisibl;
        return str;
    }
}