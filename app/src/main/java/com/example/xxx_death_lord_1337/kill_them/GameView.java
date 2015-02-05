package com.example.xxx_death_lord_1337.kill_them;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

//TODO: Gyrot krashar ibland

public class GameView extends SurfaceView implements SurfaceHolder.Callback, SensorEventListener{


    private static final String TAG = GameView.class.getSimpleName();

    private Bitmap bmp;
    private SurfaceHolder holder;
    private GameLoopThread gameLoopThread;
    private List<Sprite> sprites = new ArrayList<Sprite>();
    //private Car car;
    private Sprite sprite;
    private int x = 0;
    private int xSpeed = 1;
    private Droid droid;

    //Gyro variables
    private SensorManager accelerometerSensorManager;
    private Sensor accelerometerSensor;
    private int currentAngle;


    //Constructor
    public GameView(Context context) {

        super(context);
        // Add the callback to the surface holder to intercept events
        getHolder().addCallback(this);

        // create droid and load bitmap
        droid = new Droid(BitmapFactory.decodeResource(getResources(), R.drawable.droid_1), 50, 50);

        //Create the game loop thread
        gameLoopThread = new GameLoopThread(this, getHolder());

        // Make the GameView focusable so it can handle events
        setFocusable(true);

        //get a hook to the sensor service
        accelerometerSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = accelerometerSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        accelerometerSensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_FASTEST);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){



    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){

        gameLoopThread.setRunning(true);
        gameLoopThread.start();

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        //gameLoopThread.setRunning(false);
        while (retry) {
            try {
                gameLoopThread.join();
                retry = false;
            }
            catch (InterruptedException e) {
                // try again shutting down the thread

            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // delegating event handling to the droid
            droid.handleActionDown((int)event.getX(), (int)event.getY());

            // check if in the lower part of the screen we exit
            if (event.getY() > getHeight() - 50) {
                gameLoopThread.setRunning(false);
                ((Activity)getContext()).finish();
            } else {
                Log.d(TAG, "Coords: x=" + event.getX() + ",y=" + event.getY());
            }
        } if (event.getAction() == MotionEvent.ACTION_MOVE) {
            // the gestures
            if (droid.isTouched()) {
                // the droid was picked up and is being dragged
                droid.setX((int)event.getX());
                droid.setY((int)event.getY());
            }
        } if (event.getAction() == MotionEvent.ACTION_UP) {
            // touch was released
            if (droid.isTouched()) {
                droid.setTouched(false);
            }
        }
        return true;
    }


    public void render(Canvas canvas){

        canvas.drawColor(Color.BLACK);
        droid.draw(canvas);

    }

    public void update() {

        Log.d("Angle: ", Integer.toString(currentAngle));

        // check collision with right wall if heading right
        if (droid.getSpeed().getxDirection() == Speed.DIRECTION_RIGHT
                && droid.getX() + droid.getBitmap().getWidth() / 2 >= getWidth()) {
            droid.getSpeed().toggleXDirection();
        }
        // check collision with left wall if heading left
        if (droid.getSpeed().getxDirection() == Speed.DIRECTION_LEFT
                && droid.getX() - droid.getBitmap().getWidth() / 2 <= 0) {
            droid.getSpeed().toggleXDirection();
        }
        // check collision with bottom wall if heading down
        if (droid.getSpeed().getyDirection() == Speed.DIRECTION_DOWN
                && droid.getY() + droid.getBitmap().getHeight() / 2 >= getHeight()) {
            droid.getSpeed().toggleYDirection();
        }
        // check collision with top wall if heading up
        if (droid.getSpeed().getyDirection() == Speed.DIRECTION_UP
                && droid.getY() - droid.getBitmap().getHeight() / 2 <= 0) {
            droid.getSpeed().toggleYDirection();
        }
        // Update the lone droid
        droid.update();
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        //if sensor is unreliable, return void
        if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE)
        {
            Log.d("Sensor", "The sensor is unreliable, whatever that means!");
            return;
        }

        float aX = event.values[0];
        float aY = event.values[1];
        currentAngle = (int) Math.round(Math.atan2(aX, aY)/(Math.PI/180));

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}