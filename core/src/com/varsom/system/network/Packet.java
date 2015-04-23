package com.varsom.system.network;

/**
 *
 */
public class Packet {
    public static class LoginRequest { }
    public static class LoginAnswer { boolean accepted = false; }
    public static class Message { String message; }
    public static class PauseRequest { boolean pause = false; }
    public static class ExitRequest { boolean exit = false; }
    public static class GamePacket { String message; }
    public static class SendGameData { Boolean send = false; }
    public static class ShutdownPacket { Boolean shutDown = false; }
}
