package com.varsom.system.games.car_game.com.varsomgames.cargame;

import com.badlogic.gdx.Game;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.varsom.system.VarsomSystem;
import com.varsom.system.abstract_gameobjects.VarsomGame;
import com.varsom.system.games.car_game.gameobjects.Car;
import com.varsom.system.games.car_game.helpers.AssetLoader;
import com.varsom.system.games.car_game.screens.GameScreen;
import com.varsom.system.games.car_game.screens.Splash;

import java.util.StringTokenizer;

public class CarGame extends VarsomGame {

    // TODO Kom på ett namn
    public static final String TITLE= "Crapp!";
    protected VarsomSystem varsomSystem;
    protected GameScreen gameScreen;
    // TODO Sjukt oklart namn här
    public static int ID = 1;

    public CarGame(VarsomSystem varsomSystem){

        this.varsomSystem = varsomSystem;
        varsomSystem.setScreen(new Splash(this.varsomSystem));

        AssetLoader.load();
        gameScreen = null;
    }

	@Override
	public void create () {

	}

    @Override
    public void handleDataFromClients(Connection c, String s) {

        StringTokenizer st = new StringTokenizer(s, " ");

        boolean is_driving = st.nextToken().equalsIgnoreCase("true");
        boolean is_breaking = st.nextToken().equalsIgnoreCase("true");
        float angle = Float.parseFloat(st.nextToken());

        // Updates car in class Car
        gameScreen.getTrack().getCarByConnectionID(c.getID()).handleDataFromClients(is_driving,is_breaking, angle);

    }

    public GameScreen getGameScreen(){
        return gameScreen;
    }

    @Override
    public void setGameScreen(GameScreen gameScreen){
        this.gameScreen = gameScreen;
    }
}
