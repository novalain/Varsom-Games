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

    private long FIVE_SECONDS = 5000;
    public DatagramSocket socket = new DatagramSocket(4446);

    public BroadcastServerThread(String serverip) throws IOException {
        ip = serverip;

        socket = new DatagramSocket(4447);
    }

    public void run() {

        byte[] buf = new byte[256];
        buf = ip.getBytes();

        for (int i = 0; i < 50; i++) {
            try {
                // send it
                //InetAddress group = InetAddress.getByName("224.0.0.0");
                InetAddress group = InetAddress.getByName("192.168.1.255");
                DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 4446);
                socket.setBroadcast(true);
                socket.send(packet);

                // sleep for a while
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