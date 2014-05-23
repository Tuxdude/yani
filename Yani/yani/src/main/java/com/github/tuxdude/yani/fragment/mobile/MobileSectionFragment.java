package com.github.tuxdude.yani.fragment.mobile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.tuxdude.yani.R;
import com.github.tuxdude.yani.fragment.common.BaseSectionFragment;
import com.github.tuxdude.yani.fragment.common.SectionFragmentType;

public class MobileSectionFragment extends BaseSectionFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.section_mobile, container, false);
        return rootView;
    }

    @Override
    public SectionFragmentType getFragmentType() {
        return SectionFragmentType.SECTION_MOBILE;
    }
}
