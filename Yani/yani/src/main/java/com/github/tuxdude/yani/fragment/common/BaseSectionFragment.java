package com.github.tuxdude.yani.fragment.common;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.github.tuxdude.yani.YaniActivity;
import com.github.tuxdude.yani.utils.Logger;

public abstract class BaseSectionFragment extends Fragment implements ISectionFragment {

    protected Context mContext = null;

    @Override
    public void onAttach(Activity activity) {
        Logger.trace();
        super.onAttach(activity);
        mContext = activity;

        // FIXME
        ((YaniActivity) activity).onFragmentAttached(this);
    }

    @Override
    public void onDetach() {
        Logger.trace();
        mContext = null;
        super.onDetach();
    }

    @Override
    public final String getSectionTitle() {
        return SectionFragmentsManager.getSectionTitle(mContext, getFragmentType());
    }
}
