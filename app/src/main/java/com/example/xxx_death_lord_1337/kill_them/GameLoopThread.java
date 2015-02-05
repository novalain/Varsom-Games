package com.example.xxx_death_lord_1337.kill_them;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameLoopThread extends Thread {

    // desired fps
    private final static int MAX_FPS = 50;
    // maximum number of frames to be skipped
    private final static int MAX_FRAME_SKIPS = 5;
    // the frame period
    private final static int FRAME_PERIOD = 1000/MAX_FPS;

    private static final String TAG = GameLoopThread.class.getSimpleName();
    private GameView gameView;
    private SurfaceHolder surfaceHolder;
    private boolean running = false;

    public GameLoopThread(GameView gameView, SurfaceHolder surfaceHolder) {
        super();
        this.gameView = gameView;
        this.surfaceHolder = surfaceHolder;
    }

    public void setRunning(boolean running) {

        this.running = running;

    }

    @Override
    public void run() {

        Log.d(TAG, "Starting game loop");

        long beginTime; // time when cicle begun
        long timeDiff; // time it took for the cycle to execute
        int sleepTime = 0; // ms to sleep ( < 0 if we are behind )
        int framesSkipped; // number of frames being skipped

        while (running) {

            Canvas c = null;
            //Render state to the screen
            try {
                c = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    beginTime = System.currentTimeMillis();
                    framesSkipped = 0;  // resetting the frames skipped
                    // update game state
                    this.gameView.update();
                    // render state to the screen
                    // draws the canvas on the panel
                    this.gameView.render(c);
                    // calculate how long did the cycle take
                    timeDiff = System.currentTimeMillis() - beginTime;
                    // calculate sleep time
                    sleepTime = (int)(FRAME_PERIOD - timeDiff);

                    if (sleepTime > 0) {
                        // if sleepTime > 0 we're OK
                        try {
                            // send the thread to sleep for a short period
                            // very useful for battery saving
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {}
                    }
                    while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
                        // we need to catch up
                        // update without rendering
                        this.gameView.update();
                        // add frame period to check if in next frame
                        sleepTime += FRAME_PERIOD;
                        framesSkipped++;
                    }
                }
            } finally {
                // in case of an exception the surface is not left in
                // an inconsistent state
                if (c != null) {
                    surfaceHolder.unlockCanvasAndPost(c);
                }
            }   // end finally

        }

    }

}
