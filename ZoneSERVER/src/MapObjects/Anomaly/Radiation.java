package MapObjects.Anomaly;

import MapObjects.MapObject;
import MapObjects.Units.Player;
import Server.NettyServerHandler;
import Utils.Overlap;
import Utils.Util;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 777 on 13.05.2017.
 */
public class Radiation extends MapObject {
    // список игроков в радиусе видимости аномалии
    List<Player> players = new ArrayList<>();
    List<Long> idinradius = new ArrayList<>();
    private float radtime,strong;
    public Radiation(int x, int y, int radius,float strong) {
        // создаем новый id
        id = generatorId.incrementAndGet();
        this.x = x;
        this.y = y;
        centx = x;
        centy = y;
        tip = Util.RADIATION;
        this.radius = radius;
        raddclient=radius+100;
        clientvisible=false;
        this.strong=strong;
    }
    @Override
    public void run(float delta){
        radtime += 10 * delta;
        if(radtime>10){
        for(Player pl:players){
            if(pl.state!=pl.DEAD&&pl.state!=pl.VDOME){
            if(!idinradius.contains(pl.id)){
                if(Overlap.pointPoint(pl.centx,pl.centy,centx,centy,radius)){
                    idinradius.add(pl.id);
                    NettyServerHandler.sendMsgClient("1/24/"+id+"/"+(int)x+"/"+(int)y+"/"+radius+"/"+strong,pl.idchanel);
                }
            }
            else{
                pl.radiation+=strong;
                if(!Overlap.pointPoint(pl.centx,pl.centy,centx,centy,radius))idinradius.remove(pl.id);
            }
        }}
        radtime=0;
        }
    }
    @Override
    public String addedAndDop(Player pl, String str) {
        pl.mapobjects.put(id, this);
        players.add(pl);
        //если герой еще не рядом с какой нибудь радиацией и у него есть дозимитер добавить переменную в класс игрока
      //  NettyServerHandler.sendMsgClient("12345",pl.idchanel);
        return str;
    }
    //1 stop ataked 2 rem playuer 3 you kill me 5 activ anomaly
    @Override
    public void signal(int tip,Object object) {
        switch (tip) {
            // логика удаления объектов из радиуса видимости идет в классе игрока
            // когда игрок выходит из радиуса видимости он посылает сюда сигнал - типа удали меня отовсюду ты меня уже не видиш
            case 2:
                long idv=(Long)object;
                for (Player pl : players) {
                    if (pl.id == idv) {
                        players.remove(pl);
                        break;
                    }
                }
                break;
        }
    }
}
