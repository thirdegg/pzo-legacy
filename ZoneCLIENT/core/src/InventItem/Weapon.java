package InventItem;


import GameInterface.Ginterface;
import Unit.Hero;
import Unit.Player;
import util.Util;

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
        use=true;
        remuse=false;
        fastuse=false;
        weapon=true;
    }
    public void fire(Player player) {
        if (dopintcolvo > 0) {
            dopintcolvo--;
        }
        if (dopintcolvo == 0) {
            perezarad = true;
            player.state = Util.PEREZARAD;
            player.stateTime=0;
        }
    }
    public void perezaradka(float delta, Player player) {
        if (player.colvopatronov == 0) {
            //Ginterface.setText("У вас нет патронов", 2000);
            player.ataka = false;
            perezarad = false;
            player.state = Util.WALKING;
            player.atakunit=null;
            return;
        }
        timep += 10 * delta;
        if (perezarad && timep > perezaradka) {
//сколько нужно патронов
            int colvo = oboima - dopintcolvo;
//если патронов нужно больше чем есть заряжаем что осталось
            if (colvo > player.colvopatronov) {
                dopintcolvo += player.colvopatronov;
                player.colvopatronov = 0;
            } else {
                player.colvopatronov -= colvo;
                dopintcolvo = oboima;
            }
//если патронов не осталость
//если шла атака до перезарядки
            if (player.ataka) {
                player.timevistrel =skorostrel;
                player.atakatime = 0;
                player.state = Util.ATAKA;
            } else {
                player.state = Util.WALKING;
            }
            perezarad = false;
            timep = 0;
        }
    }
    public void perezaradka(float delta, Hero hero) {
        Item patron=hero.getItem(14,true);
        if (patron==null||patron.dopintcolvo<1) {
            Ginterface.setText("У вас нет патронов", 2000);
            hero.ataka = false;
            perezarad = false;
            if(patron!=null)hero.odeto.removeValue(patron,false);
            hero.state = Util.WALKING;
            hero.atakunit=null;
            return;
        }
        timep += 10 * delta;
        if (perezarad && timep > perezaradka) {
//сколько нужно патронов
            int colvo = oboima - dopintcolvo;
//если патронов нужно больше чем есть заряжаем что осталось
            if (colvo >= patron.dopintcolvo) {
                dopintcolvo += patron.dopintcolvo;
                patron.dopintcolvo=0;
                hero.odeto.removeValue(patron,false);
            } else {
                patron.dopintcolvo -= colvo;
                dopintcolvo = oboima;
                if(patron.dopintcolvo<=0)hero.odeto.removeValue(patron,false);
            }
//если патронов не осталость
//если шла атака до перезарядки
            if (hero.ataka) {
                hero.timevistrel =skorostrel;
                hero.atakatime = 0;
                hero.state = Util.ATAKA;
            } else {
                hero.state = Util.WALKING;
            }
            perezarad = false;
            timep = 0;
        }
    }
}
