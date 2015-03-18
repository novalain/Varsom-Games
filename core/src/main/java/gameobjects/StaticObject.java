package gameobjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by oskarcarlbaum on 13/03/15.
 */
public abstract class StaticObject extends PhysicalGameObject{

    public StaticObject(Vector2 position, Shape shape, Sprite sprite, World world){
        super(position, shape, sprite,world);
        this.bodyDef.type = BodyDef.BodyType.StaticBody;
    }
    public StaticObject(float x, float y, Shape shape, Sprite sprite, World world){
        super(new Vector2(x,y), /*width, height,*/ shape,sprite, world);
        this.bodyDef.type = BodyDef.BodyType.StaticBody;
    }

}
