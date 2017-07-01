package dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.operation;

import android.os.Handler;
import android.webkit.WebView;

import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.robotium.SgSolo;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util.LogUtil;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util.OperationSleeper;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util.RandomUtil;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.view.CustomFloatWeb;

/**
 * Created by sogood on 16/11/7.
 */

public class RandExecuteJsOperation extends BaseOperation {

    private static final String TAG = RandExecuteJsOperation.class.getSimpleName();

    private WebView mWebView;
    private String mCode;
    private int mRand;
    private int mStayMin;
    private int mStayMax;

    public RandExecuteJsOperation(WebView webView, String code, int rand, int stayMin, int stayMax) {
        this.mWebView = webView;
        mCode = code;
        mRand = rand;
        mStayMin = stayMin;
        mStayMax = stayMax;
    }

    @Override
    public int doExecute(Handler handler, WebView webView, SgSolo solo) {
        if (!RandomUtil.isHit(mRand, 1000)) {
            return JUMP;
        }
        int sleepTime = RandomUtil.getRandom(mStayMin, mStayMax);
        LogUtil.i(TAG, "advertising 睡眠" + sleepTime + "s后再点击");
        OperationSleeper.sleep(sleepTime * 1000);
        handler.post(new Runnable() {
            @Override
            public void run() {
                mWebView.loadUrl("javascript:" + mCode, CustomFloatWeb.headerMap);
            }
        });
        OperationSleeper.sleep(2000);
        return SUCC;
    }

}
