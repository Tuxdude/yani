package com.github.tuxdude.yani.fragment;

public enum FragmentType {
    FRAGMENT_CONNECTIONS,
    FRAGMENT_MOBILE,
    FRAGMENT_WIFI;

    private static final FragmentType values[] = FragmentType.values();

    public static FragmentType valueAt(int position)
    {
        return values[position];
    }

    public static FragmentType[] getValues() { return values; }

    public static int size() { return values.length; }
}
