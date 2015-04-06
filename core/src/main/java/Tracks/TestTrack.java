package Tracks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.Vector;

import gameobjects.BoxObstacle;
import gameobjects.TireObstacle;
import gameobjects.Wheel;
import gameobjects.tempCar;
import helpers.AssetLoader;
import helpers.InputHandler;
import screens.MoveSprite;

/**
 * Created by oskarcarlbaum on 18/03/15.
 */
public class TestTrack {

    public static Sprite backgroundSprite;
    public Vector<Sprite> sprites;
    public World world;
    public tempCar car,car2;
    private Body boxBody;
    private Array<Body> tmpBodies = new Array<Body>();
    private Vector<Body> backLayer;
    private Vector<Body> frontLayer;
    private float scaleBG;

    //For mask
    public static Sprite backgroundMask;
    public Pixmap pixmap;

    //For waypoints
    private ShapeRenderer sr;
    private Sprite pathSprite;
    private Array<MoveSprite> moveSprites;
    public MoveSprite moveSprite;

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

        //create player cars
        //car = new tempCar(new Vector2(0.0f, -8.2f), new Vector2(1.0f,2.0f), world);
        float carWidth = 0.50f, carLength = 1.0f;
        Vector2 spawnPos1 = new Vector2(-16f, -16f);
        Vector2 spawnPos2 = new Vector2(-15f, -17f);
        float spawnPosRotation = (float) -Math.PI/2;
        car = new tempCar(carWidth, carLength, spawnPos1,world,new Sprite(AssetLoader.carTexture),
                spawnPosRotation, 60, 20, 15);
        Gdx.input.setInputProcessor(new InputHandler(car));

        car2 = new tempCar(carWidth, carLength, spawnPos2,world, new Sprite(AssetLoader.carTexture2),
                spawnPosRotation, 60, 20, 15);

