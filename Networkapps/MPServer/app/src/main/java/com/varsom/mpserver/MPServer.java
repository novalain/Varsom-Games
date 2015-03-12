package com.varsom.mpserver;

import android.widget.TextView;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

/**
 * Created by christoffer on 2015-03-06.
 */
public class MPServer {
    private Server server;

    public MPServer(TextView b) throws IOException {
        server = new Server();
        registerPackets();

        //server.addListener(new NetworkListener());

        NetworkListener nl = new NetworkListener();
        nl.init(b);
        server.addListener(nl);

        server.bind(54555, 64555);
        //TCP, UDP

        server.start();
    }

    private void registerPackets() {
        Kryo kryo = server.getKryo();
        // Register packets
        kryo.register(Packet.LoginRequest.class);
        kryo.register(Packet.LoginAnswer.class);
        kryo.register(Packet.Message.class);
    }

    public void stop() {
        server.close();
    }

}
