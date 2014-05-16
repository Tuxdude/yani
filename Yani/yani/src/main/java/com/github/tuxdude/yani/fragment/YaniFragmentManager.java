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
            case FRAGMENT_STATUS:
                if (statusFragment == null) {
                    statusFragment = new StatusFragment();
                }
                fragment = statusFragment;
                break;
            case FRAGMENT_WIFI_TOOLS:
                if (wifiToolsFragment == null) {
                    wifiToolsFragment = new WifiToolsFragment();
                }
                fragment = wifiToolsFragment;
                break;
            case FRAGMENT_NETWORK_SCANNER:
                if (networkScannerFragment == null) {
                    networkScannerFragment = new NetworkScannerFragment();
                }
                fragment = networkScannerFragment;
                break;
            default:
                break;
        }
        return fragment;
    }
}
