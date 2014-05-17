package com.github.tuxdude.yani.fragment;

import android.app.Activity;
import android.app.Fragment;

import com.github.tuxdude.yani.YaniActivity;
import com.github.tuxdude.yani.utils.Logger;

public class BaseFragment extends Fragment {

    public enum FragmentType {
        FRAGMENT_STATUS,
        FRAGMENT_WIFI_TOOLS,
        FRAGMENT_NETWORK_SCANNER;

        public static FragmentType values[] = FragmentType.values();

        public static FragmentType valueAt(int position)
        {
            return values[position];
        }
    }

    protected FragmentType fragmentType = null;

    public BaseFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        Logger.trace();
        super.onAttach(activity);
        ((YaniActivity) activity).onFragmentAttached(fragmentType);
    }
}
