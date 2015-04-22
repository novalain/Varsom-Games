package com.controller_app;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuScreen extends ScaledScreen {

    private TextButton buttonController;
    private TextButton buttonExit;

    private TextField textField;

    private Texture logo;
    private MPClient mpClient;

    public MenuScreen(Main m, MPClient mpc) {
        super();

        this.main = m;
        mpClient = mpc;

        //logo
        logo = new Texture(Gdx.files.internal("images/logo.png"));

        // font generator
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/arial.ttf"));
        backgroundColor = Color.BLACK;

        //buttonAtlas = new TextureAtlas(Gdx.files.internal("skins/menuSkin.pack"));
        //buttonAtlas = new TextureAtlas(Gdx.files.internal("uiskin/uiskin.json"));
        //skin.addRegions(buttonAtlas);
        Gdx.app.log("fan" , "first");
        skin = new Skin(Gdx.files.internal("uiskin/uiskin.json"));
        Gdx.app.log("fan" , "second");

        Gdx.input.setInputProcessor(stage);

        generateFonts();
        generateTextButtonStyle();

        generateUI();
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
/*        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("up");
        textButtonStyle.down = skin.getDrawable("down");

        textButtonStyle.up = skin.getDrawable("up");
        textButtonStyle.down = skin.getDrawable("down"); */
    }

    void generateUI() {

        buttonController = new TextButton("Controller", skin);
        buttonExit = new TextButton("Exit", skin);


        textField = new TextField("" , skin);
        textField.setWidth(800);
        textField.setHeight(200);
        textField.setPosition(Commons.WORLD_WIDTH / 2 - textField.getWidth() , Commons.WORLD_WIDTH / 2 - textField.getWidth());


        buttonController.setWidth(800);
        buttonController.setHeight(200);
        buttonController.setPosition(Commons.WORLD_WIDTH / 2 - buttonController.getWidth() / 2, Commons.WORLD_HEIGHT / 3 - buttonController.getHeight() / 2);

        buttonExit.setWidth(400);
        buttonExit.setHeight(100);
        buttonExit.setPosition(Commons.WORLD_WIDTH / 2 - buttonExit.getWidth() / 2, 0);

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

        stage.addActor(textField);
        stage.addActor(buttonController);
        stage.addActor(buttonExit);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(backgroundColor.r, backgroundColor.g, backgroundColor.b, backgroundColor.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Sprite renders
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();

        spriteBatch.draw(logo, Commons.WORLD_WIDTH / 2 - logo.getWidth() / 2, Commons.WORLD_HEIGHT - logo.getHeight());

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