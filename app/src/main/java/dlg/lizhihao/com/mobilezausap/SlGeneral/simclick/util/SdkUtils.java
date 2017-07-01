package dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.telephony.TelephonyManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by sogood on 16/11/29.
 */

public class SdkUtils {

    private static final String TAG = SdkUtils.class.getSimpleName();

    /** 没有网络 */
    public static final int NETWORKTYPE_INVALID = 0;
    /** wap网络 */
    public static final int NETWORKTYPE_WAP = 1;
    /** 2G网络 */
    public static final int NETWORKTYPE_2G = 2;
    /** 3G和3G以上网络，或统称为快速网络 */
    public static final int NETWORKTYPE_3G = 3;
    /** wifi网络 */
    public static final int NETWORKTYPE_WIFI = 4;

    private static HashMap<String, String> uaMap = new HashMap<>();

    private static final String PACKAGE_UC = "com.UCMobile";
    private static final String PACKAGE_UC_HD = "com.uc.browser.hd";
    private static final String PACKAGE_SOUGOU = "sogou.mobile.explorer";
    private static final String PACKAGE_LIEBAO = "com.ijinshan.browser_fast";
    private static final String PACKAGE_BAIDU = "com.baidu.browser.apps";
    private static final String PACKAGE_OPERA = "com.oupeng.mini.android";
    private static final String PACKAGE_2345 = "com.browser2345";
    private static final String PACKAGE_QIHOO = "com.qihoo.browser";
    private static final String PACKAGE_HUOHU = "org.mozilla.firefox";
    private static final String PACKAGE_AOYOU = "com.mx.browser";
    private static final String PACKAGE_QQ = "com.tencent.mtt";


    private static final String DEVICE_INFO = "device_info";

    private static final String BROWSE_VERSION = "browser_version";

