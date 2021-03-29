package MapObjects.Artefakts;

import InventItem.core.Item;
import MapObjects.MObject;
import MapObjects.MapObject;
import MapObjects.Units.Player;
import Server.NettyServerHandler;
import Utils.Overlap;
import Utils.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 777 on 22.03.2017.
 */
public class Veter extends MObject {
    MapObject anomaly;
    float timevisibl;
    public Veter(float x, float y, MapObject anomaly) {
        super(x,y);
        id = MapObject.generatorId.incrementAndGet();
        tip = Util.ARTVETER;
        podtip=Util.PPARTEFAKT;
        clientvisible=false;
        this.anomaly=anomaly;
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
        Item it=Util.createItem(24);
        if(it!=null)player.inventar.addItem(it);
        if(anomaly!=null)anomaly.signal(8,id);}
    }
    @Override
    public String addedAndDop(Player pl,String str){
        pl.mapobjects.put(id,this);
        if(clientvisible)str+="/"+timevisibl;
        return str;
    }
}