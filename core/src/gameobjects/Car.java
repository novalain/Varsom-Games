package gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Alice on 2015-03-11.
 */
public class Car extends GameObject{

    public Car(Vector2 position, Vector2 velocity){
        super(position, velocity, new Rectangle(position.x, position.y, 10, 10));

    }
}
