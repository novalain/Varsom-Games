package gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import gameobjects.Car;

public class GameWorld {
    private Car car;
    World physicsWorld;
   // private Texture texture;

    public GameWorld() {

       // Box2D.init();
        physicsWorld = new World(new Vector2(0, 10), true);
        car = new Car(physicsWorld);

    }

    public void update(float delta) {

        physicsWorld.step(delta, 6, 2);
        car.update(Gdx.input.getAccelerometerY(), delta);

        //Gdx.app.log("Acc i X:", Gdx.input.getAccelerometerX() + "");
       // Gdx.app.log("Acc i Y:", Gdx.input.getAccelerometerY() + "");
       // Gdx.app.log("Acc i Z:", Gdx.input.getAccelerometerZ() + "");
        //Gdx.app.log("LEHEL", "kele");

    }

    public Car getCar() {

        return car;

    }

    public void dispose(){

        physicsWorld.dispose();

    }

}
