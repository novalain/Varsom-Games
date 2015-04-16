package com.controller_app;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class Main extends Game {

    private MenuScreen menuScreen = new MenuScreen();

    @Override
    public void create() {

        setScreen(menuScreen);
    }
}
