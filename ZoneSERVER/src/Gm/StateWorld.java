package Gm;

import Klans.SystemKlans;
import Maps.WMap;
import Server.NettyServerHandler;
import MapObjects.Units.Player;
import Utils.rand;
//сделать подписку на время
public class StateWorld {
    public static boolean rain;
  public static int hours = 12, minutes=10;
    float seconds;
    int durationrain, durationnotrain;
    long startrain, endrain;
    Vibros vibros;
    WMap maps[];
    SystemKlans sk;

    public StateWorld(WMap maps[], SystemKlans sk) {
        startrain = System.currentTimeMillis() + rand.getIntRnd(200000, 300000);
        vibros = new Vibros(maps);
        this.maps=maps;
        this.sk=sk;
    }

    void run(float delta, long systemtime) {
        //time world
        //seconds += 10 * delta;
        seconds += 80 * delta;
        if (seconds > 60) {
            seconds = 0;
            minutes++;
        }
        if (minutes == 60) {
            minutes = 0;
            hours++;
            //отправляем время игрокам
            for (WMap map : maps) {
                for(Player pl:map.players){
                    NettyServerHandler.sendMsgClient("6/" + hours, pl.idchanel);
                }
            }
            if(hours==7)timeZombi(false);
            if (hours == 24) {
                hours = 0;
                timeZombi(true);
            }
            if(hours==7)sk.startTimeZahvat();
            if(hours==9)sk.endTimeZahvat();
        }
        vibros.run(delta);
//rain
if(rain){if(systemtime>endrain)endRain();}
else{if(systemtime>startrain)startRain();}
    }

    void startRain() {
        rain = true;
        durationrain = rand.getIntRnd(90000, 90000);
        endrain = startrain + durationrain;

        for (WMap map : maps) {
            for(Player pl:map.players){
                NettyServerHandler.sendMsgClient("17/1/", pl.idchanel);
            }
        }
    }

    void endRain() {
        rain = false;
        durationnotrain = rand.getIntRnd(90000, 90000);
        startrain = endrain + durationnotrain;

        for (WMap map : maps) {
            for(Player pl:map.players){
                NettyServerHandler.sendMsgClient("17/2/", pl.idchanel);
            }
        }
    }
    void timeZombi(boolean add){
        if(add)
            for(WMap map:maps){
          map.signal(1);
        }
        else
            for(WMap map:maps){
                map.signal(2);
            }
    }
}
