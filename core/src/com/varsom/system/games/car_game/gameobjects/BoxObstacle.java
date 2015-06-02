package com.varsom.system.games.car_game.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import com.varsom.system.games.car_game.abstract_gameobjects.StaticObject;
import com.varsom.system.games.car_game.helpers.AssetLoader;

/**
 * Created by oskarcarlbaum on 18/03/15.
 */
public class BoxObstacle extends StaticObject {
    private Vector2 size;
    private float width, height;

    /**
     *
     * @param inPosition
     * @param angle
     * @param inSize
     * @param inWorld
     */
    public BoxObstacle(Vector2 inPosition, float angle, Vector2 inSize,World inWorld) {
        super(inPosition, angle, new PolygonShape(), new Sprite(AssetLoader.wallTexture), inWorld);
        width = inSize.x;
        height = inSize.y;
        defineParameters();
    }
    public BoxObstacle(Vector2 inPosition, float angle, float inWidth, float inHeight ,World inWorld) {
        super(inPosition, angle, new PolygonShape(), new Sprite(AssetLoader.wallTexture), inWorld);
        width = inWidth;
        height = inHeight;
        defineParameters();
    }

    private void defineParameters(){
        size = new Vector2(width,height);
        //we have to parse the Shape to a PolygonShape in order to access the .setAsBox method
        ((PolygonShape) shape).setAsBox(width/2f,height/2f);

        sprite.setSize(width, height);
        sprite.setOriginCenter();

        fixtureDef.shape = shape;
        fixtureDef.density = 2.0f;
        fixtureDef.friction = 0.9f;     //value between 0-1
        fixtureDef.restitution = 0.01f; //value between 0-1
        addObjectToWorld();
        shape.dispose();
    }
}