    static {


//        UC:
//        Mozilla/5.0 (Linux; U; Android 4.4.3; zh-CN; Nexus 5 Build/KTU84M) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/40.0.2214.89 UCBrowser/11.2.1.888 Mobile Safari/537.36
//        Mozilla/5.0 (Linux; U; Android 4.4.3; zh-CN; Nexus 5 Build/KTU84M) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/40.0.2214.89 UCBrowser/11.2.1.888 Mobile Safari/537.36
//
//        Chrome (6.0虚拟机)
//        Mozilla/5.0 (Linux; Android 6.0; Android SDK built for x86 Build/MASTER; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/44.0.2403.119 Mobile Safari/537.36
//
//        Mozilla/5.0 (Linux; Android 4.4.3; Nexus 5 Build/KTU84M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.84 Mobile Safari/537.36
//
//        Chrome：
//        Mozilla/5.0 (Linux; Android 4.4.3; Nexus 5 Build/KTU84M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.84 Mobile Safari/537.36
//
//        UC HD:
//        Mozilla/5.0 (Linux; U; Android 4.4.3; zh-CN; Nexus 5 Build/KTU84M) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 UCBrowser/10.5.2.598 U3/0.8.0 Mobile Safari/534.30
//
//        sogou：
//        Mozilla/5.0 (Linux; Android 4.4.3; Nexus 5; Build/KTU84M; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/46.0.2490.92 SDK/1.2.1.578 Mobile Safari/537.36 SogouMSE,SogouMobileBrowser/5.4.1
//
//        猎豹：
//        Mozilla/5.0 (Linux; Android 4.4.3; Nexus 5 Build/KTU84M) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/33.0.0.0 Mobile Safari/537.36 LieBaoFast/4.27.1
//
//        百度：
//        Mozilla/5.0 (Linux; Android 4.4.3; Nexus 5 Build/KTU84M; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/48.0.2564.116 Mobile Safari/537.36 baidubrowser/7.7.13.0 (Baidu; P1 4.4.3)
//
//        opera：
//        Mozilla/5.0 (Linux; U; Android 4.4.3; zh-CN; Nexus 5 Build/KTU84M) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 OPR/12.2.0.11 Mobile Safari/537.36
//
//        360：
//        Mozilla/5.0 (Linux; Android 4.4.3; Nexus 5 Build/KTU84M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.94 Mobile Safari/537.36 360 Aphone Browser (8.0.0.112)
//
//        火狐：
//        Mozilla/5.0 (Android 4.4.3; Mobile; rv:50.0) Gecko/50.0 Firefox/50.0
//
//        遨游浏览器：
//        Mozilla/5.0 (Linux; Android 4.4.3; Nexus 5 Build/KTU84M) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/33.0.0.0 Mobile Safari/537.36 MxBrowser/4.5.10.2000
//
//        QQ浏览器
//        Mozilla/5.0 (Linux; U; Android 4.4.3; zh-cn; Nexus 5 Build/KTU84M) AppleWebKit/537.36 (KHTML, like Gecko)Version/4.0 Chrome/37.0.0.0 MQQBrowser/7.1 Mobile Safari/537.36

//        package name = com.android.chrome, version name = 45.0.2454.84
//        package name = sogou.mobile.explorer, version name = 5.4.1
//        package name = com.oupeng.mini.android, version name = 12.2.0.11
//        package name = com.baidu.browser.apps, version name = 7.7.13.0
//        package name = com.browser2345, version name = 8.4
//        package name = com.qihoo.browser, version name = 8.0.0.112
//        package name = org.mozilla.firefox, version name = 50.0
//        package name = com.mx.browser, version name = 4.5.10.2000
//        package name = com.UCMobile, version name = 11.2.1.888
//        package name = com.ijinshan.browser_fast, version name = 4.27.1
//        package name = com.uc.browser.hd, version name = 10.5.2
//        package name = com.tencent.mtt, version name = 7.1.1.2865

        //搜狗
        uaMap.put(PACKAGE_SOUGOU,
                "Mozilla/5.0 (Linux; Android " + DEVICE_INFO + "; wv) " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/46.0.2490.92 " +
                        "SDK/1.2.1.578 Mobile Safari/537.36 " +
                        "SogouMSE,SogouMobileBrowser/" + BROWSE_VERSION);
        //opera
        uaMap.put(PACKAGE_OPERA,
                "Mozilla/5.0 (Linux; U; Aroid " + DEVICE_INFO + ") " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 OPR/"+ BROWSE_VERSION + " " +
                        "Mobile Safari/537.36");
        //百度
        uaMap.put(PACKAGE_BAIDU,
                "Mozilla/5.0 (Linux; Android " + DEVICE_INFO + "; wv) " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/48.0.2564.116 " +
                        "Mobile Safari/537.36 baidubrowser/" + BROWSE_VERSION + " (Baidu; P1 4.4.3)");
        //2345
        uaMap.put(PACKAGE_2345,
                "Mozilla/5.0 (Linux; Android " + DEVICE_INFO + ") " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/33.0.0.0 " +
                        "Mobile Safari/537.36 Mb2345Browser/" + BROWSE_VERSION);
        //360
        uaMap.put(PACKAGE_QIHOO,
                "Mozilla/5.0 (Linux; Android " + DEVICE_INFO + ") " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.94 " +
                        "Mobile Safari/537.36 360 Aphone Browser (" + BROWSE_VERSION + ")");
        //火狐
        uaMap.put(PACKAGE_HUOHU, "Mozilla/5.0 (Android " + DEVICE_INFO + "; Mobile; rv:" + BROWSE_VERSION + ") " +
                "Gecko/" + BROWSE_VERSION + " Firefox/" + BROWSE_VERSION);
        //傲游
        uaMap.put(PACKAGE_AOYOU,
                "Mozilla/5.0 (Linux; Android " + DEVICE_INFO + ") " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/33.0.0.0 " +
                        "Mobile Safari/537.36 MxBrowser/" + BROWSE_VERSION);
        //uc
        uaMap.put(PACKAGE_UC,
                "Mozilla/5.0 (Linux; U; Android " + DEVICE_INFO + ") " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/40.0.2214.89 " +
                        "UCBrowser/" + BROWSE_VERSION + " Mobile Safari/537.36");
        //uc hd版
        uaMap.put(PACKAGE_UC_HD,
                "Mozilla/5.0 (Linux; U; Android " + DEVICE_INFO + ") " +
                        "AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 " +
                        "UCBrowser/" + BROWSE_VERSION + " U3/0.8.0 " +
                        "Mobile Safari/534.30");
        //猎豹
        uaMap.put(PACKAGE_LIEBAO,
                "Mozilla/5.0 (Linux; " + DEVICE_INFO + ") " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/33.0.0.0 " +
                        "Mobile Safari/537.36 LieBaoFast/" + BROWSE_VERSION);
        //QQ浏览器
        uaMap.put(PACKAGE_QQ,
                "Mozilla/5.0 (Linux; U; " + DEVICE_INFO + ") " +
                        "AppleWebKit/537.36 (KHTML, like Gecko)Version/4.0 Chrome/37.0.0.0 MQQBrowser/" + BROWSE_VERSION + " " +
                        "Mobile Safari/537.36");
    }

