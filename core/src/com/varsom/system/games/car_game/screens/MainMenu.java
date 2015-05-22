package com.varsom.system.games.car_game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.varsom.system.Commons;
import com.varsom.system.DPad;
import com.varsom.system.games.car_game.helpers.AssetLoader;
import com.varsom.system.games.car_game.helpers.KrazyRazyCommons;
import com.varsom.system.games.car_game.helpers.SoundHandler;
import com.varsom.system.screens.ScaledScreen;

import java.util.ArrayList;

import com.varsom.system.VarsomSystem;
import com.varsom.system.games.car_game.gameobjects.BackgroundObject;
import com.varsom.system.network.NetworkListener;
import com.varsom.system.screens.*;

// TODO Change name of class
public class MainMenu extends ScaledScreen {

    //TODO: This is quite rushed. Refactor it all!

    //private Table table = new Table();

    private final int WIDTH = Commons.WORLD_WIDTH;//Gdx.graphics.getWidth();
    private final int HEIGHT = Commons.WORLD_HEIGHT;//Gdx.graphics.getHeight();

    private SpriteBatch spriteBatch = new SpriteBatch();
    private ArrayList<BackgroundObject> objectList;

    // Select buttons
    //private ArrayList<TextButton> buttonList = new ArrayList<TextButton>();
    private ArrayList<Image> buttonList = new ArrayList<Image>();
   // private String buttonTexts[] = {"Play level 1", "Play level 2", "Settings", "About", "Exit"};
    private int selectedButtonIndex = 0;

    protected VarsomSystem varsomSystem;
    // keeping track of which button is hovered by the controller app (Dpad)
    // Button has x and y index, where index 0,0 is the button on top to the left and
    // sizeX, sizeY (where size is amout of buttons) is bottom right
    //TODO Load files from AssetLoader

    /*private Skin defaultSkin = new Skin(Gdx.files.internal("car_game_assets/skins/menuSkin.json"),
            new TextureAtlas(Gdx.files.internal("car_game_assets/skins/menuSkin.pack")));

    private Skin selectedSkin = new Skin(Gdx.files.internal("car_game_assets/skins/selectedSkin.json"),
            new TextureAtlas(Gdx.files.internal("car_game_assets/skins/selectedSkin.pack")));

    private TextButton buttonPlay = new TextButton(buttonTexts[0], selectedSkin),
            buttonPlay2 = new TextButton(buttonTexts[1], defaultSkin),
            buttonSettings = new TextButton(buttonTexts[2], defaultSkin),
            buttonAbout = new TextButton(buttonTexts[3], defaultSkin),
            buttonExit = new TextButton(buttonTexts[4], defaultSkin);

    private Label title = new Label(CarGame.TITLE, defaultSkin);
    */
    private Label connectedClientNames;

    private String clientNames;

    //NEW MENU
    private Skin skin = AssetLoader.skin;
    private Image krazyTitle = new Image(AssetLoader.krazyTitleTexture),
                  playBtn = new Image(AssetLoader.krazyPlayTexture),
                  playBtnDown = new Image(AssetLoader.krazyPlayDownTexture),
                  exitBtn = new Image(AssetLoader.krazyExitTexture),
                  exitBtnDown = new Image(AssetLoader.krazyExitDownTexture);



