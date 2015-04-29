package com.controller_app;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.controller_app.network.NetworkListener;
import com.controller_app.screens.ControllerScreen;
import com.controller_app.screens.MenuScreen;

import java.io.IOException;

import com.controller_app.network.MPClient;
import com.controller_app.screens.StandbyScreen;

public class Main extends Game {

    private MenuScreen menuScreen;
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
        menuScreen = new MenuScreen(this, mpClient);
        controllerScreen = new ControllerScreen(this, mpClient);
        standbyScreen = new StandbyScreen(this, mpClient);

        mpClient.controllerScreen = controllerScreen;
        mpClient.menuScreen = menuScreen;


        changeScreen(1);

    }

    public void changeScreen(int s) {
        switch (s) {
            case 1:
                //change to the menuScreen
                Gdx.input.setInputProcessor(menuScreen.getStage());
                setScreen(menuScreen);
                menuScreen.check = 1;
                break;
            case 2:
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
                break;
            default:
                break;
        }
    }

    public MenuScreen getMenuScreen(){
        return menuScreen;
    }

}
