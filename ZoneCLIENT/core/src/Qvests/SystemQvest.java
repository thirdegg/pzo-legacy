package Qvests;

public class SystemQvest {
    Qvest activqvest;

public void createQvestandEnd(int tipqvest){
    switch(tipqvest){
        case 0:
            activqvest=new ZaradAkkom();
            break;
        case 1:
            activqvest=new Kill10kabanov();
            break;
        case 2:
            activqvest=new Binokl();
            break;
        case 3:
            activqvest=new Kill10Zombi();
            break;
        case 4:
            activqvest=new Artefactss();
            break;
        case 5:
            activqvest=new DetekrorArtefaktovv();
            break;
        case 6:
            activqvest=new QRukiZombi();
            break;
        case 7:
            activqvest=new QKlikiKabana();
            break;
        case 8:
            activqvest=new KillBossKaban();
            break;
    }
    activqvest.endQvest();
}
}
