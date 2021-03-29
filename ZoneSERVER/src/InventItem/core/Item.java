package InventItem.core;

import Maps.WMap;
import MapObjects.Units.Player;

import java.util.concurrent.atomic.AtomicInteger;

public class Item {
   public static AtomicInteger generatorId = new AtomicInteger();
    public int tipitem, useslot,id, dopintcolvo,cena;
    public boolean use;
    public boolean vibrosit=true,remuse=true;
    public void use(WMap wm,Player player) {}
    public void odet(Player player,boolean send){}
    public void snyat(Player player,boolean send) {}
}