    public MainMenu(final VarsomSystem varsomSystem) {
        this.varsomSystem = varsomSystem;
        varsomSystem.setActiveStage(stage);
        objectList = new ArrayList<BackgroundObject>();
        for (int i = 0; i < 20; i++) {

            if(Math.random() < 0.5){
                objectList.add(new BackgroundObject(new Image(AssetLoader.krazyThingyTexture_1)));
            }else{
                objectList.add(new BackgroundObject(new Image(AssetLoader.krazyThingyTexture_2)));
            }

            stage.addActor(objectList.get(i).getImage());
        }

        SoundHandler.MUSIC.setLooping(true);
        SoundHandler.MUSIC.play();

/*
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
        */

    // NEW MENU
        //SET POSITIONS
        krazyTitle.setPosition( WIDTH/2 - krazyTitle.getWidth()/2,  HEIGHT -krazyTitle.getHeight());
        playBtn.setPosition( WIDTH/2 - playBtn.getWidth()/2,  HEIGHT/16 + exitBtn.getHeight()*(3f/5));
        exitBtn.setPosition( WIDTH/2 - exitBtn.getWidth()/2,  HEIGHT/16);//-playBtn.getHeight());
        playBtnDown.setPosition( WIDTH/2 - playBtnDown.getWidth()/2,  HEIGHT/16 + exitBtn.getHeight()*(3f/5));
        exitBtnDown.setPosition( WIDTH/2 - exitBtnDown.getWidth()/2,  HEIGHT/16);//2-playBtn.getHeight());

        //ADD TO BUTTON LIST
        buttonList.add(playBtn);
        buttonList.add(exitBtn);
        buttonList.get(selectedButtonIndex).addAction(Actions.alpha(0));

        //ADD LISTENERS
        playBtn.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                //Gdx.app.log("Example", "touch started at (" + x + ", " + y + ")");
                playBtn.addAction(Actions.alpha(0));
                pressedButtonPlay();
                return false;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                playBtn.addAction(Actions.alpha(1));
                //Gdx.app.log("Example", "touch done at (" + x + ", " + y + ")");
            }
        });
        exitBtn.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                exitBtn.addAction(Actions.alpha(0));
                pressedButtonExit();
                return false;
            }

            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                exitBtn.addAction(Actions.alpha(1));
            }
        });
    }

    @Override
    public void show() {

        Gdx.input.setCatchBackKey(false);

        //TODO Fix hardcoded values on buttons

        // Elements are displayed in the order they're added, top to bottom
        /*
        table.add(title).padBottom(40).row();
        table.add(buttonPlay).size(500, 150).padBottom(20).row();
        table.add(buttonPlay2).size(500, 150).padBottom(20).row();
        table.add(buttonSettings).size(500, 150).padBottom(20).row();
        table.add(buttonAbout).size(500, 150).padBottom(20).row();
        table.add(buttonExit).size(500, 150).padBottom(20).row();
        */



        //BitmapFont fontType = new BitmapFont(new FileHandle("system/fonts/badaboom64w.fnt"));
        //fontType.scale(2.f);

        BitmapFont font = Commons.getFont(52,AssetLoader.krazyFontFile,KrazyRazyCommons.KRAZY_BLUE,3f,KrazyRazyCommons.KRAZY_GREEN);
        Label.LabelStyle style = new Label.LabelStyle(font,Color.WHITE);




        //label that shows all connected players
        clientNames = "Connected players";
        connectedClientNames = new Label(clientNames, style);
        connectedClientNames.setAlignment(Align.topLeft);
        connectedClientNames.setPosition(8,  HEIGHT - connectedClientNames.getHeight());
        connectedClientNames.setFontScale(1.5f);

        //table.setPosition( WIDTH / 2 - table.getWidth() / 2,  HEIGHT / 2 - table.getHeight() / 2);

        //stage.addActor(table);
        stage.addActor(connectedClientNames);

        Gdx.input.setInputProcessor(stage);

        //NEW MENU
        stage.addActor(krazyTitle);
        stage.addActor(playBtnDown);
        stage.addActor(exitBtnDown);
        stage.addActor(playBtn);
        stage.addActor(exitBtn);
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

        Gdx.gl.glClearColor(24 / 255.0f, 102 / 255.0f, 105 / 255.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        handleClients();
        //drawBackground();

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
        //defaultSkin.dispose();
    }

    public void updateBackground() {

        for (int i = 0; i < objectList.size(); i++) {
            BackgroundObject temp = objectList.get(i);
            temp.update();
            int ww =  WIDTH/*Gdx.graphics.getWidth()*/, wh =  WIDTH/*Gdx.graphics.getWidth()*/,
                    sw = AssetLoader.krazyThingyTexture_1.getWidth(), sh = AssetLoader.krazyThingyTexture_1.getHeight();

            if (temp.getPos().x > ww + sw || temp.getPos().x < 0 - sw || temp.getPos().y > wh + sh || temp.getPos().y < 0 - sh ){
                Vector2 tempVec = new Vector2((float) (Math.random() *  WIDTH), (float) (Math.random() *  HEIGHT));
                temp.allNewValues();
            }
            /*if (temp.getPos().x >  WIDTH + temp.getWidth() + 10) {
                temp.setX(-10 - temp.getWidth());
                temp.setY((float) (Math.random() *  HEIGHT));
            }*/
        }

        if(Math.random() < 0.001){
            playWackySound();
        }
    }

    /*public void drawBackground() {
        spriteBatch.begin();

        for (int i = 0; i < objectList.size(); i++) {
            objectList.get(i).draw(spriteBatch);
        }

        spriteBatch.end();
    }*/

    //make sure that all connected clients are displayed in a label
    //if a new client is connected add it
    //if a client is disconnected remove it
    public void handleClients() {
        clientNames = "Connected players";

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
            //buttonList.get(prevIndex).setSkin(defaultSkin);


            if (NetworkListener.dpady == DPad.UP) {
                if (selectedButtonIndex <= 0) {
                    selectedButtonIndex = buttonList.size() - 1;
                } else {
                    selectedButtonIndex--;

                }
                buttonList.get(selectedButtonIndex).addAction(Actions.alpha(0));
                buttonList.get(prevIndex).addAction(Actions.alpha(1));
            } else if (NetworkListener.dpady == DPad.DOWN) {
                if (selectedButtonIndex >= buttonList.size() - 1) {
                    selectedButtonIndex = 0;
                } else {
                    selectedButtonIndex++;
                }
                buttonList.get(selectedButtonIndex).addAction(Actions.alpha(0));
                buttonList.get(prevIndex).addAction(Actions.alpha(1));
            }

            // The newly selected button's skin is set to "selectedSkin". Does not work ATM

            //buttonList.get(selectedButtonIndex).setText("<" + buttonTexts[selectedButtonIndex] + ">");
            //buttonList.get(prevIndex).setText(buttonTexts[prevIndex]);
            //buttonList.get(selectedButtonIndex).setSkin(selectedSkin);

            Gdx.app.log("button selected: ", selectedButtonIndex + "");

            if (NetworkListener.dPadSelect) {
                switch (selectedButtonIndex) {
                    case 0:
                        pressedButtonPlay();
                        break;
                    case 1:
                        /*pressedButtonPlay2();
                        break;
                    case 2:
                        pressedButtonSettings();
                        break;
                    case 3:
                        pressedButtonAbout();
                        break;
                    case 4:*/
                        pressedButtonExit();
                        break;
                    default:
                        break;
                }
                NetworkListener.dPadSelect = false;
            }

        }
    }

    private void pressedButtonPlay() {
        varsomSystem.getMPServer().setJoinable(false);

        SoundHandler.stopAll();
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
        SoundHandler.stopAll();
        ((Game) Gdx.app.getApplicationListener()).setScreen(new VarsomMenu(varsomSystem));
    }

    private void playWackySound(){
        double d = Math.random();

        if(d < 0.333){
            SoundHandler.WACKY_1.play();
        }else if(d < 0.666){
            SoundHandler.WACKY_2.play();
        }else{
            SoundHandler.WACKY_3.play();
        }
    }
}