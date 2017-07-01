package dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.operation;

import android.os.Handler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.common.SdkContext;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.robotium.SgSolo;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util.LogUtil;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util.OperationSleeper;

/**
 * Created by sogood on 16/11/2.
 */

public class ReloadOperation extends BaseOperation {

    private static final String TAG = LoadUrlOperation.class.getSimpleName();

    private WebView mWebView;

    private boolean mLoadFinish;

    public ReloadOperation(WebView webView) {
        this.mWebView = webView;
    }

    @Override
    public int doExecute(Handler handler, WebView webView, SgSolo solo) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                mWebView.setWebViewClient(new ReloadOperation.MyWebViewClient());
                mWebView.reload();
            }
        });

        int loopTimes = 0;
        while (!mLoadFinish && loopTimes < (LOAD_WEB_TIMEOUT / OperationSleeper.sleepMiniDuration)) {
            OperationSleeper.sleepMini();
            loopTimes ++;
        }
        LogUtil.i(TAG, "advertising load web finish = " + mLoadFinish + ", loopTimes = " + loopTimes);
        return SUCC;
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mLoadFinish = true;
            SdkContext.pageViewCount ++;
        }
    }

}
