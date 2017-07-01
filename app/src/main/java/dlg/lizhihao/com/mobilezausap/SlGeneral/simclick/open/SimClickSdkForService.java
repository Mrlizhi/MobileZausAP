package dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.open;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.webkit.WebView;

import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingDeque;

import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.common.SdkContext;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.operation.BackOperation;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.operation.BaseOperation;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.operation.CleanCookiesOperation;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.operation.LoadAndRedirectUrlOperation;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.operation.LoadUrlOperation;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.operation.Operation;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.operation.PrintWebElementOperation;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.operation.RandClickByConditionOperation;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.operation.RandClickPositionOperation;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.operation.RandExecuteJsOperation;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.operation.ReloadOperation;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.operation.ResizeWebViewOperation;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.operation.WaitOperation;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.robotium.By;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.robotium.SgSolo;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util.LogUtil;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.view.CustomFloatWeb;

/**
 * Created by sogood on 16/10/15.
 * 模拟点击SDK API,其所提供方法不阻塞线程
 */
public class SimClickSdkForService {

    private static final String TAG = SimClickSdkForService.class.getSimpleName();

    private WebView mWebview;
    private SgSolo mWebSolo;
    private Context mContext;
    private static SimClickSdkForService sInstance;

    /** 线程安全的操作任务队列 */
    private LinkedBlockingDeque<LinkedList<BaseOperation>> mQueueList = new LinkedBlockingDeque<LinkedList<BaseOperation>>();

    /** 操作任务 */
    private LinkedList<BaseOperation> mTempOperateList;

    /** 当前任务id */
    private int mTmpTaskId;

    private int mParamId;

    private OnTaskResultListener mTaskResultListener;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    private SimClickSdkForService(Context context) {
        mContext = context;
        setupWebView(context);
        mWebSolo = new SgSolo(context, mWebview, mHandler);
        setupOperationThread();
    }

    public static synchronized SimClickSdkForService getInstance(Context context) {
        if (null == sInstance) {
            sInstance = new SimClickSdkForService(context);
        }
        return sInstance;
    }

    private void setupWebView(Context context) {
        CustomFloatWeb customFloatWeb = new CustomFloatWeb(context);
        customFloatWeb.create();
        mWebview = customFloatWeb.getWebView();
    }

    private void setupOperationThread() {
        new TaskRunningThread().start();
    }

    public void setDebug(boolean debug) {
        SdkContext.debug = debug;
    }

    public void beginTransaction(int taskId, int paramId) {
        LogUtil.i(TAG, "advertising beginTransaction, taskId = " + taskId + ", paramId = " + paramId);
        mTmpTaskId = taskId;
        mParamId = paramId;
        mTempOperateList = new LinkedList<>();
    }

