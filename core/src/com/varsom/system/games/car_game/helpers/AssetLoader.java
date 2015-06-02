package com.varsom.system.games.car_game.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


/**
 * Created by Alice on 2015-03-12.
 * Handles all textures such as car texture
 * textureRegions are several
 */
public class AssetLoader {
    //car
    public static Texture carTextureAmbulance, carTextureTurtle, carTextureOld, carTextureCoffin, carTextureHotdog, carTexturePiggelin, tireTrackTexture, pathCircleTexture;
    // other colors
    public static Texture  carTextureTurtle2, carTextureCoffin2, carTextureHotdog2, carTexturePiggelin2;

    //obstacles
    public static Texture tireObstacleTexture, wallTexture;
    //backgrounds
    public static Texture bgTexture, testTrackTexture, testTrackMask, track2Texture, track2Mask;
    //redlight
    public static Texture redlightTexture;

    public static TextureRegion bg;
    public static TextureRegion car, tracks1, tracks2, tracks3;
    // public static Animation carAnimation;
    public static TextureRegion tex1, tex2, tex3;

    public static FileHandle particleFile, particleImg, firepitFile;
    // animation of redlights
    public static TextureRegion[] redlightsFrames;

    //KRAZY RACY MENU
    public static Texture krazyTitleTexture,
            krazyPlayTexture, krazyPlayDownTexture,
            krazyExitTexture, krazyExitDownTexture,
            krazyThingyTexture_1, krazyThingyTexture_2, krazyThingyTexture_mad;

    public static Skin skin;
    public static FileHandle krazyFontFile;


    public static void load() {
        //NEW MENU
        krazyFontFile = Gdx.files.internal("system/fonts/BADABB__.TTF");
        skin = new Skin(Gdx.files.internal("car_game_assets/skins/menuSkin.json"),
                new TextureAtlas(Gdx.files.internal("system/skins/menuSkin.pack")));
        krazyTitleTexture = new Texture(Gdx.files.internal("car_game_assets/menu/krazyRacyText.png"));
        krazyPlayTexture = new Texture(Gdx.files.internal("car_game_assets/menu/krazyRacyMenuPlay.png"));
        krazyPlayDownTexture = new Texture(Gdx.files.internal("car_game_assets/menu/krazyRacyMenuPlayHover.png"));
        krazyExitTexture = new Texture(Gdx.files.internal("car_game_assets/menu/krazyRacyMenuExit.png"));
        krazyExitDownTexture = new Texture(Gdx.files.internal("car_game_assets/menu/krazyRacyMenuExitHover.png"));
        krazyThingyTexture_1 = new Texture(Gdx.files.internal("car_game_assets/menu/crazythingy.png"));
        krazyThingyTexture_2 = new Texture(Gdx.files.internal("car_game_assets/menu/hot-dog.png"));
        krazyThingyTexture_mad = new Texture(Gdx.files.internal("car_game_assets/menu/crazythingymad.png"));


        pathCircleTexture = new Texture(Gdx.files.internal("car_game_assets/img/colorwheel.png"));
        // loading texture for car
        carTextureAmbulance = new Texture(Gdx.files.internal("car_game_assets/img/ambulance_animation/1c.png"));
        carTextureOld = new Texture(Gdx.files.internal("car_game_assets/img/cars/car.png"));
        carTextureTurtle = new Texture(Gdx.files.internal("car_game_assets/img//cars/TurtleCarFast.png"));
        carTextureCoffin = new Texture(Gdx.files.internal("car_game_assets/img/cars/coffinCar.png"));
        carTextureHotdog = new Texture(Gdx.files.internal("car_game_assets/img/cars/hotdogCar.png"));
        carTexturePiggelin = new Texture(Gdx.files.internal("car_game_assets/img/cars/piggelinCar.png"));

        // Secondary
        carTextureTurtle2 = new Texture(Gdx.files.internal("car_game_assets/img//cars/TurtleCarFast2.png"));
        carTextureCoffin2 = new Texture(Gdx.files.internal("car_game_assets/img/cars/coffinCar2.png"));
        carTextureHotdog2 = new Texture(Gdx.files.internal("car_game_assets/img/cars/hotdogCar2.png"));
        carTexturePiggelin2 = new Texture(Gdx.files.internal("car_game_assets/img/cars/piggelinCar2.png"));
        //carTextureAmbulance.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        //carTextureTurtle.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Linear);

        tireTrackTexture = new Texture(Gdx.files.internal("car_game_assets/img/tire-tracks.jpg"));
        //tireTrackTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        tireObstacleTexture = new Texture("car_game_assets/img/tire.png");
        wallTexture = new Texture("car_game_assets/img/wall.png");

       // bgTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        testTrackTexture = new Texture("car_game_assets/img/tracks/newtrack2.png");
        testTrackMask = new Texture("car_game_assets/img/tracks/trackmask.png");

        track2Texture = new Texture("car_game_assets/img/tracks/track2.png");
        track2Mask = new Texture("car_game_assets/img/tracks/track2mask.png");

        //bg = new TextureRegion(bgTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //bg.flip(false, true); // Flip the sprite vertically to fit coordinate system Y-down

        particleFile = Gdx.files.internal("car_game_assets/effects/smoke_cartoon.p");
        firepitFile = Gdx.files.internal("car_game_assets/effects/firepit.p");
        particleImg = Gdx.files.internal("car_game_assets/img");

        // For animation on ambulance but should probably use an atlas
        tex1 = new TextureRegion(new Texture("car_game_assets/img/ambulance_animation/1c.png"));
        tex2 = new TextureRegion(new Texture("car_game_assets/img/ambulance_animation/2c.png"));
        tex3 = new TextureRegion(new Texture("car_game_assets/img/ambulance_animation/3c.png"));

        // For animation of redlights
        redlightTexture = new Texture(Gdx.files.internal("car_game_assets/img/trafficLights.png"));
        redlightsFrames = new TextureRegion[4];
        redlightsFrames[3] = new TextureRegion(redlightTexture, 0, 0, redlightTexture.getWidth(), (redlightTexture.getHeight() / 4)-1);
        redlightsFrames[2] = new TextureRegion(redlightTexture, 0, redlightTexture.getHeight() / 4, redlightTexture.getWidth(), (redlightTexture.getHeight() / 4)-1);
        redlightsFrames[1] = new TextureRegion(redlightTexture, 0, redlightTexture.getHeight() / 2, redlightTexture.getWidth(), (redlightTexture.getHeight() / 4)-1);
        redlightsFrames[0] = new TextureRegion(redlightTexture, 0, 3 * (redlightTexture.getHeight() / 4), redlightTexture.getWidth(), (redlightTexture.getHeight() / 4)-1);

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
        //car = new TextureRegion(carTextureAmbulance, 0, 0, 50, 74);
        //car.flip(false, true);

        //    Animate an array of textureRegions to make the sprite object seem to move
        //TextureRegion[] cars = { car,car,car };
        //carAnimation = new Animation(0.06f, cars);
        //carAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

    }

    public static void dispose() {
        // We must dispose of the texture when we are finished.
    }
}
