package com.varsom.mpclient;

import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

/**
 * Created by christoffer on 2015-03-06.
 */
public class MPClient {
    public Client client;
    public Scanner scanner;



    public MPClient(EditText ip, EditText o , TextView b) throws IOException {
        client = new Client();
        register();

        NetworkListener nl = new NetworkListener();

        nl.init(client, ip, o, b);

        client.addListener(nl);


        client.start();

        try{
            client.connect(50000,ip.getText().toString(), 54555);
        }
        catch(IOException e){
            e.printStackTrace();
            b.setText("");
            b.append("Connect doesn't work");
        }




    }

    /*public void stop() {
        client.stop();
    }
    public void check(){
        InetAddress address = client.discoverHost(54555, 50000);
        System.out.println(address);


    }*/

    private void register() {
        Kryo kryo = client.getKryo();
        // Register packets
        kryo.register(Packet.LoginRequest.class);
        kryo.register(Packet.LoginAnswer.class);
        kryo.register(Packet.Message.class);
    }

}