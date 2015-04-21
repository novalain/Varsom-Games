package com.varsom.system.games.car_game.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


/**
 * Created by Alice on 2015-03-12.
 * Handles all textures such as car texture
 * textureRegions are several
 */
public class AssetLoader {
    //car
    public static Texture carTexture,carTexture2, tireTrackTexture,pathCircleTexture;
    //obstacles
    public static Texture tireObstacleTexture,wallTexture;
    //backgrounds
    public static Texture bgTexture, testTrackTexture, testTrackMask, track2Texture, track2Mask;

    public static TextureRegion bg;
    public static TextureRegion car,tracks1,tracks2,tracks3;
   // public static Animation carAnimation;
    public static TextureRegion tex1, tex2, tex3;

    public static FileHandle particleFile, particleImg;


    public static void load() {
        pathCircleTexture = new Texture(Gdx.files.internal("car_game_assets/img/colorwheel.png"));
        // loading texture for car
        carTexture = new Texture(Gdx.files.internal("car_game_assets/img/ambulance_animation/1c.png"));
        carTexture2 = new Texture(Gdx.files.internal("car_game_assets/img/car2.png"));
        //carTexture = new Texture(Gdx.files.internal("car_game_assets/img/car.png"));
        //carTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        tireTrackTexture = new Texture(Gdx.files.internal("car_game_assets/img/tire-tracks.jpg"));
        tireTrackTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        tireObstacleTexture = new Texture("car_game_assets/img/tire.png");
        wallTexture = new Texture("car_game_assets/img/blackbox.jpg");

        // Guessing bg is background
        bgTexture = new Texture(Gdx.files.internal("car_game_assets/img/temp_background.png"));
        bgTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        testTrackTexture = new Texture("car_game_assets/img/tracks/temptrack.png");
        testTrackMask = new Texture("car_game_assets/img/tracks/trackmask.png");

        track2Texture = new Texture("car_game_assets/img/tracks/track2.png");
        track2Mask = new Texture("car_game_assets/img/tracks/track2mask.png");

        bg = new TextureRegion(bgTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        bg.flip(false, true); // Flip the sprite vertically to fit coordinate system Y-down

        particleFile = Gdx.files.internal("car_game_assets/effects/smoke.p");
        particleImg = Gdx.files.internal("car_game_assets/img");

        // For animation on ambulance but should probably use an atlas
        tex1 = new TextureRegion(new Texture("car_game_assets/img/ambulance_animation/1c.png"));
        tex2 = new TextureRegion(new Texture("car_game_assets/img/ambulance_animation/2c.png"));
        tex3 = new TextureRegion(new Texture("car_game_assets/img/ambulance_animation/3c.png"));

        // Texture size of tire-tracks.jpg is 112*289 px
       /* tracks1 = new TextureRegion(tireTrackTexture, 0,      0, 112, 96f);
        tracks2 = new TextureRegion(tireTrackTexture, 0, 94f, 112,192);
        tracks3 = new TextureRegion(tireTrackTexture, 0,193, 112,  289f);
        tracks1.flip(false, true);

  //    Animate an array of textureRegions to make the sprite object seem to move
        TextureRegion[] tireTracks = { tracks1,tracks2,tracks3};
        wheelAnimation = new Animation(0.5f, tireTracks);
        wheelAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);*/

        // Texture size of car.png is 50*74 px
        //car = new TextureRegion(carTexture, 0, 0, 50, 74);
        //car.flip(false, true);

        //    Animate an array of textureRegions to make the sprite object seem to move
        //TextureRegion[] cars = { car,car,car };
        //carAnimation = new Animation(0.06f, cars);
        //carAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);



    }

    public static void dispose() {
        // We must dispose of the texture when we are finished.
       // carTexture.dispose();
    }
}
