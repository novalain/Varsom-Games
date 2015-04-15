package com.controller_app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

/**
 *
 */
public class ControllerScreen implements Screen {

    private float accelX, accelY, tiltAngle;
    static private float steeringSensitivity = 0.4f;

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    protected float updateDeviceRotation() {
        accelX = Gdx.input.getAccelerometerX();
        accelY = Gdx.input.getAccelerometerY();

        tiltAngle = (float) Math.atan2(accelX, accelY);
        tiltAngle -= Math.PI /2;
        tiltAngle *= steeringSensitivity;

        return  tiltAngle;

        //Gdx.app.log("inputhandler", "tiltAngle: " + tiltAngle);
    }
}
