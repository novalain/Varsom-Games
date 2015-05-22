package com.varsom.system;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import com.varsom.system.abstract_gameobjects.VarsomGame;
import com.varsom.system.games.car_game.com.varsomgames.cargame.CarGame;
import com.varsom.system.games.other_game.OtherGame;
import com.varsom.system.network.BroadcastServerThread;
import com.varsom.system.network.MPServer;
import com.varsom.system.screens.VarsomSplash;

import java.io.IOException;

public class VarsomSystem extends /*ApplicationAdapter*/Game {
    public static final String TITLE= "Varsom-System";

    //an array that stores the games IDs

    public static final int SIZE = 2;
    public static int[] games = new int[SIZE];

    static protected MPServer mpServer;

    private VarsomGame activeGame;

    private Stage activeStage;
    private Skin skin;
    private Dialog errorDialog;

    public VarsomSystem(){

    }

    @Override
	public void create () {
        activeGame = null;
        skin = new Skin(Gdx.files.internal("data/uiskin.json"));
        startServers();
        setScreen(new VarsomSplash(this));


        //TODO ful-lösning, vi borde inte behöva berätta vilka spel eller hur många utan systemet ska känna av det
        //Handle games
        //For CarGame
        games[0] = CarGame.ID;

        //For OtherGame
        games[1] = OtherGame.ID;
    }

    @Override
    public void dispose() {
        mpServer.gameRunning(false);
        mpServer.stop();
        super.dispose();
    }
// starServer starts the MPServer and the broadcastServer
    private void startServers(){

        try {
            mpServer = new MPServer(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Broadcast sender
        try {
            new BroadcastServerThread().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MPServer getMPServer(){
        return mpServer;
    }
    public Server getServer(){
        return mpServer.getServer();
    }

    public VarsomGame getActiveGame(){
        return activeGame;
    }

    public void setActiveGame(VarsomGame vG){

        this.activeGame = vG;

    }
    // a function that is used in every screen to get the stage that is active
    public void setActiveStage(Stage s){

        this.activeStage = s;

    }


    //a function to show a popup message when a player is disconnected
    //
    //TODO lägga in en error message funktion för om man är mindra än 2 spelare
    public void errorMessage(Connection c){
        final Connection myC = c;

        errorDialog = new Dialog("", skin) {
            {
                text(myC.toString() + " has disconnected");
                button("Ok");
            }

            @Override
            protected void result(final Object object) {

            }

        };

        //this is values that can be modified if the popup window is to big or to small
        errorDialog.setHeight(errorDialog.getPrefHeight()*2);
        errorDialog.setWidth(errorDialog.getPrefWidth()*2);
        errorDialog.setScale(2);

        activeStage.addActor(errorDialog);
        activeStage.setKeyboardFocus(errorDialog);
        activeStage.setScrollFocus(errorDialog);



    }


}