package com.example.xxx_death_lord_1337.kill_them;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class MainActivity extends Activity{

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new GameView(this));
    }

}
