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
import com.varsom.system.games.car_game.helpers.KrazyRazyCommons;

public class Track1 extends Track{

    //float TURTLE_WIDTH = 1.5f;

    //Particleeffects..
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

        // Create and set up firepit
        ParticleEffect firePit = new ParticleEffect();
        firePit.load(AssetLoader.firepitFile, AssetLoader.particleImg);
        firePit.setPosition(471 / 50.f, 842 / 50.f);
        firePit.scaleEffect(0.008f);
        firePit.start();

        particleEffectsInTrack.addElement(firePit);

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
        int carType = KrazyRazyCommons.CAR;

        //TODO. Fix when the game can be started from the server, DO NOT REMOVE THE COMMENTED FUNCTION
        cars = new Car[NUMBER_OF_PLAYERS];
        connectionIDs = new int[NUMBER_OF_PLAYERS];
        for(int i = 0; i < NUMBER_OF_PLAYERS; i++) {
            Sprite carSprite;
            switch(i){
                case 0:
                    carSprite = new Sprite(AssetLoader.carTextureTurtle);
                    carWidth = 1.2f;
                    carType = KrazyRazyCommons.TURTLE;
                    break;
                case 1:
                    carSprite = new Sprite(AssetLoader.carTextureHotdog);
                    carWidth = 0.6f;
                    carType = KrazyRazyCommons.HOTDOG; // carType doesn't do anything in this case
                    break;
                case 2:
                    carSprite = new Sprite(AssetLoader.carTextureCoffin);
                    carWidth = 0.5f;
                    carType = KrazyRazyCommons.COFFIN;
                    break;
                case 3:
                    carSprite = new Sprite(AssetLoader.carTexturePiggelin);
                    carWidth = 0.6f;
                    carType = KrazyRazyCommons.AMBULANCE;
                    break;
                // Other colors
                case 4:
                    carSprite = new Sprite(AssetLoader.carTextureTurtle2);
                    carWidth = 1.2f;
                    carType = KrazyRazyCommons.TURTLE;
                    break;
                case 5:
                    carSprite = new Sprite(AssetLoader.carTextureHotdog2);
                    carWidth = 0.6f;
                    carType = KrazyRazyCommons.HOTDOG;
                    break;
                case 6:
                    carSprite = new Sprite(AssetLoader.carTextureCoffin2);
                    carWidth = 0.5f;
                    carType = KrazyRazyCommons.COFFIN;
                    break;
                case 7:
                    carSprite = new Sprite(AssetLoader.carTexturePiggelin2);
                    carWidth = 0.6f;
                    carType = KrazyRazyCommons.PIGGELIN;
                    break;
                default:
                    System.out.println("Mega Error");
                    carSprite = new Sprite(AssetLoader.carTextureOld);
                    carWidth = 0.5f;
                    carType = KrazyRazyCommons.CAR;
            }

            cars[i] = new Car(carWidth, carLength, hardcodedSpawnPoints()[i], world, carSprite,
                spawnPosRotation, carPower, maxSteerAngle, maxSpeed,this,i, carType);



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
            //if hte car is a turtle we don't want wheels
            //TODO gör huvudet till wheel ??
            if(cars[i].getCarType() != KrazyRazyCommons.TURTLE) {
                for (Wheel tempWheel : cars[i].getWheels()) {

                    backLayer.addElement(tempWheel.body);
                }
            }
            frontLayer.addElement(cars[i].getBody());
        }

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
