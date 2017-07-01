package dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.operation;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;

import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.robotium.SgSolo;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util.LogUtil;

/**
 * Created by sogood on 16/11/29.
 */

public class CleanCookiesOperation extends BaseOperation {

    private static final String TAG = CleanCookiesOperation.class.getSimpleName();

    private Context mContext;

    public CleanCookiesOperation(Context context) {
        mContext = context;
    }

    @Override
    public int doExecute(Handler handler, WebView webView, SgSolo solo) {
        LogUtil.i(TAG, "advertising doExecute, CleanCookiesOperation~~");
        handler.post(new Runnable() {
            @Override
            public void run() {
                CookieSyncManager.createInstance(mContext);
                CookieManager cookieManager = CookieManager.getInstance();
                cookieManager.removeSessionCookie();

                if (Build.VERSION.SDK_INT < 21) {
                    CookieSyncManager.getInstance().sync();
                } else {
                    CookieManager.getInstance().flush();
                }
            }
        });
        return SUCC;
    }

}
