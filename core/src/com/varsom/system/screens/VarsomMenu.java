package com.varsom.system.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.varsom.system.Commons;
import com.varsom.system.DPad;
import com.varsom.system.VarsomSystem;
import com.varsom.system.abstract_gameobjects.VarsomGame;
import com.varsom.system.games.car_game.com.varsomgames.cargame.CarGame;
import com.varsom.system.network.NetworkListener;

import java.util.Vector;

/**
 * Created by michaelnoven on 15-05-17.
 */
public class VarsomMenu extends ScaledScreen{

    private static final int NR_OF_GAMES = 10, GAME_SPACING = 260;
    private static final float GAME_SCALE = 0.34f, GAME_SCALE_SELECTED = 0.37f, GAME_ALPHA = 0.5f, DEVICES_SCALE = 0.15f, FADE_AND_SCALE_TIME = 0.6f, HELPBUTTONS_ALPHA = 0.3f, HELPBUTTONS_SCALE = 1.5f;

    protected VarsomSystem varsomSystem;
    private Vector<Image> gamesList, devicesGray, devicesActive;
    private Vector<String> connectionNames;
    private Vector<Label> devicesLabels;
    private int currentGame, row, currentHelpButton;
    private boolean swipedLeft, swipedRight, swipedDown, swipedUp, shutDownReady;
    private Image cargameImage, backgroundImage, varsomLogo, shutdownImage, shutdownHover, questionmarkImage, questionmarkHover;
    private Vector2 lastTouch;

    private BitmapFont playerFont;
    private Dialog errorDialog;

    private BitmapFont devicesFont;


    public VarsomMenu(VarsomSystem varsomSystem) {

        this.varsomSystem = varsomSystem;
        varsomSystem.setActiveStage(stage);
        //TODO We should have an AssetLoader for the VarsomMenu!
        devicesFont = Commons.getFont(30, Gdx.files.internal("system/fonts/Impact.ttf"));

        currentHelpButton = 1;
        currentGame = 0;
        row = 1;
        swipedLeft = swipedRight = swipedDown = swipedUp = false;
        gamesList = new Vector();
        devicesGray = new Vector();
        devicesActive = new Vector();
        devicesLabels = new Vector();
        lastTouch = new Vector2();

    }

    @Override
    public void show(){

        // Initialize the menu items
        init();

        stage.addActor(backgroundImage);
        stage.addActor(varsomLogo);
        stage.addActor(shutdownImage);
        stage.addActor(shutdownHover);
        stage.addActor(questionmarkImage);
        stage.addActor(questionmarkHover);

        stage.addListener(new ClickListener() {

            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {

                Vector2 newTouch = new Vector2(x, y);
                Vector2 delta = newTouch.cpy().sub(lastTouch);
                lastTouch = newTouch;

                // Swiped right
                if (delta.x > 20 && !swipedRight && !swipedLeft) {

                    stage.cancelTouchFocus();
                    handleMenuLeft();

                }
                // Swiped left
                else if (delta.x < -20 && !swipedRight && !swipedLeft) {

                    stage.cancelTouchFocus();
                    handleMenuRight();

                }
                // Swiped down
                else if (delta.y < -20 && !swipedRight && !swipedLeft && row > 0){

                    stage.cancelTouchFocus();
                    handleMenuUp();

                }
                // Swiped up
                else if(delta.y > 20 && !swipedRight && !swipedLeft && row < 2){

                    stage.cancelTouchFocus();
                    handleMenuDown();

                }

                System.out.println(currentGame);
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                lastTouch.set(x, y);
                return true;
            }

        });

        for(int i = 0; i < gamesList.size(); i++){

            stage.addActor(gamesList.elementAt(i));

        }

        for(int i = 0; i < 8; i++){

            stage.addActor(devicesGray.elementAt(i));
            stage.addActor(devicesActive.elementAt(i));
            stage.addActor(devicesLabels.elementAt(i));
        }


        Gdx.input.setInputProcessor(stage);


    }

