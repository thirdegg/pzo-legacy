package MessageSystem;

import Base.Abonent;
import Base.Address;
import Base.Msg;
import Gm.GameMechanics;

public abstract class MsgToGM extends Msg {

    public MsgToGM(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Abonent abonent) {
        if (abonent instanceof GameMechanics) {
            exec((GameMechanics) abonent);
        }
    }

    public abstract void exec(GameMechanics gm);
}
