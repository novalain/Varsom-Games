package com.example.xxx_death_lord_1337.kill_them;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;

public class Sprite {
    private static final int BMP_ROWS = 4;
    private static final int BMP_COLUMNS = 3;
    private Point pos;
    private Point vel;
    private Bitmap bmp;
    private int currentFrame = 0;
    private int width, height;
    private int spritePosY;

    GameView gameView;

    // Constructor
    public Sprite(GameView gameView, Bitmap bmp){

        pos = new Point(0,0);
        vel = new Point(0,0);
        this.gameView = gameView;
        this.bmp = bmp;
        width = bmp.getWidth() / BMP_COLUMNS;
        height = bmp.getHeight() / BMP_ROWS;
        spritePosY = 0;

    }

    private void newPosition(){

        //Left
        if (pos.x > gameView.getWidth() - bmp.getWidth() - vel.x) {
            vel.x = -20;
            spritePosY = 1;
        }

        //Right
        if (pos.x + vel.x <= 0) {
            vel.x = 20;
            spritePosY = 2;
        }

        pos.x = pos.x + vel.x;
    }

    public void update() {

        newPosition();
        currentFrame = ++currentFrame % BMP_COLUMNS;

    }

    public void draw(Canvas canvas){

        update();

        int srcX = currentFrame * width;
        int srcY = spritePosY*height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dist = new Rect(pos.x, pos.y, pos.x + width, pos.y + height);

        canvas.drawBitmap(bmp, src, dist, null);

    }
}
