package Qvests;


import GameWorld.Game;

public class Kill10kabanov extends Qvest {

Kill10kabanov(){tip=1;}
    @Override
    void endQvest() {
        Game.hero.qvests.remove(tip);
    }
}
