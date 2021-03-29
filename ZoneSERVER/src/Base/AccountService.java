package Base;

import AccountService.UserDataSet;
import MapObjects.Units.Player;

public interface AccountService extends Abonent {
    MessageSystem getMessageSystem();

    int autorizationUser(String name, String password, Player pl);

    boolean registrationUser(String userName, String userPassword);
//имя нужно когда нет базы данных
    void setState(long id, float x, float y, float life, float holod,
                         float golod, float zhazda, float rad, float krovotech, int timedie, String items, String qvest, UserDataSet uds,
                         String name,int idmap,String klanname,int exp, String skils);
    boolean isNamePlayer(String name);
}
