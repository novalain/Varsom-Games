package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import Tracks.TestTrack;
import gameobjects.TireObstacle;
import gameobjects.tempCar;
import helpers.InputHandler;


public class GameScreenWPhysics implements Screen{

    public static final int STEER_NONE=0;
    public static final int STEER_RIGHT=1;
    public static final int STEER_LEFT=2;

    public static final int ACC_NONE=0;
    public static final int ACC_ACCELERATE=1;
    public static final int ACC_BRAKE=2;


    // Class variables
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;

    private ShapeRenderer shapeRenderer;

    private final float TIMESTEP = 1/60f;
    private final int   VELOCITY_ITERATIONS = 8,
                        POSITION_ITERATIONS = 3;

    private SpriteBatch batch;

    private Array<Body> tmpBodies = new Array<Body>();

    private final float pixelsToMeters = 1;

    //TEMP VARIABLES
    private tempCar car;
    private TireObstacle tire, tire2, tire3, tire4;
    private TestTrack testTrack;


    public GameScreenWPhysics(){
        world = new World(new Vector2(0f,0f),true);
        debugRenderer = new Box2DDebugRenderer();
        int SCREEN_WIDTH = Gdx.graphics.getWidth();
        int SCREEN_HEIGHT = Gdx.graphics.getHeight();

        camera = new OrthographicCamera(SCREEN_WIDTH/100,SCREEN_HEIGHT/100);
        // camera.setToOrtho(true, Gdx.graphics.getWidth()/100, Gdx.graphics.getHeight()/100);

        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

        // Create objects
        testTrack = new TestTrack(world);

        //car = new tempCar(new Vector2(0.0f, -8.2f), new Vector2(1.0f,2.0f), world);
        this.car = new tempCar(world, 1, 2,
                new Vector2(3.2f, 0), (float) Math.PI, 60, 20, 40);

        Gdx.input.setInputProcessor(new InputHandler(car));

        tire = new TireObstacle(new Vector2(0.0f,-1.6f),1.5f,world);
        tire2 = new TireObstacle(new Vector2(0.0f,1.6f),0.5f,world);
        tire3 = new TireObstacle(new Vector2(-1.3f,0.20f),0.5f,world);
        tire4 = new TireObstacle(new Vector2(1.3f,0.20f),0.5f,world);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.7f, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        batch.setProjectionMatrix(camera.combined);

        testTrack.addToRenderBatch(batch);

       // car.getDeviceAccleration();

        debugRenderer.render(world, camera.combined);
        world.step(TIMESTEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);

        car.update(Gdx.app.getGraphics().getDeltaTime());

        camera.position.set(car.body.getPosition().x, car.body.getPosition().y, 0);

        //world.clearForces();
        // Set camera position to same as car

        // Get current angle from body
        float playerAngle = constrainAngle(car.body.getAngle()*MathUtils.radiansToDegrees);

       // Convert camera angle from [-180, 180] to [0, 360]
        float camAngle = -getCurrentCameraAngle(camera) + 180;

        camera.rotate((camAngle - playerAngle));
        camera.update();
    }

    // For more than 2pi rotation of the car the angle continues to increase, limit it to [0, 2pi].
    private float constrainAngle(float playerAngle){

        while(playerAngle<=0){
            playerAngle += 360;
        }
        while(playerAngle>360){
            playerAngle -= 360;
        }

        return playerAngle;

    }

    private float getCurrentCameraAngle(OrthographicCamera cam)
    {
        Gdx.app.log("cam.up.x", cam.up.x + "");

        return (float)Math.atan2(cam.up.x, cam.up.y)*MathUtils.radiansToDegrees;
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