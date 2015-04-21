package com.varsom.system.games.car_game.com.varsomgames.cargame;

import com.badlogic.gdx.Game;

import com.badlogic.gdx.Gdx;
import com.varsom.system.VarsomSystem;
import com.varsom.system.abstract_gameobjects.VarsomGame;
import com.varsom.system.games.car_game.helpers.AssetLoader;
import com.varsom.system.games.car_game.screens.Splash;

public class CarGame extends VarsomGame {
    public static final String TITLE= "Crapp!";

    public static final int WIDTH = 800;
    public static final int HEIGHT= 480; // used later to set window size
    protected VarsomSystem varsomSystem;
    public static int ID = 1;

    public CarGame(VarsomSystem varsomSystem){
        Gdx.app.log("CarGame", "Creates cargame");
        this.varsomSystem = varsomSystem;
        //Gdx.app.log("CarGame", "Creates cargame");
        varsomSystem.setScreen(new Splash(this.varsomSystem));

        // load assets
        AssetLoader.load();
    }

	@Override
	public void create () {
        //Gdx.app.log("CarGame", "Creates cargame");
        //varsomSystem.setScreen(new Splash());

        // load assets
        //AssetLoader.load();
	}
/*
    @Override
    public void dispose() {
        super.dispose();
        AssetLoader.dispose();
    }*/
}
