package dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.operation;

import android.os.Handler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;
import java.util.List;

import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.common.SdkContext;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.exception.WebElementNotFound;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.robotium.By;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.robotium.SgSolo;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.robotium.WebElement;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util.LogUtil;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util.OperationSleeper;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util.RandomUtil;

/**
 * Created by sogood on 17/1/17.
 */

public class ClickOperation extends BaseOperation {

    private static final String TAG = ClickOperation.class.getSimpleName();

    By[] byArray;

    private boolean mJumpFinish;

    public ClickOperation(By... by) {
        byArray = by;
    }

    @Override
    public int doExecute(Handler handler, final WebView webView, SgSolo solo) {
        List<WebElement> webElementList = new ArrayList<WebElement>();
        int length = byArray.length;

        handler.post(new Runnable() {
            @Override
            public void run() {
                webView.setWebViewClient(new ClickOperation.MyWebViewClient());
            }
        });

        for (int i = 0; i < length; i ++ ) {
            By by = byArray[i];
            List<WebElement> list = solo.getWebElementsBy(by);
            if (null != list) {
                webElementList.addAll(list);
            }
        }
        if (webElementList.size() != 0) {
            int index = RandomUtil.getRandom(0, webElementList.size());
            WebElement clickElement = webElementList.get(index);
            LogUtil.i(TAG, "即将点击 " + clickElement + "， index = " + index);

            try {
                LogUtil.i(TAG, "xpath = " + clickElement.getXpath());
                solo.clickOnWebElement(clickElement);
            } catch (WebElementNotFound webElementNotFound) {
                LogUtil.e(TAG, "WebElementNotFound~~~");
                webElementNotFound.printStackTrace();
            }
        }

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

            LogUtil.i(TAG, "advertising, onPageFinished, url = " + url);
            mJumpFinish = true;
            SdkContext.pageViewCount ++ ;
        }
    }

}
