package Frontend;

import Base.Address;
import Base.Frontend;
import MessageSystem.MsgToFrontend;

public class MsgUpdateUserId extends MsgToFrontend {

    private int idchanel;
    private int otvet;

    public MsgUpdateUserId(Address from, Address to, int otvet, int idchanel) {
        super(from, to);
        this.idchanel = idchanel;
        this.otvet = otvet;
    }

    public void exec(Frontend frontend) {
        frontend.AutStatus(otvet, idchanel);
    }

}
