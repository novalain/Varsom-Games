package helpers;

import com.badlogic.gdx.Gdx;
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
    public static Texture carTexture,carTexture2, tireTrackTexture;
    //obstacles
    public static Texture tireObstacleTexture,wallTexture;
    //backgrounds
    public static Texture bgTexture, testTrackTexture, testTrackMask;

    public static TextureRegion bg;
    public static TextureRegion car,tracks1,tracks2,tracks3;
    public static Animation carAnimation,wheelAnimation;


    public static void load() {
        // loading texture for car
        carTexture = new Texture(Gdx.files.internal("img/Car.png"));
        carTexture2 = new Texture(Gdx.files.internal("img/car2.png"));
        //carTexture = new Texture(Gdx.files.internal("img/car.png"));
        //carTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        tireTrackTexture = new Texture(Gdx.files.internal("img/tire-tracks.jpg"));
        tireTrackTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        tireObstacleTexture = new Texture("img/tire.png");
        wallTexture = new Texture("img/blackbox.jpg");

        // Guessing bg is background
        bgTexture = new Texture(Gdx.files.internal("img/temp_background.png"));
        bgTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        testTrackTexture = new Texture("img/tracks/temptrack.png");
        testTrackMask = new Texture("img/tracks/trackmask.png");

        bg = new TextureRegion(bgTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        bg.flip(false, true); // Flip the sprite vertically to fit coordinate system Y-down

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
