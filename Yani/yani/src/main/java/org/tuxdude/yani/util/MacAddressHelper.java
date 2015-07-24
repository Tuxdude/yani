/*  Copyright (C) 2015 Ash [Tuxdude] <tuxdude.io@gmail.com>
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
package org.tuxdude.yani.util;

import android.content.Context;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import org.tuxdude.yani.network.MacAddressInfo;
import org.tuxdude.yani.R;
import org.tuxdude.yani.util.Log;

public final class MacAddressHelper {
    private static final String TAG = "MacAddressPrefixHelper";

    // String used for unknown vendors
    public static final String UNKNOWN_VENDOR = "Unknown Vendor";

    // XML Parsing Information
    private static final int RESOURCE_XML_FILE = R.xml.mac_address_map;
    private static final String TAG_MAP = "map";
    private static final String TAG_ENTRY = "entry";
    private static final String ATTRIBUTE_KEY = "key";
    private static final String ATTRIBUTE_VALUE = "value";

    private static final String REGEX_MAC_ADDRESS_FULL = "^([0-9a-f]{2}:){5}[0-9a-f]{2}$";
    private static final Pattern PATTERN_MAC_ADDRESS_FULL =
        Pattern.compile(REGEX_MAC_ADDRESS_FULL);
    private static final String REGEX_MAC_ADDRESS_PREFIX = "^([0-9a-f]{2}:){2}[0-9a-f]{2}$";
    private static final Pattern PATTERN_MAC_ADDRESS_PREFIX =
        Pattern.compile(REGEX_MAC_ADDRESS_PREFIX);

    private static Map<String, String> sPrefixMap;

    public static void init(Context context) {
        sPrefixMap = Collections.unmodifiableMap(parseMacAddressMapXml(context));
    }

    public static String getPrefixForMacAddress(String fullMacAddress) {
        return getPrefixForMacAddress(fullMacAddress, true);
    }

    public static String getLowerAddressForMacAddress(String fullMacAddress) {
        return getLowerAddressForMacAddress(fullMacAddress, true);
    }

    public static String getVendorForAddress(String fullMacAddress) {
        return getVendorForAddress(fullMacAddress, true);
    }

    public static String getVendorForPrefix(String macPrefix) {
        return getVendorForPrefix(macPrefix, true);
    }

    public static MacAddressInfo getInfo(String fullMacAddress) {
        if (isValidMacAddress(fullMacAddress)) {
            String vendor = getVendorForAddress(fullMacAddress, false);
            String lowerAddress = getLowerAddressForMacAddress(fullMacAddress, false);
            return new MacAddressInfo(fullMacAddress, vendor, lowerAddress);
        } else {
            throw new IllegalArgumentException("Invalid MAC Address - Cannot be null");
        }
    }

    private static String convertInput(String input) {
        if (input != null) {
            return input.toLowerCase();
        } else {
            throw new IllegalArgumentException("Invalid MAC Address - Cannot be null");
        }
    }

    private static boolean isValidMacAddress(String fullMacAddress) {
        if (fullMacAddress != null) {
            return PATTERN_MAC_ADDRESS_FULL.matcher(fullMacAddress).matches();
        } else {
            return false;
        }
    }

    private static boolean isValidMacAddressPrefix(String macAddressPrefix) {
        if (macAddressPrefix != null) {
            return PATTERN_MAC_ADDRESS_PREFIX.matcher(macAddressPrefix).matches();
        } else {
            return false;
        }
    }

    private static String getPrefixForMacAddress(String fullMacAddress,
            boolean isValidationRequired) {
        if (isValidationRequired && !isValidMacAddress(fullMacAddress)) {
            throw new IllegalArgumentException("Invalid MAC Address");
        }

        // Return first 3 octets
        return fullMacAddress.substring(0, 8);
    }

    private static String getLowerAddressForMacAddress(String fullMacAddress,
            boolean isValidationRequired) {
        if (isValidationRequired && !isValidMacAddress(fullMacAddress)) {
            throw new IllegalArgumentException("Invalid MAC Address");
        }

        // Return last 3 octets
        return fullMacAddress.substring(9, 17);
    }

    private static String getVendorForAddress(String fullMacAddress,
            boolean isValidationRequired) {
        return getVendorForPrefix(getPrefixForMacAddress(fullMacAddress, isValidationRequired),
                false);
    }

    private static String getVendorForPrefix(String macPrefix, boolean isValidationRequired) {
        if (isValidationRequired && !isValidMacAddressPrefix(macPrefix)) {
            throw new IllegalArgumentException("Invalid MAC Address Prefix");
        }

        String result = null;
        if (sPrefixMap != null) {
            result = sPrefixMap.get(macPrefix);
        }

        if (result == null) {
            result = UNKNOWN_VENDOR;
        }

        return result;
    }

    private static Map<String, String> parseMacAddressMapXml(final Context context) {
        Map<String, String> prefixMap = new HashMap();
        boolean isEndOfDocument = false;
        boolean isParsingMap = false;
        boolean isParsingEntry = false;
        boolean isError = false;
        int numEntries = 0;

        Log.i(TAG, "Parsing MAC address prefix entries");
        XmlPullParser xmlParser = context.getResources().getXml(RESOURCE_XML_FILE);
        try {
            int xmlParserEvent = xmlParser.getEventType();
            while (!isEndOfDocument && !isError) {
                String tagName = null;
                switch (xmlParserEvent) {
                    case XmlPullParser.START_TAG:
                        tagName = xmlParser.getName();
                        if (tagName.equals(TAG_MAP)) {
                            if (isParsingMap) {
                                Log.e(TAG, "Found duplicate map tag");
                                isError = true;
                            } else {
                                isParsingMap = true;
                            }
                        } else if (tagName.equals(TAG_ENTRY)) {
                            if (isParsingEntry) {
                                Log.e(TAG, "Found duplicate entry tag");
                                isError = true;
                            } else {
                                isParsingEntry = true;
                                if (!parseEntry(xmlParser, prefixMap)) {
                                    isError = true;
                                }
                            }
                        } else {
                            Log.e(TAG, "Found unknown tag: " + tagName);
                            isError = true;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        tagName = xmlParser.getName();
                        if (tagName.equals(TAG_ENTRY)) {
                            isParsingEntry = false;
                            ++numEntries;
                        }
                        break;
                    case XmlPullParser.END_DOCUMENT:
                        isEndOfDocument = true;
                        break;
                    case XmlPullParser.TEXT:
                        Log.e(TAG, "No text is expected within tags, but XML seems to have some");
                        isError = true;
                }

                xmlParserEvent = xmlParser.next();
            }
        } catch (XmlPullParserException exception) {
            Log.e(TAG, "XmlPullParserException", exception);
            isError = true;
        } catch (IOException exception) {
            Log.e(TAG, "IOException", exception);
            isError = true;
        }

        if (isError) {
            Log.e(TAG, "Parsing Mac Address Prefix Map XML failed!");
            prefixMap.clear();
        } else {
            Log.i(TAG, "Successfully parsed " + numEntries + " MAC address prefix entries");
        }

        return prefixMap;
    }

    private static boolean parseEntry(XmlPullParser xmlParser, Map<String, String> prefixMap) {
        boolean isFailed = false;
        int attributeCount = xmlParser.getAttributeCount();
        String key = null;
        String value = null;
        for (int ix = 0; ix < attributeCount; ++ix) {
            String attributeName = xmlParser.getAttributeName(ix);
            if (key == null && attributeName.equals(ATTRIBUTE_KEY)) {
                key = xmlParser.getAttributeValue(ix);
            } else if (value == null && attributeName.equals(ATTRIBUTE_VALUE)) {
                value = xmlParser.getAttributeValue(ix);
            } else {
                isFailed = true;
                break;
            }
        }

        if (!isFailed && key != null && value != null) {
            prefixMap.put(key, value);
        } else {
            isFailed = true;
        }

        return !isFailed;
    }
}
