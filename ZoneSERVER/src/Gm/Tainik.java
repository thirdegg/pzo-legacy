package Gm;

import InventItem.core.Item;

import java.util.ArrayList;
import java.util.List;

public class Tainik {
   private List<Item> items=new ArrayList<>();
    public float posx,posy;
    public int idmap;
    public void addIt(Item it){
    items.add(it);
    }
    public Item getit(int tip){
        for(Item it:items){
            if(it.tipitem==tip){
                Item i=it;
                items.remove(it);
                return i;
            }
        }
        return null;
    }
}
