package MapObjects;

import InventItem.*;
import InventItem.core.Item;
import InventItem.radiodetali.*;
import MapObjects.Units.Player;
import Utils.Util;
import Utils.rand;

/**
 * Created by 777 on 04.05.2017.
 */
public class RandomItemMap extends MObject {
    String category[];
    int catpodtip;
    Item it;
    long timeremov;
    public RandomItemMap(float x, float y,String podtip){
        super(x,y);
        id = MapObject.generatorId.incrementAndGet();
        tip = Util.RANDOMITEM;
        category=podtip.split("!");
        //выбераем категорию
        int radtc=0;
        if(category.length>1){
            radtc= rand.getIntRnd(0,category.length);}
        catpodtip=Integer.parseInt(category[radtc]);
        getItemFromCategory();
    }
    public RandomItemMap(float x, float y,Item it,int consttimeremov){
        super(x,y);
        id = MapObject.generatorId.incrementAndGet();
        tip = Util.RANDOMITEM;
        this.it=it;
        timeremov=System.currentTimeMillis()+consttimeremov;
    }
@Override
public void run(float delta){
        if(timeremov>0){
        if(System.currentTimeMillis()>timeremov){
        remov=true;
        }}
}
@Override
public String addedAndDop(Player pl, String str) {
    pl.mapobjects.put(id, this);
    //если это удаляемый объект
    if(timeremov>0)str+="/"+(timeremov-System.currentTimeMillis());
    return str;
}
    @Override
    public void addItem(Player player){
        player.inventar.addItem(it);
    }

    private void getItemFromCategory(){
      switch(catpodtip){
          //радиодетали
          case 1:
              int radnitem=rand.getIntRnd(1,7);
              switch(radnitem){
                  case 1:
                      it=new MedProvoda();
                      break;
                  case 2:
                      it=new Display();
                      break;
                  case 3:
                      it=new Tranzistor();
                      break;
                  case 4:
                      it=new Rezistor();
                      break;
                  case 5:
                      it=new Mikrochip();
                      break;
                  case 6:
                      it=new Kondensator();
                      break;
                  case 7:
                      it=new Dinamik();
                      break;
              }
              break;
              //всякое
          case 2:
              int radnitem2=rand.getIntRnd(1,6);
              switch(radnitem2){
                  case 1:
                      it= new Aptechka();
                      break;
                  case 2:
                      it= new Patrony();
                      it.dopintcolvo=70;
                      break;
                  case 3:
                      it=new IneRad();
                      break;
                  case 4:
                      it= new Kurtka1();
                      break;
                  case 5:
                      it= new Aptechka();
                      break;
                  case 6:
                      it= new Aptechka();
                      break;
              }
              break;
      }
    }
}
