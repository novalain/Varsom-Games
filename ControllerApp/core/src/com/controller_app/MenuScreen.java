package com.controller_app;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;

public class MenuScreen extends ScaledScreen {

    private TextButton button;

    //new
    private Label lblLogo;
    private Label lblFPS;

    private SelectBox selectBox;
    private SelectBox.SelectBoxStyle selectBoxStyle;
    // end of new

    private Texture logo;

    private ArrayList<String> serverList;

    public MenuScreen(Main m) {
        super(m);

        serverList = new ArrayList<String>();

        //temporary
        serverList.add("Server Connection 1");
        serverList.add("Server Connection 2");
        serverList.add("Server Connection > 9000");

        //logo
        logo = new Texture(Gdx.files.internal("images/logo.png"));

        // font generator
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/arial.ttf"));
        backgroundColor = Color.BLACK;

        buttonAtlas = new TextureAtlas(Gdx.files.internal("skins/menuSkin.pack"));
        skin.addRegions(buttonAtlas);

        generateFonts();
        generateTextButtonStyle();

        Gdx.app.log("fel", "outside generateUI");
        generateUI();

        stage.addActor(button);

        stage.addActor(selectBox);
        Gdx.app.log("fel", "placed selectbox ");
    }

    @Override
    void generateFonts() {
        parameter.color = Color.WHITE;
        parameter.size = 100;
        font = generator.generateFont(parameter);

        generator.dispose();
    }

    @Override
    void generateTextButtonStyle() {
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("up");
        textButtonStyle.down = skin.getDrawable("down");


        Gdx.app.log("fel" , "trying to make style");


        selectBoxStyle = new SelectBox.SelectBoxStyle();
        selectBoxStyle.font = font;
        textButtonStyle.up = skin.getDrawable("up");
        textButtonStyle.down = skin.getDrawable("down");

        Gdx.app.log("fel" , "stylish");
    }

    void generateUI() {
        button = new TextButton("Controller", textButtonStyle);
        // lblFPS = new Label("kokoko" , skin);

        Gdx.app.log("fel", "inside generateUI");

        selectBox = new SelectBox(skin);

        Gdx.app.log("fel" , "set skin");


        selectBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log("ui", selectBox.getSelected().toString());
            }
        });

        Gdx.app.log("fel" , "added change listener");

        selectBox.setItems(serverList);
        selectBox.setSelected(serverList.get(0));

        Gdx.app.log("fel" , "set items");

        selectBox.setPosition(Commons.WORLD_WIDTH / 2 - selectBox.getWidth(), Commons.WORLD_HEIGHT / 3);

        Gdx.app.log("fel" , "set position");

        button.setWidth(800);
        button.setHeight(200);
        button.setPosition(Commons.WORLD_WIDTH / 2 - button.getWidth() / 2, Commons.WORLD_HEIGHT / 3 - button.getHeight() / 2);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Do many great things...

                main.changeScreen(2);
            }
        });


        Gdx.app.log("fel", "exiting generateUI");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(backgroundColor.r, backgroundColor.g, backgroundColor.b, backgroundColor.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // GUI renders
        //lblFPS.setText("fps: " + Gdx.graphics.getFramesPerSecond() );

        // Sprite renders
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();
        spriteBatch.draw(logo, Commons.WORLD_WIDTH / 2 - logo.getWidth() / 2, Commons.WORLD_HEIGHT - logo.getHeight());
        spriteBatch.end();

        //stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
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