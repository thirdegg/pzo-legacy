package InventItem.core;

import InventItem.core.Item;
import MapObjects.Korob;
import Maps.WMap;
import Server.NettyServerHandler;
import MapObjects.Units.Player;
import Utils.AddMOparameters;
import Utils.rand;

import java.util.*;

public class Inventar {
    public HashMap<Integer,Item> item = new HashMap<>();
    public HashMap<Integer,Item> odeto = new HashMap<>();
    public int many;
    Player player;
    public boolean canuse=true;
    public float timecanuse;
    public int colvoslotov=6;
    public boolean skillberezhlivost;
    public Inventar(Player player){
        this.player=player;
    }
// использовать предмет на себя
    public void useObject(WMap map,int id) {
        if (player.state!=player.DEAD) {
            Item it = item.get(id);
            if (canuse&&it != null){
                it.use(map,player);if(it.remuse)item.remove(it.id);return;
            }
            else {
                it=odeto.get(id);
                if(it!=null){
                    it.use(map,player);if(it.remuse)odeto.remove(it.id);
                }
            }
        }
    }
    // использовать предмет кого то другого
    public void useObject(WMap map,int idit,long idmo) {
        Player pl=null;
        if(idmo==player.id)pl=player;
        else
        pl=(Player) player.mapobjects.get(idmo);
        if(pl!=null){
        if (player.state!=player.DEAD) {
            Item it = item.get(idit);
            if (it != null){
                it.use(map,pl);if(it.remuse)item.remove(it.id);return;
            }
            else {
                it=odeto.get(idit);
                if(it!=null){
                    it.use(map,pl);if(it.remuse)odeto.remove(it.id);
                }
            }
        }
        }
    }

   public void setItem(int id) {
       if(canuse&&odeto.size()<colvoslotov){
       Item it = item.get(id);
       if(it!=null){
        // если броня или оружие
        if(it.useslot==1||it.useslot==2){
            item.remove(it.id);
            // проверяем не одел ли этот тип
        for (Item i : odeto.values()) {
            if (i.useslot == it.useslot) {
                odeto.remove(i.id);
                item.put(i.id,i);
                i.snyat(player,true);
                break;
            }}
            it.odet(player,true);
            odeto.put(it.id,it);
        }
        else{
            item.remove(it.id);
            it.odet(player,true);
            odeto.put(it.id,it);
        }}}
    }

    public boolean getMesto() {
        if (item.size() < 20) return true;
        return false;
    }

    public String getItemsForPlayer() {
        String str = many + "/";
        String sit=null;
        for (Item it : item.values()) {
            if(sit==null)sit=it.id+"-"+it.tipitem+"-"+it.dopintcolvo;
            else sit+="-"+it.id+"-"+it.tipitem+"-"+it.dopintcolvo;
        }
        if(sit!=null)str+=sit;
        str+="/";
        String sod=null;
        for (Item it : odeto.values()) {
            if(sod==null)sod=it.id+"-"+it.tipitem+"-"+it.dopintcolvo;
            else sod+="-"+it.id+"-"+it.tipitem+"-"+it.dopintcolvo;
        }
        if(sod!=null)str+=sod;
        return str;
    }

    public void Snyat(int id) {
        if(getMesto()&&player.state!=player.DEAD){
            Item it=odeto.get(id);
            if(it!=null){
                odeto.remove(it.id);
                item.put(it.id,it);
                it.snyat(player,true);
            }
        }
    }
    public void throwItemAll(List<AddMOparameters>amop){
        if(item.size()>1) {
            int it1 = rand.getIntRnd(1, item.size() - 1);
            int it2 = rand.getIntRnd(1, item.size() - 1);
            int it3 = rand.getIntRnd(1, item.size() - 1);
            int it4 = rand.getIntRnd(1, item.size() - 1);
            int it5 = rand.getIntRnd(1, item.size() - 1);
            Iterator<Item> itit = item.values().iterator();
            int itter = 0;
            int naprav = 1;
            String senmsg = "20";
            while (itit.hasNext()) {
                Item it = itit.next();
                itter++;
                if (itter == it1 || itter == it2 || itter == it3 || itter == it4 || itter == it5) {
                    if (it.vibrosit) {
                        naprav++;
                        senmsg += "/" + it.id;
                        Korob korob = new Korob(player.x, player.y, it);
                        amop.add(new AddMOparameters(korob,true,true,naprav));
                        if (naprav == 5) naprav = 1;
                        itit.remove();
                        if(skillberezhlivost&&naprav==3)break;
                    }
                }
            }
            NettyServerHandler.sendMsgClient(senmsg, player.idchanel);
        }
    }

    public void throwItem(List<AddMOparameters>amop,int iditem,int napravlenie){
        Item it=item.remove(iditem);
        if(it!=null&&it.vibrosit){
            Korob korob=new Korob(player.x,player.y,it);
            amop.add(new AddMOparameters(korob,true,true,napravlenie));
        }
    }
    public void addItem(Item it){
        String s="19/";
        item.put(it.id,it);
        s+=it.tipitem+"-"+it.id;
        if(it.dopintcolvo != 0){
            s+="/"+it.dopintcolvo;
        }
        NettyServerHandler.sendMsgClient(s,player.idchanel);
    }

    public void addMany(int pmany){
        String s="19/m/";
        many+=pmany;
        s+=many;
        NettyServerHandler.sendMsgClient(s,player.idchanel);
    }
public int getTipOdeto(){
    for (Item i : odeto.values()) {
        if (i.useslot == 1) {
            return i.tipitem;
        }
    }
    return -1;
}
    public Item isItem(int tipitem, boolean bodeto) {
    if(!bodeto){
        for (Item it : item.values()) {
            if (it.tipitem == tipitem) return it;
        }}
        else{
        for (Item it : odeto.values()) {
            if (it.tipitem == tipitem) return it;
        }}
        return null;
    }
}
