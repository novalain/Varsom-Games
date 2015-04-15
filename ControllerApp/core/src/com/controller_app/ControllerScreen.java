package com.controller_app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import static com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

/**
 *
 */
public class ControllerScreen implements Screen {

    private float accelX, accelY, tiltAngle;
    static private float steeringSensitivity = 0.4f;

    Stage stage;
    TextButton button;
    TextButtonStyle textButtionStyle;
    BitmapFont font;
    Skin skin;
    TextureAtlas buttonAtlas;

    ControllerScreen() {

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        font = new BitmapFont();
        skin = new Skin();
        buttonAtlas = new TextureAtlas(Gdx.files.internal("skins/menuSkin.json"));
        skin.addRegions(buttonAtlas);
        textButtionStyle = new TextButtonStyle();
        textButtionStyle.font = font;
        textButtionStyle.up = skin.getDrawable("up");
        textButtionStyle.down = skin.getDrawable("down");
        textButtionStyle.checked = skin.getDrawable("up");
        button = new TextButton("drive", textButtionStyle);
        button.setPosition(Commons.WORLD_HEIGHT/2, Commons.WORLD_WIDTH/2);
        button.setHeight(200);
        button.setWidth(400);

        stage.addActor(button);


    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //super.render();
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

    protected float updateDeviceRotation() {
        accelX = Gdx.input.getAccelerometerX();
        accelY = Gdx.input.getAccelerometerY();

        tiltAngle = (float) Math.atan2(accelX, accelY);
        tiltAngle -= Math.PI /2;
        tiltAngle *= steeringSensitivity;

        return  tiltAngle;

        //Gdx.app.log("inputhandler", "tiltAngle: " + tiltAngle);
    }

}
