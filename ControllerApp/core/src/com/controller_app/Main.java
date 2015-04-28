package com.controller_app;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.controller_app.screens.ControllerScreen;
import com.controller_app.screens.MenuScreen;

import java.io.IOException;

import com.controller_app.network.MPClient;

public class Main extends Game {

    private MenuScreen menuScreen;
    private ControllerScreen controllerScreen;
    private MPClient mpClient;

    @Override
    public void create() {

        try {
            mpClient = new MPClient();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gdx.app.log("check", "created app");
        menuScreen = new MenuScreen(this, mpClient);
        controllerScreen = new ControllerScreen(this, mpClient);

        mpClient.controllerScreen = controllerScreen;

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
                Gdx.app.log("screen", "changed screen");
                setScreen(controllerScreen);
                menuScreen.connect();
                break;
            default:
                break;
        }
    }

}
