package org.tuxdude.yani.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;

import org.tuxdude.yani.utils.Logger;

public class NetworkBroadcastListener extends BroadcastReceiver {

    private static NetworkBroadcastListener instance = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        Logger.trace();
        if (intent.getAction().equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)) {
            //Toast.makeText(context, "Airplane Mode Changed!", Toast.LENGTH_LONG).show();
            Logger.d("Airplane Mode Changed");
        }
        else if (intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
            //Toast.makeText(context, "Wifi State Changed!", Toast.LENGTH_LONG).show();
            Logger.d("Wifi State Changed");
        }
        else if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
            //Toast.makeText(context, "Network State Changed!", Toast.LENGTH_LONG).show();
            Logger.d("Network State Changed");
        }
        else {
            Logger.d("Unhandled intent: " + intent.getAction().toString());
        }
    }

    private NetworkBroadcastListener() {
        // Empty constructor
    }

    public static NetworkBroadcastListener getInstance() {
        if (instance == null) {
            instance = new NetworkBroadcastListener();
        }
        return instance;
    }
}
