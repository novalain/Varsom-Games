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
        nl.init(client, ip, o, b);

        client.addListener(nl);

        client.start();

        System.setProperty("java.net.preferIPv4Stack" , "true");

        try{
            b.setText("");
            b.append("Connected");
<<<<<<< HEAD
            System.out.println("FUNC: MPClinet, try");
            check();
            //InetAddress address = client.discoverHost(54555, 64555);
            //System.out.println("HOST: " + address);

            //System.out.println(ip.getText().toString());

=======
            //check();
            InetAddress address = client.discoverHost(62555, 50000);
            System.out.println("HOST: " + address);

            //System.out.println("FUNC: MPClinet, try");
>>>>>>> origin/network
            client.connect(50000,ip.getText().toString(), 54555, 64555);
        }
        catch(IOException e){
            e.printStackTrace();
            client.stop();
            b.setText("");
            b.append("Connect doesn't work");
        }
    }


    // Looks for opened servers within a port. Currently not working.
    public void check(){
<<<<<<< HEAD
        InetAddress address = client.discoverHost(64555, 5000);
=======
        InetAddress address = client.discoverHost(65555, 50000);
>>>>>>> origin/network
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