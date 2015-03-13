package gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Alice on 2015-03-11.
 */

public class Car extends GameObject{

    //private Texture carTexture;
    public Body body;
    private Fixture fixture;

    //public Car(Vector2 position, Vector2 velocity, Vector2 acceleration, Texture texture){
    public Car(World physicsWorld){

        //super(position, velocity, acceleration, new Rectangle(position.x, position.y, 10, 10)); // Last parameters for width of hitbox
        super(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 50, 74);

        // Now create a BodyDefinition.  This defines the physics objects type
       // and position in the simulation
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        // We are going to use 1 to 1 dimensions.  Meaning 1 in physics engine
        // is 1 pixel
        // Set our body to the same position as our sprite
        bodyDef.position.set(position.x, position.y);

        // Create a body in the world using our definition
        body = physicsWorld.createBody(bodyDef);

        // Now define the dimensions of the physics shape
        PolygonShape shape = new PolygonShape();
        // We are a box, so this makes sense, no?
        // Basically set the physics polygon to a box with the same dimensions
        //as our sprite
        shape.setAsBox(this.getWidth()/2, this.getHeight()/2);

        // FixtureDef is a confusing expression for physical properties
        // Basically this is where you, in addition to defining the shape of the
        //body
        // you also define it's properties like density, restitution and others
        //we will see shortly
        // If you are wondering, density and area are used to calculate over all
       // mass
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        fixture = body.createFixture(fixtureDef);

        // Shape is the only disposable of the lot, so get rid of it
        shape.dispose();



    }

    private void checkBoundaries(){

        if(position.x > Gdx.graphics.getWidth() - 50){

            position.x = Gdx.graphics.getWidth() - 50;
            velocity.x = -velocity.x*0.3f;

        }

        else if(position.x < 0){

            position.x = 0;
            velocity.x = -velocity.x*0.3f;

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
    public void update(float angle, float delta) {
        //delta = 1/45.f;
        // set velocity of the car according to acceleration
       /* velocity.add(acceleration.cpy().scl(delta));
        velocity.x = angle*150;
        //Gdx.app.log("in Car", ""+velocity);
        //Gdx.app.log("posx" , position.x + "");

        // Set position to new value
        position.add(velocity.cpy().scl(delta));*/

        // Check boundaries (Hard coded values for width and height of the sprite)
        checkBoundaries();
        setPosition(new Vector2(body.getPosition().x, body.getPosition().y));


    }

    // For spritebatch
   //public Texture getTexture(){return carTexture;    }

    // If the speed button has been touched, increase velocity
    public void onTouch(){
        this.velocity.y -= 100;
    }

    public Body getBody(){

        return body;

    }

    public Fixture getFixture(){

        return fixture;

    }



}
