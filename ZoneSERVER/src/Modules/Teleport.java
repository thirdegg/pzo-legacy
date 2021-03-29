package Modules;

import Gm.SpatialHashGrid;
import MapObjects.MapObject;

/**
 * Created by 777 on 23.02.2017.
 */
public class Teleport extends Modifikator {
    float kx,ky;
    boolean t;
    public Teleport(MapObject mo,float x,float y){
        this.mo=mo;
        kx=x;ky=y;
        tip=2;
    }
    @Override
    public void run(float delta, SpatialHashGrid grid){
        mtime+=delta*10;
        if(mtime>7){
            if(!t){
            mtime=0;t=true;
                mo.setPosition(kx,ky);}
            else{
                mo.signal(5,tip);
            }
    }
    }
    public String getState(){
    return "21/"+tip+"/"+mo.id+"/"+mtime+"/"+(t?t:t+"/"+kx+"/"+ky);
    }
}
