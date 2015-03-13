package com.varsom.mpserver;

import android.widget.TextView;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import com.varsom.mpserver.Packet.LoginAnswer;
import com.varsom.mpserver.Packet.LoginRequest;

import static com.varsom.mpserver.Packet.Message;


/**
 *
 */
public class NetworkListener extends Listener {

    private TextView ourOutput;

    public void init(TextView b) {
        ourOutput = b;
    }

    public void connected(Connection connection) {
        Log.info("[SERVER] Someone has connect.");

    }

    public void disconnected(Connection connection) {
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
            //ourOutput.setText(((Packet.Message) o).message);

            //The received message is saved in a string
            String message = ((Message) o).message;

            //Writes the message in the log
            System.out.println("MESSAGE: " + message);
        }

    }


}
