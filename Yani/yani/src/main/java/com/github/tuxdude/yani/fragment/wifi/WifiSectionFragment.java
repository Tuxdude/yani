package com.github.tuxdude.yani.fragment.wifi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.tuxdude.yani.R;
import com.github.tuxdude.yani.fragment.BaseSectionFragment;
import com.github.tuxdude.yani.fragment.FragmentType;

public class WifiSectionFragment extends BaseSectionFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_connections_wifi, container, false);
        return rootView;
    }

    @Override
    public FragmentType getFragmentType() {
        return FragmentType.FRAGMENT_WIFI;
    }
}
