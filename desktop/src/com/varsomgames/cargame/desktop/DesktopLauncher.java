package com.varsomgames.cargame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.varsomgames.cargame.CarGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width=CarGame.WIDTH; // sets window width
        config.height=CarGame.HEIGHT;  // sets window height
		new LwjglApplication(new CarGame(), config);
	}
}
