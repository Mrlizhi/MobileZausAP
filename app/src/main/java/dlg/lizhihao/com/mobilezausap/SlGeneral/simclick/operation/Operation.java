package dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.operation;

import android.os.Handler;
import android.webkit.WebView;

import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.robotium.SgSolo;

/**
 * Created by sogood on 16/10/27.
 */

public interface Operation {

    public static final int LOAD_WEB_TIMEOUT = 30000;

    public static final int SUCC = 1;
    public static final int FAIL = 0;
    public static final int JUMP = 2;

    public int doExecute(Handler handler, WebView webView, SgSolo solo);

}
