package com.varsom.system.games.car_game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.varsom.system.Commons;
import com.varsom.system.VarsomSystem;
import com.varsom.system.games.car_game.helpers.AssetLoader;
import com.varsom.system.games.car_game.helpers.KrazyRazyCommons;
import com.varsom.system.network.NetworkListener;

import java.util.ArrayList;
import java.util.StringTokenizer;

import java.util.Vector;

public class ResultScreen extends ScaledScreen {

    protected VarsomSystem varsomSystem;
    private ArrayList<String> carOrder;

    //TODO Load files from AssetLoader
    private Skin skin = AssetLoader.skin;

   // private TextButton btnOK;
    private Label result, winningPlayer, score, proceed;
    private Vector<Label> playerStandings;
    private Image smileyMad, winningCarImage;
    private float frameCounter = 0;
    private int winnerCar = 0;

    //private String playerScores;

    public ResultScreen(VarsomSystem varsomSystem, String names, int carType) {

        this.varsomSystem = varsomSystem;
        this.winnerCar = carType;
        carOrder = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(names, "\n");
        while(st.hasMoreTokens()){
            carOrder.add(st.nextToken());
        }

        playerStandings = new Vector<Label>();

        //Switch screen on the controller to NavigationScreen
        varsomSystem.getMPServer().changeScreen(Commons.NAVIGATION_SCREEN);
    }

