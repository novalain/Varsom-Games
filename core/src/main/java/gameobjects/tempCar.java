package gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by michaelnoven on 15-03-18.
 */
public class tempCar extends DynamicObject{

    private Vector2 size;
    private float width, height;

    public tempCar(Vector2 inPosition, Vector2 inSize,World inWorld){
        super(inPosition,new PolygonShape(),new Sprite(new Texture("img/Car.png")),inWorld);
        size = inSize;
        width = inSize.x;
        height = inSize.y;

        //we have to parse the Shape to a PolygonShape in order to access the .setAsBox method
        ((PolygonShape) shape).setAsBox(width/2f,height/2f);

        sprite.setSize(width, height);
        sprite.setOriginCenter();
        fixtureDef.shape = shape;
        fixtureDef.density = 2.0f;
        fixtureDef.friction = 0.5f;     //value between 0-1
        fixtureDef.restitution = 0.2f; //value between 0-1
        addObjectToWorld();
        shape.dispose();
    }

    public void onTouchDown(){

        Gdx.app.log("car", "touched");

        body.applyForceToCenter(-10, 10, true);

    }

}
