package MapObjects;

import MapObjects.Anomaly.DvigElectra1;
import InventItem.Akkom;
import InventItem.AkkomZ;
import MapObjects.Units.Player;
import Utils.Overlap;
import Utils.Util;

import java.util.HashMap;

public class ObjectAkkom extends MObject {
    public boolean zarad = false;
    HashMap<Long, MapObject> mapobjects;
    String pla;
    public ObjectAkkom(float x, float y, HashMap<Long, MapObject> mapobjects,String pl) {
        super(x,y);
        id = generatorId.incrementAndGet();
        tip = Util.AKKOMULATOR;
        this.mapobjects=mapobjects;
        pla=pl;
    }
    @Override
    public String addedAndDop(Player pl, String str) {
        pl.mapobjects.put(id, this);
        str+="/"+pla;
        return str;
    }
    @Override
    public void run(float delta) {
        if(!zarad){
        for (MapObject mo : mapobjects.values()) {
            if (mo.tip == Util.DVIGELECTRA && Overlap.pointPoint(mo.centx, mo.centy, centx, centy, 25)) {
                DvigElectra1 de = (DvigElectra1) mo;
                de.zarad = false;
                de.sendMsgPlayers("2/2/" + de.id);
                zarad = true;
                break;
            }
        }
        }
    }
    @Override
    public void addItem(Player player){
        if(pla.equals(player.name)){
        if (!zarad) {
            Akkom akk = new Akkom();
            player.inventar.addItem(akk);
        } else {
            AkkomZ akkz = new AkkomZ();
            player.inventar.addItem(akkz);
        }
    }}
}
