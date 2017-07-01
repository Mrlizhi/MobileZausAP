package dlg.lizhihao.com.mobilezausap.tbk;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by SEELE on 2017/6/9.
 */

public class GetTaokoulingTask {
    private String mUrl="http://baidu.wzzjh168.cn/s/kk.php?id=";
    private String mContent;
    private final static int  MSG_EXECUTE_GET_TASK = 1024;
    private Runnable mDelayRunnable;
    private Activity mActivity;
    private int id;
    public GetTaokoulingTask(Activity mActivity,int id) {
        this.mActivity = mActivity;
        this.id=id;
    }

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case MSG_EXECUTE_GET_TASK:
                    postTimeoutKeepLive();
                    break;
            }
        }

        private void postTimeoutKeepLive(){
            mDelayRunnable = new Runnable() {
                @Override
                public void run() {
                    Message msg = new Message();
                    msg.what = MSG_EXECUTE_GET_TASK;
                    mHandler.sendMessage(msg);
                    new ClipboardTask().execute();
                }
            };
            mHandler.postDelayed(mDelayRunnable, 360000);
        }
    };

    public   void startTask(){
        new ClipboardTask().execute();
        Message msg = new Message();
        msg.what = MSG_EXECUTE_GET_TASK;
        mHandler.sendMessage(msg);
    }

    private class ClipboardTask  extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            return mContent =  HttpClient.sendGet(mUrl+id);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (null != mContent && !mContent.trim().equals("")){
                clipboard();
            }
        }
    }


    private void clipboard(){
        ClipboardManager clipboardManager = (ClipboardManager) mActivity.getSystemService(Context.CLIPBOARD_SERVICE);
        mContent = "￥" + mContent + "￥" ;
        clipboardManager.setPrimaryClip(ClipData.newPlainText(null, mContent));  // 将内容set到剪贴板
        Log.d("MySDK","#===success");
        if (clipboardManager.hasPrimaryClip()){
//            clipboardManager.getPrimaryClip().getItemAt(0).getText();  // 获取内容
        }
    }
}
