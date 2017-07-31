package com.sundy.gametest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by David on 2017/7/15.
 */

public class MyView extends View {

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint background=new Paint();
        Paint line=new Paint();
        line.setStrokeWidth(4);
        background.setColor(Color.GRAY);
        line.setColor(Color.RED);
        int px=500;
        int py=500;

        canvas.drawRect(0,0,px,py,background);

        canvas.rotate(90,px/2,py/2);

        canvas.drawLine(px/2,0,0,py/2,line);
        canvas.drawLine(px/2,0,px,py/2,line);
        canvas.drawLine(px/2,0,px/2,py,line);

        canvas.drawCircle(px-100,py-100,50,line);
    }
}
