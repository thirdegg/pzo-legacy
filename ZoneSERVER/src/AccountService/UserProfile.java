package AccountService;

public class UserProfile {
    String login;
    String password;
    int idmap=0;
    float x = 72, y = 1283, life = 100, speed = 42, holod, golod, zhazda, rad, krovotech;//speed 35
    float normalspeed=42;
    int timedie;
    String items,qvest,klanname,skils;
    int exp=10;

    public UserProfile(String login, String password) {
        this.login = login;
        this.password = password;
        //все делится слешами на 3 части -деньги итем одето
        items="300/11-0-14-20-1-0-0-0-43-0-43-0-43-0-43-0-43-0-43-0-45-0-22-0/";
    }

    void setState(float x, float y, float life, float holod, float golod, float zhazda, float rad, float krovotech, int timedie,
                  String items, String qvest,int idmap,String klanname,int exp,String skils) {
        this.x = x;
        this.y = y;
        this.life = life;
        this.speed = normalspeed;
        this.holod = holod;
        this.golod = golod;
        this.zhazda = zhazda;
        this.rad = rad;
        this.krovotech = krovotech;
        this.timedie = timedie;
        this.items = items;
        this.qvest = qvest;
        this.idmap=idmap;
        this.klanname=klanname;
        this.exp=exp;
        this.skils=skils;
    }
}
