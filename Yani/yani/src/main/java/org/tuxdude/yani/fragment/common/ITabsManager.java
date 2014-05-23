package org.tuxdude.yani.fragment.common;

import android.support.v4.app.Fragment;

public interface ITabsManager {
    public Fragment getTab(int position);
    public CharSequence getTabTitle(int position);
    public int getTabCount();
}
