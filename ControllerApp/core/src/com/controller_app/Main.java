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

        changeScreen(1);
    }

    public void changeScreen(int s) {
        switch (s) {
            case 1:
                Gdx.input.setInputProcessor(menuScreen.getStage());
                setScreen(menuScreen);
                break;
            case 2:

                Gdx.input.setInputProcessor(controllerScreen.getStage());
                Gdx.app.log("olle" , "changed screen");
                setScreen(controllerScreen);
                break;
            default:
                break;
        }
    }
}
