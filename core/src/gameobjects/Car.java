package gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Alice on 2015-03-11.
 */

public class Car extends GameObject{

    public Car(Vector2 position, Vector2 velocity){
        super(position, velocity, new Rectangle(position.x, position.y, 10, 10)); // Last parameters for width of hitbox

    }

    // Update car position based on angle of gyro
    public void update(int angle) {

        // From previous version

        /*float gx = 90 - angle;

        if(position.x + gx < GameView.window_size.x && position.x + gx > 0)
            position.x +=Math.round(Math.cos(angle*Math.PI/180)*20);*/

    }

}
