package com.controller_app;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.controller_app.network.NetworkListener;
import com.controller_app.helper.Commons;
import com.controller_app.screens.ConnectionScreen;
import com.controller_app.screens.ControllerScreen;

import java.io.IOException;
import java.sql.Connection;

import com.controller_app.network.MPClient;
import com.controller_app.screens.NavigationScreen;
import com.controller_app.screens.SettingsScreen;
import com.esotericsoftware.kryonet.Client;
import com.controller_app.screens.StandbyScreen;

/**
 * <h1>ControllerApp Main Class</h1>
 * This main class holds reference to all other screens,
 * and a switch case to change between them.
 *
 * @author  VarsomGames
 * @version 1.0
 * @since   2015-05-07
 */
public class Main extends Game {
    /**
     * @param settingsScreen This calls to the SettingsScreen
     * @param connectionScreen This calls to the ConnectionScreen
     * @param navigationScreen This calls to the NavigationScreen
     * @param controllerScreen This calls to the ControllerScreen
     * @param standbyScreen This calls to the StandbyScreen
     * @param mpClient This calls to the MPClient to start a new network connection
     */
    private SettingsScreen settingsScreen;
    private ConnectionScreen connectionScreen;
    private NavigationScreen navScreen;
    private ControllerScreen controllerScreen;
    private StandbyScreen standbyScreen;
    private MPClient mpClient;

    /**
     * The create-function opens a mpClient, creates the
     * screens and calls on function changeScreen
     * @exception IOException on MPClient
     * @return Nothing.
     */
    @Override
    public void create() {

        try {
            mpClient = new MPClient();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gdx.app.log("check", "created app");
        settingsScreen = new SettingsScreen(this);
        connectionScreen = new ConnectionScreen(this, mpClient);
        controllerScreen = new ControllerScreen(this, mpClient);
        navScreen = new NavigationScreen(this, mpClient);
        standbyScreen = new StandbyScreen(this, mpClient);
        mpClient.controllerScreen = controllerScreen;

        changeScreen(Commons.CONNECTION_SCREEN);

    }
    /**
     * The changeScreen-function changes screen based on an index passed
     * on by the program.
     * @param s The index for switching screens
     * @return Nothing.
     */
    public void changeScreen(int s) {
        switch (s) {

            case Commons.CONNECTION_SCREEN:
                Gdx.input.setInputProcessor(connectionScreen.getStage());
               // connectionScreen.check = 1;
                setScreen(connectionScreen);
                break;
            case Commons.NAVIGATION_SCREEN:
                Gdx.input.setInputProcessor(navScreen.getStage());
               // connectionScreen.check = 2;
                setScreen(navScreen);
                break;
            case Commons.SETTINGS_SCREEN:
                //TODO: Settings Screen
                Gdx.input.setInputProcessor(settingsScreen.getStage());
              //  connectionScreen.check = 2;
                setScreen(settingsScreen);
                break;
            case Commons.CONTROLLER_SCREEN:
                Gdx.input.setInputProcessor(controllerScreen.getStage());
               // connectionScreen.check = 2;
                setScreen(controllerScreen);
                break;

            default: System.out.println("Error in changeScreen");
        }
    }
    // TODO: Maybe delete this function if not used!
    /**
     * @return The created mpClient
     */
    public MPClient getMpClient(){
        return mpClient;
    }

    /**
     * @return The created client of mpClient
     */
    public Client getClient(){
        return mpClient.client;
    }

    /**
     * @return The created settingsScreen
     */
    public SettingsScreen getSettingsScreen(){
        return settingsScreen;
    }

    /**
     * @return The created ConnectionScreen
     */
    public ConnectionScreen getConnectionScreen(){
        return connectionScreen;
    }

    /**
     * @return The created NavigationScreen
     */
    public NavigationScreen getNavigationScreen(){
        return navScreen;
    }

    /**
     * @return The created ControllerScreen
     */
    public ControllerScreen getControllerScreen(){
        return controllerScreen;
    }

    /**
     * Handles input from the controller
     * and changes screen in main
     */
    public void handleController(){
        //TODO gör get och set funktion för changeController i networklistener istället
        if(NetworkListener.changeController) {
            changeScreen(NetworkListener.controller);
            NetworkListener.changeController = false;
        }
    }

}
