/*
 *  Copyright (C) 2015 Ash [Tuxdude] <tuxdude.io@gmail.com>
 *
 *  This file is part of yani.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.tuxdude.yani.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.net.wifi.ScanResult;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;

import java.util.List;

import org.tuxdude.yani.network.MacAddressInfo;
import org.tuxdude.yani.util.Log;
import org.tuxdude.yani.util.MacAddressHelper;
import org.tuxdude.yani.wrapper.WifiManagerWrapper;
import org.tuxdude.yani.wifi.WifiChannelInfo;

public final class YaniService extends Service {
    private static final String TAG = "YaniService";

    // Heartbeat timeout in ms
    // The interval at which the Service checks for presence of clients,
    // and if none present, stops itself
    private static final int HEART_BEAT_TIMEOUT_MS = 10000;

    // Set to true when the service is active
    private boolean isStarted;
    // Set to true when there was any external Client activity with the Service
    private boolean mIsActiveSinceLastHeartBeat;

    private ServiceBinder mBinder;
    // Handler used within the Service
    private Handler mHandler;
    // BroadcastReceiver for receiving all the broadcast intents
    private ServiceReceiver mReceiver;
    // HeartBeat to check if the Service needs to be still running
    private HeartBeatRunnable mHeartBeatTimeout;
    // List of Wi-Fi channels supported by the device hardware
    private List<WifiChannelInfo> mWifiChannelList;
    // Set to true if the device supports Wi-Fi Dual Band
    private boolean mIsWifiDualBandSupported;
    // Wi-Fi STA Scan Results
    private List<ScanResult> mWifiStaScanResults;
    // Wi-Fi P2P Scan Results
    // private List

    // System Services
    private WifiManager mWifiManager;

    // Binder implementing the Service AIDL
    private final class ServiceBinder extends IYaniService.Stub {
        public void poke() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    pokeService();
                }
            });
        }
    }

    // A recurring timeout which checks if there was any activity from the Client
    // since the last time this timeout ran, and if none, stops the Service
    private final class HeartBeatRunnable implements Runnable {
        @Override
        public void run() {
            if (!mIsActiveSinceLastHeartBeat) {
                Log.i(TAG, "No activity from the Client since last timeout, stopping Service");
                stopService();
            } else {
                mIsActiveSinceLastHeartBeat = false;
                mHandler.postDelayed(this, HEART_BEAT_TIMEOUT_MS);
            }
        }
    };

    // Broadcast receiver
    private final class ServiceReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action.equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                Log.d(TAG, "Scan results available");
                updateWifiScanResults();
            }
        }
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "Creating Service");
        super.onCreate();

        // Create a Handler that runs on the Application's Main Thread
        // Since the Service runs in a separate process, no need for a separate Thread
        mHandler = new Handler(Looper.getMainLooper());

        // Create the Binder interface for allowing IPC
        mBinder = new ServiceBinder();

        // Create the Heart Beat monitor for Service Keep Alive
        mHeartBeatTimeout = new HeartBeatRunnable();

        // Obtain the handles for all the System Services
        getSystemServices();

        // Parse the MAC address prefix information (once when loaded)
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                MacAddressHelper.init(YaniService.this);
            }
        });
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "Destroying Service");
        super.onDestroy();

        cleanupService();
    }

    @Override
    public void onLowMemory() {
        Log.i(TAG, "Low Memory Notification");
        super.onLowMemory();
    }

    @Override
    public void onTaskRemoved (Intent rootIntent) {
        Log.i(TAG, "Service's Task Removed");
        super.onLowMemory();

        stopService();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Received Start Command - " + intent);
        if (intent != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    initService();
                }
            });
            return START_STICKY;
        } else {
            Log.w(TAG, "Service crashed previously and is now being restarted");
            // FIXME: Add any recovery logic required here
            return START_NOT_STICKY;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    // Initialize the service
    private void initService() {
        if (!isStarted) {
            // Setup the BroadcastReceiver
            setupReceiver();

            // Get the channel list and other device capabilities
            updateWifiCapabilities();

            // Initiate the Heart Beat Timeout
            mHandler.postDelayed(mHeartBeatTimeout, HEART_BEAT_TIMEOUT_MS);

            // Mark that the Service has started
            isStarted = true;
        } else {
            Log.i(TAG, "Service has already been initialized, not re-initializing");
        }
    }

    // Cleanup the service
    private void cleanupService() {
        if (isStarted) {
            Log.i(TAG, "Cleaning up Service");
            // Cleanup the BroadcastReceiver
            cleanupReceiver();

            isStarted = false;
        } else {
            Log.i(TAG, "Service has already been cleaned up, not cleaning up");
        }
    }

    // Stop the service, performing all necessary cleanup
    private void stopService() {
        Log.i(TAG, "Stopping Service");

        // Perform all cleanup
        cleanupService();

        // Request to stop the Service
        stopSelf();
    }

    private void pokeService() {
        Log.d(TAG, "Service Poked");
        mIsActiveSinceLastHeartBeat = true;
    }

    // Get all the System Service Handles
    private void getSystemServices() {
        mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
    }

    // Setup the BroadcastReceiver
    private void setupReceiver() {
        mReceiver = new ServiceReceiver();

        final IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);

        // Receive all Broadcast actions on the Handler thread
        registerReceiver(mReceiver, filter, null, mHandler);
    }

    // Cleanup the BroadcastReceiver
    private void cleanupReceiver() {
        unregisterReceiver(mReceiver);
    }

    // Get the updated Wi-Fi Scan results
    private void updateWifiScanResults() {
        mWifiStaScanResults = mWifiManager.getScanResults();
        if (Log.isDebugLoggable()) {
            Log.d(TAG, "Dumping Wi-FI STA Scan Results");
            for (ScanResult result : mWifiStaScanResults) {
                Log.d(TAG, "SSID: " + result.SSID +
                        " BSSID: " + result.BSSID +
                        " RSSI: " + result.level +
                        " Signal Level: " + WifiManager.calculateSignalLevel(result.level, 5) +
                        " freq: " + result.frequency + "MHz");
                MacAddressInfo macAddressInfo = MacAddressHelper.getInfo(result.BSSID);
                Log.d(TAG, "MAC Address Vendor: " + macAddressInfo.vendor +
                        " Address: " + macAddressInfo.lowerAddress);
            }
        }
    }

    // Update the Device's Wi-Fi Capability information
    private void updateWifiCapabilities() {
        mWifiChannelList = WifiManagerWrapper.getChannelList(mWifiManager);
        mIsWifiDualBandSupported = WifiManagerWrapper.isDualBandSupported(mWifiManager);

        if (mWifiChannelList != null) {
            if (Log.isDebugLoggable()) {
                Log.d(TAG, "Dumping Supported Wi-Fi Channel List:");
                for (WifiChannelInfo channelInfo : mWifiChannelList) {
                    Log.i(TAG, "Channel " + channelInfo.channelNum + " - " +
                            channelInfo.freqMHz + "MHz");
                }
                Log.d(TAG, "isWifiDualBandSupported: " + mIsWifiDualBandSupported);
            }
        } else {
            Log.w(TAG, "Unable to retrieve the device channel list capabilities");
        }
    }
}
