package MapObjects.Anomaly;

import MapObjects.Artefakts.Iskra;
import MapObjects.MapObject;
import Gm.StateWorld;
import MapObjects.Units.Player;
import Utils.AddMOparameters;
import Utils.Overlap;
import Utils.Util;
import Utils.rand;

import java.util.HashMap;
import java.util.List;

public class Electra1 extends Electra {
    float timezarad;
    Iskra iskra;
    List<AddMOparameters> listaddobject;
    boolean esayvers;
    long timelifeanomaly;
    int skipframe;
    public Electra1(int x, int y, HashMap<Long, MapObject> mapobjects, List<AddMOparameters> listaddobject,boolean esayvers) {
        super(mapobjects);
        tip = Util.ELECTRA;
        this.x = x;
        this.y = y;
        centx = x;
        centy = y;
        this.listaddobject=listaddobject;
        this.esayvers=esayvers;
        if(esayvers){
            for (MapObject mo:mapobjects.values()){
            if(mo.tip==Util.IMPULS){if(Overlap.pointPoint(centx,centy,mo.centx,mo.centy,radius+mo.radius)){
            remov=true;mo.signal(9,null);
            }}
            }
            timelifeanomaly=System.currentTimeMillis()+360000;}

        if(!esayvers){
            int rnd= rand.getIntRnd(0,11);
            if(rnd>4){
                iskra=new Iskra(x,y,this);
                listaddobject.add(new AddMOparameters(iskra,false,false,0));}
        }
    }

    public void run(float delta) {
        if (zarad) {
            for (MapObject  mo: mapobjects.values()) {
                if (mo.unit&&mo.canbeattacked && Overlap.pointPoint(mo.centx, mo.centy, centx, centy, radius)) {
                    mo.ataked(id,StateWorld.rain?duron:uron,Util.ELLURON,true);
                    zarad = false;
                    String msg="2/1/" + id;
                    if(iskra!=null&&!iskra.clientvisible){iskra.clientvisible=true;
                        msg+="\0"+"1/" +iskra.tip + "/" + iskra.id + "/" + (int) x + "/" + (int) y;}
                    sendMsgPlayers(msg);
                    break;
                }
            }
        } else {
            timezarad += delta * 10;
            if (timezarad > 45) {
                zarad = true;
                timezarad = 0;
            }
        }
        //если аномалия создана игроком
        if(esayvers){
            skipframe++;
            if(skipframe>30){
               if(System.currentTimeMillis()>timelifeanomaly)remov=true;
        skipframe=0;}
        }
    }
    @Override
    public void signal(int tip,Object object) {
        switch (tip) {
            case 2:
                long idv=(Long)object;
                for(Player pl:players){
                    if(pl.id==idv){players.remove(pl);
                        break;}
                }
                break;
            // start anomaly
            case 5:
                if(zarad){
                zarad = false;
                String msg="2/1/" + id;
                if(iskra!=null&&!iskra.clientvisible){iskra.clientvisible=true;
                   msg+="\0"+"1/" +iskra.tip + "/" + iskra.id + "/" + (int) x + "/" + (int) y;}
                sendMsgPlayers(msg);}
                break;
            case 7:
                if(!esayvers&&iskra==null){
                    int rnd= rand.getIntRnd(0,11);
                    if(rnd>4){
                    iskra=new Iskra(x,y,this);
                    listaddobject.add(new AddMOparameters(iskra,false,false,0));}
                }
                break;
                //арт подобрали
            case 8:
                iskra=null;
                break;
        }
    }
}
