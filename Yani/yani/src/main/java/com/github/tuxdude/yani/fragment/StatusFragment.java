package com.github.tuxdude.yani.fragment;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.tuxdude.yani.R;

public class StatusFragment extends BaseFragment {

    public StatusFragment() {
        this.fragmentType = FragmentType.FRAGMENT_STATUS;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_yani, container, false);

        TextView tv = (TextView)rootView.findViewById(R.id.section_label);
        if (tv != null) {
            tv.append("HELLO!\n");

            WifiManager wifiManager = (WifiManager)getActivity().getSystemService(Context.WIFI_SERVICE);

            android.net.wifi.WifiInfo wifiInfo = wifiManager.getConnectionInfo();

            tv.append(wifiInfo.getSSID() + "\n");
        }

        return rootView;
    }
}
