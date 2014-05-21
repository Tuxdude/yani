package com.github.tuxdude.yani.fragment;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.github.tuxdude.yani.R;
import com.github.tuxdude.yani.utils.Logger;

public class ConnectionsFragment extends BaseFragment {

    private ViewPager mViewPager = null;
    private PagerSlidingTabStrip mTabs = null;

    public static ConnectionsFragment newInstance() {
        Logger.trace();
        return new ConnectionsFragment();
    }

    public ConnectionsFragment() {
        this.fragmentType = FragmentType.FRAGMENT_CONNECTIONS;
    }

    class TabsPagerAdapter extends FragmentPagerAdapter {

        public TabsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Logger.d("getItem() " + position);
            // Return the Fragment which corresponds to the tab at position
            switch (position) {
                default: return ConnectionsWifiFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Wifi";
        }
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Logger.trace();

        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(new TabsPagerAdapter(getChildFragmentManager()));

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        mViewPager.setPageMargin(pageMargin);

        mTabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        mTabs.setViewPager(mViewPager);

        changeColor(mCurrentColor);
    }

    private int mCurrentColor = 0xFF666666;
    private Drawable oldBackground = null;
    private final Handler handler = new Handler();

    private Drawable.Callback drawableCallback = new Drawable.Callback() {
        @Override
        public void invalidateDrawable(Drawable who) {
            getActivity().getActionBar().setBackgroundDrawable(who);
        }

        @Override
        public void scheduleDrawable(Drawable who, Runnable what, long when) {
            handler.postAtTime(what, when);
        }

        @Override
        public void unscheduleDrawable(Drawable who, Runnable what) {
            handler.removeCallbacks(what);
        }
    };


    private void changeColor(int newColor) {

        mTabs.setIndicatorColor(newColor);

        // change ActionBar color just if an ActionBar is available
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

            Drawable colorDrawable = new ColorDrawable(newColor);
            Drawable bottomDrawable = getResources().getDrawable(R.drawable.actionbar_bottom);
            LayerDrawable ld = new LayerDrawable(new Drawable[] { colorDrawable, bottomDrawable });

            if (oldBackground == null) {

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    ld.setCallback(drawableCallback);
                } else {
                    getActivity().getActionBar().setBackgroundDrawable(ld);
                }

            } else {

                TransitionDrawable td = new TransitionDrawable(new Drawable[] { oldBackground, ld });

                // workaround for broken ActionBarContainer drawable handling on
                // pre-API 17 builds
                // https://github.com/android/platform_frameworks_base/commit/a7cc06d82e45918c37429a59b14545c6a57db4e4
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    td.setCallback(drawableCallback);
                } else {
                    getActivity().getActionBar().setBackgroundDrawable(td);
                }

                td.startTransition(200);

            }

            oldBackground = ld;

            // http://stackoverflow.com/questions/11002691/actionbar-setbackgrounddrawable-nulling-background-from-thread-handler
            getActivity().getActionBar().setDisplayShowTitleEnabled(false);
            getActivity().getActionBar().setDisplayShowTitleEnabled(true);

        }

        mCurrentColor = newColor;
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

    @Override
    public void onDetach() {
        Logger.trace();
        super.onDetach();
    }
}
