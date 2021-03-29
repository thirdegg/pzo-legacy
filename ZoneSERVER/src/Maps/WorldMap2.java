package Maps;

import MapObjects.MapObject;
import Base.MessageSystem;
import Gm.WorldRec;
import MapObjects.Units.Npc;
import MapObjects.Units.Player;
import MapObjects.Units.Zombi;
import Server.NettyServerHandler;
import Utils.*;

import java.util.List;

/**
 * Created by 777 on 11.12.2016.
 */
public class WorldMap2 extends  WMap{
    private boolean sendnapadenie = true;
    private long timenapadeniezombi;
    public WorldMap2() {
        super();
        //map 1600x1600 world 1575x1565
        idmap=2;
        String mapname="map2.tmx";
        worldrec = new WorldRec(mapname+".txt");
        LoadObjects.loadobjects(this,mapname+"sobject.txt");
        Npc npc =new Npc(906,1106,grid,mapobjects,1,"Кошмар",4,worldrec.getRec());
        mapobjects.put(npc.id,npc);
        Npc npc2 =new Npc(878,1076,grid,mapobjects,2,"Вымпел",3,worldrec.getRec());
        mapobjects.put(npc2.id,npc2);
        List<Rectang> rec = worldrec.getRec();
        for (Rectang r : rec) {
            grid.insertStaticObject(r);
        }
        timenapadeniezombi = System.currentTimeMillis() + rand.getIntRnd(100000, 50000);
        createobjects.add(new ObjectParametr(Util.ARTVETER2,Util.PPARTEFAKT,3,0,0,0,null));
        createobjects.add(new ObjectParametr(Util.ARTHEART2,Util.PPARTEFAKT,3,0,0,0,null));
        createobjects.add(new ObjectParametr(Util.ARTBLOOD2,Util.PPARTEFAKT,3,0,0,0,null));
        createobjects.add(new ObjectParametr(Util.ARTOBEREG2,Util.PPARTEFAKT,2,0,0,0,null));
    }
@Override
    public void run(float delta, MessageSystem ms) {
        addObjectsToPlayer();
        dell();
        napadenieZombi();
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
            if (object.rectang!=null?Overlap.overlapRectang(object.rectang, r2):Overlap.overlapPointRectang(object.x,object.y,r2)) {
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
    void napadenieZombi() {
        if (sendnapadenie && timenapadeniezombi - System.currentTimeMillis() < 20000) {
            if(Util.podschet(mapobjects.values(),Util.ZOMBI)<4){
                sendMsg("5/SERVER/Внимание! К лагерю сталкеров с востока приближается волна зомби.");
                sendnapadenie = false;}else{
                timenapadeniezombi = System.currentTimeMillis() + rand.getIntRnd(350000, 400000);}
        }
        if (System.currentTimeMillis() > timenapadeniezombi) {
            for (int i = 0; i < 12; i++) {
                Zombi zombi = new Zombi(rand.getIntRnd(600, 100), rand.getIntRnd(484, 110),grid,mapobjects,addmapobjects);
                mapobjects.put(zombi.id, zombi);
                zombi.setState(zombi.DVIGCELL);
                zombi.dvigcellx = (int) zombi.x ;
                zombi.dvigcelly = rand.getIntRnd(1000,50);
                zombi.detectNapravlenie(zombi.dvigcellx, zombi.dvigcelly);
            }
            timenapadeniezombi = System.currentTimeMillis() + rand.getIntRnd(300000, 400000);
            sendnapadenie = true;
        }
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
                    //npc
                for(MapObject mo: mapobjects.values()){
                    mo.signal(7,null);
                }
                break;
                // start vibros
            case 4:
for(MapObject mo: mapobjects.values()){
    mo.signal(6,null);
}
                for(Player pl:players){
                    NettyServerHandler.sendMsgClient("7", pl.idchanel);
                }
                break;
        }
    }
}
