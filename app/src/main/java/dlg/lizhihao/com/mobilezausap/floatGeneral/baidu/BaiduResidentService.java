package dlg.lizhihao.com.mobilezausap.floatGeneral.baidu;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.util.Log;


import java.util.Collections;
import java.util.List;

import dlg.lizhihao.com.mobilezausap.floatGeneral.CurrentApplicationPackageRetriever;


public  class BaiduResidentService extends Service {
    protected String tag = BaiduResidentService.class.getName();
    private Handler mHandler = null;
    private final static int LOOPHANDLER = 0;
    private HandlerThread handlerThread = null;
    private ScreenListener screenListener;
    //每隔100ms检查一次
    private static long cycleTime = 200;

    @Override
    public IBinder onBind(Intent intent) {
        String msg = String.format("resident >>服务%s:onBind,intent=%s", tag, intent);
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Log.e(tag, msg);
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        String msg = String.format("resident >>服务%s:onUnbind,intent=%s", tag, intent);
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Log.e(tag, msg);
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        String msg = String.format("3resident>>服务%s:onStart,intent=%s,startId=%s", tag, intent, startId);
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Log.e(tag, msg);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //monitorApp();
        screenListener = new ScreenListener(this);
        screenListener.begin(new ScreenListener.ScreenStateListener() {
            @Override
            public void onScreenOn() {
                Log.i("lizhihao", "---------------屏幕打开了");
            }
            @Override
            public void onScreenOff() {
                Log.i("lizhihao", "----------------屏幕关闭了");
            }
            @Override
            public void onUserPresent() {
                Log.i("lizhihao", "---------------------解锁了");
                Intent intent=new Intent(getApplicationContext(),ResidentAdActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        });
//        StatService.onEvent(this, "custom_toast_service_start", "mssp");
        //notify_normal_singleLine();
        String msg = String.format("3resident >> 2服务%s:onStartCommand,intent=%s,flags=%s,startId=%s", tag, intent, flags, startId);
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Log.d(tag, msg);
        flags = Service.START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }
    private void monitorApp() {
        Log.e(tag, "thread starting");
//        Toast.makeText(getApplicationContext(), "监听启动", Toast.LENGTH_SHORT).show();
        handlerThread = new HandlerThread("count_thread");
        handlerThread.start();
        //开始循环检查
        mHandler = new Handler(handlerThread.getLooper()) {
            public void dispatchMessage(android.os.Message msg) {
                switch (msg.what) {
                    case LOOPHANDLER:
                        CurrentApplicationPackageRetriever currentAc = new CurrentApplicationPackageRetriever(getApplicationContext());
                        List<String> currentAcs = currentAc.get();
                        List<String> preActivity = currentAc.getTopApps();
                        if ((null != currentAcs && currentAcs.size() > 0) && null != preActivity) {
                            if (!compare(currentAcs, preActivity)) {
                                openAdActivity();
                                currentAc.setTopapps(currentAcs);
                            }
                        } else if (null != currentAcs && currentAcs.size() > 0) {
                            openAdActivity();
                            currentAc.setTopapps(currentAcs);
                        }
                }
                mHandler.sendEmptyMessageDelayed(LOOPHANDLER, cycleTime);
            }
        };
        mHandler.sendEmptyMessage(LOOPHANDLER);
    }

    private <T extends Comparable<T>> boolean compare(List<T> a, List<T> b) {
        if (a.size() != b.size())
            return false;
        Collections.sort(a);
        Collections.sort(b);
        for (int i = 0; i < a.size(); i++) {
            if (!a.get(i).equals(b.get(i)))
                return false;
        }
        return true;
    }

    private void openAdActivity(){
        Intent intent = new Intent(getApplicationContext(), ResidentAdActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        String msg = String.format("3resident >>服务%s:onDestroy", tag);
        screenListener.unregisterListener();
        Log.e(tag, msg);
        super.onDestroy();
    }

    @Override
    public void onRebind(Intent intent) {
        String msg = String.format("3resident >>服务%s:onRebind,intent=%s", tag, intent);
        Log.e(tag, msg);
        super.onRebind(intent);
    }
}
