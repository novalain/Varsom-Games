package gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import gameobjects.Car;

public class GameWorld {
    private Car car;

    public GameWorld() {
       // car = new Car(new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()), new Vector2(0,0));
    }

    public void update(float delta) {
        car.update(0);
    }

    public Car getCar() {
        return car;

    }
}
