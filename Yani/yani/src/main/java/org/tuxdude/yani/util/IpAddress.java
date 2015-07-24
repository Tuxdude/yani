/*
 *  Copyright (C) 2015 Ash [Tuxdude] <tuxdude.io@gmail.com>
 *
 *  This file is part of yani.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.tuxdude.yani.utils;

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
