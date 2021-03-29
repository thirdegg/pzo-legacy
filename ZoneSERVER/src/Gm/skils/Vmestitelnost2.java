package Gm.skils;

import MapObjects.Units.Player;

/**
 * Created by 777 on 08.08.2017.
 */
public class Vmestitelnost2 extends Skil {
    Vmestitelnost2(){
        super(200,5);
    }
    public void activation(Player pl){
        pl.inventar.colvoslotov+=2;
    }
}
