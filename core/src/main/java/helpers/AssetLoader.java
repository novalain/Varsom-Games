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
    public static Texture bgTexture;
    public static TextureRegion bg;
    public static TextureRegion car;


    public static void load() {
        // loading texture for car
        texture = new Texture(Gdx.files.internal("img/Car.png"));
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        // Guessing bg is background
        bgTexture = new Texture(Gdx.files.internal("img/temp_background.png"));
        bgTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        bg = new TextureRegion(bgTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        bg.flip(false, true); // Flip the sprite vertically to fit coordinate system Y-down

        // Texture size of car.png is 50*74 px
        car = new TextureRegion(texture, 0, 0, 50, 74);
        car.flip(false, true);

  //    Animate an array of textureRegions to make the sprite object seem to move
  //    TextureRegion[] birds = { birdDown, bird, birdUp };
  /*      carAnimation = new Animation(0.06f, car);
        carAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
*/
    }

    public static void dispose() {
        // We must dispose of the texture when we are finished.
        texture.dispose();
    }
}
