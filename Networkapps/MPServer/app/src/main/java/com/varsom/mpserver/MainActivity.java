package com.varsom.mpserver;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
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

import static com.esotericsoftware.minlog.Log.LEVEL_DEBUG;


public class MainActivity extends ActionBarActivity {

    MPServer server;
    boolean handledClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //The buttons and the textviews are imported from the XML-file
        final Button serverStart = (Button) findViewById(R.id.start);
        final Button serverStop = (Button) findViewById(R.id.stop);
        final Button serverSend = (Button) findViewById(R.id.sendbutton);
        final Button connectedDevicesBtn = (Button) findViewById(R.id.cnctbutton);
        final TextView textElement = (TextView) findViewById(R.id.textView);
        final EditText sendMessage = (EditText) findViewById(R.id.sendText);

        int connectedDevices;
        WifiManager wifi = (WifiManager)getSystemService(WIFI_SERVICE);
        WifiInfo connectionInfo = wifi.getConnectionInfo();
        int ip = connectionInfo.getIpAddress();
        final String IP = String.format(
                "%d.%d.%d.%d",
                (ip & 0xff),
                (ip >> 8 & 0xff),
                (ip >> 16 & 0xff),
                (ip >> 24 & 0xff));

        //Start a server if the button "Start server" is clicked
        serverStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(!handledClick) {
                    try {
                        server = new MPServer(sendMessage);
                        textElement.setText("Server is running and your IP Adress is: "); //leave this line to assign a specific text
                        textElement.append(IP);
                        Log.set(LEVEL_DEBUG);
                        serverStart.setEnabled(false);
                        serverStop.setEnabled(true);

                    } catch (IOException e) {
                        e.printStackTrace();
                        textElement.setText("Something went wrong");
                    }
                    handledClick = true;
                }
            }
        });

        //Stop the server if the button "Stop server" is clicked
        serverStop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(handledClick) {
                    server.stop();
                    textElement.setText("Server stopped"); //leave this line to assign a specific text
                    handledClick = false;
                    serverStart.setEnabled(true);
                    serverStop.setEnabled(false);
                }
            }
        });

        serverSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                server.sendMassMessage(sendMessage);
                sendMessage.setText("");

            }
        });

        connectedDevicesBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.info("TESTAR","No. of connected devices: " + server.getServer().getConnections().length);
                //server.sendMassMessage(sendMessage);
                //sendMessage.setText("");

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
