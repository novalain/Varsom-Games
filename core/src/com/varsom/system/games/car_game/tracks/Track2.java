package com.varsom.system.games.car_game.tracks;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import com.varsom.system.VarsomSystem;
import com.varsom.system.games.car_game.gameobjects.Car;
import com.varsom.system.games.car_game.gameobjects.TireObstacle;
import com.varsom.system.games.car_game.gameobjects.Wheel;

import com.varsom.system.games.car_game.helpers.AssetLoader;
import com.varsom.system.games.car_game.helpers.InputHandler;
import com.varsom.system.games.car_game.helpers.KrazyRazyCommons;

public class Track2 extends Track{

    //Particleeffects..
    // TODO shouldn't this be within the 'Car' class?
    public ParticleEffect effect;
    // This one is needed if we want to access several layers in our particlesystem
    //public ParticleEmitter emitter;

    public Track2(World inWorld, int NUMBER_OF_PLAYERS,VarsomSystem vs) {
        super(inWorld,new Sprite(AssetLoader.track2Texture),new Sprite(AssetLoader.track2Mask),10f,NUMBER_OF_PLAYERS, vs);
        createTestTrack();
    }

    private void createTestTrack(){
        createObstacles();
        createWayPoints(hardcodedWayPoints(),1);
        createCars();
    }

    // Create objects that are unique for this track
    private void createObstacles(){
        //Static physical objects
        TireObstacle tire  = new TireObstacle(new Vector2(  0.0f, -6.0f), 0, 1.5f, world);
        TireObstacle tire2 = new TireObstacle(new Vector2(  0.0f,  1.6f), 0, 0.5f, world);
        TireObstacle tire3 = new TireObstacle(new Vector2(-13.0f,  0.2f), 0, 0.5f, world);
        TireObstacle tire4 = new TireObstacle(new Vector2( 13.0f,  0.2f), 0, 0.5f, world);

        //Add all newly made obstacles to a layer
        backLayer.addElement(tire.getBody());
        backLayer.addElement(tire2.getBody());
        backLayer.addElement(tire3.getBody());
        backLayer.addElement(tire4.getBody());
    }

    private void createCars() {
        float carWidth = 0.5f, carLength = 1.0f;
        float spawnPosRotation = (float) -Math.PI/2;
        float carPower = 60, maxSteerAngle = 20, maxSpeed = 30;
        int carType = KrazyRazyCommons.CAR;

        //TODO. Fix when the game can be started from the server, DO NOT REMOVE THE COMMENTED FUNCTION

        cars = new Car[NUMBER_OF_PLAYERS];
        for(int i = 0; i < NUMBER_OF_PLAYERS; i++) {
            cars[i] = new Car(carWidth, carLength, hardcodedSpawnPoints()[i], world, null,
                spawnPosRotation, carPower, maxSteerAngle, maxSpeed,this,i, carType);
                Gdx.input.setInputProcessor(new InputHandler(cars[i]));

                //add car to the frontLayer and all its wheels to the backLayer
                for(Wheel tempWheel : cars[i].getWheels()) {
                    backLayer.addElement(tempWheel.body);
                }
                frontLayer.addElement(cars[i].getBody());
        }
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
