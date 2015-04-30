package com.varsom.system;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Server;
import com.varsom.system.abstract_gameobjects.VarsomGame;
import com.varsom.system.games.car_game.com.varsomgames.cargame.CarGame;
import com.varsom.system.games.other_game.OtherGame;
import com.varsom.system.network.MPServer;
import com.varsom.system.network.MulticastServerThread;
import com.varsom.system.screens.VarsomSplash;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

public class VarsomSystem extends /*ApplicationAdapter*/Game {
    public static final String TITLE= "Varsom-System";

    //an array that stores the games IDs

    public static final int SIZE = 2;
    public static int[] games = new int[SIZE];

    static protected MPServer mpServer;
    static protected Server server;

    private String serverIPAddress;

    private VarsomGame activeGame;

    public VarsomSystem(){

    }

    @Override
	public void create () {
        activeGame = null;
        startServer();
        setScreen(new VarsomSplash(this));
        //setScreen(new VarsomMenu());

        //TODO ful-lösning, vi borde inte behöva berätta vilka spel eller hur många utan systemet ska känna av det
        //Handle games
        //For CarGame
        games[0] = CarGame.ID;

        //For OtherGame
        games[1] = OtherGame.ID;
    }

    @Override
    public void dispose() {
        mpServer.stop();
        super.dispose();
    }

    private void startServer(){
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
        serverIPAddress = ipAddress;//addresses.get(1) + "\n" +addresses.get(0);
        Gdx.app.log("NETWORK", "NETWORK: IP-address is " + ipAddress);
        try {
            mpServer = new MPServer(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Start a multicastsender
        try {
            new MulticastServerThread(serverIPAddress).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getServerIP(){
        return serverIPAddress;
    }

    public MPServer getMPServer(){
        return mpServer;
    }
    public Server getServer(){
        return mpServer.getServer();
    }

    public VarsomGame getActiveGame(){
        return activeGame;
    }

    public void setActiveGame(VarsomGame vG){

        this.activeGame = vG;

    }
}