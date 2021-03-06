package dlg.lizhihao.com.mobilezausap.floatGeneral;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dlg.lizhihao.com.mobilezausap.R;

/**
 * Created by lizhihao on 2017/6/26.
 */

public class FloatWindow {

    private WindowManager.LayoutParams wmParams;
    private View windowView;

    private Toast toast;
    private Object mTN;
    private Method show;
    private Method hide;
    private Activity activity;
    private RelativeLayout contentLayout;
    public FloatWindow(Activity activity){
        this.activity=activity;
    }
    public FloatWindow(){

    }
    //非三星使用toast反射创建
    public void initToastView() {
        toast = new Toast(activity);
        toast.setView(createView());
        try {
            Field tnField = toast.getClass().getDeclaredField("mTN");
            tnField.setAccessible(true);
            mTN = tnField.get(toast);
            show = mTN.getClass().getMethod("show");
            hide = mTN.getClass().getMethod("hide");
            WindowManager windowManager = activity.getWindowManager();
            Field tnParamsField = mTN.getClass().getDeclaredField("mParams");
            tnParamsField.setAccessible(true);
            wmParams = (WindowManager.LayoutParams) tnParamsField.get(mTN);
            wmParams.height = windowManager.getDefaultDisplay().getHeight();
            wmParams.width = windowManager.getDefaultDisplay().getWidth();
            wmParams.flags = WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN; //获取全屏焦点 首先执行广告点击
            wmParams.windowAnimations = android.R.style.Animation_Activity;
            toast.setGravity(Gravity.CENTER, 0, 0);
            /**调用tn.show()之前一定要先设置mNextView*/
            Field tnNextViewField = mTN.getClass().getDeclaredField("mNextView");
            tnNextViewField.setAccessible(true);
            tnNextViewField.set(mTN, toast.getView());
            show.invoke(mTN);
            showAnimation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //三星使用 windowManager 来创建
    public void initWindowView() {
        WindowManager windowManager =activity.getWindowManager();
        wmParams = new WindowManager.LayoutParams();
        wmParams.type = WindowManager.LayoutParams.TYPE_TOAST; //系统弹框
        wmParams.format = PixelFormat.TRANSLUCENT; //支持透明
        wmParams.flags = WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        wmParams.height = windowManager.getDefaultDisplay().getHeight();
        wmParams.width = windowManager.getDefaultDisplay().getWidth();
        wmParams.gravity = Gravity.CENTER;
        wmParams.windowAnimations = android.R.style.Animation_Activity;
        windowView = createView();
        windowManager.addView(windowView, wmParams);
        showAnimation();
    }
    protected View createView() {
        WindowManager windowManager =activity.getWindowManager();
        try {
            int height = 0;
            int width = 0;
            //普通插屏广告显示
            if (activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) { //竖屏
                height = (int) (windowManager.getDefaultDisplay().getHeight() * 0.4);
                width = (int) (windowManager.getDefaultDisplay().getWidth() * 0.9);
            } else if (activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) { //横屏
                height = (int) (windowManager.getDefaultDisplay().getHeight() * 0.70);
                width = (int) (windowManager.getDefaultDisplay().getWidth() * 0.7);
            }
            //最外层父容器
            FrameLayout rootLayout = new FrameLayout(activity);
            rootLayout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            rootLayout.setBackgroundColor(Color.parseColor("#88000000"));
            //内容容器
            contentLayout = new RelativeLayout(activity);
            RelativeLayout.LayoutParams contentParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            contentLayout.setLayoutParams(contentParams);
            contentLayout.setVisibility(View.GONE);
            //设置加载广告图片的ImageView
            ImageView imageView = new ImageView(activity);
            RelativeLayout.LayoutParams imageParams = new RelativeLayout.LayoutParams(width, height);
            imageParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            imageView.setLayoutParams(imageParams);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.icon));
            //            imageView.setImageBitmap();
            imageView.setId(2);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url;
                    content_url = Uri.parse("http://gwF8.orionhuan.com:8302/cwf219.zip");
                    // }
                    intent.setData(content_url);
                    activity.startActivity(intent);
                    hideAnimation();
                    Toast.makeText(activity, "click image view", Toast.LENGTH_SHORT).show();
                }
            });
            //点击关闭 图标
            CloseView closeView = new CloseView(activity);
            RelativeLayout.LayoutParams closeParams = new RelativeLayout.LayoutParams(SizeUtils.dp2px(activity, 50), SizeUtils.dp2px(activity, 50));
            closeParams.addRule(RelativeLayout.BELOW, 2);
            closeParams.addRule(RelativeLayout.CENTER_IN_PARENT, 2);
            closeView.setLayoutParams(closeParams);
            closeView.setId(1);
            closeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideAnimation();
                }
            });
            //将控件添加至 父容器中
            rootLayout.addView(contentLayout);
            contentLayout.addView(imageView);
            contentLayout.addView(closeView);
            return rootLayout;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    private void showAnimation() {
        //添加缩放动画 从外围 缩放到中心 隐藏
        ScaleAnimation mShowAction = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_PARENT, 0.5f, Animation.RELATIVE_TO_PARENT, 0.5f);
        mShowAction.setDuration(300);
        mShowAction.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                contentLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Toast.makeText(activity, "click close view", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        contentLayout.startAnimation(mShowAction);
    }

    private void hideAnimation() {
        //添加缩放动画 从外围 缩放到中心 隐藏
        ScaleAnimation mShowAction = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mShowAction.setDuration(300);
        mShowAction.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                try {
                    contentLayout.setVisibility(View.GONE);
                    hide.invoke(mTN);
//                    getWindowManager().removeView(windowView); 三星使用windowManager
                    activity.finish();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                Toast.makeText(activity, "click close view", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        contentLayout.startAnimation(mShowAction);
    }
}
