package dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.operation;

import android.os.Handler;
import android.webkit.WebView;

import java.util.List;

import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.robotium.By;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.robotium.SgSolo;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.robotium.WebElement;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util.LogUtil;

/**
 * Created by sogood on 17/1/17.
 */

public class PrintWebElementOperation extends BaseOperation {

    private static final String TAG = PrintWebElementOperation.class.getSimpleName();

    private By mBy;

    public PrintWebElementOperation(By by) {
        mBy = by;
    }

    @Override
    public int doExecute(Handler handler, WebView webView, SgSolo solo) {
        List<WebElement> webElementList = solo.getWebElementsBy(mBy);

//        net.task I/PrintWebElementOperation: null, {href=//yys.moguzb.com}
//        net.task I/PrintWebElementOperation: null, {href=/}
//        net.task I/PrintWebElementOperation: null, {href=update_log.html}
//        net.task I/PrintWebElementOperation: null, {href=bug.html}
//        net.task I/PrintWebElementOperation: null, {href=help.html}
//        net.task I/PrintWebElementOperation: null, {href=http://www.miitbeian.gov.cn/state/outPortal/loginPortal.action, target=_blank}
//        net.task I/PrintWebElementOperation: null, {href=http://www.cnzz.com/stat/website.php?web_id=1260739646, title=站长统计, target=_blank}

        for (WebElement element : webElementList) {
            LogUtil.i(TAG, element.getName() + ", " + element.getAttributes());
        }
        return SUCC;
    }

}
