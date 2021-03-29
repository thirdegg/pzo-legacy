package MapObjects;

import MapObjects.Units.Player;
import Utils.Overlap;
import Utils.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 777 on 01.03.2017.
 */
public class Vkoster extends MapObject{
    public List<Player> players = new ArrayList<>();
    public List<Player> playersukostra = new ArrayList<>();
    int radius = 50;
    int propk;

    public Vkoster(int xx, int yy) {
        x = xx;
        y = yy;
        centx=x;
        centy=y;
        tip = Util.VKOSTER;
        id = generatorId.incrementAndGet();
    }
    @Override
    public void run(float delta) {
            propk++;
            if(propk==4){
                propk=0;
                for (Player pl : players) {
                    if (!pl.ukostra) {
                        if (Overlap.pointPoint(pl.x, pl.y, x, y, radius)) {
                            pl.ukostra = true;
                            pl.holod += 5;
                            playersukostra.add(pl);
                        }
                    }
                }
                for(Player pl:playersukostra){
                    if (!Overlap.pointPoint(pl.x, pl.y, x, y, radius)) {
                        pl.ukostra = false;
                        pl.holod -= 5;
                        playersukostra.remove(pl);
                        break;
                    }
                }
            }
    }
    @Override
    public String addedAndDop(Player pl,String str){
        pl.mapobjects.put(id,this);
        players.add(pl);
        return str;
    }
    //1 stop ataked 2 rem playuer 3 you kill me
    @Override
    public void signal(int tip,Object object){
        switch(tip){
            case 2:
                long idv=(Long)object;
                for(Player pl:players){
                    if(pl.id==idv){
                        players.remove(pl);
                        if(pl.ukostra){
                            pl.ukostra = false;
                            pl.holod -= 5;
                        }
                        break;
                    }
                }
                break;
        }
    }
}

