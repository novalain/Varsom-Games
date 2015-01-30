package com.example.xxx_death_lord_1337.kill_them;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

public class GameView extends SurfaceView {

    private Bitmap bmp;
    private SurfaceHolder holder;
    private GameLoopThread gameLoopThread;
    private List<Sprite> sprites = new ArrayList<Sprite>();

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


                //sprite = new Sprite(this, bmp);

                for(int i = 0; i < 10; i++){

                    sprites.add(createNewSprite());

                }

                gameLoopThread.setRunning(true);
                gameLoopThread.start();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

        });

    }

    public boolean onTouchEvent(MotionEvent event){

        for(int i = 0; i < sprites.size(); i++){

            if(sprites.get(i).hasCollided(event.getX(), event.getY())){

                sprites.remove(sprites.get(i));

            }
        }

        return super.onTouchEvent(event);
    }

    private Sprite createNewSprite(){

        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.lolfigur);

        return new Sprite(this, bmp);

    }

    public void draw(Canvas canvas){

        canvas.drawColor(Color.BLACK);

        for(int i = 0; i < sprites.size(); i++){

            sprites.get(i).draw(canvas);

        }

    }
}