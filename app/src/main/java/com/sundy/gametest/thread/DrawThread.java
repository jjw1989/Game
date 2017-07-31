package com.sundy.gametest.thread;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

import com.sundy.gametest.opengl.Mysurface;
import com.sundy.gametest.view.MySurfaceView;

/**
 * Created by David on 2017/7/23.
 */

public class DrawThread extends Thread {
    private boolean flag=true;
    private int sleepSpan=100;
    MySurfaceView gameView;
    SurfaceHolder surfaceHolder;
    public DrawThread(MySurfaceView gameView){
     this.gameView=gameView;
     this.surfaceHolder=gameView.getHolder();
    }

    @SuppressLint("WrongCall")
    @Override
    public void run() {
        Canvas c;
        while (this.flag){
            c=null;
            try{
                c=this.surfaceHolder.lockCanvas(null);
                synchronized (this.surfaceHolder){
                    gameView.onDraw(c);
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(c!=null){
                    this.surfaceHolder.unlockCanvasAndPost(c);
                }
            }

            try{
                Thread.sleep(sleepSpan);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    public void setFloag(boolean flag){
        this.flag=flag;
    }
}
