package com.varsom.mpserver;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

import com.varsom.mpserver.Packet.*;

/**
 * Created by christoffer on 2015-03-06.
 */
public class NetworkListener extends Listener {

    public void connected(Connection connection) {
        Log.info("[SERVER] Someone has connect.");
    }

    public void disconnected(Connection connection) {
        Log.info("[SERVER] Someone have disconnect.");
    }

    public void received(Connection c, Object o) {
        if (o instanceof LoginRequest) {
            LoginAnswer loginaccepted = new LoginAnswer();
            loginaccepted.accepted = true;
            // Send a packet back
            c.sendTCP(loginaccepted);
        }
        if (o instanceof LoginAnswer) {
            String message = ((Message) o).message;
            Log.info(message);
        }

    }
}
