package com.github.tuxdude.yani.utils;

public class IpAddress {
    public int octets[] = null;

    private void setUp(int intIp, boolean littleEndian) {
        if (littleEndian) {
            octets[3] = (intIp >> 24) & 0xFF;
            octets[2] = (intIp >> 16) & 0xFF;
            octets[1] = (intIp >> 8) & 0xFF;
            octets[0] = intIp & 0xFF;
        }
        else {
            octets[0] = (intIp >> 24) & 0xFF;
            octets[1] = (intIp >> 16) & 0xFF;
            octets[2] = (intIp >> 8) & 0xFF;
            octets[3] = intIp & 0xFF;
        }
    }

    // Default is Little Endian
    public IpAddress(int intIp) {
        octets = new int[4];
        setUp(intIp, true);
    }

    public IpAddress(int intIp, boolean littleEndian) {
        octets = new int[4];
        setUp(intIp, littleEndian);
    }

    @Override
    public String toString() {
        return octets[0] + "." + octets[1] + "." + octets[2] + "." + octets[3];
    }
}