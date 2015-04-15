package com.controller_app;


import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;

import java.io.IOException;

/**
 *
 */
public class MPClient {
    public Client client;

    public MPClient() throws IOException {
        client = new Client();
        register();

        NetworkListener nl = new NetworkListener();

        // Initialise variables (not sure if it needed, maybe later)
        nl.init(client);

        client.addListener(nl);

        client.start();

        System.setProperty("java.net.preferIPv4Stack", "true");

        //hostCheck();
        /*
        try{
            client.connect(50000, "192.168.1.1", 54555, 64555);
            //b.setText("Connected");
        }
        catch(IOException e){
            e.printStackTrace();
            client.stop();
            //b.setText("Connect doesn't work");
        }
        */

    }
    /*
    public void sendMess(EditText b) {
        Packet.Message sendMessage = new Packet.Message();
        sendMessage.message = b.getText().toString();
        client.sendUDP(sendMessage);
        client.sendTCP(sendMessage);
    }
    */

    // Looks for opened servers within a port. Currently not working.
    /*
    private void hostCheck(){
        try {
            InetAddress address = client.discoverHost(64555, 5000);
            System.out.println("HOST: " + address);
        }catch (KryoNetException e) {
            e.printStackTrace();
        }

    }
    */

    // Register packets to a kryo
    private void register() {
        Kryo kryo = client.getKryo();
        // Register packets
        kryo.register(Packet.LoginRequest.class);
        kryo.register(Packet.LoginAnswer.class);
        kryo.register(Packet.Message.class);
    }

}