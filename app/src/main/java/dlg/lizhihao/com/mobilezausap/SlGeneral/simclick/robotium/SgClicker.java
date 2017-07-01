package dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.robotium;

import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;

import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.exception.WebElementNotFound;

/**
 * Created by sogood on 16/11/15.
 */

public class SgClicker {

    private SgWaiter waiter;
    private SgWebUtils webUtils;
    private Context mContext;
    private Handler mHandler;

    public SgClicker(Context context, WebView webView, Solo.Config config, Handler handler, Sleeper sleeper, SgSearcher searcher, SgScroller scroller) {
        webUtils = new SgWebUtils(context, webView, config, handler, sleeper);
        waiter = new SgWaiter(searcher, scroller, sleeper);
        mContext = context;
        mHandler = handler;
    }

    /**
     * Clicks on a given coordinate on the screen.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */

    public void clickOnScreen(final float x, final float y, final View view) {
        final SecurityException ex = null;

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    long downTime = SystemClock.uptimeMillis();
                    long eventTime = SystemClock.uptimeMillis();
                    MotionEvent event = MotionEvent.obtain(downTime, eventTime,
                            MotionEvent.ACTION_DOWN, x, y, 0);
                    MotionEvent event2 = MotionEvent.obtain(downTime, eventTime,
                            MotionEvent.ACTION_UP, x, y, 0);
                    view.dispatchTouchEvent(event);
                    view.dispatchTouchEvent(event2);

                } catch(SecurityException e){
                    Assert.fail("Click at ("+x+", "+y+") can not be completed! ("+(ex != null ? ex.getClass().getName()+": "+ex.getMessage() : "null")+")");
                }

            }
        });


    }

    public void clickOnWebElement(By by) throws WebElementNotFound {
        WebElement webElement = null;
        webElement = waiter.waitForWebElement(by, 0, Timeout.getSmallTimeout(), true);
        if(webElement == null){
            Assert.fail("WebElement with " + webUtils.splitNameByUpperCase(by.getClass().getSimpleName()) + ": '" + by.getValue() + "' is not found!");
        }
        webUtils.executeJavaScript(by, true);
        return;
    }

    /**
     * Clicks on a web element using the given By method.
     *
     * @param by the By object e.g. By.id("id");
     * @param match if multiple objects match, this determines which one will be clicked
     * @param scroll true if scrolling should be performed
     * @param useJavaScriptToClick true if click should be perfomed through JavaScript
     */

    public void clickOnWebElement(By by, int match, boolean scroll, boolean useJavaScriptToClick) throws WebElementNotFound {
        WebElement webElement = null;

        if(useJavaScriptToClick){
            webElement = waiter.waitForWebElement(by, match, Timeout.getSmallTimeout(), true);
            if(webElement == null){
                Assert.fail("WebElement with " + webUtils.splitNameByUpperCase(by.getClass().getSimpleName()) + ": '" + by.getValue() + "' is not found!");
            }
            webUtils.executeJavaScript(by, true);
            return;
        }

    }

}
