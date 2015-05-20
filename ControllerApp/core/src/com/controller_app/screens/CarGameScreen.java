package com.controller_app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.controller_app.Main;

import com.controller_app.helper_classes.ScaledScreen;
import com.controller_app.network.MPClient;

import com.controller_app.helper.Commons;

/**
 *
 */

public class CarGameScreen extends ScaledScreen {

    private TextButton buttonDrive;
    private TextButton buttonReverse;
    private TextButton buttonHome;
    private TextButton buttonExit;
    private TextButton buttonPause;
    private TextButton btnName;
    private TextButton buttonPlay;

    // Menu stuff
    private Main main;
    private Skin skin;

    private FreeTypeFontGenerator generator;
    private Color backgroundColor;

    private TextureAtlas buttonAtlas;
    private TextButton.TextButtonStyle textButtonStyle;
    private BitmapFont font;


    private boolean drivePressed = false, reversePressed = false;

    private float steeringSensitivity = 0.4f;

    private String packet;
    private String playerName = "";

    private MPClient mpClient;

    private float tiltAngle;

    public CarGameScreen(Main m, MPClient mpc) {

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

        stage.addActor(buttonDrive);
        stage.addActor(buttonReverse);
        stage.addActor(buttonHome);
        stage.addActor(buttonExit);
        stage.addActor(buttonPause);
        stage.addActor(btnName);
        stage.addActor(buttonPlay);

        mpc.carGameScreen = this;
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(backgroundColor.r, backgroundColor.g, backgroundColor.b, backgroundColor.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //changes screen if the server has told us to
        main.handleController();

        printName();

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
        Gdx.app.log("in controllerScreen", "in dispose");

    }

    private void generateFonts() {

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.color = Color.WHITE;
        parameter.size = 100;
        font = generator.generateFont(parameter);

        generator.dispose();
    }

    private void generateUI() {
        buttonDrive = new TextButton("Drive", textButtonStyle);
        buttonDrive.setWidth(1000);
        buttonDrive.setHeight(900);
        buttonDrive.setPosition(Commons.WORLD_WIDTH * 0.5f, 0);

        buttonReverse = new TextButton("Reverse", textButtonStyle);
        buttonReverse.setWidth(1000);
        buttonReverse.setHeight(180);
        buttonReverse.setPosition(Commons.WORLD_WIDTH * 0.5f, 900);

        buttonHome = new TextButton("Menu", textButtonStyle);
        buttonHome.setWidth(300);
        buttonHome.setHeight(300);
        buttonHome.setPosition(Commons.WORLD_WIDTH * 0.2f, Commons.WORLD_HEIGHT * 0.2f);

        buttonPause = new TextButton("Pause", textButtonStyle);
        buttonPause.setWidth(300);
        buttonPause.setHeight(300);
        buttonPause.setPosition(Commons.WORLD_WIDTH * 0.2f, Commons.WORLD_HEIGHT * 0.2f + 300f);

        buttonPlay = new TextButton("Play", textButtonStyle);
        buttonPlay.setWidth(300);
        buttonPlay.setHeight(300);
        buttonPlay.setPosition(Commons.WORLD_WIDTH * 0.2f, Commons.WORLD_HEIGHT * 0.2f + 600f);

        buttonExit = new TextButton("Exit", textButtonStyle);
        buttonExit.setWidth(300);
        buttonExit.setHeight(300);
        buttonExit.setPosition(Commons.WORLD_WIDTH * 0.5f - 150f, Commons.WORLD_HEIGHT - 300f);

        btnName = new TextButton(playerName, textButtonStyle);
        btnName.setWidth(300);
        btnName.setHeight(300);
        btnName.setPosition(Commons.WORLD_WIDTH * 0.2f, Commons.WORLD_HEIGHT * 0.2f - 300f);

        buttonHome.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.input.vibrate(main.getVarsomSystemScreen().getVibTime());
                main.changeScreen(Commons.VARSOM_SYSTEM_SCREEN);
            }
        });

        buttonPause.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.input.vibrate(main.getVarsomSystemScreen().getVibTime());
                //send pause request to server with true
                mpClient.sendPause(true);

            }
        });

        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.input.vibrate(main.getVarsomSystemScreen().getVibTime());
                //send pause request to server with false
                mpClient.sendPause(false);
            }
        });

        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.input.vibrate(main.getVarsomSystemScreen().getVibTime());
                mpClient.sendExit(true);
                //Go back to main menu on the server

            }
        });


        buttonDrive.addListener(new ClickListener() {

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                drivePressed = true;

                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                drivePressed = false;
            }
        });

        buttonReverse.addListener(new ClickListener() {

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                reversePressed = true;



                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                reversePressed = false;
            }
        });
    }



    private void generateTextButtonStyle() {
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("up");
        textButtonStyle.down = skin.getDrawable("down");
    }

    // Sends a boolean of the state of the variable drive
    public boolean getDrive() {

        return drivePressed;
    }

    public boolean getReverse(){

        return reversePressed;

    }

    // Calculates the rotation of the phone
    public float getRotation() {

        float xAxix, yAxix;

        xAxix = Gdx.input.getAccelerometerX();
        yAxix = Gdx.input.getAccelerometerY();

        tiltAngle = (float) (-Math.atan2(yAxix, xAxix));
        tiltAngle *= steeringSensitivity;

        return tiltAngle;
    }

    public void printName(){
        btnName.setText(main.getClient().toString());
    }
}
