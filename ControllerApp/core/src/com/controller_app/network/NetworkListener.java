package com.controller_app.network;


import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

/**
 *
 */
public class NetworkListener extends Listener {

    private Client client;
    private MPClient mpClient;

    private boolean start;
    public static boolean answer = false;
    public static boolean standby = false;


    // If you want to send a object, you need to send it with this client variable
    public void init(Client client, MPClient mpClient) {
        this.client = client;
        this.mpClient = mpClient;
    }

    public void connected(Connection c) {
        client.sendTCP(new Packet.LoginRequest());
        System.out.println("You have connected.");

    }

    public void disconnected(Connection c) {
        System.out.println("You have disconnected.");
    }

    public void received(Connection c, Object o) {
        // checks for login answers from server
        if (o instanceof Packet.LoginAnswer) {
            answer = ((Packet.LoginAnswer) o).accepted;
            standby = ((Packet.LoginAnswer) o).standby;

            if (answer) {
                String mess = o.toString();
                System.out.println("in NetworkListener: answer = true");
                //System.out.println("Message: " + mess);
            } else {
                c.close();
            }

        }
        if (o instanceof Packet.SendGameData) {

            start = ((Packet.SendGameData) o).send;
            new Thread() {
                public void run() {
                    mpClient.sendPacket(start);
                }
            }.start();

        }
        if (o instanceof Packet.StandbyOrder) {
            System.out.println("in NetworkListener: standby");

            standby = ((Packet.StandbyOrder) o).standby;

        }

    }

}
