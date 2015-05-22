package com.varsom.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.varsom.system.games.car_game.helpers.AssetLoader;
import com.varsom.system.games.car_game.helpers.KrazyRazyCommons;

public class Commons {

    // A nice singleton

    public final static int WORLD_WIDTH = 1920;
    public final static int WORLD_HEIGHT = 1080;

    // Screen ID:s
    public static final int CONNECTION_SCREEN = 0;
    public static final int NAVIGATION_SCREEN = 1;
    public static final int SETTINGS_SCREEN = 2;
    public static final int CONTROLLER_SCREEN = 3;

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
