package com.example.xxx_death_lord_1337.kill_them;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;

/**
 * Created by michaelnoven on 15-03-04.
 */
public class Obstacle extends GameObject {


    public Obstacle(PointF position, PointF vel, Bitmap bmp, Rect hitbox){

           super(position, vel, hitbox, bmp);

    }

    public void update(){

        vel = new PointF(0, GameView.firstCarSpeed);

        position.y = position.y - vel.y;

    }

    public void draw(Canvas canvas){

        canvas.drawBitmap(bmp, 50, position.y, null);

    }


}
