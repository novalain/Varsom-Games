package com.controller_app;


import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;

import java.io.IOException;

/**
 *
 */
public class MPClient {
    public Client client;

    public MPClient() throws IOException {



        client = new Client();
        register();

        NetworkListener nl = new NetworkListener();

        // Initialise variables (not sure if it needed, maybe later)
        nl.init(client);

        client.addListener(nl);

        client.start();

        System.setProperty("java.net.preferIPv4Stack", "true");



    }

    //TODO: get IP from user
    public void connectToServer() {

        try{
            client.connect(50000, "192.168.1.1", 54555, 64555);
        }
        catch(IOException e){
            e.printStackTrace();
            client.stop();
        }

    }

    // Sends a string with drive-state and rotation
    public void sendPacket(String p) {

        Packet.Message sendMessage = new Packet.Message();
        sendMessage.message = p;
        client.sendUDP(sendMessage);
        //client.sendTCP(sendMessage);
    }



    // Register packets to a kryo
    private void register() {
        Kryo kryo = client.getKryo();
        // Register packets
        kryo.register(Packet.LoginRequest.class);
        kryo.register(Packet.LoginAnswer.class);
        kryo.register(Packet.Message.class);
    }

}