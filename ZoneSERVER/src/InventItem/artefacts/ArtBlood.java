package InventItem.artefacts;

import InventItem.core.Item;
import MapObjects.Units.Player;

/**
 * Created by 777 on 28.03.2017.
 */
public class ArtBlood extends Item {
    public ArtBlood() {
        id = generatorId.incrementAndGet();
        tipitem = 28;
        cena=800;
    }

    @Override
    public void odet(Player player, boolean send) {
        player.pmLife.timePlusLife(id,5,100,0,0,true);
        if(send) player.sendMsg("3/4/"+player.id+"/"+id+"/5/100/0/0/1",true);
    }

    @Override
    public void snyat(Player player, boolean send) {
       player.pmLife.stopPlusMinusLife(id);
        if(send) player.sendMsg("3/2/"+player.id+"/"+id,true);
    }
}