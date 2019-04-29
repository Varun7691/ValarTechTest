package com.valartech.test.util;

import android.util.Log;

public class TestUtils {

    /**
     * Log String data in Error
     *
     * @param logData
     */
    public static void log(String logData) {
        Log.e(Constants.LOG_TAG, logData + "");
    }
}
