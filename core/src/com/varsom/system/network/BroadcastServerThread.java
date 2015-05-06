package com.varsom.system.network;

/**
 * Created by christoffer on 2015-04-28.
 */

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

public class BroadcastServerThread extends Thread {

    private String ip;
    private String broadip;

    private long FIVE_SECONDS = 5000;
    public DatagramSocket socket = new DatagramSocket(4446);

    public BroadcastServerThread(String serverip) throws IOException {

        if(serverip.equals(""))
        {
            System.out.println("ip is empty");
        }

        ip = serverip;

        // Removes unneeded ip addresses
        if(ip.contains(" ")) {
            ip = ip.substring(0, ip.indexOf(" "));
        }
        System.out.println("IP is " + ip);

        broadip = getBroadcastIP();

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

    private String getBroadcastIP() throws SocketException {

        Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
        while (en.hasMoreElements()) {
            NetworkInterface ni = en.nextElement();

            List<InterfaceAddress> list = ni.getInterfaceAddresses();
            Iterator<InterfaceAddress> it = list.iterator();

            while (it.hasNext()) {
                InterfaceAddress ia = it.next();
                if(ia.getBroadcast() != null) {
                    broadip = ia.getBroadcast().toString();
                    broadip = broadip.replace("/", "");
                    //System.out.println(" Broadcast = " + broadip);
                }
            }
        }
        return broadip;
    }

}