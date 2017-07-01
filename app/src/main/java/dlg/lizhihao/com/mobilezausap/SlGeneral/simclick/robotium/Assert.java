package dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.robotium;

import android.util.Log;

import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.common.SdkContext;

/**
 * Created by sogood on 16/10/19.
 */

public class Assert {

    private static  final String TAG = Assert.class.getSimpleName();

    protected Assert() {

    }

    public static void assertTrue(String message, boolean condition) {

    }

    public static void assertTrue(boolean condition) {

    }

    public static void assertFalse(String message, boolean condition) {
        if (SdkContext.debug) {
            Log.e(SdkContext.TAG, "assertFalse, message:" + message + ", condition:" + condition);
        }
    }

    public static void assertFalse(boolean condition) {


    }

    public static void fail(String message) {
        if (SdkContext.debug) {
            Log.e(SdkContext.TAG, "fail(" + message + ")");
        }
    }

    public static void fail() {

        if (SdkContext.debug) {
            Log.e(SdkContext.TAG, "fail()");
        }
    }

    public static void assertEquals(String message, Object expected, Object actual) {


    }

    public static void assertEquals(Object expected, Object actual) {


    }

    public static void assertEquals(String message, String expected, String actual) {

        if (SdkContext.debug) {
            Log.e(SdkContext.TAG, "assertEquals, message:" + message + ", expected:" + expected + ", actual:" + actual);
        }

    }

    public static void assertEquals(String expected, String actual) {


    }

    public static void assertEquals(String message, double expected, double actual, double delta) {


    }

    public static void assertEquals(double expected, double actual, double delta) {


    }

    public static void assertEquals(String message, float expected, float actual, float delta) {


    }

    public static void assertEquals(float expected, float actual, float delta) {


    }

    public static void assertEquals(String message, long expected, long actual) {


    }

    public static void assertEquals(long expected, long actual) {


    }

    public static void assertEquals(String message, boolean expected, boolean actual) {


    }

    public static void assertEquals(boolean expected, boolean actual) {


    }

    public static void assertEquals(String message, byte expected, byte actual) {


    }

    public static void assertEquals(byte expected, byte actual) {


    }

    public static void assertEquals(String message, char expected, char actual) {


    }

    public static void assertEquals(char expected, char actual) {


    }

    public static void assertEquals(String message, short expected, short actual) {


    }

    public static void assertEquals(short expected, short actual) {


    }

    public static void assertEquals(String message, int expected, int actual) {


    }

    public static void assertEquals(int expected, int actual) {


    }

    public static void assertNotNull(Object object) {


    }

    public static void assertNotNull(String message, Object object) {


    }

    public static void assertNull(Object object) {


    }

    public static void assertNull(String message, Object object) {


    }

    public static void assertSame(String message, Object expected, Object actual) {


    }

    public static void assertSame(Object expected, Object actual) {


    }

    public static void assertNotSame(String message, Object expected, Object actual) {

        if (SdkContext.debug) {
            Log.e(SdkContext.TAG, "assertNotSame message:" + message + ", expected:" + expected + ", actual:" + actual);
        }
    }

    public static void assertNotSame(Object expected, Object actual) {


    }

    public static void failSame(String message) {


    }

    public static void failNotSame(String message, Object expected, Object actual) {


    }

    public static void failNotEquals(String message, Object expected, Object actual) {


    }

    public static String format(String message, Object expected, Object actual) {
        return null;

    }

}
