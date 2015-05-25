package com.varsom.system.games.car_game.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;
import java.util.Map;

public class SoundHandler {

    private static float soundVolume = 1.0f;
    private static float musicVolume = 1.0f;
    private static boolean muteSounds = false;
    private static boolean muteMusic = false;

    // Sound effects
    //TODO: Add sounds to a hashmap instead of lots of public static finals.

    private static Map<Sound_ID, Sound> soundMap = new HashMap<Sound_ID, Sound>();
    private static Map<Music_ID, Music> musicMap = new HashMap<Music_ID, Music>();

    public static void load() {

        // Add all sounds
        soundMap.put(Sound_ID.WIN_1, Gdx.audio.newSound(Gdx.files.internal("car_game_assets/sounds/sfx/HERCULES.mp3")));
        soundMap.put(Sound_ID.CRASH_1, Gdx.audio.newSound(Gdx.files.internal("car_game_assets/sounds/sfx/jab.mp3")));
        soundMap.put(Sound_ID.CRAZY_2, Gdx.audio.newSound(Gdx.files.internal("car_game_assets/sounds/sfx/ricochet.mp3")));
        soundMap.put(Sound_ID.MONKEY, Gdx.audio.newSound(Gdx.files.internal("car_game_assets/sounds/sfx/chimpanzee.mp3")));

        soundMap.put(Sound_ID.HONK_1, Gdx.audio.newSound(Gdx.files.internal("car_game_assets/sounds/sfx/honk_1.wav")));
        soundMap.put(Sound_ID.HONK_2, Gdx.audio.newSound(Gdx.files.internal("car_game_assets/sounds/sfx/honk_2.wav")));

        // soundMap.put(Sound_ID.POP, Gdx.audio.newSound(Gdx.files.internal("car_game_assets/sounds/sfx/pop.wav")));
        soundMap.put(Sound_ID.SWOOSH, Gdx.audio.newSound(Gdx.files.internal("car_game_assets/sounds/sfx/swoosh.wav")));
        soundMap.put(Sound_ID.START_LIGHT, Gdx.audio.newSound(Gdx.files.internal("car_game_assets/sounds/sfx/start_light.mp3")));
        soundMap.put(Sound_ID.START_WHISTLE, Gdx.audio.newSound(Gdx.files.internal("car_game_assets/sounds/sfx/start_whistle.wav")));

        soundMap.put(Sound_ID.WACKY_1, Gdx.audio.newSound(Gdx.files.internal("car_game_assets/sounds/sfx/wacky_1.wav")));
        soundMap.put(Sound_ID.WACKY_2, Gdx.audio.newSound(Gdx.files.internal("car_game_assets/sounds/sfx/wacky_2.wav")));
        soundMap.put(Sound_ID.WACKY_3, Gdx.audio.newSound(Gdx.files.internal("car_game_assets/sounds/sfx/wacky_3.wav")));

        // Add all music
        musicMap.put(Music_ID.MENU, Gdx.audio.newMusic(Gdx.files.internal("car_game_assets/sounds/music/Jaunty_Gumption.mp3")));
        musicMap.get(Music_ID.MENU).setLooping(true);
    }

    public static void play(Sound_ID s) {

        if (!muteSounds) {
            soundMap.get(s).play();
        }
    }

    public static void play(Music_ID m) {

        if (!muteMusic) {
            musicMap.get(m).play();
        }
    }

    public static void setMuteSounds(boolean b) {
        muteSounds = b;
    }

    public static void setMuteMusic(boolean b) {
        muteMusic = b;
    }

    public static void stopAll() {
        // The "smart" loop does apparently not work at the moment.
        for (Map.Entry<Sound_ID, Sound> entry : soundMap.entrySet()) {
            entry.getValue().stop();
        }

        // Old temporary manual fix
        soundMap.get(Sound_ID.WIN_1).stop();
        soundMap.get(Sound_ID.CRASH_1).stop();
        soundMap.get(Sound_ID.CRAZY_2).stop();
        soundMap.get(Sound_ID.MONKEY).stop();
        soundMap.get(Sound_ID.HONK_1).stop();
        soundMap.get(Sound_ID.HONK_2).stop();
        soundMap.get(Sound_ID.SWOOSH).stop();
        soundMap.get(Sound_ID.START_LIGHT).stop();
        soundMap.get(Sound_ID.START_WHISTLE).stop();
        soundMap.get(Sound_ID.WACKY_1).stop();
        soundMap.get(Sound_ID.WACKY_2).stop();
        soundMap.get(Sound_ID.WACKY_3).stop();

        musicMap.get(Music_ID.MENU).stop();
    }
}