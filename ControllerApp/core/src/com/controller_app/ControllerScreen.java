package com.controller_app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

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

        super();

        //drive_pressed = false;

        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/arial.ttf"));
        backgroundColor = new Color(0.0f, 0.0f, 7.0f, 1.0f);

        buttonAtlas = new TextureAtlas(Gdx.files.internal("skins/menuSkin.pack"));
        skin.addRegions(buttonAtlas);

        generateFonts();
        generateTextButtonStyle();
        generateButtons();

        stage.addActor(drive);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(backgroundColor.r, backgroundColor.g, backgroundColor.b, backgroundColor.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
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
        /*
        drive.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                drive_pressed = true;
            }
        });
        */

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
