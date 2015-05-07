package com.varsom.system.games.car_game.tracks;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import com.varsom.system.VarsomSystem;
import com.varsom.system.games.car_game.gameobjects.BoxObstacle;
import com.varsom.system.games.car_game.gameobjects.Car;
import com.varsom.system.games.car_game.gameobjects.TireObstacle;
import com.varsom.system.games.car_game.gameobjects.Wheel;
import com.varsom.system.games.car_game.helpers.AssetLoader;

/**
 * Created by oskarcarlbaum on 18/03/15.
 */
public class Track1 extends Track{

    //Particleeffects..
    // TODO shouldn't this be within the 'Car' class?
    public ParticleEffect effect;
    // This one is needed if we want to access several layers in our particlesystem
    //public ParticleEmitter emitter;

    public Track1(World inWorld,int NUMBER_OF_PLAYERS,VarsomSystem vS) {
        super(inWorld,new Sprite(AssetLoader.testTrackTexture),new Sprite(AssetLoader.testTrackMask),50f,NUMBER_OF_PLAYERS,vS);
        createTestTrack();
        offTrackSpeed = 0.95f;
    }

    private void createTestTrack(){
        createObstacles();
        createWayPoints(hardcodedWayPoints(),1); //if you change the '1', then you have to implement catmull in Track
        createCars();
    }

    // Create objects that are unique for this track
    private void createObstacles(){
        int hrf = 5; //higherResFactor
      //Static physical objects
        TireObstacle tire  = new TireObstacle(new Vector2(  0.0f, -6.0f), 0, 1.5f, world);
        TireObstacle tire2 = new TireObstacle(new Vector2(  0.0f,  1.6f), 0, 0.5f, world);
        TireObstacle tire3 = new TireObstacle(new Vector2(-13.0f,  0.2f), 0, 0.5f, world);
        TireObstacle tire4 = new TireObstacle(new Vector2( 13.0f,  0.2f), 0, 0.5f, world);

        //HORIZONTAL WALLS
        BoxObstacle bO0 = new BoxObstacle(new Vector2(-12.12f,-11.1f),0,new Vector2(22.25f,0.5f), world);
        BoxObstacle bO1 = new BoxObstacle(new Vector2(10.12f,-11.67f),0,new Vector2(22.25f,0.5f), world);
        BoxObstacle bO2 = new BoxObstacle(new Vector2(-3.05f,12.47f),0,new Vector2(40.5f,0.5f), world);
        BoxObstacle bO3 = new BoxObstacle(new Vector2(26.0f,3.7f),  0, new Vector2(20f,0.5f), world);
        BoxObstacle bO4 = new BoxObstacle(new Vector2(-22.7f,1.4f),0, new Vector2(27.0f,0.5f), world);
        //VERTICAL WALLS
        BoxObstacle bO5 = new BoxObstacle(new Vector2(-1.2f,0.4f), (float) Math.PI/2, new Vector2(24.5f,0.5f), world);
        BoxObstacle bO6 = new BoxObstacle(new Vector2(21.2f,-8.3f),(float) Math.PI/2, new Vector2(7.3f,0.5f), world);
        //ANGELED WALLS
        BoxObstacle bO7 = new BoxObstacle(new Vector2(11.9f,-0.2f),(float) Math.toRadians(43),new Vector2(11.8f,0.5f), world);

        //Add all newly made obstacles to a layer
        backLayer.addElement(tire.getBody());
        backLayer.addElement(tire2.getBody());
        backLayer.addElement(tire3.getBody());
        backLayer.addElement(tire4.getBody());

        backLayer.addElement(bO0.getBody());
        backLayer.addElement(bO1.getBody());
        backLayer.addElement(bO2.getBody());
        backLayer.addElement(bO3.getBody());
        backLayer.addElement(bO4.getBody());
        backLayer.addElement(bO5.getBody());
        backLayer.addElement(bO6.getBody());
        backLayer.addElement(bO7.getBody());
    }

