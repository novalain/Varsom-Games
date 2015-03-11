package com.varsom.mpserver;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

/**
 * Created by christoffer on 2015-03-06.
 */
public class MPServer {
    private Server server;

    public MPServer() throws IOException {
        server = new Server();
        registerPackets();
        server.addListener(new NetworkListener());
        server.bind(54555);
        //TCP, UDP
        //server.bind(54555, 54666);
        server.start();
    }

    private void registerPackets() {
        Kryo kryo = server.getKryo();
        // Register packets
        kryo.register(Packet.LoginRequest.class);
        kryo.register(Packet.LoginAnswer.class);
        kryo.register(Packet.Message.class);
    }

}
