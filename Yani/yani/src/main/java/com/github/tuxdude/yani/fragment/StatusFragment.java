package com.github.tuxdude.yani.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.tuxdude.yani.R;

public class StatusFragment extends BaseFragment {

    public StatusFragment() {
        this.fragmentType = FragmentType.FRAGMENT_STATUS;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_yani, container, false);
        return rootView;
    }
}
