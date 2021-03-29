package Anomaly;

import GameWorld.MapObject;
import Modules.Teleport;
import util.Util;

import java.util.HashMap;

/**
 * Created by 777 on 03.02.2017.
 */
public class Lipuchka extends MapObject {
    MapObject atakobject;
    HashMap<Long, MapObject> mapobjects;
    public Lipuchka(int xx, int yy, long id,HashMap<Long, MapObject> mapobjects){
        super("kisoblako.png", xx - 3, yy - 10, 50,null,false);
        this.id = id;
        centx = xx;
        centy = yy;
        tip= Util.LIPUCHKA;
        width = this.getRegionWidth();
        height = this.getRegionHeight();
        this.mapobjects=mapobjects;
        visibl=false;
    }
    @Override
    public void setState(int newstate, String[] str) {
        atakobject = mapobjects.get(Long.parseLong(str[3]));
        if (atakobject != null) {
            atakobject.signal(4,new Teleport(atakobject,centx,centy));
        }
    }
    }