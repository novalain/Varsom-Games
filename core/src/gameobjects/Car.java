package gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Alice on 2015-03-11.
 */

public class Car extends GameObject{

    private Texture carTexture;

    public Car(Vector2 position, Vector2 velocity, Vector2 acceleration, Texture texture){

        super(position, velocity, acceleration, new Rectangle(position.x, position.y, 10, 10)); // Last parameters for width of hitbox
        this.carTexture = texture;

    }

    // Update car position based on angle of gyro
    public void update(int angle, float delta) {
        // set velocity of the car according to acceleration
        velocity.add(acceleration.cpy().scl(delta));
        // From previous version

        /*float gx = 90 - angle;

        if(position.x + gx < GameView.window_size.x && position.x + gx > 0)
            position.x +=Math.round(Math.cos(angle*Math.PI/180)*20);*/

    }

    // For spritebatch
    public Texture getTexture(){

        return carTexture;

    }
    // If the speed button has been touched, increase velocity
    public void onTouch(){
        Gdx.app.log("in Car", "touched!");
        this.velocity.y += 10;
    }


}
