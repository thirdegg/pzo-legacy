package Qvests;

import GameWorld.Game;

public class Kill10Zombi extends Qvest{

    Kill10Zombi(){tip=3;}
    @Override
    void endQvest() {
        Game.hero.qvests.remove(tip);
    }
}
