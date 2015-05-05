package com.varsom.system.games.car_game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.varsom.system.Commons;
import com.varsom.system.DPad;
import com.varsom.system.screens.ScaledScreen;

import java.util.ArrayList;

import com.varsom.system.VarsomSystem;
import com.varsom.system.games.car_game.com.varsomgames.cargame.CarGame;
import com.varsom.system.games.car_game.gameobjects.BackgroundObject;
import com.varsom.system.network.NetworkListener;
import com.varsom.system.screens.*;

public class MainMenu extends ScaledScreen {

    private Table table = new Table();

    private final int WIDTH = Gdx.graphics.getWidth();
    private final int HEIGHT = Gdx.graphics.getHeight();

    private SpriteBatch spriteBatch = new SpriteBatch();
    private ArrayList<BackgroundObject> objectList;

    // Select buttons
    private ArrayList<TextButton> buttonList = new ArrayList<TextButton>();
    private int selectedButtonIndex = 0;

    protected VarsomSystem varsomSystem;
    // keeping track of which button is hovered by the controller app (Dpad)
    // Button has x and y index, where index 0,0 is the button on top to the left and
    // sizeX, sizeY (where size is amout of buttons) is bottom right
    private Vector2 currentButton = new Vector2(0,0);
    //TODO Load files from AssetLoader

    private Skin defaultSkin = new Skin(Gdx.files.internal("car_game_assets/skins/menuSkin.json"),
            new TextureAtlas(Gdx.files.internal("car_game_assets/skins/menuSkin.pack")));

    private Skin selectedSkin = new Skin(Gdx.files.internal("car_game_assets/skins/selectedSkin.json"),
            new TextureAtlas(Gdx.files.internal("car_game_assets/skins/selectedSkin.pack")));


    private TextButton buttonPlay = new TextButton("Play level 1", selectedSkin),
            buttonPlay2 = new TextButton("Play level 2", defaultSkin),
            buttonSettings = new TextButton("Settings", defaultSkin),
            buttonAbout = new TextButton("About", defaultSkin),
            buttonExit = new TextButton("Exit", defaultSkin);

    private Label title = new Label(CarGame.TITLE, defaultSkin);
    private Label connectedClientNames;

    private String clientNames;

    public MainMenu(VarsomSystem varsomSystem) {
        this.varsomSystem = varsomSystem;
        varsomSystem.setActiveStage(stage);
        objectList = new ArrayList<BackgroundObject>();
        for (int i = 0; i < 100; i++) {
            Vector2 temp = new Vector2((float) (Math.random() * WIDTH), (float) (Math.random() * HEIGHT));
            objectList.add(new BackgroundObject(temp, "car_game_assets/img/cloud.png"));
        }
        // add to ArrayList

        buttonList.add(buttonPlay);
        buttonList.add(buttonPlay2);
        buttonList.add(buttonSettings);
        buttonList.add(buttonAbout);
        buttonList.add(buttonExit);
    }

