package org.tuxdude.yani.activity;

import android.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tuxdude.yani.R;
import org.tuxdude.yani.common.Constants;
import org.tuxdude.yani.fragment.common.BaseSectionFragment;
import org.tuxdude.yani.fragment.common.ISectionFragment;
import org.tuxdude.yani.fragment.common.SectionFragmentsManager;
import org.tuxdude.yani.fragment.navigationdrawer.NavigationDrawerFragment;
import org.tuxdude.yani.network.NetworkBroadcastListener;


public class MainActivity extends FragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
        BaseSectionFragment.FragmentEventsListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(Constants.LOGGER_NAME);
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private CharSequence mTitle;

    private static final String TAG_CURRENT_SECTION = "tag_current_section";
    private static final String KEY_LAST_TITLE = "key_last_title";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LOGGER.trace(Constants.EMPTY_STRING);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);

        if (savedInstanceState != null) {
            mTitle = savedInstanceState.getCharSequence(KEY_LAST_TITLE);
        }
        else {
            mTitle = getTitle();
        }

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    protected void onResume() {
        LOGGER.trace(Constants.EMPTY_STRING);
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence(KEY_LAST_TITLE, mTitle);
    }

    @Override
    protected void onPause() {
        LOGGER.trace(Constants.EMPTY_STRING);
        super.onPause();

        // Unregister receiver
        unregisterReceiver(NetworkBroadcastListener.getInstance());
    }

    @Override
    public void onNavigationDrawerItemSelected(int oldPosition, int newPosition) {
        LOGGER.debug("onNavigationDrawerItemSelected oldPosition: " +
                oldPosition + " newPosition: " + newPosition);

        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = null;

        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (oldPosition == newPosition && newPosition >= 0) {
            LOGGER.debug("Trying to reuse old fragment");
            fragment = fragmentManager.findFragmentByTag(TAG_CURRENT_SECTION);
            if (fragment == null) {
                LOGGER.debug("Could not find the fragment by tag");
            }
        }

        if (null == fragment) {
            fragment = SectionFragmentsManager.getManager().getFragment(newPosition);
            transaction.replace(R.id.container, fragment, TAG_CURRENT_SECTION);
        }

        transaction.commit();

        if (oldPosition == newPosition && newPosition >= 0) {
            restoreActionBar();
        }
    }

    @Override
    public void onFragmentAttached(ISectionFragment fragmentInfo) {
        LOGGER.trace(Constants.EMPTY_STRING);
        if (fragmentInfo != null) {
            mTitle = fragmentInfo.getSectionTitle();
        }
        else {
            LOGGER.error("onFragmentAttached() received null fragmentInfo, unable to set Title");
        }
    }

    public void restoreActionBar() {
        LOGGER.trace(Constants.EMPTY_STRING);
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        LOGGER.trace(Constants.EMPTY_STRING);
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
        LOGGER.trace(Constants.EMPTY_STRING);
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