    public void handleSwipedImages() {

       // System.out.println("ROW = " + row);
        //System.out.println("currentHelpButton = " + currentHelpButton);

        // Move to the right
        if(swipedLeft && !swipedRight){

            if(row == 0){

                if(currentHelpButton == 1){

                    questionmarkImage.addAction(Actions.alpha(HELPBUTTONS_ALPHA, FADE_AND_SCALE_TIME));
                    questionmarkHover.addAction(Actions.alpha(0.f, FADE_AND_SCALE_TIME));
                    shutdownImage.addAction(Actions.alpha(0.f, FADE_AND_SCALE_TIME));
                    shutdownHover.addAction(Actions.alpha(1.f, FADE_AND_SCALE_TIME));
                   // shutdownImage.addAction(Actions.scaleTo(HELPBUTTONS_SCALE, FADE_AND_SCALE_TIME));
                    //questionmarkImage.addAction(Actions.scaleTo(1.f, FADE_AND_SCALE_TIME));
                    currentHelpButton--;
                }

                else if (currentHelpButton == 0){
                    questionmarkImage.addAction(Actions.alpha(0.f, FADE_AND_SCALE_TIME));
                    questionmarkHover.addAction(Actions.alpha(1.f, FADE_AND_SCALE_TIME));
                    shutdownImage.addAction(Actions.alpha(HELPBUTTONS_ALPHA, FADE_AND_SCALE_TIME));
                    shutdownHover.addAction(Actions.alpha(0, FADE_AND_SCALE_TIME));
                    //questionmarkImage.addAction(Actions.scaleTo(HELPBUTTONS_SCALE, FADE_AND_SCALE_TIME));
                  //  shutdownImage.addAction(Actions.scaleTo(1.f, FADE_AND_SCALE_TIME));
                    currentHelpButton++;
                }

                else{

                    System.out.println("Something is is wrong in the top row.. ");

                }

                swipedLeft = false;
                return;

            }

            if(currentGame == 0)
                gamesList.elementAt(4).addAction(Actions.scaleTo(GAME_SCALE, GAME_SCALE, FADE_AND_SCALE_TIME));
            else if(currentGame == 5)
                gamesList.elementAt(9).addAction(Actions.scaleTo(GAME_SCALE, GAME_SCALE, FADE_AND_SCALE_TIME));
            else
                gamesList.elementAt(currentGame-1).addAction(Actions.scaleTo(GAME_SCALE, GAME_SCALE, FADE_AND_SCALE_TIME));

            gamesList.elementAt(currentGame).addAction(Actions.scaleTo(GAME_SCALE_SELECTED, GAME_SCALE_SELECTED, FADE_AND_SCALE_TIME));
            gamesList.elementAt(currentGame).addAction(Actions.sequence(Actions.alpha(1.f, FADE_AND_SCALE_TIME)));

            // Fade down
            for(int i = 0; i < gamesList.size(); i++){
                if(i != currentGame)
                    gamesList.elementAt(i).addAction(Actions.sequence(Actions.alpha(GAME_ALPHA, FADE_AND_SCALE_TIME)));

            }
            swipedLeft = false;
        }

        // Move to the left
        else if(swipedRight && !swipedLeft){

            if(row == 0){

                if(currentHelpButton == 1){

                    questionmarkImage.addAction(Actions.alpha(HELPBUTTONS_ALPHA, FADE_AND_SCALE_TIME));
                    questionmarkHover.addAction(Actions.alpha(0.f, FADE_AND_SCALE_TIME));
                    shutdownImage.addAction(Actions.alpha(0.f, FADE_AND_SCALE_TIME));
                    shutdownHover.addAction(Actions.alpha(1.f, FADE_AND_SCALE_TIME));
                    //shutdownImage.addAction(Actions.scaleTo(HELPBUTTONS_SCALE, FADE_AND_SCALE_TIME));
                    //questionmarkImage.addAction(Actions.scaleTo(1.f, FADE_AND_SCALE_TIME));
                    currentHelpButton--;
                }

                else if (currentHelpButton == 0){
                    questionmarkImage.addAction(Actions.alpha(0.f, FADE_AND_SCALE_TIME));
                    questionmarkHover.addAction(Actions.alpha(1.f, FADE_AND_SCALE_TIME));
                    shutdownImage.addAction(Actions.alpha(HELPBUTTONS_ALPHA, FADE_AND_SCALE_TIME));
                    shutdownHover.addAction(Actions.alpha(0, FADE_AND_SCALE_TIME));
                    //questionmarkImage.addAction(Actions.scaleTo(HELPBUTTONS_SCALE, FADE_AND_SCALE_TIME));
                    //shutdownImage.addAction(Actions.scaleTo(1.f, FADE_AND_SCALE_TIME));
                    currentHelpButton++;
                }

                else{

                    System.out.println("Something is is wrong in the top row.. ");

                }

                swipedRight = false;
                return;

            }

            if(currentGame == 4)
                gamesList.elementAt(0).addAction(Actions.scaleTo(GAME_SCALE, GAME_SCALE, FADE_AND_SCALE_TIME));
            else if(currentGame == 9)
                gamesList.elementAt(5).addAction(Actions.scaleTo(GAME_SCALE, GAME_SCALE, FADE_AND_SCALE_TIME));
            else
                gamesList.elementAt(currentGame+1).addAction(Actions.scaleTo(GAME_SCALE, GAME_SCALE, FADE_AND_SCALE_TIME));

            gamesList.elementAt(currentGame).addAction(Actions.scaleTo(GAME_SCALE_SELECTED, GAME_SCALE_SELECTED, FADE_AND_SCALE_TIME));

            gamesList.elementAt(currentGame).addAction(Actions.sequence(Actions.alpha(1.f, FADE_AND_SCALE_TIME)));

            // Fade down
            for(int i = 0; i < gamesList.size(); i++){
                if(i != currentGame)
                    gamesList.elementAt(i).addAction(Actions.sequence(Actions.alpha(GAME_ALPHA, FADE_AND_SCALE_TIME)));

            }

            swipedRight = false;
        }

        // Move down
        else if(swipedUp && !swipedDown){

            if(row == 0){

                questionmarkImage.addAction(Actions.alpha(HELPBUTTONS_ALPHA, FADE_AND_SCALE_TIME));
                questionmarkHover.addAction(Actions.alpha(0.f, FADE_AND_SCALE_TIME));
                shutdownImage.addAction(Actions.alpha(HELPBUTTONS_ALPHA, FADE_AND_SCALE_TIME));
                shutdownHover.addAction(Actions.alpha(0.f, FADE_AND_SCALE_TIME));
                // questionmarkImage.addAction(Actions.scaleTo(1.f, FADE_AND_SCALE_TIME));
                //shutdownImage.addAction(Actions.scaleTo(1.f, FADE_AND_SCALE_TIME));

                gamesList.elementAt(currentGame).addAction(Actions.scaleTo(GAME_SCALE_SELECTED, GAME_SCALE_SELECTED, FADE_AND_SCALE_TIME));
                // Fade up
                gamesList.elementAt(currentGame).addAction(Actions.sequence(Actions.alpha(1.f, FADE_AND_SCALE_TIME)));

            }

            else{

                // Scale down
                gamesList.elementAt(currentGame - 5).addAction(Actions.scaleTo(GAME_SCALE, GAME_SCALE, FADE_AND_SCALE_TIME));
                // Scale up
                gamesList.elementAt(currentGame).addAction(Actions.scaleTo(GAME_SCALE_SELECTED, GAME_SCALE_SELECTED, FADE_AND_SCALE_TIME));
                // Alpha up
                gamesList.elementAt(currentGame).addAction(Actions.sequence(Actions.alpha(1.f, FADE_AND_SCALE_TIME)));

                // Fade down
                for(int i = 0; i < gamesList.size(); i++){
                    if(i != currentGame)
                        gamesList.elementAt(i).addAction(Actions.sequence(Actions.alpha(GAME_ALPHA, FADE_AND_SCALE_TIME)));

                }

            }

            row++;
            swipedUp = false;

        }
        // Move up
        else if(swipedDown && !swipedUp){

            // At top row
            if(row == 1){

                questionmarkImage.addAction(Actions.alpha(0.f, FADE_AND_SCALE_TIME));
                questionmarkHover.addAction(Actions.alpha(1.f, FADE_AND_SCALE_TIME));
              //  questionmarkImage.addAction(Actions.scaleTo(HELPBUTTONS_SCALE, FADE_AND_SCALE_TIME));
                gamesList.elementAt(currentGame).addAction(Actions.scaleTo(GAME_SCALE, GAME_SCALE, FADE_AND_SCALE_TIME));
                // Fade down
                gamesList.elementAt(currentGame).addAction(Actions.sequence(Actions.alpha(GAME_ALPHA, FADE_AND_SCALE_TIME)));
                row--;
                swipedDown = false;
                return;
            }

            // Scale down
            gamesList.elementAt(currentGame + 5).addAction(Actions.scaleTo(GAME_SCALE, GAME_SCALE, FADE_AND_SCALE_TIME));
            // Scale up
            gamesList.elementAt(currentGame).addAction(Actions.scaleTo(GAME_SCALE_SELECTED, GAME_SCALE_SELECTED, FADE_AND_SCALE_TIME));
            // Alpha up
            gamesList.elementAt(currentGame).addAction(Actions.sequence(Actions.alpha(1.f, FADE_AND_SCALE_TIME)));

            // Fade down
            for(int i = 0; i < gamesList.size(); i++){
                if(i != currentGame)
                    gamesList.elementAt(i).addAction(Actions.sequence(Actions.alpha(GAME_ALPHA, FADE_AND_SCALE_TIME)));

            }

            row--;
            swipedDown = false;

        }

        else if(shutDownReady && NetworkListener.goBack){
            errorDialog.hide();
            shutDownReady = false;
        }

    }

