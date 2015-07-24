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

import android.app.Activity;
import android.os.Bundle;
import android.content.pm.PackageManager;

public final class MetadataHelper {
    private static final String TAG = "MetadataHelper";

    public static final String SERVICE_PACKAGE = "org.tuxdude.yani.SERVICE_PACKAGE";
    public static final String SERVICE_CLASS = "org.tuxdude.yani.SERVICE_CLASS";

    private static Bundle sMetadataBundle;

    private MetadataHelper() {
    }

    public static void init(Activity activity) {
        if (sMetadataBundle != null) {
            Log.w(TAG, "Possibly re-initializing Metadata");
        }

        final String packageName = activity.getPackageName();
        try {
            sMetadataBundle = activity.getPackageManager().getApplicationInfo(
                    packageName, PackageManager.GET_META_DATA).metaData;
            Log.i(TAG, "Successfully obtained Metadata information");
        } catch (PackageManager.NameNotFoundException exception) {
            Log.e(TAG, "Package with name: " + packageName + " not found", exception);
            sMetadataBundle = null;
        } catch (NullPointerException exception) {
            Log.e(TAG, "Getting Application's metadata failed", exception);
            sMetadataBundle = null;
        }
    }

    public static String getValue(String key) {
        String value = null;
        if (sMetadataBundle != null) {
            value = sMetadataBundle.getString(key, null);
        } else {
            Log.w(TAG, "Metadata Bundle is null, possibly not yet initialized!");
        }
        return value;
    }
}
