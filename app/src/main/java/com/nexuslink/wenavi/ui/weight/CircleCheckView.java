package com.nexuslink.wenavi.ui.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.nexuslink.wenavi.R;

/**
 * Created by 18064 on 2018/1/13.
 */

public class CircleCheckView extends View {

    public static final String TAG = "CircleCheckView";

    private Paint mPaint;

    private int mColor = R.color.colorPrimary_Default;

    private boolean checked  = false;

    public CircleCheckView(Context context) {
        this(context,null);
    }

    public CircleCheckView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleCheckView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int x = getWidth()/2;
        int y = getHeight()/2;
        int r = x;
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(getResources().getColor(mColor));
        canvas.drawCircle(x,y, r,mPaint);
        if (checked) {
            //绘制沟
            drawFlag(canvas,x,y,r);
        }
    }

    public void setColor(int color) {
        this.mColor = color;
    }

    public void setChecked(boolean checked) {
//        if (checked) {
//            //绘制沟
//            drawFlag();
//        }else {
//            //设置为没有沟的过程：1.原本有沟选（消除勾选） 2.原本没有沟选(不需要做任何操作)
//            if (this.checked) {
//                eraserFlag();
//            }
//        }
        this.checked = checked;
        postInvalidate();
    }

//    private void eraserFlag() {
//
//    }

    private void drawFlag(Canvas canvas, int x, int y, int r) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);
        mPaint.setColor(Color.WHITE);
        Path path = new Path();
        path.moveTo(x/2,y);
        path.lineTo(x/4 + 17,y + 10);
        path.lineTo(x/4 + 35,y - 8);
        canvas.drawPath(path,mPaint);
    }
}
