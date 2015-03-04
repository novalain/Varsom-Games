package com.example.xxx_death_lord_1337.kill_them;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;

import java.util.Vector;

/**
 * Created by Alice on 2015-03-04.
 */
public abstract class GameObject {

    protected PointF position;
    protected PointF vel;
    protected Rect objectHitbox;
    protected Bitmap bmp;

     /*class Rect incudes intersect (http://developer.android.com/reference/android/graphics/Rect.html)
     */

    public GameObject(PointF position, PointF vel, Rect objectHitbox, Bitmap bmp) {
        this.position = position;
        this.vel = vel;
        this.objectHitbox = objectHitbox;
        this.bmp = bmp;

    }
    // Get functions
    public PointF getPosition(){
        return position;
    }
    public PointF getVelocity(){
        return vel;
    }
    public Rect getObjectHitbox(){
        return objectHitbox;
    }

    // Set functions
    public void setPosition(PointF position){
        this.position = position;
    }
    public void setVelocity(PointF vel){
        this.vel = vel;
    }
    public void setObjectHitbow(Rect objectHitbox){
        this.objectHitbox = objectHitbox;
    }

    // Draws the object into canvas
    public void draw(Canvas canvas){

        canvas.drawBitmap(bmp, position.x - (bmp.getWidth() / 2), position.y - (bmp.getHeight() / 2), null);

    }

    public Bitmap getBmp(){
        return bmp;
    }

    public void setBmp(Bitmap bmp){
        this.bmp = bmp;
    }

}
