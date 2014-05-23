package org.tuxdude.yani.fragment.connections;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.tuxdude.yani.R;
import org.tuxdude.yani.fragment.common.SectionFragmentType;
import org.tuxdude.yani.fragment.common.SwipeableTabsFragment;
import org.tuxdude.yani.fragment.connections.tab.BluetoothTabFragment;
import org.tuxdude.yani.fragment.connections.tab.MobileTabFragment;
import org.tuxdude.yani.fragment.connections.tab.WifiTabFragment;
import org.tuxdude.yani.utils.Logger;

public class ConnectionsSectionFragment extends SwipeableTabsFragment {

    // Mapping of tab position to Fragment class name (which provides title)

    public static ConnectionsSectionFragment newInstance() {
        Logger.trace();
        return new ConnectionsSectionFragment();
    }

    @Override
    public SectionFragmentType getFragmentType() {
        return SectionFragmentType.SECTION_CONNECTIONS;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Logger.trace();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Logger.trace();
        return inflater.inflate(R.layout.section_connections, container, false);
    }

    @Override
    public void onStart() {
        Logger.trace();
        super.onStart();
    }

    @Override
    public void onResume() {
        Logger.trace();
        super.onResume();
    }

    @Override
    public void onPause() {
        Logger.trace();
        super.onPause();
    }

    @Override
    public void onStop() {
        Logger.trace();
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Logger.trace();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Logger.trace();
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
