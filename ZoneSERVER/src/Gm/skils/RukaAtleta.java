package Gm.skils;

import MapObjects.Units.Player;

/**
 * Created by 777 on 08.08.2017.
 */
public class RukaAtleta extends Skil {
    RukaAtleta(){
        super(150,1);
    }
    @Override
    public void activation(Player pl){
    pl.bolt.timefly=20;
    }
}
