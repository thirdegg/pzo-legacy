package Gm;

import AccountService.UserDataSet;
import Base.AccountService;
import Base.Address;
import MessageSystem.MsgToAS;

public class MsgSetPlayerState extends MsgToAS {
    int timedie;
    //имя тест базаданных
    String qvest,name;
    UserDataSet uds;
    private String items;
    private long id;
    private float x, y, life,holod, golod, zhazda, rad, krovotech;
    private int idmap;
    private String klanname,skils;
    int exp;
// имя нужно когда нет базы
    public MsgSetPlayerState(Address from, Address to, long id, float x, float y, float life,
                             float holod, float golod, float zhazda, float rad, float krovotech, int timedie, String items, String qvest, UserDataSet uds,String name,int idmap,String klanname,int exp,String skils) {
        super(from, to);
        this.id = id;
        this.x = x;
        this.y = y;
        this.life = life;
        this.holod = holod;
        this.golod = golod;
        this.zhazda = zhazda;
        this.rad = rad;
        this.krovotech = krovotech;
        this.timedie = timedie;
        this.items = items;
        this.qvest = qvest;
        this.uds = uds;
        this.name=name;
        this.idmap=idmap;
        this.klanname=klanname;
        this.exp=exp;
        this.skils=skils;
    }

    @Override
    public void exec(AccountService accountService) {
        accountService.setState(id, x, y, life, holod, golod, zhazda, rad, krovotech, timedie, items, qvest, uds,name,idmap,klanname,exp,skils);
    }

}
