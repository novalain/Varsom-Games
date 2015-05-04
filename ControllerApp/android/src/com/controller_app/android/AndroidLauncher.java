package com.controller_app.android;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.controller_app.Main;

public class AndroidLauncher extends AndroidApplication {
	WifiManager.MulticastLock lock = null;
	WifiManager wifi = null;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new Main(), config);

		//Unlocks the abuility to use multicast
		wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		if (wifi != null) {
			lock = wifi.createMulticastLock("mylock");
			//lock.setReferenceCounted(true);
			lock.acquire();
		}

		// Use when ip is recived
		//lock.release();


	}
}
