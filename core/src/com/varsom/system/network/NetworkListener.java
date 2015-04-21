package com.varsom.system.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import com.varsom.system.network.Packet.LoginAnswer;
import com.varsom.system.network.Packet.LoginRequest;

import static com.varsom.system.network.Packet.Message;


/**
 *
 */
public class  NetworkListener extends Listener {

    public String message;
/*
    public void init(EditText b) {
        //message = b.getText().toString();
        message = "";
    }
*/
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
            System.out.println("Login is accepted");


        }
        //Message is received from the client
        if (o instanceof Message) {

            //The received message is saved in a string
            message = ((Message) o).message;

            //Writes the message in the log
            System.out.println("MESSAGE: " + message);

        }

    }

}
