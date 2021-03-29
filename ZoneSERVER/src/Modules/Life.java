package Modules;

import Utils.Util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 777 on 17.11.2016.
 */
public class Life {
    public float life;
    public float maxlife=100;
    private List<Tmpl> listtmpl = new ArrayList<>();
    public long killid;

    //одноразовое пополнение\удаление жизней
    public void minusLife(float ml) {
        life -= ml;
    }

    public void plusLife(float ml) {
        life += ml;
    }

    //удаление\пополнеие жизней в течении некоторого времени
    public void timeMinusLife(long id, float kolvolife, float timeperiod, float fulltime, int tip, boolean notendtime,boolean fasturon) {
        Tmpl xtml = new Tmpl(id,kolvolife,timeperiod,fulltime,tip,notendtime,false);
        if(fasturon)life-=kolvolife;
        listtmpl.add(xtml);
    }

    public void timePlusLife(long id, float kolvolife, float timeperiod, float fulltime, int tip, boolean notendtime) {
        Tmpl xtml = new Tmpl(id,kolvolife,timeperiod,fulltime,tip,notendtime,true);
        listtmpl.add(xtml);
    }

    //удаление\добавление жизней при условиях boolean
    public void boolPlasLife() {
    }

    public void boolMinusLife() {
    }
    public void stopAllMobMinusLife(){
        for (Tmpl tmpl : listtmpl) {
            if (tmpl.tip == Util.FIZURON||tmpl.tip==Util.PULLURON){tmpl.run = false;}
        }
    }
    public void stopPlusMinusLife(long id) {
        for (Tmpl tmpl : listtmpl) {
            if (tmpl.id == id){tmpl.run = false;break;}
        }
    }
/*    public void stopPlusMinusLife(int tip) {
        for (Tmpl tmpl : listtmpl) {
            if (tmpl.tip == tip){tmpl.run = false;break;}
        }
    }*/
    public boolean containsMinusLife(int tip) {
        for (Tmpl tmpl : listtmpl) {
            if (tmpl.tip == tip)return true;
        }
        return false;
    }
    public void stopAll() {
        listtmpl.clear();
    }

    public String getAllTmpl(long thisplayerid) {
        String s =null;
        if(!listtmpl.isEmpty()){
                s="3/3/"+thisplayerid+"/";
        for (Tmpl tmpl : listtmpl) {
            if (tmpl.run) s += tmpl.id + ":" + tmpl.timerun + ":" + tmpl.kolvolife + ":" + tmpl.timeperiod + ":"
                    + tmpl.doptimeperiod + ":" +tmpl.fulltime + ":" + (tmpl.plusorminus?1:0) + ":" + tmpl.tip + "/";
        }}
        return s;
    }

    //пример правильного удаления елемента из лист в цикле
   public void run(float delta) {
       if (!listtmpl.isEmpty()&&life>0) {
       //вариант 1
       Iterator<Tmpl>itter=listtmpl.iterator();
       while(itter.hasNext()){
           Tmpl tmpl = itter.next();
           tmpl.run(delta);
           if(life<=0){killid=tmpl.id;return;}
           if (!tmpl.run)itter.remove();
       }
/*
       //вариант 2
            for (ListIterator<Tmpl> i = listtmpl.listIterator(); i.hasNext(); ) {
                Tmpl tmpl = i.next();
                tmpl.run(delta);
                if (!tmpl.run) i.remove();
            }*/
        }
    }

    //class time minus or plus kolvolife
    private class Tmpl {
        boolean run = true, plusorminus,notendtime;
        private float kolvolife, timeperiod, doptimeperiod, timerun, fulltime;
        private long id;
        private int tip;

        Tmpl(long id, float kolvolife, float timeperiod, float fulltime, int tip, boolean notendtime, boolean plasorminus) {
        this.id=id;
            this.kolvolife=kolvolife;
            this.timeperiod=timeperiod;
            this.fulltime=fulltime;
            this.tip=tip;
            this.notendtime=notendtime;
            this.plusorminus=plasorminus;
            this.doptimeperiod=timeperiod;
        }

        void run(float delta) {
            if (run&&life>0) {
                if(!notendtime){
                if (fulltime > timerun) {
                    timerun += 10 * delta;
                    if (timerun > doptimeperiod) {
                        if (plusorminus) {if(life<maxlife)life += kolvolife;}
                        else {life -= kolvolife;}
                        doptimeperiod = timerun + timeperiod;
                    }
                } else {
                    run = false;
                }}
                else{
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
}
