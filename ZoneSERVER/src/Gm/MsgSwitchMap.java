package Gm;

import AccountService.UserDataSet;
import Base.AccountService;
import Base.Address;
import MapObjects.Units.Player;
import MessageSystem.MsgToAS;
import MessageSystem.MsgToGM;

/**
 * Created by 777 on 01.06.2017.
 */
public class MsgSwitchMap extends MsgToGM {
  Player pl;
  int idmap,x,y;

    public MsgSwitchMap(Address from, Address to,Player pl,int idmap, int x,int y) {
        super(from, to);
      this.idmap=idmap;
      this.pl=pl;
      this.x=x;
      this.y=y;
    }

    @Override
    public void exec(GameMechanics gm) {
      gm.teleportMap(pl,idmap,x,y);
    }
}
