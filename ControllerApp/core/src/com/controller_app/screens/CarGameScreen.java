package com.controller_app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
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

    private Image btnAccelerate, btnAcceleratePressed;
    private Image btnReverse, btnReversePressed;
    private Image btnPause;
    private Image btnFire, btnFirePressed;
    private Image btnAmmo1;
    private Image btnAmmo2;
    private Image logo;

    // For current ammo
    private int ammoSelected = 0;

    // Menu stuff
    private Main main;
    private Skin skin;

    private FreeTypeFontGenerator generator;
    private Color backgroundColor;

    private TextureAtlas buttonAtlas;
    private TextButton.TextButtonStyle textButtonStyle;
    private BitmapFont font;

    private boolean drivePressed = false, reversePressed = false, firePressed = false;

    //private float steeringSensitivity = 0.4f;

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
        //backgroundColor = new Color(24.0f/255.f, 102.0f/255.f, 115.0f/255.f, 1.0f);
        backgroundColor = new Color(1.f, 1.f, 1.f, 1.f);

        buttonAtlas = new TextureAtlas(Gdx.files.internal("skins/menuSkin.pack"));
        skin.addRegions(buttonAtlas);

        generateFonts();
        generateTextButtonStyle();
        generateUI();

        stage.addActor(btnAccelerate);
        stage.addActor(btnAcceleratePressed);
        stage.addActor(btnReverse);
        stage.addActor(btnReversePressed);
        stage.addActor(btnPause);
        stage.addActor(btnFire);
        stage.addActor(btnFirePressed);
        stage.addActor(logo);
        stage.addActor(btnAmmo1);
        stage.addActor(btnAmmo2);

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

        stage.act();
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

        btnAccelerate = new Image(new Texture(Gdx.files.internal("cargame_controller/krazyRacyAccelerate.png")));
        btnAccelerate.setPosition(Commons.WORLD_WIDTH - btnAccelerate.getWidth() - 10, Commons.WORLD_HEIGHT - btnAccelerate.getHeight() - 5);

        btnAcceleratePressed = new Image(new Texture(Gdx.files.internal("cargame_controller/krazyRacyAcceleratePressed.png")));
        btnAcceleratePressed.setPosition(Commons.WORLD_WIDTH - btnAccelerate.getWidth() - 10, Commons.WORLD_HEIGHT - btnAccelerate.getHeight() - 5);
        btnAcceleratePressed.addAction(Actions.sequence(Actions.alpha(0.0f, 0.0f)));

        btnReverse = new Image(new Texture(Gdx.files.internal("cargame_controller/krazyRacyReverse.png")));
        btnReverse.setPosition(Commons.WORLD_WIDTH - btnReverse.getWidth() - btnAccelerate.getWidth()/4 + 50, 10);

        btnReversePressed = new Image(new Texture(Gdx.files.internal("cargame_controller/krazyRacyReversePressed.png")));
        btnReversePressed.setPosition(Commons.WORLD_WIDTH - btnReverse.getWidth() - btnAccelerate.getWidth()/4 + 50, 10);
        btnReversePressed.addAction(Actions.sequence(Actions.alpha(0.0f, 0.0f)));

        btnPause = new Image(new Texture(Gdx.files.internal("cargame_controller/krazyRacyPause.png")));
        btnPause.setPosition(Commons.WORLD_WIDTH / 2 - btnPause.getWidth()/2, Commons.WORLD_HEIGHT / 2 - btnPause.getHeight() / 2 );

        btnFire = new Image(new Texture(Gdx.files.internal("cargame_controller/krazyRacyFire.png")));
        btnFire.setPosition(50, Commons.WORLD_HEIGHT - btnFire.getHeight() - 50);

        btnFirePressed = new Image(new Texture(Gdx.files.internal("cargame_controller/krazyRacyFirePressed.png")));
        btnFirePressed.setPosition(50, Commons.WORLD_HEIGHT - btnFire.getHeight() - 50);
        btnFirePressed.addAction(Actions.sequence(Actions.alpha(0.0f, 0.0f)));

        logo = new Image(new Texture(Gdx.files.internal("cargame_controller/krazyRacyText.png")));
        logo.setPosition(Commons.WORLD_WIDTH / 2 - logo.getWidth() / 2, Commons.WORLD_HEIGHT/2 - logo.getHeight() / 2 + 340);

        btnAmmo1 = new Image(new Texture(Gdx.files.internal("cargame_controller/krazyRacyAmmo.png")));
        btnAmmo2 = new Image(new Texture(Gdx.files.internal("cargame_controller/krazyRacyAmmo.png")));

        btnAmmo1.setPosition(50, 50);
        btnAmmo2.setPosition(btnAmmo1.getWidth() + 50, 50);
        btnAmmo1.setOrigin(btnAmmo1.getWidth() / 2, btnAmmo1.getHeight() / 2);
        btnAmmo2.setOrigin(btnAmmo2.getWidth()/2, btnAmmo2.getHeight()/2);
        btnAmmo2.setScale(0.5f);

        btnPause.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.input.vibrate(Commons.VIBRATION_TIME);
                //send pause request to server with true
                mpClient.sendPause(true);

            }
        });

        btnAcceleratePressed.addListener(new ClickListener() {

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                drivePressed = true;
                btnAcceleratePressed.addAction(Actions.alpha(1.f, 0.f));
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                drivePressed = false;
                btnAcceleratePressed.addAction(Actions.alpha(0.f, 0.f));
            }
        });

        btnReversePressed.addListener(new ClickListener() {

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                reversePressed = true;
                btnReversePressed.addAction(Actions.alpha(1.f, 0.f));
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                reversePressed = false;
                btnReversePressed.addAction(Actions.alpha(0.f, 0.f));
            }
        });

        btnFirePressed.addListener(new ClickListener() {

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                System.out.println("BOOOM!!!!");
                firePressed = true;
                btnFirePressed.addAction(Actions.alpha(1.f, 0.f));
                return true;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                firePressed = false;
                btnFirePressed.addAction(Actions.alpha(0.f, 0.f));
            }

        });

        btnAmmo1.addListener(new ClickListener() {

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                if(ammoSelected == 1){

                    ammoSelected = 0;

                    // Scale down
                    btnAmmo2.addAction(Actions.sequence(Actions.scaleTo(0.5f, 0.5f, 0.3f)));
                    // Scale up
                    btnAmmo1.addAction(Actions.sequence(Actions.scaleTo(1.0f, 1.0f, 0.3f)));

                }

                return true;

            }

        });

        btnAmmo2.addListener(new ClickListener() {

            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                if(ammoSelected == 0){

                    ammoSelected = 1;

                    // Scale down
                    btnAmmo1.addAction(Actions.sequence(Actions.scaleTo(0.5f, 0.5f, 0.3f)));
                    // Scale up
                    btnAmmo2.addAction(Actions.sequence(Actions.scaleTo(1.0f, 1.0f, 0.3f)));

                }

                return true;

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

        return tiltAngle;
    }

    public void printName(){
        //btnName.setText(main.getClient().toString());
    }
}
