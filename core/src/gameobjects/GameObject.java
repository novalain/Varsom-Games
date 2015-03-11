package gameobjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Alice on 2015-03-11.
 */
public abstract class GameObject {
    private Vector2 position;
    private Vector2 velocity;
    private Rectangle hitBox;
//  private Vector2 acceleration;
//  private float rotation;
//  Add Bitmap ?? Spritebatch or whatever it is called in libgdx?

    public GameObject(Vector2 position, Vector2 velocity, Rectangle hitBox){
        this.position = position;
        this.velocity = velocity;
        this.hitBox = hitBox;
    }

    // Get functions
    public Vector2 getPosition(){
        return position;
    }
    public Vector2 getVelocity(){
        return velocity;
    }
    public Rectangle getObjectHitbox(){
        return hitBox;
    }

    // Set functions
    public void setPosition(Vector2 position){
        this.position = position;
    }
    public void setVelocity(Vector2 vel){
        this.velocity = vel;
    }
    public void setObjectHitbow(Rectangle objectHitbox){
        this.hitBox = objectHitbox;
    }

    // Draw onto canvas here?
    public void draw(){


    }

}
