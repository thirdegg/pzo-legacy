package Gm;

import Base.Address;
import Gm.GameMechanics;
import MessageSystem.MsgToGM;

/**
 * Created by 777 on 22.08.2017.
 */
public class MsgResultIsName extends MsgToGM {
    boolean result;
    int idcanel;
    LichkaMessage lmsg;
    public MsgResultIsName(Address from, Address to, boolean result,int idcanel,LichkaMessage lmsg) {
        super(from, to);
        this.result=result;
        this.idcanel=idcanel;
       this.lmsg=lmsg;
    }
    @Override
    public void exec(GameMechanics gm) {
        gm.resultIsName(result,idcanel,lmsg);
    }
}
