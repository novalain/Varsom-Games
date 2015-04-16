package com.varsom.system.games.car_game.abstract_gameobjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by oskarcarlbaum on 13/03/15.
 */
public abstract class DynamicObject extends PhysicalGameObject {

    public DynamicObject(Vector2 position, float angle, Shape shape, Sprite sprite, World world){
        super(position, angle, shape, sprite,world);
        this.bodyDef.type = BodyDef.BodyType.DynamicBody;
    }
    public DynamicObject(float x, float y, float angle, Shape shape, Sprite sprite, World world){
        super(new Vector2(x,y), angle, shape, sprite, world);
        this.bodyDef.type = BodyDef.BodyType.DynamicBody;
    }

}
