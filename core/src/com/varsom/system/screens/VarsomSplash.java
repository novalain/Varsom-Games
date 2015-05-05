package com.varsom.system.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.varsom.system.Commons;
import com.varsom.system.VarsomSystem;

public class VarsomSplash extends ScaledScreen {
    private Texture splashTexture;
    private Image splashImage;
    //private Stage stage;
    protected VarsomSystem varsomSystem;

    public VarsomSplash(VarsomSystem varsomSystem) {
        this.varsomSystem = varsomSystem;
        varsomSystem.setActiveStage(stage);
    }

    @Override
    public void show() {
        splashTexture = new Texture(Gdx.files.internal("system/img/varsomsplash.png"));
        splashImage = new Image(splashTexture);
        splashImage.setX((Commons.WORLD_WIDTH - splashImage.getWidth()) / 2);
        splashImage.setY((Commons.WORLD_HEIGHT - splashImage.getHeight()) / 2);
        // stage = new Stage();
        stage.addActor(splashImage); //adds the image as an actor to the stage
        splashImage.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(0f), Actions.delay(0), Actions.run(new Runnable() {
            @Override
            public void run() {
                ((Game) Gdx.app.getApplicationListener()).setScreen(new VarsomMenu(varsomSystem));
            }
        })));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.137f, 0.121f, 0.125f, 1); //sets clear color to black
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //clear the batch
        stage.act(); //update all actors
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
        splashTexture.dispose();
        stage.dispose();
    }
}