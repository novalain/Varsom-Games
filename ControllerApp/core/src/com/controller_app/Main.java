package com.controller_app;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.controller_app.helper.Commons;
import com.controller_app.screens.ConnectionScreen;
import com.controller_app.screens.ControllerScreen;

import java.io.IOException;

import com.controller_app.network.MPClient;
import com.controller_app.screens.NavigationScreen;
import com.controller_app.screens.SettingsScreen;

public class Main extends Game {

    private ConnectionScreen connectionScreen;
    private NavigationScreen navScreen;
    private SettingsScreen settingsScreen;
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
        connectionScreen = new ConnectionScreen(this, mpClient);
        controllerScreen = new ControllerScreen(this, mpClient);
        navScreen = new NavigationScreen(this, mpClient);
        settingsScreen = new SettingsScreen(this);

        mpClient.controllerScreen = controllerScreen;

        changeScreen(Commons.CONNECTION_SCREEN);
    }

    public void changeScreen(int s) {
        switch (s) {
            case Commons.CONNECTION_SCREEN:
                Gdx.input.setInputProcessor(connectionScreen.getStage());
                setScreen(connectionScreen);

                break;

            case Commons.NAVIGATION_SCREEN:
                Gdx.input.setInputProcessor(navScreen.getStage());
                setScreen(navScreen);

                break;

            case Commons.SETTINGS_SCREEN:
                //TODO: Settings Screen
                Gdx.input.setInputProcessor(settingsScreen.getStage());
                setScreen(settingsScreen);

                break;
            case Commons.CONTROLLER_SCREEN:
                Gdx.input.setInputProcessor(controllerScreen.getStage());
                setScreen(controllerScreen);

                break;
            default:
                break;
        }
    }

}