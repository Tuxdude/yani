package com.github.tuxdude.yani.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.tuxdude.yani.R;

public class MobileFragment extends BaseFragment {

    public MobileFragment() {
        this.fragmentType = FragmentType.FRAGMENT_MOBILE;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_connections_wifi, container, false);
        return rootView;
    }
}
