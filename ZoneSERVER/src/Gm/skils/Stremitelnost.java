package Gm.skils;

import MapObjects.Units.Player;

/**
 * Created by 777 on 08.08.2017.
 */
public class Stremitelnost extends Skil {
    Stremitelnost(){
        super(200,2);
    }
    @Override
    public void activation(Player pl){
    pl.speed+=5;
    pl.sendMsg("2/9/" + pl.id + "/4/" + (int) pl.speed,true);
    }
}
