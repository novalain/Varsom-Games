package com.varsom.system.games.car_game.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

import com.varsom.system.games.car_game.gameobjects.Car;
import com.varsom.system.games.car_game.screens.GameScreen;
/**
 * Created by Alice on 2015-03-12.
 */


/**
 * Created by Alice on 2015-03-11.
 * Handlig input such as gyro and tough event
 */
public class InputHandler implements InputProcessor{

    float accelY = Gdx.input.getAccelerometerY();
    private Car car;

    // Ask for a reference to the car when InputHandler is created.
    public InputHandler(Car car) {
        this.car = car;

    }

    // Function to check if screen is touched
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return true; // return true to say there's been a touch event
    }

    // We don't need these right now, but they have to be declared to implement InputProcessor
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
    // Create a function for gyro too!
}
