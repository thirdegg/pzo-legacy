package Klans;

import MapObjects.Units.Player;
import Server.NettyServerHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 777 on 02.04.2017.
 */
public class Klan {
    public String nameklan;
    private List<String> plname;
  //  private List<String> rukovodstvo;
    List<Player>onlineplayers;
    private List<String>remplayers;
    public List<String>addplayers;
    private String osnovname;

    public Klan(String name, String osnovpname) {
        this.nameklan = name;
        plname = new ArrayList<>();
        onlineplayers = new ArrayList<>();
        remplayers = new ArrayList<>();
        addplayers = new ArrayList<>();
        osnovname = osnovpname;
        plname.add(osnovpname);
    }

    public boolean addPlayer(Player pl) {
        if(pl.nameclan!=null){NettyServerHandler.sendMsgClient("24/3",pl.idchanel);
            return false;}
        if (plname.size() < 25) {
            for (String s : plname) {
                if (s.equals(pl.name)) return false;
            }
            plname.add(pl.name);
            onlineplayers.add(pl);
            pl.nameclan = nameklan;
            addplayers.remove(pl);
            pl.sendMsg("2/13/"+pl.id+"/1/"+pl.nameclan,true);
            NettyServerHandler.sendMsgClient("24/9",pl.idchanel);
            return true;
        }
        return false;
    }

    public void remPlayer(Player pl) {
     /*   for (String s : rukovodstvo) {
            if (s.equals(namer)) {
                if (plname.remove(pl.name)) pl.nameclan = null;
            }
        }*/
        if (plname.remove(pl.name)){ pl.nameclan = null;
            pl.sendMsg("2/13/"+pl.id+"/2",true);
            onlineplayers.remove(pl);
            NettyServerHandler.sendMsgClient("24/10",pl.idchanel);}
    }
public boolean adminRemPlayer(String nameadmin,String remplname){
        if(nameadmin.equals(osnovname)){
            for(Player onpl:onlineplayers){
                if(onpl.name.equals(remplname)){remPlayer(onpl);return true;}
            }
            //если игрок не в сети добавляем в лист для удаления
            plname.remove(remplname);
            remplayers.add(remplname);
            return true;
        }
        return false;
}
    public String getNameOsnovatel() {
        return osnovname;
    }

   /* public Object[] getNamesRukovodstvo() {
        return rukovodstvo.toArray();
    }*/
    public Object[] getNamesPgroup() {
        return plname.toArray();
    }

    public boolean isRemPlayer(String plname){
        for(String remname:remplayers){
            if(plname.equals(remname)){remplayers.remove(plname);return  true;}
        }
        return false;
    }
 /*   public boolean setRukovodstvo(String nameo, String namer) {
        if (osnovname.equals(nameo) && rukovodstvo.size() < 5) {
            for (String s : rukovodstvo) {
                if (s.equals(namer)) return false;
            }
            for(String s2:plname){
            if(namer.equals(s2)){plname.remove(namer);rukovodstvo.add(namer);return true;}
            }
        }
        return false;
    }*/
    // idname in player class error
    public void rospusk(){
        for (String name:plname){
            for(Player pl:NettyServerHandler.sessionIdToUserSession.values()){
                if(name.equals(pl.name)){pl.nameclan=null;
                NettyServerHandler.sendMsgClient("24/10",pl.idchanel);break;}
            }
        }
    plname.clear();
    //rukovodstvo.clear();
    }
    public void addOnlinePlayer(Player pl){onlineplayers.add(pl);}
    public void remOnlinePlayer(Player pl){onlineplayers.remove(pl);}
public String getInfoKlan(){
        StringBuilder sbi=new StringBuilder("24/7/"+nameklan+"/"+getNameOsnovatel()+"/");
        for (Object namepl:getNamesPgroup()){
        sbi.append(namepl+":");}
        return sbi.toString();
}
}
