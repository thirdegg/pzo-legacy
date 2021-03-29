package MapObjects;

import InventItem.core.Item;
import MapObjects.Units.Player;
import Utils.Util;

public class Korob extends MObject{
    Item it;
    long timeremov;
    int tmp;
    public Korob(float x, float y,Item it) {
        super(x,y);
    id = MapObject.generatorId.incrementAndGet();
    tip = Util.KOROB;
    podtip=Util.PPKOROB;
    this.it=it;
    timeremov=System.currentTimeMillis()+240000;
    }
    @Override
    public void addItem(Player player){
        player.inventar.addItem(it);
    }
    @Override
    public void run(float delta){
        tmp++;
        if(tmp>50){
            if(System.currentTimeMillis()>timeremov)remov=true;
            tmp=0;
        }
    }
    @Override
    public String addedAndDop(Player pl, String str) {
        pl.mapobjects.put(id, this);
        str+="/"+(timeremov-System.currentTimeMillis());
        return str;
    }
}
