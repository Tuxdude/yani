package com.github.tuxdude.yani.fragment.common;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.github.tuxdude.yani.utils.Logger;

public abstract class BaseSectionFragment extends Fragment implements ISectionFragment {

    public interface FragmentEventsListener {
        public void onFragmentAttached(ISectionFragment fragmentInfo);
    }

    protected Context mContext = null;

    @Override
    public void onAttach(Activity activity) {
        Logger.trace();
        super.onAttach(activity);
        mContext = activity;

        if (activity instanceof FragmentEventsListener) {
            ((FragmentEventsListener)activity).onFragmentAttached(this);
        }
        else {
            throw new ClassCastException(activity.toString() +
                " must implement FragmentEventsListener");
        }
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
