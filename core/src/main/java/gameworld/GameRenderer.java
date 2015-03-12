package gameworld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by Alice on 2015-03-11.
 */
public class GameRenderer {
    // get a camera object creating a 2D-field
    private OrthographicCamera cam;
    private GameWorld myWorld;

    //constructor
    public GameRenderer(GameWorld world){
        myWorld = world;
        cam = new OrthographicCamera();
        cam.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    }
    public void render(){
        Gdx.app.log("GameRender", "render");
        Gdx.gl.glClearColor(0, 0, 0, 1); // Creating black background
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

    }
}
