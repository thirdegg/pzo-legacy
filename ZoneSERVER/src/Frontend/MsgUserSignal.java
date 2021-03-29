package Frontend;

import Base.Address;
import Gm.GameMechanics;
import MessageSystem.MsgToGM;

public class MsgUserSignal extends MsgToGM {
    int id;
    String str;

    public MsgUserSignal(Address from, Address to, int id, String str) {
        super(from, to);
        this.id = id;
        this.str = str;
    }

    @Override
    public void exec(GameMechanics gm) {
        gm.userSignal(id, str);
    }

}
