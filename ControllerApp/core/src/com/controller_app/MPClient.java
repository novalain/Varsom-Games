package com.controller_app;


import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;

import java.io.IOException;

/**
 *
 */
public class MPClient {
    public Client client;
    public ControllerScreen controllerScreen;
    

    public MPClient() throws IOException {

        client = new Client();
        register();

        NetworkListener nl = new NetworkListener();

        // Initialise variables (not sure if it needed, maybe later)
        nl.init(client, this);
        client.addListener(nl);

        client.start();

        System.setProperty("java.net.preferIPv4Stack", "true");

    }

    // get IP from user input and connects
    public void connectToServer(String ip) {

        try{
            client.connect(5000, ip, 54555, 64555);
        }
        catch(IOException e){
            e.printStackTrace();
            client.stop();
        }

    }

    // Sends a string with drive-state and rotation
    public void sendPacket(String p) {
        Packet.Message sendMessage = new Packet.Message();
        //sendMessage.message = p;
        //client.sendUDP(sendMessage);
    }

    // Register packets to a kryo
    private void register() {
        Kryo kryo = client.getKryo();
        // Register packets
        kryo.register(Packet.LoginRequest.class);
        kryo.register(Packet.LoginAnswer.class);
        kryo.register(Packet.Message.class);
        kryo.register(Packet.GamePacket.class);
        kryo.register(Packet.SendGameData.class);
        kryo.register(Packet.ShutDownPacket.class);
    }

    public void sendPacket(Boolean send) {

        final int TICKS_PER_SECOND = 1;
        System.out.println(send);
        while (send && client.isConnected()) {

            System.out.println("send" + send);
            System.out.println("client " + client.isConnected());

            Packet.GamePacket packet = new Packet.GamePacket();

            //System.out.println("TEST" + controllerScreen.getDrive());
            packet.message = controllerScreen.getDrive() + " " + controllerScreen.getRotation();
            client.sendUDP(packet);

            //System.out.println(packet);

            try {
                Thread.sleep(1000 / TICKS_PER_SECOND);
            } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
                break;
            }
        }

    }

}