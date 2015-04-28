package com.controller_app.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.controller_app.helper.Commons;

public abstract class ScaledScreen implements Screen {

    protected Stage stage;

    protected OrthographicCamera camera;
    protected Viewport viewport;

    public ScaledScreen() {

        camera = new OrthographicCamera();
        viewport = new StretchViewport(Commons.WORLD_WIDTH, Commons.WORLD_HEIGHT, camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);

        stage = new Stage();

        stage.getViewport().setCamera(camera);
    }

    public Stage getStage() {
        return stage;
    }

}
