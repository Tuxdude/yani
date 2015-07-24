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
package org.tuxdude.yani.wrapper;

import android.net.wifi.WifiManager;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.tuxdude.yani.wifi.WifiChannelInfo;
import org.tuxdude.yani.util.ReflectionHelper;

public class WifiManagerWrapper {
    // WifiManager
    public static final String CLASS_NAME_WIFI_MANAGER = "android.net.wifi.WifiManager";
    public static final String METHOD_NAME_GET_CHANNEL_LIST = "getChannelList";
    public static final String METHOD_NAME_IS_DUAL_BAND_SUPPORTED = "isDualBandSupported";
    public static final String METHOD_NAME_GET_CONNECTION_STATISTICS = "getConnectionStatistics";
    public static final Class CLASS_WIFI_MANAGER =
        ReflectionHelper.getClass(CLASS_NAME_WIFI_MANAGER);
    public static final Method METHOD_GET_CHANNEL_LIST = (Method) ReflectionHelper.makeAccessible(
            ReflectionHelper.getMethod(CLASS_WIFI_MANAGER, METHOD_NAME_GET_CHANNEL_LIST));
    public static final Method METHOD_IS_DUAL_BAND_SUPPORTED = (Method) ReflectionHelper.makeAccessible(
            ReflectionHelper.getMethod(CLASS_WIFI_MANAGER, METHOD_NAME_IS_DUAL_BAND_SUPPORTED));
    public static final Method METHOD_GET_CONNECTION_STATISTICS = (Method) ReflectionHelper.makeAccessible(
            ReflectionHelper.getMethod(CLASS_WIFI_MANAGER, METHOD_NAME_GET_CONNECTION_STATISTICS));

    // WifiChannel
    public static final String CLASS_NAME_WIFI_CHANNEL = "android.net.wifi.WifiChannel";
    public static final String FIELD_NAME_WIFI_CHANNEL_FREQ_MHZ = "freqMHz";
    public static final String FIELD_NAME_WIFI_CHANNEL_CHANNEL_NUM = "channelNum";
    public static final Class CLASS_WIFI_CHANNEL =
            ReflectionHelper.getClass(CLASS_NAME_WIFI_CHANNEL);
    public static final Field FIELD_WIFI_CHANNEL_FREQ_MHZ = (Field) ReflectionHelper.makeAccessible(
            ReflectionHelper.getField(CLASS_WIFI_CHANNEL, FIELD_NAME_WIFI_CHANNEL_FREQ_MHZ));
    public static final Field FIELD_WIFI_CHANNEL_CHANNEL_NUM = (Field) ReflectionHelper.makeAccessible(
            ReflectionHelper.getField(CLASS_WIFI_CHANNEL, FIELD_NAME_WIFI_CHANNEL_CHANNEL_NUM));

    // WifiConnectionStatistics
    public static final String CLASS_NAME_WIFI_CONNECTION_STATISTICS =
        "android.net.wifi.WifiConnectionStatistics";

    public static WifiChannelInfo getChannelInfo(Object wifiChannel) {
        return new WifiChannelInfo(
                (int) ReflectionHelper.getFieldValue(wifiChannel, FIELD_WIFI_CHANNEL_FREQ_MHZ),
                (int) ReflectionHelper.getFieldValue(wifiChannel, FIELD_WIFI_CHANNEL_CHANNEL_NUM));
    }

    public static List<WifiChannelInfo> getChannelList(WifiManager wifiManager) {
        List<WifiChannelInfo> result = null;
        List<Object> channelList = (List<Object>) ReflectionHelper.invokeMethod(
                wifiManager, METHOD_GET_CHANNEL_LIST);
        if (channelList != null) {
            result = new ArrayList<WifiChannelInfo>();
            for (Object channel : channelList) {
                result.add(getChannelInfo(channel));
            }
        }
        return result;
    }

    public static boolean isDualBandSupported(WifiManager wifiManager) {
        Object result = ReflectionHelper.invokeMethod(
                wifiManager, METHOD_IS_DUAL_BAND_SUPPORTED);
        if (result != null) {
            return (boolean) result;
        } else {
            return false;
        }
    }

    /* Needs Signature/System Permission to access this information */
    public static void getConnectionStatistics(WifiManager wifiManager) {
        Object result = ReflectionHelper.invokeMethod(
                wifiManager, METHOD_GET_CONNECTION_STATISTICS);
    }
}
