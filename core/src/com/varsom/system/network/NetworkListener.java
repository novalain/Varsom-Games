package com.varsom.system.network;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import com.varsom.system.VarsomSystem;
import com.varsom.system.abstract_gameobjects.VarsomGame;
import com.varsom.system.network.Packet.LoginAnswer;
import com.varsom.system.network.Packet.LoginRequest;
import com.varsom.system.network.Packet.PauseRequest;
import com.varsom.system.network.Packet.ExitRequest;

import static com.varsom.system.network.Packet.Message;
import static com.varsom.system.network.Packet.GamePacket;


/**
 *
 */
public class  NetworkListener extends Listener {

    public String message;

    public static boolean pause = false;
    public static boolean goBack = false;

    private VarsomSystem varsomSystem;

    public NetworkListener(VarsomSystem varsomSystem){
        this.varsomSystem = varsomSystem;
    }

    public void connected(Connection c) {
        Log.info("[SERVER] Someone has connect.");
    }

    public void disconnected(Connection c) {
        Log.info("[SERVER] Someone have disconnect.");
    }

    public void received(Connection c, Object o) {
        //if a LoginRequest is sent from the client, accept the request
        if (o instanceof LoginRequest ) {
            LoginAnswer loginaccepted = new LoginAnswer();
            loginaccepted.accepted = true;
            // Send the packet back with the variable login.accepted, that is now true
            c.sendTCP(loginaccepted);
            //Write in the log if login was successful
            System.out.println("Connection: " + c.getID() + " Login is accepted");

        }
        //Message is received from the client
        else if (o instanceof Message) {

            //The received message is saved in a string
            //message = ((Message) o).message;

            //Writes the message in the log
            //System.out.println("MESSAGE: " + message);

        }

        else if (o instanceof PauseRequest) {
            //saves the received bool
            pause = ((PauseRequest) o).pause;
        }

        else if (o instanceof ExitRequest) {
            //saves the received bool
            goBack = ((ExitRequest) o).exit;
        }

        else if (o instanceof GamePacket) {
            System.out.println("NETWORK: " + "Recieved GamePacket");
            String toDeCode = ((GamePacket) o).message;
            //System.out.println("Connection " + c.getID() + " says " + toDeCode);
            varsomSystem.getActiveGame().handleDataFromClients(c, toDeCode);
            //varsomGame.handleDataFromClients();
        }

    }

}
