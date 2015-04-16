package com.varsom.system.games.car_game.tracks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Animation;
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

import com.varsom.system.games.car_game.gameobjects.BoxObstacle;
import com.varsom.system.games.car_game.gameobjects.Car;
import com.varsom.system.games.car_game.helpers.AssetLoader;

/**
 * Created by oskarcarlbaum on 15/04/15.
 */
public abstract class Track {

    //PHYSICS
    protected World world;

    //BACKGROUND
    protected Sprite bgSprite;
    protected float bgScale;

    //BACKGROUND MASK
    protected Sprite bgMask;
    protected Pixmap pixmap;

    //FOR LEADER CAR & CAMERA POSITION
    private Vector2[] startLine;
    private Car leaderCar;

    //WAYPOINTS
    private ShapeRenderer shapeRenderer;
    private CatmullRomSpline<Vector2> myCatmull;
    private Vector2[] points;
    private Array<Vector2> path;
    private int NUM_INTERPOLATED_POINTS;

    //FOR RENDERING
    protected Vector<Body> backLayer;
    protected Vector<Body> frontLayer;
    protected Vector<Sprite> sprites;

    //CARS
    protected int MAXIMUM_PLAYERS;
    protected Car[] cars;
    //protected float[] spawnPoints;

    //NETWORK RELATED??
    // TODO should maybe be within the gameScreen?!?!?!
    protected int NUMBER_OF_PLAYERS;

    //ANIMATION..
    // TODO shouldn't this be withing the 'Car' class?
    public float elapsedTime = 0;
    protected Animation carAnimation;

    /**
     *
     * @param world
     * @param backgroundSprite
     * @param backgroundMask
     * @param backgroundScale
     */
    protected Track(World world,Sprite backgroundSprite, Sprite backgroundMask, float backgroundScale) {
        bgSprite = backgroundSprite;
        bgMask = backgroundMask;
        bgScale = backgroundScale;
        this.world = world;
        sprites = new Vector<Sprite>();
        frontLayer = new Vector<Body>();
        backLayer = new Vector<Body>();
        createBackground();
    }

    protected void createBackground() {
        bgSprite.setSize(bgSprite.getWidth()/bgScale,bgSprite.getHeight()/bgScale);
        //backgroundSprite.setOriginCenter();

        bgSprite.setPosition(-bgSprite.getWidth()/2,-bgSprite.getHeight()/2);
        //sprites.addElement(backgroundSprite);

        // Set up mask
        bgMask.getTexture().getTextureData().prepare();
        pixmap = bgMask.getTexture().getTextureData().consumePixmap();
        //pixmap.dispose();

        //Create walls around the background sprite
        createWalls();
    }

    /**
     *
     * @param waypoints
     * @param smoothCurve
     * smoothCurve should be greater than 1 for any smoothing effect to occur
     */
    //TODO THIS FUNCTION NEEDS SOME WORK
    protected void createWayPoints(Vector2[] waypoints, int smoothCurve){
        if(smoothCurve < 1){ //can't be fewer waypoints
            smoothCurve = 1;
        }

        shapeRenderer = new ShapeRenderer();

        path = new Array<Vector2>();

        //scale down with respect to background scale
        for(int i = 0; i < waypoints.length; i++){
            waypoints[i].x /= bgScale;
            waypoints[i].y /= bgScale;
        }

        NUM_INTERPOLATED_POINTS = waypoints.length * smoothCurve;
//        Gdx.app.log("CATMULL", "SmoothCurve : "+ smoothCurve + ", WayPoints.lenght : " + waypoints.length);
//        Gdx.app.log("CATMULL", "Total: " + NUM_INTERPOLATED_POINTS);


        // Allocate number of total points
        points = new Vector2[NUM_INTERPOLATED_POINTS];

        // Create Catmull-Rom spline
        //myCatmull = new CatmullRomSpline<Vector2>(waypoints, true);

        // Calculate the new interpolated points
        for(int i = 0; i < NUM_INTERPOLATED_POINTS; i++)
        {
            //points[i] = new Vector2();
            points[i] = waypoints[i];
            //Gdx.app.log("CATMULL", "SmoothCurve : "+ smoothCurve + ", WayPoints.lenght : " + waypoints.length);
            //Gdx.app.log("CATMULL", "At position: " + i + ", out of total: " + NUM_INTERPOLATED_POINTS);
            //myCatmull.valueAt(points[i], ((float)i)/((float)NUM_INTERPOLATED_POINTS-1));
        }

        /* DEBUG WAYPOINTS */
       /* for(int i = 0; (i+1) < waypoints.length; i+=2){
            path.add(new Vector2(waypoints[i], waypoints[i+1]));
            Gdx.app.log("In chosen path", "point : "+waypoints[i] + " and " + waypoints[i+1]);
            Gdx.app.log("In chosen path", "size: "+i);
        }*/

        // Same as before
        for(int i = 0 ; i < NUM_INTERPOLATED_POINTS; i++){
            path.add(points[i]);
        }

        startLine = new Vector2[]{path.get(0), path.get(1)};
        Gdx.app.log("StartLine", "startLine[0]: (" + startLine[0].toString() + "), startLine[1] = (" +startLine[1].toString() + ")");
    }

