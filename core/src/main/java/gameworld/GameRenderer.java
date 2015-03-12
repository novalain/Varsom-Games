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
        cam.position.set(car.getPosition().x,car.getPosition().y, 0);
        cam.update();
        Gdx.gl.glClearColor(0, 0, 0.3f, 1); // Creating black background
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


/*      To create background color or "outside the sprite"-color, use this code
        // Begin ShapeRenderer
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Draw Background color
        shapeRenderer.setColor(55 / 255.0f, 80 / 255.0f, 100 / 255.0f, 1);
        shapeRenderer.rect(0, 0, screenWidth, screenHeight);

        // End ShapeRenderer
        shapeRenderer.end(); */

        // Begin SpriteBatch
        batcher.begin();
        batcher.setProjectionMatrix(cam.combined);
        // Disable transparency
        // This is good for performance when drawing images that do not require
        // transparency.
        batcher.disableBlending();
        batcher.draw(AssetLoader.bg,0, 0, screenWidth, screenHeight);

        // The car needs transparency, so we enable that again.
        batcher.enableBlending();

        //batcher.draw(AssetLoader.car, screenWidth/2, screenHeight/2, 50, 74);

        // Draw car at its coordinates, set rotation around middle based on car x-velocity, no scaling.
        // Retrieve the Animation object from Assetloader
        // Pass in the runTime variable to get the current frame.
        batcher.draw(AssetLoader.carAnimation.getKeyFrame(runTime),
                car.getPosition().x, car.getPosition().y, car.getWidth()/2, car.getHeight()/2, car.getWidth(), car.getHeight(), 1, 1, car.getVelocity().x/10);

        // End SpriteBatch
        batcher.end();

    }
}
