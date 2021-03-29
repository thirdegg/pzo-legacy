package com.blackbirds.projectzone;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Version;
import util.Sounds;
import util.Util;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class GdxGame extends Game {
    public Text text;
    public String myname;
    public boolean android;
    public ThreadIN tin;
    public ThreadOUT tout;
    Socket s = null;

    @Override
    public void create() {
        text = new Text();
        if (Gdx.app.getType() == ApplicationType.Android) android = true;
       // setScreen(new ConnectKorvin(this));
       setScreen(new Connect(this));
    }

    boolean createConnect() {

        try {
      //   s = new Socket("127.0.0.1", 4444);
            s = new Socket("212.109.192.129",4444);

        } catch (UnknownHostException e) {
            //e.printStackTrace();
            return false;
        } catch (IOException e) {
            //e.printStackTrace();
            return false;
        }
        tin = new ThreadIN(s);
        tout = new ThreadOUT(s);
        return true;
    }

    @Override
    public void dispose() {
        super.dispose();
        try {
            if (tin != null) {
                tin.bis.close();
                tout.bos.close();
                s.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
