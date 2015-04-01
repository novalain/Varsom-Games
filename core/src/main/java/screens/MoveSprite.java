package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import gameobjects.tempCar;

/**
 * Created by Alice on 2015-03-18.
 */
public class MoveSprite extends Sprite {
    private Vector2 velocity = new Vector2();
    private float speed = 5.0f;

    private Array<Vector2> path;
    private int waypoint = 0;
    private tempCar leaderCar;



    public MoveSprite(Sprite sprite, Array<Vector2> path){
        super(sprite);
        this.setOriginCenter();
        this.path = path;
        setPosition(this.path.get(0).x,this.path.get(0).y);

    }

    @Override
    public void draw(Batch batch) {
        update(Gdx.graphics.getDeltaTime());
        super.draw(batch);
    }

    public void update(float delta){
        // atan(distance from current position to waypoint position - y,x)
        // angle is returned in radianes
        float angle = (float) Math.atan2(path.get(waypoint).y - getY(), path.get(waypoint).x - getX());
        velocity.set((float) Math.cos(angle) * speed, (float) Math.sin(angle) * speed);
        setPosition(getX() + (velocity.x * delta), getY() + (velocity.y*delta));

//        String message = "\nAngle: " + Math.toDegrees(angle) +
//                       "\nCurrent pos: ( " + getX() + " , " + getY() + " )\n"+
//                         "Wanted  pos: ( " + path.get(waypoint).x + " , " + path.get(waypoint).y + " )\n";
       // Gdx.app.log("in MoveSprite", message);
            //Gdx.app.log("in MoveSprite", "angle: " + path.get(waypoint).y + ",   getY: " + getY());
            //Gdx.app.log("in MoveSprite", "x: "+velocity.x + ",   y: " + velocity.y);

        // mathutils and redDeg converts from radianes to degrees
        //setRotation(angle * MathUtils.radDeg);
        //setRotation((float)Math.toDegrees(angle));
        this.setRotation((float)Math.toDegrees(angle));

        if(waypointIsReached()){
            //System.out.println(waypoint);
            //System.out.println(path.size);
            setPosition(path.get(waypoint).x, path.get(waypoint).y);
            if(waypoint + 1 >= path.size){
                waypoint = 0;
            }
            else {
                waypoint++;
            }

        }

    }
    public boolean waypointIsReached(){
        return (Math.abs(path.get(waypoint).x - getX()) <= speed * Gdx.graphics.getDeltaTime() && Math.abs(path.get(waypoint).y - getY()) <= speed * Gdx.graphics.getDeltaTime()) ;
        /*if(Math.abs(path.get(waypoint).x - getX()) <= speed * Gdx.graphics.getDeltaTime() && Math.abs(path.get(waypoint).y - getY()) <= speed * Gdx.graphics.getDeltaTime()) {
           // Gdx.app.log("in MoveSprite", "angle: " + path.get(waypoint).y + ",   getY: " + getY());
            return true;
        }
        else {
            return false;
        }*/
    }

    public Array<Vector2> getPath() {
        return path;
    }
    public int getWaypoint(){
        return waypoint;
    }
}