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

import com.varsom.system.VarsomSystem;
import com.varsom.system.games.car_game.gameobjects.BoxObstacle;
import com.varsom.system.games.car_game.gameobjects.Car;
import com.varsom.system.games.car_game.helpers.AssetLoader;

/**
 * Created by oskarcarlbaum on 15/04/15.
 */
public abstract class Track {

    protected float offTrackSpeed;

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
    private float trackLength;

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
    protected int[] connectionIDs;
    protected VarsomSystem varsomSystem;

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
    protected Track(World world,Sprite backgroundSprite, Sprite backgroundMask, float backgroundScale,int NUMBER_OF_PLAYERS,VarsomSystem vS) {
        varsomSystem = vS;
        bgSprite = backgroundSprite;
        bgMask = backgroundMask;
        bgScale = backgroundScale;
        this.world = world;
        this.NUMBER_OF_PLAYERS = NUMBER_OF_PLAYERS;
        sprites = new Vector<Sprite>();
        frontLayer = new Vector<Body>();
        backLayer = new Vector<Body>();
        createBackground();
    }

    protected void createBackground() {

        bgSprite.setSize(bgSprite.getWidth()/bgScale,bgSprite.getHeight()/bgScale);
        bgSprite.setPosition(-bgSprite.getWidth()/2,-bgSprite.getHeight()/2);

        bgMask.getTexture().getTextureData().prepare();
        pixmap = bgMask.getTexture().getTextureData().consumePixmap();

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


        // Allocate number of total points
        points = new Vector2[NUM_INTERPOLATED_POINTS];

        // Calculate the new interpolated points
        for(int i = 0; i < NUM_INTERPOLATED_POINTS; i++)
        {
            points[i] = waypoints[i];
        }

        // Same as before
        for(int i = 0 ; i < NUM_INTERPOLATED_POINTS; i++){
            path.add(points[i]);
        }

        startLine = new Vector2[]{path.get(0), path.get(1)};
        Gdx.app.log("StartLine", "startLine[0]: (" + startLine[0].toString() + "), startLine[1] = (" + startLine[1].toString() + ")");
        calcTrackLength(path);
    }

    /**
     * Creates walls around the background sprite
     * **/
    protected void createWalls(){
        float wallThickness = 4.0f;
        float posVertical = (bgSprite.getHeight()+wallThickness)/2f;
        float posHorizontal = (bgSprite.getWidth()+wallThickness)/2f;

        //Create walls surrounding the background sprite
        BoxObstacle upperWall = new BoxObstacle(new Vector2(0,posVertical), 0,new Vector2(bgSprite.getWidth()+2*wallThickness,wallThickness), world);
        BoxObstacle lowerWall = new BoxObstacle(new Vector2(0,-posVertical),0,new Vector2(bgSprite.getWidth()+2*wallThickness,wallThickness), world);
        BoxObstacle leftWall  = new BoxObstacle(new Vector2(-posHorizontal,0),(float) Math.PI/2,new Vector2(bgSprite.getHeight(),wallThickness), world);
        BoxObstacle rightWall = new BoxObstacle(new Vector2(posHorizontal,0) ,(float) Math.PI/2,new Vector2(bgSprite.getHeight(),wallThickness), world);

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

        // Draw smoke effects
        for(int i = 0; i < cars.length; i++){

            cars[i].getSmokeEffect().draw(inBatch, Gdx.graphics.getDeltaTime());

        }


        // Draw sprites that are not connected to a physical body
        for (Sprite sprite : sprites) {
            sprite.draw(inBatch);
        }

        elapsedTime += Gdx.graphics.getDeltaTime();

        drawBodySprites(backLayer,inBatch);
        drawBodySprites(frontLayer,inBatch);

        // Set shape renderer to be drawn in world, not on screen
        shapeRenderer.setProjectionMatrix(camera.combined);
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
            shapeRenderer.circle(points[i].x, points[i].y, 0.5f);
            shapeRenderer.end();
        }
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.circle(points[points.length-1].x, points[points.length-1].y, 0.5f);
        shapeRenderer.end();
    }

    public Car[] getCars(){
        return cars;
    }
    public int getNoOfPlayers() { return NUMBER_OF_PLAYERS; }

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

    public float getOffTrackSpeed(){

        return offTrackSpeed;

    }

    public float getBgScale(){
        return bgScale;
    }

    public Sprite getBgSprite() {

        return bgSprite;

    }
    public VarsomSystem getVarsomSystem() {
        return varsomSystem;
    }

    public Car getCarByConnectionID (int conID) {
        int i = 0;
        for( ; i < NUMBER_OF_PLAYERS; i++) {
            try {
                if (connectionIDs[i] == conID) {
                    return cars[i];
                }
            } catch (Exception e) {
                //System.out.println("No connection");

            }

        }

        System.out.println("ERRRRROOOORRR: There's something wrong in the getCarByConnectionID function");
        System.out.println("The connectionID is probably wrong! Some car might be without a controlling device");
        return cars[i];
    }

    private void calcTrackLength(Array<Vector2> p){
        trackLength = 0;
        int i = 0;
        while(i < p.size-1){
            trackLength += lengthBetweenPoints(p.get(i),p.get(i+1));
            i++;
        }
        trackLength += lengthBetweenPoints(p.get(i),p.get(0));
        System.out.println("TRACK LENGTH IS: " + trackLength);
    }

    private float lengthBetweenPoints(Vector2 a, Vector2 b) {
        return (float) Math.sqrt(Math.pow(a.x-b.x,2) + Math.pow(a.y-b.y,2));
    }

    public float getTrackLength(){
        return trackLength;
    }

}



