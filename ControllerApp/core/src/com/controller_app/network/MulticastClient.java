package com.controller_app.network;

/**
 * Created by christoffer on 2015-04-28.
 */

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastClient {

    public String IP;

    public MulticastClient() throws IOException {

        MulticastSocket socket = new MulticastSocket(4447);
        InetAddress address = InetAddress.getByName("230.0.0.1");
        socket.joinGroup(address);

        DatagramPacket packet = null;

        // get a few quotes
        while (packet == null) {
            byte[] buf = new byte[256];
            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);
            IP = new String(packet.getData(), 0, packet.getLength());
        }

        socket.leaveGroup(address);
        socket.close();
    }

    public String getSertverIP() { return IP; }

}
