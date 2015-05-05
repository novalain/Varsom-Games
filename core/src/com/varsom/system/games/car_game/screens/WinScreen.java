package com.varsom.system.games.car_game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.varsom.system.Commons;
import com.varsom.system.VarsomSystem;
import com.varsom.system.games.car_game.gameobjects.Car;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class WinScreen extends ScaledScreen {

    private Table table = new Table();

    protected VarsomSystem varsomSystem;

    private ArrayList<String> carOrder;

    //TODO Load files from AssetLoader

    private Skin skin = new Skin(Gdx.files.internal("car_game_assets/skins/menuSkin.json"),
            new TextureAtlas(Gdx.files.internal("car_game_assets/skins/menuSkin.pack")));

    private TextButton btnOK;
    private Label result;

    private String playerScores;

    public WinScreen(VarsomSystem varsomSystem, String names) {
        this.varsomSystem = varsomSystem;
        carOrder = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(names, "\n");
        while(st.hasMoreTokens()){
            carOrder.add(st.nextToken());
        }
        //carList = varsomSystem.getActiveGame().getGameScreen().getTrack().getCars();
        //Gdx.app.log("carList-length", carList.length());
    }

    @Override
    public void show() {
        btnOK = new TextButton("OK", skin);
        btnOK.setPosition(Commons.WORLD_WIDTH / 2 - btnOK.getWidth() / 2, Commons.WORLD_HEIGHT * 0.2f);

        btnOK.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "pressed the OK button.");
                varsomSystem.getMPServer().setJoinable(true);
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(varsomSystem));
            }
        });

        BitmapFont fontType = new BitmapFont();
        fontType.scale(2.f);
        Label.LabelStyle style = new Label.LabelStyle(fontType, Color.WHITE);

        //label that shows all connected players
        //playerScores = ": Name : Time/Score/Dist : Knockouts :\n";
        //result = new Label(playerScores, style);
        result = new Label(carOrder.get(0) + " is VICTORIOUS!!", style);
        result.setPosition(Commons.WORLD_WIDTH / 2 - result.getWidth() / 2, Commons.WORLD_HEIGHT * 0.8f - result.getHeight());

        stage.addActor(result);
        stage.addActor(btnOK);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(122 / 255.0f, 209 / 255.0f, 255 / 255.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        /*for(Car car : carList) {
            //Ranking order
            playerScores += varsomSystem.getServer().getConnections()[car.getID()].toString() + " : ";
            //Points or time
            playerScores += car.getTraveledDistance() + " : ";
            //Knockouts
            playerScores += "- : ";
        }*/

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

/*

public class WinScreen extends ScaledScreen {

    public void render(){

        Gdx.gl.glClearColor(0.12f, 0.12f, 0.12f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        for(Car car : carList) {
            //Ranking order
            players += varsomSystem.getServer().getConnections()[car.getID()].toString() + " : ";
            //Points or time
            players += car.getTraveledDistance() + " : ";
            //Knockouts
            players += "- : ";
            //OK button

        }

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
*/