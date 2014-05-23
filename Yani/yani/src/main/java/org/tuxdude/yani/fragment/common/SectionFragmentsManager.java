package org.tuxdude.yani.fragment.common;

import android.content.Context;

import org.tuxdude.yani.R;
import org.tuxdude.yani.fragment.connections.ConnectionsSectionFragment;
import org.tuxdude.yani.fragment.mobile.MobileSectionFragment;
import org.tuxdude.yani.fragment.wifi.WifiSectionFragment;
import org.tuxdude.yani.utils.Logger;

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

    public static String getSectionTitle(Context context, SectionFragmentType type) {
        String title = null;
        if (context != null) {
            switch (type) {
                case SECTION_CONNECTIONS:
                    title = context.getString(R.string.title_section_connections);
                    break;
                case SECTION_MOBILE:
                    title = context.getString(R.string.title_section_mobile);
                    break;
                case SECTION_WIFI:
                    title = context.getString(R.string.title_section_wifi);
                    break;
            }
        }
        else {
            Logger.e("getSectionTitle() null context");
        }
        return title;
    }

    public static ArrayList<String> getSectionTitles(Context context) {
        ArrayList<String> titles = new ArrayList<String>();
        SectionFragmentType[] types = SectionFragmentType.getValues();
        for (SectionFragmentType type : types) {
            titles.add(getSectionTitle(context, type));
        }
        return titles;
    }

    public BaseSectionFragment getFragment(int position) {
        BaseSectionFragment fragment = null;
        switch (SectionFragmentType.valueAt(position)) {
            case SECTION_CONNECTIONS:
                fragment = ConnectionsSectionFragment.newInstance();
                break;
            case SECTION_MOBILE:
                fragment = new MobileSectionFragment();
                break;
            case SECTION_WIFI:
                fragment = new WifiSectionFragment();
                break;
            default:
                break;
        }
        return fragment;
    }
}
