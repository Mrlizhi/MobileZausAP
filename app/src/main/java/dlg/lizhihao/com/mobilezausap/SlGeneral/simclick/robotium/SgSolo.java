package dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.robotium;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.webkit.WebView;

import java.util.ArrayList;

import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.exception.WebElementNotFound;

/**
 * Created by sogood on 16/11/15.
 */

public class SgSolo {

    private SgClicker clicker;
    private Solo.Config config = new Solo.Config();
    private SgWebUtils webUtils;
    private WebView mWebView;

    public SgSolo(Context context, WebView webView, Handler handler) {
        Sleeper sleeper = new Sleeper(config.sleepDuration, config.sleepMiniDuration);
        SgScroller scroller = new SgScroller(config, handler, webView, sleeper);
        webUtils = new SgWebUtils(context, webView, config, handler, sleeper);
        SgSearcher searcher = new SgSearcher(webUtils, scroller, sleeper);
        clicker = new SgClicker(context, webView, config, handler, sleeper, searcher, scroller);
        mWebView = webView;
    }
    /**
     * Clicks the specified WebElement.
     *
     * @param webElement the WebElement to click
     */
    public void clickOnWebElement(WebElement webElement) throws WebElementNotFound {
        if(config.commandLogging){
            Log.d(config.commandLoggingTag, "clickOnWebElement("+webElement+")");
        }
        if(webElement == null)
            Assert.fail("WebElement is null and can therefore not be clicked!");
//        clicker.clickOnScreen(webElement.getLocationX(), webElement.getLocationY(), mWebView);
        clickOnWebElement(By.xpath(webElement.getXpath()));
    }
    /**
     * Clicks a WebElement matching the specified By object.
     *
     * @param by the By object. Examples are: {@code By.id("id")} and {@code By.name("name")}
     */
    public void clickOnWebElement(By by) throws WebElementNotFound {
        if(config.commandLogging){
            Log.d(config.commandLoggingTag, "clickOnWebElement("+by+")");
        }
        clickOnWebElement(by, 0, true);
    }
    /**
     * Clicks a WebElement matching the specified By object.
     *
     * @param by the By object. Examples are: {@code By.id("id")} and {@code By.name("name")}
     * @param match if multiple objects match, this determines which one to click
     */

    public void clickOnWebElement(By by, int match) throws WebElementNotFound {
        if(config.commandLogging){
            Log.d(config.commandLoggingTag, "clickOnWebElement("+by+", "+match+")");
        }

        clickOnWebElement(by, match, true);
    }
    /**
     * Clicks a WebElement matching the specified By object.
     *
     * @param by the By object. Examples are: {@code By.id("id")} and {@code By.name("name")}
     * @param match if multiple objects match, this determines which one to click
     * @param scroll {@code true} if scrolling should be performed
     */

    public void clickOnWebElement(By by, int match, boolean scroll) throws WebElementNotFound {
        if(config.commandLogging){
            Log.d(config.commandLoggingTag, "clickOnWebElement("+by+", "+match+", "+scroll+")");
        }

        clicker.clickOnWebElement(by, match, scroll, config.useJavaScriptToClickWebElements);
    }

    /**
     * Returns an ArrayList of the currently displayed WebElements in the active WebView.
     *
     * @return an {@code ArrayList} of the {@link WebElement} objects displayed in the active WebView
     */

    public ArrayList<WebElement> getCurrentWebElements(){
        if(config.commandLogging){
            Log.d(config.commandLoggingTag, "getCurrentWebElements()");
        }
        return webUtils.getWebElements(false);
    }

    public ArrayList<WebElement> getWebElementsBy(By by) {
        return webUtils.getWebElements(by, false);
    }

}
