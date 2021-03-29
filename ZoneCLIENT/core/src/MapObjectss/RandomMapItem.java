package MapObjectss;

import GameWorld.MapObject;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import util.Rectang;
import util.Util;

/**
 * Created by 777 on 04.05.2017.
 */
public class RandomMapItem extends MapObject {
    long timeremov;
    public RandomMapItem(float xx, float yy, Long id,int tromov) {
        super("randit.png", xx, yy, 4,null,false);
        this.id = id;
        tip = Util.RANDOMITEM;
        centx = x;
        centy = y;
        rectang = new Rectang(x, y, 7, 10);
        mput=true;
        if(tromov>0)timeremov=System.currentTimeMillis()+tromov;
    }
    @Override
    public void run(float delta){
        if(timeremov>0){
            if(System.currentTimeMillis()>timeremov){
                remov=true;
            }}
    }
    @Override
    public void setState(int newstate, String[] str) {
        x = Integer.parseInt(str[2]);
        y = Integer.parseInt(str[3]);
        rectang.x = x;
        rectang.y = y;
        centx = rectang.x + rectang.width / 2;
        centy = rectang.y + rectang.height / 2;
    }
}
