package Frontend;

import Base.Address;
import Base.Frontend;
import MessageSystem.MsgToFrontend;

public class MsgRegistrationResult extends MsgToFrontend {
    int sessionId;
    String name;
    int resultreg;

    public MsgRegistrationResult(Address from, Address to, int sessionId, String name, int resultreg) {
        super(from, to);
        this.sessionId = sessionId;
        this.name = name;
        this.resultreg = resultreg;
    }

    @Override
    public void exec(Frontend frontend) {
        frontend.RegStatus(sessionId, name, resultreg);
    }

}
