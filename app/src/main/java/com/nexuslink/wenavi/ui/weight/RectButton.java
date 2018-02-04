package com.nexuslink.wenavi.ui.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;

import com.nexuslink.wenavi.R;

/**
 * Created by 18064 on 2018/1/13.
 */

public class RectButton extends android.support.v7.widget.AppCompatTextView {

    private Paint mPaint;

    private int color;

    private boolean checked = false;

    public RectButton(Context context) {
        this(context, null);
    }

    public RectButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RectButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        if (checked) {
            mPaint.setColor(getResources().getColor(color));
            setTextColor(getResources().getColor(color));
        } else {
            mPaint.setColor(getResources().getColor(R.color.colorGray));
            setTextColor(getResources().getColor(R.color.colorGray));
        }

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        canvas.drawRoundRect(10,10 ,getMeasuredWidth()-10,getMeasuredHeight()-10,8,8,mPaint);
        super.onDraw(canvas);
    }

    public void setColor(int color) {
        this.checked = true;
        this.color = color;
        postInvalidate();
    }
}
