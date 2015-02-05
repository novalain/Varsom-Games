package com.example.xxx_death_lord_1337.kill_them;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import java.util.Random;

public class Sprite {
    private static final int BMP_ROWS = 4;
    private static final int BMP_COLUMNS = 3;
    private static final int MAX_SPEED = 5;
    private Point pos;
    private Point vel;
    private Bitmap bmp;
    private int currentFrame = 0;
    private int width, height;
    private GameView gameView

    // Constructor
    public Sprite(GameView gameView, Bitmap bmp){

        Random rand = new Random();

        pos = new Point(rand.nextInt(gameView.getWidth() - width),rand.nextInt(gameView.getHeight() - height));
        vel = new Point(0,0);
        this.gameView = gameView;
        this.bmp = bmp;
        width = bmp.getWidth() / BMP_COLUMNS;
        height = bmp.getHeight() / BMP_ROWS;

        vel.x = rand.nextInt(MAX_SPEED * 2) - MAX_SPEED;
        vel.y = rand.nextInt(MAX_SPEED * 2) - MAX_SPEED;

    }

    private void newPosition(){

        if (pos.x >= gameView.getWidth() - width - vel.x || pos.x + vel.x <= 0) {
            vel.x = -vel.x;
        }

        pos.x = pos.x + vel.x;

        if(pos.y >= gameView.getHeight() - height - vel.y || pos.y + vel.y <= 0){

            vel.y = -vel.y;
        }

        pos.y = pos.y + vel.y;

    }

    public void update() {

        newPosition();
        currentFrame = ++currentFrame % BMP_COLUMNS;

    }

    public boolean hasCollided(float x2, float y2) {

        return x2 > pos.x && x2 < pos.x + width && y2 > pos.y && y2 < pos.y + height;

    }

    public void draw(Canvas canvas){

        update();

        int srcX = currentFrame * width;
        int srcY = 1*height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dist = new Rect(pos.x, pos.y, pos.x + width, pos.y + height);

        canvas.drawBitmap(bmp, src, dist, null);

    }
}
