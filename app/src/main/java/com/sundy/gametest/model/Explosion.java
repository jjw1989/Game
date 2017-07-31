package com.sundy.gametest.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.sundy.gametest.opengl.Mysurface;
import com.sundy.gametest.view.MySurfaceView;

/**
 * b波炸对象
 * Created by David on 2017/7/23.
 */

public class Explosion {
    MySurfaceView gameView;
    private Bitmap[] bitmaps;
    float x;
    float y;
    private int animIndex=0;
    public Explosion(MySurfaceView gameView,Bitmap[] bitmaps,float x,float y){
        this.gameView=gameView;
        this.bitmaps=bitmaps;
        this.x=x;
        this.y=y;
    }
    public void drawSelf(Canvas canvas,Paint paint){
        if(animIndex>=bitmaps.length-1){
            return;
        }
        canvas.drawBitmap(bitmaps[animIndex],x,y,paint);
        animIndex++;
    }
}