    @Override
    public void show() {

        generateUI();

        stage.addActor(winningPlayer);
       // stage.addActor(result);
        //stage.addActor(score);
        stage.addActor(proceed);
        stage.addActor(winningCarImage);
        stage.addActor(smileyMad);

        for(int i = 0; i < playerStandings.size(); i++){

            stage.addActor(playerStandings.get(i));

        }

        winningPlayer.addAction(Actions.sequence(Actions.alpha(1.f)));

        winningCarImage.addAction(Actions.sequence(Actions.delay(0.0f), Actions.alpha(1.f), Actions.rotateTo(360f, 1.5f)));
        smileyMad.addAction(Actions.sequence(Actions.delay(0.0f), Actions.alpha(1.f)));

        for(int i = 0; i < playerStandings.size(); i++){

            playerStandings.elementAt(i).addAction(Actions.sequence(Actions.delay(1.f + 0.1f*i), Actions.alpha(1.f)));

            if( i == playerStandings.size() - 1)
                proceed.addAction(Actions.sequence(Actions.delay(1.1f + 0.1f*i), Actions.alpha(1.f)));

        }

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(24 / 255.0f, 102 / 255.0f, 105 / 255.0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        handleDpad();
        handleScore();

        frameCounter += Gdx.graphics.getDeltaTime();
        proceed.setPosition(proceed.getX(), proceed.getY() + 0.2f * (float) Math.sin(frameCounter));

        stage.act();
        stage.draw();
    }

    private void setWinningCar(Label.LabelStyle styleWinning){

        //Title
        switch(winnerCar){

            case KrazyRazyCommons.TURTLE:
                winningPlayer = new Label("Turtely Awesome, " + carOrder.get(0), styleWinning);
                winningCarImage = new Image(AssetLoader.carTextureTurtle);
                break;

            case KrazyRazyCommons.COFFIN:
                winningPlayer = new Label(carOrder.get(0) + " Killed it!", styleWinning);
                winningCarImage = new Image(AssetLoader.carTextureCoffin);
                break;

            case KrazyRazyCommons.HOTDOG:
                winningPlayer = new Label(carOrder.get(0) + ", is the weiner!", styleWinning);
                winningCarImage = new Image(AssetLoader.carTextureHotdog);
                break;

            case KrazyRazyCommons.PIGGELIN:
                winningPlayer = new Label("ICE COLD, " + carOrder.get(0), styleWinning);
                winningCarImage = new Image(AssetLoader.carTexturePiggelin);
                break;
            case KrazyRazyCommons.TURTLE_COPY:
                winningPlayer = new Label("Turtely Awesome, " + carOrder.get(0), styleWinning);
                winningCarImage = new Image(AssetLoader.carTextureTurtle2);
                break;

            case KrazyRazyCommons.COFFIN_COPY:
                winningPlayer = new Label(carOrder.get(0) + " Killed it!", styleWinning);
                winningCarImage = new Image(AssetLoader.carTextureCoffin2);
                break;

            case KrazyRazyCommons.HOTDOG_COPY:
                winningPlayer = new Label(carOrder.get(0) + ", is the weiner!", styleWinning);
                winningCarImage = new Image(AssetLoader.carTextureHotdog2);
                break;

            case KrazyRazyCommons.PIGGELIN_COPY:
                winningPlayer = new Label("ICE COLD, " + carOrder.get(0), styleWinning);
                winningCarImage = new Image(AssetLoader.carTexturePiggelin2);
                break;

            default:
                System.out.println("Uh oh some random car is in the game.. ");
                winningPlayer = new Label(carOrder.get(0) + " is victorious!", styleWinning);

        }


    }

    public void generateUI(){

        //Font
        BitmapFont winningFont = Commons.getFont(150, AssetLoader.krazyFontFile, KrazyRazyCommons.KRAZY_GREEN, 3f, KrazyRazyCommons.KRAZY_BLUE);
        BitmapFont playerStandingFont = Commons.getFont(70, AssetLoader.krazyFontFile,KrazyRazyCommons.KRAZY_BLUE, 3f, KrazyRazyCommons.KRAZY_GREEN);
        BitmapFont continueFont = Commons.getFont(80, AssetLoader.krazyFontFile, KrazyRazyCommons.KRAZY_GREEN, 3f, KrazyRazyCommons.KRAZY_BLUE);

        Label.LabelStyle styleWinning = new Label.LabelStyle(winningFont, Color.WHITE);
        Label.LabelStyle stylePlayerStanding = new Label.LabelStyle(playerStandingFont, Color.WHITE);
        Label.LabelStyle styleContinue = new Label.LabelStyle(continueFont, Color.WHITE);

        smileyMad = new Image(AssetLoader.krazyThingyTexture_mad);
        smileyMad.setScale(1.5f, 1.5f);

        setWinningCar(styleWinning);

        winningCarImage.setWidth(winningCarImage.getWidth() / 3);
        winningCarImage.setHeight(winningCarImage.getHeight() / 3);

        winningPlayer.setPosition(Commons.WORLD_WIDTH /2 - winningPlayer.getWidth()/2, Commons.WORLD_HEIGHT - winningPlayer.getHeight());
        winningPlayer.addAction(Actions.alpha(0.f));

        winningCarImage.setPosition(winningPlayer.getX() - winningCarImage.getWidth() - 50, winningPlayer.getY() );
        winningCarImage.setOrigin(winningCarImage.getWidth() / 2, winningCarImage.getHeight() / 2);
        smileyMad.setOrigin(smileyMad.getWidth() / 2, smileyMad.getHeight() / 2);
        winningCarImage.addAction(Actions.alpha(0.f));
        smileyMad.setPosition(Commons.WORLD_WIDTH * 0.8f, Commons.WORLD_HEIGHT / 2 - smileyMad.getHeight() / 2);
        smileyMad.addAction(Actions.alpha(0.f));

        for(int i = 1; i < carOrder.size(); i++){

            Label playerStanding = new Label( (i + 1) + ". " + carOrder.get(i), stylePlayerStanding ) ;
            //Label playerStanding = new Label ( (i + 1) + ". " + "Carlbaum", stylePlayerStanding );
            playerStanding.setPosition(Commons.WORLD_WIDTH / 2 - playerStanding.getWidth() / 2, Commons.WORLD_HEIGHT - winningPlayer.getHeight() - playerStanding.getHeight()*i  );
            playerStanding.addAction(Actions.alpha(0.f));
            playerStandings.add(playerStanding);

        }

/*
        result = new Label(carOrder.get(0) + " is VICTORIOUS!!", skin);
        result.setPosition(Commons.WORLD_WIDTH / 2 - result.getWidth() / 2, Commons.WORLD_HEIGHT * 0.8f - result.getHeight());
        score = new Label(playerScores, skin);
        score.setPosition(Commons.WORLD_WIDTH / 2 - score.getWidth() / 2, Commons.WORLD_HEIGHT * 0.6f - score.getHeight());*/

        proceed = new Label("Press anywhere to continue", styleContinue);
        proceed.addAction(Actions.alpha(0.f));
        proceed.setPosition(Commons.WORLD_WIDTH / 2 - proceed.getWidth() / 2, Commons.WORLD_HEIGHT * 0.2f);

      /*  btnOK = new TextButton("OK", skin);
        btnOK.setPosition(Commons.WORLD_WIDTH / 2 - btnOK.getWidth() / 2, Commons.WORLD_HEIGHT * 0.2f);*/

      /*  proceed.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("clicked", "pressed the OK button.");
                varsomSystem.getMPServer().setJoinable(true);
                ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(varsomSystem));
            }
        });*/

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

    public void handleDpad() {

        if (NetworkListener.dPadSelect) {
            Gdx.app.log("clicked", "pressed the OK button.");
            varsomSystem.getMPServer().setJoinable(true);
            ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu(varsomSystem));

            NetworkListener.dPadSelect = false;
        }
    }

    // A later feature
    private void handleScore(){

        /*playerScores = ": Name : Time/Score/Dist : Knockouts :\n";

        for(String car : carOrder) {
            Gdx.app.log("handleScore " + carOrder., varsomSystem.getServer().getConnections()[carList.get(i).getID()].toString());
            //Ranking order
            playerScores += varsomSystem.getServer().getConnections()[carList.get(i).getID()].toString() + " : ";
            //Points or time
            playerScores += carList.get(i).getTraveledDistance() + " : ";
            //Knockouts
            playerScores += "- : \n";
        }

        score.setText(playerScores);*/

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