package Qvests;

import MapObjects.MapObject;
import Maps.WMap;
import MapObjects.Units.Player;
import Utils.Overlap;
import Utils.Util;

public class SystemQvest {
    private Qvest[]qvests=new Qvest[9];
   public SystemQvest(){
    qvests[0]=new ZaradAkkom();
        qvests[1]=new Kill10kabanov();
       qvests[2]=new Binokl();
       qvests[3]=new Kill10Zombi();
       qvests[4]=new Artefactss();
       qvests[5]=new DetekrorArtefaktovv();
       qvests[6]=new QRukiZombi();
       qvests[7]=new QKlikiKabana();
       qvests[8]=new KillBossKaban();
    }
    public void Qvest(String str[], Player player,WMap[]maps) {
        // сделать проверку на то что игрок рядом с квестодателем
        int state = Integer.parseInt(str[2]);
        int qvest = Integer.parseInt(str[3]);
        switch(qvest){
            case 0:case 1:case 3:case 4:case 5:case 6:case 7:case 8:
        if (state == 1) qvests[qvest].startQvest(player);
        if (state == 2) qvests[qvest].endQvest(player);
                break;
            case 2:
                if (state == 1) qvests[qvest].startQvest(player,maps);
                if (state == 2) qvests[qvest].endQvest(player);
                break;
        }
    }
}
