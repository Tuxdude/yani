package com.github.tuxdude.yani.fragment.connections.tab;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.tuxdude.yani.R;
import com.github.tuxdude.yani.utils.Logger;

public class BluetoothTabFragment extends Fragment {

    public static BluetoothTabFragment newInstance() {
        return new BluetoothTabFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Logger.trace();
        View rootView = inflater.inflate(R.layout.tab_connections_bluetooth, container, false);

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
        }

        return rootView;
    }
}
