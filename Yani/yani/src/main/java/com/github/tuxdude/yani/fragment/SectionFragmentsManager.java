package com.github.tuxdude.yani.fragment;

import android.content.Context;

import com.github.tuxdude.yani.R;
import com.github.tuxdude.yani.fragment.connections.ConnectionsSectionFragment;
import com.github.tuxdude.yani.fragment.mobile.MobileSectionFragment;
import com.github.tuxdude.yani.fragment.wifi.WifiSectionFragment;
import com.github.tuxdude.yani.utils.Logger;

import java.util.ArrayList;

public class SectionFragmentsManager {

    private static SectionFragmentsManager manager = null;

    public static SectionFragmentsManager getManager() {
        if (manager == null) {
            manager = new SectionFragmentsManager();
        }
        return manager;
    }

    private SectionFragmentsManager() {
        // Constructor
    }

    public static String getFragmentTitle(Context context, FragmentType type) {
        String title = null;
        if (context != null) {
            switch (type) {
                case FRAGMENT_CONNECTIONS:
                    title = context.getString(R.string.title_section_connections);
                    break;
                case FRAGMENT_MOBILE:
                    title = context.getString(R.string.title_section_mobile);
                    break;
                case FRAGMENT_WIFI:
                    title = context.getString(R.string.title_section_wifi);
                    break;
            }
        }
        else {
            Logger.e("getFragmentTitle() null context");
        }
        return title;
    }

    public static ArrayList<String> getFragmentTitles(Context context) {
        ArrayList<String> titles = new ArrayList<String>();
        FragmentType[] types = FragmentType.getValues();
        for (FragmentType type : types) {
            titles.add(getFragmentTitle(context, type));
        }
        return titles;
    }

    public BaseSectionFragment getFragment(int position) {
        BaseSectionFragment fragment = null;
        switch (FragmentType.valueAt(position)) {
            case FRAGMENT_CONNECTIONS:
                fragment = ConnectionsSectionFragment.newInstance();
                break;
            case FRAGMENT_MOBILE:
                fragment = new MobileSectionFragment();
                break;
            case FRAGMENT_WIFI:
                fragment = new WifiSectionFragment();
                break;
            default:
                break;
        }
        return fragment;
    }
}
