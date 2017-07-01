package dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.robotium;

import android.os.SystemClock;
;import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.exception.WebElementNotFound;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util.LogUtil;


/**
 * Contains various wait methods. Examples are: waitForText(),
 * waitForView().
 *
 * @author Renas Reda, renas.reda@robotium.com
 *
 */

class SgWaiter {

	private static final String TAG = SgWaiter.class.getSimpleName();

	private final SgSearcher searcher;
	private final SgScroller scroller;
	private final Sleeper sleeper;

	/**
	 * Constructs this object.
	 *
	 * @param searcher the {@code Searcher} instance
	 * @param scroller the {@code Scroller} instance
	 * @param sleeper the {@code Sleeper} instance
	 */

	public SgWaiter(SgSearcher searcher, SgScroller scroller, Sleeper sleeper){
		this.searcher = searcher;
		this.scroller = scroller;
		this.sleeper = sleeper;
	}
	/**
	 * Waits for a web element.
	 *
	 * @param by the By object. Examples are By.id("id") and By.name("name")
	 * @param minimumNumberOfMatches the minimum number of matches that are expected to be shown. {@code 0} means any number of matches
	 * @param timeout the the amount of time in milliseconds to wait
	 * @param scroll {@code true} if scrolling should be performed
	 */

	public WebElement waitForWebElement(final By by, int minimumNumberOfMatches, int timeout, boolean scroll) throws WebElementNotFound {
		final long endTime = SystemClock.uptimeMillis() + timeout;

		while (true) {

			final boolean timedOut = SystemClock.uptimeMillis() > endTime;

			if (timedOut){
				searcher.logMatchesFound(by.getValue());
				throw new WebElementNotFound();
//				return null;
			}
			sleeper.sleep();

			WebElement webElementToReturn = searcher.searchForWebElement(by, minimumNumberOfMatches);

			if(webElementToReturn != null)
				return webElementToReturn;

			if(scroll) {
				LogUtil.i(TAG, "scroller.scrollDown()");
				scroller.scrollDown();
			}
		}

	}
}
