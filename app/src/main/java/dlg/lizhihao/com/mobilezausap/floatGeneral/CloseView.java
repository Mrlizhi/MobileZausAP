package dlg.lizhihao.com.mobilezausap.floatGeneral;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import org.jetbrains.annotations.Nullable;

/**
 * Created by Administrator on 2017/6/23.
 */

public class CloseView extends View {

    private Paint mPaint = new Paint();
    private int mHeight, mWidth;

    public CloseView(Context context) {
        super(context);
    }

    public CloseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //初始化画笔属性
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(3f);

        //设置画布抗齿距 将坐标定位到中心
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        canvas.translate(mWidth / 2, mHeight / 2);

        //绘制圆形
        RectF rectF3 = new RectF(-30, -30, 30, 30);
        canvas.drawOval(rectF3, mPaint);

        //从坐标 x -12,y -12 ->  x 12,y 12 之间绘制一条线
        canvas.drawLines(new float[]{
                -12, -12, 12, 12,
                12, -12, -12, 12
        }, mPaint);

    }
}
