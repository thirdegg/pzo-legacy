package Utils;

import InventItem.*;
import InventItem.artefacts.*;
import InventItem.core.Inventar;
import InventItem.core.Item;
import InventItem.device.DetektorAnomalys;
import InventItem.device.Guitar;
import InventItem.device.Radiomiter;
import InventItem.mobitem.KlikiBossKabana;
import InventItem.mobitem.KlikiKabana;
import InventItem.mobitem.RukaZombi;
import InventItem.radiodetali.*;
import MapObjects.MapObject;
import MapObjects.Units.Player;

import java.util.Collection;

/**
 * Created by 777 on 11.01.2017.
 */
public class Util {
    //MapObject
    public static final int PLAYER = 1, KABAN = 2, ARTMGLA = 3, ELECTRA = 4, DVIGELECTRA = 5, ELECTRA2 = 6,
            ZOMBI=7,NPC =8, QVEST = 9, AKKOMULATOR = 10,BINOKL=11,KOROB=12,KOSTER=13,KRUSA=14,KISOBLAKO=15, IMPULS =16,
    LIPUCHKA=17,VKOSTER=18,ARTVETER=19,ARTISKRA=20,ARTHEART=21,ARTBLOOD=22,RANDOMITEM=23,KOROB2=23,RADIATION=24,CONTROLMAP=25,
            BOSKABAN=26,ARTBLOOD1=27,ARTBLOOD2=28,ARTHEART1=29,ARTHEART2=30,ARTVETER1=31,ARTVETER2=32,IMPULS2=33,ARTOBERG=34,
            ARTOBEREG1=35,ARTOBEREG2=36;
    //TipUrona
    public static final int FIZURON=1,ELLURON=2,PULLURON=3,FIREURON=4,YADURON=5,AEROURON=6;
    // object podtip
    public static final int PPARTEFAKT=1,PPMOB=2,PPANOMALY=3,PPKOROB=4,PPPLAYER=5,PPRANDOMITEM=6,PPKOROB2=7,PPBOS=8;
    public static int podschet(Collection<MapObject>mapobjects,int tip){
        int coll=0;
        for(MapObject mo:mapobjects){
           if(mo.tip==tip)coll++;
        }
        return coll;
    }
    public static int podtippodschet(Collection<MapObject>mapobjects,int ptip){
        int coll=0;
        for(MapObject mo:mapobjects){
            if(mo.podtip==ptip)coll++;
        }
        return coll;
    }
    public static void setItIzProf(Player pl, String str) {
        String tmp[] = str.split("/");
        pl.inventar.many = Integer.parseInt(tmp[0]);
        String sitem[] = null, sodeto[] = null;
        if (tmp.length > 1) sitem = tmp[1].split("-");
        if (tmp.length > 2) sodeto = tmp[2].split("-");
//установка итем
        if (sitem != null && sitem.length > 1) {
            for (int i = 0; i < sitem.length; i+=2) {
                int tip = Integer.parseInt(sitem[i]);
                int colvo=Integer.parseInt(sitem[i+1]);
                Item it = Util.createItem(tip,false,colvo);
                if(it!=null){
                pl.inventar.item.put(it.id,it);}
            }
        }
//установка одето
        if (sodeto != null && sodeto.length > 1) {
            for (int i = 0; i < sodeto.length; i+=2) {
                // переделать тут ошибка
                int tip = Integer.parseInt(sodeto[i]);
                int colvo=Integer.parseInt(sodeto[i+1]);
                Item it = Util.createItem(tip,false,colvo);
                if(it!=null){
                pl.inventar.odeto.put(it.id,it);
                it.odet(pl,false);}
            }
        }
    }
    public static Item createItem(int tip){
    return createItem(tip,true,0);
    }
    public static Item createItem(int tip,boolean createnewcolvo,int colvo){
        Item it=null;
        switch (tip) {
            case 0:
                it = new Kurtka2();
                break;
            case 1:
                it = new Kurtka1();
                break;
            case 2:
                it = new Spichki();
                break;
//case 3:Lom lm=new Lom();addObject(lm);break;
            case 4:
                it = new Flaga();
                break;
            case 5:
                it = new Baton();
                break;
            case 6:
                it = new Konserva();
                break;
//case 7:Noz no=new Noz();addObject(no);break;
            case 8:
                it = new Bint();
                break;
//case 9:Kanistra ka=new Kanistra();addObject(ok);break;
            case 10:
                it = new IneRad();
                break;
            case 11:
                it = new Aptechka();
                break;
            case 12:
                it = new ArtMgla();
                break;
            case 13:
                it = new Pistolet1();
                break;
            case 14:
                it = new Patrony();
                break;
            case 15:
                it = new ArtElec1();
                break;
            case 16:
                it = new Kurtka3();
                break;
            case 17:
                it = new Kurtka4();
                break;
            case 18:
                it = new Pistolet2();
                break;
            case 19:
                it = new Pistolet3();
                break;
            case 20:
                it = new Pistolet4();
                break;
            case 21:
                it = new Akkom();
                break;
            case 22:
                it = new AkkomZ();
                break;
            case 23:
                it = new ItBinokl();
                break;
            case 24:
                it = new ArtVeter();
                break;
            case 25:
                it = new Kurtka5();
                break;
            case 26:
                it = new Kurtka6();
                break;
            case 27:
                it = new ArtHeart();
                break;
            case 28:
                it = new ArtBlood();
                break;
            case 29:
                it = new Avtomat1();
                break;
            case 30:
                it = new Avtomat2();
                break;
            case 31:
                it = new Avtomat3();
                break;
            case 32:
                it = new KurtkaM();
                break;
            case 33:
                it = new KurtkaP();
                break;
            case 34:
                it = new KurtkaR();
                break;
            case 35:
                it = new MedProvoda();
                break;
            case 36:
                it = new Display();
                break;
            case 37:
                it = new Tranzistor();
                break;
            case 38:
                it = new Kondensator();
                break;
            case 39:
                it = new Mikrochip();
                break;
            case 40:
                it = new Dinamik();
                break;
            case 41:
                it = new Rezistor();
                break;
            case 42:
                it = new RukaZombi();
                break;
            case 43:
                it = new KlikiKabana();
                break;
            case 44:
                it = new DetektorAnomalys();
                break;
            case 45:
                it = new KlikiBossKabana();
                break;
            case 46:
                it = new ArtArmor();
                break;
            case 47:
                it = new ArtBlood1();
                break;
            case 48:
                it = new ArtBlood2();
                break;
            case 49:
                it = new ArtHeart1();
                break;
            case 50:
                it = new ArtHeart2();
                break;
            case 51:
                it = new ArtVeter1();
                break;
            case 52:
                it = new ArtVeter2();
                break;
            case 103:
                it = new Guitar();
                break;
            case 97:
                it = new Radiomiter();
                break;
            case 120:
                it=new ArtArmor1();
                break;
            case 121:
                it=new ArtArmor2();
                break;

        }
        if(it!=null&&!createnewcolvo)it.dopintcolvo=colvo;
        return it;
    }
    public static String getItemsSave(Inventar in) {
        String str = in.many + "/";
        String sit=null;
        for (Item it : in.item.values()) {
            if(sit==null)sit=it.tipitem+"-"+it.dopintcolvo;
            else sit+="-"+it.tipitem+"-"+it.dopintcolvo;
        }
        if(sit!=null)str+=sit;
        str+="/";
        String sod=null;
        for (Item it : in.odeto.values()) {
            if(sod==null)sod=""+it.tipitem+"-"+it.dopintcolvo;
            else sod+="-"+it.tipitem+"-"+it.dopintcolvo;
        }
        if(sod!=null)str+=sod;
        return str;
    }
    public static int tipToPodtip(int tip) {
       switch (tip){
           case ARTBLOOD:case ARTHEART:case ARTISKRA:case ARTMGLA:case ARTVETER:return PPARTEFAKT;
           case KRUSA:case KABAN:return PPMOB;
           case KOROB:return PPKOROB;
           case PLAYER:return PPPLAYER;
       }
        return  0;
    }
}
