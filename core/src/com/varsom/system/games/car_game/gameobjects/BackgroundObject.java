package com.varsom.system.games.car_game.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class BackgroundObject {

    private Vector2 pos;
    private double speed;

    private Sprite sprite;
    private Texture texture;

    public BackgroundObject(Vector2 pos, String image_url) {
        this.pos = pos;
        speed = 0.5f;


        try {
            texture = new Texture(Gdx.files.internal(image_url));
            sprite = new Sprite(texture);

        } catch (Exception e) {
            Gdx.app.error("error", "error while loading file");
        }
    }

    public void update() {
        pos.x += speed;
    }

    public Vector2 getPos() {
        return pos;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    public void draw(SpriteBatch sb) {
        sb.draw(texture, pos.x, pos.y);
    }

    public void setX(float x){pos.x = x;}
    public void setY(float y){pos.y = y;}

    public int getWidth(){return texture.getWidth();}
    public int getHeight(){return texture.getHeight();}
}
