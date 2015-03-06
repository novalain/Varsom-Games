package com.varsomgames.cargame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.varsomgames.cargame.screens.Splash;

public class CarGame extends Game {
    public static final String TITLE="carAce/c'Razee";
    public static final int WIDTH = 800;
    public static final int HEIGHT= 480; // used later to set window size

	@Override
	public void create () {
        String message = "Widtsdh: "+Integer.toString(WIDTH) +"  and Height: "+ Integer.toString(HEIGHT);
        Gdx.app.log("oskfan", message);
        setScreen(new Splash());
        Gdx.app.log("oskfan", "screen is set.. still in CarGame class");
	}
}
