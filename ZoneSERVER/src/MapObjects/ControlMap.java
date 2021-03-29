package MapObjects;

import MapObjects.Units.Player;
import Server.NettyServerHandler;
import Utils.Overlap;
import Utils.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 777 on 23.05.2017.
 */
public class ControlMap extends MapObject {
    public String kontrolklanname="никому не принадлежит";
    public List<Player> players = new ArrayList<>();
    public List<Player> playersuflaga = new ArrayList<>();
    public HashMap<String,Integer> klanstoscore = new HashMap<>();
    public boolean zahvat;
    private float skipframe;
    public int idmap;
    public ControlMap(int xx, int yy,int idmap) {
        x = xx;
        y = yy;
        centx=x;
        centy=y;
        tip = Util.CONTROLMAP;
        id = generatorId.incrementAndGet();
        this.idmap=idmap;
    }
    @Override
    public void run(float delta){
        if(zahvat){
            skipframe+=delta*10;
            if(skipframe>10){
                if(klanstoscore.size()<4){
        for (Player pl:players){
            if(pl.nameclan!=null&&pl.canbeattacked&&Overlap.pointPoint(pl.x,pl.y,centx,centy,200)&&!playersuflaga.contains(pl)){
                playersuflaga.add(pl);if(!klanstoscore.containsKey(pl.nameclan))klanstoscore.put(pl.nameclan,5);}
        }}
            Iterator<Player> r=playersuflaga.iterator();
            while (r.hasNext()) {
                Player pl=r.next();
                if(pl.nameclan==null||!pl.canbeattacked){r.remove();break;}
                int score=klanstoscore.get(pl.nameclan)+3;
                klanstoscore.put(pl.nameclan,score);
                if(!Overlap.pointPoint(pl.x,pl.y,centx,centy,205))
                    r.remove();
            }
           skipframe=0;
        }}
    }
   public void endZahvat(){
        zahvat=false;
       int maxscore=0;
       for(Integer score:klanstoscore.values()){
           if(score>maxscore)maxscore=score;
       }
       for(String nameclan:klanstoscore.keySet()){
           if(maxscore==klanstoscore.get(nameclan)){
           kontrolklanname=nameclan;break;
           }
       }
       for (Player pl:players) {
           NettyServerHandler.sendMsgClient("2/2/"+id+"/"+kontrolklanname, pl.idchanel);
       }
       playersuflaga.clear();
       klanstoscore.clear();
    }
    @Override
    public String addedAndDop(Player pl,String str){
        pl.mapobjects.put(id,this);
        players.add(pl);
        // доп параметры игрока
        str+="/"+zahvat+"/"+kontrolklanname+"/";
        if(zahvat){
        for(String nameclan:klanstoscore.keySet()){
         str+=nameclan+"/"+klanstoscore.get(nameclan)+"/";
        }}
        return str;
    }

    //1 stop ataked 2 rem playuer 3 you kill me 5 activ anomaly
    @Override
    public void signal(int tip,Object object) {
        switch (tip) {
            // логика удаления объектов из радиуса видимости идет в классе игрока
            // когда игрок выходит из радиуса видимости он посылает сюда сигнал - типа удали меня отовсюду ты меня уже не видиш
            case 2:
                long idv = (Long) object;
                for (Player pl : players) {
                    if (pl.id == idv) {
                        players.remove(pl);
                        playersuflaga.remove(pl);
                        break;
                    }}
                break;
            case 3:
                zahvat=true;
                for (Player pl:players) {
                    NettyServerHandler.sendMsgClient("2/3/"+id, pl.idchanel);
                }
                break;
            case 4:
                endZahvat();
                break;
        }}
}
