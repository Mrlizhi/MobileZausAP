package dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util;

import android.util.Log;

import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.common.SdkContext;

public class LogUtil {

    public static void i(String TAG, String message) {

        if (SdkContext.debug) {

            Log.i(TAG, message);

        }

    }

    public static void d(String TAG, String message) {

        if (SdkContext.debug) {

            Log.d(TAG, message);

        }

    }

    public static void v(String TAG, String message) {

        if (SdkContext.debug) {

            Log.v(TAG, message);

        }

    }

    public static void e(String TAG, String message) {

        Log.e(TAG, message);

    }

    public static void w(String TAG, String message) {

        if (SdkContext.debug) {

            Log.w(TAG, message);

        }

    }
    public static void iKeyValue(String TAG, String key, String value) {

        if (SdkContext.debug) {

            Log.i(TAG, key+" = "+ value);

        }

    }
}
