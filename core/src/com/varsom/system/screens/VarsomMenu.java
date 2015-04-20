package com.varsom.system.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.varsom.system.VarsomSystem;
import com.varsom.system.abstract_gameobjects.VarsomGame;
import com.varsom.system.games.car_game.com.varsomgames.cargame.CarGame;
import com.varsom.system.games.other_game.OtherGame;

/**
 * Created by oskarcarlbaum on 16/04/15.
 */
public class VarsomMenu implements Screen {

    private Stage stage = new Stage();
    private Table table = new Table();

    private final int WIDTH = Gdx.graphics.getWidth();
    private final int HEIGHT = Gdx.graphics.getHeight();

    //TODO Load files from a SystemAssetLoader. Also, create a folder and skin for the varsom system
    private Skin skin = new Skin(Gdx.files.internal("system/skins/menuSkin.json"), new TextureAtlas(Gdx.files.internal("system/skins/menuSkin.pack")));

    public VarsomMenu() {
    }

    @Override
    public void show() {
        //For every VarsomeGame in the game array create a button
        for(int i = 0; i < VarsomSystem.SIZE; i++) {
            switch (VarsomSystem.games[i]){

                //Cargame
                case 1:
                    TextButton buttonPlayCarGame = new TextButton("Start CarGame", skin);

                    buttonPlayCarGame.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            VarsomGame carGame = new CarGame((Game) Gdx.app.getApplicationListener());
                            Gdx.app.log("clicked", "pressed CarGame");
                            //((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(1));
                            hide();
                        }
                    });
                    table.add(buttonPlayCarGame).size(400, 75).padBottom(20).row();
                    break;

                //OtherGame
                case 2:
                    TextButton buttonPlayOtherGame = new TextButton("Start OtherGame", skin);

                    buttonPlayOtherGame.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            VarsomGame OtherGame = new OtherGame((Game) Gdx.app.getApplicationListener());
                            Gdx.app.log("clicked", "pressed OtherGame.");
                            hide();
                        }
                    });
                    table.add(buttonPlayOtherGame).size(400, 75).padBottom(20).row();
                    break;

                //If something goes wrong
                default:
                    Gdx.app.log("in game array", "wrong game ID");

            }
        }

        table.setFillParent(true);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(122 / 255.0f, 209 / 255.0f, 255 / 255.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
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
        stage.dispose();
        skin.dispose();
    }
}
