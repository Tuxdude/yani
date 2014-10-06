package org.tuxdude.yani.fragment.connections;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tuxdude.yani.R;
import org.tuxdude.yani.common.Constants;
import org.tuxdude.yani.fragment.common.SectionFragmentType;
import org.tuxdude.yani.fragment.common.SwipeableTabsFragment;
import org.tuxdude.yani.fragment.connections.tab.BluetoothTabFragment;
import org.tuxdude.yani.fragment.connections.tab.MobileTabFragment;
import org.tuxdude.yani.fragment.connections.tab.WifiTabFragment;


public class ConnectionsSectionFragment extends SwipeableTabsFragment {

    private static final Logger LOGGER = LoggerFactory.getLogger(Constants.LOGGER_NAME);

    public static ConnectionsSectionFragment newInstance() {
        LOGGER.trace(Constants.EMPTY_STRING);
        return new ConnectionsSectionFragment();
    }

    // Mapping of tab position to Fragment class name (which provides title)
    @Override
    public SectionFragmentType getFragmentType() {
        return SectionFragmentType.SECTION_CONNECTIONS;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        LOGGER.trace(Constants.EMPTY_STRING);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LOGGER.trace(Constants.EMPTY_STRING);
        return inflater.inflate(R.layout.section_connections, container, false);
    }

    @Override
    public void onStart() {
        LOGGER.trace(Constants.EMPTY_STRING);
        super.onStart();
    }

    @Override
    public void onResume() {
        LOGGER.trace(Constants.EMPTY_STRING);
        super.onResume();
    }

    @Override
    public void onPause() {
        LOGGER.trace(Constants.EMPTY_STRING);
        super.onPause();
    }

    @Override
    public void onStop() {
        LOGGER.trace(Constants.EMPTY_STRING);
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        LOGGER.trace(Constants.EMPTY_STRING);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        LOGGER.trace(Constants.EMPTY_STRING);
        super.onDestroy();
    }

    @Override
    public Fragment getTab(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = WifiTabFragment.newInstance();
                break;
            case 1:
                fragment = MobileTabFragment.newInstance();
                break;
            case 2:
                fragment = BluetoothTabFragment.newInstance();
                break;
            default:
                break;
        }
        return fragment;
    }

    @Override
    public int getTabCount() {
        return 3;
    }

    @Override
    public CharSequence getTabTitle(int position) {
        String title = null;
        switch (position) {
            case 0:
                title = getString(R.string.title_tab_connections_wifi);
                break;
            case 1:
                title = getString(R.string.title_tab_connections_mobile);
                break;
            case 2:
                title = getString(R.string.title_tab_connections_bluetooth);
                break;
            default:
                break;
        }
        return title;
    }
}
