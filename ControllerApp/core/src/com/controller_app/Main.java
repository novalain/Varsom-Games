package com.controller_app;

import com.badlogic.gdx.Game;

public class Main extends Game {

    private MenuScreen menuScreen = new MenuScreen();

    @Override
    public void create() {

        setActiveScreen(1);
    }

    public void setActiveScreen(int screen) {
        switch (screen) {
            case 1:
                setScreen(menuScreen);
                break;
            case 2:
                break;
            default:
                break;
        }
    }
}
