package com.varsom.system.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import com.varsom.system.VarsomSystem;
import com.varsom.system.games.car_game.com.varsomgames.cargame.CarGame;
import com.varsom.system.network.Packet.*;
import com.varsom.system.screens.VarsomMenu;


public class  NetworkListener extends Listener {

    public static boolean pause = false;
    public static boolean goBack = false;
    public static boolean dPadSelect = false;
    public static int dpadx = 0;
    public static int dpady = 0;
    public static boolean dpadTouched;

    private VarsomSystem varsomSystem;

    public NetworkListener(VarsomSystem varsomSystem){
        this.varsomSystem = varsomSystem;
    }

    public void connected(Connection c) {
        Log.info("[SERVER] Someone has connected.");
    }

    public void disconnected(Connection c) {
        Log.info("[SERVER] Someone has disconnected.");
        varsomSystem.errorMessage(c);
    }

    public void received(Connection c, Object o) {
        //if a LoginRequest is sent from the client, accept the request
        if (o instanceof LoginRequest ) {
            LoginAnswer loginaccepted = new LoginAnswer();
            loginaccepted.accepted = true;
            c.setName(((LoginRequest) o).playerName);

            //if the server doesn't permit new clients to join tell the client
            if(!MPServer.joinable) {
                //tell the client to stand by
                loginaccepted.standby = true;
                System.out.println("StandbyOrder sent");
            }
            else {
                loginaccepted.standby = false;
            }
            // Send the packet back with the variable login.accepted, that is now true
            c.sendTCP(loginaccepted);
            //Write in the log if login was successful
            System.out.println("Connection: " + c.toString() + " Login is accepted");

        }

        else if (o instanceof PauseRequest) {
            //saves the received bool
            pause = ((PauseRequest) o).pause;
            System.out.println("Paused");
        }

        else if (o instanceof ExitRequest) {
            //saves the received bool
            goBack = ((ExitRequest) o).exit;
        }

        else if (o instanceof GamePacket) {
            String toDeCode = ((GamePacket) o).message;
            varsomSystem.getActiveGame().handleDataFromClients(c, toDeCode);
        }

        else if (o instanceof SendDPadData) {
            System.out.println("Tar emot information om knapparna");
            dPadSelect = ((SendDPadData) o).select;
            dpadx = ((SendDPadData) o).dataX;
            dpady = ((SendDPadData) o).dataY;
            goBack = ((SendDPadData) o).back;
            dpadTouched = true;

        }

        else if (o instanceof NameUpdate) {
            c.setName(((NameUpdate) o).name);
        }

    }

}
