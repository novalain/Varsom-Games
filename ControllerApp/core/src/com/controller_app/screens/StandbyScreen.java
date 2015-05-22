package com.controller_app.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
    private Table table;
    private TextureAtlas atlas;

    private Texture figure;
    private Label labelStandby;
    private Label labelExplanation;
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

        //figure
        figure = new Texture(Gdx.files.internal("krazy_razy_controller/crazyThingyMad.png"));

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

        //Font
        FileHandle krazyFontFile = Gdx.files.internal("krazy_razy_controller/BADABB__.TTF");
        BitmapFont fontBig = Commons.getFont(200,krazyFontFile,Commons.KRAZY_BLUE,3f,Commons.KRAZY_GREEN);
        BitmapFont fontSmall = Commons.getFont(100,krazyFontFile,Commons.KRAZY_BLUE,3f,Commons.KRAZY_GREEN);
        Label.LabelStyle styleBig = new Label.LabelStyle(fontBig,Color.WHITE);
        Label.LabelStyle styleSmall = new Label.LabelStyle(fontSmall,Color.WHITE);

        //Title
        labelStandby = new Label("Standby", styleBig);
        labelStandby.setPosition(Commons.WORLD_WIDTH /2 - labelStandby.getWidth()/2, Commons.WORLD_HEIGHT/2);

        //Explanation
        String explanation = "A game is already running. You have to wait";
        labelExplanation = new Label(explanation, styleSmall);
        labelExplanation.setPosition(Commons.WORLD_WIDTH / 2 - labelExplanation.getWidth() / 2, Commons.WORLD_HEIGHT / 2 - labelExplanation.getHeight());

        //Mad figure
        float scale = 2.0f;
        Image madFigure = new Image(figure);
        madFigure.setScale(scale,scale);
        madFigure.setPosition(Commons.WORLD_WIDTH/2 - scale*madFigure.getWidth()/2, Commons.WORLD_HEIGHT/2 - labelExplanation.getHeight() - scale*madFigure.getHeight() - labelExplanation.getHeight());

        stage.addActor(labelStandby);
        stage.addActor(labelExplanation);
        stage.addActor(madFigure);
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
        Gdx.gl.glClearColor(24 / 255.0f, 102 / 255.0f, 105 / 255.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();

        //did we connect
        if(NetworkListener.connected){
            //check if the server asks the client to stand by
            if(!NetworkListener.standby) {
                main.changeScreen(Commons.VARSOM_SYSTEM_SCREEN);
            }
        }
        else {
            Gdx.app.log("in standbyScreen", "not connected");
            main.changeScreen(Commons.CONNECTION_SCREEN);
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