    /**
     * 一个commit必须对应一个begin 否则可能会导致pv统计错误等不可预知问题
     */
    public void commitTransaction() {

        mTmpTaskId = 0;
        mParamId = 0;
        SdkContext.pageViewCount = 0;
        try {
            mQueueList.put(mTempOperateList);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mTempOperateList = null;
    }
    /**
     * 加载url

     * @param step 每一个模拟操作的顺序id 暂时没用
     * @param url
     */
    public void loadPage(double step, String url,String src) {
        if (null != mTempOperateList) {
            LoadUrlOperation operation = new LoadUrlOperation(mWebview, url,src);
            operation.setId(mTmpTaskId, mParamId, step);
            mTempOperateList.add(operation);
        }
    }

    public void loadAndRedirPage(double step, String fromUrl, String toUrl,String src) {
        if (null != mTempOperateList) {
            LoadAndRedirectUrlOperation operation = new LoadAndRedirectUrlOperation(mWebview, fromUrl, toUrl,src);
            operation.setId(mTmpTaskId, mParamId, step);
            mTempOperateList.add(operation);
        }
    }

    /**
     * 刷新当前页面
     * @param step
     */
    public void reloadPage(double step) {
        if (null != mTempOperateList) {
            ReloadOperation operation = new ReloadOperation(mWebview);
            operation.setId(mTmpTaskId, mParamId, step);
            mTempOperateList.add(operation);
        }
    }
    /**
     * 在页面停留
     * @param step 每一个模拟操作的顺序id 暂时没用
     * @param timeMills 毫秒数
     */
    public void stay(double step, long timeMills) {
        if (null != mTempOperateList) {
            WaitOperation operation = new WaitOperation(timeMills);
            operation.setId(mTmpTaskId, mParamId, step);
            mTempOperateList.add(operation);
        }
    }

    public void cleanCookies(double step) {
        if (null != mTempOperateList) {
            CleanCookiesOperation operation = new CleanCookiesOperation(mContext);
            operation.setId(mTmpTaskId, mParamId, step);
            mTempOperateList.add(operation);
        }
    }
    public void goBack(double step) {
        if (null != mTempOperateList) {
            BackOperation backOperation = new BackOperation();
            backOperation.setId(mTmpTaskId, mParamId, step);
            mTempOperateList.add(backOperation);
        }
    }
    public void randClickWebElement(double step, int stayMin, int stayMax, String[] attrNames, String[] attrValues, int[] matchType,
                                    String[] attrNames2, String[] attrValues2, int[] matchType2, String[] rmAttrNames,
                                    String[] rmAttrValue, int[] rmMatchType, int linkOperatorType, int rand, int index,String src) {
        if (null != mTempOperateList) {
            RandClickByConditionOperation operation = new RandClickByConditionOperation(mWebview, rand, stayMin, stayMax, attrNames, attrValues, matchType,
            attrNames2, attrValues2, matchType2, rmAttrNames, rmAttrValue, rmMatchType, linkOperatorType, index,src);
            operation.setId(mTmpTaskId, mParamId, step);
            mTempOperateList.add(operation);
        }
    }

    public void randExecuteJavaScript(double step, String code, int rand, int stayMin, int stayMax) {
        if (null != mTempOperateList) {
            RandExecuteJsOperation operation = new RandExecuteJsOperation(mWebview, code, rand, stayMin, stayMax);
            operation.setId(mTmpTaskId, mParamId, step);
            mTempOperateList.add(operation);
        }
    }

    public void randClickByPosition(double step, int width, int heigth, int left, int top,
                                int rectWidth, int rectHeight, int retryCount, int msgType, int rand, int stayMin, int stayMax,String src) {
        if (null != mTempOperateList) {
            RandClickPositionOperation operation = new RandClickPositionOperation(mWebview,
                    rand, width, heigth, left, top, rectWidth, rectHeight, retryCount, msgType, stayMin, stayMax,src);
            operation.setId(mTmpTaskId, mParamId, step);
            mTempOperateList.add(operation);
        }
    }

    public void printWebElementBy(double step, By by) {
        if (null != mTempOperateList) {
            PrintWebElementOperation operation = new PrintWebElementOperation(by);
            operation.setId(mTmpTaskId, mParamId, step);
            mTempOperateList.add(operation);
        }

    }

    public void resizeWebView(double step, int width, int height) {
        if (null != mTempOperateList) {
            ResizeWebViewOperation operation = new ResizeWebViewOperation(width, height);
            operation.setId(mTmpTaskId, mParamId, step);
            mTempOperateList.add(operation);
        }
    }

    public void setOnTaskResultListener(OnTaskResultListener listener) {
        this.mTaskResultListener = listener;
    }

    public interface OnTaskResultListener {

        /**
         * 模拟点击/输入内容 操作成功
         * @param taskFinish 该操作是否任务的最后一步(任务是否已经完成)
         * @param taskId 任务id
         * @param programId
         * @param step 当前模拟操作的顺序id(暂时没用)
         */
        void onSuccess(boolean taskFinish, int taskId, int programId, double step, int pv);

        void onFailure(boolean taskFinish, int taskId, int programId, double step);

    }

    /**
     * 执行模拟点击任务的线程
     */
    private class TaskRunningThread extends Thread {
        @Override
        public void run() {
            super.run();

            while (true) {
                try {
                    LinkedList<BaseOperation> operationQueue = mQueueList.take();
                    LogUtil.i(TAG, "advertising TaskRunningThread, mQueueList.take(), operationQueue.size  = " + operationQueue.size());
                    double skipStepId = -1;
                    int lastRsl = 0;
                    for (int i = 0; i < operationQueue.size(); i++) {
                        boolean taskFinish = false;
                        if (i == operationQueue.size() - 1) {
                            taskFinish = true;
                        }
                        BaseOperation operation = operationQueue.get(i);
                        if (skipStepId > operation.getStep()) {
                            if (null != mTaskResultListener && lastRsl == Operation.JUMP) {
                                mTaskResultListener.onSuccess(taskFinish, operation.getTaskId(), operation.getParamId(), operation.getStep(), SdkContext.pageViewCount);
                            }
                            continue;
                        }
                        int rsl = operation.doExecute(mHandler, mWebview, mWebSolo);
                        lastRsl = rsl;

                        if (rsl == Operation.FAIL || rsl == Operation.JUMP) {
                            skipStepId = operation.getStep();
                            if (null != mTaskResultListener && rsl == Operation.FAIL) {
                                mTaskResultListener.onFailure(taskFinish, operation.getTaskId(), operation.getParamId(), operation.getStep());
                            }
                        }
                        if (null != mTaskResultListener && (rsl == Operation.JUMP || rsl == Operation.SUCC)) {
                            mTaskResultListener.onSuccess(taskFinish, operation.getTaskId(), operation.getParamId(), operation.getStep(), SdkContext.pageViewCount);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
