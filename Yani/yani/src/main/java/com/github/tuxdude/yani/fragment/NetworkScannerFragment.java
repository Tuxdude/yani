package com.github.tuxdude.yani.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.tuxdude.yani.R;

public class NetworkScannerFragment extends BaseFragment {
    public NetworkScannerFragment() {
        this.fragmentType = FragmentType.FRAGMENT_NETWORK_SCANNER;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_yani, container, false);
        return rootView;
    }
}
