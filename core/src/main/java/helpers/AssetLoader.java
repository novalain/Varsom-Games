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
    public static Texture carTexture;
    public static Texture tireTexture;
    public static Texture testTrackTexture;
    public static Texture wallTexture;
    public static Texture bgTexture;
    public static TextureRegion bg;
    public static TextureRegion car;
    public static Animation carAnimation;


    public static void load() {
        // loading texture for car
        carTexture = new Texture(Gdx.files.internal("img/Car.png"));
        carTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        tireTexture = new Texture("img/tire.png");
        wallTexture = new Texture("img/blackbox.jpg");

        // Guessing bg is background
        bgTexture = new Texture(Gdx.files.internal("img/temp_background.png"));
        bgTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        testTrackTexture = new Texture("img/tracks/temptrack.png");

        bg = new TextureRegion(bgTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        bg.flip(false, true); // Flip the sprite vertically to fit coordinate system Y-down

        // Texture size of car.png is 50*74 px
        car = new TextureRegion(carTexture, 0, 0, 50, 74);
        car.flip(false, true);

  //    Animate an array of textureRegions to make the sprite object seem to move
        TextureRegion[] cars = { car,car,car };
        carAnimation = new Animation(0.06f, cars);
        carAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

    }

    public static void dispose() {
        // We must dispose of the texture when we are finished.
        carTexture.dispose();
    }
}
