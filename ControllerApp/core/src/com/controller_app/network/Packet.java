package com.controller_app.network;

public class Packet {

    public static class LoginRequest {
        //playerName tells the server the name the user has chosen
        //if no name is chosen it will be "Player X"
        String playerName = "";}
    public static class LoginAnswer {boolean accepted = false, standby = false;}
    public static class GamePacket {String message;}
    public static class SendGameData {boolean send = false;}
    public static class ShutDownPacket {boolean shutDown = false;}
    public static class PauseRequest {boolean pause = false;}
    public static class ExitRequest {boolean exit = false;}
    public static class StandByOrder {boolean standby = false;}
    public static class SendDPadData{ int data; }

}
