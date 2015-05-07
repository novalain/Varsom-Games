package com.varsom.system.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import com.varsom.system.VarsomSystem;

import java.io.IOException;

/**
 *
 */
public class MPServer {
    private Server server;
    int TCP = 54555, UDP = 64555;
    private VarsomSystem varsomSystem;
    static private String IP;

    //Shows if new clients are permitted to join at the moment
    public static boolean joinable = true;

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
            System.out.println("Sent package");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void gameRunning(boolean b, int carID) {
        Packet.SendGameData sGD = new Packet.SendGameData();

        sGD.send = b;

        try {
            // Sends the message to client with connectionID cID
            server.sendToTCP(carID,sGD);
            System.out.println("Sent package");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void registerPackets() {
        Kryo kryo = server.getKryo();
        // Register packets in the same order as in Packet.java
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
        kryo.register(Packet.ChangeController.class);
    }

    public void stop() {
        //TODO Tell clients that server is shutting down
        server.close();
    }

    public Server getServer(){
        return server;
    }

    public void setJoinable(boolean b){
        joinable = b;

        //if the server is joinable tell all clients
        //if it isn't there's no need to tell already playing clients
        if(joinable){
            Packet.StandByOrder sendState = new Packet.StandByOrder();
            sendState.standby = true;

            //if the server doesn't permit new clients to join tell the client
            if(!MPServer.joinable) {
                //tell the client to stand by
                sendState.standby = true;
                System.out.println("StandbyOrder sent");
            }
            else {
                sendState.standby = false;
            }
            // Send the packet back with the variable login.accepted, that is now true
            varsomSystem.getServer().sendToAllTCP(sendState);
        }
    }

    public void vibrateClient(int time, int connectionID) {
        Packet.VibrateClient vibPack = new Packet.VibrateClient();

        vibPack.vibTime = time;

        try {
            // Sends the message to client with connectionID cID
            server.sendToTCP(connectionID,vibPack);
            System.out.println("Sent package");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void PulseVibrateClient(String pattern, int repeat, int connectionID) {
        Packet.PulseVibrateClient vibPack = new Packet.PulseVibrateClient();

        vibPack.pattern = pattern;
        vibPack.repeat = repeat;

        try {
            // Sends the message to client with connectionID cID
            server.sendToTCP(connectionID,vibPack);
            System.out.println("Sent package");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public boolean getJoinable(boolean b){
        return joinable;
    }

    public void changeScreen(int x) {
        Packet.ChangeController change = new Packet.ChangeController();
        change.controller = x;
        getServer().sendToAllTCP(change);
    }
}
