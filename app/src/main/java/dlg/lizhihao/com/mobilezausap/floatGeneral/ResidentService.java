package dlg.lizhihao.com.mobilezausap.floatGeneral;
import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import java.util.Collections;
import java.util.List;

import dlg.lizhihao.com.mobilezausap.MainActivity;

public  class ResidentService extends Service {
    protected String tag = ResidentService.class.getName();
    private Handler mHandler = null;
    private final static int LOOPHANDLER = 0;
    private HandlerThread handlerThread = null;
    //每隔100ms检查一次
    private static long cycleTime = 200;
    private Runnable mRunable;
    private Handler mHandle;
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
        String msg = String.format("3resident >>服务%s:onCreate", tag);
        Log.e(tag, msg);
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
//        StatService.onEvent(getApplicationContext(), "custom_toast_service_start", "custom_toast_service_start");
        monitorApp();
        String msg = String.format("3resident >> 2服务%s:onStartCommand,intent=%s,flags=%s,startId=%s", tag, intent, flags, startId);
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Log.d(tag, msg);
        flags = Service.START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }
    private void monitorApp(){
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
                        if ((null != currentAcs && currentAcs.size() > 0) && null != preActivity){
                            if (!compare(currentAcs, preActivity)){
                                mHandle = new Handler(){
                                    @Override
                                    public void handleMessage(Message msg) {
                                        super.handleMessage(msg);
                                        openAdAc();
                                    }
                                };
                                mRunable = new Runnable() {
                                    @Override
                                    public void run() {
                                        Message msg = new Message();
                                        mHandle.sendMessage(msg);
                                    }
                                };
                                mHandle.postDelayed(mRunable, 5*1000);
                                //弹窗
                                //    StatService.onEvent(getApplicationContext(),"plugin_gdt_show","show");
                                currentAc.setTopapps(currentAcs);
                            }
                        }else if (null != currentAcs && currentAcs.size() > 0){
                            //弹窗
                            mHandle = new Handler(){
                                @Override
                                public void handleMessage(Message msg) {
                                    super.handleMessage(msg);
                                    openAdAc();
                                }
                            };
                            mRunable = new Runnable() {
                                @Override
                                public void run() {
                                    Message msg = new Message();
                                    mHandle.sendMessage(msg);
                                }
                            };
                            mHandle.postDelayed(mRunable, 5*1000);
                            //  StatService.onEvent(getApplicationContext(),"plugin_gdt_show","show");
                            currentAc.setTopapps(currentAcs);
                        }
                        if (Build.VERSION.SDK_INT < 21) {

                        }else {
                        }
                }
                mHandler.sendEmptyMessageDelayed(LOOPHANDLER, cycleTime);
            }
        };
        mHandler.sendEmptyMessage(LOOPHANDLER);
    }
    private void openAdAc(){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.putExtra("url","111");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    private <T extends Comparable<T>> boolean compare(List<T> a, List<T> b) {
        if(a.size() != b.size())
            return false;
        Collections.sort(a);
        Collections.sort(b);
        for(int i=0;i<a.size();i++){
            if(!a.get(i).equals(b.get(i)))
                return false;
        }
        return true;
    }
    @Override
    public void onDestroy() {
        String msg = String.format("3resident >>服务%s:onDestroy", tag);
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
