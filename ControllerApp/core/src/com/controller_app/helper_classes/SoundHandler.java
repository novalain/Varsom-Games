package com.controller_app.helper_classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;
import java.util.Map;

public class SoundHandler {

    private static float soundVolume = 1.0f;
    private static boolean muteSounds = false;

    private static Map<Sound_ID, Sound> soundMap = new HashMap<Sound_ID, Sound>();

    public static void load() {
        soundMap.put(Sound_ID.POP, Gdx.audio.newSound(Gdx.files.internal("sounds/pop.mp3")));
        soundMap.put(Sound_ID.HONK_1, Gdx.audio.newSound(Gdx.files.internal("sounds/honk_1.wav")));
        soundMap.put(Sound_ID.HONK_2, Gdx.audio.newSound(Gdx.files.internal("sounds/honk_2.wav")));
        soundMap.put(Sound_ID.MONKEY, Gdx.audio.newSound(Gdx.files.internal("sounds/monkey.mp3")));
        soundMap.put(Sound_ID.DIXIE, Gdx.audio.newSound(Gdx.files.internal("sounds/dixie.mp3")));
    }

    public static void play(Sound_ID s) {
        if (!muteSounds) {
            soundMap.get(s).play();
        }
    }

    public static void setMuteSounds(boolean b) {
        muteSounds = b;
        stopAll();
    }

    public static void stopAll() {
        for (Map.Entry<Sound_ID, Sound> entry : soundMap.entrySet()) {
            entry.getValue().stop();
        }
    }
}