package com.controller_app.helper;

public interface Commons{

    // A nice singleton for common constants. They are final -> unchangeable after initialization.

    // Screen dimensions
    public static final int WORLD_WIDTH = 1920;
    public static final int WORLD_HEIGHT = 1080;

    // Screen ID:s
    public static final int CONNECTION_SCREEN = 0;
    public static final int NAVIGATION_SCREEN = 1;
    public final static int SETTINGS_SCREEN = 2;
    public static final int CONTROLLER_SCREEN = 3;

    // Error message
    public static final int BAD_CONNECTION = 1;
    public static final int NO_SERVER_FOUND = 2;
    public static final int DO_YOU_WANT_TO_EXIT = 3;

}