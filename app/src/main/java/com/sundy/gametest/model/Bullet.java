package com.sundy.gametest.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.sundy.gametest.config.Constant;
import com.sundy.gametest.view.MySurfaceView;

/**
 * 子弹对象
 * Created by David on 2017/7/23.
 */

public class Bullet {
    MySurfaceView gameView;
    private Bitmap bitmap;
    private Bitmap[] bitmaps;
    float x;
    float y;
    float vx;
    float vy;
    private float t=0;
    private float timeSpan=0.5f;
    int size;
    boolean explodeFlag=false;
    Explosion mExplosion;

    public Bullet(MySurfaceView gameView,Bitmap bitmap ,Bitmap[] bitmaps,float x,float y,float vx,float vy){
        this.gameView=gameView;
        this.bitmap=bitmap;
        this.bitmaps=bitmaps;
        this.x=x;
        this.y=y;
        this.vx=vx;
        this.vy=vy;
        size=bitmap.getHeight();
    }

    public void drawSelf(Canvas canvas, Paint paint){
        if(explodeFlag && mExplosion!=null){
            mExplosion.drawSelf(canvas,paint);
        }else {
            go();
            canvas.drawBitmap(bitmap,x,y,paint);
        }
    }
    public void go(){
        x+=vx*t;
        y+=vy*t+0.5f* Constant.G*t*t;
        if(x>=Constant.EXPLOSION_X ||y>=Constant.SCREEN_HEIGHT){
        mExplosion=new Explosion(gameView,bitmaps,x,y);
        explodeFlag=true;
        return;
        }
     t+=timeSpan;
    }
}
