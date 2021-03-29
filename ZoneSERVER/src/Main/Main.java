package Main;

import AccountService.AccountServiceImpl;
import AccountService.DBservicesImpl;
import Frontend.SoketFrontendImpl;
import Gm.GameMechanics;
import MessageSystem.MessageSystemImpl;
import Server.NettyServer;
import Utils.Config;
import Utils.rand;

public class Main {
    public static void main(String[] args) throws Exception {
        new Config();
        MessageSystemImpl ms = new MessageSystemImpl();
        AccountServiceImpl DBervice = new AccountServiceImpl(ms);
      //  DBservicesImpl DBervice = new DBservicesImpl(ms);
        GameMechanics gm = new GameMechanics(ms);
        SoketFrontendImpl frontend = new SoketFrontendImpl(ms);
        (new Thread(frontend)).start();
        (new Thread(DBervice)).start();
        (new Thread(gm)).start();
        System.out.println("Program starting...");
        NettyServer serv = new NettyServer(4444, ms);
        if(!Config.realtest){Window wind = new Window();}
    }

}