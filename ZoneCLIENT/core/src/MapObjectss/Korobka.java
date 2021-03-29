package MapObjectss;

import GameWorld.MapObject;
import util.Rectang;
import util.Util;

/**
 * Created by 777 on 12.01.2017.
 */
public class Korobka extends MapObject {
    long timeremov;
    public Korobka(float xx, float yy, long id,int trem) {
        super("korob.png", xx, yy, 2,null,false);
        this.id = id;
        tip = Util.KOROB;
        plasrecx = 5;
        plasrecy = 3;
        centx = x + plasrecx;
        centy = y + plasrecy;
        rectang = new Rectang(x + plasrecx, y + plasrecy, 10, 10);
        mput=true;
        timeremov=System.currentTimeMillis()+trem;
    }
    @Override
    public void run(float delta){
            if(System.currentTimeMillis()>timeremov){
                remov=true;
            }
    }
    @Override
    public void setState(int newstate, String[] str) {
        x = Integer.parseInt(str[2]);
        y = Integer.parseInt(str[3]);
        rectang.x = x + plasrecx;
        rectang.y = y + plasrecy;
        centx = rectang.x + rectang.width / 2;
        centy = rectang.y + rectang.height / 2;
    }
}
