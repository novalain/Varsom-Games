package com.example.xxx_death_lord_1337.kill_them;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;

import java.util.Vector;

/**
 * Created by Alice on 2015-03-04.
 */
public class gameObject {
    /* VARIABLES
    position
    hitbox(rect)
    speed
    image bmp
    */
    private PointF position;
    private PointF vel;
    private Rect objectHitbox;
    private Bitmap bmp;

    /* FUNCTIONS
     constructor
     get: speed, position, hitbox size, bmp
     set: all
     update
     drawObject

     class Rect incudes intersect (http://developer.android.com/reference/android/graphics/Rect.html)
     */

    public gameObject(PointF position, PointF vel, Rect objectHitbox, Bitmap bmp) {
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
    public Bitmap getBmp(){
        return bmp;
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
    public void setBmp(Bitmap bmp){
        this.bmp = bmp;
    }


}
