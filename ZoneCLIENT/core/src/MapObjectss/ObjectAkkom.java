package MapObjectss;

import GameWorld.MapObject;
import util.Rectang;
import util.Util;

public class ObjectAkkom extends MapObject {
public String namehero;
    public ObjectAkkom(float xx, float yy, long id,String namehero) {
        super("akkom.png", xx, yy, 2,null,false);
        this.id = id;
        tip = Util.AKKOMULATOR;
        plasrecx = 5;
        plasrecy = 3;
        centx = x + plasrecx;
        centy = y + plasrecy;
        rectang = new Rectang(x + plasrecx, y + plasrecy, 10, 10);
        this.namehero=namehero;
        mput=true;
    }

    public void setState(int newstate, String[] str) {
        x = Integer.parseInt(str[2]);
        y = Integer.parseInt(str[3]);
        rectang.x = x + plasrecx;
        rectang.y = y + plasrecy;
        centx = rectang.x + rectang.width / 2;
        centy = rectang.y + rectang.height / 2;
    }
}
