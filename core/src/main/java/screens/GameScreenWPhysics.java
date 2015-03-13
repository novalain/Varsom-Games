package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

import gameworld.GameRenderer;
import gameworld.GameWorld;
import helpers.InputHandler;


/**
 * Created by Alice on 2015-03-11.
 */
public class GameScreenWPhysics implements Screen{
    // Class variables
    private World world;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;

    private final float TIMESTEP = 1/60f;
    private final int   VELOCITY_ITERATIONS = 8,
                        POSITION_ITERATIONS = 3;

    private SpriteBatch batch;
    private Sprite smileySprite;

    private Array<Body> tmpBodies = new Array<Body>();

    private final float pixelsToMeters = 1;

    public GameScreenWPhysics(){
        world = new World(new Vector2(0,10),true);
        debugRenderer = new Box2DDebugRenderer();
        int SCREEN_WIDTH = Gdx.graphics.getWidth();
        int SCREEN_HEIGHT = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(SCREEN_WIDTH/100,SCREEN_HEIGHT/100);

        batch = new SpriteBatch();
        BodyDef obstacleDef = new BodyDef();
        obstacleDef.type = BodyDef.BodyType.StaticBody;
        obstacleDef.position.set(0.1f,2.0f);

        //obstacle shape
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(1.0f);

        //Obstacle Fixture
        FixtureDef obstacleFixtureDef = new FixtureDef();
        obstacleFixtureDef.shape = circleShape;
        obstacleFixtureDef.density = 2.0f;
        obstacleFixtureDef.friction = 0.5f;     //value between 0-1
        obstacleFixtureDef.restitution = 0.23f; //value between 0-1

        Body tireObstacle = world.createBody(obstacleDef);
        Fixture fixtObstacle = tireObstacle.createFixture(obstacleFixtureDef);

        BodyDef carDef = new BodyDef();

        carDef.type = BodyDef.BodyType.DynamicBody;
        carDef.position.set(0.1f,-2f);

        //Car Fixture
        FixtureDef carFixtureDef = new FixtureDef();
        circleShape.setRadius(0.5f);
        carFixtureDef.shape = circleShape;
        carFixtureDef.density = 2.0f;
        carFixtureDef.friction = 0.5f;     //value between 0-1
        carFixtureDef.restitution = 0.23f; //value between 0-1

        smileySprite = new Sprite(new Texture("img/smiley.png"));
        smileySprite.setSize(circleShape.getRadius()*2,circleShape.getRadius()*2);
        smileySprite.setOriginCenter();

        Body car = world.createBody(carDef);
        car.setUserData(smileySprite);
        car.setTransform(0.0f, 0.0f, 0);

        Fixture fixtCar = car.createFixture(obstacleFixtureDef);

        circleShape.dispose();
    }
    @Override
    public void render(float delta) {
       /* runTime+=delta;
        // connecting GameWorld and GameRenderer and updating them both
        world.update(delta);
        renderer.render(runTime);*/
        Gdx.gl.glClearColor(0.7f,0.2f,0.2f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        world.getBodies(tmpBodies);
        for (Body body : tmpBodies) {
            if ( body.getUserData() != null && body.getUserData() instanceof Sprite) {
                Sprite sprite =(Sprite) body.getUserData();
                sprite.setPosition(body.getPosition().x - sprite.getWidth()/2, body.getPosition().y - sprite.getHeight()/2);
                sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
                sprite.draw(batch);
            }
        }
        batch.end();
        debugRenderer.render(world, camera.combined);

        world.step(TIMESTEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
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