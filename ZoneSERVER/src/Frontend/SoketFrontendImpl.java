package Frontend;


import Base.Address;
import Base.Frontend;
import Base.MessageSystem;
import Gm.MsgAddNewUser;
import Server.NettyServerHandler;
import Utils.Config;
import Utils.TimeHelper;
import Utils.Util;

import java.io.FileWriter;
import java.io.IOException;

public class SoketFrontendImpl implements Runnable, Frontend {
    Address address;
    FileWriter fw;
    FileWriter fw2;
    private MessageSystem ms;
    boolean run=true;

    public SoketFrontendImpl(MessageSystem ms) {
        this.ms = ms;
        this.address = new Address();
        ms.addFrontend(this);
        try {
            fw = new FileWriter("errors.txt", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fw2 = new FileWriter("errorFrontend.txt", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doGet(int st, String str) {
//разбераем сообщение от пользователя
//System.out.print("пришло сообщениена фронтенд-"+str);
        String[] subst = str.split(":");
        String status = subst[0];
        if(subst.length>4){
            NettyServerHandler.sendMsgClient("Логин или пароль содержат запрещенные символы", st);
            return;
        }
        switch (status) {
            case "aut":
                String login = subst[1];
                String password = subst[2];
                int v=0;
                try{
                if(subst.length>3)v=Integer.parseInt(subst[3]);}
                catch(Exception e){
                    NettyServerHandler.sendMsgClient("Логин или пароль содержат запрещенные символы", st);
                    return;
                }
                if(v!= Config.gameversion){
                    NettyServerHandler.sendMsgClient("Ваша версия игры устарела.", st);
                    return;
                }
                // проверка
                for (int i = 0; i < login.length(); i++) {
                    char zchar=login.charAt(i);
                    if (zchar == ' '||zchar == '*'||zchar == '/'||zchar == ':') {
                        NettyServerHandler.sendMsgClient("Логин содержит запрещенный символы", st);
                        return;
                    }
                }
                for (int i = 0; i < password.length(); i++) {
                    char zchar=password.charAt(i);
                    if (zchar == ' '||zchar == '*'||zchar == '/'||zchar == ':') {
                        NettyServerHandler.sendMsgClient("Пароль содержит запрещенные символы", st);
                        return;
                    }
                }
                if (login.length() < 3 || login.length() > 13) {
                    NettyServerHandler.sendMsgClient("Слишком короткий или длинный логин", st);
                    return;
                }
                if (password.length() < 3 || password.length() > 13) {
                    NettyServerHandler.sendMsgClient("Слишком короткий или длинный пароль", st);
                    return;
                }
                //отправка запроса в акаунт сервис
                Address frontendAddress = getAddress();
                Address addressAS = ms.getAddressService().getAddressAS();
                ms.sendMessage(new MsgGetUserId(frontendAddress, addressAS, login, password, NettyServerHandler.sessionIdToUserSession.get(st)));
                NettyServerHandler.sendMsgClient("Ждите идет авторизация", st);
                break;
            case "reg":
                String rlogin = subst[1];
                String rpassword = subst[2];
                int v2=0;
                try{
                if(subst.length>3)v2=Integer.parseInt(subst[3]);}
                   catch(Exception e){
                NettyServerHandler.sendMsgClient("Логин или пароль содержат запрещенные символы", st);
                return;
            }
                if(v2!= Config.gameversion){
                    NettyServerHandler.sendMsgClient("Ваша версия игры устарела.", st);
                    return;
                }
                // провеерка
                for (int i = 0; i < rlogin.length(); i++) {
                    if (rlogin.charAt(i) == ' '||rlogin.charAt(i) == '*') {
                        NettyServerHandler.sendMsgClient("Логин содержит запрещенные символы", st);
                        return;
                    }
                }
                for (int i = 0; i < rpassword.length(); i++) {
                    if (rpassword.charAt(i) == ' '||rpassword.charAt(i) == '*') {
                        NettyServerHandler.sendMsgClient("Пароль содержит запрещенные символы", st);
                        return;
                    }
                }
                if (rlogin.length() < 3 || rlogin.length() > 13) {
                    NettyServerHandler.sendMsgClient("Слишком короткий или длинный логин", st);
                    return;
                }
                if (rpassword.length() < 3 || rpassword.length() > 13) {
                    NettyServerHandler.sendMsgClient("Слишком короткий или длинный пароль", st);
                    return;
                }
                //отправка запроса в акаунт сервис
                Address raddressAS = ms.getAddressService().getAddressAS();
                ms.sendMessage(new MgsRegistrationNewUser(getAddress(), raddressAS, st, rlogin, rpassword));
                NettyServerHandler.sendMsgClient("Ждите идет регистрация", st);
                break;
            case "tabl":
                String tmp = "10/";
               /* String tmp2 = null;
                for (Player pl : NettyServerHandler.sessionIdToUserSession.values()) {
                    if (tmp2 == null) tmp2 = ":" + pl.name + ":" + (int) pl.x + ":" + (int) pl.y;
                    else tmp2 += ":" + pl.name + ":" + (int) pl.x + ":" + (int) pl.y;
                }
                NettyServerHandler.sendMsgClient(tmp + tmp2, st);*/
                NettyServerHandler.sendMsgClient(tmp +NettyServerHandler.sessionIdToUserSession.size(), st);
                break;
            case "err":
                try {
                    fw.write("\nEXCEPTION:" + subst[1]+(subst.length>2?subst[2]:" "));
                    fw.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    protected void finalize() throws Throwable {
        fw.close();
        fw2.close();
        super.finalize();
    }

    @Override
    public void run() {
        while (run) {
            try{
            ms.execForAbonent(this);
            TimeHelper.sleep(100);
        }catch(Exception e){
            run=false;
            e.printStackTrace();
            try {
                String err = "\n";
                for (StackTraceElement ste : e.getStackTrace()) {
                    err += "Class name -" + ste.getClassName() + " Line -" + ste.getLineNumber() + "\n";
                }
                fw2.write("error - "+e.toString() + " Message -" + e.getMessage() + err);
                fw2.flush();
                fw2.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        }
    }

    //autorization and start game
    @Override
    public void AutStatus(int otvet, int idchanel) {
        if (otvet == 1) {
            Address addressGM = ms.getAddressService().getAddressGameMechanics();
            ms.sendMessage(new MsgAddNewUser(getAddress(), addressGM, idchanel));
        }
        if (otvet == 2) {
            NettyServerHandler.sendMsgClient("Такой пользователь уже авторизован", idchanel);
        }
        if (otvet == 3) {
            NettyServerHandler.sendMsgClient("Неверный логин или пароль", idchanel);
        }
    }

    // регистрация
    @Override
    public void RegStatus(int sessionId, String name, int resultreg) {
        if (resultreg == 2) {
            NettyServerHandler.sendMsgClient("Ура!!! Вы зарегистрированны, теперь авторизуйтесь", sessionId);
        } else {
            NettyServerHandler.sendMsgClient("Пользователь с таким именем уже существует", sessionId);
        }
    }
    @Override
    public Address getAddress() {
        return address;
    }
}
