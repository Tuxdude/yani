package org.tuxdude.yani.fragment.common;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tuxdude.yani.common.Constants;

public abstract class BaseSectionFragment extends Fragment implements ISectionFragment {

    public interface FragmentEventsListener {
        public void onFragmentAttached(ISectionFragment fragmentInfo);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(Constants.LOGGER_NAME);

    protected Context mContext = null;

    @Override
    public void onAttach(Activity activity) {
        LOGGER.trace(Constants.EMPTY_STRING);
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
        LOGGER.trace(Constants.EMPTY_STRING);
        mContext = null;
        super.onDetach();
    }

    @Override
    public final String getSectionTitle() {
        return SectionFragmentsManager.getSectionTitle(mContext, getFragmentType());
    }
}
