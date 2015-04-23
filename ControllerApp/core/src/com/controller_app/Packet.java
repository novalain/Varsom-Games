package com.controller_app;

/**
 *
 */
public class Packet {
    public static class LoginRequest { }
    public static class LoginAnswer { Boolean accepted = false; }
    public static class Message { String  message; }
    public static class GamePacket { String message; }
    public static class SendGameData { Boolean send = false; }
    public static class ShutDownPacket { Boolean shutDown = false; }
}
