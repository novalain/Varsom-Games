package com.varsom.system.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

/**
 *
 */
public class MPServer {
    private Server server;
    int TCP = 54555, UDP = 64555;

    public MPServer() throws IOException {
        server = new Server();
        registerPackets();
        server.addListener(new NetworkListener());
/*
        NetworkListener nl = new NetworkListener();
        nl.init(b);
        server.addListener(nl);
*/
        //TCP, UDP
        server.bind(TCP, UDP);
        server.start();
    }

    public void sendMassMessage() {

        Packet.Message sendMessage = new Packet.Message();

        try {
            // Sends the message to all clients
            server.sendToAllTCP(sendMessage);
            server.sendToAllUDP(sendMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

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

    public Server getServer(){
        return server;
    }

}
