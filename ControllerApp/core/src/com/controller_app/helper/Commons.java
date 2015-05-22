package com.controller_app.helper;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class Commons{

    // A nice singleton for common constants. They are final -> unchangeable after initialization.

    // Screen dimensions
    public static final int WORLD_WIDTH = 1920;
    public static final int WORLD_HEIGHT = 1080;

    // Screen ID:s
    public static final int CONNECTION_SCREEN = 0;
    public static final int VARSOM_SYSTEM_SCREEN = 1;
    public final static int SETTINGS_SCREEN = 2;
    public static final int CAR_GAME_SCREEN = 3;
    public static final int STANDBY_SCREEN = 4;

    // Error message
    public static final int BAD_CONNECTION = 1;
    public static final int NO_SERVER_FOUND = 2;
    public static final int DO_YOU_WANT_TO_EXIT = 3;

    // Vibration time for clicks (in millis)
    public static final int VIBRATION_TIME = 30;

    //KRAZY RACY SPECIFIC
    public final static Color KRAZY_GREEN = new Color(152.0f/255.0f, 205.0f/255.0f, 121.0f/255.0f, 1);
    public final static Color KRAZY_BLUE = new Color(19.0f/255.0f, 151.0f/255.0f, 212.0f/255.0f, 1);

    public static BitmapFont getFont(int size, FileHandle fontFile){
        return getFont(size, fontFile, Color.WHITE);
    }

    public static BitmapFont getFont(int size, FileHandle fontFile, Color color){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.color = color;

        BitmapFont font = generator.generateFont(parameter);
        generator.dispose(); // don't forget to dispose to avoid memory leaks!
        return font;
    }

    public static BitmapFont getFont(int size, FileHandle fontFile, Color fontColor,float borderWidth, Color borderColor){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.color = fontColor;
        parameter.borderColor = borderColor;
        parameter.borderWidth = borderWidth;

        BitmapFont font = generator.generateFont(parameter);
        generator.dispose(); // don't forget to dispose to avoid memory leaks!
        return font;
    }

}