    /**
     * 获取当前网络类型
     * @param context
     * @return
     */
    /**
     * 获取网络状态 wifi, wap, 2g, 3g
     * @param context
     * @return
     */
    public static int getNetWork(Context context) {
        int netWorkType = 0;
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            String type = networkInfo.getTypeName();
            if (type.equalsIgnoreCase("WIFI")) {
                netWorkType = NETWORKTYPE_WIFI;
            } else if (type.equalsIgnoreCase("MOBILE")) {
                String proxyHost = android.net.Proxy.getDefaultHost();
                netWorkType = SdkUtils.isEmpty(proxyHost)
                        ? (isFastMobileNetwork(context) ? NETWORKTYPE_3G : NETWORKTYPE_2G)
                        : NETWORKTYPE_WAP;
            }
        } else {
            netWorkType = NETWORKTYPE_INVALID;
        }
        return netWorkType;
    }

    private static boolean isFastMobileNetwork(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return false; // ~ 14-64 kbps
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return true; // ~ 400-1000 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return true; // ~ 600-1400 kbps
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return false; // ~ 100 kbps
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return true; // ~ 2-14 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return true; // ~ 700-1700 kbps
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return true; // ~ 1-23 Mbps
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return true; // ~ 400-7000 kbps
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return true; // ~ 1-2 Mbps
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return true; // ~ 5 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return true; // ~ 10-20 Mbps
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return false; // ~25 kbps
            case TelephonyManager.NETWORK_TYPE_LTE:
                return true; // ~ 10+ Mbps
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return false;
            default:
                return false;
        }
    }

    public static List<String> getBrowserApp(Context context) {

        List<String> packageNameList = new ArrayList<>();
        String default_browser = "android.intent.category.DEFAULT";
        String browsable = "android.intent.category.BROWSABLE";
        String view = "android.intent.action.VIEW";
        Intent intent = new Intent(view);
        intent.addCategory(default_browser);
        intent.addCategory(browsable);
        Uri uri = Uri.parse("http://");
        intent.setDataAndType(uri, null);
        // 找出手机当前安装的所有浏览器程序
        List<ResolveInfo> resolveInfoList = context.getPackageManager().queryIntentActivities(intent, PackageManager.GET_INTENT_FILTERS);
        if (null != resolveInfoList && resolveInfoList.size() > 0) {
            for (int i = 0; i < resolveInfoList.size(); i++ ) {
                ActivityInfo activityInfo = resolveInfoList.get(i).activityInfo;
                String packageName = activityInfo.packageName;
                String className = activityInfo.name;
                packageNameList.add(packageName);
                LogUtil.i(TAG, "package name = " + packageName + ", className = " + className);
            }
            return packageNameList;
        } else {
            return packageNameList;
        }
    }

    /**
     * get App versionName
     * @param context
     * @return
     */
    public static String getVersionName(Context context, String packageName){
        PackageManager packageManager=context.getPackageManager();
        PackageInfo packageInfo;
        String versionName="";
        try {
            packageInfo=packageManager.getPackageInfo(packageName,0);
            versionName=packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     *
     * @param context
     * @param defaultUa
     * @return
     */
    public static String getUaStr(Context context, String defaultUa) {
        List<String> browserList = getBrowserApp(context);
        List<String> rslList = new ArrayList<>();
        for (int i = 0; i < browserList.size(); i ++ ) {
            if (uaMap.containsKey(browserList.get(i))) {    //找出交集
                rslList.add(browserList.get(i));
            }
            LogUtil.i(TAG, "package name = " + browserList.get(i) + ", version name = " + getVersionName(context, browserList.get(i)));
        }
        if (rslList.size() == 0) {
            return defaultUa;
        } else {
            String packageName = rslList.get(RandomUtil.getRandom(0, rslList.size()));
            String uaTemp = uaMap.get(packageName);
            String rslStr = defaultUa;
            if (packageName.equalsIgnoreCase(PACKAGE_SOUGOU)) {
                rslStr = setupUaParam(uaTemp, getDeviceInfoStr(context, false),
                        getVersionName(context, packageName));
            } else if (packageName.equalsIgnoreCase(PACKAGE_OPERA)) {
                rslStr = setupUaParam(uaTemp, getDeviceInfoStr(context, true),
                        getVersionName(context, packageName));
            } else if (packageName.equalsIgnoreCase(PACKAGE_2345)) {
                rslStr = setupUaParam(uaTemp, getDeviceInfoStr(context, false),
                        getVersionName(context, packageName));
            } else if (packageName.equalsIgnoreCase(PACKAGE_AOYOU)) {
                rslStr = setupUaParam(uaTemp, getDeviceInfoStr(context, false),
                        getVersionName(context, packageName));
            } else if (packageName.equalsIgnoreCase(PACKAGE_BAIDU)) {
                rslStr = setupUaParam(uaTemp, getDeviceInfoStr(context, false),
                        getVersionName(context, packageName));
            } else if (packageName.equalsIgnoreCase(PACKAGE_HUOHU)) {
                rslStr = setupUaParam(uaTemp, Build.VERSION.RELEASE, //这个特殊的
                        getVersionName(context, packageName));
            } else if (packageName.equalsIgnoreCase(PACKAGE_LIEBAO)) {
                rslStr = setupUaParam(uaTemp, getDeviceInfoStr(context, false),
                        getVersionName(context, packageName));
            } else if (packageName.equalsIgnoreCase(PACKAGE_QIHOO)) {
                rslStr = setupUaParam(uaTemp, getDeviceInfoStr(context, false),
                        getVersionName(context, packageName));
            } else if (packageName.equalsIgnoreCase(PACKAGE_QQ)) {
                rslStr = setupUaParam(uaTemp, getDeviceInfoStr(context, true),
                        getVersionName(context, packageName));
            } else if (packageName.equalsIgnoreCase(PACKAGE_UC)) {
                rslStr = setupUaParam(uaTemp, getDeviceInfoStr(context, true),
                        getVersionName(context, packageName));
            } else if (packageName.equalsIgnoreCase(PACKAGE_UC_HD)) {
                rslStr = setupUaParam(uaTemp, getDeviceInfoStr(context, true),
                        getVersionName(context, packageName));
            }
            return rslStr;
        }
    }

    private static String setupUaParam(String formatStr, String deviceInfo, String browserVersion) {
        formatStr = formatStr.replaceAll(DEVICE_INFO, deviceInfo);
        formatStr = formatStr.replaceAll(BROWSE_VERSION, browserVersion);
        return formatStr;
    }

    private static String getDeviceInfoStr(Context context, boolean withLanguage) {
        Locale locale = context.getResources().getConfiguration().locale;
        StringBuffer buffer = new StringBuffer();
        // Add version
        final String version = Build.VERSION.RELEASE;
        if (version.length() > 0) {
            buffer.append(version);
        } else {
            // default to "1.0"
            buffer.append("1.0");
        }
        LogUtil.i(TAG, "Build.VERSION.RELEASE = " + version);
        if (withLanguage) {
            buffer.append("; ");
            final String language = locale.getLanguage();
            if (language != null) {
                buffer.append(language.toLowerCase());
                final String country = locale.getCountry();
                if (country != null) {
                    buffer.append("-");
                    buffer.append(country.toLowerCase());
                }
            } else {
                // default to "en"
                buffer.append("en");
            }
        }
        // add the model for the release build
        if ("REL".equals(Build.VERSION.CODENAME)) {
            final String model = Build.MODEL;
            if (model.length() > 0) {
                buffer.append("; ");
                buffer.append(model);
            }
        }
        final String id = Build.ID;
        if (id.length() > 0) {
            buffer.append(" Build/");
            buffer.append(id);
        }
        return buffer.toString();
    }

    public static boolean isEmpty(String str) {
        return (str == null || str.length() == 0);
    }
}
