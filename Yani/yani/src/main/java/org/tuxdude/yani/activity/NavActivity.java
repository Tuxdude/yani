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
package org.tuxdude.yani.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;

import org.tuxdude.yani.R;
import org.tuxdude.yani.service.IYaniService;
import org.tuxdude.yani.util.Log;
import org.tuxdude.yani.util.MetadataHelper;

public class NavActivity extends ActionBarActivity {
    private static final String TAG = "NavActivity";

    private static final int HEART_BEAT_TIMEOUT_MS = 8000;

    private Handler mHandler;
    private IYaniService mService;
    private Connection mServiceConnection;
    private HeartBeatRunnable mHeartBeatTimeout;
    private boolean mIsRunning;

    // ServiceConnection class for binding to the Service
    private final class Connection implements ServiceConnection {
        private void postOnHandler(Runnable runnable) {
            if (mHandler != null) {
                mHandler.post(runnable);
            }
        }

        // Called when the connection with the service is established
        public void onServiceConnected(ComponentName className, IBinder service) {
            final IYaniService yaniService = IYaniService.Stub.asInterface(service);
            postOnHandler(new Runnable() {
                @Override
                public void run() {
                    if (mIsRunning) {
                        Log.i(TAG, "Bound to service");
                        mService = yaniService;
                    } else {
                        Log.w(TAG, "Bound to service, when the Activity is not running," +
                                " explicitly unbinding from the Service");
                        unbindService();
                    }
                }
            });
        }

        // Called when the connection with the service disconnects unexpectedly
        public void onServiceDisconnected(ComponentName className) {
            Log.e(TAG, "Service has unexpectedly disconnected");
            postOnHandler(new Runnable() {
                @Override
                public void run() {
                    mService = null;
                }
            });
        }
    };

    // A recurring timeout which checks if there was any activity from the Client
    // since the last time this timeout ran, and if none, stops the Service
    private final class HeartBeatRunnable implements Runnable {
        @Override
        public void run() {
            if (mIsRunning) {
                if (mService != null) {
                    try {
                        mService.poke();
                    } catch (RemoteException exception) {
                        Log.e(TAG, "RemoteException while poking the Service", exception);
                    }
                } else {
                    Log.w(TAG, "HeartBeatRunnable - Not yet bound to service");
                }
                mHandler.postDelayed(this, HEART_BEAT_TIMEOUT_MS);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "Creating Activity");
        super.onCreate(savedInstanceState);

        // Initialize MetadataHelper in the very beginning
        MetadataHelper.init(this);

        // Create a Handler for the UI thread
        mHandler = new Handler(Looper.getMainLooper());

        // Create a ServiceConnection object for binding to the Service
        mServiceConnection = new Connection();

        // Create a Runnable which periodically pokes the Service
        // to keep it alive
        mHeartBeatTimeout = new HeartBeatRunnable();

        // Set Main Activity Layout
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitle(R.string.app_name);
        }
    }

    @Override
    protected void onPause() {
        Log.i(TAG, "Pausing Activity");
        super.onPause();

        mIsRunning = false;
        unbindService();
        mHandler.removeCallbacks(mHeartBeatTimeout);
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "Resuming Activity");
        super.onResume();

        mIsRunning = true;
        startAndBindService();
        mHandler.postDelayed(mHeartBeatTimeout, HEART_BEAT_TIMEOUT_MS);
    }

    private void startAndBindService() {
        final Intent intent = new Intent();
        intent.setComponent(new ComponentName(
                    MetadataHelper.getValue(MetadataHelper.SERVICE_PACKAGE),
                    MetadataHelper.getValue(MetadataHelper.SERVICE_CLASS)));
        Log.i(TAG, "Starting and Binding Service");
        startService(intent);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
    }

    private void unbindService() {
        Log.i(TAG, "Unbinding Service");
        unbindService(mServiceConnection);
    }
}
