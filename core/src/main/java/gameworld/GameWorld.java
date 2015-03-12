package gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import gameobjects.Car;

public class GameWorld {
    private Car car;
   // private Texture texture;

    public GameWorld() {
        car = new Car();
    }

    public void update(float delta) {
        car.update(0, delta);
    }

    public Car getCar() {
        return car;
    }
}
