package com.nexuslink.wenavi.ui.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.nexuslink.wenavi.R;

/**
 * Created by 18064 on 2018/1/14.
 */

public class SimpleDecoration extends RecyclerView.ItemDecoration {

    private Paint mPaint;

    public static final int WIDTH_DECORATION = 1;

    public SimpleDecoration(Context context) {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(context.getResources().getColor(R.color.colorDecoration));
        mPaint.setStrokeWidth(WIDTH_DECORATION);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            c.drawLine(0, child.getBottom(), child.getWidth(),child.getBottom(), mPaint);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0,0,0,WIDTH_DECORATION);
    }
}
