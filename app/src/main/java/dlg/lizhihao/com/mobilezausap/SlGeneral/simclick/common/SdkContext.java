package dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.common;

/**
 * Created by sogood on 16/10/19.
 */

public class SdkContext {

    public static boolean debug;

    public static final String TAG = SdkContext.class.getSimpleName();

    /** 当前任务的pv */
    public static volatile int pageViewCount;

    /** 浏览器user agent */
    public static volatile String userAgent;
}
