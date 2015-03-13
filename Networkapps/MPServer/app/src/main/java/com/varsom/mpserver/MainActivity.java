package com.varsom.mpserver;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.esotericsoftware.minlog.Log;

import java.io.IOException;

import static com.esotericsoftware.minlog.Log.LEVEL_DEBUG;


public class MainActivity extends ActionBarActivity {

    MPServer server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //The buttons and the textviews are imported from the XML-file
        final Button serverStart = (Button) findViewById(R.id.start);
        final Button serverStop = (Button) findViewById(R.id.stop);
        final TextView textElement = (TextView) findViewById(R.id.textView);
        final TextView textMessage = (TextView) findViewById(R.id.textmessage);

        //Start a server if the button "Start server" is clicked
        serverStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                try {
                    server = new MPServer(textMessage);
                    textElement.setText("Server is running"); //leave this line to assign a specific text
                    Log.set(LEVEL_DEBUG);

                } catch (IOException e) {
                    e.printStackTrace();
                    textElement.setText("Something went wrong");
                }
            }
        });

        //Stop the server if the button "Stop server" is clicked
        serverStop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                server.stop();
                textElement.setText("Server stopped"); //leave this line to assign a specific text
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
