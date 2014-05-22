package com.github.tuxdude.yani.fragment.common;

public enum SectionFragmentType {
    SECTION_CONNECTIONS,
    SECTION_MOBILE,
    SECTION_WIFI;

    private static final SectionFragmentType values[] = SectionFragmentType.values();

    public static SectionFragmentType valueAt(int position)
    {
        return values[position];
    }

    public static SectionFragmentType[] getValues() { return values; }

    public static int size() { return values.length; }
}
