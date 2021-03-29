package Qvests;

import GameWorld.Game;
public class QKlikiKabana extends Qvest {

    QKlikiKabana() {
        tip = 7;
    }
    @Override
    void endQvest() {
        Game.hero.qvests.remove(tip);
        Game.hero.many+=250;
    }
}


