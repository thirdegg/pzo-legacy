package Maps;

import MapObjects.Anomaly.Impuls2;
import MapObjects.MapObject;
import Base.MessageSystem;
import Gm.WorldRec;
import MapObjects.Units.Friger;
import MapObjects.Units.Kaban;
import MapObjects.Units.Player;
import Server.NettyServerHandler;
import Utils.*;

import java.util.List;

public class WorldMap0 extends WMap{

    public WorldMap0() {
        super();
        //map 1600x1600 world 1575x1565
        idmap=0;
        String mapname="map0.tmx";
        // загружаем список прямоугольнико локации
        worldrec = new WorldRec(mapname+".txt");
        // создаем все объекты карты (аномалии мутанты артефакты и тд)
     //  LoadObjects.loadobjects(this,mapname+"sobject.txt");
        // добавляем прямоуггольники в систему поиска потенциальных столкновений
        List<Rectang> rec = worldrec.getRec();
        for (Rectang r : rec) {
            grid.insertStaticObject(r);
        }
        createobjects.add(new ObjectParametr(Util.ARTVETER2,Util.PPARTEFAKT,3,0,0,0,null));
        createobjects.add(new ObjectParametr(Util.ARTHEART2,Util.PPARTEFAKT,5,0,0,0,null));
        createobjects.add(new ObjectParametr(Util.ARTOBEREG2,Util.PPARTEFAKT,2,0,0,0,null));

        Impuls2 imp=new Impuls2(200,900,mapobjects,0,addmapobjects,grid);
        mapobjects.put(imp.id,imp);
    }
    @Override
    public void run(float delta, MessageSystem ms) {
        //вызываем методы из суперкласса
        addObjectsToPlayer();
        dell();
        spawnObjects();
        addMapObjectOfamo();
        // обновляем логику каждого объекта на карте (у некоторых объектов нет логики их метод run пустой)
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
    @Override
    public void signal(int tip){
        switch(tip){
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
