package com.controller_app;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;

    final int WORLD_WIDTH = 1920;
    final int WORLD_HEIGHT = 1080;

    OrthographicCamera camera;
    Viewport viewport;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("sony.jpg");

        camera = new OrthographicCamera();
        viewport = new StretchViewport(WORLD_WIDTH,WORLD_HEIGHT, camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
	}

	@Override
	public void render () {

        camera.update();

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.draw(img, 0,0);

		batch.end();
	}

    @Override
    public void resize(int width, int height){
        viewport.update(width,height);
        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
    }
}
