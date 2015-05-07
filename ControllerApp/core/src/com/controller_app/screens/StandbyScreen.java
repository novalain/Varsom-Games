package com.controller_app.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.controller_app.Main;
import com.controller_app.helper.Commons;
import com.controller_app.helper_classes.ScaledScreen;
import com.controller_app.network.MPClient;
import com.controller_app.network.NetworkListener;
/**
 * <h1>StandbyScreen</h1>
 * This screen shows if a user tries to connect while
 * a game is already running.
 *
 * @author  VarsomGames
 * @version 1.0
 * @since   2015-05-07
 */
public class StandbyScreen extends ScaledScreen {
    /**
     * @param textField A textfield for the informing text
     * @param table Table collecting all objects on screen and structuring them
     * @param atlas Textureatlas for gathering all textures in the screen
     * @param logo Texture for the logotype
     * @param skin Skin where the textureatlas and fonts are added
     * @param font Bitmapfont for current font
     * @param generator Generates font
     * @param spriteBatch Unused!
     * @param main This refers to the mainfunction
     * @param mpClient This calls to the MPClient to start a new network connection
     */
    private TextField textField;
    private Table table;
    private TextureAtlas atlas;

    private Texture logo;
    private MPClient mpClient;

    private Main main;
    private Skin skin;
    private BitmapFont font;

    private FreeTypeFontGenerator generator;
    private SpriteBatch spriteBatch;

    /**
     * This is the constructor which loads the textures,
     * calls on function generateFonts(), adds the textures to a
     * skin and calls on function generateUI()
     * @param m Calls to the active main function
     * @param mpc Calls to the active mpClient
     */
    public StandbyScreen(Main m, MPClient mpc) {
        super();

        spriteBatch = new SpriteBatch();

        this.main = m;
        mpClient = mpc;

        //logo
        logo = new Texture(Gdx.files.internal("images/logo.png"));

        // font generator
        generateFonts();

        try {
            atlas = new TextureAtlas(Gdx.files.internal("uiskin/uiskin.atlas"));
            skin = new Skin();
            skin.addRegions(atlas);
            skin.add("default-font", font, BitmapFont.class);
            skin.load(Gdx.files.internal("uiskin/uiskin.json"));
        } catch (Exception E) {
            Gdx.app.log("font", "failed reading it");
        }

        generateUI();
    }

    /**
     * This function loads the fonts from assets
     */
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

    /**
     * This function collects the objects to be rendered on the
     * screen in a table and put the table on the stack
     */
    void generateUI() {

        skin.getFont("default-font").scale(4f);
        table = new Table(skin);

        Image image = new Image(logo);
        textField = new TextField("Standby", skin);

        // table.debug();
        table.add(image).padTop(10).padBottom(40).row();
        table.add(textField).size(800, 200).padBottom(20).row();

        table.setX(Commons.WORLD_WIDTH / 2 - table.getPrefWidth() / 2);
        table.setY(Commons.WORLD_HEIGHT / 2 - table.getPrefHeight() / 2);

        table.pack();
        stage.addActor(table);

        System.out.println("image: " + table.getPrefWidth() + " , " + table.getPrefHeight());
    }

    /**
     * Empty function that is called when standbyScreen is called
     */
    @Override
    public void show() {
    }

    /**
     * This function is called once every frame and draws the stage on
     * the screen. If Network.standby is false, it loads another screen by
     * main.changeScreen()
     * @param delta The deltatime in the running program
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();

        //did we connect
        if(NetworkListener.connected){
            //check if the server asks the client to stand by
            if(NetworkListener.standby) {
                Gdx.app.log("in standbyScreen", "standby");
            }
            else {
                Gdx.app.log("in standbyScreen", "don't standby");
                main.changeScreen(2);
            }
        }
        else {
            Gdx.app.log("in standbyScreen", "not connected");
            main.changeScreen(1);
        }
    }

    /**
     * Resizes the screen
     * @param width The width of the screen
     * @param height The height of the screen
     */
    @Override
    public void resize(int width, int height) {
    }

    /**
     * Pauses the rendering
     */
    @Override
    public void pause() {

    }

    /**
     * Resumes the render
     */
    @Override
    public void resume() {

    }

    /**
     * Hides the screen
     */
    @Override
    public void hide() {

    }

    /**
     * Disposes the screen and all its parameters
     */
    @Override
    public void dispose() {

    }
}
