package Utils;

import MapObjects.Anomaly.*;
import MapObjects.Artefakts.*;
import MapObjects.Korob;
import MapObjects.RandomItemMap;
import MapObjects.Vkoster;
import Maps.WMap;
import MapObjects.Units.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by 777 on 18.02.2017.
 */
public class LoadObjects {
    public static void loadobjects(WMap wm,String textname){
        String s = "";
        Scanner in = null;
        try {
            in = new Scanner(new File("res/" + textname));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (in.hasNext()) {
            //	s += in.nextLine() + "\r\n";
            s += in.nextLine();
        }
        in.close();
        String str[];
        str = s.split("-");
        for(int i=1;i<str.length;i++){
            if(str[i].charAt(0)=='a'){
                String ss[]=str[i].split(":");
                int tip= Integer.parseInt(ss[1]);
                int kolvo=Integer.parseInt(ss[2]);
                wm.createobjects.add(new ObjectParametr(tip,Util.tipToPodtip(tip),kolvo,0,0,0,null));
                createObjectKolvo(wm,tip,kolvo);
            }
            else{
                createObject(wm,str[i]);
            }
        }
    }
      public static void createObjectKolvo(WMap wm,int tip,int kolvo){
       switch(tip){
           case Util.KABAN:getKaban(wm,kolvo);break;
           case Util.KRUSA:getKrusa(wm,kolvo);break;
           case Util.ZOMBI:getZombi(wm,kolvo);break;
           case Util.ELECTRA:getAnomalyElectra1(wm,kolvo);break;
           case Util.ELECTRA2:getAnomalyElectra2(wm,kolvo);break;
           case Util.IMPULS:getAnomalyBatut(wm,kolvo);break;
           case Util.LIPUCHKA:getAnomalyLipuchka(wm,kolvo);break;
           case Util.KISOBLAKO:getAnomalyKisoblako(wm,kolvo);break;
           case Util.ARTMGLA:getArtMgla(wm,kolvo);break;
           case Util.ARTVETER2:getArtVeter2(wm,kolvo);break;
           case Util.ARTBLOOD2:getArtBlood2(wm,kolvo);break;
           case Util.ARTHEART2:getArtHeart2(wm,kolvo);break;
           case Util.ARTOBEREG2:getArtObereg2(wm,kolvo);break;
       }
    }
    private static void createObject(WMap wm,String str){
          String s[]=str.split(":");
int tip=Integer.parseInt(s[0]);
int x=Integer.parseInt(s[1]);
int y=Integer.parseInt(s[2]);
switch(tip){
    case Util.ELECTRA:
        Electra1 electra=new Electra1(x,y,wm.mapobjects,wm.addmapobjects,false);
        wm.mapobjects.put(electra.id,electra);
        break;
    case Util.ELECTRA2:
        Electra2 electra2=new Electra2(x,y,wm.mapobjects,wm.addmapobjects,wm.grid);
        wm.mapobjects.put(electra2.id,electra2);
        break;
    case Util.DVIGELECTRA:
        String strr[] =s[3].split("!");
        int ndt[]=new int[strr.length];
        for(int i=0;i<strr.length;i++){
            ndt[i]=Integer.parseInt(strr[i]);
        }
        //исправляем погрешность которая возникайет хуй пойми из за чего.
        if(ndt[0]==3)x-=480;
        if(ndt[0]==2)y-=480;
        DvigElectra1 dvigelectra=new DvigElectra1(x,y,ndt,wm.mapobjects);
        wm.mapobjects.put(dvigelectra.id,dvigelectra);
        break;
    case Util.LIPUCHKA:
        Lipuchka lipuchka=new Lipuchka(x,y);
        wm.mapobjects.put(lipuchka.id,lipuchka);
        break;
    case Util.IMPULS:
        int naprav =Integer.parseInt(s[3]);
        Impuls batut=new Impuls(x,y,wm.mapobjects,naprav,wm.addmapobjects,wm.grid);
        wm.mapobjects.put(batut.id,batut);
        break;
    case Util.IMPULS2:
        int naprav2 =Integer.parseInt(s[3]);
        Impuls2 batut2=new Impuls2(x,y,wm.mapobjects,naprav2,wm.addmapobjects,wm.grid);
        wm.mapobjects.put(batut2.id,batut2);
        break;
    case Util.VKOSTER:
        Vkoster vkost=new Vkoster(x,y);
        wm.mapobjects.put(vkost.id,vkost);
        break;
    case Util.ARTMGLA:
        Mgla artm=new Mgla(x,y);
        wm.mapobjects.put(artm.id,artm);
        break;
    case Util.ARTVETER:
        Veter artv=new Veter(x,y,null);
        wm.mapobjects.put(artv.id,artv);
        break;
    case Util.KOROB:
        int item =Integer.parseInt(s[3]);
        int clvo=0;
        if (item==14)clvo=20;
        Korob korob=new Korob(x,y,Util.createItem(item,false,clvo));
        wm.mapobjects.put(korob.id,korob);
        break;
    case Util.QVEST:
        TipQvest tipq=new TipQvest(x,y);
        wm.mapobjects.put(tipq.id,tipq);
        break;
    case Util.NPC:
        Torgovec torg=new Torgovec(x,y);
        wm.mapobjects.put(torg.id,torg);
        break;
    case Util.RANDOMITEM:
        int konsttimespawn=Integer.parseInt(s[3]);
        RandomItemMap rim=new RandomItemMap(x,y,s[4]);
        wm.mapobjects.put(rim.id,rim);
        wm.createobjects.add(new ObjectParametr(tip,Util.PPRANDOMITEM,0,x,y,konsttimespawn,s[4]));
        break;
}
    }
   public static void getZombi(WMap wm,int kolvo){
        for (int i = 0; i < kolvo; i++) {
            Zombi object = new Zombi(rand.getIntRnd(50,1500), rand.getIntRnd(50,1500),wm.grid,wm.mapobjects,wm.addmapobjects);
            wm.hitObject(object);
            wm.mapobjects.put(object.id, object);
        }
    }
    public static void getKaban(WMap wm,int kolvo){
        for (int i = 0; i < kolvo; i++) {
            Kaban object = new Kaban(rand.getIntRnd(50,1500), rand.getIntRnd(50,1500),wm.grid,wm.mapobjects,wm.addmapobjects);
            wm.hitObject(object);
            wm.mapobjects.put(object.id, object);
        }
    }
    public static void getKrusa(WMap wm,int kolvo){
        for (int i = 0; i < kolvo; i++) {
            Friger object = new Friger(rand.getIntRnd(50,1500), rand.getIntRnd(50,1500),wm.grid,wm.mapobjects);
            wm.hitObject(object);
            wm.mapobjects.put(object.id, object);
        }
    }
    public static  void getArtMgla(WMap wm,int kolvo){
        for (int i = 0; i < kolvo; i++) {
            Mgla object = new Mgla(rand.getIntRnd(50,1500), rand.getIntRnd(50,1500));
            wm.hitObject(object);
            wm.mapobjects.put(object.id, object);
        }
    }
    public static  void getArtVeter2(WMap wm,int kolvo){
        for (int i = 0; i < kolvo; i++) {
            Veter2 object = new Veter2(rand.getIntRnd(50,1500), rand.getIntRnd(50,1500),null);
            object.clientvisible=true;
            wm.hitObject(object);
            wm.mapobjects.put(object.id, object);
        }
    }
    public static  void getArtBlood2(WMap wm,int kolvo){
        for (int i = 0; i < kolvo; i++) {
            Blood2 object = new Blood2(rand.getIntRnd(50,1500), rand.getIntRnd(50,1500),null);
            object.clientvisible=true;
            wm.hitObject(object);
            wm.mapobjects.put(object.id, object);
        }
    }
    public static  void getArtHeart2(WMap wm,int kolvo){
        for (int i = 0; i < kolvo; i++) {
            Heart2 object = new Heart2(rand.getIntRnd(50,1500), rand.getIntRnd(50,1500),wm.grid,null);
            object.clientvisible=true;
            wm.hitObject(object);
            wm.mapobjects.put(object.id, object);
        }
    }
    public static  void getArtObereg2(WMap wm,int kolvo){
        for (int i = 0; i < kolvo; i++) {
            Obereg2 object = new Obereg2(rand.getIntRnd(50,1500), rand.getIntRnd(50,1500),null);
            object.clientvisible=true;
            wm.hitObject(object);
            wm.mapobjects.put(object.id, object);
        }
    }
    public static void getAnomalyElectra1(WMap wm,int kolvo){
        for (int i = 0; i < kolvo; i++) {
            Electra1 object = new Electra1(rand.getIntRnd(50,1500), rand.getIntRnd(50,1500),wm.mapobjects,wm.addmapobjects,false);
            wm.hitObject(object);
            wm.mapobjects.put(object.id, object);
        }
    }
    public static void getAnomalyElectra2(WMap wm,int kolvo){
        for (int i = 0; i < kolvo; i++) {
            Electra2 object = new Electra2(rand.getIntRnd(50,1500), rand.getIntRnd(50,1500),wm.mapobjects,wm.addmapobjects,wm.grid);
            wm.hitObject(object);
            wm.mapobjects.put(object.id, object);
        }
    }
    public static void getAnomalyLipuchka(WMap wm,int kolvo){
        for (int i = 0; i < kolvo; i++) {
            Lipuchka object = new Lipuchka(rand.getIntRnd(50,1500), rand.getIntRnd(50,1500));
            wm.hitObject(object);
            int naprav=2;
            if(object.x>800)naprav=5;
            else naprav=3;
            wm.addMapHitObject(object,naprav,true);
        }
    }
    public static void getAnomalyBatut(WMap wm,int kolvo){
        for (int i = 0; i < kolvo; i++) {
            Impuls object = new Impuls(rand.getIntRnd(50,1500), rand.getIntRnd(50,1500),wm.mapobjects,0,wm.addmapobjects,wm.grid);
            wm.hitObject(object);
            int naprav=2;
            if(object.x>800)naprav=5;
            else naprav=3;
            wm.addMapHitObject(object,naprav,true);
        }
    }
    public static void getAnomalyKisoblako(WMap wm,int kolvo){
        for (int i = 0; i < kolvo; i++) {
            KisOblako object = new KisOblako(wm.mapobjects);
            wm.mapobjects.put(object.id,object);
        }
    }
}
