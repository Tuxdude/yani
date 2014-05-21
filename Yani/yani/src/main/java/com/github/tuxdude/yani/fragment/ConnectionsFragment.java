package com.github.tuxdude.yani.fragment;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.tuxdude.yani.utils.IpAddress;
import com.github.tuxdude.yani.R;
import com.github.tuxdude.yani.utils.Logger;

import org.w3c.dom.Text;

import java.lang.reflect.Field;
import java.util.Locale;

public class ConnectionsFragment extends BaseFragment implements ActionBar.TabListener {

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Logger.trace();
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            Logger.trace();
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Logger.trace();
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            Logger.trace();
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
            Logger.trace();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            Logger.trace();
            View rootView = inflater.inflate(R.layout.child_fragment_connections, container, false);
/*            TextView tv = (TextView)getActivity().findViewById(R.id.section_label);
            if (tv != null) {
                tv.setText("This is section: " + getArguments().getInt(ARG_SECTION_NUMBER));
            }*/
            return rootView;
        }
    }

    public ConnectionsFragment() {
        this.fragmentType = FragmentType.FRAGMENT_CONNECTIONS;
    }

    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Logger.trace();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Logger.trace();
        View rootView = inflater.inflate(R.layout.fragment_connections_tabs, container, false);

        /*
        TextView tv = (TextView)rootView.findViewById(R.id.section_label);
        if (tv != null) {
            tv.append("Connection Status\n");
            ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

            if (connectivityManager != null) {
                NetworkInfo networkInfoList[] = connectivityManager.getAllNetworkInfo();
                if (networkInfoList != null) {
                    for (NetworkInfo networkInfo : networkInfoList) {
                        tv.append(networkInfo.toString() + "\n");
                    }
                }
            }

            WifiManager wifiManager = (WifiManager)getActivity().getSystemService(Context.WIFI_SERVICE);
            if (wifiManager != null) {
                if (wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED) {

                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                    if (wifiInfo != null) {
                        tv.append("SSID: " + wifiInfo.getSSID() + "\n");
                        tv.append("Link Speed: " + wifiInfo.getLinkSpeed() + " " + WifiInfo.LINK_SPEED_UNITS + "\n");
                        tv.append("IP Address: " + new IpAddress(wifiInfo.getIpAddress()) + "\n");
                        tv.append("MAC Address: " + wifiInfo.getMacAddress() + "\n");
                    }

                    DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
                    if (dhcpInfo != null) {
                        tv.append("DHCP Info:\n");
                        tv.append("IP Address: " + new IpAddress(dhcpInfo.ipAddress) + "\n");
                        tv.append("Netmask: " + new IpAddress(dhcpInfo.netmask) + "\n");
                        tv.append("Gateway: " + new IpAddress(dhcpInfo.gateway) + "\n");
                        tv.append("DNS1: " + new IpAddress(dhcpInfo.dns1) + "\n");
                        tv.append("DNS2: " + new IpAddress(dhcpInfo.dns2) + "\n");
                        tv.append("DHCP Server IP: " + new IpAddress(dhcpInfo.serverAddress) + "\n");
                    }
                } else {
                    tv.append("Wifi is not enabled\n");
                }
            }
        }*/

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Logger.trace();
        super.onViewCreated(view, savedInstanceState);

        // Set up the action bar.
        final ActionBar actionBar = getActivity().getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
        actionBar.show();

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

        final ActionBar actionBar = getActivity().getActionBar();
        actionBar.removeAllTabs();
    }

    @Override
    public void onDestroy() {
        Logger.trace();
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Logger.trace();
        super.onDetach();

        // The following is a workaround for the ChildFragmentManager
        // ending up with a broken state when the fragment is detached
        //
        // Thanks to:
        // http://stackoverflow.com/a/15656428/380757
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }
}
