package tracks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.Vector;

import gameobjects.BoxObstacle;
import gameobjects.Car;
import gameobjects.TireObstacle;
import gameobjects.Wheel;
import helpers.AssetLoader;
import helpers.InputHandler;
//import gameobjects.MoveSprite;

/**
 * Created by oskarcarlbaum on 18/03/15.
 */
public class TestTrack {

    public static Sprite backgroundSprite;
    public Vector<Sprite> sprites;
    public World world;
    public Car car,car2;
    private Body boxBody;
    private Array<Body> tmpBodies = new Array<Body>();
    private Vector<Body> backLayer;
    private Vector<Body> frontLayer;
    private float scaleBG;

    /** For catmull rom spline**/
    private CatmullRomSpline<Vector2> myCatmull;
    private Vector2[] points;
    // This must be atleast as much as number nr of waypoints. Higher value => smoother curve
    private static final int NUM_INTERPOLATED_POINTS = 20;

    //For mask
    public static Sprite backgroundMask;
    public Pixmap pixmap;

    //Particleeffects
    public ParticleEffect effect;
    // This one is needed if we want to access several layers in our particlesystem
    //public ParticleEmitter emitter;

    //For waypoints
    private ShapeRenderer sr;
    private Sprite pathSprite;
    //private Array<MoveSprite> moveSprites;
    //public MoveSprite moveSprite;

    public float elapsedTime = 0;
    private Animation carAnimation;

    private Array<Vector2> path;

    //For leader car & camera position
    //private Vector2 startPoint;
    private Vector2[] startLine;

    public TestTrack(World inWorld) {
        scaleBG = 10.0f;
        world = inWorld;
        sprites = new Vector<Sprite>();
        frontLayer = new Vector<Body>();
        backLayer = new Vector<Body>();
        createTestTrack();
    }

    private void createTestTrack(){

        createBackground();
        createObstacles();

        // Creates fake "Car" and waypoints
        createWayPoints();
        startLine = new Vector2[]{getPath().get(0), getPath().get(1)};

        //create player cars
        float carWidth = 0.5f, carLength = 1.0f;

        // Car body should be relative to the sprite?
        //float carWidth = carSprite.getWidth()/150;
        //float carLength = carSprite.getHeight()/150;

       // Sprite carSprite = new Sprite(AssetLoader.carTexture);
        TextureRegion[] frames = {AssetLoader.tex1, AssetLoader.tex2, AssetLoader.tex3};

        carAnimation = new Animation(1/15f, frames);
        carAnimation.setPlayMode(Animation.PlayMode.LOOP);

        Vector2 spawnPos1 = new Vector2(-16f, -16f);
        Vector2 spawnPos2 = new Vector2(-15f, -17f);

        float spawnPosRotation = (float) -Math.PI/2;

        car = new Car(carWidth, carLength, spawnPos1, world, null,
                spawnPosRotation, 60, 20, 30,this);
        sprites.addElement(car.pathTrackingSprite);

        car2 = new Car(carWidth, carLength, spawnPos2,world, new Sprite(AssetLoader.carTexture2),
                spawnPosRotation, 60, 20, 30,this);

        Gdx.input.setInputProcessor(new InputHandler(car));

        //add all cars to the frontLayer and all wheels to the backLayer
        for(Wheel tempWheel : car.wheels) {
            backLayer.addElement(tempWheel.body);
        }

        frontLayer.addElement(car.getBody());
        frontLayer.addElement(car2.getBody());

        // Set up particlesystem, smoke.p is created in i build-in software in libgdx
        // "Smoke.p" is linked together with a sample particle.png that is found in img folder
        effect = new ParticleEffect();
        effect.load(AssetLoader.particleFile, AssetLoader.particleImg);
        effect.setPosition(car.getBody().getPosition().x, car.getBody().getPosition().y);
        effect.scaleEffect(0.01f);
        effect.start();

    }

    private void createWayPoints(){
        // Set up shaperenderer
        sr = new ShapeRenderer();

        pathSprite = new Sprite(AssetLoader.wallTexture);
        pathSprite.setSize(1, 1);

        path = new Array<Vector2>();

        Vector2[] waypoints = { new Vector2(-170, -131),
                                new Vector2(-220, -131),
                                new Vector2(-245, -111),
                                new Vector2(-220,  -90),
                                new Vector2(-110,  -30),
                                new Vector2( -85,   11),
                                new Vector2(-110,   55),
                                new Vector2(-226,  114),
                                new Vector2(-245,  126),
                                new Vector2(-226,  138),
                                new Vector2( 169,  138),
                                new Vector2( 196,  127),
                                new Vector2( 158,   52),
                                new Vector2(  73,  -30),
                                new Vector2(  64,  -43),
                                new Vector2(  85,  -47),
                                new Vector2( 216,  -40),
                                new Vector2( 239,  -60),
                                new Vector2( 239, -110),
                                new Vector2( 210, -131)};

        //scale down with respect to background scale
        for(int i = 0; i < waypoints.length; i++){
            waypoints[i].x /= scaleBG;
            waypoints[i].y /= scaleBG;
        }

        // Allocate number of total points
        points = new Vector2[NUM_INTERPOLATED_POINTS];

        // Create Catmull-Rom spline
        //myCatmull = new CatmullRomSpline<Vector2>(waypoints, true);

        // Calculate the new interpolated points
        for(int i = 0; i < NUM_INTERPOLATED_POINTS; ++i)
        {
            //points[i] = new Vector2();
            points[i] = waypoints[i];
            //myCatmull.valueAt(points[i], ((float)i)/((float)NUM_INTERPOLATED_POINTS-1));
        }

        /** DEBUG WAYPOINTS **/
       /* for(int i = 0; (i+1) < waypoints.length; i+=2){
            path.add(new Vector2(waypoints[i], waypoints[i+1]));
            Gdx.app.log("In chosen path", "point : "+waypoints[i] + " and " + waypoints[i+1]);
            Gdx.app.log("In chosen path", "size: "+i);
        }*/

        // Same as before
        for(int i = 0 ; i < NUM_INTERPOLATED_POINTS; i++){
            path.add(points[i]);
        }

        //moveSprite = new MoveSprite(pathSprite, getPath());
    }

