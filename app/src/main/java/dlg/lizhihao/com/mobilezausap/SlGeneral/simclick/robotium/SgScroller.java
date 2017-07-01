package dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.robotium;

import android.os.Handler;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.GridView;

;


/**
 * Contains scroll methods. Examples are scrollDown(), scrollUpList(),
 * scrollToSide().
 *
 * @author Renas Reda, renas.reda@robotium.com
 *
 */

class SgScroller {

	public static final int DOWN = 0;
	public static final int UP = 1;
	public enum Side {LEFT, RIGHT}
	private boolean canScroll = false;
	private final Sleeper sleeper;
	private final Solo.Config config;
	private Handler mHandler;
	private WebView mWebView;

	public SgScroller(Solo.Config config, Handler handler, WebView webview, Sleeper sleeper) {
		this.config = config;
		this.sleeper = sleeper;
		this.mHandler = handler;
		this.mWebView = webview;
	}

	/**
	 * Simulate touching a specific location and dragging to a new location.
	 *
	 * This method was copied from {@code TouchUtils.java} in the Android Open Source Project, and modified here.
	 *
	 * @param fromX X coordinate of the initial touch, in screen coordinates
	 * @param toX Xcoordinate of the drag destination, in screen coordinates
	 * @param fromY X coordinate of the initial touch, in screen coordinates
	 * @param toY Y coordinate of the drag destination, in screen coordinates
	 * @param stepCount How many move steps to include in the drag
	 */

