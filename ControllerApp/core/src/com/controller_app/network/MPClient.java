package com.controller_app.network;

import com.badlogic.gdx.Gdx;
import com.controller_app.screens.ConnectionScreen;
import com.controller_app.screens.ControllerScreen;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;

import java.io.IOException;

public class MPClient {
    public Client client;
    public ControllerScreen controllerScreen;
    private BroadcastClient broadcastClient;
    public boolean correctIP;
    private String serverIP;
    private Thread networkThread;
    private ConnectionScreen connectionScreen;
    private boolean closeThread;


    public MPClient() throws IOException {
        correctIP = false;
        closeThread = true;

        client = new Client();
        register();

        NetworkListener nl = new NetworkListener();
        networkThread = newThread();

        // Initialise variables (not sure if it needed, maybe later)

        nl.init(client, this);
        client.addListener(nl);

        client.start();

        System.setProperty("java.net.preferIPv4Stack", "true");
    }

    // get IP from user input and connects
    public void connectToServer(String ip) {
        // Start a broadcast receiver
        try {
            broadcastClient = new BroadcastClient();
        } catch (IOException e) {
            e.printStackTrace();
            connectionScreen.errorMessage(2);
        }

        //Gets ip from broadcast
        serverIP = broadcastClient.getServerIP();

        System.out.print("\nIP is " + serverIP + "\n");

        if (serverIP != null && !serverIP.isEmpty()) {

            try {
                client.connect(5000, serverIP.trim(), 54555, 64555);

            } catch (IOException e) {
                e.printStackTrace();
                client.stop();
                connectionScreen.errorMessage(2);
            }
        }
    }

    // Register packets to a kryo
    private void register() {
        Kryo kryo = client.getKryo();
        // Register packets
        kryo.register(Packet.LoginRequest.class);
        kryo.register(Packet.LoginAnswer.class);
        kryo.register(Packet.GamePacket.class);
        kryo.register(Packet.SendGameData.class);
        kryo.register(Packet.ShutDownPacket.class);
        kryo.register(Packet.PauseRequest.class);
        kryo.register(Packet.ExitRequest.class);
        kryo.register(Packet.StandByOrder.class);
        kryo.register(Packet.SendDPadData.class);
        kryo.register(Packet.VibrateClient.class);
        kryo.register(Packet.PulseVibrateClient.class);
    }

    public void sendPacket(boolean send) {

        System.out.println("Sending device data: " + send);
        if(!networkThread.isAlive()){
            Gdx.app.log("SEND PACKETS", "THREAD WASN'T ALIVE");
            if(send) {
                Gdx.app.log("SEND PACKETS", "STARTING NEW THREAD");
                try {
                    networkThread.start();
                }
                catch (Exception e){
                    networkThread = newThread();
                    networkThread.start();
                }
            }
            else if (!send) {

            }
        }
        else{
            Gdx.app.log("SEND PACKETS", "THREAD IS ALIVE");
            if(!send) {
                Gdx.app.log("SEND PACKETS", "INTERUPTING THREAD");
                networkThread.interrupt();
            }
            else{
                Gdx.app.log("SEND PACKETS", "RESTARTING THREAD");
                //newThread(networkThread);
                networkThread = newThread();
                networkThread.start();

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

    public void sendDPadData(int x, int y, boolean bool) {
        Packet.SendDPadData dp = new Packet.SendDPadData();
        // TODO: Put dataX and dataY together to an array or string for efficiency
        dp.dataX = x;
        dp.dataY = y;
        dp.select = bool;
        client.sendTCP(dp);
        Gdx.app.log("in MPClient", "sent dPadInfo");
    }

    public void setConnectionScreen(ConnectionScreen cs){

        this.connectionScreen = cs;
    }

    public void errorHandler() {
          connectionScreen.errorMessage(1);
        closeThread = false;
    }
    public Thread newThread(){
        Thread nT = new Thread(new Runnable() {

            final int TICKS_PER_SECOND = 30;

            public void run() {

                Gdx.app.log("Thread", "NEW THREAD IS RUNNING");
                try {
                    while (!Thread.currentThread().isInterrupted() && closeThread) {
                        Thread.sleep(1000 /  TICKS_PER_SECOND );
                        Gdx.app.log("Thread", "DATA IS BEING SENT!!");
                        Packet.GamePacket packet = new Packet.GamePacket();

                        packet.message = controllerScreen.getDrive() + " " + controllerScreen.getReverse() + " " + controllerScreen.getRotation();
                        client.sendUDP(packet);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        return nT;
    }
}