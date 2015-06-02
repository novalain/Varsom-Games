package com.varsom.system.games.car_game.gameobjects;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import com.badlogic.gdx.utils.Array;
import com.varsom.system.games.car_game.abstract_gameobjects.DynamicObject;
import com.varsom.system.games.car_game.helpers.AssetLoader;
import com.varsom.system.games.car_game.helpers.WaypointHandler;
import com.varsom.system.games.car_game.screens.GameScreen;
//import tracks.TestTrack;
import com.varsom.system.games.car_game.tracks.Track;

public class Car extends DynamicObject implements Comparable<Car> {

    public static final float SMOKE_SCALE = 0.008f;

    private float width, length, maxSteerAngle, maxSpeed, power, wheelAngle;
    private boolean userAccelerate = false, userBreaking = false;
    private boolean smoke = false;
    private List<Wheel> wheels;
    private TextureRegion wheelTexture;

    private float tiltAngle, steeringSensitivity;
    private Track track;
    private WaypointHandler wpHandler;
    private Vector2 pointOnTrack;
    //temp
    private int ID,connectionID;
    private String connectionName;
    private boolean active;
    public Sprite pathTrackingSprite;
    private int carType;

    // Particleeffects
    public ParticleEffect smokeParticles;

    public Car(float width, float length, Vector2 position, World world, Sprite carSprite, float angle, float power, float maxSteerAngle, float maxSpeed,Track inTrack,int ID/*,int conID*/, int carType) { //TODO remove the comment before game is finished.. lolzzz
        super(position, angle, new PolygonShape(), carSprite, world);

        this.width = width;
        this.length = length;
        this.maxSteerAngle = maxSteerAngle;
        this.maxSpeed = maxSpeed;
        this.power = power;
        this.wheelAngle = 0;
        this.steeringSensitivity = 0.36f; // a value less than 1 makes ta car less sensitive to device rotation
        this.ID = ID; //temp.. i think..
        this.active = true;
        this.track = inTrack;
        this.carType = carType;

        //TODO this should be uncommented, but for developing purposes it could be beneficial to use the setConnectionID funciton instead
        //connectionID = conID

        bodyDef.angle = angle; //start angle

        //init shape
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.6f; //friction when rubbing against other shapes
        fixtureDef.restitution = 0.4f; //amount of force feedback when hitting something. >0 makes the car bounce off, it's fun!

        //we have to parse the Shape to a PolygonShape in order to access the .setAsBox method
        ((PolygonShape) shape).setAsBox(width / 2f, length / 2f);

        fixtureDef.shape = shape;

        addObjectToWorld();

        //WHEELS
        float wheelWidth = width * 0.3f, wheelHeight = length * 0.3f;
        this.wheels = new ArrayList<Wheel>();
        this.wheels.add(new Wheel(world, this, -width / 2.5f, -length / 3, wheelWidth, wheelHeight, true, true)); //top left
        this.wheels.add(new Wheel(world, this,  width / 2.5f, -length / 3, wheelWidth, wheelHeight, true, true)); //top right
        this.wheels.add(new Wheel(world, this, -width / 2.5f,  length / 3, wheelWidth, wheelHeight, false, false)); //back left
        this.wheels.add(new Wheel(world, this,  width / 2.5f,  length / 3, wheelWidth, wheelHeight, false, false)); //back right

        if (sprite != null) {
            sprite.setSize(width, length);
            sprite.setOriginCenter();
        }

        wpHandler = new WaypointHandler(track,position);
        pointOnTrack = wpHandler.getProjectionPoint(position);

        pathTrackingSprite = new Sprite(AssetLoader.pathCircleTexture);
        pathTrackingSprite.setSize(length,length);
        pathTrackingSprite.setPosition(pointOnTrack.x, pointOnTrack.y);
        pathTrackingSprite.setRotation((float)Math.toDegrees(wpHandler.getCurrentLineAngle()));
        pathTrackingSprite.setOriginCenter();

        // Set up particlesystem, smoke.p is created in i build-in software in libgdx
        // "Smoke.p" is linked together with a sample particle.png that is found in img folder
        smokeParticles = new ParticleEffect();
        smokeParticles.load(AssetLoader.particleFile, AssetLoader.particleImg);
        smokeParticles.setPosition(getBody().getPosition().x, getBody().getPosition().y);
        smokeParticles.scaleEffect(SMOKE_SCALE);
        //ParticleEmitter emitter = smokeParticles.getEmitters().first();
        //emitter.getScale().setHigh(5, 20);

        smokeParticles.start();

    }

    public List<Wheel> getPoweredWheels() {
        List<Wheel> poweredWheels = new ArrayList<Wheel>();
        for (Wheel wheel : this.wheels) {
            if (wheel.powered)
                poweredWheels.add(wheel);
        }
        return poweredWheels;
    }

    public Vector2 getLocalVelocity() {

	    //returns car's velocity vector relative to the car
        return this.body.getLocalVector(this.body.getLinearVelocityFromLocalPoint(new Vector2(0, 0)));

    }

    public List<Wheel> getRevolvingWheels() {
        List<Wheel> revolvingWheels = new ArrayList<Wheel>();
        for (Wheel wheel : this.wheels) {
            if (wheel.revolving)
                revolvingWheels.add(wheel);
        }
        return revolvingWheels;
    }

    public float getSpeedKMH() {
        Vector2 velocity = this.body.getLinearVelocity();
        float len = velocity.len();
        return (len / 1000) * 3600;
    }

