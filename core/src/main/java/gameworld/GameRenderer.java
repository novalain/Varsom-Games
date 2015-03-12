package gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import gameobjects.Car;
import helpers.AssetLoader;

/**
 * Created by Alice on 2015-03-11.
 */
public class GameRenderer {
    // get a camera object creating a 2D-field
    private OrthographicCamera cam;
    private GameWorld myWorld;
    private ShapeRenderer shapeRenderer;

    private SpriteBatch batcher;

    private float screenHeight, screenWidth;

    //constructor
    public GameRenderer(GameWorld world){
        screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();

        myWorld = world;
        cam = new OrthographicCamera();
        cam.setToOrtho(true, screenWidth, screenHeight);

        batcher = new SpriteBatch();

        // attach batcher to camera
        batcher.setProjectionMatrix(cam.combined);

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);

    }
    public void render(float runTime){
        // We will move these outside of the loop for performance later.
        Car car = myWorld.getCar();

        Gdx.gl.glClearColor(0, 0, 0, 1); // Creating black background
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Begin ShapeRenderer
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Draw Background color
        shapeRenderer.setColor(55 / 255.0f, 80 / 255.0f, 100 / 255.0f, 1);
        shapeRenderer.rect(0, 0, screenWidth, screenHeight/2 + 66);

        // End ShapeRenderer
        shapeRenderer.end();

        // Begin SpriteBatch
        batcher.begin();
        // Disable transparency
        // This is good for performance when drawing images that do not require
        // transparency.
        batcher.disableBlending();
        batcher.draw(AssetLoader.bg, 0, 0, screenWidth, screenHeight);

        // The car needs transparency, so we enable that again.
        batcher.enableBlending();

        batcher.draw(AssetLoader.car, screenWidth/2, screenHeight/2, 50, 74);
/*
        // Draw bird at its coordinates. Retrieve the Animation object from
        // AssetLoader
        // Pass in the runTime variable to get the current frame.
        batcher.draw(AssetLoader.birdAnimation.getKeyFrame(runTime),
                bird.getX(), bird.getY(), bird.getWidth(), bird.getHeight());
*/
        // End SpriteBatch
        batcher.end();

    }
}