    /**
     * Creates walls around the background sprite
     * **/
    protected void createWalls(){
        float wallThickness = 4.0f;
        float posVertical = (bgSprite.getHeight()+wallThickness)/2f;
        float posHorizontal = (bgSprite.getWidth()+wallThickness)/2f;

        //Create walls surrounding the background sprite
        BoxObstacle upperWall = new BoxObstacle(new Vector2(0,posVertical),0
                ,new Vector2(bgSprite.getWidth()+2*wallThickness,wallThickness), world);
        BoxObstacle lowerWall = new BoxObstacle(new Vector2(0,-posVertical),0
                ,new Vector2(bgSprite.getWidth()+2*wallThickness,wallThickness), world);
        BoxObstacle leftWall = new BoxObstacle(new Vector2(-posHorizontal,0),0
                ,new Vector2(wallThickness,bgSprite.getHeight()), world);
        BoxObstacle rightWall = new BoxObstacle(new Vector2(posHorizontal,0),0
                ,new Vector2(wallThickness,bgSprite.getHeight()), world);

        //Place walls in the backLayer
        backLayer.addElement(upperWall.getBody());
        backLayer.addElement(lowerWall.getBody());
        backLayer.addElement(leftWall.getBody());
        backLayer.addElement(rightWall.getBody());
    }

    public Array<Vector2> getPath(){
        return path;
    }

    public Vector2[] getStartLine(){
        return startLine;
    }

    public void addToRenderBatch(SpriteBatch inBatch, Camera camera) {

        inBatch.begin();

        bgSprite.draw(inBatch);

        // Draw sprites that are not connected to a physical body
        for (Sprite sprite : sprites) {
            sprite.draw(inBatch);
        }

        elapsedTime += Gdx.graphics.getDeltaTime();

        // Set shape renderer to be drawn in world, not on screen
        shapeRenderer.setProjectionMatrix(camera.combined);

        drawBodySprites(backLayer,inBatch);
        drawBodySprites(frontLayer,inBatch);

        TextureRegion txtRegion = carAnimation.getKeyFrame(elapsedTime, true);
        Sprite sprite = new Sprite(txtRegion);

        // For the animation, could probably be done in a cleaner way
        sprite.setSize(cars[0].width, cars[0].length);
        sprite.setOriginCenter();
        sprite.setPosition(cars[0].getBody().getPosition().x - sprite.getWidth()/2, cars[0].getBody().getPosition().y - sprite.getHeight()/2);
        sprite.setRotation(cars[0].getBody().getAngle() * MathUtils.radiansToDegrees);
        sprite.draw(inBatch);

        //drawWayPoints();

        inBatch.end();
    }

    /**
     * Draw the sprite of each body in the vector 'bodies'.
     * **/
    private void drawBodySprites(Vector<Body> bodies, SpriteBatch inBatch){
        for (Body body : bodies) {
            if ( body.getUserData() != null && body.getUserData() instanceof Sprite) {
                Sprite sprite = (Sprite) body.getUserData();
                sprite.setPosition(body.getPosition().x - sprite.getWidth()/2, body.getPosition().y - sprite.getHeight()/2);
                sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
                sprite.draw(inBatch);
            }
        }
    }

    private void drawWayPoints(){
        for(int i = 0; i < NUM_INTERPOLATED_POINTS - 1; i++){

            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.line(points[i], points[i+1]);
            shapeRenderer.end();

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.circle(points[i+1].x, points[i+1].y, 0.5f);
            shapeRenderer.end();
        }
    }

    public Car[] getCars(){
        return cars;
    }

    public Pixmap getPixmap(){
        return pixmap;
    }

    //TODO This function might need optimizing... should we really check each car's TD?
    public Car getLeaderCar(){
        leaderCar = cars[0];
        for(int i = 1; i < cars.length ; i++){
           if (cars[i].getTraveledDistance() > leaderCar.getTraveledDistance()){
               leaderCar = cars[i];
           }
        }
        return leaderCar;
    }
}

