package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;

import Tracks.TestTrack;
import Tracks.Track2;
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

    public static int level;

    // For countdown
    private static float countDownTimer = 5.f;
    private static boolean paused = true;

    // Class variables
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;

    private ShapeRenderer shapeRenderer;

    private final float TIMESTEP = 1/60f;
    private final int   VELOCITY_ITERATIONS = 8,
                        POSITION_ITERATIONS = 3;

    private SpriteBatch batch;
    private TestTrack testTrack;
    private Track2 track2;
    private Pixmap pixmap;
    private tempCar car;
    private MoveSprite moveSprite;
    private Sprite bgSprite;

    public GameScreenWPhysics(int level){

        this.level = level;
        world = new World(new Vector2(0f,0f),true);
        debugRenderer = new Box2DDebugRenderer();
        int SCREEN_WIDTH = Gdx.graphics.getWidth();
        int SCREEN_HEIGHT = Gdx.graphics.getHeight();

        // Create objects and select level
        switch(level){
            case 1:
                testTrack = new TestTrack(world);
                moveSprite = testTrack.moveSprite;
                car = testTrack.car;
                pixmap = testTrack.pixmap;
                bgSprite = testTrack.backgroundSprite;
                break;
            case 2:
                track2 = new Track2(world);
                moveSprite = track2.moveSprite;
                car = track2.car;
                pixmap = track2.pixmap;
                bgSprite = track2.backgroundSprite;
                break;
            default:
                System.out.println("Mega Error");

        }

        // Init camera
        camera = new OrthographicCamera(SCREEN_WIDTH/100,SCREEN_HEIGHT/100);
        camera.rotate(-90);
        camera.position.set(new Vector2(moveSprite.getX(),moveSprite.getY()), 0);
        //camera.zoom = 5.0f; // can be used to see the entire track
        camera.update();

        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);

       // float camAngle = getCurrentCameraAngle(camera);
       // camera.rotate(-camAngle + testTrack.moveSprite.getRotation()-90);

       /* //car = new tempCar(new Vector2(0.0f, -8.2f), new Vector2(1.0f,2.0f), world);
        float carWidth = 1.0f, carLength = 2.0f;
        this.car = new tempCar(carWidth, carLength, new Vector2(3.2f, 0),world,
                 (float) Math.PI, 60, 20, 40);*/

        //Gdx.input.setInputProcessor(new InputHandler(car));

    }

    private void handleCountDownTimer(){

        countDownTimer -= Gdx.graphics.getDeltaTime();
        float secondsLeft = (int)countDownTimer % 60;

        // Render some kick-ass countdown label
        if(secondsLeft > 0){

            Gdx.app.log("COUNTDOWN: ", (int)secondsLeft + "");

        }

        if(secondsLeft == 0){

            paused = false;

        }

    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0.2f, 0.7f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        handleCountDownTimer();

        batch.setProjectionMatrix(camera.combined);

        if(level == 1)
            testTrack.addToRenderBatch(batch,camera);

        else if (level == 2)
            track2.addToRenderBatch(batch, camera);

        debugRenderer.render(world, camera.combined);

        world.step(TIMESTEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);

        //world.clearForces();
        // Set camera position to same as car

        // Get current angle from body
        //float playerAngle = constrainAngle(car.body.getAngle()*MathUtils.radiansToDegrees);

       // Here goes the all the updating / game logic
       if(!paused){

           setCarSpeed();
           updateCamera();
           moveSprite.update(Gdx.graphics.getDeltaTime());
           car.update(Gdx.app.getGraphics().getDeltaTime());
           //testTrack.car2.update()..

       }

    }


    // Sets speed on car based on value from color on backgroundMask (black or white),
    public void setCarSpeed(){

        // Gets value from pixmap, cars position in Box2D coord system is mapped onto the coordinate system of the mask (y down , x right).
        int valueFromMask = pixmap.getPixel((int)((bgSprite.getWidth()/2 + car.getBody().getPosition().x)*10), (int)((bgSprite.getHeight()/2 - car.getBody().getPosition().y)*10));

        // If car is not on track
        if(valueFromMask != -1){
            car.setSpeed(car.getSpeedKMH()*0.9f);
        }

    }



    private void updateCamera(){

        //float playerAngle = constrainAngle(testTrack.car.getAngle()*MathUtils.radiansToDegrees);

        camera.position.set(new Vector2(moveSprite.getX(),moveSprite.getY()), 0);

        // Convert camera angle from [-180, 180] to [0, 360]
        float camAngle = -getCurrentCameraAngle(camera) + 180;
        // Vector3 camDir = camera.direction;
        float desiredCamRotation = (camAngle - moveSprite.getRotation() - 90);
        float camRotDeviation = desiredCamRotation - camAngle;

        if(desiredCamRotation > 180){
            desiredCamRotation -= 360;
        }
        else if(desiredCamRotation < -180) {
            desiredCamRotation += 360;
        }

        // camera.rotate((camAngle - playerAngle));
        camera.rotate(desiredCamRotation*0.02f);

        camera.update();


    }

    // For more than 2pi rotation of the car the angle continues to increase, limit it to [0, 2pi].
    /** Is not used anymore??**/
   /* private float constrainAngle(float playerAngle){

        while(playerAngle<=0){
            playerAngle += 360;
        }
        while(playerAngle>360){
            playerAngle -= 360;
        }

        return playerAngle;

    }*/

    private float getCurrentCameraAngle(OrthographicCamera cam)
    {
        //Gdx.app.log("cam.up.x", cam.up.x + "");

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