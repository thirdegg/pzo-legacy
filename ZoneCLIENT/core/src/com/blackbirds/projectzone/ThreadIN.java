package com.blackbirds.projectzone;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class ThreadIN extends Thread {
    public Queue<String> sQueue = new ArrayBlockingQueue<String>(100);
    BufferedInputStream bis;
    private String str = null;
    boolean lost;
    String lostmsg;

    public ThreadIN(Socket s) {
        try {
            bis = new BufferedInputStream(s.getInputStream(), 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.start();
    }

    @Override
    public void run() {
        while (true) {
            byte buf[] = new byte[1024];
            int lenght = 0;
            try {
                lenght = bis.read(buf);
            } catch (IOException e) {
                return;
            }
            if (lenght == -1) break;
            try {
                str = new String(buf, "UTF-8");
                System.out.println("пришло сообщение-" + str);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String msg[] = str.split("\0");
            // (прорблема последнего \0 решена)
            if(msg.length==0){lostmsg+="\0";msg=lostmsg.split("\0");
            lostmsg=null;lost=false;}
            if(lostmsg!=null){msg[0]=lostmsg+msg[0];lostmsg=null;}
            // из за русских букв выходит str.lenght < lenght (проблемма решена)
            if(lenght<str.length()){
            if(str.charAt(lenght-1)=='\0')lost=false;else lost=true;
            }
            else{if(str.charAt(str.length()-1)=='\0')lost=false;else lost=true;}

            if(!lost){
            for (int i = 0; i < msg.length; i++) {
                sQueue.add(msg[i]);
            }
            }else{
                for (int i = 0; i < msg.length-1; i++) {
                    sQueue.add(msg[i]);
                }
                lostmsg=msg[msg.length-1];
            }
        }
    }
}
