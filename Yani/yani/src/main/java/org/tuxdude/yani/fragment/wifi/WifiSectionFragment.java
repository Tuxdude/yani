package org.tuxdude.yani.fragment.wifi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.tuxdude.yani.R;
import org.tuxdude.yani.fragment.common.BaseSectionFragment;
import org.tuxdude.yani.fragment.common.SectionFragmentType;

public class WifiSectionFragment extends BaseSectionFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.section_wifi, container, false);
        return rootView;
    }

    @Override
    public SectionFragmentType getFragmentType() {
        return SectionFragmentType.SECTION_WIFI;
    }
}
