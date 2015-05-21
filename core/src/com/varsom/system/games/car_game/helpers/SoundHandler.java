package com.varsom.system.games.car_game.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundHandler {
    // Sound effects

    //TODO: Add sounds to a hashmap instead of lots of public static finals.

        // Temporary win sound
    public static final Sound WIN_1 = Gdx.audio.newSound(Gdx.files.internal("car_game_assets/sounds/sfx/HERCULES.mp3"));

    public static final Sound CRASH_1 = Gdx.audio.newSound(Gdx.files.internal("car_game_assets/sounds/sfx/jab.mp3"));
    public static final Sound CRAZY_2 = Gdx.audio.newSound(Gdx.files.internal("car_game_assets/sounds/sfx/ricochet.mp3"));

    public static final Sound MONKEY = Gdx.audio.newSound(Gdx.files.internal("car_game_assets/sounds/sfx/chimpanzee.mp3"));
    public static final Sound HONK_1 = Gdx.audio.newSound(Gdx.files.internal("car_game_assets/sounds/sfx/honk_1.wav"));
    public static final Sound HONK_2 = Gdx.audio.newSound(Gdx.files.internal("car_game_assets/sounds/sfx/honk_2.wav"));

    public static final Sound SWOOSH = Gdx.audio.newSound(Gdx.files.internal("car_game_assets/sounds/sfx/swoosh.wav"));
    public static final Sound START_LIGHT = Gdx.audio.newSound(Gdx.files.internal("car_game_assets/sounds/sfx/start_light.mp3"));
    public static final Sound START_WHISTLE = Gdx.audio.newSound(Gdx.files.internal("car_game_assets/sounds/sfx/start_whistle.wav"));

    public static final Sound WACKY_1 = Gdx.audio.newSound(Gdx.files.internal("car_game_assets/sounds/sfx/wacky_1.wav"));
    public static final Sound WACKY_2 = Gdx.audio.newSound(Gdx.files.internal("car_game_assets/sounds/sfx/wacky_2.wav"));
    public static final Sound WACKY_3 = Gdx.audio.newSound(Gdx.files.internal("car_game_assets/sounds/sfx/wacky_3.wav"));


    // Music
    public static final Music MUSIC = Gdx.audio.newMusic(Gdx.files.internal("car_game_assets/sounds/music/Jaunty_Gumption.mp3"));

    public static void stopAll(){
        WIN_1.stop();

        CRASH_1.stop();
        CRAZY_2.stop();

        MONKEY.stop();
        HONK_1.stop();
        HONK_2.stop();

        SWOOSH.stop();
        START_LIGHT.stop();
        START_WHISTLE.stop();

        WACKY_1.stop();
        WACKY_2.stop();
        WACKY_3.stop();

        MUSIC.stop();

    }

}