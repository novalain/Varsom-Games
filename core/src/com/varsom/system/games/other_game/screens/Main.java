package com.varsom.system.games.other_game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Main implements Screen{

    SpriteBatch batch;
    Texture img;

    @Override
    public void show() {
        Gdx.app.log("in OtherGame", "in create");
        batch = new SpriteBatch();
        img = new Texture("other_game_assets/badlogic.jpg");

    }

    @Override
    public void render(float delta) {
        //Gdx.app.log("in OtherGame", "in render");
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, 1, 1);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }

    @Override
    public void hide() {

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
}