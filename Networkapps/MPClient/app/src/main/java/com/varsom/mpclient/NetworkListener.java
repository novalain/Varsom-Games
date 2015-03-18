package com.varsom.mpclient;

import android.widget.EditText;
import android.widget.TextView;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.varsom.mpclient.Packet.LoginAnswer;
import com.varsom.mpclient.Packet.LoginRequest;
import com.varsom.mpclient.Packet.Message;

/**
 *
 */
public class NetworkListener extends Listener {

    private Client client;
    private EditText ourIP;
    private EditText ourMessage;
    private TextView ourOutput;


    // If you want to send a object, you need to send it with this client variable
    public void init(Client client, EditText ip, EditText o, TextView b) {
        this.client = client;
        this.ourIP = ip;
        this.ourMessage = o;
        this.ourOutput = b;

        System.out.println("FUNC: init");
    }
    public void connected(Connection connection) {
        System.out.println("FUNC: connect");
        client.sendTCP(new LoginRequest());
        ourOutput.setText("");
        ourOutput.append("You have connected.");

    }

    public void disconnected(Connection connection) {
        ourOutput.setText("");
        ourOutput.append("You have disconnected.");
    }

    public void received(Connection c, Object o) {
        // checks for login answers from server
        if (o instanceof LoginAnswer) {
            Boolean answer = ((LoginAnswer) o).accepted;
            System.out.println("PREANSWER");

            if (answer) {
                System.out.println("ANSWER");
                Message sendMessage = new Message();
                sendMessage.message = ourMessage.getText().toString();
                c.sendTCP(sendMessage);

            }else {
                c.close();
                ourOutput.append("Nothing");
            }

        }

    }

}
