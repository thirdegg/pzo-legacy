package MapObjects.Anomaly;

import MapObjects.MapObject;
import MapObjects.Units.Player;
import Modules.Teleport;
import Utils.Overlap;
import Utils.Util;
import Utils.rand;

import java.util.ArrayList;
import java.util.List;

public class Lipuchka extends MapObject{
    List<Player> players=new ArrayList<>();
    Player atakobject;
    int state,napravout;
    public Lipuchka(int x, int y){
        id = generatorId.incrementAndGet();
        radius=25;
        this.x = x;
        this.y = y;
        centx = x;
        centy = y;
        tip = Util.LIPUCHKA;
        podtip=Util.PPANOMALY;
        napravout= rand.getIntRnd(2,4);
        clientvisible=false;
    }
    @Override
    public void run(float delta) {
        switch(state){
            case 0:
            for(Player pl:players){
                if (pl.canbeattacked && Overlap.pointPoint(pl.centx, pl.centy, centx, centy, radius)) {
                    atakobject=pl;state=1;break;
                }
            }
            break;
            case 1:
                if(atakobject==null||atakobject.state==atakobject.DEAD||atakobject.state==atakobject.VDOME){atakobject=null;state=0;return;}
                if(!Overlap.pointPoint(atakobject.centx, atakobject.centy, centx, centy,150)){
                    if(atakobject.urdlnapravlenie==napravout){
                        state=0;atakobject=null;napravout= rand.getIntRnd(2,4);
                    }else{
                    if(atakobject.state!=6){atakobject.signal(4,new Teleport(atakobject,centx,centy));
                    atakobject.sendMsg("1/" + tip + "/" + id + "/" + (int) x + "/" + (int) y+"\0"+"2/1/" + id+"/"+atakobject.id, true);}
                    state=0;atakobject=null;
                }}
                if(atakobject!=null&&atakobject.napravlenie==1){
                    if(atakobject.urdlnapravlenie==napravout){
                        state=0;atakobject=null;napravout= rand.getIntRnd(2,4);
                    }else{
                        if(!Overlap.pointPoint(atakobject.centx, atakobject.centy, centx, centy, radius)) {
                            if(atakobject.state!=6){
                            atakobject.signal(4,new Teleport(atakobject,centx,centy));
                            atakobject.sendMsg("1/" + tip + "/" + id + "/" + (int) x + "/" + (int) y+"\0"+"2/1/" + id+"/"+atakobject.id, true);}
                            state=0;atakobject=null;
                        }
                    }
                }
                break;
        }
    }
    @Override
    public String addedAndDop(Player pl,String str){
        pl.mapobjects.put(id,this);
        players.add(pl);
        //
        return str;
    }

    //1 stop ataked 2 rem playuer 3 you kill me
    @Override
    public void signal(int tip,Object object){
        switch(tip){
            case 2:
                long idv=(Long)object;
                for(Player pl:players){
                    if(pl.id==idv){players.remove(pl);
                        if(pl==atakobject)atakobject=null;
                        break;}
                }
                break;
        }
    }
}
