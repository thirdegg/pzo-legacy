package Map;

import MapObjectss.AnimObject;
import MapObjectss.ClientMapObject;
import Unit.ClientNpc;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.StringBuilder;
import com.blackbirds.projectzone.GdxGame;
import util.Rectang;
import util.Util;

import java.util.Iterator;
import java.util.List;

public class Map {
    StringBuilder filerect,filesobject;
    ArrayMap<Integer, String> gidToName = new ArrayMap<Integer, String>();
    private TiledMap map;
    private TiledMapTileLayer layer;
    public Map(String mapName, Array<Imagee> tr, List<Rectang> rec, Array<ClientMapObject> cmo,GdxGame game) {

        map = new TmxMapLoade().load(mapName, Util.atlas);
        //может понадобится
        //layer = (TiledMapTileLayer) map.getLayers().get(0);
        //layer.getCell();
        loadGidToName();
        filerect=new StringBuilder();
        filesobject=new StringBuilder();
        MapObjects mo = map.getLayers().get("clientobjects").getObjects();
        AtlasRegion trava = Util.atlas.findRegion("kam");
        for (MapObject mm : mo) {
            float x = (Float) mm.getProperties().get("x");
            float y = (Float) mm.getProperties().get("y");
            int type=0;
            int zindex = 4;
            if(mm.getProperties().containsKey("zindex"))zindex=(Integer) mm.getProperties().get("zindex");
            if(mm.getProperties().containsKey("type"))type=(Integer) mm.getProperties().get("type");
            switch(type){
                // простое изображение
                case 0:
                    AtlasRegion region = Util.atlas.findRegion(gidToName.get((Integer) mm.getProperties().get("gid")));
                    System.out.println("!!!!!!!!!!!!"+mm.getProperties().get("gid"));
                    Imagee img = new Imagee(region, x, y, zindex);
                    //уменьшаем траву
                    if(region.equals(trava)){
                        img.scalex=0.80f;
                        img.scaley=0.80f;
                    }
                    tr.add(img);
                    break;
                    // анимация
                case 1:
                    TextureRegion tere=new TextureRegion(new Texture("animation/"+mm.getProperties().get("animname")+".png"));
                    AnimObject img2 = new AnimObject(tere, x, y, zindex,type,(Integer) mm.getProperties().get("kolvokadrov"), (Float) mm.getProperties().get("animspeed"));
                    tr.add(img2);
                    cmo.add(img2);
                    break;
                    // npc
                case 2:
                    TextureRegion tere2=new TextureRegion(new Texture("animation/"+mm.getProperties().get("animname")+".png"));
                    //TextureRegion tere2=new TextureRegion(Util.ekza);
                    ClientNpc cnpc=new ClientNpc(tere2,x-15,y,type,game.text.textName,(Integer) mm.getProperties().get("tipnpc"));
                    tr.add(cnpc);
                    cmo.add(cnpc);
                    break;
            }
        }

        for (MapObject mm : mo) {
            map.getLayers().get("clientobjects").getObjects().remove(mm);
        }

        MapLayer ml = map.getLayers().get("serverobjects");
        if (ml != null) {
            //мутанты
            if(ml.getProperties().containsKey("kaban"))filesobject.append("-a:"+Util.KABAN+":"+(Integer)ml.getProperties().get("kaban"));
            if(ml.getProperties().containsKey("friger"))filesobject.append("-a:"+Util.KRUSA+":"+(Integer)ml.getProperties().get("friger"));
            if(ml.getProperties().containsKey("zombi"))filesobject.append("-a:"+Util.ZOMBI+":"+(Integer)ml.getProperties().get("zombi"));
            //аномалии
            if(ml.getProperties().containsKey("electra"))filesobject.append("-a:"+Util.ELECTRA+":"+(Integer)ml.getProperties().get("electra"));
            if(ml.getProperties().containsKey("electra2"))filesobject.append("-a:"+Util.ELECTRA2+":"+(Integer)ml.getProperties().get("electra2"));
            if(ml.getProperties().containsKey("lipuchka"))filesobject.append("-a:"+Util.LIPUCHKA+":"+(Integer)ml.getProperties().get("lipuchka"));
            if(ml.getProperties().containsKey("kisoblako"))filesobject.append("-a:"+Util.KISOBLAKO+":"+(Integer)ml.getProperties().get("kisoblako"));
            if(ml.getProperties().containsKey("impuls"))filesobject.append("-a:"+Util.IMPULS +":"+(Integer)ml.getProperties().get("impuls"));
            //артефакты
            if(ml.getProperties().containsKey("mgla"))filesobject.append("-a:"+Util.ARTMGLA+":"+(Integer)ml.getProperties().get("mgla"));
            if(ml.getProperties().containsKey("veter"))filesobject.append("-a:"+Util.ARTVETER+":"+(Integer)ml.getProperties().get("veter"));
            if(ml.getProperties().containsKey("iskra"))filesobject.append("-a:"+Util.ARTISKRA+":"+(Integer)ml.getProperties().get("iskra"));
            MapObjects smo = ml.getObjects();
            for (MapObject mm : smo) {
                float fx = (Float) mm.getProperties().get("x");
                float fy = (Float) mm.getProperties().get("y");
                int x=(int)fx;int y=(int)fy;
                System.out.println("x-"+x+" y-"+y);
                String str = gidToName.get((Integer) mm.getProperties().get("gid"));
               char p = str.charAt(0);
                switch (p) {
                       //electra
                    case 'e':
                        x+=17;y+=16;
                        filesobject.append("-"+ Util.ELECTRA+":"+x+":"+y);
                        break;
                       // electra2
                    case '2':
                        x+=87;y+=82;
                        filesobject.append("-"+ Util.ELECTRA2+":"+x+":"+y);
                        break;
                        //dvigelectra
                    case 'd':
                        x+=17;y+=16;
                        String ndt=(String) mm.getProperties().get("ndt");
                        filesobject.append("-"+ Util.DVIGELECTRA+":"+x+":"+y+":"+ndt);
                        break;
                        //lipuchka
                    case 'l':
                        x+=17;y+=16;
                        filesobject.append("-"+ Util.LIPUCHKA+":"+x+":"+y);
                        break;
                        //impuls//iskra//item
                    case 'i':
                        if(str.equals("impulstmx")){
                        x+=26;y+=15;
                        int naprav=0;
                        if(mm.getProperties().containsKey("naprav"))naprav=(Integer)mm.getProperties().get("naprav");
                        filesobject.append("-"+ Util.IMPULS +":"+x+":"+y+":"+naprav);}
                        if(str.equals("impuls2tmx")){
                            x+=26;y+=15;
                            int naprav=0;
                            if(mm.getProperties().containsKey("naprav"))naprav=(Integer)mm.getProperties().get("naprav");
                            filesobject.append("-"+ Util.IMPULS2 +":"+x+":"+y+":"+naprav);}
                        if(str.equals("iskratmx")){
                            filesobject.append("-"+ Util.ARTISKRA+":"+x+":"+y);
                        }
                        if(str.equals("itemtmx")){
                            filesobject.append("-"+ Util.RANDOMITEM+":"+x+":"+y+":"+((Integer)mm.getProperties().get("timespawn")+20000)+":"+mm.getProperties().get("category"));
                        }
                        break;
                        //vkoster
                    case 'k':
                        filesobject.append("-"+ Util.VKOSTER+":"+x+":"+y);
                        break;
                        //artveter
                    case 'v':
                        filesobject.append("-"+ Util.ARTVETER+":"+x+":"+y);
                        break;
                       // artmgla
                    case 'm':
                        filesobject.append("-"+ Util.ARTMGLA+":"+x+":"+y);
                        break;
                        //korob
                    case 'o':
                        int item=(Integer)mm.getProperties().get("item");
                        filesobject.append("-"+ Util.KOROB+":"+x+":"+y+":"+item);
                        break;
                    // сделать загрузку нпс

                }
            }
            for (MapObject mm : smo) {
                map.getLayers().get("serverobjects").getObjects().remove(mm);
            }
            //записть в файл
        //    FileHandle file = Gdx.files.local(mapName + "sobject.txt");
        //    file.writeString(filesobject.toString(), false);
        }

        MapLayer rml = map.getLayers().get("rectangle");
        if (rml != null) {
            MapObjects smo = rml.getObjects();
            for (MapObject mm : smo) {
                float x = (Float) mm.getProperties().get("x");
                float y = (Float) mm.getProperties().get("y");
                float width = (Float) mm.getProperties().get("width");
                float height = (Float) mm.getProperties().get("height");
                boolean dom=false;
                if(mm.getProperties().containsKey("dom"))dom=(Boolean) mm.getProperties().get("dom");
                Rectang r = new Rectang(x, y, width, height);
                filerect.append("-" + x + "-" + y + "-" + width + "-" + height);
                if (dom) {
                    r.dom = dom;
                    filerect.append("-a");
                }
                rec.add(r);
            }
            for (MapObject mm : smo) {
                map.getLayers().get("rectangle").getObjects().remove(mm);
            }
            //записть в файл
         //  FileHandle file = Gdx.files.local(mapName + ".txt");
         //   file.writeString(filerect.toString(), false);
        }
    }

    public TiledMap getMap() {
        return map;
    }

    void loadGidToName() {
        Iterator<TiledMapTileSet> it;
        it = map.getTileSets().iterator();
        while (it.hasNext()) {
            TiledMapTileSet tmts = it.next();
            gidToName.put((Integer) tmts.getProperties().get("firstgid"), tmts.getName());
        }
    }
}