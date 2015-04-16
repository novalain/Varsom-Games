package com.controller_app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 *
 */
public class ControllerScreen extends ScaledScreen {

    private TextButton drive;
    private boolean drive_pressed = false;

    // Gyro
    private float accelX, accelY, tiltAngle;
    static private float steeringSensitivity = 0.4f;

    public ControllerScreen() {

        super();

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

        parameter.color = Color.WHITE;
        parameter.size = 100;
        font = generator.generateFont(parameter);

        generator.dispose();

    }

    @Override
    void generateButtons() {
        drive = new TextButton("Gasa", textButtonStyle);
        drive.setWidth(1000);
        drive.setHeight(1080);
        drive.setPosition(Commons.WORLD_WIDTH / 2, 0);

        drive.addListener(new ClickListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                drive_pressed = true;
                Gdx.app.log("Gasa", "Button is down");

                return drive_pressed;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                drive_pressed = false;
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

    protected boolean getDrive() {

        return drive_pressed;
    }

    protected float getRotation() {
        accelX = Gdx.input.getAccelerometerX();
        accelY = Gdx.input.getAccelerometerY();

        tiltAngle = (float) Math.atan2(accelX, accelY);
        tiltAngle -= Math.PI / 2;
        tiltAngle *= steeringSensitivity;

        return tiltAngle;

        //Gdx.app.log("inputhandler", "tiltAngle: " + tiltAngle);
    }
}
