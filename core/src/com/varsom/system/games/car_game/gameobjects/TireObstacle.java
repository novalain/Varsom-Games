package com.varsom.system.games.car_game.gameobjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;

import com.varsom.system.games.car_game.abstract_gameobjects.StaticObject;
import com.varsom.system.games.car_game.helpers.AssetLoader;

/**
 * Created by oskarcarlbaum on 18/03/15.
 */
public class TireObstacle extends StaticObject {
    private float radius;

    public TireObstacle(Vector2 inPosition,float angle, float inRadius,World inWorld){
        super(inPosition,angle,new CircleShape(),new Sprite(AssetLoader.tireObstacleTexture),inWorld);
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