    private void createCars() {
        float carWidth = 0.5f, carLength = 1.0f;
        float spawnPosRotation = (float) -Math.PI/2;
        float carPower = 60, maxSteerAngle = 20, maxSpeed = 25;

        //TODO. Fix when the game can be started from the server, DO NOT REMOVE THE COMMENTED FUNCTION
        cars = new Car[NUMBER_OF_PLAYERS];
        connectionIDs = new int[NUMBER_OF_PLAYERS];
        for(int i = 0; i < NUMBER_OF_PLAYERS; i++) {
            Sprite carSprite;
            switch(i){
                case 0:
                    carSprite = new Sprite(AssetLoader.carTexture1);
                    break;
                case 1:
                    carSprite = new Sprite(AssetLoader.carTexture2);
                    break;
                case 2:
                    carSprite = new Sprite(AssetLoader.carTexture);
                    break;
                case 3:
                    carSprite = new Sprite(AssetLoader.carTexture1);
                    break;
                case 4:
                    carSprite = new Sprite(AssetLoader.carTexture2);
                    break;
                case 5:
                    carSprite = new Sprite(AssetLoader.carTexture);
                    break;
                case 6:
                    carSprite = new Sprite(AssetLoader.carTexture1);
                    break;
                case 7:
                    carSprite = new Sprite(AssetLoader.carTexture2);
                    break;
                default:
                    System.out.println("Mega Error");
                    carSprite = new Sprite(AssetLoader.carTexture2);
            }

            cars[i] = new Car(carWidth, carLength, hardcodedSpawnPoints()[i], world, carSprite,
                spawnPosRotation, carPower, maxSteerAngle, maxSpeed,this,i);

            try {
                connectionIDs[i] = varsomSystem.getServer().getConnections()[NUMBER_OF_PLAYERS-1-i].getID();
                cars[i].setConnectionID(varsomSystem.getServer().getConnections()[NUMBER_OF_PLAYERS-1-i].getID());
                cars[i].setConnectionName(varsomSystem.getServer().getConnections()[NUMBER_OF_PLAYERS-1-i].toString());
            }
            catch(Exception e) {
                System.out.print("FAILED TO ACCESS CONNECTED DEVICE");
            }
          //  sprites.addElement(cars[i].pathTrackingSprite);

            //TODO fix input
             //Gdx.input.setInputProcessor(new InputHandler(cars[i]));

            //add car to the frontLayer and all its wheels to the backLayer
            for(Wheel tempWheel : cars[i].getWheels()) {
                backLayer.addElement(tempWheel.body);
            }
            frontLayer.addElement(cars[i].getBody());
        }

        //TODO Everything in this following block should be deleted/moved/changed when game can be started from the server
        /*    cars = new Car[2];
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
            */
    }

    private Vector2[] hardcodedSpawnPoints() {
        Vector2[] sPs = {
                new Vector2(-17.0f, -14.00f),
                new Vector2(-17.0f, -16.00f),
                new Vector2(-17.0f, -18.00f),
                new Vector2(-16.0f, -15.00f),
                new Vector2(-16.0f, -17.00f),
                new Vector2(-15.0f, -14.00f),
                new Vector2(-15.0f, -16.00f),
                new Vector2(-15.0f, -18.00f)}; //these do not have to be scaled
        return sPs;
    }
    private Vector2[] hardcodedWayPoints() {
        int hrf = 5; // higher res factor
        Vector2[] wPs = {
            new Vector2(-190*hrf, -131*hrf),
            new Vector2(-220*hrf, -131*hrf),
            new Vector2(-230*hrf, -111*hrf),
            new Vector2(-220*hrf,  -90*hrf),
            new Vector2(-110*hrf,  -30*hrf),
            new Vector2( -85*hrf,   11*hrf),
            new Vector2(-110*hrf,   55*hrf),
            new Vector2(-226*hrf,  114*hrf),
            new Vector2(-230*hrf,  126*hrf),
            new Vector2(-226*hrf,  138*hrf),
            new Vector2( 169*hrf,  138*hrf),
            new Vector2( 172*hrf,  127*hrf),
            new Vector2( 158*hrf,   52*hrf),
            new Vector2(  73*hrf,  -30*hrf),
            new Vector2(  73*hrf,  -43*hrf),
            new Vector2(  85*hrf,  -47*hrf),
            new Vector2( 216*hrf,  -40*hrf),
            new Vector2( 239*hrf,  -60*hrf),
            new Vector2( 239*hrf, -110*hrf),
            new Vector2( 210*hrf, -131*hrf),
            new Vector2( -95*hrf, -131*hrf)}; // these need to be scaled by 10
        return wPs;
    }
}
