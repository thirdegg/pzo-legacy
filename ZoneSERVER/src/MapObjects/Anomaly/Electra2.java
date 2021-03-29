package MapObjects.Anomaly;

import Gm.SpatialHashGrid;
import MapObjects.Artefakts.*;
import MapObjects.MapObject;
import Gm.StateWorld;
import MapObjects.Units.Player;
import Utils.AddMOparameters;
import Utils.Overlap;
import Utils.Util;
import Utils.rand;

import java.util.HashMap;
import java.util.List;

public class Electra2 extends Electra {
    boolean zarad2=true;
    float timezarad;
    Veter veter;
    Blood blood;
    List<AddMOparameters> listaddobject;
    long timeart;
    SpatialHashGrid grid;
    public Electra2(int x, int y, HashMap<Long, MapObject> mapobjects, List<AddMOparameters> listaddobject, SpatialHashGrid grid) {
        super(mapobjects);
        tip = Util.ELECTRA2;
        this.x = x;
        this.y = y;
        this.grid=grid;
        this.listaddobject=listaddobject;
        centx = x;
        centy = y;
        canbeattacked=true;

            int rnd= rand.getIntRnd(0,11);
            if(rnd>=4){
                rnd=rand.getIntRnd(0,3);
                if(rnd==0)veter=new Veter(x,y,this);
                if(rnd==1||rnd==2)veter=new Veter1(x,y,this);
                listaddobject.add(new AddMOparameters(veter,false,false,0));
            }

            rnd= rand.getIntRnd(0,11);
            if(rnd>=4){
            rnd=rand.getIntRnd(0,3);
            if(rnd==0)blood=new Blood(x,y,this);
            if(rnd==1||rnd==2)blood=new Blood1(x,y,this);
            listaddobject.add(new AddMOparameters(blood,false,false,0));
            }

    }
@Override
    public void run(float delta) {
        if (zarad) {
            for (MapObject  mo: mapobjects.values()) {
                if (mo.unit&&mo.canbeattacked && Overlap.pointPoint(mo.centx, mo.centy, centx, centy, radius)) {
                    if(mo!=this){
                    mo.ataked(id, StateWorld.rain?duron:uron,Util.ELLURON,true);
                    zarad = false;
                        sendMsgPlayers("2/1/" + id);
                    break;
                }}
            }
        } else {
            timezarad += delta * 10;
            if (timezarad > 10&&zarad2) {
                boolean bblood=false;
                for (MapObject mo : mapobjects.values()) {
                    if ((mo.unit||mo.tip==Util.ELECTRA2)&&mo.canbeattacked && Overlap.pointPoint(mo.centx, mo.centy, centx, centy, 100)) {
                        if(mo!=this)mo.ataked(id,StateWorld.rain?duron+10:uron+10,Util.ELLURON,true);
                    }
                    if(!bblood&&blood!=null&&!blood.clientvisible){
                            if(mo.tip==Util.KISOBLAKO){
                                if(Overlap.pointPoint(centx,centy,mo.centx,mo.centy,mo.radius)){
                                    blood.clientvisible=true;
                                    sendMsgPlayers("1/" +blood.tip + "/" + blood.id + "/" + (int) x + "/" + (int) y);bblood=true;}}
                    }}
                zarad2=false;
            }

                if (timezarad > 70) {
                zarad = true;
                    zarad2=true;
                timezarad = 0;
                }
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
                sendMsgPlayers("2/1/" + id);}
                break;
            case 7:
                if(veter==null){
                    int rnd= rand.getIntRnd(0,11);
                    if(rnd>=4){
                        rnd=rand.getIntRnd(0,3);
                        if(rnd==0)veter=new Veter(x,y,this);
                        if(rnd==1||rnd==2)veter=new Veter1(x,y,this);
                        listaddobject.add(new AddMOparameters(veter,false,false,0));
                    }
                }
                if(blood==null){
                    int rnd= rand.getIntRnd(0,11);
                       if(rnd>=4){
                           rnd=rand.getIntRnd(0,3);
                           if(rnd==0)blood=new Blood(x,y,this);
                           if(rnd==1||rnd==2)blood=new Blood1(x,y,this);
                    listaddobject.add(new AddMOparameters(blood,false,false,0));
                       }
                }
                break;
            case  8:
                long oid=(Long)object;
                if(veter!=null&&veter.id==oid)veter=null;
                if(blood!=null&&blood.id==oid)blood=null;
                break;
        }
    }
    //startVzriv ot electra2
    @Override
    public void ataked(long id, float kolvolife,int tipuron,boolean send) {
        if(zarad&&zarad2){sendMsgPlayers("2/1/" + this.id);
            zarad=false;
            if(veter!=null)timeart=System.currentTimeMillis()+1500;
        }
        //если аномалия попадает по аномалии во второй раз
        else{
        if(veter!=null&&System.currentTimeMillis()<timeart){
        veter.clientvisible=true;
        sendMsgPlayers("1/" +veter.tip + "/" + veter.id + "/" + (int) x + "/" + (int) y);
        }
        }
    }
    @Override
    public String getStateForClient() {
        if(!zarad&&zarad2)return "2/1/"+id+"/"+timezarad;
        return null;
    }
}
