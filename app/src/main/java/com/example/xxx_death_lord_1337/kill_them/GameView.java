package com.example.xxx_death_lord_1337.kill_them;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.text.Layout;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

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
    private Droid droid;
    private Point window_size;

    //Gyro variables
    private SensorManager accelerometerSensorManager;
    private Sensor accelerometerSensor;
    private int currentAngle = 90; // just a default value, probably unnecessary

    //Background variables
    private Bitmap backgroundBMP;
    private BitmapDrawable scrollingBG;
    private int backgroundFarMoveY = 0;
    private int backgroundNearMoveY = 0;

    //Car variables
    private int TOP_SPEED = 20;
    private int firstCarSpeed = TOP_SPEED;


    //Temporary variables only for developing purposes.. NOT to be used in final product
    private Paint paint;
    private int tick = 0;

    //Constructor
    public GameView(Context context) {

        super(context);

        // Add the callback to the surface holder to intercept events
        getHolder().addCallback(this);

        //Create the game loop thread
        gameLoopThread = new GameLoopThread(this, getHolder());

        // Make the GameView focusable so it can handle events
        setFocusable(true);

        //get a hook to the sensor service
        accelerometerSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = accelerometerSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){

        window_size = new Point(getWidth(), getHeight());

        //create and apply scrolling background
        backgroundBMP = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.temp_background), getWidth(),getHeight()*3 , false);
        scrollingBG = new BitmapDrawable(getResources(),backgroundBMP);
        scrollingBG.setBounds(0, 0, getWidth(), getHeight());

        scrollingBG.setTileModeY(Shader.TileMode.REPEAT);

        // create droid and load bitmap
        droid = new Droid(BitmapFactory.decodeResource(getResources(), R.drawable.player_car), window_size.x/2, 3*window_size.y/5, window_size);

        gameLoopThread.setRunning(true);
        gameLoopThread.start();

        //detta är bara för texten i bakgrunden, så man slipper kolla sug-logCat:en
        paint = new Paint();
        paint.setColor(Color.DKGRAY);
        paint.setTextSize(50.0f);

        // create listener. Important that this is after the creation of new Droids!
        accelerometerSensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_FASTEST);
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

            droid.toggleSpeed();

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

            droid.toggleSpeed();
            // touch was released
            if (droid.isTouched()) {
                droid.setTouched(false);
            }
        }
        return true;
    }

    //will draw the tiled background
    public void drawBackground(Canvas canvas){
        backgroundFarMoveY = backgroundFarMoveY - firstCarSpeed;
        //backgroundNearMoveY = backgroundNearMoveY - 4*firstCarSpeed;

        // calculate the wrap factor for matching image draw
        int newFarY = backgroundFarMoveY - backgroundBMP.getHeight();
        // if we have scrolled all the way, reset to start
        if (newFarY >= 0) {
            backgroundFarMoveY = newFarY;
            canvas.drawBitmap(backgroundBMP, 0, backgroundFarMoveY, null);
        } else {
            canvas.drawBitmap(backgroundBMP, 0, backgroundFarMoveY, null);
            canvas.drawBitmap(backgroundBMP, 0, newFarY,  null);
        }
    }

    public void render(Canvas canvas){

        //TODO: Implement a scrolling background
        drawBackground(canvas);
        //canvas.drawColor(Color.rgb(200,44,0));

        //när currentAngle är 180 ska bilen va roterad -90
        Matrix rotMat = new Matrix();
        rotMat.postRotate(90-currentAngle, (droid.getBitmap().getWidth() / 2), (droid.getBitmap().getHeight() / 2)); //rotate it
        rotMat.postTranslate(droid.getX(), droid.getY());
        canvas.drawBitmap(droid.getBitmap(),rotMat,null);

        //droid.draw(canvas);

        //detta är bara för texten i bakgrunden, så man slipper kolla sug-logCat:en

        String temp = "Angle: " + currentAngle + "   speed: " + firstCarSpeed;
        canvas.drawText(temp, 0, temp.length(), getWidth()/2-temp.length()*paint.getTextSize()/3, getHeight()/3,paint);

    }

    public void update() {

    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        //if sensor is unreliable, return void
        if (event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE)
        {
            Toast messageBox =  Toast.makeText(getContext(), "Your accelerometer is unreliable!", Toast.LENGTH_SHORT);
            messageBox.show();
            return;
        }

        float aX = event.values[0];
        float aY = event.values[1];

        int temp = (int) Math.round(Math.atan2(aX, aY)/(Math.PI/180));
        if ( temp >= 0) {
            currentAngle = temp;
            firstCarSpeed = (int) ((-1)*Math.round(Math.sin(currentAngle*Math.PI/180)*TOP_SPEED));
        }

        droid.update(currentAngle);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}