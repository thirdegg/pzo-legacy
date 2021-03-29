package Gm.skils;

import MapObjects.Units.Player;

/**
 * Created by 777 on 19.08.2017.
 */
public class Berezhlivost extends Skil {
    Berezhlivost(){
        super(200,6);
    }
    @Override
    public void activation(Player pl){
        pl.inventar.skillberezhlivost=true;
    }
}