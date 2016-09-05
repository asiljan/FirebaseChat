package com.alen.firebasesampleproject.common.helpers;

import com.alen.firebasesampleproject.BuildConfig;

/**
 * Created by alensiljan on 01/09/16.
 */
public class LogHelper {

    private static final String TAG = "FirebaseChat";
    private static boolean LOG_ENABLED = BuildConfig.DEBUG;

    private LogHelper() {}

    public static void printLogMsg(String msg) {
        if (LOG_ENABLED) {
            System.out.println(TAG + ": " + msg);
        }
    }
}
