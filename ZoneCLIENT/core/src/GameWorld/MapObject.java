package GameWorld;

import Map.Imagee;
import Modules.Life;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import util.Rectang;

public class MapObject extends Imagee {
    public int tip, plasrecx, plasrecy,state;
    public long id;
    public Rectang rectang;
    public float centx, centy;
    public boolean canbeattacked,unit,remov;
    public Life life;
    public int raddclient=300;
    //можно поднять
    public boolean mput;
    public MapObject(String internalPath, float xx, float yy, float z,Label.LabelStyle lsuron,boolean unit) {
        super(internalPath, xx, yy, z);
        this.unit=unit;
        if(unit)life=new Life(lsuron);
    }

    public void run(float delta) {

    }

    public void dopDraw(Batch sb, ShapeRenderer sr) {
    }

    public void setState(int newstate,String []str) {
    }
    //1 stop ataked 2 rem playuer 3 you kill me 4 start modifikarion 5 stop modofikation
    public void signal(int tip,Object object){}
  //  public void ataked(float kolvolife,int tipurona) {
   //     life.life-=kolvolife;
    //}
    public void msgLife(int tipmsg, String[] str){
    switch(tipmsg){
        //minus life
        case 1:
            if(str.length>5){
                life.timeMinusLife(Long.parseLong(str[3]), Float.parseFloat(str[4]), Float.parseFloat(str[5]), Float.parseFloat(str[6]),
                        Integer.parseInt(str[7]),Boolean.parseBoolean(str[8]),Boolean.parseBoolean(str[9]));}
            else{life.minusLife(Float.parseFloat(str[4]));}
            if(id==Game.hero.id){Game.hero.inventcanuse=false;Game.hero.inventtimecanuse=0;}
            break;
        //stop minus plus life
        case 2:
            life.stopPlusMinusLife(Long.parseLong(str[3]));
            break;
        //allMinusPlusLife
        case 3:
            if(str.length<8)return;
            for (int i = 3; i < str.length; i++) {
                String mlstr[] = str[i].split(":");
                life.runMinusPlusLife(Long.parseLong(mlstr[0]), Float.parseFloat(mlstr[1]), Float.parseFloat(mlstr[2]), Float.parseFloat(mlstr[3]),
                        Float.parseFloat(mlstr[4]), Float.parseFloat(mlstr[5]), Integer.parseInt(mlstr[6]), Integer.parseInt(mlstr[7]));
            }
            break;
        //plus life
        case 4:
            if(str.length>5){
                life.timePlusLife(Long.parseLong(str[3]), Float.parseFloat(str[4]), Float.parseFloat(str[5]), Float.parseFloat(str[6]),
                        Integer.parseInt(str[7]), Integer.parseInt(str[8]) == 1 ? true : false);}
            else{life.plusLife(Float.parseFloat(str[4]));}
            break;
    }
    }
    public void setPosition(float xx,float yy){
        x=xx;y=yy;
        if(rectang!=null){
            rectang.y = y + plasrecy;
            rectang.x = x + plasrecx;
            centx = rectang.x + rectang.width / 2;
            centy = rectang.y + rectang.height / 2;}
        else{
            centx=x;centy=y;
        }
    }
}
