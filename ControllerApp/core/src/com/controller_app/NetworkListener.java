package com.controller_app;


import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

/**
 *
 */
public class NetworkListener extends Listener {

    private Client client;
    //private EditText ourMessage;
    //private TextView ourOutput;

    public String message;


    // If you want to send a object, you need to send it with this client variable
    public void init(Client client) {
        this.client = client;
        //this.ourMessage = o;
        //this.ourOutput = b;
    }

    public void connected(Connection c) {
        client.sendTCP(new Packet.LoginRequest());
        System.out.println("You have connected.");

    }

    public void disconnected(Connection c) {
        System.out.println("You have disconnected.");
    }

    public void received(Connection c, Object o) {
        // checks for login answers from server
        if (o instanceof Packet.LoginAnswer) {
            Boolean answer = ((Packet.LoginAnswer) o).accepted;

            if (answer) {
                String mess = o.toString();
                System.out.println("Message: " + mess);

            }else {
                c.close();
                //ourOutput.append("Nothing");
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
