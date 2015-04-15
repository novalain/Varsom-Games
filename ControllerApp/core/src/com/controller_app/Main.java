package com.controller_app;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Main extends Game {

    OrthographicCamera camera;
    Viewport viewport;

	@Override
	public void create () {

        camera = new OrthographicCamera();
        viewport = new StretchViewport(Commons.WORLD_WIDTH,Commons.WORLD_HEIGHT, camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);

        setScreen(new MenuScreen());
	}
}
