package Qvests;

import GameWorld.Game;

/**
 * Created by 777 on 08.06.2017.
 */
public class KillBossKaban extends Qvest {

    KillBossKaban() {
        tip = 8;
    }
    @Override
    void endQvest() {
        Game.hero.qvests.remove(tip);
    }
}