package com.controller_app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 *
 */
public class ControllerScreen extends ScaledScreen {

    private TextButton drive;
    private TextButton home;
    private TextButton buttonExit;

    private boolean drive_pressed = false;

    private String packet;

    private MPClient mpClient;

    private float tiltAngle;

    public ControllerScreen(Main m, MPClient mpc) {

        super();

        this.main = m;
        mpClient = mpc;

        skin = new Skin();

        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/arial.ttf"));
        backgroundColor = new Color(0.0f, 0.0f, 7.0f, 1.0f);

        buttonAtlas = new TextureAtlas(Gdx.files.internal("skins/menuSkin.pack"));
        skin.addRegions(buttonAtlas);

        generateFonts();
        generateTextButtonStyle();
        generateUI();

        stage.addActor(drive);
        stage.addActor(home);
        stage.addActor(buttonExit);
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

        buttonExit = new TextButton("Exit", textButtonStyle);
        buttonExit.setWidth(300);
        buttonExit.setHeight(300);
        buttonExit.setPosition(Commons.WORLD_WIDTH * 0.5f - 150f, Commons.WORLD_HEIGHT - 300f);

        home.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                main.changeScreen(1);

            }
        });

        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                Gdx.app.log("in ControllerScreen", "pressed Exit");
                //Go back to main menu on the server

            }
        });


        drive.addListener(new ClickListener() {

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                drive_pressed = true;

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

    // Sends a boolean of the state of the variable drive

    protected boolean getDrive() {

        return drive_pressed;
    }


    // Calculates the rotation of the phone
    protected float getRotation() {

        float Xaxis, Yaxis;

        Xaxis = Gdx.input.getAccelerometerX();
        Yaxis = Gdx.input.getAccelerometerY();

        tiltAngle = (float) (Math.atan2(Yaxis, Xaxis));
        tiltAngle -= Math.PI / 2;

        tiltAngle = (float) Math.toDegrees(tiltAngle);

        if (tiltAngle < -200 && tiltAngle > -270) {
            tiltAngle += 360;
        }

        return tiltAngle;

        //Gdx.app.log("inputhandler", "tiltAngle: " + tiltAngle);
    }

    // Converts boolean drive and float rotation to one sting and calls upon a function to send the packet to the server
    public void sendPacket() {

        packet = Boolean.toString(getDrive()) + " " + Float.toString(getRotation());

        Gdx.app.log("send", packet);

        mpClient.sendPacket(packet);

    }

}
