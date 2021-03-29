package Gm;

import Maps.WMap;
import Server.NettyServerHandler;
import MapObjects.Units.Player;
import Utils.rand;

public class Vibros {
    public float vibrostime;
    long startTime, endTime, stepTime;
    int timedu = 15, timeuron;
    boolean opovest,opovestskil, vibros;
    //продолжительность, урон, колличество итераций, кол-во урона для повышения, длительность одной итерации,
//сл выброс
    int duration, damage, collitter, colldmg, timeitter, nextvibros;
    WMap maps[];
    public Vibros( WMap maps[]) {
        nextvibros = rand.getIntRnd(400000, 500000);
        endTime = System.currentTimeMillis();
        this.maps=maps;
    }

    void startVibros() {
        startTime = System.currentTimeMillis();
        stepTime = startTime;
        vibros = true;
        duration = 69000;
        damage = 3;
        collitter = 3;
        timeitter = 23000;
        colldmg = 3;
        vibrostime = 0;
        timeuron = (int) (vibrostime + timedu);
        //старт выбраса сообщаем всем картам (отправляем сообщения игрокам)
        for(WMap map:maps){
            map.signal(4);
        }
    }

    void run(float delta) {
        if (vibros) {
            vibrostime += 10 * delta;
            if (System.currentTimeMillis() - startTime < duration) {
                //переход на следующую итерацию
                if (System.currentTimeMillis() - stepTime > timeitter && collitter != 0) {
                    damage += colldmg;
                    stepTime = System.currentTimeMillis();
                    collitter--;
                }
            } else endVibros();
            // тратим жизни
            if (vibrostime > timeuron) {
                timeuron = (int) (vibrostime + timedu);
                for(WMap map:maps){
                    for(Player pl:map.players){
                        if (pl.getState() != pl.VDOME && pl.getState() != pl.DEAD) pl.pmLife.life -= damage;
                    }
                }
            }
            //если выброс не идет
        } else {
            if (endTime + nextvibros < System.currentTimeMillis()) startVibros();
//оповещение игроков о скором выбросе
            if (endTime + nextvibros - System.currentTimeMillis() < 25000 && !opovest) {
                opovest = true;
                for(WMap map:maps){
                    for(Player pl:map.players){
                        NettyServerHandler.sendMsgClient("26", pl.idchanel);
                    }
                }

            }
            //оповещение игроков о скором выбросе skill
            if (endTime + nextvibros - System.currentTimeMillis() < 40000 && !opovestskil) {
                opovestskil = true;
                for(WMap map:maps){
                    for(Player pl:map.players){
                        if(pl.numstoskils.containsKey(0))NettyServerHandler.sendMsgClient("26/0", pl.idchanel);
                    }
                }

            }
        }
    }

    void endVibros() {
        vibros = false;
        opovest = false;
        opovestskil=false;
        endTime = System.currentTimeMillis();
        nextvibros = rand.getIntRnd(500000, 700000);
        //создание артефактов
for(WMap map:maps){
    map.signal(3);
}
    }
public void AdminStartVibros(){
    endTime=System.currentTimeMillis();
    nextvibros=41000;
}
    int getOst() {
        return (int) (timeitter - (System.currentTimeMillis() - stepTime));
    }

}
