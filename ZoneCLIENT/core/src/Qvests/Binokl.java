package Qvests;

import GameWorld.Game;

public class Binokl extends Qvest {
    Binokl(){tip=2;}
    @Override
    void endQvest() {
        Game.hero.qvests.remove(tip);
    }
}
