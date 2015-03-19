package com.varsom.mpclient;

import android.widget.EditText;
import android.widget.TextView;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.varsom.mpclient.Packet.LoginAnswer;
import com.varsom.mpclient.Packet.LoginRequest;

/**
 *
 */
public class NetworkListener extends Listener {

    private Client client;
    private EditText ourMessage;
    private TextView ourOutput;

    public String message;


    // If you want to send a object, you need to send it with this client variable
    public void init(Client client, EditText o, TextView b) {
        this.client = client;
        this.ourMessage = o;
        this.ourOutput = b;
    }

    public void connected(Connection c) {
        client.sendTCP(new LoginRequest());
        System.out.println("You have connected.");

    }

    public void disconnected(Connection c) {
        System.out.println("You have disconnected.");
    }

    public void received(Connection c, Object o) {
        // checks for login answers from server
        if (o instanceof LoginAnswer) {
            Boolean answer = ((LoginAnswer) o).accepted;

            if (answer) {
                String mess = o.toString();
                System.out.println("Message: " + mess);

            }else {
                c.close();
                ourOutput.append("Nothing");
            }

        }
        if (o instanceof Packet.Message) {

            //The received message is saved in a string
            message = ((Packet.Message) o).message;

            //Writes the message in the log
            System.out.println("MESSAGE: " + message);

        }

    }

}
