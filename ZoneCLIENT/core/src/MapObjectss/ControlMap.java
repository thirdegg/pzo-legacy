package MapObjectss;

import GameWorld.Game;
import GameWorld.MapObject;
import GameWorld.WorldState;
import Unit.Player;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import util.Overlap;
import util.Util;

import java.util.*;

public class ControlMap extends MapObject {
    Label kontrolklanname,statusgroup[],vosemchas;
    public boolean zahvat;
    private float skipframe;
    Collection<Player> players;
    public List<Player> playersuflaga = new ArrayList<Player>();
    public HashMap<String,Integer> klanstoscore = new HashMap<String,Integer>();
    public ControlMap(float xx, float yy,long idd,Collection<Player> players,Label.LabelStyle ls) {
        super("controlflag.png",xx, yy, 5,null,false);
        tip= Util.CONTROLMAP;
        id=idd;
        centx=x;
        centy=y;
        this.players=players;
        scalex=0.35f;
        scaley=0.40f;
        kontrolklanname=new Label("хер",ls);
        vosemchas=new Label("захват начнется в 7: 00 по игровому времени",ls);
        vosemchas.setPosition(xx-150,yy+120);
        kontrolklanname.setColor(Color.SKY);
        statusgroup=new Label[4];
        int yz=(int)y+70;
        for(int i=0;i<statusgroup.length;i++){
            statusgroup[i]=new Label("groupppp - 90",ls);
            statusgroup[i].setPosition(x+5-statusgroup[i].getWidth()/2,yz);
            yz-=20;
        }
    }
    @Override
    public void run(float delta){
        if(zahvat){
            skipframe+=delta*10;
            if(skipframe>10){
                if(klanstoscore.size()<4){
        for (Player pl:players){
            if(pl.klanname!=null&&pl.canbeattacked&& Overlap.pointPoint(pl.x,pl.y,centx,centy,200)&&!playersuflaga.contains(pl)){
                playersuflaga.add(pl);if(!klanstoscore.containsKey(pl.klanname.getText().toString()))klanstoscore.put(pl.klanname.getText().toString(),5);}
        }}
            Iterator<Player> r=playersuflaga.iterator();
            while (r.hasNext()) {
                Player pl=r.next();
                if(pl.klanname==null||!pl.canbeattacked){r.remove();break;}
                int score=klanstoscore.get(pl.klanname.getText().toString())+3;
                klanstoscore.put(pl.klanname.getText().toString(),score);
                if(!Overlap.pointPoint(pl.x,pl.y,centx,centy,205))
                    r.remove();
            }
           skipframe=0;
        }}
    }
    @Override
    public void dopDraw(Batch sb, ShapeRenderer sr) {
         if(sr.isDrawing())sr.circle(x,y,200);
        if(sb.isDrawing()){
            kontrolklanname.draw(sb,1);
            if(WorldState.hours!=7&&WorldState.hours!=8)vosemchas.draw(sb,1);
            if(zahvat){
            int i=0;
            for(String klanname:klanstoscore.keySet()){
                statusgroup[i].setText(klanname+" - "+klanstoscore.get(klanname));
                statusgroup[i].draw(sb,1);
                i++;
            }}
        }

    }
    @Override
    public void setState(int newstate,String []str) {
       switch (newstate){
           case 1:
       kontrolklanname.setText(str[6]);
       kontrolklanname.pack();
        kontrolklanname.setPosition(x+10-kontrolklanname.getWidth()/2,y+110);
       for(int i=7;i<str.length;i+=2){
           klanstoscore.put(str[i],Integer.parseInt(str[i+1]));
       }
        int i=0;
        int yz=(int)y+70;
        for(String klanname:klanstoscore.keySet()){
            statusgroup[i].setText(klanname+" - "+klanstoscore.get(klanname));
          statusgroup[i].pack();
          statusgroup[i].setPosition(x+5-statusgroup[i].getWidth()/2,yz);
          yz-=20;
            i++;
        }
       break;
           case 2:
               kontrolklanname.setText(str[3]);
               kontrolklanname.pack();
               kontrolklanname.setPosition(x+10-kontrolklanname.getWidth()/2,y+110);
               zahvat=false;
               playersuflaga.clear();
               break;
           case 3:
               zahvat=true;
               break;
       }
    }
}
