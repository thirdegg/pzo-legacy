package MapObjects.Units;

import InventItem.*;
import InventItem.core.Item;
import MapObjects.MapObject;
import Server.NettyServerHandler;
import Utils.Rectang;
import Utils.Util;

public class Torgovec extends MapObject {
    public Torgovec(float x, float y) {
        id = generatorId.incrementAndGet();
        tip = Util.NPC;
        this.x = x;
        this.y = y;
        centx = x + 5;
        centy = y + 9;
        rectang = new Rectang(x, y, 35, 35);
    }

    public void bay(int tovar, Player player) {
        switch (tovar) {
            case 0:
                if (player.inventar.many >= 1600) {
                    player.inventar.many -= 1600;
                    Kurtka2 to = new Kurtka2();
                    player.inventar.addItem(to);
                }
                break;
            case 1:
                if (player.inventar.many >= 600) {
                    player.inventar.many -= 600;
                    Kurtka1 ob = new Kurtka1();
                    player.inventar.addItem(ob);
                }
                break;
            case 2:
                if (player.inventar.many >= 50) {
                    player.inventar.many -= 50;
                    Spichki sp = new Spichki();
                    player.inventar.addItem(sp);
                }
                break;
            case 13:
                if (player.inventar.many >= 300) {
                    player.inventar.many -= 300;
                    Pistolet1 pi = new Pistolet1();
                    player.inventar.addItem(pi);
                }
                break;
            case 14:
                if (player.inventar.many >= 80) {
                    player.inventar.many -= 80;
                    Patrony pat = new Patrony();
                    player.inventar.addItem(pat);
                }
                break;
            case 11:
                if (player.inventar.many >= 50) {
                    player.inventar.many -= 50;
                    Aptechka ap = new Aptechka();
                    player.inventar.addItem(ap);
                }
                break;
            case 5:
                if (player.inventar.many >= 30) {
                    player.inventar.many -= 30;
                    Baton ba = new Baton();
                    player.inventar.addItem(ba);
                }
                break;
            case 4:
                if (player.inventar.many >= 40) {
                    player.inventar.many -= 40;
                    Flaga fl = new Flaga();
                    player.inventar.addItem(fl);
                }
                break;
            case 16:
                if (player.inventar.many >= 1300) {
                    player.inventar.many -= 1300;
                    Kurtka3 kur = new Kurtka3();
                    player.inventar.addItem(kur);
                }
                break;
            case 17:
                if (player.inventar.many >= 4000) {
                    player.inventar.many -= 4000;
                    Kurtka4 kur = new Kurtka4();
                    player.inventar.addItem(kur);
                }
                break;
            case 10:
                if (player.inventar.many >= 30) {
                    player.inventar.many -= 30;
                    IneRad inr = new IneRad();
                    player.inventar.addItem(inr);
                }
                break;
            case 18:
                if (player.inventar.many >= 800) {
                    player.inventar.many -= 800;
                    Pistolet2 inr = new Pistolet2();
                    player.inventar.addItem(inr);
                }
                break;
            case 19:
                if (player.inventar.many >= 1500) {
                    player.inventar.many -= 1500;
                    Pistolet3 inr = new Pistolet3();
                    player.inventar.addItem(inr);
                }
                break;
            case 20:
                if (player.inventar.many >= 3500) {
                    player.inventar.many -= 3500;
                    Pistolet4 inr = new Pistolet4();
                    player.inventar.addItem(inr);
                }
                break;
            case 25:
                if (player.inventar.many >= 800) {
                    player.inventar.many -= 800;
                    Kurtka5 ku = new Kurtka5();
                    player.inventar.addItem(ku);
                }
                break;
            case 26:
                if (player.inventar.many >= 2500) {
                    player.inventar.many -= 2500;
                    Kurtka6 ku2 = new Kurtka6();
                    player.inventar.addItem(ku2);
                }
                break;
            case 29:
                if (player.inventar.many >= 2000) {
                    player.inventar.many -= 2000;
                    Avtomat1 ku2 = new Avtomat1();
                    player.inventar.addItem(ku2);
                }
                break;
            case 30:
                if (player.inventar.many >= 3500) {
                    player.inventar.many -= 3500;
                    Avtomat2 ku2 = new Avtomat2();
                    player.inventar.addItem(ku2);
                }
                break;
            case 31:
                if (player.inventar.many >= 4500) {
                    player.inventar.many -= 4500;
                    Avtomat3 ku2 = new Avtomat3();
                    player.inventar.addItem(ku2);
                }
                break;
        }
    }

    public void sell(int idtovar, Player player) {
        Item it = null;
        it = player.inventar.item.remove(idtovar);
        if (it != null) {
            switch (it.tipitem) {
                case 14:
                    player.inventar.many += it.dopintcolvo * 2;
                    break;
                case 13:
                    player.inventar.many += it.cena/2;
                    break;
                case 11:
                    player.inventar.many += 25;
                    break;
                case 1:
                    player.inventar.many +=it.cena/2;
                    break;
                case 10:
                    player.inventar.many += 15;
                    break;
                case 0:
                    player.inventar.many +=it.cena/2;
                    break;
                case 5:
                    player.inventar.many += 15;
                    break;
                case 4:
                    player.inventar.many += 20;
                    break;
                case 12:
                    player.inventar.many += 25;
                    break;
                case 2:
                    player.inventar.many += 25;
                    break;
                case 17:
                    player.inventar.many += it.cena/2;
                    break;
                case 16:
                    player.inventar.many +=it.cena/2;
                    break;
                case 15:
                    player.inventar.many += 50;
                    break;
                case 18:
                    player.inventar.many +=it.cena/2;
                    break;
                case 19:
                    player.inventar.many +=it.cena/2;
                    break;
                case 20:
                    player.inventar.many +=it.cena/2;
                    break;
                case 24:
                    player.inventar.many += 100;
                    break;
                case 25:
                    player.inventar.many += it.cena/2;
                    break;
                case 26:
                    player.inventar.many += it.cena/2;
                    break;
                case 27:
                    player.inventar.many += it.cena/2;
                    break;
                case 28:
                    player.inventar.many += it.cena/2;
                    break;
                case 29:
                    player.inventar.many += it.cena/2;
                    break;
                case 30:
                    player.inventar.many += it.cena/2;
                    break;
                case 31:
                    player.inventar.many += it.cena/2;
                    break;
                case 32:
                    player.inventar.many += it.cena/2;
                    break;
                case 333:
                    player.inventar.many += it.cena/2;
                    break;
                case 34:
                    player.inventar.many += it.cena/2;
                    break;
            }
            NettyServerHandler.sendMsgClient("19/m/"+player.inventar.many+"\0"+"20/"+it.id,player.idchanel);
        }
    }
}
