package com.controller_app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MenuScreen implements Screen {

    private Stage stage;
    private TextButton button;
    private TextButtonStyle textButtonStyle;
    private Skin skin;
    private TextureAtlas buttonAtlas;

    private OrthographicCamera camera;
    private Viewport viewport;

    // nice fonts
    private BitmapFont font;
    private BitmapFont font12pt;
    private BitmapFont font24pt;
    private BitmapFont font50pt;
    private BitmapFont font120pt;

    private FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/arial.ttf"));
    private FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

    // temp
    Color backgroundColor = new Color(0.9f, 0.6f, 0.7f, 1.0f);
    int clicks = 0;

    public MenuScreen() {

        // The viewport
        camera = new OrthographicCamera();
        viewport = new StretchViewport(Commons.WORLD_WIDTH, Commons.WORLD_HEIGHT, camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

        stage = new Stage();

        font = new BitmapFont();
        skin = new Skin();
        buttonAtlas = new TextureAtlas(Gdx.files.internal("skins/menuSkin.pack"));
        skin.addRegions(buttonAtlas);

        generateFonts();
        generateTextButtonStyle();
        generateButtons();

        stage.addActor(button);
        Gdx.input.setInputProcessor(stage);
        stage.getViewport().setCamera(camera);
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

    private void generateFonts(){
        parameter.color = Color.WHITE;
        parameter.size = 12;
        font12pt = generator.generateFont(parameter);
        parameter.size = 24;
        font24pt = generator.generateFont(parameter);
        parameter.size = 50;
        font50pt = generator.generateFont(parameter);
        parameter.size = 120;
        font120pt = generator.generateFont(parameter);

    }

    private void generateTextButtonStyle() {
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = font120pt;
        textButtonStyle.up = skin.getDrawable("up");
        textButtonStyle.down = skin.getDrawable("down");
    }

    private void generateButtons() {
        button = new TextButton("Fuck you!", textButtonStyle);
        button.setWidth(800);
        button.setHeight(200);
        button.setPosition(Commons.WORLD_WIDTH / 2 - button.getWidth() / 2, Commons.WORLD_HEIGHT / 2 - button.getHeight() / 2);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                float rand1 = (float) (255 * Math.random());
                float rand2 = (float) (255 * Math.random());
                float rand3 = (float) (255 * Math.random());

                backgroundColor.set(rand1,rand2,rand3,1.0f);
                clicks++;
                Gdx.app.log("clicked", "pressed the button " + clicks + " many times, motherfucker.");

                // The million Dollar Question: How do we change the background in the upper class?
            }
        });
    }
}