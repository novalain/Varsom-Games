package com.controller_app.network;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class NetworkListener extends Listener {

    private Client client;
    private MPClient mpClient;


    private boolean start;
    public static boolean connected = false;
    public static boolean standby = false;
    public static int paddir;
    public static int controller;

    // If you want to send a object, you need to send it with this client variable
    public void init(Client client, MPClient mpClient) {
        this.client = client;
        this.mpClient = mpClient;


    }

    public void connected(Connection c) {
        Packet.LoginRequest login = new Packet.LoginRequest();

        if(c.toString().equals("Connection " + c.getID()) || c.toString().equals("")){
            //No name was chosen, change to "Player X"
            login.playerName = "Player " + c.getID();
            c.setName(login.playerName);
        }
        else
            login.playerName = c.toString();

        client.sendTCP(login);
        System.out.println("You have connected.");
        //connected = true;

    }

    public void disconnected(Connection c) {
        System.out.println("You have disconnected.");
        mpClient.errorHandler();
        connected = false;
    }

    public void received(Connection c, Object o) {
        // checks for login answers from server
        String message = "Recieved a message of type: ";
        if (o instanceof Packet.LoginAnswer) {
            connected = ((Packet.LoginAnswer) o).accepted;
            standby = ((Packet.LoginAnswer) o).standby;

            if (connected) {
                String mess = o.toString();
                System.out.println("in NetworkListener: answer = true");
                //System.out.println("Message: " + mess);
            } else {
                c.close();
            }

        }

        else if (o instanceof Packet.SendGameData) {
            message += "SendGameData";
            Gdx.app.log("NETWORK", message);
            start = ((Packet.SendGameData) o).send;

            mpClient.sendPacket(start);
            /*
            if(start) {
                new Thread() {
                    public void run() {
                        mpClient.sendPacket(start);
                    }
                }.start();
            }
            */
        }

        else if (o instanceof Packet.StandByOrder) {
            System.out.println("in NetworkListener: standby");

            standby = ((Packet.StandByOrder) o).standby;

        }

        else if (o instanceof Packet.SendDPadData) {
            System.out.println("in NetworkListener: standby");

            paddir = ((Packet.SendDPadData) o).dataX;
            boolean padSelected = ((Packet.SendDPadData) o).select;
            System.out.println("Paddir is " + paddir + " and select is " + padSelected );

        }

        else if (o instanceof Packet.VibrateClient) {
            System.out.println("in NetworkListener: vibrate");
            Gdx.input.vibrate(((Packet.VibrateClient) o).vibTime);
        }

        else if (o instanceof Packet.PulseVibrateClient) {
            System.out.println("in NetworkListener: pulse vibrate");
            ArrayList<Long> temp = new ArrayList<Long>();
            StringTokenizer st = new StringTokenizer(((Packet.PulseVibrateClient) o).pattern, " ");
            while (st.hasMoreTokens()) {
                temp.add(Long.parseLong(st.nextToken()));
            }

            long[] pattern = new long[ temp.size() ];
            for(int i =0 ; i < pattern.length; i++) {
                pattern[i] = temp.get(i);
            }
            System.out.println("VibrationPattern: "+pattern.toString());
            Gdx.input.vibrate(pattern,((Packet.PulseVibrateClient) o).repeat);
        }

        else if (o instanceof Packet.ChangeController) {

            mpClient.setActiveScreenIndex(((Packet.ChangeController) o).controller);
            mpClient.setChangeController(true);

        }
    }
}
