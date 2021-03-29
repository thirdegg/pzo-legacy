package GameInterface.Pda;


import GameInterface.Ginterface;
import GameWorld.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blackbirds.projectzone.GdxGame;
import util.Util;

import java.util.*;

/**
 * Created by 777 on 21.08.2017.
 */
public class Lichka extends Group {
    Label newmessage,vhodiashie,ishodiashe;
    Image fon,nazad;
    Group groupnewmessage,groupspisok, groupvhnames,groupvhmsg,groupreadmsg;
    TextField tfname;
    TextArea tfmsg;
    Button otpravit,yes,no;
    GdxGame game;
    HashMap<String,ArrayList<Message>>nametomsg=new HashMap<String, ArrayList<Message>>();
    Label msg;
    int zy=390;
  //  FileHandle file;
    String mstr[];
    Label lvhname[]=new Label[10];
    Label lvhmsg[]=new Label[10];
    String namezapros;
    CorePda pda;
    Lichka(final GdxGame game, final CorePda pda){
     setVisible(false);
     this.game=game;
     this.pda=pda;
        fon=new Image(new Texture("pda/fontext3.png"));
        fon.setPosition(70,59);
        addActor(fon);
        newmessage=new Label("отправить сообщение",game.text.linvent);
        vhodiashie=new Label("входящие сообщения",game.text.linvent);
        ishodiashe=new Label("исходящие сообщения",game.text.linvent);
        msg=new Label("zzzzzzzzzzzzzzzzzzzzzzzz",game.text.linvent);
        newmessage.setPosition(100,391);
        vhodiashie.setPosition(100,370);
        ishodiashe.setPosition(100,349);
        newmessage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                groupnewmessage.setVisible(true);
                groupspisok.setVisible(false);
                super.clicked(event, x, y);
            }
        });
        groupspisok=new Group();
        groupvhnames =new Group();
        groupvhmsg =new Group();
        groupreadmsg =new Group();
        groupnewmessage=new Group();
        groupnewmessage.setVisible(false);
        groupspisok.setVisible(false);
        groupvhnames.setVisible(false);
        groupvhmsg.setVisible(false);
        groupreadmsg.setVisible(false);
        groupspisok.addActor(newmessage);
        groupspisok.addActor(vhodiashie);
        groupspisok.addActor(ishodiashe);
        addActor(groupnewmessage);
        addActor(groupspisok);
        addActor(groupvhnames);
        addActor(groupvhmsg);
        addActor(groupreadmsg);
        tfname = new TextField("", game.text.tgamechat);
        tfname.setSize(200, 30);
        tfname.setPosition(90, 365);
        tfname.setMaxLength(15);
        tfname.setMessageText("Кому:");
        groupnewmessage.addActor(tfname);

        tfmsg = new TextArea("", game.text.tgamechat);
        tfmsg.setSize(600, 150);
        tfmsg.setPosition(90, 195);
        tfmsg.setMaxLength(400);
        tfmsg.setMessageText("Сообщение...");

        groupnewmessage.addActor(tfmsg);

        nazad= new Image(new Texture("pda/nazad.png"));
        nazad.setPosition(690,62);
        nazad.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(groupvhmsg.isVisible()){groupvhmsg.setVisible(false);
                groupvhnames.setVisible(true);return;}
                if(groupreadmsg.isVisible()){groupreadmsg.setVisible(false);
                    groupvhmsg.setVisible(true);yes.setVisible(false);no.setVisible(false);return;}
                setVisible(false);
                groupvhnames.setVisible(false);
                groupspisok.setVisible(false);
                groupnewmessage.setVisible(false);
                pda.rabstol.setVisible(true);
                super.clicked(event, x, y);
            }
        });
        vhodiashie.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                openVhodiashie();
                super.clicked(event, x, y);
            }
        });
        addActor(nazad);
        otpravit = new TextButton("отправить", game.text.ButtonStyle);
        otpravit.setSize(130, 30);
        otpravit.setPosition(550, 145);
        otpravit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (tfname.getText().length() > 2&&tfmsg.getText().length()>2)game.tout.sendMsg("1/17/" + tfname.getText()+"/"+tfmsg.getText());
                super.clicked(event, x, y);
            }
        });
        groupnewmessage.addActor(otpravit);

        yes= new TextButton("да", game.text.ButtonStyle);
        yes.setSize(50, 30);
        yes.setPosition(130, 300);
        yes.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.tout.sendMsg("1/21/1/"+Game.hero.klanname.getText().toString()+"/"+namezapros);
                groupreadmsg.setVisible(false);
                groupvhmsg.setVisible(true);yes.setVisible(false);no.setVisible(false);
                super.clicked(event, x, y);
            }
        });
        yes.setVisible(false);
        no= new TextButton("нет", game.text.ButtonStyle);
        no.setSize(50, 30);
        no.setPosition(230, 300);
        no.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.tout.sendMsg("1/21/2/"+Game.hero.klanname.getText().toString()+"/"+namezapros);
                groupreadmsg.setVisible(false);
                groupvhmsg.setVisible(true);yes.setVisible(false);no.setVisible(false);
                super.clicked(event, x, y);
            }
        });
        no.setVisible(false);
        groupreadmsg.addActor(msg);
        groupreadmsg.addActor(yes);
        groupreadmsg.addActor(no);
        for (int i = 0; i < lvhname.length; i++) {
            lvhname[i] = new Label("fak", game.text.lgamechat);
            lvhname[i].setPosition(90, zy);
            zy -= lvhname[i].getHeight() - 3;
            groupvhnames.addActor(lvhname[i]);
            lvhname[i].setText("");
            final int ii=i;
            lvhname[i].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                 openPlMsg(lvhname[ii].getText().toString());
                 lvhname[ii].setColor(Color.WHITE);
                 namezapros=lvhname[ii].getText().toString();
                    super.clicked(event, x, y);
                }
            });

        }
        zy=390;
        for (int i = 0; i < lvhmsg.length; i++) {
            lvhmsg[i] = new Label("fak", game.text.lgamechat);
            lvhmsg[i].setPosition(90, zy);
            zy -= lvhmsg[i].getHeight() - 3;
            groupvhmsg.addActor(lvhmsg[i]);
            lvhmsg[i].setText("");
        }

   /*     file = Gdx.files.local(Game.hero.name.getText().toString()+"/message/PZmessages.txt");
        if(!file.exists())file.writeString("test/testmsg/", true);
        file= Gdx.files.internal(Game.hero.name.getText().toString()+"/message/PZmessages.txt");
        String sstr=file.readString();
        file = Gdx.files.local(Game.hero.name.getText().toString()+"/message/PZmessages.txt");
        mstr=sstr.split("/");
        for(int i=0;i<mstr.length;i+=2){
            if(nametomsg.containsKey(mstr[i])){
                ArrayList<Message> pmsg=nametomsg.get(mstr[i]);
                pmsg.add(new Message(mstr[i+1]));
                nametomsg.remove(mstr[i]);
                nametomsg.put(mstr[i],pmsg);
            }else{
                ArrayList<Message> ss=new ArrayList<Message>();
                ss.add(new Message(mstr[i+1]));
                nametomsg.put(mstr[i],ss);
            }
        }*/
    }
    public void serverMsg(String str[]){
        int otvet=Integer.parseInt(str[1]);
        switch(otvet){
            case 0:
                Ginterface.setText("сообщение отправлено",3000);
                tfmsg.setText("");
                tfname.setText("");
                tfmsg.setMessageText("Сообщение...");
                tfname.setMessageText("Кому:");
                groupnewmessage.setVisible(false);
                pda.rabstol.setVisible(true);
                break;
            case 1:
                Ginterface.setText("игрока с таким именем не существует",3000);
                break;
            case 2:
                Ginterface.setText("вам пришло сообщение",3000);
                Message mmsg=new Message(str[3]);
                mmsg.newmsg=true;
                if(str.length>4)mmsg.zapros=true;
                if(nametomsg.containsKey(str[2])){
                    ArrayList<Message> pmsg=nametomsg.get(str[2]);
                    pmsg.add(mmsg);
                    nametomsg.remove(str[2]);
                    nametomsg.put(str[2],pmsg);
                }else{
                    ArrayList<Message> ss=new ArrayList<Message>();
                    ss.add(mmsg);
                    nametomsg.put(str[2],ss);
                }
             //   file.writeString(str[2]+"/"+str[3]+"/",true);
                break;
        }
    }
    void openVhodiashie(){
        groupvhnames.setVisible(true);
        groupspisok.setVisible(false);
        Iterator<String> names=nametomsg.keySet().iterator();
        for(int i=0;i<lvhname.length;i++){
            if(names.hasNext()){
                String sss=names.next();
                lvhname[i].setText(sss);lvhname[i].pack();
            for(Message mmsg:nametomsg.get(sss)){
                if (mmsg.newmsg){lvhname[i].setColor(Color.YELLOW);break;}
            }
            }
        }
    }
    void openPlMsg(String name){
        groupvhnames.setVisible(false);
        groupvhmsg.setVisible(true);
        for(int i=0;i<lvhmsg.length;i++){
        lvhmsg[i].setText("");
        lvhmsg[i].setVisible(false);
        lvhmsg[i].clearListeners();
        }
        ArrayList<Message>message=nametomsg.get(name);
        if(message!=null){
            Iterator<Message>itm=message.iterator();
            for(int i=0;i<lvhmsg.length;i++){
                if(itm.hasNext()){
                    final Message pmsg=itm.next();
                lvhmsg[i].setText(pmsg.text.substring(0,pmsg.text.length()>55?55:pmsg.text.length())+"...");
                lvhmsg[i].pack();
                    lvhmsg[i].setVisible(true);
                    if(pmsg.newmsg)lvhmsg[i].setColor(Color.YELLOW);
                    final int ii=i;
                    lvhmsg[i].addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            StringTokenizer str = new StringTokenizer(pmsg.text, " ", true);
                            msg.setText(Util.perenosSlov(str,60));
                            msg.pack();
                            msg.setPosition(80,420-msg.getHeight());
                            groupreadmsg.setVisible(true);
                            groupvhmsg.setVisible(false);
                            pmsg.newmsg=false;
                            if(pmsg.zapros){yes.setVisible(true);no.setVisible(true);}
                            lvhmsg[ii].setColor(Color.WHITE);
                            super.clicked(event, x, y);
                        }
                    });
                }
            }
        }
    }
    private  class Message{
        String text;
        boolean newmsg;
        boolean zapros;
        Message(String t){
            text=t;}
    }
}
