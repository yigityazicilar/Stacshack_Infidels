package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MainGame;
import com.mygdx.game.MainMenu;
import com.mygdx.game.MyGame;
import com.mygdx.game.Logic;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "TypeWarrior";
		config.useGL30 = false;
		config.width = 800;
		config.height = 450;
		config.fullscreen = true;
		new LwjglApplication(new MyGame(), config);
		Logic.run();
	}
}
