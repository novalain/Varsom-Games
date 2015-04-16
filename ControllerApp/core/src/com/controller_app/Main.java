package com.controller_app;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class Main extends Game {

    private MenuScreen menuScreen;

    @Override
    public void create() {

        Gdx.app.log("olle", "created app");
        menuScreen = new MenuScreen();

        setScreen(menuScreen);
    }
}
