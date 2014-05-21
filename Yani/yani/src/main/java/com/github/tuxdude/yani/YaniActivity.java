package com.github.tuxdude.yani;

import android.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import com.github.tuxdude.yani.fragment.BaseFragment;
import com.github.tuxdude.yani.fragment.navigationdrawer.NavigationDrawerFragment;
import com.github.tuxdude.yani.fragment.YaniFragmentManager;
import com.github.tuxdude.yani.network.NetworkBroadcastListener;
import com.github.tuxdude.yani.utils.Logger;


public class YaniActivity extends FragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Logger.trace();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yani);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    protected void onResume() {
        Logger.trace();
        super.onResume();

        // Register receiver
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        filter.addAction(WifiManager.NETWORK_IDS_CHANGED_ACTION);
        filter.addAction(WifiManager.RSSI_CHANGED_ACTION);
        filter.addAction(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION);
        registerReceiver(NetworkBroadcastListener.getInstance(), filter);
    }

    @Override
    protected void onPause() {
        Logger.trace();
        super.onPause();

        // Unregister receiver
        unregisterReceiver(NetworkBroadcastListener.getInstance());
    }

    @Override
    public void onNavigationDrawerItemSelected(BaseFragment.FragmentType type) {
        Logger.trace();
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, YaniFragmentManager.getManager().getFragment(type))
                .commit();
    }

    public void onFragmentAttached(BaseFragment.FragmentType type) {
        Logger.trace();
        switch (type) {
            case FRAGMENT_CONNECTIONS:
                mTitle = getString(R.string.title_status);
                break;
            case FRAGMENT_MOBILE:
                mTitle = getString(R.string.title_wifi_tools);
                break;
            case FRAGMENT_WIFI:
                mTitle = getString(R.string.title_network_scanner);
                break;
        }
    }

    public void restoreActionBar() {
        Logger.trace();
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Logger.trace();
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.yani, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Logger.trace();
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
