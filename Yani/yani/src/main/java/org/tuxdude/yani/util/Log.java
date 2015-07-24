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
package org.tuxdude.yani.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.IllegalFormatException;
import java.util.Locale;

final public class Log {
    // Tag for all the YANI logs
    private static final String TAG = "Yani";

    public static final boolean FORCE_LOGGING = false;
    public static final boolean MASK_PII = true;

    private Log() {}

    public static boolean isDebugLoggable() {
        return isLoggable(android.util.Log.DEBUG);
    }

    public static boolean isVerboseLoggable() {
        return isLoggable(android.util.Log.DEBUG);
    }

    public static boolean isLoggable(int level) {
        return FORCE_LOGGING || android.util.Log.isLoggable(TAG, level);
    }

    public static void v(String prefix, String message) {
        if (isLoggable(android.util.Log.VERBOSE)) {
            android.util.Log.v(TAG, buildMessage(prefix, message));
        }
    }

    public static void v(Object objectPrefix, String message) {
        if (isLoggable(android.util.Log.VERBOSE)) {
            android.util.Log.v(TAG,
                    buildMessage(getPrefixFromObject(objectPrefix), message));
        }
    }

    public static void d(String prefix, String message) {
        if (isLoggable(android.util.Log.DEBUG)) {
            android.util.Log.d(TAG, buildMessage(prefix, message));
        }
    }

    public static void d(Object objectPrefix, String message) {
        if (isLoggable(android.util.Log.DEBUG)) {
            android.util.Log.d(TAG,
                    buildMessage(getPrefixFromObject(objectPrefix), message));
        }
    }

    public static void i(String prefix, String message) {
        if (isLoggable(android.util.Log.INFO)) {
            android.util.Log.i(TAG, buildMessage(prefix, message));
        }
    }

    public static void i(Object objectPrefix, String message) {
        if (isLoggable(android.util.Log.INFO)) {
            android.util.Log.i(TAG,
                    buildMessage(getPrefixFromObject(objectPrefix), message));
        }
    }

    public static void w(String prefix, String message) {
        if (isLoggable(android.util.Log.WARN)) {
            android.util.Log.w(TAG, buildMessage(prefix, message));
        }
    }

    public static void w(Object objectPrefix, String message) {
        if (isLoggable(android.util.Log.WARN)) {
            android.util.Log.w(TAG,
                    buildMessage(getPrefixFromObject(objectPrefix), message));
        }
    }

    public static void e(String prefix, String message) {
        if (isLoggable(android.util.Log.ERROR)) {
            android.util.Log.e(TAG, buildMessage(prefix, message));
        }
    }

    public static void e(Object objectPrefix, String message) {
        if (isLoggable(android.util.Log.ERROR)) {
            android.util.Log.e(TAG,
                    buildMessage(getPrefixFromObject(objectPrefix), message));
        }
    }

    public static void e(String prefix, String message, Throwable tr) {
        if (isLoggable(android.util.Log.ERROR)) {
            android.util.Log.e(TAG, buildMessage(prefix, message), tr);
        }
    }

    public static void e(Object objectPrefix,String message, Throwable tr) {
        if (isLoggable(android.util.Log.ERROR)) {
            android.util.Log.e(TAG,
                    buildMessage(getPrefixFromObject(objectPrefix), message), tr);
        }
    }

    public static void wtf(String prefix, String message) {
        String msg = buildMessage(prefix, message);
        android.util.Log.wtf(TAG, msg, new IllegalStateException(msg));
    }

    public static void wtf(Object objectPrefix, String message) {
        String msg = buildMessage(getPrefixFromObject(objectPrefix), message);
        android.util.Log.wtf(TAG, msg, new IllegalStateException(msg));
    }

    public static void wtf(String prefix, String message, Throwable tr) {
        android.util.Log.wtf(TAG, buildMessage(prefix, message), tr);
    }

    public static void wtf(Object objectPrefix, String message, Throwable tr) {
        android.util.Log.wtf(TAG,
                buildMessage(getPrefixFromObject(objectPrefix), message), tr);
    }

    // Helper to mask personally identifiable information
    public static String pii(Object pii) {
        if (pii != null && MASK_PII) {
            return "[" + secureHash(String.valueOf(pii).getBytes()) + "]";
        } else {
            return String.valueOf(pii);
        }
    }

    private static String secureHash(byte[] input) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        messageDigest.update(input);
        byte[] result = messageDigest.digest();
        return encodeHex(result);
    }

    private static String encodeHex(byte[] bytes) {
        StringBuffer hex = new StringBuffer(bytes.length * 2);

        for (int i = 0; i < bytes.length; i++) {
            int byteIntValue = bytes[i] & 0xff;
            if (byteIntValue < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toString(byteIntValue, 16));
        }

        return hex.toString();
    }

    private static String getPrefixFromObject(Object obj) {
        return obj == null ? "<null>" : obj.getClass().getSimpleName();
    }

    private static String buildMessage(String prefix, String message) {
        return String.format(Locale.US, "%s: %s", prefix, message);
    }
}

