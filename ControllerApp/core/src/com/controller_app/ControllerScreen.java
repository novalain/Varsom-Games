package com.controller_app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

/**
 *
 */
public class ControllerScreen extends ScaledScreen {

    private TextButton drive;
    private boolean drive_pressed;

    // Gyro
    private float accelX, accelY, tiltAngle;
    static private float steeringSensitivity = 0.4f;

    public ControllerScreen() {

        drive_pressed = false;

        generateFonts();
        generateTextButtonStyle();
        generateButtons();

    }

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

    @Override
    void generateFonts() {


    }

    @Override
    void generateButtons() {
        drive = new TextButton("Gasa", textButtonStyle);
        drive.setWidth(400);
        drive.setHeight(400);
        drive.setPosition(Commons.WORLD_WIDTH / 2 - drive.getWidth(), Commons.WORLD_HEIGHT / 2 - drive.getHeight());

        drive.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                drive_pressed = true;
            }
        });

    }

    

    @Override
    void generateTextButtonStyle() {
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("up");
        textButtonStyle.down = skin.getDrawable("down");

    }

    protected float updateDeviceRotation() {
        accelX = Gdx.input.getAccelerometerX();
        accelY = Gdx.input.getAccelerometerY();

        tiltAngle = (float) Math.atan2(accelX, accelY);
        tiltAngle -= Math.PI / 2;
        tiltAngle *= steeringSensitivity;

        return tiltAngle;

        //Gdx.app.log("inputhandler", "tiltAngle: " + tiltAngle);
    }
}
