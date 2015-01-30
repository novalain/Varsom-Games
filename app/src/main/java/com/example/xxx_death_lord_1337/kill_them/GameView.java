package com.example.xxx_death_lord_1337.kill_them;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView {

    private Bitmap bmp;
    private SurfaceHolder holder;
    private GameLoopThread gameLoopThread;
    private Sprite sprite;

    private int x = 0;
    private int xSpeed = 1;

    //Constructor
    public GameView(Context context) {

        super(context);
        gameLoopThread = new GameLoopThread(this);
        holder = this.getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                gameLoopThread.setRunning(false);
                while (retry) {
                    try {
                        gameLoopThread.join();
                        retry = false;
                    }
                    catch (InterruptedException e) {

                    }
                }
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                gameLoopThread.setRunning(true);
                gameLoopThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

        });

        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.lolfigur);
        sprite = new Sprite(this, bmp);

    }

    public void draw(Canvas canvas){

        canvas.drawColor(Color.BLACK);
        sprite.draw(canvas);

    }
}