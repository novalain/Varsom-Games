package gameobjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Alice on 2015-03-11.
 */
public abstract class GameObject {
    private Vector2 position;
    private Vector2 velocity;
//    private Vector2 acceleration;

//    private float rotation;
    private Rectangle box;

    public GameObject(Vector2 position, Vector2 velocity, Rectangle box){
        this.position = position;
        this.velocity = velocity;
        this.box = box;
    }
}
