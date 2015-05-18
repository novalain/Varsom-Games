package com.controller_app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.controller_app.Main;
import com.controller_app.helper.Commons;
import com.controller_app.helper_classes.GifDecoder;
import com.controller_app.helper_classes.ScaledScreen;
import com.controller_app.network.MPClient;
import com.controller_app.network.NetworkListener;

public class ConnectionScreen extends ScaledScreen {

    private Table table;
    private TextureAtlas atlas;
    private boolean showGif = false;

    private Texture logo;
    private MPClient mpClient;
    private Label text, text2;
    private Animation anim;
    private float frameCounter = 0;

    private Main main;
    private Skin skin;
    private Image varsomLogo;
    private BitmapFont font;

    private FreeTypeFontGenerator generator;
    private SpriteBatch spriteBatch;

    // TODO Maybe a better name for this variable????
    //public int check;

    public ConnectionScreen(Main m, MPClient mpc) {

        super();

        spriteBatch = new SpriteBatch();

        this.main = m;
        mpClient = mpc;
        mpClient.setConnectionScreen(this);

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


    void generateFonts() {

        //TODO gör ett nytt skin pack, Kallis fattar
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

    void generateUI() {

        skin.getFont("default-font").scale(4f);
        table = new Table(skin);

        Texture splashTexture = new Texture(Gdx.files.internal("system/img/varsomsplash.png"));
        varsomLogo = new Image(splashTexture);

        /*
        Texture settingsTexture = new Texture(Gdx.files.internal("system/img/settings.png"));
        Image settingsImage = new Image(settingsTexture);
        settingsImage.setScale(0.1f);
        settingsImage.setX(Commons.WORLD_WIDTH - settingsTexture.getWidth()*0.1f - 20 );
        settingsImage.setY(Commons.WORLD_HEIGHT - settingsTexture.getHeight()*0.1f - 20 );

        stage.addActor(settingsImage);
        */
        // stage = new Stage();

        // This font is too ugly when scaled up
        BitmapFont fontType = new BitmapFont();
        fontType.scale(1.3f);

        Label.LabelStyle style = new Label.LabelStyle(fontType, Color.WHITE);
        text = new Label("Welcome!", style);
        text2 = new Label("Set up server and touch anywhere to connect", style);
        //text.setPosition(Commons.WORLD_WIDTH / 2 - text.getWidth() / 2, Commons.WORLD_HEIGHT - 200);



        anim = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("system/img/loading.gif").read());

        // Adds listener to the whole screen
        // TODO Obviusly overrides settings clicklistener.. fix this göra så att listener läggs till i en ny actor istället
        stage.addListener(new ClickListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                Gdx.app.log("in MenuScreen", "pressed controller");

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // do something important here, asynchronously to the rendering thread

                        System.out.println("In new thread loading...");
                        connect();
                    }
                }).start();

                text.addAction(Actions.sequence(Actions.alpha(0f, 0.4f)));
                text2.addAction(Actions.sequence(Actions.alpha(0, 0.4f), Actions.run(new Runnable() {

                    @Override
                    public void run() {
                        showGif = true;
                    }

                })));

                return true;
            }

        });

        /*
        // TODO Not working properly because we are listening to event on the whole screen
        settingsImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("in MenuScreen", "pressed settings");
                main.changeScreen(Commons.SETTINGS_SCREEN);
            }
        });
        */
        /*
        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                errorMessage(Commons.DO_YOU_WANT_TO_EXIT);

            }
        });*/


        table.add(varsomLogo).pad(80).row();
        table.add(text).row();
        table.add(text2).row();

        table.setX(Commons.WORLD_WIDTH / 2 - table.getPrefWidth() / 2);
        table.setY(Commons.WORLD_HEIGHT / 2 - table.getPrefHeight() / 2);

        table.pack();
        stage.addActor(table);

        System.out.println("image: " + table.getPrefWidth() + " , " + table.getPrefHeight());
    }

    public void errorMessage(int s){
        //TODO lägg in i main som en klass, om vi har tid
        main.changeScreen(Commons.CONNECTION_SCREEN);

        switch(s){
            case Commons.BAD_CONNECTION:
            new Dialog("Error", skin) {
                {
                    text("It's seems that your connection sucks");
                    button("Ok");
                }

                @Override
                protected void result(final Object object) {

                }

            }.show(stage);
                break;

            case Commons.NO_SERVER_FOUND:
                showGif = false;
                new Dialog("Error", skin) {
                    {
                        text("Could not find a server");
                        button("Ok");
                    }

                    @Override
                    protected void result(final Object object) {


                    }

                }.show(stage);
                break;

            case Commons.DO_YOU_WANT_TO_EXIT:
                new Dialog("Exit", skin){
                    {
                        text("Are you sure you want to exit?");
                        button("No", "hide");
                        button("Yes", "Exit");


                    }

                    @Override
                    protected void result(Object object) {
                        if (object.toString() == "Exit") {
                            Gdx.app.exit();
                            dispose();
                        }
                        else if(object.toString() == "hide") {
                            hide();

                        }
                    }
                }.show(stage);
                break;
        }


    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.137f, 0.121f, 0.125f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        frameCounter += Gdx.graphics.getDeltaTime();

        // Makes logo bounce a little bit so that user knows that game hasn't freezed or crashed :)
        varsomLogo.setPosition(varsomLogo.getX(), varsomLogo.getY() + 0.3f * (float) Math.sin(frameCounter));

        //Check if we have connected we should change to the controllerScreen
        if(NetworkListener.connected) {// && mpClient.client.isConnected()) {
            main.changeScreen(Commons.NAVIGATION_SCREEN);
        }
        // Sprite renders
        spriteBatch.setProjectionMatrix(camera.combined);

        spriteBatch.begin();

        // Shows animation
        if(showGif)
            spriteBatch.draw(anim.getKeyFrame(frameCounter, true), Commons.WORLD_WIDTH / 2 - new Texture(Gdx.files.internal("system/img/loading.gif")).getWidth() / 2, 150);

        spriteBatch.end();

        stage.act(delta);
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
    //TODO lägg in en funktion för att hantera namn
    private void connect() {
        //Get the customized player name
        //if the name id "Player -1" the user hasn't chosen a name and we don't change connection name
        //if the user has changed the name we change the connection name

        if(!main.getSettingsScreen().getPlayerName().equals("Player -1")){
            main.getClient().setName(main.getSettingsScreen().getPlayerName());
        }//else Kryonet gives the automatic name "Connection X"

        // Removed the value from textfield here, because it's always empty anyway
        // TODO-carlbaum Ask user to enter adress manually if not able to find any servers..
        mpClient.connectToServer("");
    }

    // Disconnect from server
    public void disconnect() {
        mpClient.client.stop();
        NetworkListener.connected = false;
    }
}