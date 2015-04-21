package com.varsom.system.games.car_game.abstract_gameobjects;

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
    protected float angle;
    protected BodyDef bodyDef;
    protected Body body;
    protected FixtureDef fixtureDef;
    protected Fixture fixture;
    protected Shape shape;
    protected World world;
    protected Sprite sprite;

    public PhysicalGameObject(Vector2 inPosition,float inAngle,Shape inShape,Sprite inSprite, World inWorld){
        position = inPosition;
        angle = inAngle;
        shape = inShape;
        sprite = inSprite;
        world = inWorld;
        bodyDef = new BodyDef();
        fixtureDef = new FixtureDef();
    }

    // Get functions
    public Vector2 getPosition(){
        return body.getPosition();
    }

    public float getAngle(){
        return body.getAngle();
    }

    public Vector2 getWorldPoint(Vector2 localPoint) {
        return body.getWorldPoint(localPoint);
    }

    public FixtureDef getFixtureDef() {
        return fixtureDef;
    }

    public Sprite getSprite() {return sprite;}

    public Body getBody() {
        return body;
    }

    protected void addObjectToWorld() {
        body = world.createBody(bodyDef);
        body.setUserData(sprite);
        body.setTransform(position,angle);
        body.createFixture(fixtureDef);
    }
}