        //add all cars to the frontLayer and all wheels to the backLayer
        for(Wheel tempWheel : car.wheels) {
            backLayer.addElement(tempWheel.body);
        }
        frontLayer.addElement(car.getBody());
        frontLayer.addElement(car2.getBody());

    }

    private void createWayPoints(){

        // Set up shaperenderer
        sr = new ShapeRenderer();

        pathSprite = new Sprite(AssetLoader.wallTexture);
        pathSprite.setSize(1, 1);

        moveSprite = new MoveSprite(pathSprite, getChosenPath());

    }
    private void createObstacles(){
      //Static physical objects
        float wallThickness = 4.0f;
       /* BoxObstacle upperWall = new BoxObstacle(new Vector2(0,backgroundSprite.getHeight()/2f),0
                ,new Vector2(backgroundSprite.getWidth(),wallThickness), world);
        BoxObstacle lowerWall = new BoxObstacle(new Vector2(0,-backgroundSprite.getHeight()/2f),0
                ,new Vector2(backgroundSprite.getWidth(),wallThickness), world);
        BoxObstacle leftWall = new BoxObstacle(new Vector2(-backgroundSprite.getWidth()/2f,0),0
                ,new Vector2(wallThickness,backgroundSprite.getHeight()), world);
        BoxObstacle rightWall = new BoxObstacle(new Vector2(backgroundSprite.getWidth()/2f,0),0
                ,new Vector2(wallThickness,backgroundSprite.getHeight()), world);*/

        TireObstacle tire  = new TireObstacle(new Vector2( 0.0f, -6f), 0, 1.5f, world);
        TireObstacle tire2 = new TireObstacle(new Vector2( 0.0f,  1.6f), 0, 0.5f, world);
        TireObstacle tire3 = new TireObstacle(new Vector2(-13f, 0.20f), 0, 0.5f, world);
        TireObstacle tire4 = new TireObstacle(new Vector2( 13f, 0.20f), 0, 0.5f, world);

        //Add all newly made obstacles to the backLayer
      /*  backLayer.addElement(upperWall.getBody());
        backLayer.addElement(lowerWall.getBody());
        backLayer.addElement(leftWall.getBody());
        backLayer.addElement(rightWall.getBody());*/
        backLayer.addElement(tire.getBody());
        backLayer.addElement(tire2.getBody());
        backLayer.addElement(tire3.getBody());
        backLayer.addElement(tire4.getBody());
    }

    private Array<Vector2> getChosenPath(){
        Array<Vector2> path = new Array<Vector2>();

        /*float[] waypoints = {
                100, 400, 45, 355, 100, 300,
                220, 300, 252, 278, 270, 229,
                252, 170, 204, 153, 79, 154,
                43, 116, 93, 70, 504, 70,
                559, 110, 352, 278, 393, 323,
                560, 249, 581, 387, 1900, 1
        };*/

        // Hardcoded values for this track only
        float[] waypoints = {   -180,  -160,
                                -255,  -153,
                                -275,  -115,
                                -255,   -77,
                                -220,   -60,
                                -100,   -60,
                                 -68,   -38,
                                 -50,    11,
                                 -68,    70,
                                -116,    87,
                                -241,    86,
                                -277,   124,
                                -227,   170,
                                 184,   170,
                                 239,   130,
                                  32,   -38,
                                  73,   -83,
                                 240,    -9,
                                 261,  -147,
                                -170,  -160};



        //scale down with respect to background scale
        for(int i = 0; i < waypoints.length; i++){
            waypoints[i] /= scaleBG;
        }


        for(int i = 0; (i+1) < waypoints.length; i+=2){
            path.add(new Vector2(waypoints[i], waypoints[i+1]));
            Gdx.app.log("In chosen path", "point : "+waypoints[i] + " and " + waypoints[i+1]);
            Gdx.app.log("In chosen path", "size: "+i);
        }

        return path;
    }

    private void createBackground() {
        backgroundSprite = new Sprite(AssetLoader.testTrackTexture);
        backgroundSprite.setSize(backgroundSprite.getWidth()/scaleBG,backgroundSprite.getHeight()/scaleBG);
        //backgroundSprite.setOriginCenter();

        backgroundSprite.setPosition(-backgroundSprite.getWidth()/2,-backgroundSprite.getHeight()/2);
        sprites.addElement(backgroundSprite);

        // Set up mask
        backgroundMask = new Sprite(AssetLoader.testTrackMask);
        backgroundMask.getTexture().getTextureData().prepare();
        pixmap = backgroundMask.getTexture().getTextureData().consumePixmap();
        //pixmap.dispose();
    }

    public void addToRenderBatch(SpriteBatch inBatch, Camera camera) {
        
        inBatch.begin();
        // Draw sprites
        for (Sprite sprite : sprites) {
            sprite.draw(inBatch);//backgroundSprite.draw(inBatch);
        }

       // Gdx.app.log("color:",  inBatch.);


        // Set shape renderer to be drawn in world, not on screen
        sr.setProjectionMatrix(camera.combined);

        // Draw fake car ?
        moveSprite.draw(inBatch);

      //  Vector2 previous = moveSprite.getPath().first();

        //Draw physical objects
        //world.getBodies(tmpBodies);
        /*for (Body body : tmpBodies) {
            if ( body.getUserData() != null && body.getUserData() instanceof Sprite) {
                Sprite sprite = (Sprite) body.getUserData();
                sprite.setPosition(body.getPosition().x - sprite.getWidth()/2, body.getPosition().y - sprite.getHeight()/2);
                sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
                sprite.draw(inBatch);
            }
        }*/

        drawBodySprites(backLayer,inBatch);
        drawBodySprites(frontLayer,inBatch);

        // Draw all waypoints
        /*for(Vector2 waypoint: moveSprite.getPath()){
            sr.begin(ShapeRenderer.ShapeType.Line);
            sr.line(previous, waypoint);
            sr.end();

            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.circle(waypoint.x, waypoint.y, 0.5f);
            sr.end();

            previous = waypoint;
        }*/

        inBatch.end();
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
}
