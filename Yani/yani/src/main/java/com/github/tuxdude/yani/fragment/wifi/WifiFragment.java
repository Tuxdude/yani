package com.github.tuxdude.yani.fragment.wifi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.tuxdude.yani.R;
import com.github.tuxdude.yani.fragment.BaseFragment;

public class WifiFragment extends BaseFragment {
    public WifiFragment() {
        this.fragmentType = FragmentType.FRAGMENT_WIFI;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_connections_wifi, container, false);
        return rootView;
    }
}
