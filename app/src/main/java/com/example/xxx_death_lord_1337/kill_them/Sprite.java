package com.example.xxx_death_lord_1337.kill_them;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;

/**
 * Created by XxX_DEATH_LORD_1337 on 29/01/2015.
 */
public class Sprite {
    private static final int BMP_ROWS = 4;
    private static final int BMP_COLUMNS = 3;
    private PointF pos = new PointF(0,0);
    private PointF vel = new PointF(0,0);
    private Bitmap bmp;
    private int currentFrame = 0;
    private int width, height;

    GameView gameView;

    public Sprite(GameView gameView, Bitmap bmp){
        this.gameView = gameView;
        this.bmp = bmp;
        this.width = bmp.getWidth() / BMP_COLUMNS;
        this.height = bmp.getHeight() / BMP_ROWS;
    }

    public void update() {
        // Move
        pos.x += vel.x;
        pos.y += vel.y;
        //
    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(bmp,pos.x,pos.y,null);
    }
}
