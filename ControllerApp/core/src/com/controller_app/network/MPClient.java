package com.controller_app.network;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;

import java.io.IOException;

import com.controller_app.screens.ControllerScreen;

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

        try {
            client.connect(5000, ip, 54555, 64555);
        } catch (IOException e) {
            e.printStackTrace();
            client.stop();
        }
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
        kryo.register(Packet.PauseRequest.class);
        kryo.register(Packet.ExitRequest.class);
        kryo.register(Packet.SendDPadData.class);
    }

    public void sendPacket(boolean send) {

        final int TICKS_PER_SECOND = 30;
        System.out.println(send);
        while (send && client.isConnected()) {

            Packet.GamePacket packet = new Packet.GamePacket();

            packet.message = controllerScreen.getDrive() + " " + controllerScreen.getRotation();
            client.sendUDP(packet);

            try {
                Thread.sleep(1000 / TICKS_PER_SECOND);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    //send a boolean for pause state
    public void sendPause(boolean p) {
        Packet.PauseRequest sendState = new Packet.PauseRequest();
        sendState.pause = p;
        client.sendTCP(sendState);
    }

    //send a boolean for pause state
    public void sendExit(boolean p) {
        Packet.ExitRequest sendState = new Packet.ExitRequest();
        sendState.exit = p;
        client.sendTCP(sendState);
        Gdx.app.log("in MPClient", "sent Exit");
    }

    public void sendDPadData(int i){
        Packet.SendDPadData dp = new Packet.SendDPadData();
        dp.data = i;
        client.sendTCP(dp);
    }
}