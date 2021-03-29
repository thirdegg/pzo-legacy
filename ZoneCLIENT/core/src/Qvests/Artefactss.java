package Qvests;

import GameWorld.Game;

public class Artefactss extends Qvest {
    Artefactss() {
        tip = 4;
    }
    @Override
    void endQvest() {
        Game.hero.qvests.remove(tip);
        Game.hero.many+=600;
    }
}
