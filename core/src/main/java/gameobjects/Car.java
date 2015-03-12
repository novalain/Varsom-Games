package gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Alice on 2015-03-11.
 */

public class Car extends GameObject{

    //private Texture carTexture;

    //public Car(Vector2 position, Vector2 velocity, Vector2 acceleration, Texture texture){
    public Car(){

        //super(position, velocity, acceleration, new Rectangle(position.x, position.y, 10, 10)); // Last parameters for width of hitbox
        super(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 50, 74);
        //this.carTexture = texture;

    }

    private void checkBoundaries(){

        if(position.x > Gdx.graphics.getWidth() - 50){

            position.x = Gdx.graphics.getWidth() - 50;

        }

        else if(position.x < 0){

            position.x = 0;

        }
        else if(position.y > Gdx.graphics.getHeight() - 74){

            position.y = Gdx.graphics.getHeight() - 74;
            velocity.y = -velocity.y*0.3f;
        }

        else if(position.y < 0){
            position.y = 0;
            velocity.y = -velocity.y*0.3f;
        }

    }

    // Update car position based on angle of gyro
    public void update(int angle, float delta) {
        // set velocity of the car according to acceleration
        velocity.add(acceleration.cpy().scl(delta));
        //Gdx.app.log("in Car", ""+velocity);

        // Set position to new value
        position.add(velocity.cpy().scl(delta));

        // Check boundaries (Hard coded values for width and height of the sprite)
        checkBoundaries();

    }

    // For spritebatch
   //public Texture getTexture(){return carTexture;    }

    // If the speed button has been touched, increase velocity
    public void onTouch(){
        this.velocity.y -= 100;
    }
}
