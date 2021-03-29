package Qvests;

import GameWorld.Game;

public class DetekrorArtefaktovv extends Qvest {

    DetekrorArtefaktovv(){tip=5;}
    @Override
    void endQvest() {
        Game.hero.qvests.remove(tip);
        Game.hero.many-=1500;
    }
}
