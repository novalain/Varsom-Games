package com.varsomgames.cargame;

import com.badlogic.gdx.Game;

import helpers.AssetLoader;
import screens.Splash;

public class CarGame extends Game {
    public static final String TITLE= "Crapp!";

    public static final int WIDTH = 800;
    public static final int HEIGHT= 480; // used later to set window size

    public CarGame(){

    }

	@Override
	public void create () {
        // start splash screen which will start gamescreen
        setScreen(new Splash());

        // load assets
        AssetLoader.load();
	}

    @Override
    public void dispose() {
        super.dispose();
        AssetLoader.dispose();
    }
}
