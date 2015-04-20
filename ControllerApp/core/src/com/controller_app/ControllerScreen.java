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
    private TextButton home;

    private boolean drive_pressed = false;

    // Gyro
    private float Xaxis, Yaxis, tiltAngle;

    public ControllerScreen(Main m) {
        super(m);

        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/arial.ttf"));
        backgroundColor = new Color(0.0f, 0.0f, 7.0f, 1.0f);

        buttonAtlas = new TextureAtlas(Gdx.files.internal("skins/menuSkin.pack"));
        skin.addRegions(buttonAtlas);

        generateFonts();
        generateTextButtonStyle();
        generateUI();

        stage.addActor(drive);
        stage.addActor(home);
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
    void generateUI() {
        drive = new TextButton("Drive", textButtonStyle);
        drive.setWidth(1000);
        drive.setHeight(1080);
        drive.setPosition(Commons.WORLD_WIDTH * 0.5f, 0);

        home = new TextButton("Menu", textButtonStyle);
        home.setWidth(300);
        home.setHeight(300);
        home.setPosition(Commons.WORLD_WIDTH * 0.2f, Commons.WORLD_HEIGHT * 0.3f);

        home.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                main.changeScreen(1);

            }
        });

        drive.addListener(new ClickListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                drive_pressed = true;

                float rot = getRotation();

                Gdx.app.log("Gasa", "Rotations is " + rot);

                return drive_pressed;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
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
        Xaxis = Gdx.input.getAccelerometerX();
        Yaxis = Gdx.input.getAccelerometerY();

        tiltAngle = (float) (Math.atan2(Xaxis, Xaxis));
        tiltAngle -= Math.PI / 2;

        tiltAngle = (float) Math.toDegrees(tiltAngle);

        if (tiltAngle < -200 && tiltAngle > -270) {
            tiltAngle += 360;
        }

        return tiltAngle;

        //Gdx.app.log("inputhandler", "tiltAngle: " + tiltAngle);
    }
}
