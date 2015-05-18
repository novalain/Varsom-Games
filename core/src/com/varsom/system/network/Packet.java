package com.varsom.system.network;

/**
 *
 */
public class Packet {
    public static class LoginRequest {
        String playerName = "";
    }

    public static class LoginAnswer {
        boolean accepted = false, standby = false;
    }

    public static class GamePacket {
        String message;
    }

    public static class SendGameData {
        boolean send = false;
    }

    public static class ShutDownPacket {
        boolean shutDown = false;
    }

    public static class PauseRequest {
        boolean pause = false;
    }

    public static class ExitRequest {
        boolean exit = false;
    }

    public static class StandByOrder {
        boolean standby = false;
    }

    public static class SendDPadData {
        int dataX, dataY;
        boolean select;
    }

    public static class VibrateClient {
        int vibTime;
    }

    public static class PulseVibrateClient {
        String pattern;
        int repeat;
    }

    public static class ChangeController {
        int controller;
    }

    public static class NameUpdate {
        String name;
    }
}
