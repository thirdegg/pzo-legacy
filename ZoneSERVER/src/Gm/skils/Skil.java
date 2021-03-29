package Gm.skils;

import MapObjects.Units.Player;
import Server.NettyServerHandler;

/**
 * Created by 777 on 02.08.2017.
 */
public class Skil {
    int texp;
    int numskil;
    public Skil(int texp,int numskil){
        this.texp=texp;
        this.numskil=numskil;
    }
    public void learn(Player pl){
        if(pl.numstoskils.containsKey(numskil)){NettyServerHandler.sendMsgClient("28/2",pl.idchanel);return;}
    if(pl.exp>=texp){pl.exp-=texp;pl.numstoskils.put(numskil,this);
        NettyServerHandler.sendMsgClient("28/0/"+numskil+"/"+pl.exp,pl.idchanel);
        activation(pl);}
        else{NettyServerHandler.sendMsgClient("28/1",pl.idchanel);}
    }
    public void activation(Player pl){

    }
}
