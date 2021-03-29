package AccountService;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserDataSet {
    @Column(name = "name")
    String name;
    @Column(name = "password")
    String password;
    @Column(name = "x")
    float x;
    @Column(name = "y")
    float y;
    @Column(name = "kolvolife")
    float kolvolife;
    @Column(name = "holod")
    float holod;
    @Column(name = "golod")
    float golod;
    @Column(name = "zhazda")
    float zhazda;
    @Column(name = "rad")
    float rad;
    @Column(name = "krovotech")
    float krovotech;
    @Column(name = "timedie")
    int timedie;
    @Column(name = "items")
    String items;
    @Column(name = "klanname")
    String klanname;
    @Column(name = "qvest")
    String qvest;
    @Column(name = "idmap")
    int exp;
    @Column(name = "exp")
    String skils;
    @Column(name = "skils")
    int idmap;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    public UserDataSet(String name, String pass) {
        this.setId(-1);
        this.setName(name);
        password = pass;
        kolvolife = 100;
        idmap=0;x = 72;y = 1283;
        //все делится слешами на 3 части -деньги итем одето (итем - колво)
        items ="300/14-70-11-0-2-0-2-0-2-0/13-6";
        if(name.equals("korvin"))items ="150000/14-70-11-0-2-0-2-0-2-0-32-0-27-0-28-0-33-0-34-0/13-6";
        if(name.equals("shevelev"))items ="5000/14-70-11-0-2-0-2-0-2-0-33-0/13-6";
        if(name.equals("roma"))items ="5000/14-70-11-0-2-0-2-0-2-0-34-0/13-6";
        if(name.equals("Western"))items ="1000/14-70-11-0-2-0-2-0-2-0-32-0-33-0-34-0/13-6";
    }

    public UserDataSet() {
    }

    public UserDataSet(long id, String name) {
        this.setId(id);
        this.setName(name);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


}

