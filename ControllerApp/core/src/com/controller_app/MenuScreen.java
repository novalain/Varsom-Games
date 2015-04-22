package com.controller_app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuScreen extends ScaledScreen {

    private TextButton buttonController;
    private TextButton buttonExit;
    private TextField textField;

    private Table table;

    private TextureAtlas atlas;

    private Texture logo;
    private MPClient mpClient;

    public MenuScreen(Main m, MPClient mpc) {
        super();

        this.main = m;
        mpClient = mpc;

        //logo
        logo = new Texture(Gdx.files.internal("images/logo.png"));

        // font generator

        generateFonts();
        // skin = new Skin(Gdx.files.internal("uiskin/uiskin.json"));
        try {
            //skin = new Skin();
            //skin.addRegions(new TextureAtlas(Gdx.files.internal("uiskin/uiskin.atlas")));
            atlas = new TextureAtlas(Gdx.files.internal("uiskin/uiskin.atlas"));
            skin = new Skin();
            skin.addRegions(atlas);
            skin.add("default-font", font, BitmapFont.class);
            skin.load(Gdx.files.internal("uiskin/uiskin.json"));
            //skin = new Skin(Gdx.files.internal("uiskin/uiskin.json"), atlas);
        } catch (Exception E) {
            Gdx.app.log("fan", "failed reading it");
        }
        Gdx.input.setInputProcessor(stage);
        //generateTextButtonStyle();

        generateUI();
    }

    @Override
    void generateFonts() {
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/arial.ttf"));
        parameter.color = Color.WHITE;
        parameter.size = 100;
        font = generator.generateFont(parameter);

        try {
            skin.add("default-font", font);
        } catch (Exception e) {
            Gdx.app.log("fail", "failed adding font");
        }
        generator.dispose();
    }

    @Override
    void generateTextButtonStyle() {
/*        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("up");
        textButtonStyle.down = skin.getDrawable("down");

        textButtonStyle.up = skin.getDrawable("up");
        textButtonStyle.down = skin.getDrawable("down"); */
    }

    void generateUI() {

        skin.getFont("default-font").scale(4f);
        table = new Table(skin);

        Image image = new Image(logo);
        buttonController = new TextButton("Connect Controller", skin);
        buttonExit = new TextButton("Exit", skin);
        textField = new TextField("ip:", skin);

        buttonController.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.changeScreen(2);
            }
        });

        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
                dispose();
            }
        });

        // Add everything to the table
        table.add(image).padTop(10).padBottom(40).row();
        table.add(textField).size(800, 200).padBottom(20).row();
        table.add(buttonController).size(800, 200).padBottom(100).row();
        table.add(buttonExit).size(800, 200).row();

        table.setFillParent(true);
        stage.addActor(table);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Sprite renders
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();

        spriteBatch.end();

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

    // Connect to server
    public void connect() {

        mpClient.connectToServer(textField.getText());
        Gdx.app.log("ipadress", textField.getText());
    }
}