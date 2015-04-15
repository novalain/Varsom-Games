package com.controller_app;

import com.badlogic.gdx.Game;

public class Main extends Game {
/*
    OrthographicCamera camera;
    Viewport viewport;
*/


	@Override
	public void create () {
/*
        camera = new OrthographicCamera();
        viewport = new StretchViewport(Commons.WORLD_WIDTH,Commons.WORLD_HEIGHT, camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth/2,camera.viewportHeight/2,0);
*/


        setScreen(new MenuScreen());
	}
}
