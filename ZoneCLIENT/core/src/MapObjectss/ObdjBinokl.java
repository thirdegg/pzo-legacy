package MapObjectss;

import GameWorld.MapObject;
import util.Rectang;
import util.Util;

/**
 * Created by 777 on 03.01.2017.
 */
public class ObdjBinokl extends MapObject {
    public ObdjBinokl(float xx, float yy, Long id) {
        super("imgbin.png", xx, yy, 2,null,false);
        this.id = id;
        tip = Util.BINOKL;
        plasrecx = 5;
        plasrecy = 3;
        centx = x + plasrecx;
        centy = y + plasrecy;
        rectang = new Rectang(x + plasrecx, y + plasrecy, 10, 10);
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
