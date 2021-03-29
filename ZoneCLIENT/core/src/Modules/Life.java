package Modules;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import util.Util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 777 on 17.11.2016.
 */
public class Life {
    public float life;
    private List<Tmpl> listtmpl = new ArrayList<Tmpl>();
    public List<MinusLife> minuslife= new ArrayList<MinusLife>();
    private Label.LabelStyle lsuron;
    private float oldlife;
    public float maxlife=100;
    public Life(Label.LabelStyle lsuron){this.lsuron=lsuron;}
    //одноразовое пополнение\удаление жизней
    public void minusLife(float ml) {
        life -= ml;
    }

    public void plusLife(float pl) {
        life += pl;
    }

    //удаление\пополнеие жизней в течении некоторого времени
    public void timeMinusLife(long id, float kolvolife, float timeperiod, float fulltime, int tip, boolean notendtime,boolean fasturon) {
        Tmpl xtml = new Tmpl(id, kolvolife, timeperiod, fulltime, tip, notendtime, false);
        if(fasturon)life-=kolvolife;
        listtmpl.add(xtml);
    }

    public void timePlusLife(long id, float kolvolife, float timeperiod, float fulltime, int tip, boolean notendtime) {
        Tmpl xtml = new Tmpl(id, kolvolife, timeperiod, fulltime, tip, notendtime, true);
        listtmpl.add(xtml);
    }
    public void runMinusPlusLife(long id,float timerun,float kolvolife, float timeperiod,float doptimeperiod,
                                 float fulltime,int pm,int tip) {
        Tmpl xtml = new Tmpl(id,timerun,kolvolife,timeperiod,doptimeperiod,fulltime,pm,tip,(fulltime>0?false:true));
        listtmpl.add(xtml);
    }

    //удаление\добавление жизней при условиях boolean
    public void boolPlasLife() {
    }

    public void boolMinusLife() {
    }

    public void stopPlusMinusLife(long id) {
        for (Tmpl tmpl : listtmpl) {
            if (tmpl.id == id) tmpl.run = false;
        }
    }
    public void stopAllMobMinusLife(){
        for (Tmpl tmpl : listtmpl) {
            if (tmpl.tip == Util.FIZURON||tmpl.tip==Util.PULLURON){tmpl.run = false;}
        }
    }
    public boolean containsPlusMinusLife(int tip) {
        for (Tmpl tmpl : listtmpl) {
            if (tmpl.tip == tip)return  true;
        }
        return false;
    }

    public void stopAll() {
        listtmpl.clear();
    }

    //пример правильного удаления елемента из лист в цикле
    public void run(float delta,float x,float y) {
        if (!listtmpl.isEmpty()) {
            Iterator<Tmpl>iter=listtmpl.iterator();
            while(iter.hasNext()) {
                Tmpl tmpl = iter.next();
                tmpl.run(delta,x,y);
                if (!tmpl.run)iter.remove();
            }
        }
        if(oldlife!=life){float kolvo=oldlife-life;
            if(kolvo>-1&&kolvo<1){oldlife=life;}else{
            MinusLife ml=new MinusLife(x,y, Util.round(kolvo,2),kolvo>0?Color.RED:Color.GREEN);
            oldlife=life;minuslife.add(ml);}
        }
        if (!minuslife.isEmpty()) {
            Iterator<MinusLife>iter=minuslife.iterator();
            while(iter.hasNext()) {
                MinusLife ml = iter.next();
                ml.run();
                if (!ml.worck)iter.remove();
            }
        }
    }
    //class time minus or plus life
    private class Tmpl {
        boolean run = true, plusorminus, notendtime;
        private float kolvolife, timeperiod, doptimeperiod, timerun, fulltime;
        private long id;
        private int tip;

        Tmpl(long id, float kolvolife, float timeperiod, float fulltime, int tip, boolean notendtime, boolean plasorminus) {
            this.id = id;
            this.kolvolife = kolvolife;
            this.timeperiod = timeperiod;
            this.fulltime = fulltime;
            this.tip = tip;
            this.notendtime = notendtime;
            this.plusorminus = plasorminus;
            this.doptimeperiod = timeperiod;
        }
        Tmpl(long id,float timerun,float kolvolife, float timeperiod,float doptimeperiod,float fulltime,int pm,int tip,boolean notendtime) {
            this.id = id;
            this.kolvolife = kolvolife;
            this.timeperiod = timeperiod;
            this.fulltime = fulltime;
            this.tip = tip;
            this.timerun=timerun;
            this.notendtime = notendtime;
            this.doptimeperiod = doptimeperiod;
            if(pm==1)plusorminus=true;
        }

        void run(float delta,float x,float y) {
            if (run) {
                if (!notendtime) {
                    if (fulltime > timerun) {
                        timerun += 10 * delta;
                        if (timerun > doptimeperiod) {
                            if (plusorminus) {if(life<maxlife)life += kolvolife;}
                            else {life -= kolvolife;}
                            doptimeperiod = timerun + timeperiod;
                        }
                    } else {
                        run = false;
                    }
                } else {
                    timerun += 10 * delta;
                    if (timerun > doptimeperiod) {
                        if (plusorminus) {if(life<maxlife)life += kolvolife;}
                        else {life -= kolvolife;}
                        doptimeperiod = timerun + timeperiod;
                    }
                }
            }
        }
    }
    public class MinusLife {
        public Label luron;
        public boolean worck=true;
        int ypos;
        float x, y;

        public MinusLife(float x,float y,double uron,Color color) {
            luron = new Label("55",lsuron);
            this.x=x;
            this.y=y;
            luron.setPosition(x + 10, y + 20);
            if(color== Color.RED)luron.setText("-" + uron);
            if(color==Color.GREEN){uron-=uron+uron;luron.setText("" + uron);}
            luron.setColor(color);
        }

        void run() {
            if (worck) {
                luron.setPosition(x + 10, y + ypos + 20);
                ypos++;
                if (ypos == 25) {
                    worck = false;
                }
            }
        }
    }
}
