package GameInterface.Pda;

import GameWorld.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.blackbirds.projectzone.GdxGame;

import java.util.StringTokenizer;

public class Qvests extends Group {
    Image nazad,font1,font2;
    Label qvest[]=new Label[10];
    Label qvesttext,notqvest;
    CorePda pda;
    Qvests(GdxGame game, final CorePda pda){
        setVisible(false);
        this.pda=pda;
        nazad= new Image(new Texture("pda/nazad.png"));
        font1= new Image(new Texture("pda/fontext1.png"));
        font2= new Image(new Texture("pda/fontext2.png"));
        font1.setPosition(87,90);
        font2.setPosition(317,90);
        nazad.setPosition(690,62);
        nazad.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVisible(false);
                pda.rabstol.setVisible(true);
                super.clicked(event, x, y);
            }
        });
        addActor(font1);
        addActor(font2);
        addActor(nazad);

        float ych = 345;
        for (int i = 0; i < qvest.length; i++) {
            qvest[i] = new Label("fak", game.text.linvent);
            qvest[i].setPosition(95, ych);
            ych -= qvest[i].getHeight() - 3;
            addActor(qvest[i]);
            qvest[i].setVisible(false);
        }
        qvesttext = new Label("", game.text.linvent);
        addActor(qvesttext);
        qvesttext.setPosition(330,358);
        notqvest = new Label("нет активных заданий", game.text.linvent);
        addActor(notqvest);
        notqvest.setPosition(320,245);
        notqvest.setColor(Color.RED);
    }
    public void setNameQvests(){
        qvesttext.setVisible(false);
        if(Game.hero.qvests.isEmpty()){
            font1.setVisible(false);font2.setVisible(false);
            notqvest.setVisible(true);
        }else{
            font1.setVisible(true);font2.setVisible(true);
            notqvest.setVisible(false);
        }
        for (int i = 0; i < qvest.length; i++) {
            qvest[i].setVisible(false);
        }
        int n=0;
        for(final Integer ti:Game.hero.qvests.keySet()){
            FileHandle handle = Gdx.files.internal("qvest/qvest"+ti+".txt");
            String qt=handle.readString("windows-1251");
            final String strdialog[]=qt.split("--");
            StringTokenizer str=new StringTokenizer(strdialog[0]," ",true);
            qvest[n].setText(pda.perenosSlov(str,10));
            qvest[n].pack();
            qvest[n].setVisible(true);
            if(n!=0)qvest[n].setPosition(95, qvest[n-1].getY() - qvest[n].getHeight());
            qvest[n].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    setQvestText(strdialog[1]);
                    super.clicked(event, x, y);
                }
            });
            n++;
        }
    }
    public void setQvestText(final String sstr) {
        qvesttext.setVisible(true);
            StringTokenizer str=new StringTokenizer(sstr," ",true);
            qvesttext.setText(pda.perenosSlov(str,30));
            qvesttext.pack();
            qvesttext.setPosition(330, 400 - qvesttext.getHeight());}
}
