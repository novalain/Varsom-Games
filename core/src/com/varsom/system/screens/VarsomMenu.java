package com.varsom.system.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.varsom.system.VarsomSystem;
import com.varsom.system.abstract_gameobjects.VarsomGame;
import com.varsom.system.games.car_game.com.varsomgames.cargame.CarGame;
import com.varsom.system.games.other_game.OtherGame;
import com.varsom.system.network.MPServer;

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
    //private Skin skin2 = new Skin(Gdx.files.internal("data/uiskin.json"));

    private Label connectedClientNames;
    private Label ips;

    private String clientNames;

    protected VarsomSystem varsomSystem;

    private int addedClients = 0;

    public VarsomMenu(VarsomSystem varsomSystem){
        this.varsomSystem = varsomSystem;

    }

    @Override
    public void show() {
        //For every VarsomeGame in the game array create a button
        ips = new Label("Server IP: " + this.varsomSystem.getServerIP(),skin);
        table.add(ips).size(600,90).padBottom(80).row();

        for(int i = 0; i < VarsomSystem.SIZE; i++) {
            switch (VarsomSystem.games[i]){

                //Cargame
                case 1:
                    TextButton buttonPlayCarGame = new TextButton("Start CarGame", skin);

                    buttonPlayCarGame.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            VarsomGame carGame = new CarGame((VarsomSystem) Gdx.app.getApplicationListener());
                            Gdx.app.log("clicked", "pressed CarGame");
                            //((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(1));
                            hide();
                        }
                    });
                    table.add(buttonPlayCarGame).size(450, 90).padBottom(10).row();
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
                    table.add(buttonPlayOtherGame).size(450, 90).padBottom(80).row();
                    break;

                //If something goes wrong
                default:
                    Gdx.app.log("in game array", "wrong game ID");

            }

        }

        //Exit button
        TextButton buttonExit = new TextButton("Exit system", skin);
        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "pressed the EXIT SYSTEM button.");
                Gdx.app.exit();
                dispose();
            }
        });

        table.add(buttonExit).size(450, 90).padBottom(20).row();

        //label that shows all connected players
        clientNames = "Connected players:";
        connectedClientNames = new Label(clientNames,skin);
        connectedClientNames.setWidth(600);
        connectedClientNames.setHeight(90);
        connectedClientNames.setPosition(0, 0);

        table.setFillParent(true);
        stage.addActor(table);
        stage.addActor(connectedClientNames);



        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.12f, 0.12f, 0.12f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        handleClients();

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

    //make sure that all connected clients are displayed in a label
    //if a new client is connected add it
    //if a client is disconnected remove it
    public void handleClients(){
        clientNames = "Connected players:";

        //update the client names label with clients connected at the moment
        for(int i = 0; i < varsomSystem.getServer().getConnections().length; i++){
            //TODO right now the IP is displayed, it should be the name chosen by the player
            clientNames += "\n" + varsomSystem.getServer().getConnections()[i].getRemoteAddressTCP().toString();
        }
        connectedClientNames.setText(clientNames);
    }
}
