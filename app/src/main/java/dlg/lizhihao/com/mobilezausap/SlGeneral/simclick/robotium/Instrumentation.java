package dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.robotium;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import android.app.Activity;
import android.app.Application;
import android.app.UiAutomation;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class Instrumentation {
    public static final String REPORT_KEY_IDENTIFIER = "id";
    public static final String REPORT_KEY_STREAMRESULT = "stream";

    private Activity mActivity;

    public Instrumentation(Activity activity) {
        this.mActivity = activity;
    }

    public void onCreate(Bundle arguments) {
    }

    public void start() {
    }

    public void onStart() {
    }

    public boolean onException(Object obj, Throwable e) {
        return false;
    }

    public void sendStatus(int resultCode, Bundle results) {

    }

    public void finish(int resultCode, Bundle results) {
    }

    public void setAutomaticPerformanceSnapshots() {
    }

    public void startPerformanceSnapshot() {
    }

    public void endPerformanceSnapshot() {
    }

    public void onDestroy() {
    }

    public Context getContext() {
        return mActivity;
    }

    public ComponentName getComponentName() {
        return null;
    }

    public Context getTargetContext() {
        return mActivity;
    }

    public boolean isProfiling() {
        return false;
    }

    public void startProfiling() {
    }

    public void stopProfiling() {
    }

    public void setInTouchMode(boolean inTouch) {
    }

    public void waitForIdle(Runnable recipient) {
    }

    public void waitForIdleSync() {
    }

    public void runOnMainSync(Runnable runner) {
        mActivity.runOnUiThread(runner);
    }

    public Activity startActivitySync(Intent intent) {
        return null;
    }

    public void addMonitor(ActivityMonitor monitor) {
    }

    public ActivityMonitor addMonitor(IntentFilter filter, ActivityResult result, boolean block) {
        return null;
    }

    public ActivityMonitor addMonitor(String cls, ActivityResult result, boolean block) {
        return null;
    }

    public boolean checkMonitorHit(ActivityMonitor monitor, int minHits) {
        return false;
    }

    public Activity waitForMonitor(ActivityMonitor monitor) {
        return null;
    }

    public Activity waitForMonitorWithTimeout(ActivityMonitor monitor, long timeOut) {
        return null;
    }

    public void removeMonitor(ActivityMonitor monitor) {
    }

    public boolean invokeMenuActionSync(Activity targetActivity, int id, int flag) {
        return false;
    }

    public boolean invokeContextMenuAction(Activity targetActivity, int id, int flag) {
        return false;
    }

    public void sendStringSync(String text) {
    }

    public void sendKeySync(KeyEvent event) {
    }

    public void sendKeyDownUpSync(int key) {
    }

    public void sendCharacterSync(int keyCode) {
    }

    public void sendPointerSync(MotionEvent event) {
        mActivity.dispatchTouchEvent(event);
    }

    public void sendTrackballEventSync(MotionEvent event) {
    }

    public Application newApplication(ClassLoader cl, String className, Context context) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return null;
    }

    public static Application newApplication(Class<?> clazz, Context context) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return null;
    }

    public void callApplicationOnCreate(Application app) {
    }

    public Activity newActivity(Class<?> clazz, Context context, IBinder token, Application application, Intent intent, ActivityInfo info, CharSequence title, Activity parent, String id, Object lastNonConfigurationInstance) throws InstantiationException, IllegalAccessException {
        return null;
    }

    public Activity newActivity(ClassLoader cl, String className, Intent intent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return null;
    }

    public void callActivityOnCreate(Activity activity, Bundle icicle) {
    }

    public void callActivityOnCreate(Activity activity, Bundle icicle, PersistableBundle persistentState) {
    }

    public void callActivityOnDestroy(Activity activity) {
    }

    public void callActivityOnRestoreInstanceState(Activity activity, Bundle savedInstanceState) {
    }

    public void callActivityOnRestoreInstanceState(Activity activity, Bundle savedInstanceState, PersistableBundle persistentState) {
    }

    public void callActivityOnPostCreate(Activity activity, Bundle icicle) {
    }

    public void callActivityOnPostCreate(Activity activity, Bundle icicle, PersistableBundle persistentState) {
    }

    public void callActivityOnNewIntent(Activity activity, Intent intent) {
    }

    public void callActivityOnStart(Activity activity) {
    }

    public void callActivityOnRestart(Activity activity) {
    }

    public void callActivityOnResume(Activity activity) {
    }

    public void callActivityOnStop(Activity activity) {
    }

    public void callActivityOnSaveInstanceState(Activity activity, Bundle outState) {
    }

    public void callActivityOnSaveInstanceState(Activity activity, Bundle outState, PersistableBundle outPersistentState) {
    }

    public void callActivityOnPause(Activity activity) {
    }

    public void callActivityOnUserLeaving(Activity activity) {
    }

    /** @deprecated */
    @Deprecated
    public void startAllocCounting() {
    }

    /** @deprecated */
    @Deprecated
    public void stopAllocCounting() {
    }

    public Bundle getAllocCounts() {
        return null;
    }

    public Bundle getBinderCounts() {
        return null;
    }

    public UiAutomation getUiAutomation() {
        return null;
    }

    public static final class ActivityResult {
        public ActivityResult(int resultCode, Intent resultData) {
        }

        public int getResultCode() {
            return 0;
        }

        public Intent getResultData() {
            return null;
        }
    }

    public class ActivityMonitor {
        public ActivityMonitor(IntentFilter which, ActivityResult result, boolean block) {
        }

        public ActivityMonitor(String cls, ActivityResult result, boolean block) {
        }

        public final IntentFilter getFilter() {
            return null;
        }

        public final ActivityResult getResult() {
            return null;
        }

        public final boolean isBlocking() {
            return false;
        }

        public final int getHits() {
            return 0;
        }

        public final Activity getLastActivity() {
            return mActivity;
        }

        public final Activity waitForActivity() {
            return null;
        }

        public final Activity waitForActivityWithTimeout(long timeOut) {
            return mActivity;
        }
    }
}
