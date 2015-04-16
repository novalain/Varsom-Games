package com.controller_app;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class Main extends Game {

    private MenuScreen menuScreen;
    private ControllerScreen controllerScreen;

    @Override
    public void create() {

        Gdx.app.log("olle", "created app");
        menuScreen = new MenuScreen(this);
        controllerScreen = new ControllerScreen();

        setScreen(menuScreen);
    }

    public void changeScreen(int s) {
        switch (s) {
            case 1:
                setScreen(menuScreen);
                break;
            case 2:
                Gdx.app.log("olle" , "changed screen");
                //setScreen(controllerScreen);
                break;
            default:
                break;
        }
    }
}
