package GameInterface.Pda;

import GameWorld.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.blackbirds.projectzone.GdxGame;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * Created by 777 on 04.05.2017.
 */
public class Help extends Group {
        Image nazad,font1,font2;
        Label vopros[]=new Label[10];
        Label helptext;
        CorePda pda;
        String tag;
    XmlReader.Element root;
    Help(GdxGame game, final CorePda pda){
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
        float ych = 365;
        for (int i = 0; i < vopros.length; i++) {
            vopros[i] = new Label("fak", game.text.linvent);
            vopros[i].setPosition(100, ych);
            ych -= vopros[i].getHeight() - 3;
            addActor(vopros[i]);
            vopros[i].setText("");
        }
        helptext = new Label("", game.text.linvent);
        addActor(helptext);
        helptext.setPosition(330,358);


        tag="a";
        try {
        XmlReader xmlReader = new XmlReader();
        FileHandle file = Gdx.files.internal("dialog/help.xml");
        root = xmlReader.parse(file);
            SetText();
    } catch (IOException e) {
        e.printStackTrace();
    } catch (Exception e) {
        e.printStackTrace();
    }


    }
    void SetText(){
        final Array<XmlReader.Element> nodeList=root.getChildrenByName(tag);
        for (int i = 0; i < nodeList.size; i++) {
            StringTokenizer str=new StringTokenizer(nodeList.get(i).getText()," ",true);
            vopros[i].setText(pda.perenosSlov(str,10));
            vopros[i].pack();
            if(i!=0)vopros[i].setPosition(100, vopros[i-1].getY() - vopros[i].getHeight());
                final int nomer=i;
            vopros[i].addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        tochHelp(nodeList.get(nomer));
                        super.clicked(event, x, y);
                    }
                });
        }
    }
    void tochHelp(XmlReader.Element element){
        String attTag=element.getAttribute("tag",null);
        if(attTag!=null)tag=attTag;
        final Array<XmlReader.Element> nodeList=root.getChildrenByName(tag);
        String  tagtext=nodeList.get(0).getText();
        StringTokenizer str=new StringTokenizer(tagtext," ",true);
        helptext.setText(pda.perenosSlov(str,27));
        helptext.pack();
        helptext.setPosition(330, 385 - helptext.getHeight());
    }
}
