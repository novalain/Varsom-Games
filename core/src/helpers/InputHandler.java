package helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import gameobjects.Car;
/**
 * Created by Alice on 2015-03-12.
 */


/**
 * Created by Alice on 2015-03-11.
 * Handlig input such as gyro and tough event
 */
public class InputHandler implements InputProcessor{
    private Car myCar;

    // Ask for a reference to the car when InputHandler is created.
    public InputHandler(Car car) {
        // myCar now represents the gameWorld's car.
        myCar = car;
    }

    // Function to check if screen is touched
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        myCar.onTouch();
        Gdx.app.log("in Handler", "touched true");
        return true; // return true to say there's been a touch event
    }

    // We don't need these right now, but they have to be declared to implement InputProcessor
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
    // Create a function for gyro too!
}
