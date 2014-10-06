package org.tuxdude.yani.fragment.connections.tab;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tuxdude.yani.R;
import org.tuxdude.yani.common.Constants;
import org.tuxdude.yani.utils.IpAddress;


public class WifiTabFragment extends Fragment {

    private static final Logger LOGGER = LoggerFactory.getLogger(Constants.LOGGER_NAME);

    public static WifiTabFragment newInstance() {
        LOGGER.trace(Constants.EMPTY_STRING);
        return new WifiTabFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LOGGER.trace(Constants.EMPTY_STRING);
        View rootView = inflater.inflate(R.layout.tab_connections_wifi, container, false);

        TextView tv = (TextView)rootView.findViewById(R.id.section_label);
        if (tv != null) {
            tv.append("Wifi Status\n");

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
