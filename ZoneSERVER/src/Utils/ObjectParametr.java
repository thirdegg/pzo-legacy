package Utils;

/**
 * Created by 777 on 19.02.2017.
 */
public class ObjectParametr {
    public int tip,kolvo,cx,cy,podtip,konstimespawn;
    public long timespaawn;
    public Object dopobject;
    public boolean create;
    public ObjectParametr(int tip,int podtip,int kolvo,int cx,int cy,int konstimespawn,Object dopobject){
        this.tip=tip;this.kolvo=kolvo;this.cx=cx;this.cy=cy;this.podtip=podtip;
        this.konstimespawn=konstimespawn;this.dopobject=dopobject;
        timespaawn=System.currentTimeMillis()+konstimespawn;
        create=true;
    }
    }
