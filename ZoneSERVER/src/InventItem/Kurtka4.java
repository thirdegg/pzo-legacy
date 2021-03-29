package InventItem;

        import InventItem.core.Item;
        import MapObjects.Units.Player;

public class Kurtka4 extends Item {
    private final int fizarmor = 30;
    private final int ellarmor = 45;
    private final int pullarmor = 50;

    public Kurtka4() {
        id = generatorId.incrementAndGet();
        tipitem = 17;
        useslot = 1;
        cena=39000;
    }
    @Override
    public void odet(Player player,boolean send) {
        player.fizarmor+=fizarmor;
        player.ellarmor+=ellarmor;
        player.pullarmor+=pullarmor;
        if(send)player.sendMsg("2/8/" + player.id + "/"+tipitem,true);
    }
    @Override
    public void snyat(Player player,boolean send) {
        player.fizarmor-=fizarmor;
        player.ellarmor-=ellarmor;
        player.pullarmor-=pullarmor;
        if(send)player.sendMsg("2/8/" + player.id + "/"+""+-1,true);
    }
}
