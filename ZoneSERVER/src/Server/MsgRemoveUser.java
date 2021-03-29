package Server;

import Base.Address;
import Gm.GameMechanics;
import MessageSystem.MsgToGM;
import MapObjects.Units.Player;

public class MsgRemoveUser extends MsgToGM {
    Player pl;

    public MsgRemoveUser(Address from, Address to, Player pl) {
        super(from, to);
 this.pl=pl;
    }

    @Override
    public void exec(GameMechanics gm) {
        gm.remUser(pl);
    }

}
