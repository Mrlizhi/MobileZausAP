package dlg.lizhihao.com.mobilezausap.floatGeneral.baidu;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.baidu.mobads.AdView;
import com.baidu.mobads.InterstitialAd;
import com.baidu.mobads.InterstitialAdListener;

import dlg.lizhihao.com.mobilezausap.R;

public class ResidentAdActivity extends Activity {

    InterstitialAd interAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resident_ad);
        //        Toast.makeText(this, "baidu ad show", Toast.LENGTH_SHORT).show();
//        StatService.onEvent(this, "custom_toast_event_start", "mssp");
        AdView.setAppSid(this,"e5c20c12");//可用
//        AdView.setAppSid(this,"e50c232c");
//        AdView.setAppSid(this,"e1fb7141");
//        2403633
//        String adPlaceId = "2403633"; //测试
        String adPlaceId = "3667152"; //  可用
//        String adPlaceId = "3664459"; // 拉不出广告
//        String adPlaceId = "4138732"; // 拉不出广告
        interAd = new InterstitialAd(this, adPlaceId);
        interAd.setListener(new InterstitialAdListener() {
            @Override
            public void onAdClick(InterstitialAd arg0) {
//                StatService.onEvent(ResidentAdActivity.this, "custom_toast_event_click", "mssp");
                Log.i("InterstitialAd", "onAdClick");
                finish();
            }
            @Override
            public void onAdDismissed() {
                Log.i("InterstitialAd", "onAdDismissed");
                interAd.loadAd();
                finish();
            }
            @Override
            public void onAdFailed(String arg0) {
                Log.i("InterstitialAd", "onAdFailed");
            }

            @Override
            public void onAdPresent() {
                Log.i("InterstitialAd", "onAdPresent");
            }

            @Override
            public void onAdReady() {
                Log.i("InterstitialAd", "onAdReady");
                interAd.showAd(ResidentAdActivity.this);
//                StatService.onEvent(ResidentAdActivity.this, "event_bdad_load_finish", "mssp");
            }

        });
        interAd.loadAd();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
//        StatService.onEvent(ResidentAdActivity.this, "event_touch_finish", "mssp");
        return super.onTouchEvent(event);
    }
}
