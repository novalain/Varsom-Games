package com.varsom.system.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import com.varsom.system.VarsomSystem;
import com.varsom.system.abstract_gameobjects.VarsomGame;

import java.io.IOException;

/**
 *
 */
public class MPServer {
    private Server server;
    int TCP = 54555, UDP = 64555;
    private VarsomSystem varsomSystem;

    public MPServer(VarsomSystem varsomSystem) throws IOException {
        this.varsomSystem = varsomSystem;
        server = new Server();
        registerPackets();


        server.addListener(new NetworkListener(varsomSystem));

        //TCP, UDP
        server.bind(TCP, UDP);
        server.start();
    }

    public void gameRunning(boolean b) {
        Packet.SendGameData sGD = new Packet.SendGameData();
        sGD.send = b;

        try {
            // Sends the message to all clients
            server.sendToAllTCP(sGD);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        //TODO Tell clients that server is shutting down
        server.close();
    }

    public Server getServer(){
        return server;
    }
}
