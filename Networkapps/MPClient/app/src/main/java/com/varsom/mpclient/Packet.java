package com.varsom.mpclient;

/**
 * Created by christoffer on 2015-03-06.
 */
public class Packet {
    public static class LoginRequest { }
    public static class LoginAnswer { Boolean accepted = false; }
    public static class Message { String message; }
}
