package Gm.skils;

import MapObjects.Units.Player;

/**
 * Created by 777 on 08.08.2017.
 */
public class Vmestitelnost extends Skil {
    Vmestitelnost(){
        super(150,4);
    }
    public void activation(Player pl){
    pl.inventar.colvoslotov+=1;
    }
}
