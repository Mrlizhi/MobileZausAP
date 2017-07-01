package dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util;

/**
 * Created by sogood on 16/10/29.
 */

public class OperationSleeper {


    public static final int sleepMiniDuration = 100;

    /**
     * Sleeps the current thread for the pause length.
     */
    public static void sleepMini() {
        sleep(sleepMiniDuration);
    }

    /**
     * Sleeps the current thread for <code>time</code> milliseconds.
     *
     * @param time the length of the sleep in milliseconds
     */
    public static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ignored) {
            ignored.printStackTrace();
        }
    }

}
