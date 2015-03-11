package com.varsom.mpclient;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

import com.varsom.mpclient.Packet.*;

/**
 * Created by christoffer on 2015-03-06.
 */
public class NetworkListener extends Listener {

    private Client client;

    // If you want to send a object, you need to send it with this client variable
    public void init(Client client) {
        this.client = client;
    }

    public void connected(Connection connection) {
        Log.info("[SERVER] You have connect.");
        client.sendTCP(new LoginRequest());
    }

    public void disconnected(Connection connection) {
        Log.info("[SERVER] you have disconnect.");
    }

    public void received(Connection c, Object o) {
        if (o instanceof LoginRequest) {
            Boolean answer = ((LoginAnswer) o).accepted;
            if (answer) {

            }else {
                c.close();
            }
        }
    }
}
