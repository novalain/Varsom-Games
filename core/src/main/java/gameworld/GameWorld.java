package gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import gameobjects.Car;

public class GameWorld {
    private Car car;
   // private Texture texture;

    public GameWorld() {
        car = new Car();
    }

    public void update(float delta) {

        car.update(Gdx.input.getAccelerometerY(), delta);

        //Gdx.app.log("Acc i X:", Gdx.input.getAccelerometerX() + "");
       // Gdx.app.log("Acc i Y:", Gdx.input.getAccelerometerY() + "");
       // Gdx.app.log("Acc i Z:", Gdx.input.getAccelerometerZ() + "");



        //Gdx.app.log("LEHEL", "kele");

    }

    public Car getCar() {
        return car;
    }
}
