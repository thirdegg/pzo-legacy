package MapObjects.Anomaly;

import MapObjects.MapObject;
import Server.NettyServerHandler;
import MapObjects.Units.Player;
import Utils.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Electra extends MapObject {
    List<Player> players=new ArrayList<>();
    HashMap<Long, MapObject> mapobjects;
    public int uron,duron;
   public boolean zarad;
    public Electra(HashMap<Long, MapObject> mapobjects){
        id = generatorId.incrementAndGet();
        this.mapobjects=mapobjects;
        podtip=Util.PPANOMALY;
        zarad=true;
        uron=30;
        radius=25;
        duron=40;
    }
    @Override
    public String addedAndDop(Player pl,String str){
        pl.mapobjects.put(id,this);
        players.add(pl);
        return str;
    }
  public void sendMsgPlayers(String msg) {
        for (Player unit : players) {
            NettyServerHandler.sendMsgClient(msg, unit.idchanel);
        }
    }
}
