package com.varsom.mpserver;

import android.widget.TextView;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;
import com.varsom.mpserver.Packet.LoginAnswer;
import com.varsom.mpserver.Packet.LoginRequest;


/**
 * Created by christoffer on 2015-03-06.
 */
public class NetworkListener extends Listener {


    //private static final String TAG = NetworkListener.class.getSimpleName();
    private TextView ourOutput;

    public void init(TextView b) {
        this.ourOutput = b;
    }

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
            ourOutput.setText(((Packet.Message) o).message);
            //Log.info(message);
            //android.util.Log.d(TAG, message);
        }

    }


}
