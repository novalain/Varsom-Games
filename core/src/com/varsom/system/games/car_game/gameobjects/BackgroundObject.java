package com.varsom.system.games.car_game.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.varsom.system.Commons;
import com.varsom.system.games.car_game.helpers.AssetLoader;

public class BackgroundObject {

    private Vector2 pos;
    private float speed,xdir,ydir;
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
        //image.setPosition(image.getX()+xdir*speed,image.getY()+ydir*speed);
        image.moveBy(xdir*speed,ydir*speed);
        image.rotateBy(rotSpeed);
    }

    public void allNewValues(){
        speed = (float) Math.random();//0.5f;
        xdir = 2 * (float) Math.random() -1;
        ydir = 2 * (float) Math.random() -1;
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
