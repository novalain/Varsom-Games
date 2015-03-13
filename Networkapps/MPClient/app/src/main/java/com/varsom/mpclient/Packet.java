package com.varsom.mpclient;

/**
 *
 */
public class Packet {
    public static class LoginRequest { }
    public static class LoginAnswer { Boolean accepted = false; }
    public static class Message { String message; }
}
