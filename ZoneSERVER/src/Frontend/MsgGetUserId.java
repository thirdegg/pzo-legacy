package Frontend;

import Base.AccountService;
import Base.Address;
import MessageSystem.MsgToAS;
import MapObjects.Units.Player;

public class MsgGetUserId extends MsgToAS {
    private String name;
    private String password;
    private Player pl;

    public MsgGetUserId(Address from, Address to, String name, String password, Player pl) {
        super(from, to);
        this.name = name;
        this.pl = pl;
        this.password = password;
    }

    public void exec(AccountService accountService) {
        int otvet=0;
        if(pl!=null){
            otvet = accountService.autorizationUser(name, password, pl);}
        if(pl!=null){
        accountService.getMessageSystem().sendMessage(new MsgUpdateUserId(getTo(), getFrom(), otvet, pl.idchanel));}
    }
}
