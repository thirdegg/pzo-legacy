package InventItem.core;

import InventItem.core.Item;
import MapObjects.Units.Player;
import Maps.WMap;

public class Weapon extends Item {
    public int uron, oboima, dalnostrel, skorostrel, perezaradka;
    public boolean perezarad;
    public float timep;

    public Weapon(int uron, int oboima, int dalnostrel, int skorostrel, int perezaradka) {
        this.uron = uron;
        this.oboima = oboima;
        this.dalnostrel = dalnostrel;
        this.skorostrel = skorostrel;
        dopintcolvo = oboima;
        this.perezaradka = perezaradka;
        useslot = 2;
        use=true;
        remuse=false;
    }
    @Override
    public void use(WMap wm, Player player) {

    }
    public void fire(Player player) {
        if (dopintcolvo > 0) {
             dopintcolvo--;
        }
        if (dopintcolvo == 0) player.setState(player.PEREZARAD, null);
    }

    public void perezaradka(float delta, Player player) {
        Item patrony=player.inventar.isItem(14,true);
        if (patrony==null||patrony.dopintcolvo<1) {
            player.ataka = false;
            perezarad = false;
            if(patrony!=null)player.inventar.odeto.remove(patrony.id);
            player.setState(player.DVIG, null);
            return;
        }
        timep += 10 * delta;
        if (perezarad && timep > perezaradka) {
//сколько нужно патронов
            int colvo = oboima - dopintcolvo;
//если патронов нужно больше чем есть заряжаем что осталось
            if (colvo >= patrony.dopintcolvo) {
                dopintcolvo += patrony.dopintcolvo;
                patrony.dopintcolvo = 0;
                player.inventar.odeto.remove(patrony.id);
            } else {
                patrony.dopintcolvo -= colvo;
                if(patrony.dopintcolvo<1)player.inventar.odeto.remove(patrony.id);
                dopintcolvo = oboima;
            }
//если шла атака до перезарядки
            if (player.ataka) {
                player.setState(player.ATAKA,null);
            } else {
                player.setState(player.DVIG,null);
            }
            perezarad = false;
            timep = 0;
        }
    }
}
