package com.github.tuxdude.yani.fragment;

public class YaniFragmentManager {

    private static YaniFragmentManager manager = null;
    private BaseFragment statusFragment = null;
    private BaseFragment wifiToolsFragment = null;
    private BaseFragment networkScannerFragment = null;

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
                if (statusFragment == null) {
                    statusFragment = ConnectionsFragment.newInstance();
                }
                fragment = statusFragment;
                break;
            case FRAGMENT_MOBILE:
                if (wifiToolsFragment == null) {
                    wifiToolsFragment = new MobileFragment();
                }
                fragment = wifiToolsFragment;
                break;
            case FRAGMENT_WIFI:
                if (networkScannerFragment == null) {
                    networkScannerFragment = new WifiFragment();
                }
                fragment = networkScannerFragment;
                break;
            default:
                break;
        }
        return fragment;
    }
}
