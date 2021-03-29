package Base;

import MessageSystem.AddressService;

public interface MessageSystem {
    void addFrontend(Abonent abonent);

    void addGameMechanics(Abonent abonent);

    void addAccountService(Abonent abonent);

    AddressService getAddressService();

    void sendMessage(Msg message);

    void execForAbonent(Abonent abonent);

}
