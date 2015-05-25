package com.controller_app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.controller_app.Main;
import com.controller_app.helper.Commons;
import com.controller_app.helper.DPad;
import com.controller_app.helper_classes.ScaledScreen;
import com.controller_app.network.MPClient;

public class VarsomSystemScreen extends ScaledScreen {

    private TextureAtlas atlas;
    private Skin skin;
    private BitmapFont font;
    private FreeTypeFontGenerator generator;

    private Label playerNameLabel;

    private MPClient mpClient;
    private Main main;

    // TODO ONLY to get player name, function should be static
    private SettingsScreen settingsScreen;

    // temporary solution with Images as masks
    private Image btnDPad, btnSettings, btnSelect, btnBack;
    private Image logo, maskUp, maskDown, maskRight, maskLeft;

    public VarsomSystemScreen(Main m, MPClient mpc, SettingsScreen setScreen) {
        super();
        this.main = m;
        mpClient = mpc;
        settingsScreen = setScreen;

        generateFonts(); // call to all generating functions, have to be called in this order!
        generateSkin();
        generateUI();

        stage.addActor(playerNameLabel);
        stage.addActor(btnDPad);
        stage.addActor(btnSettings);
        stage.addActor(btnSelect);
        stage.addActor(btnBack);
        stage.addActor(logo);
        stage.addActor(maskUp);
        stage.addActor(maskDown);
        stage.addActor(maskRight);
        stage.addActor(maskLeft);
    }

    void generateFonts() {
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/arial.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.color = Color.WHITE;
        parameter.size = 100;
        font = generator.generateFont(parameter);

        try {
            skin.add("default-font", font);
        } catch (Exception e) {
            Gdx.app.log("font", "failed adding font");
        }
        generator.dispose();
    }

    void generateSkin() {
        try {
            atlas = new TextureAtlas(Gdx.files.internal("uiskin/uiskin.atlas"));
            skin = new Skin();
            skin.addRegions(atlas);
            skin.add("default-font", font, BitmapFont.class);
            skin.load(Gdx.files.internal("uiskin/uiskin.json"));
        } catch (Exception E) {
            Gdx.app.log("font", "failed reading");
        }
    }

    void generateUI() {
        // The dPad-image
        btnDPad = new Image(new Texture(Gdx.files.internal("system/img/varsom_dpad_gray_center_25.png")));
        btnDPad.setPosition(40, 102);

        BitmapFont font = Commons.getFont(102, Gdx.files.internal("system/fonts/Impact.ttf"));
        Label.LabelStyle style = new Label.LabelStyle(font, Color.DARK_GRAY);

        // String playerName = settingsScreen.getPlayerName(); ??
        playerNameLabel = new Label(settingsScreen.getPlayerName(), style);
        playerNameLabel.setPosition(Commons.WORLD_WIDTH - playerNameLabel.getWidth() - 30, Commons.WORLD_HEIGHT - playerNameLabel.getHeight() - 30);

        // The back button w. functionality
        btnBack = new Image(new Texture(Gdx.files.internal("system/img/varsom_backButton_25.png")));
        btnBack.setPosition(1055, 16);
        btnBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.input.vibrate(Commons.VIBRATION_TIME);
                mpClient.sendDPadData(0,0,false,true);
                mpClient.setMain(main);
            }
        });


        // The select button w. functionality
        btnSelect = new Image(new Texture(Gdx.files.internal("system/img/varsom_checkButton_25.png")));
        btnSelect.setPosition(Commons.WORLD_WIDTH - btnSelect.getWidth(), 400);
        btnSelect.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.input.vibrate(Commons.VIBRATION_TIME);
                mpClient.sendDPadData(0, 0, DPad.SELECT,false);
                return false;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }
        });


        // The settings button w. functionality
        btnSettings = new Image(new Texture(Gdx.files.internal("system/img/varsom_settingsButton_25.png")));
        btnSettings.setPosition(910, 880);
        btnSettings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                main.changeScreen(Commons.SETTINGS_SCREEN);
            }
        });

        // The logo
        logo = new Image(new Texture(Gdx.files.internal("system/img/varsom_logo_dassig_25.png")));
        logo.setPosition(Commons.WORLD_WIDTH - logo.getWidth(), 0);

        // The clickable mask for left
        maskLeft = new Image(new Texture(Gdx.files.internal("system/img/varsom_logo_dassig_25.png")));
        maskLeft.setWidth(300);
        maskLeft.setHeight(300);
        maskLeft.setPosition(40, 420);
        maskLeft.addAction(Actions.alpha(0.0f, 0.0f));
        maskLeft.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.input.vibrate(Commons.VIBRATION_TIME);
                mpClient.sendDPadData(DPad.LEFT, 0, false, false);
                return false;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }
        });


        // The clickable mask for right
        maskRight = new Image(new Texture(Gdx.files.internal("system/img/varsom_logo_dassig_25.png")));
        maskRight.setWidth(300);
        maskRight.setHeight(300);
        maskRight.setPosition(653, 420);
        maskRight.addAction(Actions.alpha(0.0f, 0.0f));
        maskRight.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.input.vibrate(Commons.VIBRATION_TIME);
                mpClient.sendDPadData(DPad.RIGHT, 0, false, false);
                return false;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }
        });

        // The clickable mask for up
        maskUp = new Image(new Texture(Gdx.files.internal("system/img/varsom_logo_dassig_25.png")));
        maskUp.setWidth(300);
        maskUp.setHeight(300);
        maskUp.setPosition(340, 700);
        maskUp.addAction(Actions.alpha(0.0f, 0.0f));
        maskUp.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.input.vibrate(Commons.VIBRATION_TIME);
                mpClient.sendDPadData(0, DPad.UP, false, false);
                return false;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }
        });


        // The clickable mask for down
        maskDown = new Image(new Texture(Gdx.files.internal("system/img/varsom_logo_dassig_25.png")));
        maskDown.setWidth(300);
        maskDown.setHeight(300);
        maskDown.setPosition(340, 120);
        maskDown.addAction(Actions.alpha(0.0f, 0.0f));
        maskDown.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.input.vibrate(Commons.VIBRATION_TIME);
                mpClient.sendDPadData(0, DPad.DOWN, false, false);
                return false;
            }

            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            }
        });
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0f, 0f, 0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //changes screen if the server has told us to
        main.handleController();

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
        font.dispose();
        atlas.dispose();
        skin.dispose();
    }
}