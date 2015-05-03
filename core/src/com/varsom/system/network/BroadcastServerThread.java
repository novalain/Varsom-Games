package com.varsom.system.network;

/**
 * Created by christoffer on 2015-04-28.
 */

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class BroadcastServerThread extends Thread {

    private String ip;
    private String broadip;

    private long FIVE_SECONDS = 5000;
    public DatagramSocket socket = new DatagramSocket(4446);

    public BroadcastServerThread(String serverip) throws IOException {
        ip = serverip;
        // gets the broadcast subgroup
        broadip = ip.substring(0,ip.lastIndexOf(".")) +  ".255";

        socket = new DatagramSocket(4447);
    }

    public void run() {

        byte[] buf = ip.getBytes();;

        for (int i = 0; i < 50; i++) {
            try {
                InetAddress broadcastaddress = InetAddress.getByName(broadip);
                DatagramPacket packet = new DatagramPacket(buf, buf.length, broadcastaddress, 4446);
                socket.setBroadcast(true);
                socket.send(packet);

                try {
                    sleep((long) (Math.random() * FIVE_SECONDS));
                } catch (InterruptedException e) { }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        socket.close();
    }

}