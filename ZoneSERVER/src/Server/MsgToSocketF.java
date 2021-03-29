package Server;

import Base.Address;
import Base.Frontend;
import MessageSystem.MsgToFrontend;

public class MsgToSocketF extends MsgToFrontend {
    int st;
    String str;

    public MsgToSocketF(Address from, Address to, int st, String str) {
        super(from, to);
        this.st = st;
        this.str = str;

    }

    @Override
    public void exec(Frontend frontend) {
        frontend.doGet(st, str);
    }

}
