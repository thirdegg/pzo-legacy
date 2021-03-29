package Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Imagee extends TextureRegion {
    public float x, y, zheight;
    public float rotate, scalex = 1, scaley = 1, width, height, originx, originy;
    public boolean visibl=true;

    public Imagee(String internalPath, float xx, float yy, float z) {
        super(new Texture(internalPath));
        x = xx;
        y = yy;
        zheight = z;
        width = this.getRegionWidth();
        height = this.getRegionHeight();
    }

    public Imagee(TextureRegion trr, float xx, float yy, float z) {
        super(trr);
        x = xx;
        y = yy;
        zheight = z;
        width = this.getRegionWidth();
        height = this.getRegionHeight();
    }
}
