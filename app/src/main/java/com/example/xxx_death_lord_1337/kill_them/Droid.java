/**
 * 
 */
package com.example.xxx_death_lord_1337.kill_them;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

/**
 * This is a test droid that is dragged, dropped, moved, smashed against
 * the wall and done other terrible things with.
 * Wait till it gets a weapon!
 * 
 * @author impaler
 *
 */

public class Droid {

	private Bitmap bitmap;	// the actual bitmap
	private float x;			// the X coordinate
	private float y;			// the Y coordinate
    private float vy;
    private GameView gameView;

	private boolean touched;	// if droid is touched/picked up
	private boolean speed;	// the speed with its directions
	
	public Droid(Bitmap bitmap, int x, int y) {
		this.bitmap = bitmap;
		this.x = x;
		this.y = y;
        //this.vx = 0;
        this.vy = 0;
		this.speed = false;
	}
	
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	public float getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

	public boolean isTouched() {
		return touched;
	}

	public void setTouched(boolean touched) {
		this.touched = touched;
	}

    //Draw the droid into canvas
	public void draw(Canvas canvas) {
		canvas.drawBitmap(bitmap, x - (bitmap.getWidth() / 2), y - (bitmap.getHeight() / 2), null);
	}

	/**
	 * Method which updates the droid's internal state every tick
	 */
    public void update(int angle, Point window_size) {

        //Log.d("window_size", Integer.toString(window_size.x));
        float gx = -(angle - 90);

        if(x + gx < window_size.x && x + gx > 0)
            x = x + gx*0.1f;

        if(speed){
            vy = 5;
            Log.d("ACC", "ACC");
        }

        else
            vy = 0;

        if(y + (9.82f * 0.1f) < window_size.y && y + vy > 0)
            y = y + (9.82f * 0.1f) - vy;

        Log.d("y", y + "");

    }
	
	
	/**
	 * Handles the {@link android.view.MotionEvent.ACTION_DOWN} event. If the event happens on the
	 * bitmap surface then the touched state is set to <code>true</code> otherwise to <code>false</code>
	 * @param eventX - the event's X coordinate
	 * @param eventY - the event's Y coordinate
	 */

    public void toggleSpeed(){

        Log.d("tgg", "toggle");

        speed = speed ? false : true;

    }


	public void handleActionDown(int eventX, int eventY) {

		if (eventX >= (x - bitmap.getWidth() / 2) && (eventX <= (x + bitmap.getWidth()/2))) {
			if (eventY >= (y - bitmap.getHeight() / 2) && (y <= (y + bitmap.getHeight() / 2))) {
				// droid touched
				setTouched(true);
			} else {
				setTouched(false);
			}

		}

		else {
			setTouched(false);
		}

	}


}
