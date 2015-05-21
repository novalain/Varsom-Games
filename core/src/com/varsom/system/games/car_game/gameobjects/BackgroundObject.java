package com.varsom.system.games.car_game.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.varsom.system.Commons;

public class BackgroundObject {

    private Vector2 pos;
    private float speed, xDir, yDir;
    private float rotSpeed;

    private Image image;
    private Texture texture;

    public BackgroundObject(Image s) {
        pos = new Vector2();

        try {
            image = s;
            image.setOrigin(image.getWidth()/2,image.getHeight()/2);
        } catch (Exception e) {
            Gdx.app.error("error", "error while loading file");
        }

        allNewValues();
    }

    public void update() {
        image.moveBy(xDir *speed, yDir *speed);
        image.rotateBy(rotSpeed);
    }

    public void allNewValues(){
        speed = (float) Math.random();//0.5f;
        xDir = 2 * (float) Math.random() -1;
        yDir = 2 * (float) Math.random() -1;
        image.setPosition((float) Math.random() * Commons.WORLD_WIDTH, (float) Math.random() * Commons.WORLD_HEIGHT);
        image.setRotation((float)(360*Math.random()));
        rotSpeed = (float) Math.random() * 3.0f;
    }

    public Vector2 getPos() {
        return pos;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    /*public void draw(SpriteBatch sb) {
        //sb.draw(sprite, pos.x, pos.y);
        sprite.draw(sb);
    }*/

    public void setX(float x){pos.x = x;}
    public void setY(float y){pos.y = y;}

    public int getWidth(){return texture.getWidth();}
    public int getHeight(){return texture.getHeight();}
    public Image getImage(){
        return image;
    }
}
