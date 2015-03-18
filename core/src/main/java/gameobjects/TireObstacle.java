package gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by oskarcarlbaum on 18/03/15.
 */
public class TireObstacle extends StaticObstacle{
    private float radius;

    public TireObstacle(Vector2 inPosition, float inRadius,World inWorld){
        super(inPosition,new CircleShape(),new Sprite(new Texture("img/tire.png")),inWorld);
        radius = inRadius;
        shape.setRadius(radius);
        sprite.setSize(radius * 2, radius * 2);
        sprite.setOriginCenter();
        fixtureDef.shape = shape;
        fixtureDef.density = 2.0f;
        fixtureDef.friction = 0.5f;     //value between 0-1
        fixtureDef.restitution = 0.99f; //value between 0-1
        addObjectToWorld();
        shape.dispose();
    }
}