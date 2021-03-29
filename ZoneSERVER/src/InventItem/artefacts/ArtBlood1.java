package InventItem.artefacts;

import InventItem.core.Item;
import MapObjects.Units.Player;

/**
 * Created by 777 on 10.06.2017.
 */
public class ArtBlood1 extends Item {
    public ArtBlood1() {
        id = generatorId.incrementAndGet();
        tipitem = 47;
        cena=400;
    }

    @Override
    public void odet(Player player, boolean send) {
        player.pmLife.timePlusLife(id,3,100,0,0,true);
        if(send) player.sendMsg("3/4/"+player.id+"/"+id+"/3/100/0/0/1",true);
    }

    @Override
    public void snyat(Player player, boolean send) {
        player.pmLife.stopPlusMinusLife(id);
        if(send) player.sendMsg("3/2/"+player.id+"/"+id,true);
    }
}
