package Maps;

import Base.MessageSystem;
import Gm.WorldRec;
import MapObjects.Anomaly.Radiation;
import MapObjects.MapObject;
import MapObjects.Units.Player;
import Server.NettyServerHandler;
import Utils.*;

import java.util.List;

/**
 * Created by 777 on 25.06.2017.
 */
public class WorldMap19 extends  WMap{
    public WorldMap19() {
        super();
        //map 1600x1600 world 1575x1565
        idmap=19;
        String mapname="map19.tmx";
        worldrec = new WorldRec(mapname+".txt");
        LoadObjects.loadobjects(this,mapname+"sobject.txt");
        List<Rectang> rec = worldrec.getRec();
        for (Rectang r : rec) {
            grid.insertStaticObject(r);
        }
        for(int i=0;i<7;i++){
            int radiuss= rand.getIntRnd(50,80);
            Radiation rad=new Radiation(rand.getIntRnd(50,1500), rand.getIntRnd(50,1500),radiuss,0.6f);
            mapobjects.put(rad.id,rad);
        }
        createobjects.add(new ObjectParametr(Util.ARTVETER2,Util.PPARTEFAKT,3,0,0,0,null));
        createobjects.add(new ObjectParametr(Util.ARTBLOOD2,Util.PPARTEFAKT,3,0,0,0,null));
        createobjects.add(new ObjectParametr(Util.ARTOBEREG2,Util.PPARTEFAKT,2,0,0,0,null));
    }
    @Override
    public void run(float delta, MessageSystem ms) {
        addObjectsToPlayer();
        dell();
        spawnObjects();
        addMapObjectOfamo();
        for (MapObject mo : mapobjects.values()) {
            mo.run(delta);
        }
    }
    @Override
    public void hitObject(MapObject object) {
        boolean out = false;
        for (Rectang r2 : worldrec.getRec()) {
            if (object.rectang!=null? Overlap.overlapRectang(object.rectang, r2):Overlap.overlapPointRectang(object.x,object.y,r2)) {
                object.x = rand.getIntRnd(50,1500);
                object.y = rand.getIntRnd(50,1500);
                if(object.rectang!=null) {
                    object.rectang.x = object.x + object.plasrecx;
                    object.rectang.y = object.y + object.plasrecy;
                }
                else {object.centx=object.x;object.centy=object.y;
                }
                out = true;
                break;
            }
        }
        if (out) hitObject(object);
    }
    @Override
    public void signal(int tip){
        switch(tip){
            //add zombi
            case 1:
                for(ObjectParametr op:createobjects){
                    if(op.tip==Util.ZOMBI){LoadObjects.getZombi(this,op.kolvo);
                        break;}
                }
                break;
            //dellzombi
            case 2:
                for(MapObject mo:mapobjects.values()){
                    if(mo.tip== Util.ZOMBI)mo.remov=true;
                }
                break;
            //end vibros addArtefacts
            case 3:
                if(Util.podtippodschet(mapobjects.values(),Util.PPARTEFAKT)<20){
                    for (ObjectParametr op:createobjects){
                        if(op.podtip==Util.PPARTEFAKT){LoadObjects.createObjectKolvo(this,op.tip,op.kolvo);}
                    }}
                for(MapObject mo:mapobjects.values()){
                    if(mo.podtip==Util.PPANOMALY)mo.signal(7,null);
                }
                break;
            case 4:
                for(Player pl:players){
                    NettyServerHandler.sendMsgClient("7", pl.idchanel);
                }
                break;
        }
    }
}
