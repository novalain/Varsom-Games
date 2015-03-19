package Tracks;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.util.Vector;

import gameobjects.BoxObstacle;
import helpers.AssetLoader;

/**
 * Created by oskarcarlbaum on 18/03/15.
 */
public class TestTrack {

    public static Sprite backgroundSprite;
    public Vector<Sprite> sprites;
    public World world;
    private Body boxBody;
    private Array<Body> tmpBodies = new Array<Body>();

    public TestTrack(World inWorld) {
        world = inWorld;
        sprites = new Vector<Sprite>();
        createTestTrack();
    }

    private void createTestTrack(){
        createBackground();

    //Static physical objects
        float wallThickness = 4.0f;
        BoxObstacle upperWall = new BoxObstacle(new Vector2(0,backgroundSprite.getHeight()/2f)
                                ,new Vector2(backgroundSprite.getWidth(),wallThickness), world);
        BoxObstacle lowerWall = new BoxObstacle(new Vector2(0,-backgroundSprite.getHeight()/2f)
                                ,new Vector2(backgroundSprite.getWidth(),wallThickness), world);
        BoxObstacle leftWall = new BoxObstacle(new Vector2(-backgroundSprite.getWidth()/2f,0)
                                ,new Vector2(wallThickness,backgroundSprite.getHeight()), world);
        BoxObstacle rightWall = new BoxObstacle(new Vector2(backgroundSprite.getWidth()/2f,0)
                                ,new Vector2(wallThickness,backgroundSprite.getHeight()), world);
//        BoxObstacle upperWall = new BoxObstacle(new Vector2(-backgroundSprite.getWidth()/2f,backgroundSprite.getHeight()/2f)
//                                                ,new Vector2(backgroundSprite.getWidth(),wallThickness), world);
//        BoxObstacle lowerWall = new BoxObstacle(new Vector2(-backgroundSprite.getWidth()/2f,-backgroundSprite.getHeight()/2f)
//                                                ,new Vector2(backgroundSprite.getWidth(),wallThickness), world);
//        BoxObstacle leftWall = new BoxObstacle(new Vector2(-backgroundSprite.getWidth()/2f,-backgroundSprite.getHeight()/2f)
//                                                ,new Vector2(wallThickness,backgroundSprite.getHeight()), world);
//        BoxObstacle rightWall = new BoxObstacle(new Vector2(backgroundSprite.getWidth()/2f,-backgroundSprite.getHeight()/2f)
//                                                ,new Vector2(wallThickness,backgroundSprite.getHeight()), world);
        /*sprites.addElement(upperWall);
        sprites.addElement(lowerWall);
        sprites.addElement(leftWall);
        sprites.addElement(rightWall);*/

    }

    private void createBackground() {
        backgroundSprite = new Sprite(AssetLoader.testTrackTexture);
        backgroundSprite.setSize(backgroundSprite.getWidth()/10f,backgroundSprite.getHeight()/10f);
        //backgroundSprite.setOriginCenter();
        backgroundSprite.setPosition(-backgroundSprite.getWidth()/2,-backgroundSprite.getHeight()/2);
        sprites.addElement(backgroundSprite);
    }

    public void addToRenderBatch(SpriteBatch inBatch) {
        inBatch.begin();
        // Draw sprites
        for (Sprite sprite : sprites) {
            sprite.draw(inBatch);//backgroundSprite.draw(inBatch);
        }

        //Draw physical objects
        world.getBodies(tmpBodies);
        for (Body body : tmpBodies) {
            if ( body.getUserData() != null && body.getUserData() instanceof Sprite) {
                Sprite sprite = (Sprite) body.getUserData();
                sprite.setPosition(body.getPosition().x - sprite.getWidth()/2, body.getPosition().y - sprite.getHeight()/2);
                sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
                sprite.draw(inBatch);
            }
        }
        inBatch.end();
    }
}
