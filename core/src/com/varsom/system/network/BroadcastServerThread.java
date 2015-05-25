package com.varsom.system.network;

/**
 * Created by christoffer on 2015-04-28.
 */

import com.badlogic.gdx.Gdx;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

public class BroadcastServerThread extends Thread {

    private String ip;
    private String broadip;

    private long FIVE_SECONDS = 5000;
    private DatagramSocket socket;

    public BroadcastServerThread() throws IOException {

        ip = getServerIP();
        broadip = getBroadcastIP();
        try {
            socket = new DatagramSocket(4447);
        } catch( Exception e){
            System.out.println("Socket port is occupied. An other version of the VarsomSystem may already be running! Shutting down");
        }
    }

    public void run() {

        byte[] buf = ip.getBytes();

        while(true) {
        //for (int i = 0; i < 50; i++) {
            try {
                InetAddress broadcastaddress = InetAddress.getByName(broadip);
                DatagramPacket packet = new DatagramPacket(buf, buf.length, broadcastaddress, 4446);
                System.out.println(packet.getSocketAddress().toString());
                socket.setBroadcast(true);
                socket.send(packet);

                try {
                    sleep((long) (Math.random() * FIVE_SECONDS));
                } catch (InterruptedException e) { }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Never reached -> while(true)
        //socket.close();
    }

    private String getBroadcastIP() throws SocketException {

        Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
        while (en.hasMoreElements()) {
            NetworkInterface ni = en.nextElement();

            List<InterfaceAddress> list = ni.getInterfaceAddresses();
            Iterator<InterfaceAddress> it = list.iterator();

            while (it.hasNext()) {
                InterfaceAddress ia = it.next();
                //System.out.println(ia.getAddress());
                if(ia.getBroadcast() != null && !ia.getAddress().isLoopbackAddress()) {
                    broadip = ia.getBroadcast().toString();
                    broadip = broadip.replace("/", "");
                    //System.out.println(" Broadcast = " + broadip);
                }
            }
        }
        return broadip;
    }

    private String getServerIP(){

        // The following code loops through the available network interfaces
        // Keep in mind, there can be multiple interfaces per device, for example
        // one per NIC, one per active wireless and the loopback
        // In this case we only care about IPv4 address ( x.x.x.x format )
        List<String> addresses = new ArrayList<String>();
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            for(NetworkInterface ni : Collections.list(interfaces)){
                for(InetAddress address : Collections.list(ni.getInetAddresses()))
                {
                    if(address instanceof Inet4Address && !address.isLoopbackAddress()){ //&& !address.isSiteLocalAddress()){
                        addresses.add(address.getHostAddress());
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }

        // Print the contents of our array to a string.  Yeah, should have used StringBuilder
        String ipAddress = new String("");
        for(String str:addresses)
        {
            ipAddress = ipAddress + str + "\n";
        }
        String serverIPAddress = ipAddress;//addresses.get(1) + "\n" +addresses.get(0);
        Gdx.app.log("NETWORK", "NETWORK: IP-address is " + ipAddress);

        if(ipAddress.equals(""))
        {
            System.out.println("ip is empty");
        }

        // Removes unneeded ip addresses
        if(serverIPAddress.contains(" ")) {
            serverIPAddress = serverIPAddress.substring(0, serverIPAddress.indexOf(" "));
        }
        System.out.println("IP is " + serverIPAddress);

        return serverIPAddress;

    }

}