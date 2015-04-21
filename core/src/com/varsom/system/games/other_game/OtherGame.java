package com.varsom.system.games.other_game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.varsom.system.abstract_gameobjects.VarsomGame;
import com.varsom.system.games.other_game.screens.Splash;

public class OtherGame extends VarsomGame {
    public static int ID = 2;
    protected Game varsomSystem;

    public OtherGame(Game varsomSystem){
        Gdx.app.log("CarGame", "Creates cargame");
        this.varsomSystem = varsomSystem;
        //Gdx.app.log("CarGame", "Creates cargame");
        varsomSystem.setScreen(new Splash());

    }

    @Override
    public void create () {
    }

    @Override
    public void render () {
    }
}