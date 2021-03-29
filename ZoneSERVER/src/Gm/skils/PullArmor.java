package Gm.skils;

import MapObjects.Units.Player;

/**
 * Created by 777 on 19.08.2017.
 */
public class PullArmor extends Skil {
    PullArmor(){
        super(150,7);
    }
    @Override
    public void activation(Player pl){
        pl.pullarmor+=5;
    }
}
