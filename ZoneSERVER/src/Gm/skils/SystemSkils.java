package Gm.skils;

import MapObjects.Units.Player;
import Server.NettyServerHandler;

/**
 * Created by 777 on 02.08.2017.
 */
public class SystemSkils {
    Skil listskils[]=new Skil[20];
   public SystemSkils(){
        listskils[0]=new PredchuvstvieZoni();
        listskils[1]=new RukaAtleta();
       listskils[2]=new Stremitelnost();
       listskils[3]=new Stremitelnost2();
       listskils[4]=new Vmestitelnost();
       listskils[5]=new Vmestitelnost2();
       listskils[6]=new Berezhlivost();
       listskils[7]=new PullArmor();
       listskils[8]=new EllArmor();
       listskils[9]=new FizArmor();
    }
  public void getPlayerMsg(Player pl,int tipm,int skil){
      switch (tipm){
          // колво опыта
          case 0:
              NettyServerHandler.sendMsgClient("27/"+pl.exp,pl.idchanel);
              break;
          //изучить скил
          case 1:
              if(skil<=9){
              listskils[skil].learn(pl);
             // listskils[skil].activation(pl);
                   }
              break;
      }
    }
    //сделать занисение скила в базу данных при его изучении
    public String saveHeroSkillstoBase(Player pl){
     StringBuilder skills=new StringBuilder();
     for(Integer tips:pl.numstoskils.keySet()){
     skills.append(tips+"-");
     }
     return skills.toString();
    }
    public  void addHeroSkills(Player pl){
        if(pl.sskill!=null&&pl.sskill.length()>1){
        String sk[]=pl.sskill.split("-");
        for(String str:sk){
        int tips=Integer.parseInt(str);
        pl.numstoskils.put(tips,listskils[tips]);
        listskils[tips].activation(pl);}
        }
    }
}
