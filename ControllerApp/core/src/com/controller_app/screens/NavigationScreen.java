package com.controller_app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.controller_app.Main;
import com.controller_app.helper.Commons;
import com.controller_app.helper.InputHandler;
import com.controller_app.network.MPClient;

/**
 * Created by Alice on 2015-04-28.
 */
public class NavigationScreen extends ScaledScreen{
    private TextButton btnUp, btnDown, btnRight, btnLeft, btnSettings, btnSelect;
    private Table table;
    private TextureAtlas atlas;
    private Skin skin;
    private BitmapFont font;
    private FreeTypeFontGenerator generator;
    private SpriteBatch batch;

    private MPClient mpClient;
    private Main main;
    private InputHandler inputHandler;

    public NavigationScreen(Main m, MPClient mpc){
        super();
        this.main = m;
        mpClient = mpc;
        batch = new SpriteBatch();
        inputHandler = new InputHandler();

        generateFonts(); // call to all generating functions, have to be called in this order!
        generateSkin();
        generateUI();

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

        skin.getFont("default-font").scale(4f);
        table = new Table(skin);

        btnUp = new TextButton("Up", skin);
        btnDown = new TextButton("Down", skin);
        btnLeft = new TextButton("Left", skin);
        btnRight = new TextButton("Right", skin);
        btnSelect = new TextButton("Select", skin);
        btnSettings = new TextButton("Settings", skin);

        btnUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                inputHandler.setUpPressed();
            }
        });
        btnDown.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                inputHandler.setDownPressed();
            }
        });
        btnLeft.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                inputHandler.setLeftPressed();
            }
        });
        btnRight.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                inputHandler.setRightPressed();
            }
        });
        btnSelect.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                inputHandler.setSelectPressed();
            }
        });
        btnUp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("input", "settings pressed");
            }
        });
        table.debug();
        table.row();
        table.add(btnSettings).size(300, 300).padLeft(Commons.WORLD_WIDTH - 200);
        table.row();
        table.add(btnUp).size(300, 300);
        table.row();
        Cell<TextButton> cell = table.add(btnSettings);
        cell.colspan(3);
        table.add(btnLeft).size(300, 300);
        table.add(btnSelect).size(300, 300);
        table.add(btnRight).size(300, 300);
        table.row();
        table.add(btnDown).size(300, 300);

        table.setX(Commons.WORLD_WIDTH / 2 - table.getPrefWidth() / 2);
        table.setY(Commons.WORLD_HEIGHT / 2 - table.getPrefHeight() / 2);

        table.pack();
        stage.addActor(table);

        System.out.println("image: " + table.getPrefWidth() + " , " + table.getPrefHeight());
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Sprite renders
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.end();

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
}
