package gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;


/**
 * Created by michaelnoven on 15-03-18.
 */
public class tempCar extends DynamicObject{

    private Vector2 size, direction;
    private float width, height;
    private float accelX, accelY, tot;

    private final float MAX_TURNING_ANGLE = 1.134f;

    public tempCar(Vector2 inPosition, Vector2 inSize,World inWorld){
        super(inPosition,new PolygonShape(),new Sprite(new Texture("img/Car.png")),inWorld);
        size = inSize;
        width = inSize.x;
        height = inSize.y;

        //we have to parse the Shape to a PolygonShape in order to access the .setAsBox method
        ((PolygonShape) shape).setAsBox(width/2f,height/2f);

        sprite.setSize(width, height);
        //sprite.setOriginCenter();
        sprite.setOrigin(width/2,height/4);
        bodyDef.position.set(width/2,-height/2);
        fixtureDef.shape = shape;
        fixtureDef.density = 2.0f;
        fixtureDef.friction = 0.5f;     //value between 0-1
        fixtureDef.restitution = 0.2f; //value between 0-1
        addObjectToWorld();
        shape.dispose();
    }

    public void onTouchDown(){
        //body.applyForceToCenter(direction,true);
/*
        Gdx.app.log("car", "touched");


       // body.getLocalVector()
       // body.
       // direction = body.getWorldVector(new Vector2(0, 1));
        //body.applyForceToCenter(-100, 0, true);
        int q = 1000;
        direction.x *= q;
        direction.y *= q;
        body.applyForceToCenter(direction,true);
*/
    }

    public void getDeviceAccleration() {
        accelX = Gdx.input.getAccelerometerX();
        accelY = Gdx.input.getAccelerometerY();

        tot = (float) Math.atan2(accelX, accelY);
        tot -= Math.PI /2;
        Gdx.app.log("inputhandler", "" + tot);
        turnCar();
    }

    private void turnCar() {

        float q = 0.1f;
        Vector2 move = body.getWorldVector(new Vector2(0,0.1f));
        Vector2 newPos = new Vector2 (body.getPosition().x+move.x,body.getPosition().y+move.y);
        float turningAngle = Math.abs(tot)*q*(float)Math.sin(tot);
        if (Math.abs(turningAngle) > MAX_TURNING_ANGLE) {
            if(turningAngle < 0) {
                turningAngle = MAX_TURNING_ANGLE;
            }
            else {
                turningAngle = -MAX_TURNING_ANGLE;
            }
        }


        body.setTransform(newPos, body.getAngle() + turningAngle);
        direction = body.getWorldVector(new Vector2( 0, 1));
        //direction.x *= q;
        //direction.y *= q;

       // Gdx.app.log("inputhandler", "X-led: " + Math.cos(tot) + "    Y-led: " + Math.sin(tot));
    }

}
