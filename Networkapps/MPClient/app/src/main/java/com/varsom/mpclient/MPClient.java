package com.varsom.mpclient;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by christoffer on 2015-03-06.
 */
public class MPClient {
    public Client client;
    public Scanner scanner;

    public MPClient() throws IOException {
        scanner = new Scanner(System.in);
        client = new Client();
        register();

        client.addListener(new NetworkListener());
        client.connect(5000, scanner.nextLine(), 54555);
        client.start();

    }

    public void stop() {
        client.stop();
    }

    private void register() {
        Kryo kryo = client.getKryo();
        // Register packets
        kryo.register(Packet.LoginRequest.class);
        kryo.register(Packet.LoginAnswer.class);
        kryo.register(Packet.Message.class);
    }

}