    @Override
    public void pause(){



    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.11f, 0.11f, 0.11f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        handleSwipedImages();
        handleClients();
        handleDpad();

    }

    @Override
    public void resume(){



    }

    @Override
    public void resize(int width, int height){

    }


    @Override
    public void dispose() {
        stage.dispose();
        //skin.dispose();
    }

    @Override
    public void hide() {

    }

    //make sure that all connected clients are displayed in a label
    //if a new client is connected add it
    //if a client is disconnected remove it
    public void handleClients() {

       // System.out.println("NR OF CONNECTIONS" + varsomSystem.getServer().getConnections().length);

        //update the client names label with clients connected at the moment
        int n = varsomSystem.getServer().getConnections().length;
        for (int i = 0; i < n; i++) {
            //TODO show the names
            //clientNames += "\n" + varsomSystem.getServer().getConnections()[i].toString();
            devicesActive.elementAt(i).addAction(Actions.alpha(1.f, FADE_AND_SCALE_TIME));
            devicesLabels.elementAt(i).addAction(Actions.alpha(1.f, FADE_AND_SCALE_TIME*2));
            devicesLabels.elementAt(i).setText(varsomSystem.getServer().getConnections()[n-1-i].toString());

            //System.out.println("CONNECTED");
        }

        for(int i = n; i < 8; i++){

            devicesActive.elementAt(i).addAction(Actions.alpha(0.f, FADE_AND_SCALE_TIME));
            //TODO REMOVE NAME
            devicesLabels.elementAt(i).setText("");

        }

        //connectedClientNames.setText(clientNames);
    }

