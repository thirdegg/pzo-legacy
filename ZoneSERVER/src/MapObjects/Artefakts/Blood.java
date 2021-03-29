package MapObjects.Artefakts;

import Gm.SpatialHashGrid;
import InventItem.core.Item;
import MapObjects.MObject;
import MapObjects.MapObject;
import MapObjects.Units.Player;
import Server.NettyServerHandler;
import Utils.Overlap;
import Utils.Rectang;
import Utils.Util;
import Utils.rand;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 777 on 28.03.2017.
 */
public class Blood extends MObject {
    float timevisibl;
    MapObject anomaly;
    public Blood(float x, float y, MapObject anomaly) {
        super(x,y);
        this.anomaly=anomaly;
        id = MapObject.generatorId.incrementAndGet();
        tip = Util.ARTBLOOD;
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
        Item it=Util.createItem(28);
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