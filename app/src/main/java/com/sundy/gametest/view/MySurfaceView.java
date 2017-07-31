package com.sundy.gametest.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.sundy.gametest.MainActivity;
import com.sundy.gametest.R;
import com.sundy.gametest.model.Bullet;
import com.sundy.gametest.thread.DrawThread;

/**
 * Created by David on 2017/7/23.
 */

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    MainActivity activity;
    Paint paint;
    DrawThread drawThread;
    Bitmap bgBmp;
    Bitmap bulletBmp;
    Bitmap[] explodeBmps;
    Bullet bullet;

    public MySurfaceView(MainActivity context) {
        super(context);
        this.activity = context;
        this.requestFocus();
        this.setFocusableInTouchMode(true);
        getHolder().addCallback(this);
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bgBmp, 0, 0, paint);
        bullet.drawSelf(canvas, paint);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        paint = new Paint();
        paint.setAntiAlias(true);
        bulletBmp = BitmapFactory.decodeResource(this.getResources(), R.mipmap.bullet);
        bgBmp = BitmapFactory.decodeResource(this.getResources(), R.mipmap.bg);
        explodeBmps = new Bitmap[]{
                BitmapFactory.decodeResource(this.getResources(), R.mipmap.explode0),
                BitmapFactory.decodeResource(this.getResources(), R.mipmap.explode1),
                BitmapFactory.decodeResource(this.getResources(), R.mipmap.explode2),
                BitmapFactory.decodeResource(this.getResources(), R.mipmap.explode3),
                BitmapFactory.decodeResource(this.getResources(), R.mipmap.explode4),
                BitmapFactory.decodeResource(this.getResources(), R.mipmap.explode5),

        };
        bullet = new Bullet(this, bulletBmp, explodeBmps, 0, 290, 1.3f, -5.9f);
        drawThread = new DrawThread(this);
        drawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        drawThread.setFloag(false);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
