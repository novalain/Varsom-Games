package com.controller_app;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.controller_app.helper.Commons;
import com.controller_app.screens.ConnectionScreen;
import com.controller_app.network.NetworkListener;
import com.controller_app.screens.ControllerScreen;

import java.io.IOException;

import com.controller_app.network.MPClient;
import com.controller_app.screens.NavigationScreen;
import com.controller_app.screens.SettingsScreen;
import com.esotericsoftware.kryonet.Client;
import com.controller_app.screens.StandbyScreen;

public class Main extends Game {

    private ConnectionScreen connectionScreen;
    private NavigationScreen navScreen;
    private SettingsScreen settingsScreen;
    private ControllerScreen controllerScreen;

    private StandbyScreen standbyScreen;
    private MPClient mpClient;

    @Override
    public void create() {

        try {
            mpClient = new MPClient();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gdx.app.log("check", "created app");
        settingsScreen = new SettingsScreen(this);
        connectionScreen = new ConnectionScreen(this, mpClient);
        controllerScreen = new ControllerScreen(this, mpClient);
        navScreen = new NavigationScreen(this, mpClient);
        standbyScreen = new StandbyScreen(this, mpClient);

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
 // TODO: Erase if not needed, left after merge with Dpad branch
           /* case 2:
                //change to the controllerScreen if we shouldn't standby
                //check if the server asks the client to stand by
                if(NetworkListener.standby) {
                    //change to standbyScreen
                    Gdx.app.log("in Main", "standby");
                    changeScreen(3);
                }
                else {
                    //change to the controllerScreen
                    Gdx.input.setInputProcessor(controllerScreen.getStage());
                    Gdx.app.log("screen", "changed to controller");
                    setScreen(controllerScreen);
                }
                menuScreen.check = 2;
                break;
            case 3:
                //change to standbyScreen
                Gdx.input.setInputProcessor(standbyScreen.getStage());
                Gdx.app.log("screen", "changed to standby");
                setScreen(standbyScreen);
                menuScreen.check = 3;
                break;  */

            case Commons.SETTINGS_SCREEN:
                //TODO: Settings Screen
                Gdx.input.setInputProcessor(settingsScreen.getStage());
                setScreen(settingsScreen);

                break;
            case Commons.CONTROLLER_SCREEN:
                Gdx.input.setInputProcessor(controllerScreen.getStage());
                setScreen(controllerScreen);
        }
    }

 /*   public MPClient getMpClient(){
        return mpClient;
    } */

    public Client getClient(){
        return mpClient.client;
    }

    public SettingsScreen getSettingsScreen(){
        return settingsScreen;
    }
}