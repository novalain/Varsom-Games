package com.example.xxx_death_lord_1337.kill_them;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity{
    // creating a tag to see where the logging comes from
    private static final String TAG = MainActivity.class.getSimpleName();

    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Send content to game view if game should be started
        setContentView(new GameView(this));

    }

    @Override
    protected void onDestroy(){

        Log.d(TAG, "Destroying");
        super.onDestroy();

    }
    @Override
    protected void onStop(){

        Log.d(TAG, "Stopping");
        super.onStop();

    }

}
