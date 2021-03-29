package InventItem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.ArrayMap;

public class ImgItems {
    private final int CALLS = 20;
    private final int ROWS = 15;
    public  ArrayMap<Integer, Image> tiptoitem = new ArrayMap<Integer, Image>();

    public ImgItems() {
        Texture titem = new Texture(Gdx.files.internal("items.png"));
        TextureRegion[][] tmp = TextureRegion.split(titem, titem.getWidth() / CALLS, titem.getHeight() / ROWS);
        int index = 0;
        TextureRegion[] item = new TextureRegion[ROWS * CALLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < CALLS; j++) {
                item[index++] = tmp[i][j];
            }
        }
        for (int i = 0; i < item.length; i++) {
            Image img = new Image(item[i]);
            tiptoitem.put(i, img);
        }
    }
}
