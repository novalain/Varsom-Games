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
public class Track1 extends Track{

    //Particleeffects..
    // TODO shouldn't this be within the 'Car' class?
    public ParticleEffect effect;
    // This one is needed if we want to access several layers in our particlesystem
    //public ParticleEmitter emitter;

    public Track1(World inWorld) {
        super(inWorld,new Sprite(AssetLoader.testTrackTexture),new Sprite(AssetLoader.testTrackMask),10f);
        createTestTrack();
    }

    private void createTestTrack(){
        createObstacles();
        createWayPoints(hardcodedWayPoints(),1); //if you change the '1', then you have to implement catmull in Track
        createCars();
    }

    // Create objects that are unique for this track
    private void createObstacles(){
      //Static physical objects
        TireObstacle tire  = new TireObstacle(new Vector2(  0.0f, -6.0f), 0, 1.5f, world);
        TireObstacle tire2 = new TireObstacle(new Vector2(  0.0f,  1.6f), 0, 0.5f, world);
        TireObstacle tire3 = new TireObstacle(new Vector2(-13.0f,  0.2f), 0, 0.5f, world);
        TireObstacle tire4 = new TireObstacle(new Vector2( 13.0f,  0.2f), 0, 0.5f, world);

        BoxObstacle bO1 = new BoxObstacle(new Vector2(-8.5f/bgScale,-116.7f/bgScale),0
                ,new Vector2(445/bgScale,15/bgScale), world);
        BoxObstacle bO2 = new BoxObstacle(new Vector2(-30.50f/bgScale,124.7f/bgScale),0
                ,new Vector2(405/bgScale,6/bgScale), world);

        //Add all newly made obstacles to a layer
        backLayer.addElement(tire.getBody());
        backLayer.addElement(tire2.getBody());
        backLayer.addElement(tire3.getBody());
        backLayer.addElement(tire4.getBody());

        backLayer.addElement(bO1.getBody());
        backLayer.addElement(bO2.getBody());
    }

    private void createCars() {
        float carWidth = 0.5f, carLength = 1.0f;
        float spawnPosRotation = (float) -Math.PI/2;
        float carPower = 60, maxSteerAngle = 20, maxSpeed = 30;

        //TODO. Fix when the game can be started from the server, DO NOT REMOVE THE COMMENTED FUNCTION
        /*
        cars = new Car[AMOUNT_OF_PLAYERS];
        for(int i = 0; i < AMOUNT_OF_PLAYERS; i++) {
            cars[i] = new Car(carWidth, carLength, hardcodedSpawnPoints()[i], world, null,
                spawnPosRotation, carPower, maxSteerAngle, maxSpeed,this);
                Gdx.input.setInputProcessor(new InputHandler(cars[i]));

                //add car to the frontLayer and all its wheels to the backLayer
                for(Wheel tempWheel : cars[i].wheels) {
                    backLayer.addElement(tempWheel.body);
                }
                frontLayer.addElement(cars[i].getBody());
        }
        */

        //TODO Everything in this following block should be deleted/moved/changed when game can be started from the server
            cars = new Car[2];
            TextureRegion[] frames = {AssetLoader.tex1, AssetLoader.tex2, AssetLoader.tex3};
            carAnimation = new Animation(1/15f, frames);
            carAnimation.setPlayMode(Animation.PlayMode.LOOP);
            cars[0] = new Car(carWidth, carLength, hardcodedSpawnPoints()[0], world, null,
                    spawnPosRotation, carPower, maxSteerAngle, maxSpeed,this);
            sprites.addElement(cars[0].pathTrackingSprite);
            cars[1] = new Car(carWidth, carLength, hardcodedSpawnPoints()[1],world, new Sprite(AssetLoader.carTexture2),
                    spawnPosRotation, carPower, maxSteerAngle, maxSpeed,this);
            Gdx.input.setInputProcessor(new InputHandler(cars[0]));
            //add car to the frontLayer and all wheels to the backLayer
            for(Wheel tempWheel : cars[0].wheels) {
                backLayer.addElement(tempWheel.body);
            }
            // Set up particlesystem, smoke.p is created in i build-in software in libgdx
            // "Smoke.p" is linked together with a sample particle.png that is found in img folder
            effect = new ParticleEffect();
            effect.load(AssetLoader.particleFile, AssetLoader.particleImg);
            effect.setPosition(cars[0].getBody().getPosition().x, cars[0].getBody().getPosition().y);
            effect.scaleEffect(0.01f);
            effect.start();
    }

    private Vector2[] hardcodedSpawnPoints() {
        Vector2[] sPs = {
                new Vector2(-16.0f, -16.00f),
                new Vector2(-16.0f, -17.50f),
                new Vector2(-16.0f, -19.00f),
                new Vector2(-14.0f, -16.75f),
                new Vector2(-14.0f, -18.25f),
                new Vector2(-12.0f, -16.00f),
                new Vector2(-12.0f, -17.50f),
                new Vector2(-12.0f, -19.00f)};
        return sPs;
    }
    private Vector2[] hardcodedWayPoints() {
        Vector2[] wPs = {
            new Vector2(-170, -131),
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
        return wPs;
    }
}
