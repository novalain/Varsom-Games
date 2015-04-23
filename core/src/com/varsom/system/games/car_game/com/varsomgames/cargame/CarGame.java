package com.varsom.system.games.car_game.com.varsomgames.cargame;

import com.badlogic.gdx.Game;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.varsom.system.VarsomSystem;
import com.varsom.system.abstract_gameobjects.VarsomGame;
import com.varsom.system.games.car_game.helpers.AssetLoader;
import com.varsom.system.games.car_game.screens.Splash;

import java.util.StringTokenizer;

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

    @Override
    public void handleDataFromClients(Connection c, String s) {

        int carNumber = c.getID();
        StringTokenizer st = new StringTokenizer(s, " ");
        //while (st.hasMoreTokens()) {
        Boolean is_driving = st.nextToken().equalsIgnoreCase("true");
        Float angle = Float.parseFloat(st.nextToken());
        System.out.println("Player " + carNumber + " has the angle: " + angle);
        System.out.println("Does player " + carNumber + " accelerate: " + is_driving);
        //}


    }
}
