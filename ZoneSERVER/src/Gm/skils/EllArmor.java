package Gm.skils;

import MapObjects.Units.Player;

/**
 * Created by 777 on 19.08.2017.
 */
public class EllArmor extends Skil {
    EllArmor(){
        super(200,8);
    }
    @Override
    public void activation(Player pl){
        pl.ellarmor+=5;
    }
}