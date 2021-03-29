package Gm;

import Gm.skils.SystemSkils;
import Klans.Klan;
import Klans.SystemKlans;
import MapObjects.MObject;
import MapObjects.MapObject;
import MapObjects.ObdjBinokl;
import MapObjects.Units.Npc;
import Maps.*;
import Modules.Teleport;
import Qvests.SystemQvest;
import Base.Abonent;
import Base.Address;
import Base.MessageSystem;
import Server.NettyServerHandler;
import MapObjects.Units.Player;
import Utils.Overlap;
import Utils.TimeHelper;
import Utils.Util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class GameMechanics implements Runnable, Abonent {
    // желательное время обновления игровой логики
    private final int TICK_TIME = 50;
    Address addres;
    StateWorld stateworld;
    SystemQvest systemqvest;
    SystemKlans sklans;
    ObmenSystem systemobmen;
    SystemSkils systemskils;
    HashMap<String,List<LichkaMessage>>playersmessage=new HashMap<>();
    HashMap<String,Player>nametoPlayer=new HashMap<>();
    //переделать -- звпрет повторного выполнения квеста
    public static HashMap<String,List<Integer>>nametoQvest=new HashMap<>();
    Chat chat;
    public static MessageSystem ms;
    // все локации
    WMap maps[]=new WMap[20];
    private boolean run=true;
    // используется для записи ошибок в тхт файл
    FileWriter fw;

    public GameMechanics(MessageSystem ms) {
        this.ms = ms;
        this.addres = new Address();
        ms.addGameMechanics(this);
        chat=new Chat();
        maps[0] = new WorldMap0();
        maps[1] = new WorldMap1();
        maps[2]=new WorldMap2();
        maps[3]=new WorldMap3();
        maps[4]=new WorldMap4();
        maps[5]=new WorldMap5();
        maps[6]=new WorldMap6();
        maps[7]=new WorldMap7();
        maps[8]=new WorldMap8();
        maps[9]=new WorldMap9();
        maps[10]=new WorldMap10();
        maps[11]=new WorldMap11();
        maps[12]=new WorldMap12();
        maps[13]=new WorldMap13();
        maps[14]=new WorldMap14();
        maps[15]=new WorldMap15();
        maps[16]=new WorldMap16();
        maps[17]=new WorldMap17();
        maps[18]=new WorldMap18();
        maps[19]=new WorldMap19();
        sklans=new SystemKlans(maps);
        stateworld = new StateWorld(maps,sklans);
        systemqvest = new SystemQvest();
        systemobmen=new ObmenSystem();
        systemskils=new SystemSkils();
        try {
            fw = new FileWriter("GameMechanicsErrors.txt", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
// этот метод обновляет логику на всех картах
    // особо можеш не вникать что тут происходит
    @Override
    public void run() {
        long startTime2 = System.nanoTime();
        while (run) {
            try {
            float deltaTime2 = (System.nanoTime() - startTime2) / 1000000000.0f;
            startTime2 = System.nanoTime();
            long startTime = System.currentTimeMillis();
            stateworld.run(deltaTime2, startTime);
            ms.execForAbonent(this);
            for (WMap map:maps){map.run(deltaTime2, ms);}
            int deltaTime = (int) (System.currentTimeMillis() - startTime);
            float load = deltaTime / TICK_TIME;
            if (load < 1) {
                TimeHelper.sleep(TICK_TIME - deltaTime);
            }
            //что то пошло не так сохраняем игроков в базу
            }catch (Exception e) {
                e.printStackTrace();
                saveAllUsers();
                NettyServerHandler.sendMsgAllClients("16/");
                run=false;

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

    //метод добавления игрока в игру после авторизации
    void AddUser(int id) {
        // получаем игрока из списка всех подключенныйх игроков
        Player pl = NettyServerHandler.sessionIdToUserSession.get(id);
        if(pl!=null){
         sklans.addOnlinePlayer(pl);
         systemskils.addHeroSkills(pl);
            nametoPlayer.put(pl.name,pl);
        // отправляем игроку все его данные
        NettyServerHandler.sendMsgClient("h/"+ pl.id + "/" + pl.x + "/" + pl.y + "/" + pl.name + "/" + pl.pmLife.life + "/" + pl.speed + "/" + pl.uron + "/"
                + pl.holod + "/" + pl.golod + "/" + pl.zhazda + "/" + pl.radiation + "/" + pl.krovotech + "/" + pl.timedie + "/" + getStringQvest(pl)+
                "/"+pl.idmap+"/"+pl.inventar.getTipOdeto()+"/"+(int)pl.pmLife.maxlife+"/"+pl.nameclan, pl.idchanel);
        pl.autorized = true;
        // добавляем игрока на карту
        maps[pl.idmap].addPlayer(pl);

        // что происходит ниже не важно
        //проверяем есть ли на карте квестовый предмет (надо как то все переделать)
        if(pl.qvests.containsKey(2)){
            if(pl.qvests.get(2)==1){
        boolean esti=false;
            for(MapObject mo:maps[5].mapobjects.values()){
                if(mo.tip==Util.BINOKL){
                    ObdjBinokl bi=(ObdjBinokl)mo;
                    if(bi.pla.equals(pl.name)){esti=true;break;}
                }
            }
            if(!esti){ObdjBinokl bin=new ObdjBinokl(693,1077,pl.name);
                maps[5].mapobjects.put(bin.id,bin);
            }
        }
        }
        }
        //загружаем скилы
        if(pl.sskill!=null&&pl.sskill.length()>1)NettyServerHandler.sendMsgClient("28/3/"+pl.sskill,pl.idchanel);
        //отправляем входящие сообщения
        if(playersmessage.containsKey(pl.name)){
            for(LichkaMessage str:playersmessage.get(pl.name)){
                NettyServerHandler.sendMsgClient("30/2/"+str.from+"/"+str.message+(str.zapros?"/0":""),pl.idchanel);
            }
            playersmessage.remove(pl.name);
        }
        //проверяем вступление в клан
        if(sklans.zaprosnametoklan.containsKey(pl.name)){
            Klan klan=sklans.klans.get(sklans.zaprosnametoklan.get(pl.name));
            if(klan!=null){klan.addPlayer(pl);
            sklans.zaprosnametoklan.remove(pl.name);}
        }
    }
// удаляем игрока из игры когда он вышел
    public void remUser(Player p) {
        sklans.remOnlinePlayer(p);
        chat.remPlayer(p,maps[p.idmap]);
        // если игрок был мертв во время выхода
        if (p.getState() == p.DEAD) {
            p.timedie = (int) (p.ldt - System.currentTimeMillis());
        } else p.timedie = 0;
        // отправляем сообщение с данными игрока в аккаунт сервис там эти данные будут записаны в базу MySql(или будут хранится в опиративной памяти если сервер запущен без базы)
        ms.sendMessage(new MsgSetPlayerState(getAddress(), ms.getAddressService().getAddressAS(), p.iddatabase, p.x, p.y, p.pmLife.life,
                p.holod, p.golod, p.zhazda, p.radiation, p.krovotech, p.timedie,Util.getItemsSave(p.inventar),getStringQvest(p), p.uds,p.name,p.idmap,p.nameclan,p.exp,systemskils.saveHeroSkillstoBase(p)));
        // удаляем игрока с карты
        maps[p.idmap].removePlayer(p);
        nametoPlayer.remove(p.name);
    }

    //получение пользовательских сообщений
    public void userSignal(int chanelid, String strr) {
        try{
        // находим игрока в списке по его id канала
        Player pp = NettyServerHandler.sessionIdToUserSession.get(chanelid);
        if(pp!=null) {
//if(strr.equals(pp.oldclientmsg))return;else pp.oldclientmsg=strr;
            String str[] = strr.split("/");
            int tip = Integer.parseInt(str[1]);
            switch (tip) {
//движение игроков
                case 1:
                    pp.setState(pp.DVIG, str);
                    break;
//чат
                case 2:
                    //переделать
                    if (str.length>3&&pp.name.equals("korvin") && str[3].charAt(0) == '0') {
                        adminSignal(str[3], pp);
                    } else {
                        chat.msgPlayers(pp,str,maps[pp.idmap]);
                    }
                    break;
//инвентарь использовать предмет
                case 3:
                    int odus=Integer.parseInt(str[2]);
                    if(odus==1){
                        if(str.length>4)pp.inventar.useObject(maps[pp.idmap],Integer.parseInt(str[3]),Long.parseLong(str[4]));
                        else
                        pp.inventar.useObject(maps[pp.idmap],Integer.parseInt(str[3]));
                    }
                    if(odus==2)pp.inventar.setItem(Integer.parseInt(str[3]));
                    break;
//касание обьекта на клиенте (атака)
                case 4:
                    pp.setState(pp.ATAKA, str);
                    break;
//инвентарь снять предмет
                case 5:
                        pp.inventar.Snyat(Integer.parseInt(str[2]));
                    break;
//войти в дом(переписать если будут проблемы проверка идет на клиенте(почти))
                case 6:
                    pp.setState(pp.VDOME,null);
                    break;
//выход издома
                case 7:
                    pp.setState(pp.DVIG, null);
                    break;
//перезарядка
                case 8:
                    pp.setState(pp.PEREZARAD, str);
              break;
//загрузка игрока
                case 9:
                    switch (Integer.parseInt(str[2])) {
                        case 1:
                            NettyServerHandler.sendMsgClient("4/" + pp.inventar.getItemsForPlayer(), pp.idchanel);
                            break;
                        case 2:
                            NettyServerHandler.sendMsgClient("6/" + stateworld.seconds + "/" + stateworld.minutes + "/" + stateworld.hours + "/" + stateworld.rain, pp.idchanel);
                            if (stateworld.vibros.vibros)
                                NettyServerHandler.sendMsgClient("7/" + stateworld.vibros.collitter + "/" + stateworld.vibros.getOst() + "/" + stateworld.vibros.vibrostime + "/" + stateworld.vibros.timeuron, pp.idchanel);
                            break;
                    }
                    break;
//торговец
                case 10:
                    Npc torg = (Npc) pp.mapobjects.get(Long.parseLong(str[3]));
                    if (torg == null) return;
                    //купить
                    if (Integer.parseInt(str[2]) == 1) {
                        if (!pp.inventar.getMesto()) return;
                        torg.bay(Integer.parseInt(str[4]), pp);
                    }
                    //продать
                    else {
                        torg.sell(Integer.parseInt(str[4]), pp);
                    }
                    break;
//игрок поднимет предмет с земли
                case 11:
                    if (!pp.inventar.getMesto()) return;
                    MapObject mo = maps[pp.idmap].mapobjects.get(Long.parseLong(str[2]));
                    if (mo != null) {
                        if (Overlap.overlapRectang(pp.rectang, mo.rectang)) {
                            mo.centx = -1000;
                            mo.centy = -1000;
                            maps[pp.idmap].mapobjects.remove(mo.id);
                            MObject mob=(MObject)mo;
                            mob.addItem(pp);
                            for (Player player : maps[pp.idmap].players) {
                                if (player.mapobjects.containsKey(mo.id)) {
                                    NettyServerHandler.sendMsgClient("2/1/" + mo.id + "/-1000/-1000", player.idchanel);
                                }
                            }
                        }
                    }
                    break;
// qvest
                case 12:
                    systemqvest.Qvest(str, pp, maps);
                    break;
                //переход в другую локацию
                case 13:
                    switchMap(pp);
                    break;
                //инвентарь выбросить предмет
                case 14:
                    pp.inventar.throwItem(maps[pp.idmap].addmapobjects,Integer.parseInt(str[2]),pp.urdlnapravlenie);
                    break;
                //присесть
                case 15:
                    pp.setState(pp.SITTING,str);
                    break;
                   // bolt
                case 16:
                    if(!pp.bolt.run&&pp.state==pp.DVIG){int naprav=Integer.parseInt(str[2]);
                    //проверка соответствия направления болта и направления героя
                    if(naprav<=5)if(pp.urdlnapravlenie!=naprav)return;
                    if(naprav>5){if(naprav==9){if(pp.urdlnapravlenie!=2&&pp.urdlnapravlenie!=5)return;}else{
                    if(pp.urdlnapravlenie!=naprav-3&&pp.urdlnapravlenie!=naprav-4)return;}}
                        pp.bolt.startBolt(pp.centx, pp.centy+5,naprav);
                    //если изучен скилл
                    if(pp.numstoskils.containsKey(1))pp.sendMsg("2/12/"+pp.id+"/"+naprav+"/1",true);
                    else pp.sendMsg("2/12/"+pp.id+"/"+naprav,true);}
                    break;
                    //личное сообщение
                case 17:
                    if(nametoPlayer.containsKey(str[2])){
                    NettyServerHandler.sendMsgClient("30/2/"+pp.name+"/"+str[3],nametoPlayer.get(str[2]).idchanel);
                    NettyServerHandler.sendMsgClient("30/0",pp.idchanel);
                    }
                    else {
                        ms.sendMessage(new MsgIsNamePlayer(getAddress(), ms.getAddressService().getAddressAS(),pp.idchanel,new LichkaMessage(pp.name,str[2],str[3],false)));
                    }
                    break;
                    //klans
                case 18:
                    sklans.msgFromPlayer(pp,str);
                    break;
                    //obmen
                case 19:
                    systemobmen.msgForKlient(str,pp);
                    break;
                    //system skils
                case 20:
                    systemskils.getPlayerMsg(pp,Integer.parseInt(str[2]),str.length>3?Integer.parseInt(str[3]):0);
                    break;
                    //zapros podtverzhdenie vstuplenie v klan
                case 21:
                    if(Integer.parseInt(str[2])==0){
                    Klan klan2=sklans.klans.get(str[3]);
                        if(klan2!=null){
                            klan2.addplayers.add(pp.name);
                    if(nametoPlayer.containsKey(klan2.getNameOsnovatel())){
                        NettyServerHandler.sendMsgClient("30/2/"+pp.name+"/игрок с ником "+pp.name+" хочет вступить в вашу группировку./0",nametoPlayer.get(klan2.getNameOsnovatel()).idchanel);
                        NettyServerHandler.sendMsgClient("30/0",pp.idchanel);
                    }
                    else {
                        ms.sendMessage(new MsgIsNamePlayer(getAddress(), ms.getAddressService().getAddressAS(),pp.idchanel,new LichkaMessage(pp.name,klan2.getNameOsnovatel(),"игрок с ником "+pp.name+" хочет вступить в вашу группировку.",true)));
                    }}}
                    //podtverzhdenie
                    if(Integer.parseInt(str[2])==1){
                        Klan klan2=sklans.klans.get(str[3]);
                        //если клан существует и игрок основатель
                        if(klan2!=null&&klan2.getNameOsnovatel().equals(pp.name)){
                            System.out.println("klan est");
                            //если имя есть в списке желающих вступить
                        if(klan2.addplayers.contains(str[4])){
                            System.out.println("igrok est v sps zhelauchih");
                            //если желающий всткпить игрок онлайн
                        if(nametoPlayer.containsKey(str[4])){klan2.addPlayer(nametoPlayer.get(str[4]));
                            System.out.println("igrok v seti");}
                        //если не онлайн
                        else{sklans.zaprosnametoklan.put(str[4],klan2.nameklan);}
                        }
                        }
                    }
                    //otkaz
                    if(Integer.parseInt(str[2])==2){
                        Klan klan2=sklans.klans.get(str[3]);
                        //если клан существует и игрок основатель
                        if(klan2!=null&&klan2.getNameOsnovatel().equals(pp.name)){
                            //если имя есть в списке желающих вступить
                            if(klan2.addplayers.contains(str[4])){
                            klan2.addplayers.remove(str[4]);
                            }}
                    }
                    break;
                    //karandash
                case 22:
                    pp.sendMsg("2/14/"+pp.id,true);
                    break;
            }
        }
    }catch (Exception e){
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
    public void resultIsName(boolean isname,int idcanel,LichkaMessage lmsg){
        if(isname){
        if(playersmessage.containsKey(lmsg.to)){
            List<LichkaMessage> plmsg=playersmessage.get(lmsg.to);
            plmsg.add(lmsg);
            playersmessage.remove(lmsg.to);
            playersmessage.put(lmsg.to,plmsg);
        }
        else{
            List<LichkaMessage>newlist=new ArrayList<>();
            newlist.add(lmsg);
            playersmessage.put(lmsg.to,newlist);
        }
            NettyServerHandler.sendMsgClient("30/0",idcanel);}
        else NettyServerHandler.sendMsgClient("30/1",idcanel);
    }
    void adminSignal(String signal,Player admin){
        if(signal.equals("0vibros"))  stateworld.vibros.AdminStartVibros();
        if(signal.equals("0save"))  {saveAllUsers();NettyServerHandler.sendMsgClient("5/SERVER/Все игроки сохранены!!!",admin.idchanel);}
        if(signal.charAt(1) == 't'){teleport(admin,signal);return;}
    }
    // эксперементальный метод только для админа
    void teleport(Player player,String str){
String[]tsrt=str.split("-");
if(tsrt.length>3){
    try {
        int karta = Integer.parseInt(tsrt[1]);
        int tx = Integer.parseInt(tsrt[2]);
        int ty = Integer.parseInt(tsrt[3]);
        if (karta == player.idmap) {
            player.signal(4, new Teleport(player, tx, ty));
            player.sendMsg("1/" + Util.LIPUCHKA + "/" + 99999 + "/" + tx + "/" + ty + "\0" + "2/1/" + 99999 + "/" + player.id, true);
        } else {
            maps[player.idmap].removePlayer(player);
            player.napravlenie = 1;
            player.setPosition(tx, ty);
            player.oldx = player.x;
            player.oldy = player.y;
            maps[karta].addPlayer(player);
            NettyServerHandler.sendMsgClient("15/" + player.idmap + "/" + (int) player.x + "/" + (int) player.y, player.idchanel);
        }
     }catch(Exception e){
            return;
        }
        }
    }
    void teleportMap (Player player,int idmap, int x, int y){
        maps[player.idmap].removePlayer(player);
        player.napravlenie = 1;
        player.setPosition(x, y);
        player.oldx = player.x;
        player.oldy = player.y;
        maps[idmap].addPlayer(player);
        NettyServerHandler.sendMsgClient("15/" + player.idmap + "/" + (int) player.x + "/" + (int) player.y, player.idchanel);
    }
    //игрок может перескочить на нулевой карте с края на край исправленно на клиенте

    //метод перекидывающий игрока с карты на карту
    private void switchMap(Player player){
    float oldx=player.rectang.x;float oldy=player.rectang.y;
    //удаляем игрока со старой карты
    maps[player.idmap].removePlayer(player);
    player.napravlenie=1;
    //проверка может ли игрок переместиттся(куб 5х5) и определение куда переместится
    if(oldx>1580&&(player.idmap+1)%5!=0){
        player.idmap++;
        player.setPosition(20,oldy);
        player.oldx=player.x;
        player.oldy=player.y;
    }
    else {
        if (oldx < 10 && player.idmap != 0 && player.idmap % 5 != 0) {
            player.idmap--;
            player.setPosition(1535,oldy);
            player.oldx=player.x;
            player.oldy=player.y;
        }
    }
    if(oldy>1555&&player.idmap>4){
        player.idmap-=5;
        player.setPosition(oldx,30);
        player.oldx=player.x;
        player.oldy=player.y;}
    else{
    if(oldy<10&&player.idmap<15){
        player.idmap+=5;
        player.setPosition(oldx,1535);
        player.oldx=player.x;
        player.oldy=player.y;
    }}
       // исправлем баг с застреванием за границей карты при переходе из локации в локацию
        if(player.x>1535){player.x=1535;player.oldx=player.x;
            player.setPosition(player.x,player.y);}
        if(player.y>1535){player.y=1535;player.oldy=player.y;
            player.setPosition(player.x,player.y);}
        if(player.x<20){player.x=20;player.oldx=player.x;
            player.setPosition(player.x,player.y);}
        if(player.y<30){player.y=30;player.oldy=player.y;
            player.setPosition(player.x,player.y);}
    //добавляем игрока на новую карту
    maps[player.idmap].addPlayer(player);
    NettyServerHandler.sendMsgClient("15/"+player.idmap+"/"+(int)player.x+"/"+(int)player.y,player.idchanel);
}
    private String getStringQvest(Player player) {
        String qvest = null;
        Set<Integer> setkey = player.qvests.keySet();
        for (Integer key : setkey) {
            if (qvest != null) qvest += key + ":" + player.qvests.get(key) + ":";
            else qvest = key + ":" + player.qvests.get(key) + ":";
        }
        return qvest;
    }
    // метод сохраняет данные ВСЕХ игроков в базу
    // вызывается в случае ошибки или по спец команде от админа
void saveAllUsers(){
    for(Player p:NettyServerHandler.sessionIdToUserSession.values()){
        if(p.autorized){
            if (p.getState() == p.DEAD) {
                p.timedie = (int) (p.ldt - System.currentTimeMillis());
            } else p.timedie = 0;
            //в очередь на месседж систем добавляется колво сообщений равное кол ву игроков
            ms.sendMessage(new MsgSetPlayerState(getAddress(), ms.getAddressService().getAddressAS(), p.iddatabase, p.x, p.y, p.pmLife.life,
                    p.holod, p.golod, p.zhazda, p.radiation, p.krovotech, p.timedie,Util.getItemsSave(p.inventar), getStringQvest(p), p.uds,p.name,p.idmap,p.nameclan,p.exp,systemskils.saveHeroSkillstoBase(p)));
        }
    }
}
    @Override
    public Address getAddress() {
        return addres;
    }

    public MessageSystem getMessageSystem() {
        return ms;
    }

}
