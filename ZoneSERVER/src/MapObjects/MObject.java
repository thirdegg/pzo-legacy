package MapObjects;

import MapObjects.Units.Player;
import Utils.Rectang;

// подклас MapObject и суперкласс для всего что можно поднять с земли (артефакты коробки с вещами)
public class MObject extends MapObject {
    public MObject(float x, float y){
        this.x = x;
        this.y = y;
        centx = x;
        centy = y;
        rectang = new Rectang(x, y, 20, 16);
    }
    public void addItem(Player player){}
}
