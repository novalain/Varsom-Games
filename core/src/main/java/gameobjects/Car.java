package gameobjects;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import abstract_gameobjects.DynamicObject;
import helpers.AssetLoader;
import screens.GameScreen;
import tracks.TestTrack;

public class Car extends DynamicObject {
    //public Body body;
    public float width, length, maxSteerAngle, maxSpeed, power;
    float wheelAngle;
    public boolean smoke = false;
    public int steer, accelerate;
    //public Vector2 position;
    public List<Wheel> wheels;
    private TextureRegion wheelTexture;

    private float accelX, accelY, tiltAngle, steeringSensitivity;

    //For track tracking ;)
    private Vector2[] currentLine;
    private Vector2 pointOnTrack;
    private float traveledDistance;
    private float delta; //distance traveled since last frame
    private int waypoint;
    private TestTrack track;
    public Sprite pathTrackingSprite;
    private Vector2 lineToCar, lineToEnd;
    private float angleOfCurrentLine;

    public Car(float width, float length, Vector2 position, World world, Sprite carSprite, float angle, float power, float maxSteerAngle, float maxSpeed,TestTrack inTrack) {
        super(position, angle, new PolygonShape(), carSprite, world);
        this.steer = GameScreen.STEER_NONE;
        this.accelerate = GameScreen.ACC_NONE;
        this.width = width;
        this.length = length;
        this.maxSteerAngle = maxSteerAngle;
        this.maxSpeed = maxSpeed;
        this.power = power;
        this.wheelAngle = 0;
        this.steeringSensitivity = 0.4f; // a value less than 1 makes ta car less sensitive to device rotation
        track = inTrack;
        //init body
       /* BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position);*/
        bodyDef.angle = angle;
        //body = world.createBody(bodyDef);

        //init shape
        // FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.6f; //friction when rubbing against other shapes
        fixtureDef.restitution = 0.4f; //amount of force feedback when hitting something. >0 makes the car bounce off, it's fun!
        //PolygonShape carShape = new PolygonShape();
        //we have to parse the Shape to a PolygonShape in order to access the .setAsBox method
        ((PolygonShape) shape).setAsBox(width / 2f, length / 2f);
        //carShape.setAsBox(this.width / 2, this.length / 2);
        //fixtureDef.shape = carShape;
        fixtureDef.shape = shape;
        //this.body.createFixture(fixtureDef);

        addObjectToWorld();
        //initialize wheels
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

        //TODO Need to create a Class called Track. shouldn't be TestTrack below, should be something like GameScreen.getTrack().

        currentLine = new Vector2[2];
        lineToCar = new Vector2();
        lineToEnd =new Vector2();
        currentLine = track.getStartLine();
        pointOnTrack = getNewPointOnTrack();
        traveledDistance = pointOnTrack.sub(currentLine[0]).len();
        waypoint = 1;
       /* angleOfCurrentLine = (float) Math.atan2(currentLine[1].y - pointOnTrack.y,
                currentLine[0].x - pointOnTrack.x);
*/
        angleOfCurrentLine = -angle;
        pathTrackingSprite = new Sprite(AssetLoader.pathCircleTexture);
        pathTrackingSprite.setSize(length,length);
        pathTrackingSprite.setPosition(pointOnTrack.x, pointOnTrack.y);
        pathTrackingSprite.setRotation((float)Math.toDegrees(angleOfCurrentLine));
        pathTrackingSprite.setOriginCenter();
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
	    /*
	    returns car's velocity vector relative to the car
	    */
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

    public void setSpeed(float speed) {
	    /*
	    speed - speed in kilometers per hour
	    */
        Vector2 velocity = this.body.getLinearVelocity();
        velocity = velocity.nor();
        velocity = new Vector2(velocity.x * ((speed * 1000.0f) / 3600.0f),
                velocity.y * ((speed * 1000.0f) / 3600.0f));
        this.body.setLinearVelocity(velocity);
    }

    public void update(float deltaTime) {

        //1. KILL SIDEWAYS VELOCITY

        for (Wheel wheel : wheels) {
            wheel.killSidewaysVelocity();

        }

        //2. SET WHEEL ANGLE

        //calculate the change in wheel's angle for this update
        /*float incr=(this.maxSteerAngle) * deltaTime * 5;

        if(this.steer==GameScreenWPhysics.STEER_LEFT){
            this.wheelAngle=Math.min(Math.max(this.wheelAngle, 0)+incr, this.maxSteerAngle); //increment angle without going over max steer
        }else if(this.steer==GameScreenWPhysics.STEER_RIGHT){
            this.wheelAngle=Math.max(Math.min(this.wheelAngle, 0)-incr, -this.maxSteerAngle); //decrement angle without going over max steer
        }else{
            this.wheelAngle=0;
        }*/

        //update revolving wheels based on angle
        updateDeviceRotation();
        for (Wheel wheel : this.getRevolvingWheels()) {
            //wheel.setAngle( - Gdx.input.getAccelerometerY()*10);
            wheel.setAngle(tiltAngle);
        }

        //3. APPLY FORCE TO WHEELS
        Vector2 baseVector; //vector pointing in the direction force will be applied to a wheel ; relative to the wheel.

        //if accelerator is pressed down and speed limit has not been reached, go forwards
        if ((this.accelerate == GameScreen.ACC_ACCELERATE) && (this.getSpeedKMH() < this.maxSpeed)) {

            // Add smoke effect for low velocities
            if (this.getSpeedKMH() < 10.f)
                smoke = true;

            else
                smoke = false;

            baseVector = new Vector2(0, -0.05f);
        } else if (this.accelerate == GameScreen.ACC_BRAKE) {
            //braking, but still moving forwards - increased force

            smoke = true;

            if (this.getLocalVelocity().y < 0) {
                baseVector = new Vector2(0f, 0.05f);
            }
            //going in reverse - less force
            else
                baseVector = new Vector2(0f, 0.05f);
        } else if (this.accelerate == GameScreen.ACC_NONE) {

            //slow down if not accelerating
            baseVector = new Vector2(0, 0);
            if (this.getSpeedKMH() < 7)
                this.setSpeed(0);
            else if (this.getLocalVelocity().y < 0)
                baseVector = new Vector2(0, 0.05f);
            else if (this.getLocalVelocity().y > 0)
                baseVector = new Vector2(0, -0.05f);
            smoke = false;

        } else {
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

        //System.out.println("Car Speed: " + this.getSpeedKMH());
        //if going very slow, stop - to prevent endless sliding

        //Track point position and traveledDistance
        //delta = (new Vector2(currentLine[1].x-getNewPointOnTrack().x,currentLine[1].y-getNewPointOnTrack().y)).len();
        delta = (new Vector2(pointOnTrack.x-getNewPointOnTrack().x,pointOnTrack.y-getNewPointOnTrack().y)).len();
        if(waypointIsReached()){
            pointOnTrack = currentLine[1];
            currentLine[0] = currentLine[1];
            if(waypoint + 1 >= track.getPath().size){
                waypoint = 0;
            }
            else {
                waypoint++;
            }
            currentLine[1] = track.getPath().get(waypoint);
            traveledDistance += delta; //this is probably slightly wrong
        }
        else{
            //if the previous pointOnTrack were closer to the end point, delta should be negative. Car is going the wrong way
            if ((new Vector2(currentLine[1].x-pointOnTrack.x,currentLine[1].y-pointOnTrack.y)).len() < delta) {
                delta *= -1;
            }
            pointOnTrack = getNewPointOnTrack();
            traveledDistance += delta;
        }
        if((new Vector2(currentLine[1].x-pointOnTrack.x, currentLine[1].y-pointOnTrack.y).len() > (new Vector2(currentLine[1].x-currentLine[0].x, currentLine[1].y-currentLine[0].y)).len())){
            pointOnTrack = currentLine[0];
        }
        angleOfCurrentLine = (float) Math.atan2(currentLine[1].y - pointOnTrack.y,
                currentLine[1].x - pointOnTrack.x);
        pathTrackingSprite.setPosition(pointOnTrack.x, pointOnTrack.y);
        pathTrackingSprite.setRotation((float)Math.toDegrees(angleOfCurrentLine));
        pathTrackingSprite.setOriginCenter();
        //Gdx.app.log("CarUpdate","pointOnTrack = " + pointOnTrack + "\tangleOfCurrentLine = " + angleOfCurrentLine + "\n";
        //Gdx.app.log("CarUpdate","waypoint = " + waypoint + "\tNumOfWPs = " + track.getPath().size + "\n");
    }

    /**
     * Get the device's acceleration in x & y axis,
     * and return the tilt angle from the horizontal axis
     */
    private void updateDeviceRotation() {
        accelX = Gdx.input.getAccelerometerX();
        accelY = Gdx.input.getAccelerometerY();

        tiltAngle = (float) Math.atan2(accelX, accelY);
        tiltAngle -= Math.PI / 2;
        tiltAngle *= steeringSensitivity;
    }

    private Vector2 getNewPointOnTrack() {
        lineToCar = new Vector2(getPosition().x-currentLine[0].x,getPosition().y-currentLine[0].y); //Vector from the currentLine's start point to the car's position
        lineToEnd = new Vector2(currentLine[1].x-currentLine[0].x,currentLine[1].y-currentLine[0].y); // the vector of the currentLine
        Vector2 temp = new Vector2();
        temp.x=currentLine[0].x;
        temp.y=currentLine[0].y;
        temp.add(lineToEnd.scl(lineToCar.dot(lineToEnd) / lineToEnd.len2()));
        return temp;
    }

    public Vector2 getPointOnTrack() {
        return pointOnTrack;
    }
    public float getRotationTrack() {
        return angleOfCurrentLine;
    }

    public boolean waypointIsReached(){
        return (Math.abs(currentLine[1].x - pointOnTrack.x) <= delta && Math.abs(currentLine[1].y - pointOnTrack.y) <= delta);
    }
}