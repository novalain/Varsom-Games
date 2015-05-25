package com.varsom.system.games.car_game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.varsom.system.Commons;
import com.varsom.system.VarsomSystem;
import com.varsom.system.games.car_game.helpers.AssetLoader;
import com.varsom.system.games.car_game.helpers.KrazyRazyCommons;

public class Splash extends ScaledScreen {
    private Texture splashTexture;
    private Image splashImage;
    private boolean loaded = false;
    protected VarsomSystem varsomSystem;
    private Animation anim;
    private float frameCounter = 0;
    private SpriteBatch spriteBatch;
    private Label loadingText;

    private Image loading;

    public Splash(VarsomSystem varsomSystem) {
        this.varsomSystem = varsomSystem;
    }

    @Override
    public void show() {
        //If your image is not the same size as your screen
        //Gdx.app.log("Screen","width"+Gdx.graphics.getWidth());
        //Gdx.app.log("Screen","height"+Gdx.graphics.getHeight());
        // Set position of splash image
        //anim = GifDecoder.loadGIFAnimation(Animation.PlayMode.LOOP, Gdx.files.internal("system/img/loading_krazy.gif").read());

        spriteBatch = new SpriteBatch();

        BitmapFont font = Commons.getFont(100, Gdx.files.internal("system/fonts/BADABB__.TTF"), KrazyRazyCommons.KRAZY_BLUE, 3f, KrazyRazyCommons.KRAZY_GREEN);
        Label.LabelStyle style = new Label.LabelStyle(font, Color.WHITE);

        splashTexture = new Texture(Gdx.files.internal("car_game_assets/menu/krazyRacyText.png"));
        splashImage = new Image(splashTexture);
        splashImage.setX((Commons.WORLD_WIDTH - splashImage.getWidth()) / 2);
        splashImage.setY(Commons.WORLD_HEIGHT - splashImage.getHeight() - 50);

        loadingText = new Label("Loading ... pizz out", style);
        loadingText.setPosition(Commons.WORLD_WIDTH / 2 - loadingText.getWidth() / 2,  splashImage.getY() - loadingText.getHeight() - 50);

        loading = new Image(new Texture(Gdx.files.internal("system/img/cartoon_pizza_loading.png")));
        loading.setWidth(200f);
        loading.setHeight(200f);
        loading.setPosition(Commons.WORLD_WIDTH / 2 - loading.getWidth() / 2, loadingText.getY() - loading.getHeight() - 50);
        loading.setOrigin(loading.getWidth() / 2, loading.getHeight() / 2 );

        stage.addActor(splashImage); //adds the image as an actor to the stage
        stage.addActor(loadingText);
        stage.addActor(loading);

        splashImage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0.0f), Actions.delay(0), Actions.run(new Runnable() {
            @Override
            public void run() {
                if(!loaded){
                    AssetLoader.load();
                    loaded = true;
                }
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(varsomSystem));
            }
        })));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(KrazyRazyCommons.KRAZY_BLUE_BG.r, KrazyRazyCommons.KRAZY_BLUE_BG.g, KrazyRazyCommons.KRAZY_BLUE_BG.g, KrazyRazyCommons.KRAZY_BLUE_BG.a);
        //Gdx.gl.glClearColor(1.f, 1.f, 1.f, 1.f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //clear the batch

        frameCounter += Gdx.graphics.getDeltaTime();

        //spriteBatch.setProjectionMatrix(camera.combined);

        stage.act(); //update all actors
        stage.draw(); //draw all actors on the Stage.getBatch()

        loading.rotateBy(3);

        //spriteBatch.begin();

//        TextureRegion region = anim.getKeyFrame(frameCounter, true);

  //      spriteBatch.draw(anim.getKeyFrame(frameCounter, true), Commons.WORLD_WIDTH / 2 - new Texture(Gdx.files.internal("system/img/loading_krazy.gif")).getWidth() / 2, loadingText.getY() - new Texture(Gdx.files.internal("system/img/loading_krazy.gif")).getHeight());

      //  spriteBatch.end();

        //Gdx.app.log("Splash", "splashing");
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
        dispose();
    }

    @Override
    public void dispose() {
        splashTexture.dispose();
        stage.dispose();
    }
}
