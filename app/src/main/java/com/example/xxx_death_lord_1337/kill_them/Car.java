package com.example.xxx_death_lord_1337.kill_them;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.Rect;

/**
 * Created by michaelnoven on 15-03-04.
 */
public class Car extends GameObject {

    public Car(PointF position, PointF vel, Bitmap bmp){

        super(position, vel, new Rect(), bmp);

    }

    /**
     * Method which updates the droid's internal state every tick
     */
    public void update(int angle) {

        float gx = 90 - angle;

        if(position.x + gx < GameView.window_size.x && position.x + gx > 0)
            position.x +=Math.round(Math.cos(angle*Math.PI/180)*20);


    }

}
