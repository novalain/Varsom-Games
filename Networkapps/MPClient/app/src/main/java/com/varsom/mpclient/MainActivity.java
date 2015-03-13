package com.varsom.mpclient;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.esotericsoftware.minlog.Log;

import java.io.IOException;

import static com.varsom.mpclient.R.id.textField;


public class MainActivity extends ActionBarActivity {

    public MPClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Make it possible to change network code in other class files
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Connect buttons and textfields from xml file
        final Button connect = (Button) findViewById(R.id.connect);
        final EditText ipAddress = (EditText)findViewById(R.id.sendIp);
        final EditText textMessage = (EditText)findViewById(R.id.sendMessage);
        final TextView t=(TextView)findViewById(textField);

        // Start a client and tries to connect to a server
        connect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){

                try {
                    client = new MPClient(ipAddress, textMessage, t);
                    Log.set(Log.LEVEL_DEBUG);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
