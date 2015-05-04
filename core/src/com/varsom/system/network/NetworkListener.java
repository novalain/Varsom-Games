package com.varsom.system.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import com.varsom.system.VarsomSystem;
import com.varsom.system.network.Packet.ExitRequest;
import com.varsom.system.network.Packet.LoginAnswer;
import com.varsom.system.network.Packet.LoginRequest;
import com.varsom.system.network.Packet.PauseRequest;
import com.varsom.system.network.Packet.SendDpadData;

import static com.varsom.system.network.Packet.GamePacket;


public class  NetworkListener extends Listener {

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
            //System.out.println("NETWORK: " + "Recieved GamePacket");
            String toDeCode = ((GamePacket) o).message;
            //System.out.println("Connection " + c.getID() + " says " + toDeCode);
            varsomSystem.getActiveGame().handleDataFromClients(c, toDeCode);
            //varsomGame.handleDataFromClients();
        }

        else if (o instanceof SendDpadData) {
            int dpad = ((SendDpadData) o).dpaddir;
            System.out.println("Dpad dir is " + dpad);
        }

    }

}
