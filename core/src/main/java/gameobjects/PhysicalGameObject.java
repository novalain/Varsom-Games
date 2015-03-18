package gameobjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Supposed to store physics objects!
 */
public abstract class PhysicalGameObject {
    protected Vector2 position;
    protected BodyDef bodyDef;
    protected Body body;
    protected FixtureDef fixtureDef;
    protected Fixture fixture;
    protected Shape shape;
    protected World world;
    protected Sprite sprite;


    public PhysicalGameObject(Vector2 inPosition,/* float width, float height,*/Shape inShape,Sprite inSprite, World inWorld){
        this.setPosition(inPosition);
        shape = inShape;
        sprite = inSprite;
        world = inWorld;
        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();
    }

    // Get functions
    public Vector2 getPosition(){
        return position;
    }

    // Set functions
    public void setPosition(Vector2 inPosition){
        this.position = inPosition;
    }

    public FixtureDef getFixtureDef() {
        return fixtureDef;
    }

    public Body getBody() {
        return body;
    }

    protected void addObjectToWorld() {
        body = world.createBody(bodyDef);
        body.setUserData(sprite);
        body.setTransform(position,0);
        body.createFixture(fixtureDef);
    }
}