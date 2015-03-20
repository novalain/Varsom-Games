package com.varsom.mpserver;

import android.os.NetworkOnMainThreadException;
import android.widget.EditText;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

/**
 *
 */
public class MPServer {
    private Server server;

    public MPServer(EditText b) throws IOException {
        server = new Server();
        registerPackets();
        server.addListener(new NetworkListener());
/*
        NetworkListener nl = new NetworkListener();
        nl.init(b);
        server.addListener(nl);
*/
        //TCP, UDP
        server.bind(54555, 64555);
        server.start();
    }

    public void sendMassMessage(EditText m) {

        Packet.Message sendMessage = new Packet.Message();
        sendMessage.message = m.getText().toString();

        try {
            // Sends the message to all clients
            server.sendToAllTCP(sendMessage);
            server.sendToAllUDP(sendMessage);
        } catch (NetworkOnMainThreadException e) {
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

}
