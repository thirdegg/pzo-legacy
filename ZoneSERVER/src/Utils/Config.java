package Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by 777 on 15.07.2017.
 */
public class Config {
    public static boolean clearbase;
    public static boolean realtest;
    public static int gameversion;
    public Config(){
        Scanner in = null;
        String s = "";
        try {
            in = new Scanner(new File("res/Config.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (in.hasNext()) {
            //	s += in.nextLine() + "\r\n";
            s += in.nextLine();
        }
        in.close();
        String str[];
        str = s.split("-");
        clearbase =Boolean.parseBoolean(str[1]);
        realtest =Boolean.parseBoolean(str[3]);
        gameversion=Integer.parseInt(str[5]);
    }
}
