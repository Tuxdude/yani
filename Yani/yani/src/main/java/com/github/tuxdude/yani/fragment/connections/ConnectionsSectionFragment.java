package com.github.tuxdude.yani.fragment.connections;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.tuxdude.yani.R;
import com.github.tuxdude.yani.fragment.SwipeableTabsFragment;
import com.github.tuxdude.yani.fragment.FragmentType;
import com.github.tuxdude.yani.utils.Logger;

public class ConnectionsSectionFragment extends SwipeableTabsFragment {

    public static ConnectionsSectionFragment newInstance() {
        Logger.trace();
        return new ConnectionsSectionFragment();
    }

    @Override
    public FragmentType getFragmentType() {
        return FragmentType.FRAGMENT_CONNECTIONS;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Logger.trace();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Logger.trace();
        return inflater.inflate(R.layout.fragment_connections, container, false);
    }


    @Override
    public void onStart() {
        Logger.trace();
        super.onStart();
    }

    @Override
    public void onResume() {
        Logger.trace();
        super.onResume();
    }

    @Override
    public void onPause() {
        Logger.trace();
        super.onPause();
    }

    @Override
    public void onStop() {
        Logger.trace();
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Logger.trace();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Logger.trace();
        super.onDestroy();
    }
}
