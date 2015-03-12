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
    public static Texture texture;
    public static TextureRegion bg;

    public static Animation carAnimation;
    public static TextureRegion car;


    public static void load() {

        texture = new Texture(Gdx.files.internal("img/Car.png"));
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        // Guessing bg is background
        bg = new TextureRegion(texture, 0, 0, 136, 43);
        bg.flip(false, true);
/*
        grass = new TextureRegion(texture, 0, 43, 143, 11);
        grass.flip(false, true);

        birdDown = new TextureRegion(texture, 136, 0, 17, 12);
        birdDown.flip(false, true);
*/
        car = new TextureRegion(texture, 153, 0, 17, 12);
        car.flip(false, true);
/*
        birdUp = new TextureRegion(texture, 170, 0, 17, 12);
        birdUp.flip(false, true);
*/
        //      TextureRegion[] birds = { birdDown, bird, birdUp };
        carAnimation = new Animation(0.06f, car);
        carAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

    }

    public static void dispose() {
        // We must dispose of the texture when we are finished.
        texture.dispose();
    }
}
