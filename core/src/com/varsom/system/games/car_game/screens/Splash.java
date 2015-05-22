package com.varsom.system.games.car_game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.varsom.system.VarsomSystem;
import com.varsom.system.games.car_game.helpers.AssetLoader;

public class Splash implements Screen {
    private Texture splashTexture;
    private Image splashImage;
    private Stage stage;
    private boolean loaded = false;
    protected VarsomSystem varsomSystem;

    public Splash(VarsomSystem varsomSystem) {
        this.varsomSystem = varsomSystem;
    }

    @Override
    public void show() {
        //If your image is not the same size as your screen
        //Gdx.app.log("Screen","width"+Gdx.graphics.getWidth());
        //Gdx.app.log("Screen","height"+Gdx.graphics.getHeight());
        // Set position of splash image
        splashTexture = new Texture(Gdx.files.internal("car_game_assets/img/splashscreen.png"));
        splashImage = new Image(splashTexture);
        splashImage.setX((Gdx.graphics.getWidth() - splashImage.getWidth()) / 2);
        splashImage.setY((Gdx.graphics.getHeight() - splashImage.getHeight()) / 2);
        stage = new Stage();
        stage.addActor(splashImage); //adds the image as an actor to the stage
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
        Gdx.gl.glClearColor(0.5f, 0.3f, 0.3f, 1); //sets clear color to black
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //clear the batch
        stage.act(); //update all actors
        stage.draw(); //draw all actors on the Stage.getBatch()
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
