package Gm;

import Utils.Rectang;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WorldRec {
    boolean dom;
    private String s = "";
    private List<Rectang> rec = new ArrayList<Rectang>();

    public WorldRec(String filename) {
        Scanner in = null;
        try {
            in = new Scanner(new File("res/" + filename));
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
        float x, y;
        float width, height;
        for (int i = 1; i < str.length; i += 4) {
            //возникает ошибка путой стринг прогнать тхт файл на поиск --
            // исправленно
            if(str[i].length()==0||str[i+1].length()==0||str[i+2].length()==0||str[i+3].length()==0)i++;
            x = Float.parseFloat(str[i]);
            y = Float.parseFloat(str[i + 1]);
            width = Float.parseFloat(str[i + 2]);
            height = Float.parseFloat(str[i + 3]);
            // проверка+4 изза ошибки
            // находим дома
            if (str.length > i + 4) {
                  if(str[i+4].length()==0)i++;
                if (str[i + 4].charAt(0) == 'a') {
                    dom = true;
                    i++;
                }
            }
            Rectang r = new Rectang(x, y, width, height);
            if (dom) {
                r.dom = true;
                dom = false;
            }
            rec.add(r);
        }
    }

    public List<Rectang> getRec() {
        return rec;
    }
}
