package org.tuxdude.yani.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tuxdude.yani.common.Constants;


public class NetworkBroadcastListener extends BroadcastReceiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(Constants.LOGGER_NAME);
    private static NetworkBroadcastListener instance = null;

    @Override
    public void onReceive(Context context, Intent intent) {
        LOGGER.trace(Constants.EMPTY_STRING);
        if (intent.getAction().equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)) {
            //Toast.makeText(context, "Airplane Mode Changed!", Toast.LENGTH_LONG).show();
            LOGGER.debug("Airplane Mode Changed");
        }
        else if (intent.getAction().equals(WifiManager.WIFI_STATE_CHANGED_ACTION)) {
            //Toast.makeText(context, "Wifi State Changed!", Toast.LENGTH_LONG).show();
            LOGGER.debug("Wifi State Changed");
        }
        else if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
            //Toast.makeText(context, "Network State Changed!", Toast.LENGTH_LONG).show();
            LOGGER.debug("Network State Changed");
        }
        else {
            LOGGER.debug("Unhandled intent: " + intent.getAction().toString());
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
