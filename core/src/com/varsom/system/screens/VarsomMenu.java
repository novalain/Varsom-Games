package com.varsom.system.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.varsom.system.DPad;
import com.varsom.system.VarsomSystem;
import com.varsom.system.abstract_gameobjects.VarsomGame;
import com.varsom.system.games.car_game.com.varsomgames.cargame.CarGame;
import com.varsom.system.games.other_game.OtherGame;
import com.varsom.system.network.NetworkListener;
import com.varsom.system.screens.ScaledScreen;
import com.varsom.system.Commons;

import java.util.Vector;

public class VarsomMenu extends ScaledScreen {

    // private Stage stage = new Stage();
    private Table table = new Table();
    private Vector2 lastTouch = new Vector2();
    private int currentButton = 1;
    private boolean left = false, right = false;

    //TODO Load files from a SystemAssetLoader. Also, create a folder and skin for the varsom system
    private Skin skin = new Skin(Gdx.files.internal("system/skins/menuSkin.json"), new TextureAtlas(Gdx.files.internal("system/skins/menuSkin.pack")));
    private Skin skin2 = new Skin(Gdx.files.internal("data/uiskin.json"));
    /*private TextButton buttonExit;
    private TextButton buttonPlayOtherGame;
    private TextButton buttonPlayCarGame;*/
    private Image imagePlayCarGame;
    private Image imagePlayOtherGame;
    private Image imageExit;
    private Vector<Image> images;
    private Label connectedClientNames;
    private Label ips; //temp

    private String clientNames;

    protected VarsomSystem varsomSystem;

    private int addedClients = 0;
    private int middleImageX = -150;

    public VarsomMenu(VarsomSystem varsomSystem) {

        this.varsomSystem = varsomSystem;
    }

