package Gm.skils;

import MapObjects.Units.Player;

/**
 * Created by 777 on 19.08.2017.
 */
public class FizArmor extends Skil {
    FizArmor(){
        super(200,9);
    }
    @Override
    public void activation(Player pl){
        pl.fizarmor+=5;
    }
}
