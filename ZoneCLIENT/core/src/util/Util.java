package util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.StringTokenizer;

public class Util {
    //MapObject
    public static final int PLAYER = 1, KABAN = 2, ARTMGLA = 3, ELECTRA = 4, DVIGELECTRA = 5, ELECTRA2 = 6,
            ZOMBI=7,NPC =8, QVEST = 9, AKKOMULATOR = 10,BINOKL=11,KOROB=12,KOSTER=13,KRUSA=14,KISOBLAKO=15, IMPULS =16,
            LIPUCHKA=17,VKOSTER=18,ARTVETER=19,ARTISKRA=20,ARTHEART=21,ARTBLOOD=22,RANDOMITEM=23,RADIATION=24,
            CONTROLMAP=25,BOSKABAN=26,IMPULS2=33,ARTOBERG=34;
    //TipUrona
    public static final int FIZURON=1,ELLURON=2,PULLURON=3,FIREURON=4,YADURON=5,AEROURON=6;
    //state mapobject
    public static final int WALKING=1,ATAKA=2,DIED=3,PEREZARAD=4,VDOME=5,SITTING=6,JUMP=7,MODIFIKATOR=11;//8 kurtka 9 harakteristiki 10 pl stop atak
    public static TextureRegion kisotex;
    public static Texture zombi,zombi2,zombi3,kaban,friger;
    public static Texture dolg,seva,defult,stalker,nebo,ekza,bandit,maks,paha,roma;
    public static Texture electra;
    public static TextureAtlas atlas;
    public static final int gv=230;
    public static long starttimeset;
    public static LocalSave localSave;
    public Util(){
        atlas = new TextureAtlas(Gdx.files.internal("tmxmap/graphics.atlas"));
        kisotex=new TextureRegion(new Texture(Gdx.files.internal("kist.png")));
        zombi = new Texture(Gdx.files.internal("animation/zombie.png"));
        zombi2 = new Texture(Gdx.files.internal("animation/zombie2.png"));
        zombi3 = new Texture(Gdx.files.internal("animation/zombie3.png"));
        kaban = new Texture(Gdx.files.internal("animation/kaban.png"));
        friger = new Texture(Gdx.files.internal("animation/friger.png"));
        dolg = new Texture(Gdx.files.internal("animation/dolg.png"));
        seva = new Texture(Gdx.files.internal("animation/seva.png"));
        defult = new Texture(Gdx.files.internal("animation/default.png"));
        stalker = new Texture(Gdx.files.internal("animation/stalker.png"));
        nebo = new Texture(Gdx.files.internal("animation/nebo.png"));
        ekza = new Texture(Gdx.files.internal("animation/ekza.png"));
        bandit = new Texture(Gdx.files.internal("animation/bandit.png"));
        electra = new Texture(Gdx.files.internal("animation/molniya.png"));
        maks = new Texture(Gdx.files.internal("animation/maks.png"));
        paha = new Texture(Gdx.files.internal("animation/paha.png"));
        roma = new Texture(Gdx.files.internal("animation/romka.png"));
        localSave=new LocalSave();
        localSave.init();
    }
    //округление
    public static double round(float value, int scale) {
        return Math.round(value * Math.pow(10, scale)) / Math.pow(10, scale);
    }
    public static String perenosSlov(StringTokenizer str, int dlina) {
        StringBuilder text = new StringBuilder();
        int dlinnastroki = 0;
        while (str.hasMoreTokens()) {
            String ts = str.nextToken();
            //если длина больше чем надо добавляем перенос и пропускаем пробел
            if (dlinnastroki > dlina && ts.equals(" ")) {
                dlinnastroki = 0;
                text.append("\n");
            } else {
                dlinnastroki += ts.length();
                text.append(ts);
            }
        }
        return text.toString();
    }
}
