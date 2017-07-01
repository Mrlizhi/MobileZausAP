package dlg.lizhihao.com.mobilezausap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import dlg.lizhihao.com.mobilezausap.SlGeneral.MyService;
import dlg.lizhihao.com.mobilezausap.floatGeneral.FloatWindow;
import dlg.lizhihao.com.mobilezausap.floatGeneral.ResidentService;
import dlg.lizhihao.com.mobilezausap.floatGeneral.baidu.BaiduResidentService;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this,ZausService.class));
        if(getIntent().getStringExtra("url")!=null){
            FloatWindow floatWindow=new FloatWindow(this);
            floatWindow.initToastView();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        finish();
    }
}
