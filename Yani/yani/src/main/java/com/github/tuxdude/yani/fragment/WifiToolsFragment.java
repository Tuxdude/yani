package com.github.tuxdude.yani.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.tuxdude.yani.R;

public class WifiToolsFragment extends BaseFragment {

    public WifiToolsFragment() {
        this.fragmentType = FragmentType.FRAGMENT_WIFI_TOOLS;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_yani, container, false);
        return rootView;
    }
}