    private void createObstacles(){
      //Static physical objects
        float wallThickness = 4.0f;
        BoxObstacle upperWall = new BoxObstacle(new Vector2(0,backgroundSprite.getHeight()/2f),0
                ,new Vector2(backgroundSprite.getWidth(),wallThickness), world);
        BoxObstacle lowerWall = new BoxObstacle(new Vector2(0,-backgroundSprite.getHeight()/2f),0
                ,new Vector2(backgroundSprite.getWidth(),wallThickness), world);
        BoxObstacle leftWall = new BoxObstacle(new Vector2(-backgroundSprite.getWidth()/2f,0),0
                ,new Vector2(wallThickness,backgroundSprite.getHeight()), world);
        BoxObstacle rightWall = new BoxObstacle(new Vector2(backgroundSprite.getWidth()/2f,0),0
                ,new Vector2(wallThickness,backgroundSprite.getHeight()), world);

        TireObstacle tire  = new TireObstacle(new Vector2(  0.0f, -6.0f), 0, 1.5f, world);
        TireObstacle tire2 = new TireObstacle(new Vector2(  0.0f,  1.6f), 0, 0.5f, world);
        TireObstacle tire3 = new TireObstacle(new Vector2(-13.0f,  0.2f), 0, 0.5f, world);
        TireObstacle tire4 = new TireObstacle(new Vector2( 13.0f,  0.2f), 0, 0.5f, world);

        //Add all newly made obstacles to the backLayer
        backLayer.addElement(upperWall.getBody());
        backLayer.addElement(lowerWall.getBody());
        backLayer.addElement(leftWall.getBody());
        backLayer.addElement(rightWall.getBody());
        backLayer.addElement(tire.getBody());
        backLayer.addElement(tire2.getBody());
        backLayer.addElement(tire3.getBody());
        backLayer.addElement(tire4.getBody());
    }

    public Array<Vector2> getPath(){
        return path;
    }

    private void createBackground() {
        backgroundSprite = new Sprite(AssetLoader.testTrackTexture);
        backgroundSprite.setSize(backgroundSprite.getWidth()/scaleBG,backgroundSprite.getHeight()/scaleBG);
        //backgroundSprite.setOriginCenter();

        backgroundSprite.setPosition(-backgroundSprite.getWidth()/2,-backgroundSprite.getHeight()/2);
        //sprites.addElement(backgroundSprite);

        // Set up mask
        backgroundMask = new Sprite(AssetLoader.testTrackMask);
        backgroundMask.getTexture().getTextureData().prepare();
        pixmap = backgroundMask.getTexture().getTextureData().consumePixmap();
        //pixmap.dispose();
    }


    public void addToRenderBatch(SpriteBatch inBatch, Camera camera) {

        inBatch.begin();

        backgroundSprite.draw(inBatch);

        effect.setPosition(car.getBody().getPosition().x, car.getBody().getPosition().y);
        effect.draw(inBatch, Gdx.graphics.getDeltaTime());

        if(car.smoke){
            if(effect.isComplete()){
                effect.reset();
            }
        }

        // Draw sprites
        for (Sprite sprite : sprites) {
            sprite.draw(inBatch);
        }

        elapsedTime += Gdx.graphics.getDeltaTime();

        // Set shape renderer to be drawn in world, not on screen
        sr.setProjectionMatrix(camera.combined);

        // Draw fake car ?
        // moveSprite.draw(inBatch);

        drawBodySprites(backLayer,inBatch);
        drawBodySprites(frontLayer,inBatch);

        TextureRegion txtRegion = carAnimation.getKeyFrame(elapsedTime, true);
        Sprite sprite = new Sprite(txtRegion);

        // For the animation, could probably be done in a cleaner way
        sprite.setSize(car.width, car.length);
        sprite.setOriginCenter();
        sprite.setPosition(car.getBody().getPosition().x - sprite.getWidth()/2, car.getBody().getPosition().y - sprite.getHeight()/2);
        sprite.setRotation(car.getBody().getAngle() * MathUtils.radiansToDegrees);
        sprite.draw(inBatch);

        //drawWayPoints();

        inBatch.end();

    }

    private void drawWayPoints(){

        for(int i = 0; i < NUM_INTERPOLATED_POINTS - 1; i++){

            sr.begin(ShapeRenderer.ShapeType.Line);
            sr.line(points[i], points[i+1]);
            sr.end();

            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.circle(points[i+1].x, points[i+1].y, 0.5f);
            sr.end();
        }
    }

    private void drawBodySprites(Vector<Body> tempBodies, SpriteBatch inBatch){
        for (Body body : tempBodies) {
            if ( body.getUserData() != null && body.getUserData() instanceof Sprite) {
                Sprite sprite = (Sprite) body.getUserData();
                sprite.setPosition(body.getPosition().x - sprite.getWidth()/2, body.getPosition().y - sprite.getHeight()/2);
                sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
                sprite.draw(inBatch);
            }
        }
    }

    public Vector2[] getStartLine(){
        return startLine;
    }
}
