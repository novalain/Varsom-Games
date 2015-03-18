package com.varsomgames.cargame;

import com.badlogic.gdx.Game;

import helpers.AssetLoader;
import Tracks.TestTrack;
import screens.Splash;

public class CarGame extends Game {
    public static final String TITLE="Untitled Game: The ride to Hell!";

    public static final int WIDTH = 800;
    public static final int HEIGHT= 480; // used later to set window size

    public CarGame(){

    }

	@Override
	public void create () {

        //int screenWidth = Gdx.graphics.getWidth();
        //int screenHeight = Gdx.graphics.getHeight();
        //String message = "Widtdh: "+Integer.toString(screenWidth) +"  and Height: "+ Integer.toString(screenHeight);
        //Gdx.app.log("screen size", message);

        // load assets
        AssetLoader.load();
        // start splash screen which will start gamescreen
        setScreen(new Splash());
	}

    @Override
    public void dispose() {
        super.dispose();
        AssetLoader.dispose();
    }
}
