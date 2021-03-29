package Gm.skils;

import MapObjects.Units.Player;

/**
 * Created by 777 on 08.08.2017.
 */
public class Stremitelnost2 extends Skil {
    Stremitelnost2(){
        super(400,3);
    }
    @Override
    public void activation(Player pl){
        pl.speed+=10;
        pl.sendMsg("2/9/" + pl.id + "/4/" + (int) pl.speed,true);
    }
}