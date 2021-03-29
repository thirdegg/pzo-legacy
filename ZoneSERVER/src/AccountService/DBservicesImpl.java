package AccountService;

import Base.AccountService;
import Base.Address;
import Base.MessageSystem;
import Server.NettyServerHandler;
import Utils.Config;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import MapObjects.Units.Player;
import Utils.TimeHelper;
import Utils.Util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBservicesImpl implements Runnable, AccountService {
    UserDataSetDAO dao;
    private Address address;
    private MessageSystem ms;
    boolean run=true;
    FileWriter fw;
    HashMap <String,UserDataSet>nameuds=new HashMap<>();
    public DBservicesImpl(MessageSystem ms) {
        this.ms = ms;
        this.address = new Address();
        ms.addAccountService(this);
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UserDataSet.class);
        SessionFactory sessionFactory = createSessionFactory(configuration);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        System.out.append(transaction.getLocalStatus().toString()).append('\n');
        session.close();
        try {
            fw = new FileWriter("errorAccountService.txt", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        dao = new UserDataSetDAO(sessionFactory,fw);
        List<UserDataSet> alluds;
        alluds=dao.readAll();
        for(UserDataSet uuds:alluds){
            nameuds.put(uuds.name,uuds);
        }
    }
@Override
public boolean isNamePlayer(String name){
   // UserDataSet uds = dao.readByName(name);
    if(!nameuds.containsKey(name))return false;
    else return true;
}
    @Override
    public int autorizationUser(String name, String password, Player player) {
      //  UserDataSet uds = dao.readByName(name);
        UserDataSet uds=nameuds.get(name);
        if (uds != null&&player!=null) {
            //проверкане авторизован ли игрок
            for (Player pl: NettyServerHandler.sessionIdToUserSession.values()){
            if(pl.autorized&&pl.name.equals(name))return 2;}
            // проверка пароля
                if (password.trim().equals(uds.password)) {
                    System.out.println("new user autorized"+player.name+" kol-vo"+NettyServerHandler.sessionIdToUserSession.size());
                    player.uds = uds;
                    player.iddatabase = uds.getId();
                    player.x = uds.x;
                    player.y = uds.y;
                    player.speed = player.normalspeed;
                    player.holod = uds.holod;
                    player.golod = uds.golod;
                    player.zhazda = uds.zhazda;
                    player.name = uds.name;
                    player.pmLife.life = uds.kolvolife;
                    player.timedie = uds.timedie;
                    player.nameclan=uds.klanname;
                    Util.setItIzProf(player,uds.items);
                    if (uds.qvest != null) addHashMapQvest(player, uds.qvest);
                    player.idmap=uds.idmap;
                    player.exp=uds.exp;
                    player.sskill=uds.skils;
                    return 1;
                }
        }
        //пароль неверен или логин
        return 3;
    }

    @Override
    public boolean registrationUser(String userName, String userPassword) {
      //  if (dao.readByName(userName) != null) return false;
        if (nameuds.containsKey(userName)) return false;
        UserDataSet uds = new UserDataSet(userName, userPassword);
        dao.save(uds);
        nameuds.put(userName,uds);
        System.out.println("new user registration complate"+userName);
        return true;
    }

    private void addHashMapQvest(Player player, String qvest) {
        if(player!=null){
        String str[] = qvest.split(":");
        for (int i = 0; i < str.length; i += 2) {
            int qv=Integer.parseInt(str[i]);
            int progress=Integer.parseInt(str[i + 1]);
            player.qvests.put(qv,progress);
        }
        }
    }

    @Override
    public void setState(long id, float x, float y, float life, float holod,float golod, float zhazda, float rad,
                         float krovotech, int timedie, String items, String qvest, UserDataSet uds,String name,int idmap,String klanname,int exp,String skils) {
        uds.x = x;
        uds.y = y;
        uds.kolvolife = life;
        uds.holod = holod;
        uds.golod = golod;
        uds.zhazda = zhazda;
        uds.rad = rad;
        uds.krovotech = krovotech;
        uds.timedie = timedie;
        uds.items = items;
        uds.qvest = qvest;
        uds.idmap=idmap;
        uds.klanname=klanname;
        uds.exp=exp;
        uds.skils=skils;
        dao.update(uds);
    }

    @Override
    public void run() {
        while (run) {
            try {
                ms.execForAbonent(this);
                TimeHelper.sleep(100);
            }catch(Exception e){
                // !!!!!!!!!!!!!!!!!!!!
               // run=false;
                e.printStackTrace();
                try {
                    String err = "\n";
                    for (StackTraceElement ste : e.getStackTrace()) {
                        err += "Class name -" + ste.getClassName() + " Line -" + ste.getLineNumber() + "\n";
                    }
                    fw.write("error - "+e.toString() + " Message -" + e.getMessage() + err);
                    fw.flush();
                    fw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public Address getAddress() {
        return address;
    }

    public MessageSystem getMessageSystem() {
        return ms;
    }

    private SessionFactory createSessionFactory(Configuration configuration) {
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        // база на сервере
        if(Config.realtest){
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/mysql");
        configuration.setProperty("hibernate.connection.username", "root");
        configuration.setProperty("hibernate.connection.password", "z");}
        else{
        //на клиенте
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/MySQL");
        configuration.setProperty("hibernate.connection.username", "Korvin");
        configuration.setProperty("hibernate.connection.password", "1234");}

        configuration.setProperty("hibernate.show_sql", "false");
        //validate-проверяет схему не внося изменений
        //update-обновляет схему если находит различия
        //create-пересоздает схему
        //create-drop-уничтожает схему при закрытии SessionFactory
        String c="create";
        String u="update";
        configuration.setProperty("hibernate.hbm2ddl.auto",Config.clearbase?c:u);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }
}