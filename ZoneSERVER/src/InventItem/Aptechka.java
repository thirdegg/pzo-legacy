package InventItem;

import InventItem.core.Item;
import MapObjects.Units.Player;
import Maps.WMap;

public class Aptechka extends Item {

    public Aptechka() {
        id = generatorId.incrementAndGet();
        tipitem = 11;
        use=true;
        cena=150;
    }
//переделать
@Override
    public void use(WMap map,Player player) {
        player.pmLife.life += 30;
        if (player.pmLife.life > player.pmLife.maxlife) player.pmLife.life = player.pmLife.maxlife;
        player.sendMsg("2/9/" + player.id + "/1/" + (int) player.pmLife.life,true);
    }
}
