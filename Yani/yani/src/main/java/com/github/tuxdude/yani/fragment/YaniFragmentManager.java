package com.github.tuxdude.yani.fragment;

import com.github.tuxdude.yani.fragment.connections.ConnectionsFragment;
import com.github.tuxdude.yani.fragment.mobile.MobileFragment;
import com.github.tuxdude.yani.fragment.wifi.WifiFragment;

public class YaniFragmentManager {

    private static YaniFragmentManager manager = null;

    public static YaniFragmentManager getManager() {
        if (manager == null) {
            manager = new YaniFragmentManager();
        }
        return manager;
    }

    private YaniFragmentManager() {
        // Constructor
    }

    public BaseFragment getFragment(BaseFragment.FragmentType type) {
        BaseFragment fragment = null;
        switch (type) {
            case FRAGMENT_CONNECTIONS:
                fragment = ConnectionsFragment.newInstance();
                break;
            case FRAGMENT_MOBILE:
                fragment = new MobileFragment();
                break;
            case FRAGMENT_WIFI:
                fragment = new WifiFragment();
                break;
            default:
                break;
        }
        return fragment;
    }
}
