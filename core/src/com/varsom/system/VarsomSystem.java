package com.varsom.system;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.varsom.system.games.car_game.com.varsomgames.cargame.CarGame;
import com.varsom.system.games.car_game.screens.MainMenu;
import com.varsom.system.games.other_game.OtherGame;
import com.varsom.system.screens.VarsomMenu;
import com.varsom.system.screens.VarsomSplash;

public class VarsomSystem extends /*ApplicationAdapter*/Game {
    public static final String TITLE= "Varsom-System";

    public static final int WIDTH = 800;
    public static final int HEIGHT= 480; // used later to set window size

    //an array that stores the games IDs
    public static final int SIZE = 2;
    public static int[] games = new int[SIZE];

    public VarsomSystem(){

    }

    @Override
	public void create () {
        setScreen(new VarsomSplash());
        //setScreen(new VarsomMenu());

        //TODO ful-lösning, vi borde inte behöva berätta vilka spel eller hur många utan systemet ska känna av det
        //Handle games
        //For CarGame
        games[0] = CarGame.ID;

        //For OtherGame
        games[1] = OtherGame.ID;
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}


