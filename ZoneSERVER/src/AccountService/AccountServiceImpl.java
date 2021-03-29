package AccountService;

import Base.AccountService;
import Base.Address;
import Base.MessageSystem;
import MapObjects.Units.Player;
import Server.NettyServerHandler;
import Utils.TimeHelper;
import Utils.Util;

import java.util.HashMap;
import java.util.Map;

public class AccountServiceImpl implements Runnable, AccountService {
    private Address address;
    private MessageSystem ms;
    private Map<String, UserProfile> usersprof = new HashMap<>();

    public AccountServiceImpl(MessageSystem ms) {
        this.ms = ms;
        this.address = new Address();
        ms.addAccountService(this);
        UserProfile userProfile = new UserProfile("korvin", "123");
        usersprof.put("korvin", userProfile);
        UserProfile userProfile2 = new UserProfile("zxc", "123");
        usersprof.put("zxc", userProfile2);
        UserProfile userProfile3 = new UserProfile("zxc2", "123");
        usersprof.put("zxc2", userProfile3);
    }
@Override
    public boolean registrationUser(String userName, String userPassword) {
        TimeHelper.sleep(1000);
        if (usersprof.containsKey(userName)) return false;
        UserProfile userProfile = new UserProfile(userName, userPassword.trim());
        usersprof.put(userName, userProfile);
        System.out.println("new user created");
        return true;
    }
@Override
    public int autorizationUser(String name, String password, Player player) {
        TimeHelper.sleep(100);
        UserProfile userProfile = usersprof.get(name);
        //проверкане авторизован ли игрок
        for (Player pl: NettyServerHandler.sessionIdToUserSession.values()){
            if(pl.autorized&&pl.name.equals(name))return 2;}

        if (userProfile != null) {
                if (password.trim().equals(userProfile.password)) {
                    System.out.println("new user autorized"+" kol-vo"+NettyServerHandler.sessionIdToUserSession.size());
                    //установка характеристик
                    player.x = userProfile.x;
                    player.y = userProfile.y;
                    player.speed = userProfile.speed;
                    player.holod = userProfile.holod;
                    player.golod = userProfile.golod;
                    player.zhazda = userProfile.zhazda;
                    player.name = userProfile.login;
                    player.pmLife.life = userProfile.life;
                    player.timedie = userProfile.timedie;
                    player.idmap=userProfile.idmap;
                    player.nameclan=userProfile.klanname;
                    player.exp=userProfile.exp;
                    Util.setItIzProf(player,userProfile.items);
                    player.sskill=userProfile.skils;
                    if (userProfile.qvest != null) addHashMapQvest(player, userProfile.qvest);
                    return 1;
                }
        }
        return 3;
    }
    @Override
    public boolean isNamePlayer(String name){
        UserProfile userProfile = usersprof.get(name);
        if(userProfile==null)return false;
        else return true;
    }
    public void run() {
        while (true) {
            ms.execForAbonent(this);
            TimeHelper.sleep(100);
        }
    }

    private void addHashMapQvest(Player player, String qvest) {
        String str[] = qvest.split(":");
        for (int i = 0; i < str.length; i += 2) {
            player.qvests.put(Integer.parseInt(str[i]), Integer.parseInt(str[i + 1]));
        }
    }

    public Address getAddress() {
        return address;
    }

    public MessageSystem getMessageSystem() {
        return ms;
    }

    @Override
    public void setState(long id, float x, float y, float life,float holod,
                         float golod, float zhazda, float rad, float krovotech,
                         int timedie, String items, String qvest, UserDataSet uds,String name,int idmap,String klanname,int exp,String skils) {
            usersprof.get(name).setState(x, y, life,holod, golod, zhazda, rad, krovotech,timedie,items,qvest,idmap,klanname,exp,skils);
    }
}