	public void drag(float fromX, float toX, float fromY, float toY,
					 int stepCount) {
		long downTime = SystemClock.uptimeMillis();
		long eventTime = SystemClock.uptimeMillis();
		float y = fromY;
		float x = fromX;
		float yStep = (toY - fromY) / stepCount;
		float xStep = (toX - fromX) / stepCount;
		MotionEvent event = MotionEvent.obtain(downTime, eventTime,MotionEvent.ACTION_DOWN, fromX, fromY, 0);
		try {
			mWebView.dispatchTouchEvent(event);
		} catch (SecurityException ignored) {}
		for (int i = 0; i < stepCount; ++i) {
			y += yStep;
			x += xStep;
			eventTime = SystemClock.uptimeMillis();
			event = MotionEvent.obtain(downTime, eventTime,MotionEvent.ACTION_MOVE, x, y, 0);
			try {
				mWebView.dispatchTouchEvent(event);
			} catch (SecurityException ignored) {}
		}
		eventTime = SystemClock.uptimeMillis();
		event = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_UP,toX, toY, 0);
		try {
			mWebView.dispatchTouchEvent(event);
		} catch (SecurityException ignored) {}
	}


	/**
	 * Scrolls a ScrollView.
	 *
	 * @param direction the direction to be scrolled
	 * @return {@code true} if scrolling occurred, false if it did not
	 */

	public boolean scrollView(final View view, int direction){
		if(view == null){
			return false;
		}

		int height = view.getHeight();
		height--;
		int scrollTo = -1;

		if (direction == DOWN) {
			scrollTo = height;
		}

		else if (direction == UP) {
			scrollTo = -height;
		}

		int originalY = view.getScrollY();
		final int scrollAmount = scrollTo;
		mHandler.post(new Runnable(){
			public void run(){
				view.scrollBy(0, scrollAmount);
			}
		});

		if (originalY == view.getScrollY()) {
			return false;
		}
		else{
			return true;
		}
	}

	/**
	 * Scrolls a ScrollView to top or bottom.
	 *
	 * @param direction the direction to be scrolled
	 */

	public void scrollViewAllTheWay(final View view, final int direction) {
		while(scrollView(view, direction));
	}

	/**
	 * Scrolls up or down.
	 *
	 * @param direction the direction in which to scroll
	 * @return {@code true} if more scrolling can be done
	 */

	public boolean scroll(int direction) {
		return scroll(direction, false);
	}

	/**
	 * Scrolls down.
	 *
	 * @return {@code true} if more scrolling can be done
	 */

	public boolean scrollDown() {
		if(!config.shouldScroll) {
			return false;
		}
		return scroll(Scroller.DOWN);
	}

	/**
	 * Scrolls up and down.
	 *
	 * @param direction the direction in which to scroll
	 * @param allTheWay <code>true</code> if the view should be scrolled to the beginning or end,
	 *                  <code>false</code> to scroll one page up or down.
	 * @return {@code true} if more scrolling can be done
	 */

	@SuppressWarnings("unchecked")
	public boolean scroll(int direction, boolean allTheWay) {


		View view = mWebView;

		if (view == null) {
			return false;
		}

		if (view instanceof AbsListView) {
			return scrollList((AbsListView)view, direction, allTheWay);
		}

		if(view instanceof WebView){
			return scrollWebView((WebView)view, direction, allTheWay);
		}

		if (allTheWay) {
			scrollViewAllTheWay(view, direction);
			return false;
		} else {
			return scrollView(view, direction);
		}
	}

	/**
	 * Scrolls a WebView.
	 *
	 * @param webView the WebView to scroll
	 * @param direction the direction to scroll
	 * @param allTheWay {@code true} to scroll the view all the way up or down, {@code false} to scroll one page up or down                          or down.
	 * @return {@code true} if more scrolling can be done
	 */

	public boolean scrollWebView(final WebView webView, int direction, final boolean allTheWay){

		if (direction == DOWN) {
			mHandler.post(new Runnable(){
				public void run(){
					canScroll =  webView.pageDown(allTheWay);
				}
			});
		}
		if(direction == UP){
			mHandler.post(new Runnable(){
				public void run(){
					canScroll =  webView.pageUp(allTheWay);
				}
			});
		}
		return canScroll;
	}

	/**
	 * Scrolls a list.
	 *
	 * @param absListView the list to be scrolled
	 * @param direction the direction to be scrolled
	 * @param allTheWay {@code true} to scroll the view all the way up or down, {@code false} to scroll one page up or down
	 * @return {@code true} if more scrolling can be done
	 */

	public <T extends AbsListView> boolean scrollList(T absListView, int direction, boolean allTheWay) {

		if(absListView == null){
			return false;
		}

		if (direction == DOWN) {

			int listCount = absListView.getCount();
			int lastVisiblePosition = absListView.getLastVisiblePosition();

			if (allTheWay) {
				scrollListToLine(absListView, listCount-1);
				return false;
			}

			if (lastVisiblePosition >= listCount - 1) {
				if(lastVisiblePosition > 0){
					scrollListToLine(absListView, lastVisiblePosition);
				}
				return false;
			}

			int firstVisiblePosition = absListView.getFirstVisiblePosition();


			if(firstVisiblePosition != lastVisiblePosition)
				scrollListToLine(absListView, lastVisiblePosition);

			else
				scrollListToLine(absListView, firstVisiblePosition + 1);

		} else if (direction == UP) {
			int firstVisiblePosition = absListView.getFirstVisiblePosition();

			if (allTheWay || firstVisiblePosition < 2) {
				scrollListToLine(absListView, 0);
				return false;
			}
			int lastVisiblePosition = absListView.getLastVisiblePosition();

			final int lines = lastVisiblePosition - firstVisiblePosition;

			int lineToScrollTo = firstVisiblePosition - lines;

			if(lineToScrollTo == lastVisiblePosition)
				lineToScrollTo--;

			if(lineToScrollTo < 0)
				lineToScrollTo = 0;

			scrollListToLine(absListView, lineToScrollTo);
		}
		sleeper.sleep();
		return true;
	}


	/**
	 * Scroll the list to a given line
	 *
	 * @param view the {@link AbsListView} to scroll
	 * @param line the line to scroll to
	 */

	public <T extends AbsListView> void scrollListToLine(final T view, final int line){
		if(view == null)
			Assert.fail("AbsListView is null!");

		final int lineToMoveTo;
		if(view instanceof GridView) {
			lineToMoveTo = line+1;
		}
		else {
			lineToMoveTo = line;
		}

		mHandler.post(new Runnable(){
			public void run(){
				view.setSelection(lineToMoveTo);
			}
		});
	}

	/**
	 * Scrolls view horizontally.
	 *
	 * @param view the view to scroll
	 * @param side the side to which to scroll; {@link Side#RIGHT} or {@link Side#LEFT}
	 * @param scrollPosition the position to scroll to, from 0 to 1 where 1 is all the way. Example is: 0.55.
	 * @param stepCount how many move steps to include in the scroll. Less steps results in a faster scroll
	 */

	public void scrollViewToSide(View view, Side side, float scrollPosition, int stepCount) {
		int[] corners = new int[2];
		view.getLocationOnScreen(corners);
		int viewHeight = view.getHeight();
		int viewWidth = view.getWidth();
		float x = corners[0] + viewWidth * scrollPosition;
		float y = corners[1] + viewHeight / 2.0f;
		if (side == Side.LEFT)
			drag(corners[0], x, y, y, stepCount);
		else if (side == Side.RIGHT)
			drag(x, corners[0], y, y, stepCount);
	}
}
