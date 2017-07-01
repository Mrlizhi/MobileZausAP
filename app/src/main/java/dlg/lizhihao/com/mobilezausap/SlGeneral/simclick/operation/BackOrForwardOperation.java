package dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.operation;

import android.os.Handler;
import android.webkit.WebView;

import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.robotium.SgSolo;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util.OperationSleeper;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util.RandomUtil;

/**
 * Created by sogood on 17/1/16.
 */

public class BackOrForwardOperation extends BaseOperation {

    private int mStep;

    public BackOrForwardOperation(int step) {
        mStep = step;
    }

    @Override
    public int doExecute(Handler handler, final WebView webView, SgSolo solo) {
        OperationSleeper.sleep(RandomUtil.getRandom(3, 6) * 1000);
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (webView.canGoBackOrForward(mStep)) {
                    webView.goBackOrForward(mStep);
                }
            }
        });
        return SUCC;
    }
}