    private void init(){

        shutDownReady = false;

        // Create images of games
        cargameImage = new Image(new Texture(Gdx.files.internal("system/img/crazy_razy.png")));

        shutdownImage = new Image(new Texture(Gdx.files.internal("system/img/shut_down_icon2.png")));
        shutdownImage.setHeight(100);
        shutdownImage.setWidth(100);
        shutdownImage.setPosition(35, Commons.WORLD_HEIGHT - shutdownImage.getHeight() - 35);
        shutdownImage.addAction(Actions.alpha(HELPBUTTONS_ALPHA));

        shutdownHover = new Image(new Texture(Gdx.files.internal("system/img/shut_down_icon_hover2.png")));
        shutdownHover.setHeight(100);
        shutdownHover.setWidth(100);
        shutdownHover.setPosition(35, Commons.WORLD_HEIGHT - shutdownHover.getHeight() - 35);
        shutdownHover.addAction(Actions.alpha(0.0f));

        questionmarkImage = new Image(new Texture(Gdx.files.internal("system/img/question_mark_icon2.png")));
        questionmarkImage.setHeight(100);
        questionmarkImage.setWidth(100);
        questionmarkImage.setPosition(Commons.WORLD_WIDTH - questionmarkImage.getWidth() - 35, Commons.WORLD_HEIGHT - questionmarkImage.getHeight() - 35);
        questionmarkImage.addAction(Actions.alpha(HELPBUTTONS_ALPHA));

        questionmarkHover = new Image(new Texture(Gdx.files.internal("system/img/question_mark_icon_hover2.png")));
        questionmarkHover.setHeight(100);
        questionmarkHover.setWidth(100);
        questionmarkHover.setPosition(Commons.WORLD_WIDTH - questionmarkImage.getWidth() - 35, Commons.WORLD_HEIGHT - questionmarkImage.getHeight() - 35);
        questionmarkHover.addAction(Actions.alpha(0.0f));

        //Backgroundimage
        backgroundImage = new Image(new Texture(Gdx.files.internal("system/img/varsomwings_big.png")));

        // Scale bg image proportional to screen
        backgroundImage.setWidth(backgroundImage.getWidth() - (backgroundImage.getHeight() - Commons.WORLD_HEIGHT));
        backgroundImage.setHeight(Commons.WORLD_HEIGHT);
        backgroundImage.setPosition(Commons.WORLD_WIDTH/2 - backgroundImage.getWidth()/2, Commons.WORLD_HEIGHT/2 - backgroundImage.getHeight()/2);
        backgroundImage.addAction(Actions.sequence(Actions.alpha(0.2f, 0.0f)));

        //Varsomlogo at top
        varsomLogo = new Image(new Texture(Gdx.files.internal("system/img/varsomgames_text-02.png")));
        varsomLogo.setPosition(Commons.WORLD_WIDTH / 2 - varsomLogo.getWidth() / 2, Commons.WORLD_HEIGHT-1.25f*varsomLogo.getHeight());
        varsomLogo.setOrigin(varsomLogo.getWidth()/2, varsomLogo.getHeight()/2);
        //varsomLogo.setScale(0.25f);
        //varsomLogo.setScale(0.25f);


        // Add all games
        gamesList.add(cargameImage);
        cargameImage.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (currentGame == 0 && !swipedLeft && !swipedRight) {
                    selectMenuItem();
                    //Gdx.app.log("clicked", "pressed OtherGame.");
                }
            }
        });

        for(int i = 1; i < NR_OF_GAMES; i++){

            gamesList.add(new Image(new Texture(Gdx.files.internal("system/img/unavailable_game.png"))));

        }

        // Set position and scale
        int y = 0, x = 0;

        for(int i = 0; i < NR_OF_GAMES; i++){

            if(x < 3)
                gamesList.elementAt(i).setPosition(Commons.WORLD_WIDTH/2 - cargameImage.getWidth()/2 + x * GAME_SPACING, Commons.WORLD_HEIGHT - 650 - GAME_SPACING*y);
            else
                gamesList.elementAt(i).setPosition(Commons.WORLD_WIDTH/2 - cargameImage.getWidth()/2 - (5 - x) * GAME_SPACING, Commons.WORLD_HEIGHT - 650 - GAME_SPACING*y);

            gamesList.elementAt(i).setOrigin(gamesList.elementAt(i).getWidth()/2, gamesList.elementAt(i).getHeight()/2);
            gamesList.elementAt(i).setScale(GAME_SCALE);

            if(i != currentGame)
                gamesList.elementAt(i).addAction(Actions.sequence(Actions.alpha(GAME_ALPHA, 0.0f)));

            x++;

            if(i == 4){
                y++;
                x = 0;
            }

        }

        gamesList.elementAt(currentGame).addAction(Actions.scaleTo(GAME_SCALE_SELECTED, GAME_SCALE_SELECTED, 0.0f));

        // Create devices as actors.. and labels
        int devices = 8, maxColumns = 4;
        int rows, columns;
        int PAD_CONST = Commons.WORLD_WIDTH/32;


        columns = (devices < maxColumns ? devices : maxColumns);

        rows = (columns == maxColumns ? (devices/maxColumns) : 1);


        int maximumDevices = 8, posY = Commons.WORLD_HEIGHT/9;
        float offsetX = Commons.WORLD_WIDTH / (maximumDevices+1);
        Texture textureGray = new Texture(Gdx.files.internal("system/img/device_gray.png"));
        Texture textureRed = new Texture(Gdx.files.internal("system/img/device_red_connected.png"));
        for (int i = 0; i < maximumDevices ; i++) {
            devicesGray.add(new Image(textureGray));
            devicesGray.elementAt(i).setScale(DEVICES_SCALE);
            devicesGray.elementAt(i).setPosition(offsetX * (i + 1) - devicesGray.elementAt(i).getWidth() * DEVICES_SCALE / 2, posY);
            devicesGray.elementAt(i).addAction(Actions.sequence(Actions.alpha(0.25f)));

            devicesActive.add(new Image(textureRed));
            devicesActive.elementAt(i).setScale(DEVICES_SCALE);
            devicesActive.elementAt(i).setPosition(offsetX * (i + 1) - devicesGray.elementAt(i).getWidth() * DEVICES_SCALE / 2, posY);
            devicesActive.elementAt(i).addAction(Actions.sequence(Actions.alpha(0.0f)));

            devicesLabels.add(new Label("", new Label.LabelStyle(devicesFont, Commons.VARSOM_RED)));
            devicesLabels.get(i).setPosition(offsetX * (i + 1)-devicesLabels.get(i).getPrefWidth()/2, posY);
            //devicesLabels.get(i).setOrigin(devicesLabels.get(i).getPrefWidth()/2,devicesLabels.get(i).getHeight()/2);
            devicesLabels.get(i).setWrap(true);
            devicesLabels.get(i).setAlignment(Align.top,Align.center);

        }
       // textureGray.dispose();
       // textureRed.dispose();
    }

    // Checking if Networklistener have recieved a dPad-package
    // If something has updated, update currentButton with dpadData(x-value)
    public void handleDpad() {

        if(NetworkListener.dPadSelect){

            if(!swipedLeft && !swipedRight && !swipedUp && !swipedDown)
                selectMenuItem();

            NetworkListener.dPadSelect = false;
        }

        if (NetworkListener.dpadTouched == true) {

            NetworkListener.dpadTouched = false;
            Gdx.app.log("in varsommenu", "The dpax.x" + NetworkListener.dpadx);
            Gdx.app.log("in varsommenu", "The dpax.x" + NetworkListener.dpady);

            if (NetworkListener.dpadx == DPad.RIGHT && !swipedLeft && !swipedRight) {

                handleMenuRight();

            }

            else if(NetworkListener.dpadx == DPad.LEFT && !swipedLeft && !swipedRight){

                handleMenuLeft();

            }

            else if(NetworkListener.dpady == DPad.UP && !swipedUp && !swipedDown && row > 0){

                handleMenuUp();

            }

            else if(NetworkListener.dpady == DPad.DOWN && !swipedUp && !swipedDown && row < 2){

                handleMenuDown();

            }

        }

    }

    private void selectMenuItem() {

        if(row == 0){

            if(currentHelpButton == 1){

                System.out.println("Help selected");

            }

            else{

                System.out.println("Shutdown selected");
                Skin skin2 = new Skin(Gdx.files.internal("data/uiskin.json"));
                if(!shutDownReady) {
                    errorDialog =  new Dialog("", skin2){
                        {
                            text("Are you sure you want to exit?");
                            button("No", "hide");
                            button("Yes", "Exit");
                        }

                        @Override
                        protected void result(Object object) {
                            if (object.toString() == "Exit") {
                                Gdx.app.exit();
                                dispose();
                            }
                            else if(object.toString() == "hide") {
                                hide();

                            }
                        }
                    };
                    errorDialog.setSize(errorDialog.getPrefWidth(), errorDialog.getPrefHeight());

                    //errorDialog.getContentTable().
                    //errorDialog.getButtonTable().setSize(errorDialog.getButtonTable().getPrefWidth()*5,errorDialog.getButtonTable().getPrefHeight()*5);
                    errorDialog.setPosition(Math.round((stage.getWidth() - errorDialog.getWidth()) / 2), Math.round((stage.getHeight() - errorDialog.getHeight()) / 2));
                    stage.addActor(errorDialog);
                    //errorDialog.show(stage);
                }
                else if(shutDownReady) {
                    Gdx.app.exit();
                    skin2.dispose();
                    dispose();
                }
                shutDownReady = true;

            }

            return;

        }

        // else start some game

        switch(currentGame) {
            // Could consider making constants for game-IDs
            case 0:
                gamesList.elementAt(currentGame).addAction(Actions.sequence(Actions.scaleBy(1.2f, 1.2f, 0.6f), Actions.alpha(0.0f, 0.6f), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        VarsomGame carGame = new CarGame((VarsomSystem) Gdx.app.getApplicationListener());
                        varsomSystem.setActiveGame(carGame);


                    }
                })));
                hide();
                break;
            case 1:
                gamesList.elementAt(currentGame).addAction(Actions.sequence(Actions.scaleBy(1.2f, 1.2f, 0.6f), Actions.alpha(0.0f, 0.6f), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        // currently runs the carGame too, but should implement another game
                        // TODO: Implement otherGame instead of running carGame again!
                        VarsomGame otherGame = new CarGame((VarsomSystem) Gdx.app.getApplicationListener());
                        varsomSystem.setActiveGame(otherGame);
                    }
                })));
                hide();

                break;
            case 2:
                Gdx.app.exit();
                dispose();
                break;
            default:
                break;

        }
    }

    private void handleMenuLeft(){

        swipedRight = true;

        if(row == 0)
            return;

        if(currentGame == 0)
            currentGame = 4;
        else if(currentGame == 5)
            currentGame = 9;
        else
            currentGame--;


    }

    private void handleMenuRight(){

        swipedLeft = true;

        if(row == 0)
            return;

        if(currentGame == 4)
            currentGame = 0;
        else if(currentGame == 9)
            currentGame = 5;
        else
            currentGame++;

    }

    private void handleMenuUp(){

        if(row == 2)
            currentGame-=5;

        swipedDown = true;
        stage.cancelTouchFocus();

    }

    private void handleMenuDown(){

        if(row == 1)
            currentGame+=5;

        swipedUp = true;
        stage.cancelTouchFocus();

    }



}
