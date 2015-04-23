package com.varsom.system.network;

/**
 *
 */
public class Packet {
    public static class LoginRequest { }
    public static class LoginAnswer { Boolean accepted = false; }
    public static class Message { String message; }
    public static class GamePacket { String message; }
    public static class SendGameData { Boolean send = false; }
    public static class ShutdownPacket { Boolean shutDown = false; }
}
