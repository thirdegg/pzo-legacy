package MessageSystem;

import Base.Abonent;
import Base.Address;
import Base.Frontend;
import Base.Msg;

public abstract class MsgToFrontend extends Msg {

    public MsgToFrontend(Address from, Address to) {
        super(from, to);
    }

    public void exec(Abonent abonent) {
        if (abonent instanceof Frontend) {
            exec((Frontend) abonent);
        }
    }

    public abstract void exec(Frontend frontend);

}
