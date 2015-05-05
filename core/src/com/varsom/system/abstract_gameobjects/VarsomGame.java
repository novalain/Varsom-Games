package com.varsom.system.abstract_gameobjects;

import com.badlogic.gdx.Game;
import com.esotericsoftware.kryonet.Connection;
import com.varsom.system.games.car_game.screens.GameScreen;

import java.util.StringTokenizer;

public abstract class VarsomGame extends Game {
    //thumbnail
    protected Game varsomSystem;
    protected GameScreen gameScreen;

    public void handleDataFromClients(Connection c, String s) {
    }

    public void setGameScreen(GameScreen gameScreen){

    }

    public GameScreen getGameScreen(){
        return gameScreen;
    }
}