    @Override
    public void show() {

        BitmapFont fontType = new BitmapFont();
        fontType.scale(2.f);

        Label.LabelStyle style = new Label.LabelStyle(fontType, Color.WHITE);

        ips = new Label("IP: " + this.varsomSystem.getServerIP(), style);

        //Position IP at middle top of the screen
        ips.setPosition(Commons.WORLD_WIDTH / 2 - ips.getWidth() / 2, Commons.WORLD_HEIGHT - 200);
        stage.addActor(ips);

        //For every VarsomeGame in the game array create an image
        for (int i = 0; i < VarsomSystem.SIZE; i++) {
            // getting rid of switch since switch enters every index anyways.
            // making method createMenuItem that can call on startGame

            switch (VarsomSystem.games[i]) {
                //Cargame
                case 1:
                    //buttonPlayCarGame = new TextButton(" CarGame", skin);
                 //   startGame();
                    imagePlayCarGame = new Image(new Texture(Gdx.files.internal("system/img/varsomful.png")));

                    imagePlayCarGame.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {

                            if (currentButton == 0 && !left && !right) {

                                Gdx.app.log("clicked", "pressed CarGame");
                                //((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(1));

                                images.elementAt(currentButton).addAction(Actions.sequence(Actions.scaleBy(2.f, 2.f, 0.6f), Actions.alpha(0.0f, 0.6f), Actions.run(new Runnable() {
                                    @Override
                                    public void run() {
                                        // ((Game) Gdx.app.getApplicationListener()).setScreen(new VarsomMenu(varsomSystem));
                                        VarsomGame carGame = new CarGame((VarsomSystem) Gdx.app.getApplicationListener());
                                        varsomSystem.setActiveGame(carGame);

                                    }
                                })));

                                hide();
                            }

                        }
                    });

                    table.add(imagePlayCarGame).size(300, 300).padRight(200);

                    break;

                //OtherGame
                case 2:
                    //buttonPlayOtherGame = new TextButton(" OtherGame", skin);
                    imagePlayOtherGame = new Image(new Texture(Gdx.files.internal("system/img/varsomful.png")));

                    imagePlayOtherGame.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {

                            if (currentButton == 1 && !left && !right) {

                                images.elementAt(currentButton).addAction(Actions.sequence(Actions.scaleBy(2.f, 2.f, 0.6f), Actions.alpha(0.0f, 0.6f), Actions.run(new Runnable() {
                                    @Override
                                    public void run() {
                                        // ((Game) Gdx.app.getApplicationListener()).setScreen(new VarsomMenu(varsomSystem));
                                        VarsomGame carGame = new CarGame((VarsomSystem) Gdx.app.getApplicationListener());
                                        varsomSystem.setActiveGame(carGame);
                                        //VarsomGame OtherGame = new OtherGame((Game) Gdx.app.getApplicationListener());
                                        Gdx.app.log("clicked", "pressed OtherGame.");
                                        hide();
                                    }
                                })));

                            }
                        }
                    });
                    table.add(imagePlayOtherGame).size(300, 300).padRight(200);
                    //  imagePlayOtherGame.setWidth(350);
                    // imagePlayOtherGame.setHeight(350);
                    break;

                //If something goes wrong
                default:
                    Gdx.app.log("in game array", "wrong game ID");
            }

            imageExit = new Image(new Texture(Gdx.files.internal("system/img/varsomful.png")));

            // Add all images into array
            images = new Vector();
            images.add(imagePlayCarGame);
            images.add(imagePlayOtherGame);
            images.add(imageExit);

            imageExit.addListener(new ClickListener() {

                @Override
                public void clicked(InputEvent event, float x, float y) {

                    if (currentButton == 2 && !left && !right) {
                        Gdx.app.log("clicked", "pressed the EXIT SYSTEM button.");
                        Gdx.app.exit();
                        dispose();
                    }
                }
            });

            stage.addListener(new ClickListener() {

                @Override
                public void touchDragged(InputEvent event, float x, float y, int pointer) {

                    Vector2 newTouch = new Vector2(x, y);
                    // delta holds the difference between the last and current touch position
                    // delta.x > 0 means the touch moved to the right, delta.x < 0 to the left
                    Vector2 delta = newTouch.cpy().sub(lastTouch);
                    lastTouch = newTouch;

                    if (delta.x > 50 && currentButton > 0 && !right) {

                        right = true;
                        currentButton--;
                        stage.cancelTouchFocus();

                    } else if (delta.x < -50 && currentButton < 2 && !left) {
                        left = true;
                        currentButton++;
                        stage.cancelTouchFocus();
                    }
                }

                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    lastTouch.set(x, y);
                    return true;
                }

            });
        }

        table.add(imageExit).size(300, 300);

        //label that shows all connected players
        clientNames = "Connected players:";
        connectedClientNames = new Label(clientNames, style);
        connectedClientNames.setPosition(0, Commons.WORLD_HEIGHT - connectedClientNames.getHeight());

        // place the table in the middle of the screen.
        table.setPosition(Commons.WORLD_WIDTH / 2 - table.getWidth() / 2, Commons.WORLD_HEIGHT / 2 - table.getHeight() / 2);
        stage.addActor(table);

        table.addAction(Actions.sequence(Actions.alpha(0), Actions.fadeIn(1.2f)));

        stage.addActor(connectedClientNames);

        Gdx.input.setInputProcessor(stage);

        //Move origin to scale from middle
        imagePlayOtherGame.setOrigin(150, 150);
        imagePlayCarGame.setOrigin(150, 150);
        imageExit.setOrigin(150, 150);

        imagePlayOtherGame.setScale(1.52f);

        for (int i = 0; i < images.size(); i++) {
            if (i != currentButton) {
                images.elementAt(i).addAction(Actions.sequence(Actions.alpha(0.3f, 0.2f)));
            }
        }
       /* if(currentButton == 1 ) {
            images.elementAt(currentButton - 1).addAction(Actions.sequence(Actions.alpha(0.3f, 0.2f)));
            images.elementAt(currentButton + 1).addAction(Actions.sequence(Actions.alpha(0.3f, 0.2f)));
        }
        else if (currentButton == 0) {
            images.elementAt(currentButton + 1).addAction(Actions.sequence(Actions.alpha(0.3f, 0.2f)));
            images.elementAt(currentButton + 2).addAction(Actions.sequence(Actions.alpha(0.3f, 0.2f)));
        }*/

    }

    public void handleSwipedImages() {

        // Move to the left
        if (right && currentButton < 2) {

            //Fade down
            images.elementAt(currentButton + 1).addAction(Actions.sequence(Actions.alpha(0.3f, 1.f)));
            images.elementAt(currentButton).addAction(Actions.sequence(Actions.alpha(1.f, 1.f)));

            //If position reached middle, set swipe right to false (810 is original pos for the middle image)
            if (images.elementAt(currentButton).getX() >= middleImageX) {
                right = false;
            } else {

                //Translate all images
                for (int i = 0; i < images.size(); i++) {
                    images.elementAt(i).setPosition(images.elementAt(i).getX() + 10, images.elementAt(i).getY());
                }

                //Scale up current image and scale down previous image
                images.elementAt(currentButton).scaleBy(0.01f);
                images.elementAt(currentButton + 1).scaleBy(-0.01f);
            }

        }
        //Same but the other way
        else if (left && currentButton > 0) {

            images.elementAt(currentButton - 1).addAction(Actions.sequence(Actions.alpha(0.3f, 1.f)));
            images.elementAt(currentButton).addAction(Actions.sequence(Actions.alpha(1.f, 1.f)));

            if (images.elementAt(currentButton).getX() <= middleImageX) {
                left = false;
            } else {

                for (int i = 0; i < images.size(); i++) {
                    images.elementAt(i).setPosition(images.elementAt(i).getX() - 10, images.elementAt(i).getY());
                }

                images.elementAt(currentButton).scaleBy(0.01f);
                images.elementAt(currentButton - 1).scaleBy(-0.01f);
            }
        }
    }

    @Override
    public void render(float delta) {
        // If Exit was pressed on a client
        if (NetworkListener.goBack) {
            Gdx.app.log("in GameScreen", "go back to main menu");
            NetworkListener.goBack = false;
            Gdx.app.exit();
            dispose();
        }

        Gdx.gl.glClearColor(0.12f, 0.12f, 0.12f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        handleDpad();
        handleSwipedImages();
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

    public static class AlertDialog extends Dialog {

        public AlertDialog(String title, Skin skin, String windowStyleName) {
            super(title, skin, windowStyleName);
        }

        public AlertDialog(String title, Skin skin) {
            super(title, skin);
        }

        public AlertDialog(String title, WindowStyle windowStyle) {
            super(title, windowStyle);
        }

        {
            text("CarGame requiers at least two connected players");
            button("OK");
        }

        @Override
        protected void result(Object object) {

        }
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

    // Vill kolla om NetworkListener fått in ett SendDpadData,
    // Om något nytt kommit in, öndra currentButton med dataX
    // TODO: Gör dpad-datan till 2D, alltså en x och en y-koordinat
    public void handleDpad() {

        if (NetworkListener.dpadTouched == true) {
            NetworkListener.dpadTouched = false;
            Gdx.app.log("in varsommenu", "The value of dpadTouched is: " + NetworkListener.dpadTouched);
            if (NetworkListener.dpadx == DPad.RIGHT) {
                left = true;
                currentButton += NetworkListener.dpadx;
                Gdx.app.log("Right selected, Currentbutton is ", ""+currentButton);
                if (currentButton > images.size()) {
                    Gdx.app.log("CurrentButton value max", "is " + images.size());
                    currentButton = images.size();
                }
            } else {
                Gdx.app.log("Left selected, Currentbutton is ", ""+currentButton);
                right = true;
                currentButton += NetworkListener.dpadx;
                if (currentButton < 0) {
                    Gdx.app.log("CurrentButton value max", "is " + 0);
                    currentButton = 0;
                }
            }
            NetworkListener.dPadSelect = false;
        }
    }

    private void startGame() {

    }
}
