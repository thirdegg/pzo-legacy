package com.blackbirds.projectzone.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.blackbirds.projectzone.GdxGame;

import java.awt.*;


public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title="Project zone 2d online";
		//разкомментировать для включения полноэкранного режима
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		config.width = (int) screenSize.getWidth();
	   config.height = (int) screenSize.getHeight();
		config.fullscreen=true;
	//		config.width = 1280;
	//		config.height = 720;
		new LwjglApplication(new GdxGame(), config);
	}
}