package util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class LocalSave {
    // через это сохраняем загружаем состояние игры
    private Preferences prefs;
    public void init(){
        prefs = Gdx.app.getPreferences("save");
    }
    public void save(String key,int zn){
        prefs.putInteger(key,zn);
        prefs.flush();
    }
    public void save(String key,float zn){
        prefs.putFloat(key,zn);
        prefs.flush();
    }
    public void save(String key,String zn){
        prefs.putString(key,zn);
        prefs.flush();
    }
    public int loadint(String key,int defaultval){
        return prefs.getInteger(key,defaultval);
    }
    public float loadfloat(String key,float defaultval){
        return prefs.getFloat(key,defaultval);
    }
    public String loadstring(String key,String defaultval){
        return prefs.getString(key,defaultval);
    }

}
