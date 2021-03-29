package Main;

import javax.swing.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Window extends JFrame {
    JLabel text;
    Box box;

    Window() {
        super("Server");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        box = Box.createVerticalBox();
        box.add(new JLabel("Server start"));
        box.add(Box.createVerticalStrut(10));
        try {
            box.add(new JLabel("Local IP:" + InetAddress.getLocalHost()));
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        box.add(Box.createVerticalStrut(10));
        box.add(new JLabel("Wait new client . . ."));
        text = new JLabel();
        box.add(text);
        setContentPane(box);
        setSize(300, 200);
        this.setVisible(true);
    }

    void setText(String s) {
        text.setText(s);
        box.repaint();
    }
}
