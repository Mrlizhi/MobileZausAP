package dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.operation;

import android.os.Handler;
import android.webkit.WebView;

import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.robotium.SgSolo;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util.OperationSleeper;

/**
 * Created by sogood on 16/10/29.
 * 页面停留操作
 */

public class WaitOperation extends BaseOperation{

    private long mWaitTimeMills = 0;

    public WaitOperation(long timeMills) {
        mWaitTimeMills = timeMills;
    }

    @Override
    public int doExecute(Handler handler, WebView webView, SgSolo solo) {
        OperationSleeper.sleep(mWaitTimeMills);
        return SUCC;
    }
}
