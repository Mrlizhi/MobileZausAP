package dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.operation;

import android.os.Handler;
import android.webkit.WebView;
import android.widget.FrameLayout;

import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.robotium.SgSolo;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util.LogUtil;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util.OperationSleeper;

/**
 * Created by sogood on 16/12/18.
 * 调整webview 大小
 */

public class ResizeWebViewOperation extends BaseOperation {

    private static final String TAG = ResizeWebViewOperation.class.getSimpleName();

    private int mWidth;
    private int mHeight;

    public ResizeWebViewOperation(int width, int height) {
        mWidth = width;
        mHeight = height;
    }

    @Override
    public int doExecute(Handler handler, final WebView webView, SgSolo solo) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                LogUtil.i(TAG, "advertising 调整webview 大小, width = " + mWidth + ", height = " + mHeight);
                FrameLayout.LayoutParams linearParams = (FrameLayout.LayoutParams) webView.getLayoutParams();
                linearParams.height = mHeight;
                linearParams.width = mWidth;
                webView.setLayoutParams(linearParams);
//                webView.invalidate();
            }
        });

        OperationSleeper.sleep(2000);
        return SUCC;
    }

}
