package com.controller_app;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import java.io.IOException;

public class Main extends Game {

    private MenuScreen menuScreen;
    private ControllerScreen controllerScreen;
    private MPClient mpClient;

    @Override
    public void create() {

        //new Thread(new Runnable() {

            //@Override
            //public void run() {
                try {
                    mpClient = new MPClient();
                } catch (IOException e) {
                    e.printStackTrace();
                }
          //  }
        //}).start(); // And, start the thread running


        Gdx.app.log("check", "created app");
        menuScreen = new MenuScreen(this, mpClient);
        controllerScreen = new ControllerScreen(this, mpClient);

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
                Gdx.app.log("check", "changed screen");
                setScreen(controllerScreen);
                menuScreen.connect();

                for(int i = 0; i < 10; i++){
                    controllerScreen.sendPacket();
                }

                break;
            default:
                break;
        }
    }

    public void networkThread() {
        new Thread(new Runnable() {
            public void run() {


            }
        }).start();
    }
}
