package MapObjectss;

import Map.Imagee;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import util.Rectang;

/**
 * Created by 777 on 01.05.2017.
 */
public class ClientMapObject extends Imagee {
public int tip;
public float centx,centy;
public Rectang rectang;
   public ClientMapObject(TextureRegion trr, float xx, float yy, float z,int tip){
        super(trr,xx,yy,z);
        this.tip=tip;
    }
    public void run(float delta) {

    }

    public void dopDraw(Batch sb, ShapeRenderer sr) {
    }
}
