package Frontend;

import Base.AccountService;
import Base.Address;
import MessageSystem.MsgToAS;

public class MgsRegistrationNewUser extends MsgToAS {
    private String userName;
    private String userPassword;
    //private String userEmail;
    private int sessionId;

    public MgsRegistrationNewUser(Address from, Address to, int sessionId, String userName, String userPasswordl) {
        super(from, to);
        this.userName = userName;
        this.userPassword = userPasswordl;
        //this.userEmail=userEmail;
        this.sessionId = sessionId;
    }

    @Override
    public void exec(AccountService accountService) {
        if (accountService.registrationUser(userName, userPassword)) {
            accountService.getMessageSystem().sendMessage(new MsgRegistrationResult(getTo(), getFrom(), sessionId, userName, 2));
        } else {
            accountService.getMessageSystem().sendMessage(new MsgRegistrationResult(getTo(), getFrom(), sessionId, null, 1));
        }
    }

}
