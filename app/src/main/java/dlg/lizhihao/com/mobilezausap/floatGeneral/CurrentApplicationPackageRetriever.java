package dlg.lizhihao.com.mobilezausap.floatGeneral;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import dlg.lizhihao.com.mobilezausap.floatGeneral.models.AndroidAppProcess;
import dlg.lizhihao.com.mobilezausap.floatGeneral.models.Stat;

/**
 * Created by Mcin on 17/3/17.
 */
public class CurrentApplicationPackageRetriever {


    private final static String TOPAPPS = "top_apps";

    private final Context mContext;

    public CurrentApplicationPackageRetriever(Context context) {
        this.mContext = context;
    }

    public List<String> get() {
        if (Build.VERSION.SDK_INT < 21) {
            return getPreLollipop();
        } else {
            return getLollipop();
        }
    }
    private List<String> getPreLollipop() {
        @SuppressWarnings("deprecation")
        List<ActivityManager.RunningTaskInfo> tasks=
                activityManager().getRunningTasks(1);
        ActivityManager.RunningTaskInfo currentTask=tasks.get(0);
        ComponentName currentActivity=currentTask.topActivity;
        List<String> topApp = new ArrayList<String>();
        String curAc = currentActivity.getPackageName();
        if (null != curAc && AppUtils.isSystemApp(mContext, curAc)){
            return null;
        }
        if (!getHomes().contains(curAc) && !curAc.equals(mContext.getPackageName())){
            for (String str : WhileListUtil.whileList){
                if (!str.contains(curAc)){
                    topApp.add(curAc);
                }
            }
        }
        return topApp;
    }

    private List<String> getLollipop() {
        try {
            List<AndroidAppProcess> processes = AndroidProcesses.getRunningForegroundApps(mContext);
            List<String> topApps = new ArrayList<String>();
            if (processes != null && processes.size() > 0){
                for (int i=0; i < processes.size(); i++){
                    AndroidAppProcess  process = processes.get(i);
                    Stat stat = process.stat();
                    long bootTime = System.currentTimeMillis() - SystemClock.elapsedRealtime();
                    long startTime = bootTime + (10 * stat.starttime());
                    long currentTime = System.currentTimeMillis();
//                    long diffSeconds = (currentTime - startTime) / 1000 % 60;
                    long diffSeconds = currentTime - startTime ;
//                    Log.e("##",String.valueOf(diffSeconds) + " " + process.getPackageName());
//                    Log.e("##",String.valueOf(currentTime - startTime));
                    if(diffSeconds > 0 && diffSeconds <= 1500){
                        if (process.foreground && AndroidProcesses.isProcessInTheForeground(process.pid)){
                            String pk = process.getPackageName();
                            String myPk = mContext.getPackageName();
                            if (!pk.equals(myPk) && !getHomes().contains(pk)){
                                for (String str : WhileListUtil.whileList){
                                    if (!str.contains(pk)){
                                        topApps.add(pk);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return topApps;
        }catch (Exception e){
            return null;
        }
    }
    private ActivityManager activityManager() {
        return (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
    }

    /**
     * 返回所有桌面app的包名
     * @return
     */
    private List<String> getHomes() {
        List<String> names = new ArrayList<String>();
        PackageManager packageManager = mContext.getPackageManager();
        //属性
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        for(ResolveInfo ri : resolveInfo){
            names.add(ri.activityInfo.packageName);
//            System.out.println(ri.activityInfo.packageName);
        }
        return names;
    }

    public void setTopapps(List<String> pkg){
        SharedPreferences sp = mContext.getSharedPreferences("top_app",0);
        SharedPreferences.Editor editor = sp.edit();
        StringBuilder appPkgs = new StringBuilder();
        for (String str : pkg){
            appPkgs.append(str)
                    .append("&");
        }
        editor.putString(TOPAPPS, appPkgs.toString());
        editor.commit();
    }

    public List<String> getTopApps(){
        SharedPreferences sp = mContext.getSharedPreferences("top_app",0);
        String str = sp.getString(TOPAPPS, null);
        if (null != str && !str.trim().equals("")){
            String [] apps = str.split("&");
            if (apps.length > 0){
                List<String> list = new ArrayList<>();
                for (String ss : apps){
                    list.add(ss);
                }
                return list;
            }
        }
        return null;
    }





}

