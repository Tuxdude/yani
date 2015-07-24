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

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.tuxdude.yani.util.Log;

public final class ReflectionHelper {
    private static final String TAG = "ReflectionHelper";

    private ReflectionHelper() {
    }

    public static Class getClass(String className) {
        Class result = null;
        if (className != null) {
            try {
                result = Class.forName(className);
            } catch (ClassNotFoundException exception) {
                Log.e(TAG, "ClassNotFoundException for class: " + className,
                        exception);
            }
        }
        return result;
    }

    public static Method getMethod(Class clazz, String methodName) {
        Method result = null;
        if (clazz != null && methodName != null) {
            try {
                result = clazz.getDeclaredMethod(methodName);
            } catch (NoSuchMethodException exception) {
                Log.e(TAG, "NoSuchMethodException for class: " +
                        clazz.getName() + " method: " + methodName,
                        exception);
            }
        }
        return result;
    }

    public static Field getField(Class clazz, String fieldName) {
        Field result = null;
        if (clazz != null && fieldName != null) {
            try {
                result = clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException exception) {
                Log.e(TAG, "NoSuchFieldException for class: " +
                        clazz.getName() + " field: " + fieldName,
                        exception);
            }
        }
        return result;
    }

    public static Object getFieldValue(Object object, Field field) {
        Object result = null;
        if (object != null && field != null) {
            try {
                result = field.get(object);
            } catch (IllegalAccessException exception) {
                Log.e(TAG, "IllegalAccessException for class: " +
                        object.getClass().getName() + " field: " + field.getName(),
                        exception);
            }
        }
        return result;
    }

    public static AccessibleObject makeAccessible(AccessibleObject object) {
        if (object != null && !object.isAccessible()) {
            object.setAccessible(true);
        }
        return object;
    }

    public static Object invokeMethod(Object object, Method method, Object... args) {
        Object result = null;
        if (object != null && method != null) {
            try {
                result = method.invoke(object, args);
            } catch (IllegalAccessException exception) {
                Log.e(TAG, "IllegalAccessException for class: " +
                        object.getClass().getName() + " method: " + method.getName(),
                        exception);
            } catch (InvocationTargetException exception) {
                Log.e(TAG, "InvocationTargetException for class: " +
                        object.getClass().getName() + " method: " + method.getName(),
                        exception);
            }
        }
        return result;
    }
}
