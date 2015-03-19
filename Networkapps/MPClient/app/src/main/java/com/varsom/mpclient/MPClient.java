package com.varsom.mpclient;

import android.widget.EditText;
import android.widget.TextView;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;

import java.io.IOException;
import java.net.InetAddress;

/**
 *
 */
public class MPClient {
    public Client client;

    public MPClient(EditText ip, EditText o , TextView b) throws IOException {
        client = new Client();
        register();

        NetworkListener nl = new NetworkListener();

        // Initialise variables (not sure if it needed, maybe later)
        nl.init(client, o, b);

        client.addListener(nl);

        client.start();

        System.setProperty("java.net.preferIPv4Stack" , "true");

        try{
            b.setText("");
            b.append("Connected");

            System.out.println("FUNC: MPClinet, try");
            //check();

            //System.out.println(ip.getText().toString());

            //System.out.println("FUNC: MPClinet, try");

            client.connect(50000, ip.getText().toString(), 54555, 64555);
        }
        catch(IOException e){
            e.printStackTrace();
            client.stop();
            b.setText("");
            b.append("Connect doesn't work");
        }
    }

    public void sendMess(EditText b) {
        Packet.Message sendMessage = new Packet.Message();
        sendMessage.message = b.getText().toString();
        client.sendUDP(sendMessage);
        client.sendTCP(sendMessage);
    }

    // Looks for opened servers within a port. Currently not working.
    private void check(){
        InetAddress address = client.discoverHost(64555, 5000);
        System.out.println("HOST: " + address);
    }

    // Register packets to a kryo
    private void register() {
        Kryo kryo = client.getKryo();
        // Register packets
        kryo.register(Packet.LoginRequest.class);
        kryo.register(Packet.LoginAnswer.class);
        kryo.register(Packet.Message.class);
    }

}