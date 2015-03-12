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
 * Created by christoffer on 2015-03-06.
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
        //ourOutput.setText("");
        ourOutput.append("You have connected.");

    }

    public void disconnected(Connection connection) {
        ourOutput.setText("");
        ourOutput.append("You have disconnected.");
    }

    public void received(Connection c, Object o) {
        if (o instanceof LoginRequest) {
            Boolean answer = ((LoginAnswer) o).accepted;


            if (answer) {

                Message sendMessage = new Message();

                sendMessage.message = ourMessage.getText().toString();

                client.sendTCP(sendMessage);

                ourOutput.append("Hello");

            }else {
                c.close();
                ourOutput.append("Nothing");
            }
        }
    }


}
