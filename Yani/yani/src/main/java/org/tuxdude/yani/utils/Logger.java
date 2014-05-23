package org.tuxdude.yani.utils;

import android.util.Log;

public class Logger {
    private static boolean verboseTrace = false;
    private static final String TAG = "YaniLogger";
    private static final String TAG_PREFIX = "Yani";

    private static StackTraceElement getStackTraceElement(int depth) {
        StackTraceElement element = Thread.currentThread().getStackTrace()[depth + 4];
        if (element.isNativeMethod()) {
            element = null;
        }
        return element;
    }

    private static void writeLog(int logLevel, String logMessage, int depth) {
        StackTraceElement element = getStackTraceElement(depth + 1);
        String className = null;
        if (element != null) {
            className = element.getClassName();
            className = className.substring(className.lastIndexOf('.') + 1);
        }
        else {
            className = TAG;
        }
        writeLog(logLevel, TAG_PREFIX + className, logMessage);
    }

    private static void writeLog(int logLevel, String tag, String logMessage) {
        switch (logLevel) {
            case Log.VERBOSE:
                Log.v(tag, logMessage);
                break;
            case Log.DEBUG:
                Log.d(tag, logMessage);
                break;
            case Log.WARN:
                Log.w(tag, logMessage);
                break;
            case Log.ERROR:
                Log.e(tag, logMessage);
                break;
            case Log.INFO:
            default:
                Log.i(tag, logMessage);
                break;
        }
    }

    public static void trace() {
        StackTraceElement element = getStackTraceElement(0);
        if (element != null) {
            String fullClassName = element.getClassName();
            String className = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
            String methodName = element.getMethodName();
            if (verboseTrace) {
                String fileName = element.getFileName();
                int lineNumber = element.getLineNumber();
                Log.d(TAG_PREFIX + className, fileName + ":" + lineNumber + "  " + className + "." + methodName + "()");
            }
            else {
                Log.d(TAG_PREFIX + className, methodName + "()");
            }
        }
    }

    public static void v(String logMessage) {
        writeLog(Log.VERBOSE, logMessage, 0);
    }

    public static void v(String tag, String logMessage) {
        writeLog(Log.VERBOSE, tag, logMessage);
    }

    public static void d(String logMessage) {
        writeLog(Log.DEBUG, logMessage, 0);
    }

    public static void d(String tag, String logMessage) {
        writeLog(Log.DEBUG, tag, logMessage);
    }

    public static void i(String logMessage) {
        writeLog(Log.INFO, logMessage, 0);
    }

    public static void i(String tag, String logMessage) {
        writeLog(Log.INFO, tag, logMessage);
    }

    public static void w(String logMessage) {
        writeLog(Log.WARN, logMessage, 0);
    }

    public static void w(String tag, String logMessage) {
        writeLog(Log.WARN, tag, logMessage);
    }

    public static void e(String logMessage) {
        writeLog(Log.ERROR, logMessage, 0);
    }

    public static void e(String tag, String logMessage) {
        writeLog(Log.ERROR, tag, logMessage);
    }

    public static void setVerboseTrace(boolean verboseTrace) {
        Logger.verboseTrace = verboseTrace;
    }
}
