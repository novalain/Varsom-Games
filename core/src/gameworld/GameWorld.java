package gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import gameobjects.Car;

public class GameWorld {
    private Car car;
    private Texture texture;

    public GameWorld() {
        texture = new Texture(Gdx.files.internal("img/Car.png"));
        car = new Car(new Vector2(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2), new Vector2(0,0), new Vector2(0,0), texture);
    }

    public void update(float delta) {
        car.update(0, delta);
    }

    public Car getCar() {
        return car;

    }
}