    @Override
    public void show() {
        // Vill kolla om NetworkListener fått in ett SendDpadData.dpad,
        // Om något nytt kommit in, sätt dpad.x med currentButton
        // TODO: Gör dpad-datan till 2D, alltså en x och en y-koordinat
        // Avkommentera när det är fixat
        if(NetworkListener.dPadSelect == true) {
            //   currentButton.x += NetworkListener.dpad.x;
            // currentButton.y = NetworkListener.dpad.y;
            Gdx.app.log("in Main Menu", "dPad input catched!");
            NetworkListener.dPadSelect = false;
        }

        Gdx.input.setCatchBackKey(false);
        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "pressed the PLAY button.");
                varsomSystem.getMPServer().setJoinable(false);

                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(1, varsomSystem));
            }
        });

        buttonPlay2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "pressed the PLAY 2 button.");
                varsomSystem.getMPServer().setJoinable(false);

                ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(2, varsomSystem));
            }
        });

        buttonSettings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "pressed the SETTINGS CARGAME button.");
            }
        });

        buttonAbout.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "pressed the ABOUT CARGAME button.");
            }
        });

        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //   Boolean isKeyPressed = true;
                Gdx.app.log("clicked", "pressed the EXIT CARGAME button.");
                ((Game) Gdx.app.getApplicationListener()).setScreen(new VarsomMenu(varsomSystem));
                // or System.exit(0);
            }
        });

        //TODO Fix hardcoded values on buttons

        // Elements are displayed in the order they're added, top to bottom
        table.add(title).padBottom(40).row();
        table.add(buttonPlay).size(500, 150).padBottom(20).row();
        table.add(buttonPlay2).size(500, 150).padBottom(20).row();
        table.add(buttonSettings).size(500, 150).padBottom(20).row();
        table.add(buttonAbout).size(500, 150).padBottom(20).row();
        table.add(buttonExit).size(500, 150).padBottom(20).row();

        BitmapFont fontType = new BitmapFont();
        fontType.scale(2.f);
        Label.LabelStyle style = new Label.LabelStyle(fontType, Color.WHITE);

        //label that shows all connected players
        clientNames = "Connected players:";
        connectedClientNames = new Label(clientNames, style);
        connectedClientNames.setPosition(0, Commons.WORLD_HEIGHT - connectedClientNames.getHeight());

        table.setPosition(Commons.WORLD_WIDTH / 2 - table.getWidth() / 2, Commons.WORLD_HEIGHT / 2 - table.getHeight() / 2);

        stage.addActor(table);
        stage.addActor(connectedClientNames);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        // If Exit was pressed on a client
        if (NetworkListener.goBack) {
            Gdx.app.log("in GameScreen", "go back to main menu");
            //TODO ska vi skapa en ny meny eller gå tillbaka till den gamla?
            //TODO om va gör en ny, när tar vi bort den gamla?
            ((Game) Gdx.app.getApplicationListener()).setScreen(new VarsomMenu(varsomSystem));
            NetworkListener.goBack = false;
            //dispose(); ??
        }

        updateBackground();

        Gdx.gl.glClearColor(122 / 255.0f, 209 / 255.0f, 255 / 255.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        handleClients();
        drawBackground();

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
        defaultSkin.dispose();
    }

    public void updateBackground() {

        for (int i = 0; i < objectList.size(); i++) {
            BackgroundObject temp = objectList.get(i);
            temp.update();

            if (temp.getPos().x > WIDTH + temp.getWidth() + 10) {
                temp.setX(-10 - temp.getWidth());
                temp.setY((float) (Math.random() * HEIGHT));
            }
        }
    }

    public void drawBackground() {
        spriteBatch.begin();

        for (int i = 0; i < objectList.size(); i++) {
            objectList.get(i).draw(spriteBatch);
        }

        spriteBatch.end();
    }

    //make sure that all connected clients are displayed in a label
    //if a new client is connected add it
    //if a client is disconnected remove it
    public void handleClients() {
        clientNames = "Connected players:";

        //update the client names label with clients connected at the moment
        for (int i = 0; i < varsomSystem.getServer().getConnections().length; i++) {
            //TODO right now the IP is displayed, it should be the name chosen by the player
            clientNames += "\n" + varsomSystem.getServer().getConnections()[i].toString();
        }
        connectedClientNames.setText(clientNames);
    }

    private void processDPad(int upOrDown) {

        if (upOrDown == DPad.UP) {
            if (selectedButtonIndex <= 0) {
                selectedButtonIndex = buttonList.size() - 1;
            } else {
                selectedButtonIndex--;
            }
        } else if (upOrDown == DPad.DOWN) {
            if (selectedButtonIndex >= buttonList.size() - 1) {
                selectedButtonIndex = 0;
            } else {
                selectedButtonIndex++;
            }
        }
    }

    private void setSelectedButtonSkin() {
        for (int i = 0; i < buttonList.size(); i++) {
            if (i == selectedButtonIndex) {
                buttonList.get(i).setSkin(selectedSkin);
            } else {
                buttonList.get(i).setSkin(defaultSkin);
            }
        }
    }
}