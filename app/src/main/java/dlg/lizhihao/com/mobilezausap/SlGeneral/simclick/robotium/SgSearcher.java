package dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.robotium;

import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Contains various search methods. Examples are: searchForEditTextWithTimeout(),
 * searchForTextWithTimeout(), searchForButtonWithTimeout().
 * 
 * @author Renas Reda, renas.reda@robotium.com
 * 
 */

class SgSearcher {

	private final SgWebUtils webUtils;
	private final SgScroller scroller;
	private final Sleeper sleeper;
	private final String LOG_TAG = "Robotium";
	Set<TextView> uniqueTextViews;
	List<WebElement> webElements;
	private int numberOfUniqueViews;
	private final int TIMEOUT = 5000;


	/**
	 * Constructs this object.
	 *
	 * @param viewFetcher the {@code ViewFetcher} instance
	 * @param webUtils the {@code WebUtils} instance
	 * @param scroller the {@code Scroller} instance
	 * @param sleeper the {@code Sleeper} instance.
	 */

	public SgSearcher(SgWebUtils webUtils, SgScroller scroller, Sleeper sleeper) {
		this.webUtils = webUtils;
		this.scroller = scroller;
		this.sleeper = sleeper;
		webElements = new ArrayList<WebElement>();
		uniqueTextViews = new HashSet<TextView>();
	}

	/**
	 * Searches for a web element.
	 *
	 * @param by the By object e.g. By.id("id");
	 * @param minimumNumberOfMatches the minimum number of matches that are expected to be shown. {@code 0} means any number of matches
	 * @return the web element or null if not found
	 */

	public WebElement searchForWebElement(final By by, int minimumNumberOfMatches){

		if(minimumNumberOfMatches < 1){
			minimumNumberOfMatches = 1;
		}

		List<WebElement> viewsFromScreen = webUtils.getWebElements(by, false);
		addViewsToList (webElements, viewsFromScreen);

		return getViewFromList(webElements, minimumNumberOfMatches);
	}

	/**
	 * Adds views to a given list.
	 *
	 * @param allWebElements the list of all views
	 * @param webTextViewsOnScreen the list of views shown on screen
	 */

	private void addViewsToList(List<WebElement> allWebElements, List<WebElement> webElementsOnScreen){

		int[] xyViewFromSet = new int[2];
		int[] xyViewFromScreen = new int[2];

		for(WebElement textFromScreen : webElementsOnScreen){
			boolean foundView = false;
			textFromScreen.getLocationOnScreen(xyViewFromScreen);

			for(WebElement textFromList : allWebElements){
				textFromList.getLocationOnScreen(xyViewFromSet);

				if(textFromScreen.getText().equals(textFromList.getText()) && xyViewFromScreen[0] == xyViewFromSet[0] && xyViewFromScreen[1] == xyViewFromSet[1]) {
					foundView = true;
				}
			}

			if(!foundView){
				allWebElements.add(textFromScreen);
			}
		}

	}

	/**
	 * Returns a text view with a given match.
	 *
	 * @param webElements the list of views
	 * @param match the match of the view to return
	 * @return the view with a given match
	 */

	private WebElement getViewFromList(List<WebElement> webElements, int match){

		WebElement webElementToReturn = null;

		if(webElements.size() >= match){

			try{
				webElementToReturn = webElements.get(--match);
			}catch(Exception ignored){}
		}
		if(webElementToReturn != null)
			webElements.clear();

		return webElementToReturn;
	}

	/**
	 * Logs a (searchFor failed) message.
	 *
	 * @param regex the search string to log
	 */

	public void logMatchesFound(String regex){
		if (uniqueTextViews.size() > 0) {
			Log.d(LOG_TAG, " There are only " + uniqueTextViews.size() + " matches of '" + regex + "'");
		}
		else if(webElements.size() > 0){
			Log.d(LOG_TAG, " There are only " + webElements.size() + " matches of '" + regex + "'");
		}
		uniqueTextViews.clear();
		webElements.clear();
	}
}