    // Speed in km/h
    public void setSpeed(float speed) {
        Vector2 velocity = this.body.getLinearVelocity();
        velocity = velocity.nor();
        velocity = new Vector2(velocity.x * ((speed * 1000.0f) / 3600.0f),
                velocity.y * ((speed * 1000.0f) / 3600.0f));
        this.body.setLinearVelocity(velocity);
    }


    public void handleDataFromClients(boolean isDriving, boolean isBreaking, float angle){

       userAccelerate = isDriving;
       userBreaking = isBreaking;
       tiltAngle = angle*steeringSensitivity;

    }

    public void update(float deltaTime) {

        if(!active){
            userAccelerate = false;
            userBreaking = false;
        }
        //1. KILL SIDEWAYS VELOCITY
        for (Wheel wheel : wheels) {
            wheel.killSidewaysVelocity();

        }

        //2. SET WHEEL ANGLE
        // update revolving wheels based on angle
        for (Wheel wheel : this.getRevolvingWheels()) {
            wheel.setAngle(tiltAngle);
        }

        //3. APPLY FORCE TO WHEELS
        Vector2 baseVector; //vector pointing in the direction force will be applied to a wheel ; relative to the wheel.

        //if accelerator is pressed down and speed limit has not been reached, go forwards
        if (userAccelerate && (this.getSpeedKMH() < this.maxSpeed)) {

            // Add smoke effect for low velocities
            if (this.getSpeedKMH() < 10.f){
                smoke = true;
            }

            else{
                smoke = false;
            }

            baseVector = new Vector2(0, -0.05f);
        }

        else if(userBreaking){

            if (this.getLocalVelocity().y < 0) {
                baseVector = new Vector2(0f, 0.05f);
            }

            //going in reverse - less force
            else{
                baseVector = new Vector2(0f, 0.03f);
            }

        }

        // Slow down if not accelerating
        else if (!userAccelerate) {

            baseVector = new Vector2(0, 0);
            if (this.getSpeedKMH() < 1)
                this.setSpeed(0);
            else if (this.getLocalVelocity().y < 0)
                baseVector = new Vector2(0, 0.03f);
            else if (this.getLocalVelocity().y > 0)
                baseVector = new Vector2(0, -0.03f);
            smoke = false;
        }

        // Max speed reached, just keep going
        else {
            baseVector = new Vector2(0, 0);
            smoke = false;
        }

        //multiply by engine power, which gives us a force vector relative to the wheel
        Vector2 forceVector = new Vector2(this.power * baseVector.x, this.power * baseVector.y);

        //apply force to each wheel
        for (Wheel wheel : this.getPoweredWheels()) {
            Vector2 position = wheel.body.getWorldCenter();
            wheel.body.applyForce(wheel.body.getWorldVector(new Vector2(forceVector.x, forceVector.y)), position, true);
        }

        //update the projection on the way point lines
        wpHandler.getProjectionPoint(getPosition());
        pathTrackingSprite.setPosition(pointOnTrack.x, pointOnTrack.y);
        pathTrackingSprite.setRotation((float)Math.toDegrees(wpHandler.getCurrentLineAngle()));
        pathTrackingSprite.setOriginCenter();

        setCarSpeed();

        smokeParticles.setPosition(getBody().getPosition().x + length / 2 * getBody().getWorldVector(new Vector2(0, 1)).x, getBody().getPosition().y + length / 2 * getBody().getWorldVector(new Vector2(0, 1)).y);

        if(smoke){
            if(smokeParticles.isComplete()){
                smokeParticles.reset();
            }
        }


    }

    public ParticleEffect getSmokeEffect(){

        return smokeParticles;

    }

    public Vector2 getPointOnTrack() {
        return pointOnTrack;
    }

    public Vector2 getOffsetPoint(float dist) {
        return wpHandler.getOffsetPoint(dist);
        //return wpHandler.getOffsetPositionOnCurrentLine(pointOnTrack, dist);
    }

    public float getRotationTrack() {
        return wpHandler.getCurrentLineAngle();
    }

    // Sets speed on car based on value from color on backgroundMask (black or white),
    public void setCarSpeed(){
        // Gets value from pixmap, cars position in Box2D coord system is mapped onto the coordinate system of the mask (y down , x right).
        int valueFromMask = track.getPixmap().getPixel( (int) (track.getPixmap().getWidth() / 2 + getBody().getPosition().x*track.getBgScale()), (int)(track.getPixmap().getHeight()/2 - getBody().getPosition().y*track.getBgScale()));
        
        // If car is not on track
        if(valueFromMask != -1){
            //System.out.println("offTrack");
            this.setSpeed(this.getSpeedKMH() * track.getOffTrackSpeed());
            if(getSpeedKMH() != 0){
                track.getVarsomSystem().getMPServer().vibrateClient(10,connectionID);
            }
        }

    }

    public float getTraveledDistance(){
        return wpHandler.getTraveledDistance();
    }

    public boolean isActive(){
        return active;
    }

    public void setActive(boolean b){
        active = b;
    }

    public int getID(){
        return ID;
    }
    public List<Wheel> getWheels(){

        return wheels;

    }

    public void setSteeringSensitivity(float sens){
        steeringSensitivity = sens;
    }

    public void setConnectionID(int id){
        connectionID = id;
    }

    public int getConnectionID(){
        return connectionID;
    }

    public int compareTo(Car c) {
        return Float.compare(c.getTraveledDistance(),getTraveledDistance());
    }

    public void setConnectionName(String s){
        connectionName = s;
    }

    public String getConnectionName(){
        return connectionName;
    }

    public int getCarType() { return carType; }

    public void setTraveledDistance(float dist) {
        wpHandler.setTraveledDistance(dist);
    }

    public int getCurrentLap() {
        return wpHandler.getCurrentLap();
    }
}