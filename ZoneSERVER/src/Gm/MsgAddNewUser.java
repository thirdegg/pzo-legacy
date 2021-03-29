package Gm;

import Base.Address;
import MessageSystem.MsgToGM;

public class MsgAddNewUser extends MsgToGM {
    int sessionId;

    public MsgAddNewUser(Address from, Address to, int sessionId) {
        super(from, to);
        this.sessionId = sessionId;
    }

    @Override
    public void exec(GameMechanics gm) {
        gm.AddUser(sessionId);
    }

}
