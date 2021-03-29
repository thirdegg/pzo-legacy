package MapObjects.Artefakts;

import InventItem.core.Item;
import MapObjects.MObject;
import MapObjects.MapObject;
import MapObjects.Units.Player;
import Utils.Util;

    public class Mgla extends MObject {
public Mgla(float x, float y) {
        super(x,y);
        id = MapObject.generatorId.incrementAndGet();
        tip = Util.ARTMGLA;
        podtip=Util.PPARTEFAKT;
        //12 mgla 15 elec1 24 veter
        }
@Override
public void addItem(Player player){
        Item it=Util.createItem(12);
        if(it!=null)player.inventar.addItem(it);
        }
        }
