package dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.operation;

import android.os.Handler;
import android.webkit.WebView;

import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.robotium.SgSolo;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util.LogUtil;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util.OperationSleeper;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util.RandomUtil;

/**
 * Created by sogood on 16/12/5.
 */

public class BackOperation extends BaseOperation {

    private static final String TAG = BackOperation.class.getSimpleName();

    @Override
    public int doExecute(Handler handler, final WebView webView, SgSolo solo) {
        OperationSleeper.sleep(RandomUtil.getRandom(3, 6) * 1000);
        LogUtil.i(TAG, "advertising, BackOperation");
        handler.post(new Runnable() {
        @Override
        public void run() {
            if (webView.canGoBack()) {
                webView.goBack();
            }
        }
    });
    return SUCC;
}
}
