package MapObjects.Anomaly;

import MapObjects.MapObject;
import Gm.StateWorld;
import MapObjects.Units.Player;
import Utils.Overlap;
import Utils.Util;

import java.util.HashMap;

public class DvigElectra1 extends Electra {
    private final int LEFT = 5, RIGHT = 3, UP = 2, DOWN = 4, UR = 6, RD = 7, DL = 8, LU = 9;
    public int napravlenie = 1;
    float timezarad;
    int  speed = 100;
    int ittendt = 0;
    int ndt[],ndt2[];
    int rasst;
    boolean nazad;
    float pcentx,pcenty;
    public DvigElectra1(int x, int y, int[] ndt,HashMap<Long, MapObject> mapobjects) {
        super(mapobjects);
        tip = Util.DVIGELECTRA;
        this.ndt = ndt;
        this.x = x;
        this.y = y;
        centx = x;
        centy = y;
        pcentx=centx;
        pcenty=centy;
        ndt2=new int[ndt.length];
        int napr,trasst,znapr;
        //создаем зеракльное отражение движения и записываем в массив
        for (int i=0;i<ndt.length;i+=2){
            napr=ndt[i];
            trasst=ndt[i+1];
            znapr=napr-2;
            if(napr==7)znapr=9;
            if(napr==6)znapr=8;
            if(napr==3)znapr=5;
            if(napr==2)znapr=4;
            ndt2[i]=znapr;
            ndt2[i+1]=trasst;
        }
        napravlenie = ndt[ittendt];
        rasst = ndt[ittendt + 1];
        if(ittendt==ndt.length)nazad=true;
    }

    public void run(float delta) {
        if (zarad) {
            for (MapObject mo : mapobjects.values()) {
                if (mo.unit&&mo.canbeattacked && Overlap.pointPoint(mo.centx, mo.centy, centx, centy, radius)) {
                    if(mo!=this){
                        mo.ataked(id, StateWorld.rain?duron:uron,Util.ELLURON,true);
                        zarad = false;
                        sendMsgPlayers("2/2/" + id);
                        break;
                    }}
            }
        } else {
            timezarad += delta * 10;
            if (timezarad > 15) {
                zarad = true;
                timezarad = 0;
            }
        }
        move(delta);
        if(!Overlap.pointPoint(centx,centy,pcentx,pcenty,rasst)){
            if(nazad){napravlenie=ndt2[ittendt];
                rasst =ndt2[ittendt+1];
                if(ittendt==0)nazad=false;else ittendt-=2;
            }else {
                napravlenie=ndt[ittendt];
                rasst =ndt[ittendt+1];
                if(ittendt+2==ndt.length)nazad=true;else ittendt+=2;
            }
            pcentx=centx;
            pcenty=centy;
            sendMsgPlayers("2/1/" + id + "/" + (int) x + "/" + (int) y + "/" + napravlenie);
        }
    }

    void move(float delta) {
        switch (napravlenie) {
            case UP:
                y += speed * delta;
                break;
            case RIGHT:
                x += speed * delta;
                break;
            case DOWN:
                y -= speed * delta;
                break;
            case LEFT:
                x -= speed * delta;
                break;
            case UR:
                y += speed * delta;
                x += speed * delta;
                break;
            case RD:
                x += speed * delta;
                y -= speed * delta;
                break;
            case DL:
                y -= speed * delta;
                x -= speed * delta;
                break;
            case LU:
                x -= speed * delta;
                y += speed * delta;
                break;
        }
        centx = x;
        centy = y;
    }
    @Override
    public void signal(int tip,Object object) {
        switch (tip) {
            // start anomaly
            case 5:
                if(zarad){
                    zarad = false;
                    sendMsgPlayers("2/2/" + id);}
                break;
            //удаление игрока
            case 2:
                long idv=(Long)object;
                for(Player pl:players){
                    if(pl.id==idv){players.remove(pl);
                        break;}
                }
                break;
        }
    }
    @Override
    public String addedAndDop(Player pl,String str){
        pl.mapobjects.put(id,this);
        players.add(pl);
        str+="/"+napravlenie;
        return str;
    }
}
