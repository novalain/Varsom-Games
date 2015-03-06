package com.varsomgames.cargame;

import com.badlogic.gdx.Game;
import com.varsomgames.cargame.screens.Splash;

public class CarGame extends Game {
    public static final String TITLE="carAce/c'Razee";
    public static final int WIDTH=640,HEIGHT=316; // used later to set window size
	
	@Override
	public void create () {
		setScreen(new Splash());
	}
}
