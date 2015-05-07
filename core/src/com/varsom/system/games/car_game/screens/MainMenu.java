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

    //TODO: This is quite rushed. Refactor it all!

    private Table table = new Table();

    private final int WIDTH = Gdx.graphics.getWidth();
    private final int HEIGHT = Gdx.graphics.getHeight();

    private SpriteBatch spriteBatch = new SpriteBatch();
    private ArrayList<BackgroundObject> objectList;

    // Select buttons
    private ArrayList<TextButton> buttonList = new ArrayList<TextButton>();
    private String buttonTexts[] = {"Play level 1", "Play level 2", "Settings", "About", "Exit"};
    private int selectedButtonIndex = 0;

    protected VarsomSystem varsomSystem;
    // keeping track of which button is hovered by the controller app (Dpad)
    // Button has x and y index, where index 0,0 is the button on top to the left and
    // sizeX, sizeY (where size is amout of buttons) is bottom right
    //TODO Load files from AssetLoader

    private Skin defaultSkin = new Skin(Gdx.files.internal("car_game_assets/skins/menuSkin.json"),
            new TextureAtlas(Gdx.files.internal("car_game_assets/skins/menuSkin.pack")));

    private Skin selectedSkin = new Skin(Gdx.files.internal("car_game_assets/skins/selectedSkin.json"),
            new TextureAtlas(Gdx.files.internal("car_game_assets/skins/selectedSkin.pack")));

    private TextButton buttonPlay = new TextButton(buttonTexts[0], selectedSkin),
            buttonPlay2 = new TextButton(buttonTexts[1], defaultSkin),
            buttonSettings = new TextButton(buttonTexts[2], defaultSkin),
            buttonAbout = new TextButton(buttonTexts[3], defaultSkin),
            buttonExit = new TextButton(buttonTexts[4], defaultSkin);

    private Label title = new Label(CarGame.TITLE, defaultSkin);
    private Label connectedClientNames;

    private String clientNames;

    public MainMenu(final VarsomSystem varsomSystem) {
        this.varsomSystem = varsomSystem;
        varsomSystem.setActiveStage(stage);
        objectList = new ArrayList<BackgroundObject>();
        for (int i = 0; i < 100; i++) {
            Vector2 temp = new Vector2((float) (Math.random() * WIDTH), (float) (Math.random() * HEIGHT));
            objectList.add(new BackgroundObject(temp, "car_game_assets/img/cloud.png"));
        }

        // add listeners to buttons
        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "pressed the PLAY button.");
                pressedButtonPlay();
            }
        });

        buttonPlay2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pressedButtonPlay2();
            }
        });

        buttonSettings.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pressedButtonSettings();
            }
        });

        buttonAbout.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pressedButtonAbout();
            }
        });

        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                pressedButtonExit();
            }
        });
        // end of adding listeners

        buttonList.add(buttonPlay);
        buttonList.add(buttonPlay2);
        buttonList.add(buttonSettings);
        buttonList.add(buttonAbout);
        buttonList.add(buttonExit);
    }

    @Override
    public void show() {

        Gdx.input.setCatchBackKey(false);

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
            //TODO om vi gör en ny, när tar vi bort den gamla?
            ((Game) Gdx.app.getApplicationListener()).setScreen(new VarsomMenu(varsomSystem));
            NetworkListener.goBack = false;
        }

        if (NetworkListener.dpadTouched) {
            processDPad();
            NetworkListener.dpadTouched = false;
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

    private void processDPad() {

        if (NetworkListener.dpady == DPad.UP || NetworkListener.dpady == DPad.DOWN || NetworkListener.dPadSelect) {


            int prevIndex = selectedButtonIndex;

            // Does not work
            buttonList.get(prevIndex).setSkin(defaultSkin);


            if (NetworkListener.dpady == DPad.UP) {
                if (selectedButtonIndex <= 0) {
                    selectedButtonIndex = buttonList.size() - 1;
                } else {
                    selectedButtonIndex--;
                }
            } else if (NetworkListener.dpady == DPad.DOWN) {
                if (selectedButtonIndex >= buttonList.size() - 1) {
                    selectedButtonIndex = 0;
                } else {
                    selectedButtonIndex++;
                }
            }

            // The newly selected button's skin is set to "selectedSkin". Does not work ATM

            buttonList.get(selectedButtonIndex).setText("<" + buttonTexts[selectedButtonIndex] + ">");
            buttonList.get(prevIndex).setText(buttonTexts[prevIndex]);
            buttonList.get(selectedButtonIndex).setSkin(selectedSkin);

            Gdx.app.log("button selected: ", selectedButtonIndex + "");

            if (NetworkListener.dPadSelect) {
                switch (selectedButtonIndex) {
                    case 0:
                        pressedButtonPlay();
                        break;
                    case 1:
                        pressedButtonPlay2();
                        break;
                    case 2:
                        pressedButtonSettings();
                        break;
                    case 3:
                        pressedButtonAbout();
                        break;
                    case 4:
                        pressedButtonExit();
                    default:
                        break;
                }
                NetworkListener.dPadSelect = false;
            }

        }
    }

    private void pressedButtonPlay() {
        varsomSystem.getMPServer().setJoinable(false);

        ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(1, varsomSystem));
        //Switch screen on the controller to controllerScreen
        varsomSystem.getMPServer().changeScreen(Commons.CONTROLLER_SCREEN);
    }

    private void pressedButtonPlay2() {
        Gdx.app.log("clicked", "pressed the PLAY 2 button.");
        varsomSystem.getMPServer().setJoinable(false);

        ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(2, varsomSystem));
        //Switch screen on the controller to controllerScreen
        varsomSystem.getMPServer().changeScreen(Commons.CONTROLLER_SCREEN);
    }

    private void pressedButtonSettings() {

    }

    private void pressedButtonAbout() {

    }

    private void pressedButtonExit() {
        Gdx.app.log("clicked", "pressed the EXIT CARGAME button.");
        ((Game) Gdx.app.getApplicationListener()).setScreen(new VarsomMenu(varsomSystem));
    }
}