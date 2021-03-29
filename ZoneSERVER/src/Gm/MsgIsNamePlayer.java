package Gm;

import Base.AccountService;
import Base.Address;
import Frontend.MsgUpdateUserId;
import MapObjects.Units.Player;
import MessageSystem.MsgToAS;

/**
 * Created by 777 on 22.08.2017.
 */
public class MsgIsNamePlayer extends MsgToAS  {
    int idcanel;
    LichkaMessage lmsg;
    public MsgIsNamePlayer(Address from, Address to,int idcanel,LichkaMessage lmsg) {
        super(from, to);
    this.idcanel=idcanel;
    this.lmsg=lmsg;
    }
    @Override
    public void exec(AccountService accountService) {
        accountService.getMessageSystem().sendMessage(new MsgResultIsName(getTo(), getFrom(),accountService.isNamePlayer(lmsg.to),idcanel,lmsg));
    }
}
