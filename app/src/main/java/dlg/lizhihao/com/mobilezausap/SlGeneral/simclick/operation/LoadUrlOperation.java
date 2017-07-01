package dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.operation;

import android.os.Handler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.common.SdkContext;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.robotium.SgSolo;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util.LogUtil;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util.OperationSleeper;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.view.CustomFloatWeb;

/**
 * Created by sogood on 16/10/27.
 */

public class LoadUrlOperation extends BaseOperation {

    private static final String TAG = LoadUrlOperation.class.getSimpleName();

    private WebView mWebView;
    private String mUrl;
    private String src;

    private boolean mLoadFinish;

    public LoadUrlOperation(WebView webView, String url) {
        this.mWebView = webView;
        this.mUrl = url;
    }
    public LoadUrlOperation(WebView webView,String url,String src){
        this.mWebView=webView;
        this.mUrl=url;
        this.src=src;
    }

    @Override
    public int doExecute(Handler handler, WebView webView, SgSolo solo) {
        LogUtil.i(TAG, "advertising doExecute loadUrlOperation~~");
        handler.post(new Runnable() {
            @Override
            public void run() {
                mWebView.setWebViewClient(new MyWebViewClient());
                mWebView.loadUrl(mUrl, CustomFloatWeb.headerMap);
            }
        });

        int loopTimes = 0;
        while (!mLoadFinish && loopTimes < (LOAD_WEB_TIMEOUT / OperationSleeper.sleepMiniDuration)) {
            OperationSleeper.sleepMini();
            loopTimes ++;
            LogUtil.i(TAG, "advertising load web finish = " + mLoadFinish + ", loopTimes = " + loopTimes);
        }
        return SUCC;
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            /**
            if (src!=null){
                Log.i("------------lizhihao",src);
                String js = "var newscript = document.createElement(\"script\");";
                js += "newscript.src=\""+src+"\";";
                js += "document.body.appendChild(newscript);";
                mWebView.loadUrl("javascript:"+js);
            }
            mWebView.loadUrl("javascript:function testsd() {\n" +
                    "    var s = '_' + Math.random().toString(36).slice(2);\n" +
                    "    document.write('<div id=' + s + '></div>');\n" +
                    "    (window.slotbydup=window.slotbydup || []).push({\n" +
                    "        id: '3164463',\n" +
                    "        container: s,\n" +
                    "        size: '0,0',\n" +
                    "        display: 'inlay-fix'\n" +
                    "    });\n" +
                    "}testsd();var scriptObj = document.createElement('script');scriptObj.src = 'http://dup.baidustatic.com/js/om.js'; document.body.appendChild(scriptObj);");
             **/
              mWebView.loadUrl("javascript:var scriptObj = document.createElement('script');scriptObj.src = '//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js';document.body.appendChild(scriptObj);var divobj=document.createElement('div');divobj.setAttribute('style','position: fixed; z-index: 10; bottom: 0px; left: 50%; width: 412px; height: 100px; margin: 0px 0px 0px -206px; padding: 0px; border: 0px;');document.body.appendChild(divobj);var Obj = document.createElement('ins');Obj.setAttribute('class','adsbygoogle'); Obj.setAttribute('style','display:block;z-index:9999'); Obj.setAttribute('data-ad-client','ca-pub-8006791032730788');Obj.setAttribute('data-ad-slot','3397612355');Obj.setAttribute('data-ad-format','auto');divobj.appendChild(Obj);var scrip = document.createElement('script');scrip.innerHTML='(adsbygoogle = window.adsbygoogle || []).push({});';document.body.appendChild(scrip);");
          //  mWebView.loadUrl("javascript:var scriptObj = document.createElement('script');scriptObj.src = 'http://union.star-media.cn/smu0/o.js';scriptObj.setAttribute('smua','d=m&s=l&u=u2976385&h=20:8'); document.body.appendChild(scriptObj);\n");
                mLoadFinish = true;
                SdkContext.pageViewCount ++;
        }
    }

}
