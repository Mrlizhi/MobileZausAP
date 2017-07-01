package dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.operation;

import android.os.Handler;
import android.webkit.WebView;

import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.robotium.SgSolo;

/**
 * Created by sogood on 17/1/11.
 */

public class ScrollByPxOperation extends BaseOperation {

    private int mScrollX;
    private int mScrollY;

    public ScrollByPxOperation(int scrollX, int scrollY) {
        this.mScrollX = scrollX;
        this.mScrollY = scrollY;
    }

    @Override
    public int doExecute(Handler handler, final WebView webView, SgSolo solo) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                webView.scrollBy(mScrollX, mScrollY);
            }
        });
        return SUCC;
    }

}
