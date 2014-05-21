package com.github.tuxdude.yani.fragment.connections;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.tuxdude.yani.R;
import com.github.tuxdude.yani.utils.IpAddress;
import com.github.tuxdude.yani.utils.Logger;

public class WifiTabFragment extends Fragment {

    public static WifiTabFragment newInstance() {
        Logger.trace();
        return new WifiTabFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Logger.trace();
        View rootView = inflater.inflate(R.layout.fragment_connections_wifi, container, false);

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
        }

        return rootView;
    }
}
