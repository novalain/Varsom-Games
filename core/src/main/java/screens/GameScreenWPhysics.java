package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import gameobjects.Car;
import gameobjects.TireObstacle;
import gameobjects.tempCar;
import helpers.InputHandler;

/**
 * Created by Alice on 2015-03-11.
 */
public class GameScreenWPhysics implements Screen{
    // Class variables
    private World world;
    private tempCar car;
    private TireObstacle tire, tire2, tire3, tire4;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;

    private ShapeRenderer shapeRenderer;

    private final float TIMESTEP = 1/60f;
    private final int   VELOCITY_ITERATIONS = 8,
                        POSITION_ITERATIONS = 3;

    private SpriteBatch batch;
    //private Sprite smileySprite;

    private Array<Body> tmpBodies = new Array<Body>();

    private final float pixelsToMeters = 1;

    public GameScreenWPhysics(){
        world = new World(new Vector2(0,10),true);
        debugRenderer = new Box2DDebugRenderer();
        int SCREEN_WIDTH = Gdx.graphics.getWidth();
        int SCREEN_HEIGHT = Gdx.graphics.getHeight();

        car = new tempCar(new Vector2(0.25f, -2.0f), 0.5f, world);
        Gdx.input.setInputProcessor(new InputHandler(car));

        camera = new OrthographicCamera(SCREEN_WIDTH/100,SCREEN_HEIGHT/100);

       // camera.setToOrtho(true, Gdx.graphics.getWidth()/100, Gdx.graphics.getHeight()/100);

        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

     //   batch.setProjectionMatrix(camera.combined);

        tire = new TireObstacle(new Vector2(0.0f,-1.6f),1.5f,world);
        tire2 = new TireObstacle(new Vector2(0.0f,1.6f),0.5f,world);
        tire3 = new TireObstacle(new Vector2(-1.3f,0.20f),0.5f,world);
        tire4 = new TireObstacle(new Vector2(1.3f,0.20f),0.5f,world);

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.7f,0.2f,0.2f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        world.getBodies(tmpBodies);
        for (Body body : tmpBodies) {
            //body.applyForceToCenter(12.0f,-11.0f,true);
            if ( body.getUserData() != null && body.getUserData() instanceof Sprite) {
                Sprite sprite = (Sprite) body.getUserData();
                sprite.setPosition(body.getPosition().x - sprite.getWidth()/2, body.getPosition().y - sprite.getHeight()/2);
                sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
                sprite.draw(batch);

            }
        }

        batch.end();


        world.step(TIMESTEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);

        camera.position.set(car.getBody().getPosition().x, car.getBody().getPosition().y, 0);
        camera.update();

        batch.setProjectionMatrix(camera.combined);

        debugRenderer.render(world, camera.combined);




    }

    @Override
    public void resize(int width, int height) {
        //Gdx.app.log("GameScreen", "resizing in here");
    }

    @Override
    public void show() {
        //Gdx.app.log("GameScreen", "show called");
    }

    @Override
    public void hide() {
        //Gdx.app.log("GameScreen", "hide called");
        dispose();
    }

    @Override
    public void pause() {
        //Gdx.app.log("GameScreen", "pause called");
    }

    @Override
    public void resume() {
        //Gdx.app.log("GameScreen", "resume called");
    }

    @Override
    public void dispose() {
        // Leave blank
    }

}