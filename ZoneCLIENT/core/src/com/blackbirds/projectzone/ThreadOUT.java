package com.blackbirds.projectzone;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;

public class ThreadOUT {
    BufferedOutputStream bos;

    public ThreadOUT(Socket s) {
        try {
            bos = new BufferedOutputStream(s.getOutputStream(), 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String str) {
        str = str + "\0";
        byte[] bytes = null;
        try {
            bytes = str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        try {
            bos.write(bytes);
            bos.flush();
            System.out.println("отправляем сообщение-" + str);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
}