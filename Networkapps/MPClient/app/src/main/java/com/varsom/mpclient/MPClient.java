package com.varsom.mpclient;

import android.widget.EditText;
import android.widget.TextView;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Created by christoffer on 2015-03-06.
 */
public class MPClient {
    public Client client;
    //public Scanner scanner;

    public MPClient(EditText ip, EditText o , TextView b) throws IOException {
        client = new Client();
        register();

        NetworkListener nl = new NetworkListener();

        nl.init(client, ip, o, b);

        client.addListener(nl);

        client.start();

        try{
            b.setText("");
            b.append("Connected");
            //InetAddress add = check();
            //System.out.println(ip.getText().toString());
            System.out.println("FUNC: MPClinet, try");
            client.connect(50000,ip.getText().toString(), 54555, 64555);

            //client.connect(50000,"130.236.86.116", 54555, 64555);
            //client.connect(50000, add, 54555, 54666);

            // 130.236.86.116
        }
        catch(IOException e){
            e.printStackTrace();
            client.stop();
            b.setText("");
            b.append("Connect doesn't work");
        }

    }

    /*public void stop() {
        client.stop();
    }*/

    public InetAddress check(){
        InetAddress address = client.discoverHost(54666, 50000);
        System.out.println("HOST: " + address);

        return address;

    }

    private void register() {
        Kryo kryo = client.getKryo();
        // Register packets
        kryo.register(Packet.LoginRequest.class);
        kryo.register(Packet.LoginAnswer.class);
        kryo.register(Packet.Message.class);
    }

}