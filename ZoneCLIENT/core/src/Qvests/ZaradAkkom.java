package Qvests;

import GameWorld.Game;

public class ZaradAkkom extends Qvest{
    ZaradAkkom(){tip=0;}
    @Override
    void endQvest() {
        Game.hero.qvests.remove(tip);
    }
}
