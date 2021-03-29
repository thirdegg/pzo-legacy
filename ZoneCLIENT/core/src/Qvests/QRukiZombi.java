package Qvests;

import GameWorld.Game;

public class QRukiZombi extends Qvest {

    QRukiZombi() {
        tip = 6;
    }
    @Override
    void endQvest() {
        Game.hero.qvests.remove(tip);
        Game.hero.many+=350;
    }
}
