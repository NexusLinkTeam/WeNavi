package com.nexuslink.wenavi.ui.weight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 18064 on 2018/1/30.
 */

public class SkewWallpaperView extends View {

    private static final float RATE = 0.6f;

    private Paint paint;

    public SkewWallpaperView(Context context) {
        super(context, null);
    }

    public SkewWallpaperView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SkewWallpaperView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint = new Paint();
        paint.setColor(Color.parseColor("#82B1FF"));
        Path path = new Path();
        path.moveTo(0, getHeight());
        path.lineTo(getWidth(), (float) (getHeight() * RATE));
        path.lineTo(getWidth(),0);
        path.lineTo(0,0);
        path.close();
        canvas.drawPath(path, paint);
    }
}
